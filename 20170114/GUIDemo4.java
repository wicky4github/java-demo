import java.awt.*;
import java.awt.event.*;

// MenuBar - Menu - MenuItem
class GUIDemo4{
	private Frame frame;
	private MenuBar menubar;
	private Menu menu, subMenu;
	private MenuItem closeItem, subItem;
	GUIDemo4(){
		init();
	}
	public void init(){
		frame = new Frame("菜单栏");
		frame.setBounds(1366/2-360/2, 30, 360, 300);
		frame.setLayout(new FlowLayout());

		menubar = new MenuBar();
		menu = new Menu("文件");
		closeItem = new MenuItem("退出");

		subMenu = new Menu("子菜单");
		subItem = new MenuItem("打开最近文件");

		menu.add(subMenu);
		subMenu.add(subItem);
		menu.add(closeItem);

		menubar.add(menu);
		frame.setMenuBar(menubar);

		addEvent();

		frame.setVisible(true);
	}
	private void addEvent(){
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		closeItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}
	public static void main(String[] args) {
		new GUIDemo4();
	}
}