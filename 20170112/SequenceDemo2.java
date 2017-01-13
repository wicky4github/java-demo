import java.io.*;
import java.util.*;

class SequenceDemo2{
	public static void main(String[] args) throws IOException
	{
		// splitFile();
		mergeFile();
	}
	/**
	 * 合并part文件
	 */
	public static void mergeFile() throws IOException
	{
		ArrayList<FileInputStream> al = new ArrayList<FileInputStream>();
		for (int x = 0; x <= 3; x++) {
			al.add(new FileInputStream("split/origin" + x + ".png.part"));
		}
		// 通过遍历迭代器，把集合元素放进Enumeration
		final Iterator<FileInputStream> it = al.iterator();
		// 不用new Vector().elements() 直接匿名对象遍历赋值
		Enumeration<FileInputStream> en = new Enumeration<FileInputStream>(){
			// 重写2个方法
			public boolean hasMoreElements(){
				return it.hasNext();
			}
			public FileInputStream nextElement(){
				return it.next();
			}
		};
		SequenceInputStream sis = new SequenceInputStream(en);

		FileOutputStream fos = new FileOutputStream("split/origin_merge.png");
		byte[] buffer = new byte[300 * 1024];
		int len = 0;
		while((len = sis.read(buffer)) != -1){
			fos.write(buffer, 0, len);
		}

		fos.close();
		sis.close();
	}
	/**
	 * 切割文件
	 */
	public static void splitFile() throws IOException
	{
		FileInputStream fis = new FileInputStream("split/origin.png");
		FileOutputStream fos = null;

		byte[] buffer = new byte[300 * 1024];
		int len = 0;
		int count = 0;
		while((len = fis.read(buffer)) != -1){
			fos = new FileOutputStream("split/origin" + (count++) + ".png.part");
			fos.write(buffer, 0, len);
			fos.close();
		}
		fis.close();
	}
}