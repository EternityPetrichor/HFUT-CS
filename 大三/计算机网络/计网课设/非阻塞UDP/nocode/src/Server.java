import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Server extends JFrame {
    private JTextArea logArea;
    private JProgressBar progressBar;
    private DatagramChannel channel;
    private Selector selector;
    private InetAddress clientAddress;
    private int clientPort;

    public Server() {
        setTitle("UDP File Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        progressBar = new JProgressBar();
        add(progressBar, BorderLayout.SOUTH);

        JButton sendFileButton = new JButton("Send File to Client");
        sendFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFileToClient();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(sendFileButton);
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
            channel.socket().bind(new InetSocketAddress(1111));
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            log("Could not open channel: " + e.getMessage());
        }

        listen();
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

    private void sendFileToClient() {
        if (clientAddress == null || clientPort == 0) {
            log("No client connected.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            new Thread(() -> {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(selectedFile))) {
                    int blockSize = 1024;
                    long fileSize = selectedFile.length();
                    int MAX_BLOCKS_SERVER = (int) Math.ceil((double) fileSize / blockSize);

                    byte[] blockSizeBytes = ByteBuffer.allocate(4).putInt(MAX_BLOCKS_SERVER).array();
                    channel.send(ByteBuffer.wrap(blockSizeBytes), new InetSocketAddress(clientAddress, clientPort));

                    byte[] fileSizeBytes = ByteBuffer.allocate(8).putLong(fileSize).array();
                    channel.send(ByteBuffer.wrap(fileSizeBytes), new InetSocketAddress(clientAddress, clientPort));

                    byte[] buffer = new byte[blockSize];
                    int bytesRead;
                    int sequenceNumber = 0;
                    long totalBytesSent = 0;
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        ByteBuffer packetBuffer = ByteBuffer.allocate(bytesRead + 4);
                        packetBuffer.putInt(sequenceNumber);
                        packetBuffer.put(buffer, 0, bytesRead);
                        packetBuffer.flip();

                        boolean ackReceived = false;
                        while (!ackReceived) {
                            channel.send(packetBuffer, new InetSocketAddress(clientAddress, clientPort));
                            totalBytesSent += bytesRead;
                            int progress = (int) ((totalBytesSent * 100) / fileSize);
                            SwingUtilities.invokeLater(() -> {
                                progressBar.setValue(progress);
                            });

                            ByteBuffer ackBuffer = ByteBuffer.allocate(4);
                            channel.receive(ackBuffer);
                            ackBuffer.flip();
                            if (ackBuffer.remaining() == 4) {
                                int ackSequenceNumber = ackBuffer.getInt();
                                if (ackSequenceNumber == sequenceNumber) {
                                    ackReceived = true;
                                }
                            }
                            packetBuffer.rewind();
                        }
                        sequenceNumber++;
                    }
                    log("File sent to client: " + selectedFile.getName());
                } catch (IOException e) {
                    log("Error: " + e.getMessage());
                }
            }).start();
        }
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
        });
    }

    private void listen() {
        new Thread(() -> {
            try {
                while (true) {
                    /*使用了DatagramChannel来处理UDP通信，并将通道注册到Selector上以监听可读事件。当有数据到达时，Selector会通知服务端进行处理。这样，服务端可以在一个线程中处理多个客户端连接。
                    另外，文件传输部分也放在了单独的线程中处理，以避免阻塞主线程，确保界面的流畅性。*/
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1);
                            SocketAddress clientSocketAddress = channel.receive(buffer);
                            if (clientSocketAddress != null) {
                                clientAddress = ((InetSocketAddress) clientSocketAddress).getAddress();
                                clientPort = ((InetSocketAddress) clientSocketAddress).getPort();
                                log("Connected to client: " + clientAddress.getHostAddress() + ":" + clientPort);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                log("Error: " + e.getMessage());
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Server server = new Server();
            server.setVisible(true);
        });
    }
}
