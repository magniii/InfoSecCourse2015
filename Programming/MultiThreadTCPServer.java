package multithreadtcpserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadTCPServer extends Thread {

    Socket s;
    int num;

    public static void main(String[] args) {
        try {
            int connections = 0;

            ServerSocket server = new ServerSocket(3000, 0, InetAddress.getByName("192.168.32.1"));

            System.out.println("server started");
       
            while (true) {
                new MultiThreadTCPServer(connections, server.accept());
                //Thread.sleep(100);
                //System.exit(-3);
                connections++;
            }
        } catch (Exception e) {
        }
    }

    private MultiThreadTCPServer(int num, Socket s) {
        this.num = num;
        this.s = s;

        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run() {
        try {
            System.out.println("client connected");
            
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            byte buf[] = new byte[64 * 1024];
            
            int r = is.read(buf);

            String data = new String(buf, 0, r);
            System.out.println("received: " + data);

            if(data.equals("version")){
                data = "1.2";
            }
            System.out.println("sent: " + data);
            
            os.write(data.getBytes());
            
            //Thread.sleep(10000);
            
            System.out.println("client disconnected");

            s.close();
        } catch (Exception e) {
            System.out.println("init error: " + e);
        }
    }
}
