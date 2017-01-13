/**
 * 面向对象有多态性
 * 1、体现
 * 	父类的引用指向子类对象
 * 	父类的引用也可以接收子类对象
 * 2、前提
 * 	类与类之间有关系，要么extends，要么implements
 * 	存在方法重写
 * 3、好处
 * 	提高扩展性，但是只能使用父类的引用访问父类成员
 */
abstract class Animal{
	abstract void eat();
}
class Cat extends Animal{
	void eat(){
		System.out.println("猫吃鱼！");
	}
	void catchMouse(){
		System.out.println("猫抓老鼠！");
	}
}
class DuotaiDemo{
	public static void main(String[] args) {
		goEat(new Cat());  // 猫类是动物，所以新建猫对象可以是Animal类的类型
		

		Animal a = new Cat(); //这个动作叫做类型提升，向上转型
		//a.catchMouse();  错误，父类引用只能访问父类成员
		
		Cat c = (Cat)a;    //这个动作叫向下转型，强制转换父类引用为子类引用
		c.catchMouse();
	}
	public static void goEat(Animal a){
		a.eat();
		// instanceof关键字 判断对象类型  -- 一般不在这用，除非子类少
		if (a instanceof Cat) {
			Cat c = (Cat)a;
			c.catchMouse();
		}
	}
}