/**
 * 数组操作工具
 * @author wicky
 * @version 1.0
 */
class ArrayTool{
    private ArrayTool(){};   //私有化构造函数达到禁止被实例化的目的

    /**
     * 获取数组最大值
     * @param  arr [数组]
     * @return     [最大值]
     */
    public static int getMax(int[] arr){
        int max = 0;
        int count = arr.length;
        for (int i = 0; i < count; i++) {
            if (arr[i] > arr[max]) {
                max = i;
            }
        }
        return arr[max];
    }

    /**
     * 获取数组最小值
     * @param  arr [数组]
     * @return     [最小值]
     */
    public static int getMin(int[] arr){
        int min = 0;
        int count = arr.length;
        for (int i = 0; i < count; i++) {
            if (arr[i] < arr[min]) {
                min = i;
            }
        }
        return arr[min];
    }

    /**
     * 选择排序
     * @param arr [数组]
     */
    public static void selectSort(int[] arr){
        int count = arr.length;
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j);
                }
            }
        }
    }

    /**
     * 冒泡排序
     * @param arr [数组]
     */
    public static void bubbleSort(int[] arr){
        int count = arr.length - 1;
        boolean flag;   // 第一次是否进行了两两交换标志
        for (int i = 0; i < count; i++) {
            flag = false;
            for (int j = 0; j < count - i; j++) {   // -i 每次循环减少参与次数
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = true;    // 进行了，说明未排序 
                }
            }
            if(!flag)
                break;  // 没进行，说明数组已经排序完毕了，无需继续执行
        }
    }

    /**
     * 打印数组
     * @param arr [数组]
     */
    public static void print(int[] arr){
        int count = arr.length;
        System.out.print("[");
        for (int i = 0; i < count; i++){
            System.out.print(arr[i]);
            if(i != count-1){
                System.out.print(",");
            }
        }
        System.out.print("]");
        System.out.println();
    }

    /**
     * 交换数组元素 - java数组对象以地址传递
     * @param arr [数组]
     * @param a   [替换点1]
     * @param b   [替换点2]
     */
    private static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}