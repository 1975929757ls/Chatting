package chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client1 implements Runnable {// 客户端
    static Socket socket = null;
    Scanner input = new Scanner(System.in);
    static String name=null;
    public static void main(String[] args) {
        int x=(int)(Math.random()*100);
        client1.name="client"+x;
        System.out.println("************客户端"+x+"*************");
        try {
            socket = new Socket("127.0.0.1", 9999);
            System.out.println("已经连上服务器了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        client1 t = new client1();
        Read r = new Read(socket);
        Thread print = new Thread(t);
        Thread read = new Thread(r);
        print.start();
        read.start();
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String msg = input.next();
                out.println(name+"说:"+msg);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


