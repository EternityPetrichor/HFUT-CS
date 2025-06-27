// 服务端 Server.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;

public class Server extends JFrame {
    private JTextArea logArea;
    private JProgressBar progressBar; // 添加进度条组件
    private DatagramSocket socket;//用于与客户端通信的 DatagramSocket 对象
    private InetAddress clientAddress;//保存客户端地址
    private int clientPort;//保存客户端端口号

    public Server() {
        setTitle("UDP File Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        progressBar = new JProgressBar(); // 初始化进度条
        add(progressBar, BorderLayout.SOUTH);

        JButton sendFileButton = new JButton("Send File to Client");//当点击按钮时，会调用 sendFileToClient() 方法。
        sendFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFileToClient();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(sendFileButton);
        add(buttonPanel, BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {//添加窗口关闭事件监听器，用于在窗口关闭时关闭 socket 连接。
            public void windowClosing(WindowEvent e) {
                if (socket != null) {
                    socket.close();
                }
            }
        });

        try {
            socket = new DatagramSocket(1111);//尝试在端口 1111 上创建一个 DatagramSocket 对象，用于接收来自客户端的数据包
        } catch (SocketException e) {
            log("Could not open socket: " + e.getMessage());
        }

        listen(); // Start listening for client requests
    }

    private void sendFileToClient() {
        if (clientAddress == null || clientPort == 0) {
            log("No client connected.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {//创建一个文件选择器，并等待用户选择要发送的文件。
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            long fileSize = selectedFile.length();
            String fileType = getFileExtension(fileName);
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(selectedFile))) {//使用缓冲输入流读取所选文件的内容。
                // Send file info to client
                ByteBuffer infoBuffer = ByteBuffer.allocate(1024);
                infoBuffer.putInt(fileName.length());
                infoBuffer.put(fileName.getBytes());
                infoBuffer.putLong(fileSize);
                infoBuffer.putInt(fileType.length());
                infoBuffer.put(fileType.getBytes());
                byte[] infoBytes = infoBuffer.array();

                DatagramPacket infoPacket = new DatagramPacket(infoBytes, infoBytes.length, clientAddress, clientPort);
                socket.send(infoPacket);
                log("File info sent to client.");

                // Wait for client response
                byte[] responseBuffer = new byte[1];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(responsePacket);
                if (responseBuffer[0] == 0) {
                    log("Client rejected the file transfer.");
                    return;
                }

                // Start sending the file
                int blockSize = 1024; // 设置块大小为1KB
                //long fileSize = selectedFile.length();
                int MAX_BLOCKS_SERVER = (int) Math.ceil((double) fileSize / blockSize);//计算文件的块数，并向上取整以确保所有文件内容都能被发送。

                byte[] blockSizeBytes = ByteBuffer.allocate(4).putInt(MAX_BLOCKS_SERVER).array();

                DatagramPacket blockSizePacket = new DatagramPacket(blockSizeBytes, blockSizeBytes.length, clientAddress, clientPort);
                socket.send(blockSizePacket);

                byte[] fileSizeBytes = ByteBuffer.allocate(8).putLong(fileSize).array(); // 用8字节存储文件大小

                DatagramPacket fileSizePacket = new DatagramPacket(fileSizeBytes, fileSizeBytes.length, clientAddress, clientPort);
                socket.send(fileSizePacket);


                byte[] buffer = new byte[blockSize];
                int bytesRead;
                int sequenceNumber = 0;
                long totalBytesSent = 0;
                /*while ((bytesRead = bis.read(buffer)) != -1) {
                    ByteBuffer packetBuffer = ByteBuffer.allocate(bytesRead + 4);
                    packetBuffer.putInt(sequenceNumber);
                    packetBuffer.put(buffer, 0, bytesRead);

                    byte[] data = packetBuffer.array();

                    CRC32 crc = new CRC32();
                    crc.update(data);
                    long checksum = crc.getValue();
                    ByteBuffer checksumBuffer = ByteBuffer.allocate(8);
                    checksumBuffer.putLong(checksum);
                    byte[] checksumBytes = checksumBuffer.array();

                    byte[] packetBytes = new byte[data.length + checksumBytes.length];
                    System.arraycopy(data, 0, packetBytes, 0, data.length);
                    System.arraycopy(checksumBytes, 0, packetBytes, data.length, checksumBytes.length);
                    DatagramPacket packet = new DatagramPacket(packetBuffer.array(), packetBuffer.array().length, clientAddress, clientPort);

                    boolean ackReceived = false;
                    while (!ackReceived) {
                        socket.send(packet);
                        totalBytesSent += bytesRead;
                        int progress = (int) ((totalBytesSent * 100) / fileSize);
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setValue(progress); // 更新进度条
                        });
                        byte[] ackBuffer = new byte[4];
                        DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length);
                        try {
                            socket.receive(ackPacket);
                            ByteBuffer ackByteBuffer = ByteBuffer.wrap(ackPacket.getData());
                            int ackSequenceNumber = ackByteBuffer.getInt();
                            if (ackSequenceNumber == sequenceNumber) {
                                ackReceived = true;
                            }
                        } catch (SocketTimeoutException e) {
                            log("Timeout occurred, resending packet " + sequenceNumber);
                        }
                    }
                    sequenceNumber++;
                }*/

                while ((bytesRead = bis.read(buffer)) != -1) {
                    ByteBuffer packetBuffer = ByteBuffer.allocate(bytesRead + 4);
                    packetBuffer.putInt(sequenceNumber);
                    packetBuffer.put(buffer, 0, bytesRead);

                    byte[] data = packetBuffer.array();

                    // 计算 CRC 校验码
                    CRC32 crc = new CRC32();
                    crc.update(data);
                    long checksum = crc.getValue();
                    log("Sending packet " + sequenceNumber + " with CRC: " + checksum);
                    ByteBuffer checksumBuffer = ByteBuffer.allocate(8);
                    checksumBuffer.putLong(checksum);
                    byte[] checksumBytes = checksumBuffer.array();

                    // 拼接数据包和 CRC 校验码
                    byte[] packetBytes = new byte[data.length + checksumBytes.length];
                    System.arraycopy(data, 0, packetBytes, 0, data.length);
                    System.arraycopy(checksumBytes, 0, packetBytes, data.length, checksumBytes.length);
                    DatagramPacket packet = new DatagramPacket(packetBytes, packetBytes.length, clientAddress, clientPort);

                    boolean ackReceived = false;
                    while (!ackReceived) {
                        socket.send(packet);
                        totalBytesSent += bytesRead;
                        int progress = (int) ((totalBytesSent * 100) / fileSize);
                        SwingUtilities.invokeLater(() -> {
                            progressBar.setValue(progress); // 更新进度条
                        });
                        byte[] ackBuffer = new byte[4];
                        DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length);
                        try {
                            socket.receive(ackPacket);
                            ByteBuffer ackByteBuffer = ByteBuffer.wrap(ackPacket.getData());
                            int ackSequenceNumber = ackByteBuffer.getInt();
                            if (ackSequenceNumber == sequenceNumber) {
                                ackReceived = true;
                            }
                        } catch (SocketTimeoutException e) {
                            log("Timeout occurred, resending packet " + sequenceNumber);
                        }
                    }
                    sequenceNumber++;
                }



                bis.close();
                log("File sent to client: " + selectedFile.getName());
            } catch (IOException e) {
                log("Error: " + e.getMessage());//捕获可能出现的 IO 异常，并记录日志
            }
        }
    }

    //在日志区域显示消息。由于界面更新必须在事件分派线程中执行，所以使用 SwingUtilities.invokeLater() 来确保在正确的线程中执行。
    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
        });
    }

    private void listen() {//启动一个新线程来监听客户端的连接请求。当有客户端连接时，记录客户端的地址和端口，并显示在日志区域中。
        new Thread(() -> {
            try {
                byte[] buffer = new byte[1];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                clientAddress = packet.getAddress();
                clientPort = packet.getPort();
                log("Connected to client: " + clientAddress.getHostAddress() + ":" + clientPort);
            } catch (IOException e) {
                log("Error: " + e.getMessage());
            }
        }).start();
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {//在事件分派线程中创建Server 对象，并将其设置为可见。
            Server server = new Server();
            server.setVisible(true);
        });
    }
}
