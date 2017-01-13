import java.util.*;

class ArraysDemo{
	public static void main(String[] args) {
		String[] aStr = {"abc", "cde", "efg"};
		/*
			把数组转成list集合
			好处：用集合的思想和方法操作数组元素
			注意：不可使用增删方法，因为数组长度固定(UnsupportedOperationException)
		 */ 
		List<String> lStr = Arrays.asList(aStr);

		System.out.println("contains:" + lStr.contains("efg"));

		/**
		 * 如果数组中的元素是对象，则数组元素会变成集合元素
		 * 如果数组中的元素是基本数据类型，则数组本身会变成集合元素
		 */
		int[] aInt = {1, 2, 4};
		List<int[]> lInt = Arrays.asList(aInt);
		System.out.println(lInt);

		//集合可转数组  collection.toArray(new Type[size])
		//目的：限定对元素的操作：不许增删
		String[] aStr2 = lStr.toArray(new String[lStr.size()]);
		System.out.println(Arrays.toString(aStr2));
	}
}