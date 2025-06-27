import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NonBlockingFileReceiver {
    private static final int THREAD_POOL_SIZE = 5; // 线程池大小

    public static void main(String[] args) {
        int port = 1222; // 监听端口号
        String savePath = "D:/Desktop/NetworkFiles"; // 文件保存路径

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器已启动，等待连接...");
            while (true) {
                Socket socket = serverSocket.accept(); // 接受连接
                System.out.println("接受到来自 " + socket.getInetAddress() + " 的连接");
                executor.execute(new FileReceiverTask(socket, savePath)); // 在线程池中执行文件接收任务
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("错误：" + e.getMessage());
        } finally {
            executor.shutdown(); // 关闭线程池
        }
    }

    private static class FileReceiverTask implements Runnable {
        private final Socket socket;
        private final String savePath;

        public FileReceiverTask(Socket socket, String savePath) {
            this.socket = socket;
            this.savePath = savePath;
        }

        @Override
        public void run() {
            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {
                String fileName = dis.readUTF();
                long fileSize = dis.readLong();

                File file = new File(savePath, fileName);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] buffer = new byte[4096];
                    long bytesRead;
                    long totalRead = 0;

                    while ((bytesRead = dis.read(buffer)) != -1) {
                        fos.write(buffer, 0, (int) bytesRead);
                        totalRead += bytesRead;

                        if (totalRead == fileSize) break; // 文件接收完成
                    }

                    System.out.println("文件接收完成：" + fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
