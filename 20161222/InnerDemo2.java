/**
 * 匿名内部类 - 新建带内容的对象
 * 必须注意：
 * 匿名内部类必须是继承一个类或实现接口
 * 匿名内部类方法不要超过3个
 */
abstract class ABS{
	abstract void show();
}
class Outer{
	int x = 3;
	/*
	class Inner extends ABS{
		void show(){
			System.out.println("x : " + x);
		}
	}
	*/
	void show(){
		/*
		new Inner().show();
		*/
	
		//匿名内部类 - 实现的注释部分的功能
		//为什么new ABS()? 去看 多态DEMO ！
		new ABS(){
			// 对象体
			void show(){
				System.out.println("x : " + x);
			}
		}.show();
	}
}
class InnerDemo2 {
	public static void main(String[] args) {
		new Outer().show();
	}
}