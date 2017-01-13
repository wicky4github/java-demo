//父（超级）类
class Father{
    int num = 1;
    Father(){
        System.out.println("--Father construct--");
    }
}
//子类
class Son extends Father{
    int num = 2;
    Son(){
        //JVM 这里会隐式 调用 super(); 访问父类构造函数  如果父类无空参数构造函数，则必须自己显示调用父类带参数构造函数 super(args);
        System.out.println("--Son construct--");
    }
    Son(int num){
        this();   //第一行必须是super(); 但是如果写了this(); 说明需要调用自己的空构造函数，而自己的空参数构造函数会隐式调用super();
        this.num = num;
    }
    void show(){
        System.out.println("super: " + super.num);  //super关键字访问父类
        System.out.println("this: " + this.num);    //this关键字访问本类
    }
}
class SuperDemo {
    public static void main(String[] args) {
        Son son = new Son(3);
        son.show();
    }
}