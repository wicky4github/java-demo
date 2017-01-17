import java.awt.*;
import javax.swing.*;

class JFrameDemo2 extends JFrame{
	JButton btn1, btn2, btn3, btn4, btn5;

	JFrameDemo2(){
		init();
	}
	public void init(){
		btn1 = new JButton("按钮1");
		btn2 = new JButton("按钮2");
		btn3 = new JButton("按钮3");
		btn4 = new JButton("按钮4");
		btn5 = new JButton("按钮5");
		this.setTitle("网格布局");
		this.setLayout(new GridLayout(3,2));   // GridLayout(rows, cols, 水平间距, 垂直间距）
		this.setBounds(200, 200, 320, 320);
		this.add(btn1);
		this.add(btn2);
		this.add(btn3);
		this.add(btn4);
		this.add(btn5);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new JFrameDemo2();
	}
}