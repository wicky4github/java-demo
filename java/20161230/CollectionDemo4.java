import java.util.*;

/**
 * 泛型：定义集合指定元素类型
 *
 * 不加泛型：编译通过，运行报错
 * 加了泛型：编译失败，程序员来解决，提高安全性
 */
class CollectionDemo4 {
	public static void main(String[] args) {
		ArrayList<String> al1 = new ArrayList<String>();
		al1.add("字符串对象");
		// al1.add(123);  //编译错误

		for (Iterator<String> it = al1.iterator(); it.hasNext(); ) {
			String s = it.next(); //无需强制转换
			System.out.println("元素：" + s);
		}
	}
}