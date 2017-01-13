/**
 * Exception
 * 		|--AException
 * 		    |--BException
 * 		|--CException
 */
class AException extends Exception {
	
}
class BException extends AException {
	
}
class CException extends Exception {
	
}

class Animal {
	void show() throws AException
	{

	}
}
/**
 * 子类覆盖父类，如果父类抛出异常，则子类只能抛出 父类的抛出的异常 或 其子异常
 *
 * 如果父类没抛出异常，子类不准抛出异常
 * -> 如果子类发生异常，必须自己try处理，不准抛出
 */
class Cat extends Animal{
	// 只能抛出 AExcption,BExcption，不能抛出父类没有的异常
	void show(){

	}
}

class ExceptionDemo6 {
	public static void main(String[] args) {
		
	}
}