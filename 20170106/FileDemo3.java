import java.io.*;

class FileDemo3{
    public static void main(String[] args) {
        File dir = new File("..");
        showDir(dir, 0);
    }
    public static void showDir(File dir, int level){
        System.out.println(getLevel(level) + dir);
        
        level++;
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                showDir(files[i], level);
            }else{
                System.out.println(getLevel(level) + files[i]); 
            }
        }
    }
    public static String getLevel(int level){
        StringBuilder sb = new StringBuilder();
        sb.append("|--");
        for (int k = 0; k < level; k++) {
            sb.insert(0, "|  ");
        }
        return sb.toString();
    }
}