class Calc{
    // Arithmetic属于Runtime异常，函数可以不需要声明
    static int division(int a, int b)
    {
        if (b == 0) {
            throw new ArithmeticException("被除数是0！"); //手动抛出异常
        }
        return a / b;
    }
}
class ExceptionDemo5 {
    public static void main(String[] args) {
        
        Calc.division(4, 0);

        System.out.println("程序结束！");
    }
}