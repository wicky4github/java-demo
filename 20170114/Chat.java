import java.net.*;
import java.io.*;

/**
 * 聊天程序-多线程，通信技术
 * 这个Demo发送和接收都在同个面板，会导致数据混杂，可以自己用图形界面分开写
 */
class Send implements Runnable {
	private DatagramSocket ds;
	public Send(DatagramSocket ds){
		this.ds = ds;
	}
	public void run(){
		try{
		    BufferedReader br =
		    	new BufferedReader(new InputStreamReader(System.in));
		    String line = null;
		    System.out.print("输入信息(quit结束)：");
		    while((line = br.readLine()) != null){
		    	if ("quit".equals(line)) {
		    		break;
		    	}
		    	byte[] buffer = line.getBytes();
		    	DatagramPacket dp = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.1.100"), 8080);
		    	ds.send(dp);
		    }
		}catch(Exception e){
		    throw new RuntimeException("发送数据失败");
		}
	}
}
class Recv implements Runnable {
	private DatagramSocket ds;
	public Recv(DatagramSocket ds){
		this.ds = ds;
	}
	public void run(){
		try{
		    while(true){
		    	byte[] buffer = new byte[1024];
		    	DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		    	ds.receive(dp);
		    	String ip = dp.getAddress().getHostAddress();
		    	String data = new String(dp.getData(), 0, dp.getLength());
		    	System.out.println("发送自" + ip + ": " + data);
		    }
		}catch(Exception e){
		    throw new RuntimeException("接收数据失败");
		}
	}
}
class Chat{
	public static void main(String[] args) throws Exception
	{
		DatagramSocket sendSocket = new DatagramSocket();
		DatagramSocket recvSocket = new DatagramSocket(8080);

		new Thread(new Send(sendSocket)).start();
		new Thread(new Recv(recvSocket)).start();
	}
}