import java.net.*;
import java.io.*;

class URLDemo{
	public static void main(String[] args) throws Exception
	{
		URL url = new URL("http://127.0.0.1:8080/myweb/demo.html?a=1");

		System.out.println("getProtocol(): " + url.getProtocol());
		System.out.println("getHost(): " + url.getHost());
		System.out.println("getPort(): " + url.getPort());  // port: -1  --> port=80
		System.out.println("getPath(): " + url.getPath());
		System.out.println("getFile(): " + url.getFile());
		System.out.println("getQuery(): " + url.getQuery());

		
		try{
		// 打开链接 
		// URLConnection
		// |- HttpURLConnection
		// |- JarURLConnection
		    URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = in.read(buffer);
			System.out.println(new String(buffer, 0, len));
		}catch(ConnectException e){
			System.out.println("--------------------------");
		 	System.out.println("连接 " + url + " 失败！");   
		}
	}
}