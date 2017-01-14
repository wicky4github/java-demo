// Java正则表达式丰富，详细查看 java.util.regex.Pattern
import java.util.regex.*;
class RegexDemo{
	public static void main(String[] args) {
		test();
	}
	public static void checkQQ(){
		String qq = "1051111111";
		String regex = "[0-9]\\d{4,14}";    // java \d 会转义，需要再转义 \\  -> \\d (decimal)
		boolean flag = qq.matches(regex);   // matches(regex)匹配正则，split(regex)通过正则分割，replaceAll(regex,new)通过正则替换
		if (flag) {
			System.out.println("qq符合规则");
		}else{
			System.out.println("qq不符合规则");
		}
	}
	public static void test(){
		String str = "I will learn java will!";
		String regex = "\\b[a-z]{4}\\b";    // \b 匹配单词边界 这样不会匹配到lear

		// 把正则封装成对象
		Pattern p = Pattern.compile(regex);
		// 匹配字串，这个对象除了matches,replaceAll等，还更多方法：查找-find，获取匹配后结果-group
		Matcher m = p.matcher(str);

		// System.out.println(m.matches());  // 返回false ✅  但是这个匹配器的指针会后移，影响到m.find()
		while(m.find()){
			System.out.println(
				"start: " + m.start() + 
				", end: " + m.end() +
				", group: " + m.group()
			);
		}
	}
}