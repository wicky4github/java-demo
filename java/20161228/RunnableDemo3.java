/**
 * 一个进程执行多条语句先执行完了，下个进程才能进来
 *
 * 同步代码块：
 * synchronized(对象){
 * 		需要同步的代码（操作共享数据的代码）;
 * }
 *
 * ☆前提：必须有>=2的线程；必须多个线程使用同一个锁；
 */
class Ticket implements Runnable{
	private int ticket = 100;
	public void run(){
		while(true){

			synchronized (this){ //解决安全问题，但消耗资源(允许)
				if (ticket > 0) { 
					try{
						Thread.sleep(10); 
					}catch (Exception e) {
						
					}
					System.out.println(Thread.currentThread().getName() + "卖了:第" + (ticket--) + "张票" );
				}else{
					break;
				}
			}

		}
	}

	//方法2  指定方法为同步方法（同步锁：非静态-this；静态-Ticket.class）
	// public synchronized void run(){
	// 	//封装需要同步的代码if..else
	// }
}
class RunnableDemo3 {
	public static void main(String[] args) {
		Ticket t = new Ticket();
		new Thread(t).start(); 
		new Thread(t).start(); 
		new Thread(t).start(); 
		new Thread(t).start(); 
	}
}