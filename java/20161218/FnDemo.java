class FnDemo{
    public static void main(String[] args) {
        //主函数是静态的，函数必须定义static
        System.out.println(power(10));
    }
    /**
     * 修饰符 返回值类型 函数名（参数列表）
     */
    public static int power(int n){
        return n * n;
    }
}