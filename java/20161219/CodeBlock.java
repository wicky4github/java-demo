class CodeBlock{
    {
        //和构造函数一样，新建对象自动执行，但是优先级比构造函数高
        //  构造代码块 ： 统一对对象初始化
        //  构造函数   ： 给对应的对象初始化
        System.out.println("构造代码块 初始化 对象");
    }
    CodeBlock(){
        System.out.println("构造函数 初始化 对象");
    }
    CodeBlock(int n){
        //构造函数 之间 的调用 必须用this， 不能用构造函数名()
        this();   //这句话必须是第一行
        System.out.println("n = " + n);
    }
    public static void main(String[] args) {
        new CodeBlock();
        new CodeBlock(100);
    }
}