/**
 * StringBuffer 线程同步
 */
class StringBufferDemo{
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();

		//增
		sb.append(123);
		sb.append("abc").insert(0, true);
		System.out.println(sb.toString());

		//删
		int start = 0;			//包含头
		int end = sb.length();  //不包含尾
		System.out.println(sb.deleteCharAt(0).toString());
		sb.delete(start, end);
		System.out.println(sb.toString());

		//查：indexOf substring length lastIndexOf
		
		sb.append("foobar");
		//改
		sb.setCharAt(0, 'b'); //void
		System.out.println(sb.toString());

		sb.replace(0, 3, "bar");
		System.out.println(sb.toString());
	}
}