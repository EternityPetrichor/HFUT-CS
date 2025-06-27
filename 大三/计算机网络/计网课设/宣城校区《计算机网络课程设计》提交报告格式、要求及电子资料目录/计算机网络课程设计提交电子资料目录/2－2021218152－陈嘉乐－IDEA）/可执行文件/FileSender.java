import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class FileSender {
    private JFrame frame;
    private JTextField ipField;
    private JTextField portField;
    private JTextField threadCountField;
    private JTextField filePathField;
    private static JLabel statusLabel;

    public static void main(String[] args) {
        // 使用事件调度线程来创建和显示应用程序的GUI
        EventQueue.invokeLater(() -> {
            try {
                FileSender window = new FileSender();
                window.frame.setVisible(true); // 显示窗口
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 构造方法，初始化GUI组件
    public FileSender() {
        initialize();
    }

    // 初始化GUI组件
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("File Sender"); // 设置窗口标题
        frame.setBounds(100, 100, 450, 300); // 设置窗口大小和位置
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作
        frame.getContentPane().setLayout(null); // 使用绝对布局

        // 创建并添加IP标签和文本框
        JLabel lblIp = new JLabel("Receiver IP:");
        lblIp.setBounds(10, 10, 80, 25);
        frame.getContentPane().add(lblIp);

        ipField = new JTextField();
        ipField.setBounds(100, 10, 200, 25);
        frame.getContentPane().add(ipField);
        ipField.setColumns(10);

        // 创建并添加端口标签和文本框
        JLabel lblPort = new JLabel("Port:");
        lblPort.setBounds(10, 45, 80, 25);
        frame.getContentPane().add(lblPort);

        portField = new JTextField();
        portField.setBounds(100, 45, 200, 25);
        frame.getContentPane().add(portField);
        portField.setColumns(10);

        // 创建并添加线程数标签和文本框
        JLabel lblThreadCount = new JLabel("Threads:");
        lblThreadCount.setBounds(10, 80, 80, 25);
        frame.getContentPane().add(lblThreadCount);

        threadCountField = new JTextField();
        threadCountField.setBounds(100, 80, 200, 25);
        frame.getContentPane().add(threadCountField);
        threadCountField.setColumns(10);

        // 创建并添加文件路径标签和文本框
        JLabel lblFile = new JLabel("File:");
        lblFile.setBounds(10, 115, 80, 25);
        frame.getContentPane().add(lblFile);

        filePathField = new JTextField();
        filePathField.setBounds(100, 115, 200, 25);
        frame.getContentPane().add(filePathField);
        filePathField.setColumns(10);

        // 创建并添加浏览按钮
        JButton btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(310, 115, 100, 25);
        btnBrowse.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getPath()); // 设置选择的文件路径
            }
        });
        frame.getContentPane().add(btnBrowse);

        // 创建并添加发送按钮
        JButton btnSend = new JButton("Send");
        btnSend.setBounds(100, 150, 100, 25);
        btnSend.addActionListener(e -> {
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());
            int threadCount = Integer.parseInt(threadCountField.getText());
            String filePath = filePathField.getText();

            try {
                sendFile(ip, port, filePath, threadCount); // 调用发送文件方法
            } catch (IOException ioException) {
                ioException.printStackTrace();
                statusLabel.setText("Error: " + ioException.getMessage()); // 显示错误信息
            }
        });
        frame.getContentPane().add(btnSend);

        // 创建并添加状态标签
        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 185, 400, 25);
        frame.getContentPane().add(statusLabel);
    }

    // 发送文件方法，使用多线程将文件分块发送
    protected static void sendFile(String ip, int port, String filePath, int threadCount) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        String fileName = file.getName();
        long chunkSize = fileSize / threadCount; // 计算每个线程发送的块大小
        CountDownLatch latch = new CountDownLatch(threadCount); // 使用CountDownLatch同步线程

        // 为每个线程创建并启动FileSenderTask
        for (int i = 0; i < threadCount; i++) {
            long start = i * chunkSize;
            long size = (i == threadCount - 1) ? (fileSize - start) : chunkSize;
            new Thread(new FileSenderTask(ip, port, filePath, start, size, latch, fileName, fileSize, i)).start();
        }

        try {
            latch.await(); // 等待所有线程完成
            System.out.println("文件发送成功！");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("错误：" + e.getMessage());
        }
    }

    // 文件发送任务类，实现Runnable接口
    private static class FileSenderTask implements Runnable {
        private final String ip;
        private final int port;
        private final String filePath;
        private final long start;
        private final long size;
        private final CountDownLatch latch;
        private final String fileName;
        private final long fileSize;
        private final int threadId;

        public FileSenderTask(String ip, int port, String filePath, long start, long size, CountDownLatch latch, String fileName, long fileSize, int threadId) {
            this.ip = ip;
            this.port = port;
            this.filePath = filePath;
            this.start = start;
            this.size = size;
            this.latch = latch;
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try (Socket socket = new Socket(ip, port);//通过指定接收方的IP地址和端口号创建一个TCP连接。这是一个阻塞调用，直到成功建立连接。
                 //使用DataOutputStream包装BufferedOutputStream来发送数据。BufferedOutputStream用于提高IO性能，DataOutputStream提供了写入基本数据类型的方法。
                 DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                 RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {

                System.out.println("正在连接到 " + ip + ":" + port);

                // 发送文件名、文件大小、线程ID、文件块的起始位置和大小等信息。flush方法确保缓冲区中的数据被发送出去。
                dos.writeUTF(fileName);
                dos.writeLong(fileSize);
                dos.writeInt(threadId);
                dos.writeLong(start);
                dos.writeLong(size);
                dos.flush();

                // 读取并发送文件块
                //读取文件的指定块并通过TCP连接发送。使用RandomAccessFile可以从文件的任意位置开始读取。每次读取最多4096字节的数据，直到读取完指定的块。
                raf.seek(start);
                byte[] buffer = new byte[4096];
                long bytesRemaining = size;
                while (bytesRemaining > 0) {
                    int bytesRead = raf.read(buffer, 0, (int) Math.min(buffer.length, bytesRemaining));
                    if (bytesRead == -1) break;
                    dos.write(buffer, 0, bytesRead);
                    bytesRemaining -= bytesRead;
                }
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // 减少CountDownLatch的计数,以便主线程可以等待所有文件块发送完成。
            }
            //读取文件数据和写入网络数据的操作都是阻塞的。raf.read方法会阻塞当前线程，直到读取到指定数量的数据或到达文件末尾。dos.write和dos.flush方法会阻塞当前线程，直到数据被写入和发送。
        }
    }
}
