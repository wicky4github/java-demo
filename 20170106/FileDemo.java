import java.io.*;

/**
 * File对象封装文件夹或者文件
 * File.separator 跨平台分隔符
 */
class FileDemo{
    public static void main(String[] args) {
        File f = new File("file.txt"); //  File f = new File("." + File.separator, "file.txt");
        // System.out.println(f); // 打印构造函数参数file.txt
        
        // 增 boolean createNewFile();  static File createTempFile(String prefix, String suffix[, File dir])
        
        // 删 boolean delete();  void deleteOnExit();

        // 查 boolean canExecute(); canRead,canWrite  compareTo;  boolean exists();
    
        // 文件夹 boolean mkdir("a");  mkdirs("a/b/c");
        
        // 判断 boolean isFile(); isDirectory(); isHidden(); isAbsolute();->是否是绝对路径名
    
        // 信息 String getName(); getPath(); getParent(); getAbosultePath();  long lastModify(); length();  File getAbsolutePath();->获取绝对路径并封装成对象返回
    
        // 重命名 boolean renameTo(File destination)  -> linux mv动作
    }
}