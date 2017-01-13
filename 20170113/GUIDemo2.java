import java.awt.*;
import java.awt.event.*;

class GUIDemo2{
	private Frame f;
	private Button button;
	GUIDemo2(){
		init();
	}
	public void init(){
		f = new Frame("title");  			// 创建框架，默认不可见
		f.setBounds(1366/2-360/2, 30, 360, 640);	// 设置left,top,width,height
		f.setLayout(new FlowLayout());      // 设置成流式布局

		button = new Button("Button");		// 创建一个按钮
		f.add(button);						// 添加按钮到框架
		
		addEvent();							// 加载事件

		f.setVisible(true);			   		// 设置框架成可见
	}
	// 添加事件与布局分离
	private void addEvent(){
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		// 给按钮添加鼠标事件
		button.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2) {  // 双击
					System.exit(0);
				}
			}
			// mouseClicked 鼠标点击 mouseDragged 鼠标拖动 mouseExited 鼠标离开
			// mouseMoved 鼠标移动 mousePressed 鼠标按下 mouseReleased 鼠标抬起
			// mouseWheelMoved 鼠标滚轮 mouseEntered 鼠标进入
		});
		// 给按钮添加键盘事件
		button.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				System.out.println(KeyEvent.getKeyText(e.getKeyChar()) + "..." + e.getKeyChar() + "..." + e.getKeyCode());
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  // 键盘码是常量，易记
					System.exit(0);
				}
				// e.isAltDown(), isControlDown, isShiftDown
			}
			// keyPressed 键盘按下 keyReleased 键盘抬起 keyTyped  键盘打字
		});
		// 不管是鼠标事件还是键盘事件都有consume方法，表示取消默认事件执行
	}

	public static void main(String[] args) {
		new GUIDemo2();  // 优化代码，实例化自身，构造调用初始化方法
	}
}