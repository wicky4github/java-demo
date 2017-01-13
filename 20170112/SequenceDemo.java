import java.io.*;
import java.util.*;

/**
 * 序列流
 */
class SequenceDemo{
	public static void main(String[] args) throws IOException
	{
		// 只有Vector里面才有Enumeration
		Vector<FileInputStream> v = new Vector<FileInputStream>();
		v.add(new FileInputStream("./sequence/1.txt"));
		v.add(new FileInputStream("./sequence/2.txt"));
		v.add(new FileInputStream("./sequence/3.txt"));

		Enumeration<FileInputStream> en = v.elements(); // 元素取出

		SequenceInputStream sis = new SequenceInputStream(en); // 参数传递，合并流

		FileOutputStream fos = new FileOutputStream("./sequence/conbine.txt");
		
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = sis.read(buffer)) != -1){
			fos.write(buffer, 0, len);
		}

		fos.close();
		sis.close();
	}
}