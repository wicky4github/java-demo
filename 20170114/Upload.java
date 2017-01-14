import java.net.*;
import java.io.*;
// 先java UploadServer   再java UploadClient
class Upload{
	public static void main(String[] args) {
		
	}
}
class UploadClient{
	public static void main(String[] args) throws Exception
	{
		Socket client = new Socket("192.168.1.100", 8080); // 建立客户端socket服务，指定主机和端口
		
		// 读取文件内容，写入数据到服务器
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		BufferedReader br = 
			new BufferedReader(new InputStreamReader(new FileInputStream("Upload.java")));
		String line = null;
		while((line = br.readLine()) != null){
			out.println(line);
		}
		client.shutdownOutput();	//文件读取完毕，关闭客户端输出流，相当于给流加结束标记-1
		
		// 读取服务器返回信息
		BufferedReader in = 
			new BufferedReader(new InputStreamReader(client.getInputStream()));
		System.out.println(in.readLine());// 如果服务器阻塞了，客户端也会阻塞

		br.close();	 // 关闭资源
		client.close();	
	}
}
class UploadServer{
	public static void main(String[] args) throws Exception
	{
		ServerSocket server = new ServerSocket(8080);   // 建立服务端socket服务，监听指定端口
		Socket client = server.accept(); 				// 获取客户端对象（阻塞）
		
		System.out.println(client.getInetAddress().getHostAddress() + " is connected...");
		//取出客户端数据
		BufferedReader in = 
			new BufferedReader(new InputStreamReader(client.getInputStream()));
		BufferedWriter bw = 
			new BufferedWriter(new OutputStreamWriter(new FileOutputStream("upload.txt"), "utf-8"));
		String line = null;
		while((line = in.readLine()) != null){    // 一直读，如果没有结束标记，这里会阻塞 
			bw.write(line);
			bw.newLine();
			bw.flush();
		}
		// 响应客户端
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		out.println("上传成功！");

		bw.close();
		client.close();
		server.close();
	}
}