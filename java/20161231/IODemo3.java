import java.io.*;

class IODemo3{
	public static void main(String[] args)
	{
		FileReader fr = null;

		try{
			fr = new FileReader("io/demo.txt"); 
			/*
			//方法1
			int chr = 0; //读取字符码
			while((chr = fr.read()) != -1){
				System.out.print((char)chr); //强转
			}
			*/
			//方法2：效率高
			char[] buffer = new char[3]; //定义字符数组，存储字符个数，即每次读多少个（如果文件大，则写1024，即2kb空间）
			int count = 0;  // 读了多少个字符
			while((count = fr.read(buffer)) != -1){
				System.out.print(new String(buffer, 0, count)); //new String(char[] chr, int from, int to)
			}

		}catch(IOException e){

			System.out.println(e.toString());

		}finally{
			
			try{
				if (fr != null) {
					fr.close();
				}
			}catch(IOException e){
			    System.out.println(e.toString());
			}

		}	
	}
}