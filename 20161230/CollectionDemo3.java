/**
 * Collection接口
 * 		|- List接口 ： 有序，可重复元素
 * 		|	|- ArrayList（数组结构：查询快，增删稍慢，线程不同步）
 * 		|	|- LinkedList（链表结构：查询慢，增删快）
 * 		|	|- Vector(数组结构：线程同步，弃用)
 * 		|
 * 		|- Set接口  ： 无序，不可重复元素
 * 		|	|- HashSet（哈希表结构：判断Object hashCode，true再判断equals，可自定义，再类中重写即可）
 * 		|	|- TreeSet（二叉树结构：可对set集合进行强行排序)
 * 					    不具备比较性的对象必须implements Comparable，重写int compareTo(Object o)）[注意：重写compareTo时，满足主要条件，需要再满足次要条件]
 *          			或者让集合初始化时，自带比较性，方法就是自己写一个Class MyComp比较器implements Comparator，重写compare(Object o1, Object o2) -> TreeSet(new MyComp())
 *          			return x - y;
 * List接口特有方法：
 * 		增： add(index, element)
 * 	  		addAll(index, Collection)
 *      删： remove(index)
 *      查： get(index)
 * 	    	subList(from, to)
 * 	     	listIterator()
 *      改： set(index, element)
 *
 * Set接口没特有方法
 */
import java.util.*;

class CollectionDemo3 {
	public static void main(String[] args) {
		ArrayList al1 = new ArrayList();
		al1.add("字符串对象");
		al1.add(0, 123);
		System.out.println("al1：" + al1);
		System.out.println("index：" + al1.indexOf("字符串对象"));
	
		//特殊：listIterator - 迭代过程中添加或删除元素（Iterator取出元素过程中不准添加元素，会有并发操作安全问题，只能remove）
		for (ListIterator li = al1.listIterator(); li.hasNext(); ) {
			Object obj = li.next();
			if(obj.equals("字符串对象")){
				li.set("新字符串对象"); //修改
				li.add("新建对象");    //添加
			}
		}
		System.out.println("al1：" + al1);

		HashSet hs = new HashSet();
		hs.add("字符串01");
		hs.add("字符串02");
		hs.add("字符串03");
		hs.add("字符串01");
		System.out.println("hs:" + hs);

		// TreeSet ts = new TreeSet();
		TreeSet ts = new TreeSet(new StrLenComparator());
		ts.add("字符串03");
		ts.add("字符串011");
		ts.add("字符串02");
		ts.add("字符串011");
		System.out.println("ts:" + ts);
	}
}

//自定义比较器
class StrLenComparator implements Comparator {
	public int compare(Object o1, Object o2){
		String s1 = (String)o1;
		String s2 = (String)o2;

		int num = s1.length() - s2.length(); //主要条件：字符串长度
		if (num == 0) { // 字符串长度相等必须再判断
			return s1.compareTo(s2); //次要条件：字符串顺序
		}
		return num;
	}
}