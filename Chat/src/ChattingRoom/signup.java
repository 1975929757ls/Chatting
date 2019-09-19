package ChattingRoom;

/*创建一张talk数据表
 * 表结构为usename和password
 * */
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class signup extends JFrame{
    private JTextField usename = new JTextField();//文本输入框
    private JTextField password  = new JTextField();
    private JButton signup = new JButton("注册");//按钮
    private JButton signin = new JButton("登陆");

    //数据库信息
    private String user = "root";
    private String pwd = "1234";
    private String url = "jdbc:mysql://localhost:3306/chat?";

    private boolean tag = false;//判断账号密码是否正确
    public signup() {
        // TODO Auto-generated constructor stub
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        this.add(usename);
        this.add(password);
        this.add(signin);
        this.add(signup);

        usename.setSize(100,30);
        password.setSize(100,30);
        signin.setSize(100,50);
        signup.setSize(100,50);

        usename.setLocation(100,100);
        password.setLocation(100,200);
        signin.setLocation(50,300);
        signup.setLocation(150,300);


        try {
            Class.forName("com.mysql.jdbc.Driver");//开启数据库
        } catch (ClassNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        signin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String useName = usename.getText().toString();
                String passWord = password.getText().toString();
                if(useName.isEmpty()||passWord.isEmpty()){
                    JOptionPane.showMessageDialog(null, "请输入用户名或密码", "error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    try {
                        Connection con = (Connection) DriverManager.getConnection(url, user, pwd);//数据库连
                        Statement stmt = (Statement) con.createStatement();//创建语句对象
                        String sql = "select * from talk";//数据库语句
                        ResultSet rs = (ResultSet) stmt.executeQuery(sql);//执行语句得到结果,以行的角度表现查询结果
                        java.sql.ResultSetMetaData rsmd = rs.getMetaData();//结果以列的形式展现
                        while(rs.next()){//按行逐个读取查询的内容,next()表示行的移动
                            if(rs.getString(1).equals(useName)&&rs.getString(2).equals(passWord)){
                                tag = true;
                                new SocketFrame().setVisible(true);//跳转到主界面
                                exits();//关闭当前界面
                                return;
                            }
                        }
                        if(tag==false){
                            JOptionPane.showMessageDialog(null, "账号密码错误！", "error",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        signup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String useName = usename.getText().toString();
                String passWord = password.getText().toString();
                if(useName.isEmpty()||passWord.isEmpty()){
                    JOptionPane.showMessageDialog(null, "请输入用户名或密码", "error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    Connection con;
                    try {
                        con = (Connection) DriverManager.getConnection(url, user, pwd);
                        Statement stmt = (Statement) con.createStatement();
                        String sql = "insert talk value('"+useName+"','"+passWord+"');";
                        stmt.executeUpdate(sql);
                        JOptionPane.showMessageDialog(null,"注册成功", "done",JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void exits() {
        this.setVisible(false);
    }


    public static void main(String[] args) {
        new signup().setVisible(true);
    }
}

