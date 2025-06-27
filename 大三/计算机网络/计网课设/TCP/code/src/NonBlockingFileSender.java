import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NonBlockingFileSender {
    private static final int THREAD_POOL_SIZE = 5; // 线程池大小

    public static void main(String[] args) {
        String ip = "127.0.0.1"; // 接收方IP地址
        int port = 1222; // 接收方端口号
        String filePath = "D:/Desktop/test.txt"; // 待发送的文件路径

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try {
            File file = new File(filePath);
            long fileSize = file.length();
            String fileName = file.getName();

            for (int i = 0; i < THREAD_POOL_SIZE; i++) {
                long start = i * fileSize / THREAD_POOL_SIZE;
                long size = (i == THREAD_POOL_SIZE - 1) ? (fileSize - start) : fileSize / THREAD_POOL_SIZE;

                executor.execute(new FileSenderTask(ip, port, fileName, filePath, start, size));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误：" + e.getMessage());
        } finally {
            executor.shutdown(); // 关闭线程池
        }
    }

    private static class FileSenderTask implements Runnable {
        private final String ip;
        private final int port;
        private final String fileName;
        private final String filePath;
        private final long start;
        private final long size;

        public FileSenderTask(String ip, int port, String fileName, String filePath, long start, long size) {
            this.ip = ip;
            this.port = port;
            this.fileName = fileName;
            this.filePath = filePath;
            this.start = start;
            this.size = size;
        }

        @Override
        public void run() {
            try (Socket socket = new Socket(ip, port);
                 DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                 RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {

                System.out.println("正在连接到 " + ip + ":" + port);

                dos.writeUTF(fileName);
                dos.writeLong(size);
                dos.writeLong(start);

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
                System.out.println("文件发送完成：" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
