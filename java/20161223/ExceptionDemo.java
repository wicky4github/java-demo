/**
 * 异常2类
 * 编译时检测
 * 编译时不被检测 - Runtime及子类 （无需声明）
 */
class Calc {
	public static int division(int x, int y)
	{
		return x / y;
	}
}
class ExceptionDemo {
	public static void main(String[] args) {
		try{
			//检测代码
			Calc.division(4,0);
		}catch (Exception e) {
			//处理异常
			System.out.println("被除数不能为0");
		}finally{
			//一定执行，通常用于关闭资源，如数据库连接
		}
	}
}