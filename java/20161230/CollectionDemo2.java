import java.util.*;
/**
 * 通过迭代器获取元素
 */
class CollectionDemo2 {
	public static void main(String[] args) {
		ArrayList al1 = new ArrayList();
		al1.add("字符串对象");
		al1.add(123);
		al1.add(true);

		// Iterator it = al1.iterator(); //通过Collection接口子类对象创建Iterator接口子类对象 
		// while(it.hasNext()){
		// 	System.out.println("元素：" + it.next());  //通过迭代器获取元素
		// }
		for (Iterator it = al1.iterator(); it.hasNext(); ) {
			System.out.println("元素：" + it.next());  //老外写法  it.next()类型是Object
		}
	}
}