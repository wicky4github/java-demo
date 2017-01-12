class Calc {
	// throws Exception 声明该方法可能有问题
	public static int division(int x, int y) throws Exception
	{
		return x / y;
	}
}
class ExceptionDemo2 {
	public static void main(String[] args) throws Exception
	{
		Calc.division(4,0); //这里会自动新建异常对象，必须处理或抛出，否则编译失败
		System.out.println("这行不会执行！");
	}
}