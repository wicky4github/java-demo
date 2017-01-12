import java.io.*;

/**
 * System.in只能读取1个字节，需求是一次读取1行
 * 用readLine读取InputStream的一行
 * 但是readLine是 BufferedReader的方法  -- 字符流
 *                InputStream           -- 字节流
 * 同理newLine是 BufferedWriter的方法
 *
 * 需要转换流：
 * （读）字节流--->转--->字符流   ======>  InputStreamReader(InputStream in)  字节流通向字符流
 * （写）字符流--->转--->字节流   ======>  OutputStreamWriter(OutputSteam out) 字符流通向字节流
 *                                ======>  OutputStreamWriter(OutputSteam out, String 编码类型)
 */
class IODemo9{
    public static void main(String[] args) throws IOException
    {
        // System.setIn(new FileInputStream("IODemo9.java"));  //改变默认输入流
        // System.setOut(new PrintStream("temp.txt"));         //改变默认输出流  System.out类型是PrintStream类型，PrintStream（OutputStream子类）有println等方法

        // InputStreamReader isr = new InputStreamReader(System.in);
        // OutputStreamWriter osr = new OutputStreamWriter(System.out);
        // 上面2行没有最高效率，用缓冲对象增强
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    // System.in是读取控制台，如果要需要从文件读取，就改成new FileInputStream("1.txt")，InputStreamWriter可以加第二个参数指定要读取文件的编码
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));  // System.out是输出到控制台，如果需要写到文件，就改成new FileOutputStrem("1.txt")，OutputStreamWriter可以加第二个参数指定写入到文件的编码

        String line = null;
        while((line = br.readLine()) != null){
            if ("over".equals(line)) {
                break;
            }
            // System.out.println(line.toUpperCase());
            bw.write(line.toUpperCase());  // 写入内存
            bw.newLine();                  // 增强对象newLine，跨平台
            bw.flush();                    // 刷新内存
        }
        br.close();
        bw.close();
    }
}