class Calc {
	public static int division(int x, int y) throws ArithmeticException, ArrayIndexOutOfBoundsException
	{
		int[] arr = new int[x];
		System.out.println(arr[x]); //数组越界异常
		return x / y;				//数字算数异常
	}
}
class ExceptionDemo3 {
	public static void main(String[] args)
	{
		System.out.println("----程序开始----");
		//针对性处理异常
		try{
			Calc.division(4,0); 
		}catch (ArithmeticException e) {
			System.out.println("除0了！");
		}catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("数组越界！");
		}
		System.out.println("----程序结束----");
	}
}