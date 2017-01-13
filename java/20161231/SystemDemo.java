import java.util.*;

class SystemDemo{
	public static void main(String[] args) {
		// 获取系统属性，类型：Properties -> Hashtable -> Map
		Properties prop = System.getProperties();
		
		System.setProperty("MyKey", "MyValue");  // 可set亦可get

		for (Map.Entry mapEntry : prop.entrySet()) {
			//prop地图里面全是字符串对象，无需指定泛型
			System.out.println(mapEntry.getKey() + " : " + mapEntry.getValue());
		}

	}
	// 运行命令： java -Dqq-1051807368 PropertiesDemo   添加自定义系统属性
}