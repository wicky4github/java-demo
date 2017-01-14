import java.awt.*;
import java.awt.event.*;
import java.io.*;

class GUIDemo3{
	private Frame frame;
	private TextField textfiled;
	private Button button;
	private TextArea textarea;

	private Dialog dialog;
	private Label label;
	private Button okBtn;

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

		dialog = new Dialog(frame, "提示信息", true);  // true表示对话框存在，frame不准操作，false则允许操作
		dialog.setBounds(1366/2-360/2, 60, 360, 100);
		dialog.setLayout(new FlowLayout());
		label = new Label();
		okBtn = new Button("确定");
		dialog.add(label);
		dialog.add(okBtn);

		addEvent();

		frame.setVisible(true);
	}
	private void addEvent(){
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		textfiled.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showDir();
				}
			}
		});
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				showDir();
			}
		});
		dialog.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dialog.setVisible(false); // 隐藏对话框
			}
		});
		okBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.setVisible(false); // 隐藏对话框
			}
		});
	}
	private void showDir(){
		textarea.setText("");
		String s = textfiled.getText();  // 获取数据
		File path = new File(s);
		if (path.exists() && path.isDirectory()) {
			String[] dirs = path.list(); // 列出文件
			for (String dir : dirs) {
				textarea.append(dir + "\r\n");
			}
		}else{ // 文件不存在，创建对话框
			label.setText("输入路径：" + s + "有误，请重新输入！");
			dialog.setVisible(true);
		}
	}
	public static void main(String[] args) {
		new GUIDemo3();
	}
}