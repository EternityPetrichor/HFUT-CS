import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class FileReceiver {
    private JFrame frame;
    private JTextField portField;
    private JTextField threadCountField;
    private JTextField savePathField;
    private JLabel statusLabel;

    public static void main(String[] args) {
        // 确保创建用户界面的操作在事件调度线程上执行
        EventQueue.invokeLater(() -> {
            try {
                FileReceiver window = new FileReceiver();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FileReceiver() {
        initialize(); // 初始化用户界面
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("File Receiver");
        frame.setBounds(100, 100, 450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 标签和文本框用于输入端口号
        JLabel lblPort = new JLabel("Port:");
        lblPort.setBounds(10, 10, 80, 25);
        frame.getContentPane().add(lblPort);

        portField = new JTextField();
        portField.setBounds(100, 10, 200, 25);
        frame.getContentPane().add(portField);
        portField.setColumns(10);

        // 标签和文本框用于输入线程数
        JLabel lblThreadCount = new JLabel("Threads:");
        lblThreadCount.setBounds(10, 45,80, 25);
        frame.getContentPane().add(lblThreadCount);

        threadCountField = new JTextField();
        threadCountField.setBounds(100, 45, 200, 25);
        frame.getContentPane().add(threadCountField);
        threadCountField.setColumns(10);

        // 标签和文本框用于输入保存路径
        JLabel lblSavePath = new JLabel("Save Path:");
        lblSavePath.setBounds(10, 80, 80, 25);
        frame.getContentPane().add(lblSavePath);

        savePathField = new JTextField();
        savePathField.setBounds(100, 80, 200, 25);
        frame.getContentPane().add(savePathField);
        savePathField.setColumns(10);

        // 浏览按钮，用于选择保存路径
        JButton btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(310, 80, 100, 25);
        btnBrowse.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                savePathField.setText(fileChooser.getSelectedFile().getPath());
            }
        });
        frame.getContentPane().add(btnBrowse);

        // 接收按钮，用于启动文件接收
        JButton btnReceive = new JButton("Receive");
        btnReceive.setBounds(100, 115, 100, 25);
        btnReceive.addActionListener(e -> {
            // 从文本框获取端口号、线程数和保存路径
            int port = Integer.parseInt(portField.getText());
            int threadCount = Integer.parseInt(threadCountField.getText());
            String savePath = savePathField.getText();

            // 调用文件接收方法
            receiveFile(port, savePath, threadCount);
        });
        frame.getContentPane().add(btnReceive);

        // 用于显示状态信息的标签
        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 150, 400, 25);
        frame.getContentPane().add(statusLabel);
    }

    // 文件接收方法
    protected static void receiveFile(int port, String savePath, int threadCount) {
        CountDownLatch latch = new CountDownLatch(threadCount);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器已启动，等待连接...");

            for (int i = 0; i < threadCount; i++) {
                Socket socket = serverSocket.accept();
                System.out.println("接受到来自 " + socket.getInetAddress() + " 的连接");

                DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                // 从输入流中读取文件信息
                String fileName = dis.readUTF();
                long fileSize = dis.readLong();
                int threadId = dis.readInt();
                long start = dis.readLong();
                long size = dis.readLong();

                // 创建文件对象和随机访问文件对象，并设置文件大小
                File file = new File(savePath, fileName);
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.setLength(fileSize);

                // 创建文件接收任务并启动线程
                new Thread(new FileReceiverTask(dis, raf, start, size, threadId, latch)).start();
            }

            latch.await(); // 等待所有接收线程完成
            System.out.println("文件接收完成！");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("错误：" + e.getMessage());
        }
    }

    // 文件接收任务
    private static class FileReceiverTask implements Runnable {
        private final DataInputStream dis;
        private final RandomAccessFile raf;
        private final long start;
        private final long size;
        private final int threadId;
        private final CountDownLatch latch;

        public FileReceiverTask(DataInputStream dis, RandomAccessFile raf, long start, long size, int threadId, CountDownLatch latch) {
            this.dis = dis;
            this.raf = raf;
            this.start = start;
            this.size = size;
            this.threadId = threadId;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[4096];
                long bytesRemaining = size;
                raf.seek(start);
                while (bytesRemaining > 0) {
                    int bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, bytesRemaining));
                    if (bytesRead == -1) break;
                    raf.write(buffer, 0, bytesRead);
                    bytesRemaining -= bytesRead;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    dis.close();
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                latch.countDown(); // 任务完成，减少计数
            }
        }
    }
}
