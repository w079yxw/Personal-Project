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
import java.util.logging.Level;
import java.util.logging.Logger;
  
/** 
 * 管道通信demo 
 *  
 * @author csc 
 *  
 */  
public class pipetest {  
  
    public static void main(String[] args) throws IOException {  
  
       
        PipedOutputStream pos = new PipedOutputStream();  
        PipedInputStream pis = new PipedInputStream();  
        try {   
            pos.connect(pis);  
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
      Producer p = new pipetest().new Producer(pos);  
        
        Consumer c = new Consumer(pis);  
        p.start();  
        c.start();   
       
    }  
  
    
    private class Producer extends Thread   {  
        private PipedOutputStream pos;  
         Random random = new Random();
        File file = new File("producer.txt");
          FileWriter writer; 


        public Producer(PipedOutputStream pos)throws IOException  { 
             writer = new FileWriter(file);
               
            this.pos = pos;  
           
        }  
        
        public void run() {  
             int i=12;
           try{
                 for (int idx = 1; idx <= 100; ++idx){
                int num=random.nextInt(100);
                pos.write(num);
                
                writer.write(String.valueOf(num)+" ");
                 writer.flush();
                  

    }
                  pos.close();
                 writer.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
           
  
        }  
    }  
  
    private static class Consumer extends Thread {  
        private PipedInputStream pis;  
         File file = new File("cunsumer.txt");
          FileWriter writer;
        public Consumer(PipedInputStream pis) throws IOException {
            super();  
            writer = new FileWriter(file);
            
            this.pis = pis;  
        }  
  
        public void run() {  
            try {  
                
                int num;
                while ((num = pis.read()) >= 0) {
                    System.out.println("consume:"+num);
		
                
                writer.write(String.valueOf(num)+" ");
                 writer.flush();	
			}
               pis.close(); 
            }
           
            catch (IOException e) {  
                e.printStackTrace();  
            }  
              
                
        }  
    }  
}  
