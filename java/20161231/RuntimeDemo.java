/**
 * Runtime对象（单例设计模式）
 */
class RuntimeDemo{
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		try{
			//MAC OS启动可执行文件，可带参数 "notepad.exe RuntimeDemo.java"
			Process process = runtime.exec("/Applications/TextEdit.app/Contents/MacOS/TextEdit");
			//结束Java启动的进程
			Thread.sleep(3000);
			process.destroy();
		}catch(Exception e){
			System.out.println("没有该文件！");
		}
	}
}