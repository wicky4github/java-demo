import java.net.*;
import java.io.*;

/**
 * TCP
 * 	|- 客户端： Socket
 * 	|		建立对象时连接主机
 * 	|
 * 	|- 服务端： ServerSocket
 * 	2个终端：先java TCPServer   再java TCPClient
 */
class NetDemo3{
	public static void main(String[] args) {
		
	}
}
class TCPClient{
	public static void main(String[] args) throws Exception
	{
		Socket client = new Socket("192.168.1.100", 8080); // 建立客户端socket服务，指定主机和端口
		
		OutputStream out = client.getOutputStream();	   // 通过socket获取输出流
		out.write("Hello!".getBytes());			   // 向服务端发送数据
		
		InputStream in = client.getInputStream(); 		  // 通过socket获取输入流
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);						  // 读取服务端返回信息
		System.out.println(new String(buffer, 0, len));

		client.close();									   // 关闭资源
	}
}
class TCPServer{
	public static void main(String[] args) throws Exception
	{
		// 服务端在接收客户端数据时，用的是该客户端的流，所以不会导致多客户端冲突
		ServerSocket server = new ServerSocket(8080);   // 建立服务端socket服务，监听指定端口，第二个参数是最大可连接数
		Socket client = server.accept(); 				// 获取客户端对象（阻塞）
		
		//取出客户端数据
		InputStream in = client.getInputStream();		// 获取客户端输入流
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		System.out.println(client.getInetAddress().getHostAddress() + ": " + new String(buffer, 0, len)); // 在服务端打印下信息
		
		OutputStream out = client.getOutputStream();	// 通过客户端输出流
		out.write(new String(server.getInetAddress().getHostAddress() + ": Hi!").getBytes());

		client.close();  // 关闭客户端，服务端可不关
	}
}