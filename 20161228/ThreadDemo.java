/**
 * 继承Thread类
 */
class myThread extends Thread{
	//重写run方法
	public void run(){
		for (int x = 1; x <= 100; x++) {
			System.out.println("自定义线程--" + x);
		}
	}
}

class ThreadDemo {
	public static void main(String[] args) {
		myThread mt = new myThread(); //创建一个线程
		mt.start();		//执行线程

		for (int x = 1; x <= 100; x++) {
			System.out.println("主线程--" + x);
		}
	}
}