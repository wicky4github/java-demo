import java.util.*;

class GenericDemo2 {
	public static void main(String[] args) {
		ArrayList<String> sList = new ArrayList<String>();
		sList.add("字符串1");
		sList.add("字符串2");
		sList.add("字符串3");

		ArrayList<Integer> iList = new ArrayList<Integer>();
		iList.add(123);
		iList.add(456);
		iList.add(789);

		//定义多个类型迭代器：愚蠢！
		print(sList);
		print(iList);
	}
	// <?> 				  通配符接受任意类
	// <? extends Person> 接受Person类以及其子类（泛型限定：上限）
	// <? super Person>   接受Person类以及其父类（泛型限定：下限）
	public static void print(ArrayList<?> t){
		for (Iterator<?> it = t.iterator(); it.hasNext(); ) {
			System.out.println("元素：" + it.next());
		}
	}
	//写法2：public static <T> void print(ArrayList<T> t){}
}