class Animal{
	int age = 100;
	void eat(){
		System.out.println("吃东西！");
	}
}
class Cat extends Animal{
	int age = 1;
	void eat(){
		System.out.println("吃鱼！");
	}
}
class DuotaiDemo2{
	public static void main(String[] args) {
		Animal a = new Cat();
		/**
		 * 多态的成员函数特点
		 * 编译看左边，运行看右边
		 * 左边是animal且有eat方法  编译✅
		 * 右边是cat有eat方法       运行✅ 
		 */
		a.eat();
		/**
		 * 多态的成员变量，静态成员函数特点
		 * 编译运行一律看左边
		 */
		System.out.println(a.age); //100
	}
}