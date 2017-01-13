class JzTrans2{
    public static void main(String[] args) {
        //进制转换
        toOct(60);
    }

    public static void toBin(int num){
        trans(num, 2, 1);
    }

    public static void toOct(int num){
        trans(num, 8, 3);
    }

    public static void toHex(int num){
        trans(num, 16, 4);
    }

    /**
     * 进制转换
     * @param num    [需要转换的数字]
     * @param base   [转换位数]
     * @param offset [移位]
     */
    public static void trans(int num, int base, int offset){
        if(num == 0){
            System.out.print(num);
            return;
        }

        char[] chr = {'0', '1', '2' ,'3','4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        
        char[] arr = new char[32];
        int point = arr.length;

        while(num != 0){
            int temp = num & (base - 1);
            arr[--point] = chr[temp];
            num >>>= offset;
        }

        for (int x = point; x < arr.length; x++) {
            System.out.print(arr[x]);
        }
    }
}