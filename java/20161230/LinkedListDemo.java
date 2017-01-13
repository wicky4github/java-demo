import java.util.*;

/**
 * 特有方法：
 *                           jdk 1.6 替代方法 (列表为空，返回null,不抛异常)
 * 	addFirst();				  offserFirst();
 * 	addLast();				  offserLast();
 *	
 *  getFirst();				  peekFirst();
 *  getLast();				  peekLast();
 *
 * 	removeFirst();		      pollFirst();	  
 * 	removeLast();			  pollLast();
 */
class LinkedListDemo{
	public static void main(String[] args) {
		LinkedList link = new LinkedList();
		link.addFirst("字符串对象");
		link.addFirst(123);
		link.addFirst(true);

		System.out.println(link); // [true, 123, 字符串对象]
	}
}