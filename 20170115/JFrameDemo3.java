import java.awt.*;
import javax.swing.*;

/**
 * 面板的使用
 */
class JFrameDemo3 extends JFrame{
	JButton btn1, btn2, btn3, btn4, btn5;
	JPanel p1, p2;

	JFrameDemo3(){
		init();
	}
	public void init(){
		btn1 = new JButton("按钮1");
		btn2 = new JButton("按钮2");
		btn3 = new JButton("按钮3");
		btn4 = new JButton("按钮4");
		btn5 = new JButton("按钮5");

		p1 = new JPanel();
		p2 = new JPanel();
		p1.setLayout(new FlowLayout());
		p2.setLayout(new GridLayout(1, 2));
		
		p1.add(btn1); p1.add(btn2);
		p2.add(btn3); p2.add(btn4);

		this.add(p1, BorderLayout.NORTH);
		this.add(btn5, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);

		this.setTitle("Panel的使用");
		this.setBounds(200, 200, 320, 320);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new JFrameDemo3();
	}
}