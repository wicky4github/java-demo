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
	public static void main(String[] args) {
		Frame f = new Frame("title");  		// 创建框架，默认不可见
		f.setSize(360, 640);		   		// 设置宽高
		f.setLocation(1366/2-360/2, 30);	// 设置left,top
		f.setLayout(new FlowLayout());      // 设置成流式布局

		Button button = new Button("按钮");	// 创建一个按钮
		f.add(button);						// 添加按钮到框架
		
		f.setVisible(true);			   		// 设置框架成可见
	}
}