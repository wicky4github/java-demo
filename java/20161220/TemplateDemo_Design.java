/**
 * 模板方法设计模式
 * 在定义功能时，功能一部分确定，一部分不确定，而确定的部分需要调用不确定的部分
 * 则将不确定功能public 出去，根据情况 abstract 化，由子类实现
 */
abstract class GetTime{

    /*
     * 获取代码运行时间，功能确定，但是执行的代码不确定
     * 加 final 关键字 表示该方法不准被重写
     */
    public final void getTime(){
        long start = System.currentTimeMillis();
        runCode();
        long end = System.currentTimeMillis();
        System.out.println("");
        System.out.println("代码执行时间：" + (end - start) + "毫秒");
    }
    /**
     * 抽象化执行代码方法，可有默认执行语句 加{}
     */
    public abstract void runCode();
}
class myClass extends GetTime{
    // 继承了getTime()方法
    public void runCode(){
        for (int i = 0; i < 5000; i++) {
            System.out.print(i);
        }
    }
}
class TemplateDemo {
    public static void main(String[] args) {
        myClass mc = new myClass();
        mc.getTime();
    }
}