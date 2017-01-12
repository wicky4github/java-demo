class StaticCode{
    {
        System.out.println("第四步执行");
    }
    StaticCode(){
        System.out.println("第五步执行");
    }
    /**
     * 静态代码块随着类加载执行，只执行一次，用于对 类 进行初始化
     */
    static{
        System.out.println("第三步执行，只执行一次");
    }
    
}
class StaticCodeBlock {
    static{
        System.out.println("第一步执行");
    }
    public static void main(String[] args) {
        new StaticCode();
        new StaticCode();
        System.out.println("END");
    }
    static{
        System.out.println("第二步执行");
    }
}