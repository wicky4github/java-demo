import java.awt.*;      // 抽象窗口工具包，跨平台性一般
import java.awt.event.*;
import javax.swing.*;   // 基于awt开发并增强，跨平台性强


/**
 * 继承关系：
 *  			 Component--组件
 *  			⬆            ⬆
 *  		Container容器      | Button
 *  		⬆        ⬆       | Label
 *  	Window窗体   Panel面板  | Checkbox
 *  	⬆      ⬆             | TextComponent
 *  Frame框架   Dialog对话框    |		|-  TextArea  |-  TextField
 */
class GUIDemo{
	private Frame f;
	private Button button;
	GUIDemo(){
		init();
	}
	// 初始化窗体
	public void init(){
		f = new Frame("title");  			// 创建框架，默认不可见
		f.setLocation(1366/2-360/2, 30);	// 设置left,top
		f.setSize(360, 640);		   		// 设置宽高
		f.setLayout(new FlowLayout());      // 设置成流式布局

		button = new Button("退出程序");	// 创建一个按钮
		f.add(button);						// 添加按钮到框架
		
		addEvent();							// 加载事件

		f.setVisible(true);			   		// 设置框架成可见
	}
	// 添加事件与布局分离
	private void addEvent(){
		// f.addWindowListener(new WinAda());  // 添加窗口监听事件，实现关闭窗口
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		// 让按钮也具备退出程序的功能
		// 按钮没Adapter适配器，所以直接新建ActionListener匿名对象
		button.addActionListener(new ActionListener(){
			// 仅有1个所需覆盖方法：actionPerformed
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new GUIDemo();  // 优化代码，实例化自身，构造调用初始化方法
	}
}



// 默认框架监听事件WindowListener
class WinLis implements WindowListener {
	//需要覆盖7个方法，麻烦
	public void windowActivated(WindowEvent e) {
		System.out.println("回到窗口调用");
	}
 	public void windowClosed(WindowEvent e) {
 		System.out.println("关闭后调用");
 	}  
 	public void windowClosing(WindowEvent e) {
 		System.out.println("关闭时调用");
 	}
 	public void windowDeactivated(WindowEvent e) {
 		System.out.println("离开窗口调用");
 	}
 	public void windowDeiconified(WindowEvent e) {
 		System.out.println("恢复正常调用");
 	}
 	public void windowIconified(WindowEvent e) {
 		System.out.println("最小化后调用");
 	}
 	public void windowOpened(WindowEvent e) {
 		System.out.println("首次打开调用");
 	}
}
// 抽象类适配器WindowAdapter实现了WindowListener，其没有抽象方法
class WinAda extends WindowAdapter {
	// 可按需覆盖方法
	public void windowClosing(WindowEvent e) {
 		System.exit(0);
 	}
}