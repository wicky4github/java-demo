import java.io.*;

class IODemo2{
	public static void main(String[] args)
	{
		FileWriter fw = null; //建立引用，防止fw.close编译失败

		try{
			fw = new FileWriter("io/demo.txt", true);  // 创建流对象会抛出异常（如文件不存在）
			fw.write("\r\nHello Java");
			fw.flush();
			fw.write("!");
		}catch(IOException e){

			System.out.println(e.toString());

		}finally{ // 关闭资源是必须操作
			
			try{
				if (fw != null) { // 加判断提高代码健壮性
					fw.close(); //关闭流会抛出异常，所以要try
				}
			}catch(IOException e){
			    System.out.println(e.toString());
			}

		}	
	}
}