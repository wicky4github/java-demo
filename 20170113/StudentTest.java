import java.util.*;
import java.io.*;

/**
 * 需求：  输入 姓名，课程成绩，计算总成绩，按成绩分数高低写入磁盘
 * 思想：
 * 		1. 学生对象
 * 		2. 集合存储
 * 		3. 流操作集合
 * 	别Ctrl+B
 */
class StudentTest {
	public static void main(String[] args) throws Exception
	{
		Comparator<Student> cmp = Collections.reverseOrder();// 反转比较器

		System.out.println("请输入学生信息（格式-name,math,chinese,enghlish），输入over结束！");
		Set<Student> stus = StudentInfoTool.getStudents(cmp);

		StudentInfoTool.write2File(stus);
	}
}
class Student implements Comparable<Student> {
	private String name;
	private int math, chinese, english;
	private int sum;
	Student(String name, int math, int chinese, int english){
		this.name = name;
		this.math = math;
		this.chinese = chinese;
		this.english = english;
		sum = math + chinese + english;
	}
	public String getName(){
		return name;
	}
	public int getSum(){
		return sum;
	}
	// 让对象具备区别性，非重复 -> HashSet
	public int hashCode(){
		return name.hashCode() + sum * 123;
	}
	public boolean equals(Object obj){
		if (!(obj instanceof Student)) {
			throw new ClassCastException("类型不匹配");
		}
		Student stu = (Student)obj;

		return this.name.equals(stu.name) && this.sum == stu.getSum();
	}
	// 让对象具备比较性，自动排序 -> TreeSet
	public int compareTo(Student stu){
		int num = new Integer(this.sum).compareTo(new Integer(stu.sum));  // 主要条件，总分
		if (num == 0) { 
			return this.name.compareTo(stu.name);  //次要条件：名字
		}
		return num;
	}
	public String toString(){
		return "student[name: " + name + ", math: " + math + ", chinese: " + chinese + ", english: " + english + "]";
	}
}
/**
 * 操作学生信息的工具
 */
class StudentInfoTool {
	public static Set<Student> getStudents() throws IOException,NumberFormatException
	{
		return getStudents(null);  // 代码复用性：传递空比较器
	}
	/**
	 * 通过键盘录入信息获取学生，录入方式： 姓名,数学成绩,语文成绩,英语成绩
	 * @param  cmp         [自定义比较器]
	 * @return             [学生TreeSet集合]
	 * @throws IOException [description]
	 */
	public static Set<Student> getStudents(Comparator<Student> cmp) throws IOException, NumberFormatException
	{
		BufferedReader br = 
		    new BufferedReader(new InputStreamReader(System.in));

		Set<Student> stus = null;
		if (cmp == null) {
			stus = new TreeSet<Student>();
		}else{
			stus = new TreeSet<Student>(cmp);
		}

		String line = null;
		while((line = br.readLine()) != null){
			if ("over".equals(line)) {
				break;
			}
			String[] info = line.split(",");
			if (info.length != 4) {
				System.out.println("！！！！！！！！输入信息有误！！！！！！！");
				continue;
			}
			Student stu = new Student(
				info[0], 
				Integer.parseInt(info[1]), 
				Integer.parseInt(info[2]), 
				Integer.parseInt(info[3]));
			stus.add(stu);
		}
		br.close();
		return stus;
	}
	/**
	 * 将集合写入文件
	 * @param  stus        [学生集合]
	 * @throws IOException
	 */
	public static void write2File(Set<Student> stus) throws IOException
	{
		BufferedWriter bw = 
			new BufferedWriter(new OutputStreamWriter(new FileOutputStream("stu.txt", true), "utf-8"));
		for (Student stu : stus) {
			bw.write(stu.toString() + "\t");
			bw.write("sum: " + stu.getSum());
			bw.newLine();
			bw.flush();
		}
		bw.close();
	}
}