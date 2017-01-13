class StringDemo {
	public static void main(String[] args) {
		String s1 = "abc";				// s1内存有一个对象
		String s2 = new String("abc");  // s2内存有两个对象

		System.out.println(s1 == s2);	   // false   对象不相等
		System.out.println(s1.equals(s2)); // true   equals被重写，判断字符串是否相等
	
		System.out.println(s1.replace('a', 'b'));          // 字符替换
		System.out.println(s1.replaceAll("[^0-9]", "cdf"));// 正则替换
	
		System.out.println(s1.split("b")[0]);

		//字符串反转
		StringBuffer sb = new StringBuffer(s1);
		System.out.println(sb.reverse().toString());

		//计算字符串某个字符出现的次数
		int count = 0;
		int index = 0;
		String str = "asdkkkasdkkasd";
		String key = "k";
		// while((index = str.indexOf(key)) != -1){
		// 	str = str.substring(index + key.length());
		// 	count++;
		// }
		while((index = str.indexOf(key, index)) != -1){  //indexOf 可指定从哪里开始找
			index += key.length();
			count++;
		}
		System.out.println("出现次数：" + count);
	}
}