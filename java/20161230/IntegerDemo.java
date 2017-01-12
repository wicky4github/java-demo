/**
 * 基本数据类型对象包装类  java.lang.类
 * byte  		Byte
 * short 		short
 * int   		Integer
 * long  		Long
 * boolean		Boolean
 * float		Float
 * double 		Double
 * char			Character
 */
class IntegerDemo {
	public static void main(String[] args) {
		//基本数据类型转字符串
		System.out.println("字符串：" + Integer.toString(123));
		
		//字符串转基本数据类型 (必须是数字格式)
		System.out.println("数字：" + Integer.parseInt("123"));
	
		//进制转换
		String s = Integer.toHexString(10);
		System.out.println("10的16进制：" + s);
		int x = Integer.parseInt(s, 16);
		System.out.println(s + "的10进制：" + x);

		// 可赋值为null，所以运算时必须判断
		Integer y = 1;	//自动装箱 Integer y = new Integer(1)
		y = x + y;      //自动拆箱 x.intValue()，运算完再自动装箱

		// 特性
		Integer a = 127;
		Integer b = 127;
		System.out.println("a==b:"+ (a==b)); //true 因为数值在byte范围内，如果数值存在，则不会开发新空间 >127则是false
	}
}