/**
 * java 1.5 版本解决多线程数据通信问题
 */
import java.util.concurrent.locks.*;  //引包

class Resource{
    private String name;
    private int count = 1;
    private boolean flag = false; //判断name是否被取出的标志

    private Lock lock = new ReentrantLock(); //创建系统Lock对象
    private Condition condition_producer = lock.newCondition(); //lock对象创建condition对象
    private Condition condition_consumer = lock.newCondition(); //lock对象创建condition对象

    public void set(String name) throws InterruptedException
    {
        lock.lock();  //加锁
        try{

            while(flag){ //避免线程跳过判断，用while取代if
                condition_producer.await(); //生产者线程等待  会抛出异常，由于没有catch 所以需要throws
            }
            this.name = name + (count++) ; 
            System.out.println(Thread.currentThread().getName() + " 生产：" + this.name + "号...");
            flag = true;
            condition_consumer.signal(); //唤醒消费者线程
        }
        finally{
            lock.unlock();  //抛出异常后必须解锁，所以要finally
        }
    }
    public void get() throws InterruptedException
    {
        lock.lock();
        try{
            while(!flag){
                condition_consumer.await();
            }
            System.out.println( Thread.currentThread().getName() + "消费：" + this.name + "号..........");
            flag = false;
            condition_producer.signal();
        }
        finally{
            lock.unlock();
        }
    }
}
class Producer implements Runnable{
    private Resource res;
    Producer(Resource res){
        this.res = res;
    }
    public void run(){
        try{
            while(true){
                res.set("商品");
            }
        }
        catch(InterruptedException e){

        }
    }
}
class Consumer implements Runnable{
    private Resource res;
    Consumer(Resource res){
        this.res = res;
    }
    public void run(){
        try{
            while(true){
                res.get();
            }
        }
        catch (InterruptedException e) {
            
        }
    }
}
class LockDemo2 {
    public static void main(String[] args) {
        Resource res = new Resource();
        Producer pro = new Producer(res);
        Consumer con = new Consumer(res);

        //多线程
        new Thread(pro).start();
        new Thread(pro).start();
        new Thread(con).start();
        new Thread(con).start();
    }
}