import java.io.*;

/**
 * 复制图片
 */
class IODemo7{
	public static void main(String[] args)
	{
		BufferedInputStream bis = null;  //读
		BufferedOutputStream bos = null; //写

		try{
			bis = new BufferedInputStream(new FileInputStream("io/wicky.jpg"));        //读取原文件
			bos = new BufferedOutputStream(new FileOutputStream("io/wicky_copy.jpg")); //创建目标文件
			
			int by = 0; //数据字节，类型为int不是byte
			while ((by = bis.read()) != -1) {
				bos.write(by); //write方法可直接写入数据字节(数据最低8位)
				// System.out.print((char)by);
			}

		}catch(IOException e){

			throw new RuntimeException("读写失败！");

		}finally{
			
			try{
				if (bos != null) {
					bos.close();
				}
			}catch(IOException e){
			    System.out.println(e.toString());
			}
			try{
				if (bis != null){
					bis.close();
				}
			}catch(IOException e){
			    System.out.println(e.toString());
			}

		}	
	}
}

/**
 * 模拟一下 缓冲读取数据
 */
class MyBufferedInputStream throws IOException
{
	private FileInputStream fis;
	MyBufferedInputStream(FileInputStream fis){
		this.fis = fis;
	}

	private byte[] buffer = new byte[1024];
	
	private int point = 0, count = 0; // point 代表指针，count代表Byte数组长度
	
	/**
	 * 一次读一个字节，从缓冲区（字节数组）获取
	 * @return [description]
	 */
	public int read(){
		if (count == 0) { //初始化和对象内字节数组读取完的情况
			
			count = fis.read(buffer);  // 重新读取，默认FileInputStream(数组)，把字节读入数组，返回长度
			if (count < 0) {
				return -1;
			}

			point = 0;	 // 指针从0开始
			byte b = buffer[point]; // 读一个字节

			count--;  // 读完减1
			point++;  // 指针右移
			return b & 255; // 返回字节

		}else if(count > 0){  // 对象内字节数组里面还有数据
			byte b = buffer[point];

			count--;
			point++;
			return b & 0xff;  // 0xff = 255
		}
		return -1;
	}
	public void close(){
		fis.close();
	}
}
/**
 * 二进制数据 可能是这样排列  1111 1111 0101 1010 1000
 * 读取一个数据,11111111 存入 byte b
 *
 * byte b ----->  11111111 ---------------------------->   -1   read 返回int
 * int b ------>  11111111 11111111 11111111 1111  ---->  -1    byte: -1 -> int: -1
 *
 * 如果 b是-1，则向上提升成int也是-1，-1代表没有字节，但是这个字节是有的
 * 如果你不返回int类型返回byte类型，则b没法改变，如果返回int类型，只需将非最低8位变成0
 * 所以需要实现
 * byte b ---->  11111111
 * int b  ---->  00000000 00000000 00000000 11111 (255)  这样就靠谱了
 * 
 * 实现方式，取一个数最低8位  即 b & 255  （位运算&, 取最低位n位 就 & 2^n ，最低8位就是2^8 = 255  
 */