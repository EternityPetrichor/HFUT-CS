// 客户端 Client.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;

public class Client extends JFrame {
    private JTextArea logArea;
    private JProgressBar progressBar; // 添加进度条组件
    private DatagramSocket socket;
    private InetAddress serverAddress;//保存服务器的地址。
    private int serverPort;//保存服务器的端口号。

    public Client() {
        setTitle("UDP File Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        progressBar = new JProgressBar(); // 初始化进度条
        add(progressBar, BorderLayout.SOUTH);

        JButton receiveButton = new JButton("Receive File");//创建一个按钮 receiveButton，用于触发接收文件的操作。当点击按钮时，会调用 receiveFile() 方法。
        receiveButton.addActionListener(e -> receiveFile());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(receiveButton);
        add(buttonPanel, BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeSocket();
            }
        });

        try {
            socket = new DatagramSocket();//创建一个 DatagramSocket 对象，用于接收来自服务器的数据包。
        } catch (SocketException e) {
            log("Could not open socket: " + e.getMessage());
        }

        sendRequest();
    }

    private void closeSocket() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    private void receiveFile() {
        new Thread(() -> {
            try {
                // Receive file info
                byte[] infoBuffer = new byte[1036];
                DatagramPacket infoPacket = new DatagramPacket(infoBuffer, infoBuffer.length);
                socket.receive(infoPacket);

                ByteBuffer infoByteBuffer = ByteBuffer.wrap(infoPacket.getData());
                int fileNameLength = infoByteBuffer.getInt();
                byte[] fileNameBytes = new byte[fileNameLength];
                infoByteBuffer.get(fileNameBytes);
                String fileName = new String(fileNameBytes);

                long fileSize = infoByteBuffer.getLong();

                int fileTypeLength = infoByteBuffer.getInt();
                byte[] fileTypeBytes = new byte[fileTypeLength];
                infoByteBuffer.get(fileTypeBytes);
                String fileType = new String(fileTypeBytes);

                log("Received file info: Name=" + fileName + ", Size=" + fileSize + ", Type=" + fileType);

                int response = JOptionPane.showConfirmDialog(this,
                        "Do you want to receive this file?\nName: " + fileName + "\nSize: " + fileSize + " bytes\nType: " + fileType,
                        "File Transfer", JOptionPane.YES_NO_OPTION);

                byte[] responseBytes = new byte[1];
                responseBytes[0] = (byte) (response == JOptionPane.YES_OPTION ? 1 : 0);
                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, serverAddress, serverPort);
                socket.send(responsePacket);

                if (response == JOptionPane.NO_OPTION) {
                    log("File transfer rejected.");
                    return;
                }


                //接收服务器端发送过来的文件块数量信息，并解析得到文件的总块数。
                byte[] blockSizeBytes = new byte[4];
                DatagramPacket blockSizePacket = new DatagramPacket(blockSizeBytes, blockSizeBytes.length);
                socket.receive(blockSizePacket);
                int MAX_BLOCKS = ByteBuffer.wrap(blockSizePacket.getData()).getInt();

                byte[] fileSizeBytes = new byte[8]; // 文件大小信息为8字节
                DatagramPacket fileSizePacket = new DatagramPacket(fileSizeBytes, fileSizeBytes.length);
                socket.receive(fileSizePacket);
                long totalFileSize = ByteBuffer.wrap(fileSizePacket.getData()).getLong(); // 解析文件大小信息


                byte[][] receivedBlocks = new byte[MAX_BLOCKS][];
                boolean[] receivedBlocksStatus = new boolean[MAX_BLOCKS];

                int expectedSequenceNumber = 0;
                long totalBytesReceived = 0;

                /*while (true) {//循环接收文件的数据包，直到接收完整个文件。
                    DatagramPacket packet = new DatagramPacket(new byte[1028], 1028);
                    socket.receive(packet);
                    ByteBuffer receivedPacketBuffer = ByteBuffer.wrap(packet.getData());
                    int sequenceNumber = receivedPacketBuffer.getInt();//接收一个数据包，并从中解析出序列号

                    if (sequenceNumber == expectedSequenceNumber) {//如果收到的数据包的序列号与期望的序列号相等，说明收到了正确的数据块。
                        //将收到的数据块保存到 receivedBlocks 数组中，并将对应的状态标记为已接收。然后，增加期望的序列号，准备接收下一个数据块
                        byte[] data = Arrays.copyOfRange(packet.getData(), 4, packet.getLength());
                        receivedBlocks[sequenceNumber] = data;
                        receivedBlocksStatus[sequenceNumber] = true;
                        expectedSequenceNumber++;

                        //发送一个确认包给服务器，通知服务器已成功接收该数据块
                        DatagramPacket ackPacket = new DatagramPacket(receivedPacketBuffer.array(), 4, packet.getAddress(), packet.getPort());
                        socket.send(ackPacket);

                        totalBytesReceived += data.length;
                        int progress = (int) ((totalBytesReceived * 100) / totalFileSize);
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setValue(progress); // 更新进度条
                        });
                    } else {//如果收到的数据包的序列号与期望的序列号不相等，说明数据包丢失或顺序错乱
                        //发送一个请求包给服务器，要求重新发送缺失的数据块
                        ByteBuffer requestBuffer = ByteBuffer.allocate(4);
                        requestBuffer.putInt(expectedSequenceNumber);
                        DatagramPacket requestPacket = new DatagramPacket(requestBuffer.array(), requestBuffer.array().length, packet.getAddress(), packet.getPort());
                        socket.send(requestPacket);
                    }


                    //检查是否所有的数据块都已经接收完成
                    boolean allReceived = true;
                    for (boolean status : receivedBlocksStatus) {
                        if (!status) {
                            allReceived = false;
                            break;
                        }
                    }

                    //如果所有数据块都已接收完成，则退出循环
                    if (allReceived) {
                        break;
                    }
                }*/


                while (true) {
                    DatagramPacket packet = new DatagramPacket(new byte[1036], 1036);
                    socket.receive(packet);
                    ByteBuffer receivedPacketBuffer = ByteBuffer.wrap(packet.getData());
                    int sequenceNumber = receivedPacketBuffer.getInt();

                    /*// 提取数据和 CRC 校验码
                    byte[] data = new byte[packet.getLength() - 12]; // -12 以考虑序列号 (4 字节) 和校验码 (8 字节)
                    receivedPacketBuffer.get(data);

                    long receivedChecksum = receivedPacketBuffer.getLong();
                    CRC32 crc = new CRC32();
                    crc.update(data);
                    long calculatedChecksum = crc.getValue();*/

                    // 创建一个包含序列号和数据部分的数组
                    byte[] dataWithSequenceNumber = new byte[packet.getLength() - 8]; // -8 以考虑校验码 (8 字节)
                    receivedPacketBuffer.position(0); // 重置缓冲区的位置，以确保从头开始读取
                    receivedPacketBuffer.get(dataWithSequenceNumber, 0, dataWithSequenceNumber.length); // 读取序列号和数据部分

                    // 从完整数据包中提取校验码
                    receivedPacketBuffer.position(dataWithSequenceNumber.length); // 将位置移动到校验码开始的地方
                    long receivedChecksum = receivedPacketBuffer.getLong();

                    // 计算数据部分的 CRC 校验码（不包括校验码本身）
                    CRC32 crc = new CRC32();
                    crc.update(dataWithSequenceNumber);
                    long calculatedChecksum = crc.getValue();

                    if (receivedChecksum == calculatedChecksum) { // 数据包校验和正确，处理数据包
                        if (sequenceNumber == expectedSequenceNumber) {
                            //对数据再加工，去掉前面的序列号
                            byte[] data = new byte[dataWithSequenceNumber.length - 4]; // -4 去掉序列号部分
                            System.arraycopy(dataWithSequenceNumber, 4, data, 0, data.length);

                            receivedBlocks[sequenceNumber] = data;
                            receivedBlocksStatus[sequenceNumber] = true;
                            expectedSequenceNumber++;

                            DatagramPacket ackPacket = new DatagramPacket(ByteBuffer.allocate(4).putInt(sequenceNumber).array(), 4, packet.getAddress(), packet.getPort());
                            socket.send(ackPacket);

                            totalBytesReceived += data.length;
                            int progress = (int) ((totalBytesReceived * 100) / totalFileSize);
                            SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
                        } else {
                            ByteBuffer requestBuffer = ByteBuffer.allocate(4);
                            requestBuffer.putInt(expectedSequenceNumber);
                            DatagramPacket requestPacket = new DatagramPacket(requestBuffer.array(), requestBuffer.array().length, packet.getAddress(), packet.getPort());
                            socket.send(requestPacket);
                        }

                        boolean allReceived = true;
                        for (boolean status : receivedBlocksStatus) {
                            if (!status) {
                                allReceived = false;
                                break;
                            }
                        }

                        if (allReceived) {
                            break;
                        }
                    } else { // 数据包校验和错误，请求重新发送数据包
                        log("Checksum mismatch for packet " + sequenceNumber + ", requesting retransmission.");
                        ByteBuffer requestBuffer = ByteBuffer.allocate(4);
                        requestBuffer.putInt(sequenceNumber);
                        DatagramPacket requestPacket = new DatagramPacket(requestBuffer.array(), requestBuffer.array().length, packet.getAddress(), packet.getPort());
                        socket.send(requestPacket);
                    }
                }


                //将接收到的所有数据块组合成文件，并保存到本地文件系统中
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
            } catch (IOException e) {
                log("Error: " + e.getMessage());
            }
        }).start();
    }

    private File chooseOutputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    private void sendRequest() {
        try {
            byte[] buffer = new byte[1];
            serverAddress = InetAddress.getLocalHost();
            serverPort = 1111;
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            log("Request sent to server.");
        } catch (IOException e) {
            log("Error sending request: " + e.getMessage());
        }
    }

    private void log(String message) {
        //由于界面更新必须在事件分派线程中执行，所以使用 SwingUtilities.invokeLater() 来确保在正确的线程中执行
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {//在事件分派线程中创建了 Client 对象，并将其设置为可见
            Client client = new Client();
            client.setVisible(true);
        });
    }
}
