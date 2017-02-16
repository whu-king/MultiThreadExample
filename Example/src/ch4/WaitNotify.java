package ch4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * the example shows that one thread wait on a lock object, and
 * another thread notify all thread waiting on the lock
 * and the waiting thread transform into BLOCKED state
 * once given the resource,it runs
 * Thread[WaitThread,5,main]flag is true. wait@14:00:12
 Thread[Notify,5,main]hold back. notify@14:00:13
 Thread[WaitThread,5,main]flag is false. running@14:00:18
 */
public class WaitNotify {

    static boolean flag = true;
    static Object lock =  new Object();

    public static void main(String[] args){
        Thread waitThread = new Thread(new Wait(),"WaitThread");
        waitThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread notifyThread = new Thread(new Notify(),"Notify");
        notifyThread.start();
    }

    static class Wait implements Runnable{
        public void run(){
            synchronized (lock){
                while(flag){

                    try {
                        System.out.println(Thread.currentThread() + "flag is true. wait@" +
                                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread() + "flag is false. running@" +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable{
        public void run(){
            synchronized(lock){
                System.out.println(Thread.currentThread() + "hold back. notify@" +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
/**
 * result
 *
 * Thread[WaitThread,5,main]flag is true. wait@14:00:12
   Thread[Notify,5,main]hold back. notify@14:00:13
   Thread[WaitThread,5,main]flag is false. running@14:00:18
 **/