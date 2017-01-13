class StopThread implements Runnable{
    private boolean flag = true;
    public synchronized void run(){
        while(flag){
            try{
                wait();  //主线程break，线程会一直等待，程序无法退出 --> 主线程使用interrupt，会让线程强制继续执行
            }catch(InterruptedException e){
                // 等待中被interrupt(中止)，会抛出异常
                System.out.println(Thread.currentThread().getName() + "...interrupt");
                // 被强制中止说明flag要改成false了
                flag = false;
            }
            System.out.println(Thread.currentThread().getName() + "...run");
        }
    }
}
class InterruptDemo {
    public static void main(String[] args) {
        StopThread st = new StopThread();

        Thread t1 = new Thread(st);
        Thread t2 = new Thread(st);

        t1.start();
        t2.start();

        int num = 0;
        while(true){
            if(num++ > 50){
                t1.interrupt();
                t2.interrupt();
                break;
            }
            System.out.println(Thread.currentThread().getName() + num +"...run");
        }
    }
}