class SwapDemo{
    public static void main(String[] args) {
        int n = 1, m = 2;
        n = n ^ m;
        m = n ^ m;
        n = n ^ m;
        System.out.println("n = " + n + "," + "m = " + m);
        /*
            一个数^同一个数2次，还是原来的数

            n  = n ^ m   n = 1 ^ 2 = 3      n  = n ^ m
            m  = n ^ m   m = 3 ^ 2 = 1      m  = (n ^ m) ^ m   = n
            n  = n ^ m   n = 3 ^ 1 = 2      n  = n ^ (n ^ m)   = m
        */
    }
}