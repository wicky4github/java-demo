import java.net.*;
// 开2个终端，分别编译2个类  一个java NetDemo2Receiver 另一个java NetDemo2
/**
 * 通过UPD发送数据（发送端）
 * 思路：
 * 		建立UDPSocket服务
 * 		提供数据，封装数据到数据包
 * 		用socket服务发送数据包
 * 		关闭资源
 */
class NetDemo2{
	public static void main(String[] args) throws Exception
	{
		DatagramSocket ds =  new DatagramSocket();  // 建立服务
		byte[] data = "Hello world!".getBytes();    // 确定数据
		DatagramPacket dp = 
			new DatagramPacket(data, data.length, InetAddress.getByName("192.168.1.100"), 8080); //打包数据，发送到 本机:8080
		ds.send(dp);	// 发送数据
		ds.close();		// 关闭资源
	}
}
/**
 * 接收UPD数据（接收端：监听端口）
 * 思路：
 * 		建立UDP服务
 * 		定义临时数据包存储接收的数据
 * 		用socket服务receive方法存入临时数据包
 * 		用数据包对象特有功能，取出数据
 * 		关闭资源
 */
class NetDemo2Receiver{
	public static void main(String[] args) throws Exception
	{
		DatagramSocket ds = new DatagramSocket(8080);  // 建立服务，监听8080端口
		
		while(true){
			byte[] d = new byte[1024];
			DatagramPacket dp = new DatagramPacket(d, d.length);  // 临时数据包
			ds.receive(dp);   //接收数据，并存储到临时数据包里（阻塞式）

			// 取出数据
			String ip = dp.getAddress().getHostAddress();
			String data = new String(dp.getData(), 0, dp.getLength());
			int port = dp.getPort();
			System.out.println("ip: " + ip + "... port: " + port + "... data: " + data);
		}
		
		// ds.close(); //关闭资源
	}
}