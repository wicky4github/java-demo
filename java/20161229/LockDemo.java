/**
 * java 1.4 版本解决多线程数据通信问题
 */
class Resource{
    private String name;
    private int count = 1;
    private boolean flag = false; //判断name是否被取出的标志
    public synchronized void set(String name){
        while(flag){ //避免线程跳过判断，用while取代if
            try{this.wait();}catch(Exception e){}
        }
        this.name = name + (count++) ; 
        System.out.println(Thread.currentThread().getName() + " 生产：" + this.name + "号...");
        flag = true;
        this.notifyAll(); //唤醒所有线程，避免线程while造成全部等待
    }
    public synchronized void get(){
        while(!flag){
            try{this.wait();}catch(Exception e){}
        }
        System.out.println( Thread.currentThread().getName() + "消费：" + this.name + "号..........");
        flag = false;
        this.notifyAll();
    }
}
class Producer implements Runnable{
    private Resource res;
    Producer(Resource res){
        this.res = res;
    }
    public void run(){
       while(true){
            res.set("商品");
        }
    }
}
class Consumer implements Runnable{
    private Resource res;
    Consumer(Resource res){
        this.res = res;
    }
    public void run(){
        while(true){
            res.get();
        }
    }
}
class LockDemo {
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