import java.io.*;

/**
 * 复制文件
 */
class IODemo4{
	public static void main(String[] args)
	{
		FileWriter fw = null;
		FileReader fr = null;

		try{
			fw = new FileWriter("io/demo_copy.txt"); //创建目标文件
			fr = new FileReader("io/demo.txt");      //读取原文件
			char[] buffer = new char[1024];
			int count = 0;
			while ((count = fr.read(buffer)) != -1) {
				fw.write(new String(buffer, 0, count)); //写入流
			}
			fw.flush(); //写入目标文件

		}catch(IOException e){

			throw new RuntimeException("读写失败！");

		}finally{
			
			try{
				if (fw != null) {
					fw.close();
				}
			}catch(IOException e){
			    System.out.println(e.toString());
			}
			try{
				if (fr != null){
					fr.close();
				}
			}catch(IOException e){
			    System.out.println(e.toString());
			}

		}	
	}
}