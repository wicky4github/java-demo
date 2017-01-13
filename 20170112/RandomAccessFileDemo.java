import java.io.*;

/**
 * RandomAccessFile
 * 不是IO体系的子类，直接继承Object
 * getFilePointer获取指针位置，seek改变指针位置
 * 只能操作文件，指定模式(r读w写)
 * r 不会创建文件，读取不存在文件会抛出异常
 * rw 文件不存在，自动创建
 *
 * 分段写入数据，实现多线程写入数据 ---- 文件下载实现原理
 */
class RandomAccessFileDemo{
	public static void main(String[] args) throws IOException
	{
		writeFile();
		readFile();
	}
	public static void readFile() throws IOException
	{
		RandomAccessFile raf = new RandomAccessFile("random.txt", "r");

		// raf.seek(10); // 调整指针，前提是存储是有规律的
		// raf.skipBytes(10); // 只能让指针向后跳	

		byte[] buffer = new byte[6];  // mac 默认写入utf-8, 一个中文占3个字节，int占4个字节
		raf.read(buffer);
		String s = new String(buffer);
		System.out.println("name=" + s);

		int age = raf.readInt();
		System.out.println("age=" + age);

		raf.close();
	}
	public static void writeFile() throws IOException
	{
		RandomAccessFile raf = new RandomAccessFile("random.txt", "rw");
		// raf.seek(10 * 3);  // 调整指针，重写数据
		raf.write("张三".getBytes());
		raf.writeInt(97);

		raf.write("李四".getBytes());
		raf.writeInt(98);

		raf.close();
	}
}