class StaticDemo{
    static int s = 0;
    int v = 1;

    public static void main(String[] args) {
        //静态成员调用2种方式
        StaticDemo sd = new StaticDemo();
        System.out.println("s = " + sd.s);          // 通过对象调用
        System.out.println("s = " + StaticDemo.s);  // 通过类名调用

        // 静态 只能 访问 静态
        // System.out.println(v);  ->   编译错误
    }
}