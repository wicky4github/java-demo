package myclass;
/**
 * 包中类名和类方法要被访问，需要public
 */
public class PackageDemo {
	public void show(){
		System.out.println("PackageDemo show run!");
	}
	public static void main(String[] args) {
		System.out.println("你好，包名！");
	}
}

// 访问 - javac -d . PackageDemo.java -> java myclass.PackageDemo
// 			   -d 目录                         包名.主类名
// 			    其他目录  则 set classpath=.;其他目录