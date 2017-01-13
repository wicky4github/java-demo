class Outer {
	private int x = 1;
	//内部类，可访问外部类私有成员
	class Inner{
		void showX(){
			// x 全写 Outer.this.x  -> 指定this的类
			System.out.println("Outer x : " + x);
		}
	}
	void showX(){
		Inner in = new Inner();
		in.showX();
	}

	//内部类可以被private修饰
	private class Inner2{}

	//静态内部类
	private static int y = 1;
	static class Inner3{
		//内部类如果有静态成员，则内部类必须是static
		void showY1(){
			System.out.println("Outer y : " + y);
		}
		static void showY2(){
			System.out.println("Outer y : " + y);
		}
	}

	void showZ(){
		//局部类想访问局部变量，则必须 final 局部变量
		final int z = 4;
		//局部类，不是成员，不能被static修饰，所以不能访问静态变量
		class Inner4{
			void showZ(){
				System.out.println("我要访问z：" + z);
			}
		}
		Inner4 inner4 = new Inner4();  //不准写在class Inner4定义前
		inner4.showZ();
	}
}
class InnerDemo {
	public static void main(String[] args) {
		Outer out = new Outer();
		out.showX();

		//直接访问内部类成员
		new Outer().new Inner().showX();

		//直接访问静态内部类成员 访问非静态要new
		new Outer.Inner3().showY1();

		//直接访问静态内部类静态方法，访问静态无需new
		Outer.Inner3.showY2();
	}
}