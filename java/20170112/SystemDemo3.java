import java.io.*;
import java.util.*;

/**
 * 记录程序运行次数
 */
class SystemDemo3{
    public static void main(String[] args) throws IOException
    {
        Properties prop = new Properties();

        File file = new File("count.ini");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileInputStream fis = new FileInputStream(file);
        prop.load(fis);

        String value = prop.getProperty("time");
        int count = 0;

        if (value != null) {
            count = Integer.parseInt(value);
            if (count >= 5) {
                System.out.println("你好，你使用次数已到，请注册！");
                return;
            }
        }
        prop.setProperty("time", ++count + "");//数字加空字串相当于强转

        FileOutputStream fos = new FileOutputStream(file);
        prop.store(fos, ""); // store第二个参数是注释信息

        fis.close();
        fos.close();
    }
}