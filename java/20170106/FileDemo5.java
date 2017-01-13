import java.io.*;
import java.util.*;

class FileDemo5{
    public static void main(String[] args) throws IOException
    {
        File dir = new File("D:/wicky/java");
        List<File> list = new ArrayList<File>();

        fileToList(dir, list);

        listToTxt(list, new File(dir, "tree.txt").toString());
    }
    /**
     * 将File对象转换为List集合
     */
    public static void fileToList(File dir, List<File> list){
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                fileToList(file, list);
            }else{
                if (file.getName().endsWith(".java")) {
                    list.add(file);
                }
            }
        }
    }
    public static void listToTxt(List<File> list, String file) throws IOException
    {
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            bw.write("当前文件夹下所有java类型的文件：");
            bw.newLine();
            for (File f : list) {
                String path = f.getAbsolutePath();
                bw.write(path);
                bw.newLine();
                bw.flush();
            }
        }catch(IOException e){
            throw e;
        }finally{
            try{
                if (bw != null) {
                    bw.close();
                }
            }catch(IOException e){
                throw e;
            }
        }
    }
}