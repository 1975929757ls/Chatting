package chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Print implements Runnable {
    static List<Socket> socketList=new ArrayList<Socket>();
    Scanner input = new Scanner(System.in);
    public Print(Socket s) {// 构造方法
        try {
            socketList.add(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            while (true) {
                String msg = input.next();
                for (int i = 0; i < socketList.size(); i++) {
                    Socket socket=socketList.get(i);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    // System.out.println("对客户端说：");
                    out.println("服务端说："+msg);
                    out.flush();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
