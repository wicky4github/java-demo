import java.io.*;

class IODemo5{
	public static void main(String[] args) throws IOException
	{
		//创建流对象
		FileWriter fw = new FileWriter("io/demo.txt");

		//为了提高写入效率，加入缓冲技术，必须先有流对象
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write("Hello world！"); 
		bw.newLine();   // 写入换行（Windows: \r\n，Linux：\n）
		bw.write("Hello java"); 
		bw.flush();

		//关闭缓冲区=关闭流对象
		bw.close();
	}
}