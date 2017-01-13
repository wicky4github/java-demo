import java.io.*;

class IODemo8{
    public static void main(String[] args) throws IOException
    {
        InputStream in = System.in;  //System.in是输入设备（键盘） 类型是InputStream

        StringBuilder sb = new StringBuilder();
        while(true){
            int chr = in.read();  // 读取键盘数据
            if (chr == '\r') {
                continue;
            }
            if (chr == '\n') {
                String s = sb.toString();
                if ("over".equals(s)) {  // 字符串是否相等用equals
                    break;
                }
                System.out.println(s.toUpperCase());
                sb.delete(0, sb.length());  // 每次输出完就清空缓冲字符串
            }else{
                sb.append((char)chr);
            }
        }
    }
}