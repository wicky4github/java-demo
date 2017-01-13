import java.io.*;
/**
 * 打印流
 *  |- 字节打印流 PrintStream
 *  	接收：File, String, OutputStream
 *  
 *  |- 字符打印流 PriterWriter
 *  	接收：File, String, OutputStream, Writer
 */
class PrintWriterDemo{
	public static void main(String[] args) throws IOException
	{
		System.out.println("请输入字符：（over结束）");
		BufferedReader br = 
			new BufferedReader(new InputStreamReader(System.in));
		// PrintWriter out = new PrintWriter(System.out, true);  // 第二个参数如果是true表示自动刷新
		PrintWriter out = new PrintWriter(new FileWriter("PrintWriterDemo.txt"), true);

		String line = null;
		while((line = br.readLine()) != null){
			if ("over".equals(line)) {
				break;
			}
			out.println(line.toUpperCase());
		}
		out.close();
		br.close();
	}
}