package ch4;

import java.util.concurrent.TimeUnit;

/**
 * the example shows that when a thread is interrupted,
 * if the thread catch the interruption or throw it
 * the isInterrupted() flag will be reset as false
 * else the isInterrupted() flag will be true
 */
public class Interrupted {

    public static void main(String[] args)throws Exception{
        Thread sleepThread = new Thread(new SleepThread(),"SleepThread");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyThread(),"BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread Interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread Interrupted is " + busyThread.isInterrupted());
        TimeUnit.SECONDS.sleep(2);
    }

    static class SleepThread implements Runnable{
        public void run(){
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BusyThread  implements Runnable {
        public void run(){
            while(true){

            }
        }
    }
}

/**
 * result
 SleepThread Interrupted is false
 BusyThread Interrupted is true
 java.lang.InterruptedException: sleep interrupted
 at java.lang.Thread.sleep(Native Method)
 at java.lang.Thread.sleep(Thread.java:338)
 at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:360)
 at ch4.Interrupted$SleepThread.run(Interrupted.java:29)
 at java.lang.Thread.run(Thread.java:722)
 */
