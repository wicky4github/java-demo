class StopThread implements Runnable{
    private boolean flag = true;
    public void run(){
        while(flag){
            // try{
            //     wait();
            // }catch(InterruptedException e){
            //     System.out.println(Thread.currentThread().getName() + "...interrupt");
            // }
            System.out.println(Thread.currentThread().getName() + "...........run");
        }
    }
}
class InterruptDemo2 {
    public static void main(String[] args) {
        StopThread st = new StopThread();

        Thread t1 = new Thread(st);
        Thread t2 = new Thread(st);

        t1.setDaemon(true);  // 线程开始前，设置成守护（后台）线程
        t2.setDaemon(true);  // JVM退出，守护进程会随之结束

        t1.start();
        t2.start();

        int num = 0;
        while(true){
            if(num++ > 50){
                break;   // 主线程结束
            }
            System.out.println(Thread.currentThread().getName() + num +"...run");
        }
    }
}