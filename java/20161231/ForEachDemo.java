import java.util.*;

class ForEachDemo{
	public static void main(String[] args) {
		ArrayList<String> lStr = new ArrayList<String>();
		lStr.add("字符串1");
		lStr.add("字符串2");
		lStr.add("字符串3");

		//集合遍历(jdk1.5 简化书写)，只能获取，不能操作（迭代器可以remove,ListIterator可以增删查改）
		for (String str : lStr) {
			System.out.println(str);
		}

		//数组也支持
		int[] aInt = {1, 2, 4};
		for (int i : aInt) {
			System.out.println("i:" + i);
		}

		//地图遍历
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		hashMap.put(1, "字符串1");
		hashMap.put(2, "字符串2");
		hashMap.put(3, "字符串3");
		for (Map.Entry<Integer, String> mapEntry : hashMap.entrySet()) {
			System.out.println("key:" + mapEntry.getKey() + ",value:" + mapEntry.getValue());
		}
	}
}