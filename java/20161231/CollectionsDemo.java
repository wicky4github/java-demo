import java.util.*;

/**
 * java.util.Collections  - 不是Collection接口，是Collections类
 * 
 * Collections可以对List类型，进行sort,max,min,binarySearch，replace[All],swap,shuffle等操作
 * 
 * Collections.sort(List l, <T extends Comparator<? super T>>)
 * 
 * Collections.reverseOrder(Comparator<T> cmp)
 *
 * Collections.sychronized[List/Set/Map](Collection c)
 */
class CollectionsDemo {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();

		list.add("asqd");
		list.add("azsde");
		list.add("afsdf");
		list.add("asgd");

		System.out.println("排序前：" + list);

		Collections.sort(list);    //Collections静态方法sort排序

		System.out.println("排序后：" + list);

		System.out.println("----------------------");

		TreeSet<String> tree = new TreeSet<String>(Collections.reverseOrder());

		tree.add("asqd");
		tree.add("azsde");
		tree.add("afsdf");
		tree.add("asgd");

		System.out.println("逆向排序：" + tree);
	}
}