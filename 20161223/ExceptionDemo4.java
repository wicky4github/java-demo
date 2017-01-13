/**
 * 自定义异常
 * 继承Exception，程序会继续执行      --  需要声明
 * 继承RuntimeException，程序会停止  --  不需要声明
 */
class MyException extends Exception {
    MyException(String msg){
        super(msg);  // Exception父类Throwable类有构造函数处理异常信息，调用了即可
    }
}
class Calc{
    // 手动抛出异常 需要声明
    static int division(int a, int b) throws MyException
    {
        if (b == 0) {
            throw new MyException("被除数是0！"); //手动抛出异常
        }
        return a / b;
    }
}
class ExceptionDemo4 {
    public static void main(String[] args) {
        try{
            Calc.division(4, 0);
        }catch (MyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("程序结束！");
    }
}