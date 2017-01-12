class JzTrans{
    public static void main(String[] args) {
        //进制转换
        toBin(-60);
    }

    public static void toHex(int num){
        char[] chr = {'0', '1', '2' ,'3','4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        
        char[] arr = new char[8];
        int point = arr.length;

        while(num != 0){
            int temp = num & 15;        // 取得最低位  4位
            arr[--point] = chr[temp];   // 先把指针左移到数组边界，反着存储最低位对应的16进制值
            num >>>= 4;                 // 无符号右移4位
        }

        //从指针位置开始打印
        for (int x = point; x < arr.length; x++) {
            System.out.print(arr[x]);
        }
    }

    public static void toBin(int num){
        char[] chr = {'0', '1'};

        char[] arr = new char[32];
        int point = arr.length;

        while(num != 0){
            int temp = num & 1;         // 计算最低位  1位
            arr[--point] = chr[temp];
            num >>>= 1;
        }

        for (int x = point; x < arr.length; x++) {
            System.out.print(arr[x]);
        }
    }
}