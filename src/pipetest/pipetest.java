/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipetest;

/**
 *
 * @author w079yxw
 */

import java.util.*;
import java.io.*;
import java.io.IOException;  
import java.io.PipedInputStream;  
import java.io.PipedOutputStream;  
  
/** 
 * 管道通信demo 
 *  
 * @author csc 
 *  
 */  
public class pipetest {  
  
    public static void main(String[] args) throws IOException {  
  
        // 创建管道输出流  
        PipedOutputStream pos = new PipedOutputStream();  
        // 创建管道输入流  
        PipedInputStream pis = new PipedInputStream();  
        try {  
            // 将管道输入流与输出流连接 此过程也可通过重载的构造函数来实现  
            pos.connect(pis);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // 创建生产者线程  
        Producer p = new pipetest().new Producer(pos);  
        // 创建消费者线程  
        Consumer c = new Consumer(pis);  
        // 启动线程  
        p.start();  
        c.start();  
    }  
  
    // 生产者线程(与一个管道输入流相关联)  
    private class Producer extends Thread   {  
        private PipedOutputStream pos;  
         Random random = new Random();
        File file = new File("d.txt");
          FileWriter writer; 
          


        public Producer(PipedOutputStream pos)throws IOException  { 
             writer = new FileWriter(file);
               
             
            this.pos = pos;  
           
        }  
        
        public void run() {  
             
            // while (true) {//加入此句将出现“java - IOException: Read end dead”异常  
           try{
                 for (int idx = 1; idx <= 10; ++idx){
                int num=random.nextInt(100);
                //System.out.println(num);
                pos.write(num);
                writer.write(String.valueOf(num)+" ");
                 writer.flush();
                  

    }
                  
                 writer.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            // }  
  
        }  
    }  
  
    // 消费者线程(与一个管道输入流相关联)  
    private static class Consumer extends Thread {  
        private PipedInputStream pis;  
  
        public Consumer(PipedInputStream pis) {  
            this.pis = pis;  
        }  
  
        public void run() {  
            // while(true){//加入此句将出现“java - IOException: Write end dead”异常  
            try {  
                System.out.println(pis.read()); 
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            // }  
  
        }  
    }  
}  
