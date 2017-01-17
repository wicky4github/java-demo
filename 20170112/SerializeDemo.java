import java.io.*;

/**
 * 操作对象流（对象序列化）
 */
class SerializeDemo{
	public static void main(String[] args) throws Exception
	{
		// writeObj();
		readObj();
	}
	public static void readObj() throws Exception
	{
		ObjectInputStream ois = 
			new ObjectInputStream(new FileInputStream("person.object"));
		Person o = (Person)ois.readObject(); // 会抛出ClassNotFound异常，如果Person类修改编译了则InvalidClass异常，需要重新writeObj
		System.out.println(o);

		ois.close();
	}
	public static void writeObj() throws Exception
	{
		ObjectOutputStream oos = 
			new ObjectOutputStream(new FileOutputStream("person.object"));
		oos.writeObject(new Person("wicky", 22)); // 不实现接口会抛出NotSerialize异常

		oos.close();
	}
}

/**
 * 想序列化就实现Serializable接口（标记接口，无需重写方法）
 * 注意： 静态数据无法序列化，如果有一些非静态数据不需要被序列号就加关键字transient
 */
class Person implements Serializable{
	public static final long serialVersionUID = 123456L; // 自定义UID，则不会出现InvalidClass异常

	private String name;
	private int age;

	private static int a = 1;
	transient int b = 1;

	Person(String name, int age){
		this.name = name;
		this.age = age;
	}
	public String toString(){
		return "name: " + name + " -- age: " + age;
	}
}