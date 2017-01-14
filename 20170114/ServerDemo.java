import java.net.*;
import java.io.*;

// 编译运行，浏览器访问 http://localhost:8888
class ServerDemo{
	public static void main(String[] args) throws Exception
	{
		ServerSocket server = new ServerSocket(8888);
		while(true){
			Socket client = server.accept();
			System.out.println(client.getInetAddress().getHostAddress() + " is connected...");

			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println("It works!");

			client.close();
		}
		// server.close();
	}
}