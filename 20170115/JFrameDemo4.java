import java.awt.*;
import javax.swing.*;

/**
 * 调整图片大小，载入图片
 */
class JFrameDemo4 extends JFrame{
	JLabel label;
	ImageIcon icon;

	JFrameDemo4(){
		init();
	}
	public void init(){
		icon = new ImageIcon("images/origin.png");
		Image img = icon.getImage().getScaledInstance(341, 192, Image.SCALE_DEFAULT); // getScaledInstance 创建此图像的缩放版本(宽，高，缩放类型标志)
		icon.setImage(img);
		label = new JLabel(icon);
		this.add(label);
		this.setResizable(false);  // 设置不可调整窗口

		this.setTitle("载入图片");
		this.setBounds(200, 200, 341, 192);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new JFrameDemo4();
	}
}