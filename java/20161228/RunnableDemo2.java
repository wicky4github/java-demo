/**
 * 结果会出现 0 -1 -2 等错票
 * 
 * 说明多线程运行出现了安全问题
 *
 * 原因：一条线程对多条执行语句只执行一部分，还没执行完，另一个进程就进来了，导致贡献数据的错误。
 *
 * 解决方法：RunnableDemo3
 */
class Ticket implements Runnable{
	private int ticket = 100;
	public void run(){
		while(true){
			if (ticket > 0) { //进程0 - 判断成功（有执行权） -> CPU可能切换至进程1 导致线程0暂时不执行 -> 票已经为0，但进程0有执行权，继续执行，导致-1出现
				try{
					Thread.sleep(10);  //让线程停10毫秒，模拟进程被暂停
				}catch (Exception e) {
					
				}
				System.out.println(Thread.currentThread().getName() + "卖了:第" + (ticket--) + "张票" );
			}else{
				break;
			}
		}
	}
}
class RunnableDemo2 {
	public static void main(String[] args) {
		Ticket t = new Ticket();
		new Thread(t).start(); 
		new Thread(t).start(); 
		new Thread(t).start(); 
		new Thread(t).start(); 
	}
}