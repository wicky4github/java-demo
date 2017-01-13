package myclass;
import myclass.PackageDemo;

class JarDemo {
	public static void main(String[] args) {
		PackageDemo pd = new PackageDemo();
		pd.show();
	}
}

//1、jar -cvf myclass.jar myclass
//2、WIN: set classpath=.;path\to\myclass.jar
//	MAC: export CLASSPATH=.:/Volumes/办公/wicky/java/20161223/myclass.jar
//3、java myclass.JarDemo