import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class GUIDemo3{
	private Frame frame;
	private TextField textfiled;
	private Button button;
	private TextArea textarea;
	GUIDemo3(){
		init();
	}
	public void init(){
		frame = new Frame("模拟资源管理器");
		frame.setBounds(1366/2-360/2, 30, 360, 640);
		frame.setLayout(new FlowLayout());

		textfiled = new TextField(30);  		// cols
		button = new Button("转到");
		textarea = new TextArea(35, 40);        // rows,cols

		frame.add(textfiled);
		frame.add(button);
		frame.add(textarea);

		addEvent();

		frame.setVisible(true);
	}
	private void addEvent(){
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				textarea.setText("");
				String s = textfiled.getText();  // 获取数据
				File path = new File(s);
				if (path.exists() && path.isDirectory()) {
					String[] dirs = path.list(); // 列出文件
					for (String dir : dirs) {
						textarea.append(dir + "\r\n");
					}
				}
			}
		});
	}
	public static void main(String[] args) {
		new GUIDemo3();
	}
}