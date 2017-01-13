import java.util.*;
import java.io.*;

class SystemDemo2{
    public static void main(String[] args) throws IOException
    {
        Properties prop = new Properties();
        // System.out.println(prop); = prop.list(System.out);
        // prop.list(new PrintStream("System.txt"));
        prop.load(new FileInputStream("Setting.ini"));

        prop.setProperty("email", "123@gmail.com");

        // stringPropertyNames读取属性名，返回Set集合
        Set<String> names = prop.stringPropertyNames();
        for (String s : names) {
            System.out.println(s + " = " + prop.getProperty(s));
        }

        // 存储 ： prop.store(new FileOutputStream("Setting.ini"));

        // new MyProperties().load(new FileInputStream("Setting.ini"));
    }
}

/**
 * 模拟load
 */
class MyProperties{
    public void load(FileInputStream fis) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        while((line = br.readLine()) != null){
            String[] arr = line.split("=");
            System.out.println(arr[0] + "=" + arr[1]);
        }
        br.close();
    }
}