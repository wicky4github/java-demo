/**
 * 接口成员属性和成员方法有固定修饰符
 *     常量： public static final
 *     方法： public abstract
 */
interface Interf{
    public static final double PI = 3.14;
    public abstract void show();
}
abstract class Abs{     //抽象类
    abstract void a();  //有抽象方法，类必须加abstract
}
class Imps extends Abs implements Interf{
    void a(){}          // 重写抽象父类方法
    public void show(){ // 重写接口方法
        System.out.println("PI是" + PI);
    }
}
class InterfaceDemo{
    public static void main(String[] args) {
        Imps imps = new Imps();
        imps.show();

        // 匿名对象
        new Interf(){
            public void show(){
                System.out.println("匿名接口对象");
            }
        }.show();
        new Abs(){
            public void a(){
                System.out.println("匿名抽象对象");
            }
        }.a();
    }
}