import java.net.*;

class NetDemo{
	public static void main(String[] args) throws Exception
	{
		System.out.println("-------------------------------------------");
		InetAddress i = InetAddress.getLocalHost();       // 获得本地主机
		System.out.println(i.toString());
		System.out.println("name: " + i.getHostName());
		System.out.println("address: " + i.getHostAddress());

		System.out.println("-------------------------------------------");

		InetAddress i2 = InetAddress.getByName("192.168.1.100");  // 获得指定主机
		System.out.println(i.toString());
		System.out.println("name: " + i2.getHostName());
		System.out.println("address: " + i2.getHostAddress());
		System.out.println("-------------------------------------------");
	}
}