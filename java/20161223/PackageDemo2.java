package myclass2;

class PackageDemo2 {
	public static void main(String[] args) {
		//想访问，先编译 PackageDemo
		// 类全名： 包名.类名
		myclass.PackageDemo p = new myclass.PackageDemo();


		System.out.println("--包之间的访问--");
		p.show();
	}
}