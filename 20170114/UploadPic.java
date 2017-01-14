import java.net.*;
import java.io.*;
// 多线程，客户端并发上传文件
class UploadPic{
	public static void main(String[] args) {
		
	}
}
class UploadPicClient{
	public static void main(String[] args) throws Exception
	{
		if (args.length != 1) {
			System.out.println("请选择1张图片！");
			return;
		}
		File pic = new File(args[0]);
		if (!(pic.exists() || pic.isFile())) {
			System.out.println("文件不存在！");
			return;
		}
		if (!pic.getName().endsWith(".png")) {
			System.out.println("图片文件类型不是png！");
			return;
		}
		if (pic.length() > 1024 * 1024 * 5) {
			System.out.println("图片大小超过5M！");
			return;
		}
		Socket client = new Socket("192.168.1.100", 8080); // 建立客户端socket服务，指定主机和端口
		
		// 读取文件内容，写入数据到服务器
		OutputStream out = client.getOutputStream();
		FileInputStream fis = new FileInputStream("images/origin.png");
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = fis.read(buffer)) != -1){
			out.write(buffer, 0, len);
		}
		client.shutdownOutput();	//文件读取完毕，关闭客户端输出流，相当于给流加结束标记-1
		
		// 读取服务器返回信息
		BufferedReader in = 
			new BufferedReader(new InputStreamReader(client.getInputStream()));
		System.out.println(in.readLine());// 如果服务器阻塞了，客户端也会阻塞

		fis.close();	 // 关闭资源
		client.close();	
	}
}
class UploadPicServer{
	public static void main(String[] args) throws Exception
	{
		ServerSocket server = new ServerSocket(8080);   // 建立服务端socket服务，监听指定端口
		// 循环获取客户端，写数据
		while(true){
			Socket client = server.accept(); 				 // 获取客户端对象（阻塞）
			new Thread(new UploadPicThread(client)).start(); // 每出现一个客户端，就开一个线程
		}
	}
}
class UploadPicThread implements Runnable {
	private Socket client;
	UploadPicThread(Socket client){
		this.client = client;
	}
	public void run(){
		String ip = client.getInetAddress().getHostAddress();
		try{
			System.out.println(ip + " is connected...");
			//取出客户端数据
			InputStream in = client.getInputStream();
			FileOutputStream fos = 
				new FileOutputStream("images/origin_" + ip + "_" + System.currentTimeMillis() +".png");
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = in.read(buffer)) != -1){    // 一直读，如果没有结束标记，这里会阻塞 
				fos.write(buffer, 0, len);
			}
			// 响应客户端
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println("上传图片成功！");

			fos.close();
			client.close();
		}catch(Exception e){
		    throw new RuntimeException(ip + "上传图片失败！");
		}
	}
}