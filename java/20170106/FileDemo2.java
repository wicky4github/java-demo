import java.io.*;

class FileDemo2{
    public static void main(String[] args) {
        File[] files = File.listRoots();  // 静态方法listRoots()，获取File数组

        for (File file : files) {
            System.out.println(file);
        }

        System.out.println("---------------------------------------");

        File c = new File(".");
        // list() 列出当前文件夹下所有文件，包含隐藏文件  listFiles()会返回对象数组
        // 如果File指的是文件，则会抛出空指针异常
        // 可带参数：FilenameFilter接口，过滤文件名
        String[] fs = c.list();    
        for (String f : fs) {
            System.out.println(f);
        }

        System.out.println("---------------------------------------");

        String[] filter = c.list(new FilenameFilter() // 匿名类实现接口
        {          
            public boolean accept(File dir, String name){    //  实现 accept 方法 接收目录名和文件名
                return name.endsWith(".java");          // endWith 过滤后缀名
            }
        });
        for (String fil : filter) {
            System.out.println(fil);
        }
    }
}