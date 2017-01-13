/**
 * 实现Runnable接口
 * new Tread(Runnable obj) 新建进程
 */
class Ticket implements Runnable{
	private int ticket = 100;  // 好处： 无需Static占用资源
	public void run(){//重写run方法
		while(true){
			if (ticket > 0) {
				System.out.println(Thread.currentThread().getName() + "卖了:第" + (ticket--) + "张票" );
			}else{
				break;
			}
		}
	}
}
class RunnableDemo {
	public static void main(String[] args) {
		Ticket t = new Ticket();
		new Thread(t).start();  //多个线程同时执行卖票行为
		new Thread(t).start();  //多个线程同时执行卖票行为
		new Thread(t).start();  //多个线程同时执行卖票行为
		new Thread(t).start();  //多个线程同时执行卖票行为
	}
}