import java.io.*;

class IODemo6{
	public static void main(String[] args) throws IOException
	{
		//创建读取流对象
		FileReader fr = new FileReader("io/demo.txt");

		//高效字符读取read()和行读取readLine()
		BufferedReader br = new BufferedReader(fr);

		String s = null;
		while((s = br.readLine()) != null){
			System.out.println(s);
		}

		br.close();
	}
}

class MyBufferedReader{
	private FileReader r;
	MyBufferedReader(FileReader r){
		this.r = r;
	}
	/**
	 * 模拟一下readLine
	 */
	public String readLine() throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int chr = 0;
		while((chr = r.read()) != -1){
			if (chr == '\r') continue; //行尾
			if (chr == '\n') return sb.toString(); //回车
			else sb.append((char)chr);
		}
		if (sb.length() != 0) return sb.toString(); // 到了行尾，但是没有回车，但是行是有的，所以也要返回 
		return null;
	}
	public void close() throws IOException
	{
		r.close();
	}
}