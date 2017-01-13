/**
 * 单对象设计模式
 * 1， 禁止本类被实例化（Instance）
 * 2， 自己创建本类对象
 * 3， 对外提供获取本类对象的方法
 */

//（开发）饿汉式单对象设计模式： 先初始化对象
class Single{
    private Single(){};                      //私有化构造函数禁止被实例化
    
    private static Single s = new Single();  // 因为 Single对象 只有一个 所以静态化
    
    public static Single getInstance(){      // 用静态方法访问静态变量
        return s;   
    }
}

//（面试）懒汉式单对象设计模式： 方法被调用才初始化，延迟加载
class Single2{
    private Single2(){};
    
    private static Single2 s = null;  

    public static Single2 getInstance(){ 
        //第一次判断
        if(s == null){
            // 上同步锁，同一时刻最多只有一个线程执行这段代码
            synchronized(Single2.class){
                // 双重判断
                if(s == null)
                    s = new Single2();
            }
            
        }
        return s;   
    }
}

class SingleDemo_Design {
    public static void main(String[] args) {
        Single s1 = Single.getInstance();
        Single s2 = Single.getInstance();
        System.out.println(s1 == s2); // true  因为地址一样
    }
}