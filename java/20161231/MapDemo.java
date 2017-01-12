/**
 * Map<K, V>集合框架（理解成php $arr=['key' => 'value']，理解成json也可）
 * 	|- Hashtable（哈希表数据结构：不可将null作为key和value，线程同步jdk1.0）
 * 	|- HashMap（哈希表数据结构：允许将null作为key和value，线程不同步jdk1.2）
 * 	|- TreeMap（二叉树数据结构：自动按key排序，线程不同步）
 */
import java.util.*;

class MapDemo {
	public static void main(String[] args) {
		System.out.println("----------------------------------");
		HashMap hm = new HashMap();
		
		//增加和更新元素 - put会返回上一个键值的值
		hm.put("key1", "字符串对象");
		hm.put("key1", "新字符串对象");
		hm.put("key2", 123);
		hm.put("key3", true); 

		//删除元素(变量、数字)  clear可清空
		hm.remove("key1");
		System.out.println("hm" + hm);

		//获取 get(Object key) size() values() entrySet() keySet()
		System.out.println("Map长度：" + hm.size());
		System.out.println("key2对应的值：" + hm.get("key2"));//单个获取

		System.out.println("----------------------------------");

		Collection collection = hm.values(); //可指定泛型类型
		System.out.println("通过values获取：" + collection);

		System.out.println("----------------------------------");

		//通过把键值存入Set集合，而Set集合具备迭代器，so...
		Set keySet = hm.keySet();
		for (Iterator it = keySet.iterator(); it.hasNext(); ) {
			Object key = it.next();
			System.out.println( "[key:" + key + ", value:" + hm.get(key) + "]");
		}

		System.out.println("----------------------------------");

		//将map集合映射关系存入Set集合中，类型是Map.Entry<K, V>  泛型可嵌套
		Set<Map.Entry> entrySet = hm.entrySet();
		for (Iterator<Map.Entry> it = entrySet.iterator(); it.hasNext(); ) {
			Map.Entry me = it.next();
			System.out.println( "[key:" + me.getKey() + ", value:" + me.getValue() + "]" );
		}

		System.out.println("----------------------------------");

		//判断: containsValue(Object v) containsKey(Object k) isEmpty()
	}
}