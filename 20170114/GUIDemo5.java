package notepad;
//javac -d . GUIDemo5.java
//touch notepadManifest 任意文件
//enter:
//	Main-Class:空格notepad.GUIDemo5回车
//	Main-Class: 包名.类名        -   固定格式：规定主入口
//jar -cvfm notepad.jar notepadManifest notepad
//open notepad.jar   相当于 javaw -jar notepad.jar

import java.awt.*;
import java.awt.event.*;
import java.io.*;

// FileDialog
class GUIDemo5{
	private Frame frame;
	private MenuBar menubar;
	private Menu menu;
	private MenuItem openItem, saveItem, closeItem;
	private FileDialog opendialog;
	private FileDialog savedialog;
	private TextArea textarea;
	private File file;
	GUIDemo5(){
		init();
	}
	public void init(){
		frame = new Frame("菜单栏");
		frame.setBounds(1366/2-360/2, 30, 360, 300);

		menubar = new MenuBar();
		menu = new Menu("文件");
		openItem = new MenuItem("打开");
		saveItem = new MenuItem("保存");
		closeItem = new MenuItem("退出");
		menu.add(openItem);
		menu.add(saveItem);
		menu.add(closeItem);
		menubar.add(menu);
		frame.setMenuBar(menubar);

		opendialog = new FileDialog(frame, "打开", FileDialog.LOAD);
		savedialog = new FileDialog(frame, "保存", FileDialog.SAVE);

		textarea = new TextArea();
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
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				opendialog.setVisible(true);
				// 通过getDirectory，getFile方法获取选择的文件的路径和文件名
				String dirname = opendialog.getDirectory();
				String filename = opendialog.getFile();
				if (dirname == null || filename == null) {  // 没选择文件会抛出空指针异常
					return;
				}
				textarea.setText("");
				file = new File(dirname, filename);
				BufferedReader br = null;
				try{
				    br = new BufferedReader(new FileReader(file));
				    String line = null;
				    while((line = br.readLine()) != null){
				    	textarea.append(line + "\r\n");
				    }
				}catch(IOException e2){
				    throw new RuntimeException("文件读取失败！");
				}finally{
					if (br != null) {
						try{
						    br.close();
						}catch(IOException e3){
						    throw new RuntimeException("资源关闭失败！");
						}
					}
				}
			}
		});
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (file == null) {  //没有打开文件,弹出保存对话框
					savedialog.setVisible(true);
					String dirname = savedialog.getDirectory();
					String filename = savedialog.getFile();
					if (dirname == null || filename == null) {  // 没选择文件会抛出空指针异常
						return;
					}
					file = new File(dirname, filename);
				}
				// 已打开文件，直接写入该文件
				try{
				    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				    String text = textarea.getText();
				    bw.write(text);
				    bw.flush();
				    bw.close();
				}catch(IOException e1){
				    throw new RuntimeException("保存失败！");
				}
			}
		});
		closeItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}
	public static void main(String[] args) {
		new GUIDemo5();
	}
}