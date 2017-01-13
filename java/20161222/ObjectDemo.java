class Person /* entends Object */ {

}
class ObjectDemo {
	public static void main(String[] args) {
		Person p = new Person();  // 任何类都是Object的子类
		Class c = p.getClass();	  // Object类有getClass方法，是Class类型

		System.out.println("类名：" + c.getName());
		System.out.println("地址哈希值：" + c.hashCode());

		//实现方式：getClass().getName() + '@' + Integer.toHexString(hashCode())
		System.out.println("类名转字串：" + p.toString());
	}
}