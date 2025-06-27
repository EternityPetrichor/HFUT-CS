import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Client extends JFrame {
    private JTextArea logArea;
    private JProgressBar progressBar;
    private DatagramChannel channel;
    private Selector selector;
    private InetAddress serverAddress;
    private int serverPort;

    private int MAX_BLOCKS;
    private long totalFileSize;
    private byte[][] receivedBlocks;
    private boolean[] receivedBlocksStatus;

    public Client() {
        setTitle("UDP File Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        progressBar = new JProgressBar();
        add(progressBar, BorderLayout.SOUTH);

        JButton receiveButton = new JButton("Receive File");
        receiveButton.addActionListener(e -> receiveFile());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(receiveButton);
        add(buttonPanel, BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeChannel();
            }
        });

        try {
            selector = Selector.open();
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            log("Could not open channel: " + e.getMessage());
        }

        sendRequest();
    }

    private void closeChannel() {
        try {
            if (channel != null) {
                channel.close();
            }
            if (selector != null) {
                selector.close();
            }
        } catch (IOException e) {
            log("Error closing channel: " + e.getMessage());
        }
    }

    private void receiveFile() {
        new Thread(() -> {
            try {
                boolean fileInfoReceived = false;
                int expectedSequenceNumber = 0;
                long totalBytesReceived = 0;

                while (true) {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1028);
                            buffer.clear();
                            SocketAddress senderAddress = channel.receive(buffer);
                            buffer.flip();

                            if (!fileInfoReceived) {
                                if (buffer.remaining() == 4) {
                                    MAX_BLOCKS = buffer.getInt();
                                    log("Max blocks received: " + MAX_BLOCKS);
                                    receivedBlocks = new byte[MAX_BLOCKS][];
                                    receivedBlocksStatus = new boolean[MAX_BLOCKS];
                                } else if (buffer.remaining() == 8) {
                                    totalFileSize = buffer.getLong();
                                    log("Total file size received: " + totalFileSize);
                                    fileInfoReceived = true;
                                }
                            } else {
                                if (buffer.remaining() >= 0) {
                                    int sequenceNumber = buffer.getInt();
                                    byte[] data = new byte[buffer.remaining()];
                                    buffer.get(data);

                                    if (sequenceNumber == expectedSequenceNumber && !receivedBlocksStatus[sequenceNumber]) {
                                        receivedBlocks[sequenceNumber] = data;
                                        receivedBlocksStatus[sequenceNumber] = true;
                                        expectedSequenceNumber++;

                                        ByteBuffer ackBuffer = ByteBuffer.allocate(4);
                                        ackBuffer.putInt(sequenceNumber);
                                        ackBuffer.flip();
                                        channel.send(ackBuffer, senderAddress);

                                        totalBytesReceived += data.length;
                                        int progress = (int) ((totalBytesReceived * 100) / totalFileSize);
                                        SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
                                        log("Received block: " + sequenceNumber);
                                    }
                                }
                            }
                        }
                    }

                    boolean allReceived = true;
                    for (boolean status : receivedBlocksStatus) {
                        if (!status) {
                            allReceived = false;
                            break;
                        }
                    }

                    if (allReceived) {
                        File outputFile = chooseOutputFile();
                        if (outputFile != null) {
                            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
                                for (byte[] block : receivedBlocks) {
                                    bos.write(block);
                                }
                                bos.flush();
                                log("File saved as: " + outputFile.getAbsolutePath());
                            } catch (IOException e) {
                                log("Error saving file: " + e.getMessage());
                            }
                        } else {
                            log("Output file is null. File saving canceled.");
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                log("Error: " + e.getMessage());
            }
        }).start();
    }

    private File chooseOutputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    private void sendRequest() {
        try {
            serverAddress = InetAddress.getLocalHost();
            serverPort = 1111;
            ByteBuffer buffer = ByteBuffer.allocate(1);
            channel.send(buffer, new InetSocketAddress(serverAddress, serverPort));
            log("Request sent to server.");
        } catch (IOException e) {
            log("Error sending request: " + e.getMessage());
        }
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            client.setVisible(true);
        });
    }
}
