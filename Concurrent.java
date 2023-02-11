package Part2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Concurrent {
  private static Lock lock = new ReentrantLock();
  private static Condition goingDown = lock.newCondition();
  private static Condition goingUp = lock.newCondition();
  private static int count = 0;


  private static void countUp() {
    lock.lock();
    try {
    	while (count > 20) {
    		goingUp.await();
    	}
    	for (int i = 0; i <= 20; i++) {
    		count = i;
    		System.out.println("Thread 1: count = " + count);
    		goingUp.signalAll();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  private static void countDown() {
    lock.lock();
    try {
    	while (count < 19) {
    		goingDown.await();
    	}
    	for (int i = 20; i >= 0; i--) {
    		count = i;
    		System.out.println("Thread 2: count = " + count);
    		goingDown.signalAll();

      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    Thread t1 = new Thread(Concurrent::countUp);
    Thread t2 = new Thread(Concurrent::countDown);
    t1.start();
    t2.start();
  }
}


