import java.util.*;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class Sound extends Thread{
    private String filename;
    public Sound(String filename){
        this.filename = filename;
    }
    public void run(){
        File soundFile = new File(filename);
        AudioInputStream audioInputStream = null;     // 音频输入流
        try{
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);  // 通过文件获取音频输入流
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        AudioFormat audioFormat = audioInputStream.getFormat(); // 音频格式化流
        SourceDataLine sourceDataLine = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);  // 数据信息，准备格式化

        try{
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(info);    // 传递音频数据信息，通过音频系统读取音频流
            sourceDataLine.open(audioFormat);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        sourceDataLine.start();     //打开音乐

        int data = 0;
        byte[] buffer = new byte[1024];
        try{
            while(data != -1){
                data = audioInputStream.read(buffer, 0, buffer.length);
                if (data >= 0) {
                    sourceDataLine.write(buffer, 0, data);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }finally{
            sourceDataLine.drain();   // 处理残余流
            sourceDataLine.close();
        }
    }
}