import java.util.*;

class EncodeDemo {
	public static void main(String[] args) throws Exception
	{
		// String --> byte     str.getBytes()       .getBytes(String charName)
		// byte   --> String   new String(byte[])   new String(byte[], String charName)
		String s = "你好";
		
		byte[] b1 = s.getBytes("GBK");            // 按GBK方式编码
		String s1 = new String(b1, "ISO8859-1");  // 按ISO方式解码
		System.out.println(Arrays.toString(b1));    // 数组转字串
		System.out.println("ISO8859-1： " + s1);

		byte[] b2 = s1.getBytes("iso8859-1");    // 按ISO方式编码
		String s2 = new String(b2,"GBK");        // 按GBK方式解码
		System.out.println(Arrays.toString(b2));    // 数组转字串
		System.out.println("GBK：" + s2);

		// 不识别中文的编码可以这么干，但是UTF-8和GBK都识别中文，所以没法这么干
		
		// 极特殊： ”联通“.getBytes("GBK")  ->  编码取后8位，其规律符合UTF-8规律 -> 如果记事本开头就是联通，会自动被编码成UTF-8导致解码GBK错误（第一个字有其他中文就能解决）
	}
}