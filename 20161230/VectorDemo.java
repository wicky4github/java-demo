import java.util.*;
class VectorDemo {
	public static void main(String[] args) {
		Vector v = new Vector();
		v.add("字符串对象");
		v.add(123);
		v.add(true);

		//带Element的方法是特殊方法，最特殊：elements - 枚举元素
		for(Enumeration en = v.elements(); en.hasMoreElements(); ){
			System.out.println(en.nextElement());
		}
		//枚举和迭代相同，但枚举方法名称太长，被Iterator替代
	}
}