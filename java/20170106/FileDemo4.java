import java.io.*;

class FileDemo4{
    public static void main(String[] args) {
        createDirs("." + File.separator + "新建文件夹1" + File.separator + "新建文件夹2");
        createFile("." + File.separator + "新建文件夹1" + File.separator + "新建文件夹2" + File.separator + "test.txt");
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            
        }
        removeDir(new File("." + File.separator + "新建文件夹1"));
    }
    public static boolean createDirs(String path){
        File p = new File(path);
        return p.mkdirs();
    }
    public static boolean createFile(String path){
        File file = new File(path);
        if (!file.exists()) {
            try{
                return file.createNewFile();
            }catch(IOException e){
                
            }
        }
        return false;
    }
    /**
     * 删除文件夹
     */
    public static void removeDir(File path){
        File[] files = path.listFiles();
        for (int x = 0; x < files.length; x++) {
            if (files[x].isDirectory()) {
                //如果是文件夹，进入文件夹再删除里面的文件
                removeDir(files[x]);
            }else{
                //如果不是文件夹直接删除该文件
                files[x].delete();
            }
        }
        // 如果文件夹内容为空，则直接删除文件夹
        path.delete();
    }
}