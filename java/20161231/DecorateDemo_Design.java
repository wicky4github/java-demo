/**
 * 装饰设计模式
 * 想增强Person的eat功能，改代码是错误行为！
 * 可以定义个类，将需要增强的对象传入，并提供加强功能，这个类就叫装饰类
 *
 * 继承模式（每个具体Reader对应一个具体BufferedReader）：
 * Reader
 * |- FileReader
 * |	|- BufferedFileReader
 * |- MediaReader
 * |	|- BufferedMediaReader
 * |- ...
 * |	|- ...
 * 
 * 装饰模式（BufferedReader直接继承Reader）
 * Reader
 * |- FileReader
 * |- MediaReader
 * |- ...
 * |- BufferedReader
 * 
 * 装饰（组合）模式比继承灵活，避免继承体系的臃肿
 * class BufferedReader extends Reader{
 * 		BufferedReader(Reader r){}
 * 		... 重写功能，增强功能
 * }
 */
import java.io.*;
class Person{
	public void eat(){ // 基本功能
		System.out.println("吃饭");
	}
}
class SuperPeron{
	private Person p;
	SuperPeron(Person p){
		this.p = p;
	}
	public void superEat(){ // 增强功能
		System.out.println("开胃菜");
		p.eat();
		System.out.println("吃甜点");
	}
}
class DecorateDemo_Design{
	public static void main(String[] args) throws IOException
	{
		System.out.println("------------------------");

		Person p = new Person();
		SuperPeron sp = new SuperPeron(p);
		sp.superEat();

		System.out.println("------------------------");

		FileReader fr = new FileReader("io/demo.txt");
		LineNumberReader lnr = new LineNumberReader(fr);  // LineNumberReader是BufferedReader的再增强子类，可获取行号
		String line = null;
		while((line = lnr.readLine()) != null){
			System.out.println(lnr.getLineNumber() + "  " + line);
		}

		System.out.println("------------------------");

	}
}