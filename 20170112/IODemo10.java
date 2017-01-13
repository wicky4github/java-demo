import java.io.*;

/**
 * 其他IO
 * |- RandomAccessFile 随机访问文件，自带读写方法
 * |	|- skipBytes(int x), seek(int x)
 * |
 * |- PipedInputStream <----管道流连接（多线程）---> PipedOutputStream
 * |	|- 构造传入，或者用connect
 * |- DataIn[Out]putStream 	            基本数据类型
 * |- ByteArrayIn[Out]putStream   	    字节类型       -> 字节数组，操作数据源，不操作资源，无需刷新关闭
 * |- CharArrayReader, CharArrayWriter  字符类型
 * |- StringReader, StringWirter	    字串类型
 */
class IODemo10{
	public static void main(String[] args) throws IOException
	{
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		in.connect(out);

		Read r = new Read(in);
		Write w = new Write(out);

		new Thread(r).start();
		new Thread(w).start();
	}

	// 流的读写思想操作基本数据
	public static void writeData() throws IOException
	{
		DataOutputStream dos = 
			new DataOutputStream(new FileOutputStream("data.txt"));
		dos.writeInt(123);		 // 4个字节
		dos.writeBoolean(true);  // 1个字节
		dos.writeDouble(123.789);// 8个字节
		dos.writeUTF("你好"); 	 // 特殊UTF-8，不是6个字节，是8个字节
	}
	public static void readData() throws IOException
	{
		DataInputStream dis = 
			new DataInputStream(new FileInputStream("data.txt"));
		int num = dis.readInt();
		boolean flag = dis.readBoolean();
		double dbl = dis.readDouble();
		String s = dis.readUTF();   // 读取正常UTF-8会抛出EOFException
	}

	// 用流的读写思想操作数组
	public static void writeByte(){
		//数据源
		ByteArrayInputStream bais = new ByteArrayInputStream("ABCDEFG".getBytes());
		//数据目的地
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int by = 0;
		while((by = bais.read()) != -1){
			baos.write(by); // writeTo（new FileOutputStream("a.txt")) 操作资源抛异常
		}

		System.out.println(baos.toString());
	}
}

class Read implements Runnable{
	private PipedInputStream in;
	Read(PipedInputStream in){
		this.in = in;
	}
	public void run(){
		try{
			System.out.println("开始读取数据！（阻塞开始）...");
		    byte[] buffer = new byte[1024];
		    int len = in.read(buffer);
		    String s = new String(buffer, 0, len);
		    System.out.println(s);
		    System.out.println("数据读取完毕！（阻塞结束）...");
		    in.close();
		}catch(Exception e){
		    throw new RuntimeException("管道流读取失败！");
		}
	}
}
class Write implements Runnable{
	private PipedOutputStream out;
	Write(PipedOutputStream out){
		this.out = out;
	}
	public void run(){
		try{
			System.out.println("开始写入数据！...");
			Thread.sleep(3000);
		    out.write("piped data is writing.....".getBytes());
		    out.close();
		}catch(Exception e){
		    throw new RuntimeException("管道流输出失败！");
		}
	}
}