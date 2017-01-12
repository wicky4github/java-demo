/**
 * StringBuilder 线程不同步（单线程效率高） -> jdk1.5
 */
class StringBuilderDemo{
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		//增删查改 与StringBuffer兼容
		System.out.println(sb.append("foobar").toString());
	}
}