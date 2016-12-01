/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ceg4350project;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;



/**
 *
 * @author Ecslogon2
 */
public class CEG4350project {
private static BlockingQueue<Integer> queue=new ArrayBlockingQueue<Integer>(10);
    public static void main(String[] args) throws InterruptedException {
   	 Thread t1=new Thread(new Runnable(){
   		 public void run(){
   			 try {
   				 producer();
   			 } catch (InterruptedException e) {
   				 // TODO Auto-generated catch block
   				 e.printStackTrace();
   			 }}
   		 });
   	 Thread t2=new Thread(new Runnable(){
   		 public void run(){
   			 try {
   				 consumer();
   			 } catch (InterruptedException e) {
   				 // TODO Auto-generated catch block
   				 e.printStackTrace();
   			 }}
   		 });
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    }
    private static void producer() throws InterruptedException{
   	 Random random=new Random();
   	 while(true){
   		 
   		 queue.put(random.nextInt(100));
   	 }
    }
    private static void consumer() throws InterruptedException{
   	 Random random=new Random();
   		 
   	 while(true){
   		 
   		 Thread.sleep(100);
   		 if(random.nextInt(10)==0){
   			 Integer value=queue.take();
   			 System.out.println("taken value: "+value+"; Queue size is "+queue.size());
   			 
   		 }
   	 }    
   	 
   	 }
    
    

    
}
