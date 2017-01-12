import java.io.*;

/**
 * IO流
 * |- 字符(char)流 （融合编码表-文字）
 * |	|- Reader         读
 * |	|- Writter  	  写
 * |
 * |- 字节(byte)流 （图片，音乐，电影等特殊文件）
 * |	|- InputStream    读 
 * |	|- OutputStream   写
 * 
 * InputStream 特殊方法：int available(); 获取文件字节个数（包含\r,\n也算1个）
 *  byte[] buffer = new byte[foo.available()] 定义一个刚刚好的缓冲区
 *  foo.read(buffer) 直接通过缓冲区读取，无需循环（大文件别用这方法，老实用1024整数倍循环读取）
 */

class IODemo{
	public static void main(String[] args) throws IOException
	{
		/**
		 * FileWriter初始化对象必须明确目标文件
		 * 如果文件不存在，则创建；如果存在，则覆盖
		 * 第二个参数传true，表示追加；false为覆盖
		 * 文件写入编码以System.getProperties() file-encoding为主
		 */
		FileWriter fw = new FileWriter("io/demo.txt");
		fw.write("Hello world");  //将字符串写到流（内存）
		fw.flush();  // 刷新流（内存）数据至目标文件中
		fw.write("!");
		fw.close();  // 关闭流，并将流数据flush至目标文件中;目标文件不可再操作
		

		FileOutputStream fos = new FileOutputStream("io/stream.txt");
		//write(byte[] b)
		fos.write("Hello, IO!".getBytes());  //字符串转字节数组:字节写入，无需flush
		fos.close();
	}
}