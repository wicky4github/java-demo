import java.awt.*;
import javax.swing.*;

class JFrameDemo extends JFrame{
	JButton north, south, west, east, center;

	JFrameDemo(){
		init();
	}
	public void init(){
		north = new JButton("北");
		south = new JButton("南");
		west = new JButton("西");
		east = new JButton("东");
		center = new JButton("中");
		// 默认布局是边界布局
		this.add(north, BorderLayout.NORTH);
		this.add(south, BorderLayout.SOUTH);
		this.add(west, BorderLayout.WEST);
		this.add(east, BorderLayout.EAST);
		this.add(center, BorderLayout.CENTER);

		this.setTitle("边界式布局");
		this.setBounds(200, 200, 320, 320);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new JFrameDemo();
	}
}