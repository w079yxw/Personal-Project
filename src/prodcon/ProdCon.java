/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodcon;

/**
 *
 * @author yipengwang
 */
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

class Producer extends Thread  {
  CircleQueue q;
 
         Random random = new Random();
  Producer(CircleQueue q) {
    this.q = q;
    new Thread(this, "Producer").start();
  }

  public void run() {
      
    for (int i = 0; i < 10; i++){
        int num=random.nextInt(100);
        System.out.println("producing:"+num);
      q.put(num);}
  }
}

class Consumer extends Thread  {
  CircleQueue q;

  Consumer(CircleQueue q) {
    this.q = q;
    new Thread(this, "Consumer").start();
  }

  public void run() {
    for (int i = 0; i < 10; i++){
      q.get();
    
  }
    
      
  for(int i =0;i<q.size();i++){
  
      System.out.println("the queue has"+q.elementData[i]);
  }
  }
  
 
}

public class ProdCon {
  public static void main(String args[]) {
    CircleQueue q = new CircleQueue(10);
    new Consumer(q);
    new Producer(q);
   
    
  }
}
class CircleQueue<T> {
    int value;

  static Semaphore semCon = new Semaphore(0);

  static Semaphore semProd = new Semaphore(1);
	
	private final int DEFAULT_SIZE = 1000;

	
	public int capacity;

	
	int[] elementData;

	
	public int head = 0;

	public int tail = 0;

	 

  void get() {
    try {
      semCon.acquire();
    } catch (InterruptedException e) {
      System.out.println("InterruptedException caught");
    }
    System.out.println("Got: " + value);
    
    semProd.release();
  }

  void put(int n) {
    try {
      semProd.acquire();
    } catch (InterruptedException e) {
      System.out.println("InterruptedException caught");
    }

    this.value = n;
    System.out.println("Put: " + n);
    this.add(n);
    semCon.release();
  }
	public CircleQueue() {
		capacity = DEFAULT_SIZE;
		elementData = new int[capacity];
	}

	
	public CircleQueue(final int initSize) {
		capacity = initSize;
		elementData = new int[capacity];
	}

	
	public int size() {
		if (isEmpty()) {
			return 0;
		} else if (isFull()) {
			return capacity;
		} else {
			return tail + 1;
		}
	}

	
	public void add(int element) {
		if (isEmpty()) {
			elementData[0] = element;
		} else if (isFull()) {
			elementData[head] = element;
			head++;
			tail++;
			head = head == capacity ? 0 : head;
			tail = tail == capacity ? 0 : tail;
		} else {
			elementData[tail + 1] = element;
			tail++;
		}
	}

	public boolean isEmpty() {
		return tail == head && tail == 0 && elementData[tail] == -1;
	}

	public boolean isFull() {
		return head != 0 && head - tail == 1 || head == 0 && tail == capacity - 1;
	}

	public void clear() {
		Arrays.fill(elementData, -1);
		head = 0;
		tail = 0;
	}

	
	public int[] getQueue() {
		final int[] elementDataSort = new int[capacity];
		final int[] elementDataCopy = elementData.clone();
		if (isEmpty()) {
		} else if (isFull()) {
			int indexMax = capacity;
			int indexSort = 0;
			for (int i = head; i < indexMax;) {
				elementDataSort[indexSort] = elementDataCopy[i];
				indexSort++;
				i++;
				if (i == capacity) {
					i = 0;
					indexMax = head;
				}
			}
		} else {
			for (int i = 0; i < tail; i++) {
				elementDataSort[i] = elementDataCopy[i];
			}
		}
		return elementDataSort;
	}

	
}
