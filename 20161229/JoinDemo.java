class Demo implements Runnable{
    public void run(){
        for (int x = 0; x < 50; x++) {
            System.out.println(Thread.currentThread().getName() + " x:" + x);
        }
    }
}
class JoinDemo {
    public static void main(String[] args) throws InterruptedException{
        Demo d = new Demo();
        Thread t1 = new Thread(d);
        Thread t2 = new Thread(d);

        t1.start();
        t1.join();  //申请执行权，加入当前（主）线程，并冻结当前（主）线程

        t2.start(); // t1线程结束后，t2线程和主线程才会继续走
        t2.setPriority(Thread.MAX_PRIORITY);  //设置运行优先级 MAX_PRIORITY 10 MIN 1 NORM 5

        for (int x = 0; x < 50; x++) {
            System.out.println(Thread.currentThread().getName() + " x:" + x);
        }
    }
}