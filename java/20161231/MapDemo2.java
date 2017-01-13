import java.util.*;

class MapDemo2{
	public static void main(String[] args) {
		HashMap<String, HashMap<String, String>> jluzh = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> class_1 = new HashMap<String, String>();
		HashMap<String, String> class_2 = new HashMap<String, String>();

		class_1.put("01", "张三");
		class_1.put("02", "李四");		

		class_2.put("01", "Wicky");
		class_2.put("02", "Taylor");

		jluzh.put("电子商务1班", class_1);
		jluzh.put("电子商务2班", class_2);

		getStudentsInfoBySchool(jluzh);
	}
	public static void getStudentsInfoBySchool(HashMap<String, HashMap<String, String>> school){
		for (Iterator<Map.Entry<String, HashMap<String, String>>> it = school.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, HashMap<String, String>> map = it.next();
			String classroom_name = map.getKey();
			HashMap<String, String> classroom = map.getValue();
			System.out.println("------" + classroom_name + "------");
			getStudentsInfoByClassroom(classroom);
		}
	}

	public static void getStudentsInfoByClassroom(HashMap<String, String> classroom){
		for (Iterator<Map.Entry<String, String>> it = classroom.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, String> map = it.next();
			String id = map.getKey();
			String name = map.getValue();
			System.out.println("学号：" + id + "--姓名：" + name);
		}
	}
}