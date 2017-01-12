/**
 * 集合框架（相当于可存储任意类型对象的容器，存储的是引用，不是对象本体）
 */
import java.util.*;

class CollectionDemo {
	public static void main(String[] args) {
		//创建集合容器，使用Collection接口的子类（查手册）
		ArrayList al1 = new ArrayList();
		
		//添加元素
		al1.add("字符串对象");
		al1.add(123);
		al1.add(true); 

		//打印集合
		System.out.println("al1" + al1);

		//删除元素(变量、数字)  clear可清空
		al1.remove("字符串对象");
		System.out.println("al1" + al1);

		//获取个数
		System.out.println("集合框架元素长度：" + al1.size());

		//判断
		System.out.println("存在123对象：" + al1.contains(123));

		ArrayList al2 = new ArrayList();
		al2.add("字符串对象");
		al2.add(123);

		//取交集
		al2.retainAll(al1);
		System.out.println("al2:" + al2);

		//取交集的剩余部分
		al1.removeAll(al2);
		System.out.println("al1" + al1);
	}
}