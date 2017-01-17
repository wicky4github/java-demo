import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class JFrameDemo5 extends JFrame implements ActionListener{
    Button b1, b2;
    JFrameDemo5(){
        init();
    }
    public void init(){
        this.setBounds(10, 10, 100, 80);
        b1 = new Button("按钮1");
        b2 = new Button("按钮2");
        this.add(b1);
        this.add(b2);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addEvent();
        this.setVisible(true);
    }
    public void addEvent(){
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("按钮1被点击--匿名对象");
            }
        });
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("按钮2被点击--匿名对象");
            }
        });
        b1.addActionListener(this); // 添加监听事件，把本对象传进去，调用本对象的actionPerformed方法
        b2.addActionListener(this); // 本对象必须实现ActionListener接口
        b1.setActionCommand("btn1");
        b2.setActionCommand("btn2");
    }
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("btn1")) {
            System.out.println("按钮1被点击--JFrameDemo5对象");
        } else if (e.getActionCommand().equals("btn2")) {
            System.out.println("按钮2被点击--JFrameDemo5对象");
        } else {
            System.out.println("未知");
        }
    }
    public static void main(String[] args) {
        new JFrameDemo5();
    }
}