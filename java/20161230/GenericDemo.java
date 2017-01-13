// △ 当类要操作的 “引用“数据类型 不确定，则定义泛型类来操作 - 定义泛型是为了在编译时期就能知道错误，不定义则编译通过，但运行会出错

// 泛型类  Type是类型变量，随意写
class Tool<Type> {
	private Type obj;
	public void setObject(Type obj){
		this.obj = obj;
	}
	public Type getObejct(){
		return obj;
	}

	// △ 如果方法操作的应用数据不确定，则定义泛型方法

	// 泛型方法  你传什么类型就操作什么类型
	public <T> void show(T t){
		System.out.println(t);
	}

	/*
	public static void print(Type obj){}  编译错误，静态方法不可以访问类定义的泛型
	 */
	// 静态泛型方法
	public static <Q> void print(Q q){
		System.out.println(q);
	}
}

//泛型接口
interface Inter<T>{
	public void show(T t);
}
class Impl<T> implements Inter<String> {
	public void show(String t){}
}

class Worker{
	
}
class Student{

}

class GenericDemo {
	public static void main(String[] args) {
		Tool<Worker> tool = new Tool<Worker>();
		// tool.setObject(new Student());//编译错误
		// tool.show(new Student());//编译通过

		// Tool.print(new Student());//编译通过
		tool.setObject(new Worker());

		Worker worker = tool.getObejct();

	}
}