class ArrayToolDemo{
    public static void main(String[] args) {
        int[] arr = {11,2,33,4};
        /**
         * 工具类无需新建对象，麻烦加占内存，直接静态化
         * 这里没有引用文件，但是却可以使用的原因是：JVM先找当前是否有该类或者源码
         *     没有则自动查找classpath下的class文件
         */

        System.out.println("Max = " + ArrayTool.getMax(arr));

        ArrayTool.bubbleSort(arr);
        System.out.println("First = " + arr[0]);
    }
}