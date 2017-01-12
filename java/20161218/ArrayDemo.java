class ArrayDemo{
    public static void main(String[] args) {
        //格式：  元素类型[] 数组变量 = new 元素类型[个数]
        int[] x = new int[3];
        int[] y = {1,2,3,4,5};
        //二位数组： 元素类型[][] 数组变量 = new 元素类型[个数][个数或空]
        int[][] z = new int[3][4];

        int[][] a = new int[3][];
        a[0] = new int[3];

        int[][] b = {
            {1,2,3},
            {3,5}
        };
    }
}