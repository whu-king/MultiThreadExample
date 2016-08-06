package ch4;

import java.util.concurrent.TimeUnit;

/**
 * this example is to show us several state of thread in java
 */
public class ThreadState {

    public static void main(String[] args){
        new Thread(new TimeWaiting(),"TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();
    }

    static class TimeWaiting implements Runnable{
        public void run(){
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Waiting implements Runnable{
        public void run(){
            while(true){
                synchronized(Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Blocked implements Runnable{
        public void run(){
            synchronized(Blocked.class){
                while(true){
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

/**
 * result via the tool of jps and jstack
 * "BlockedThread-2" prio=6 tid=0x000000000ddcd000 nid=0x1f14 waiting for monitor entry [0x000000000ed3f000]
 java.lang.Thread.State: BLOCKED (on object monitor)
 at ch4.ThreadState$Blocked.run(ThreadState.java:48)
 - waiting to lock <0x00000007d5aec4b0> (a java.lang.Class for ch4.ThreadState$Blocked)
 at java.lang.Thread.run(Thread.java:722)

 "BlockedThread-1" prio=6 tid=0x000000000dd95000 nid=0x21c0 waiting on condition [0x000000000f15e000]
 java.lang.Thread.State: TIMED_WAITING (sleeping)
 at java.lang.Thread.sleep(Native Method)
 at java.lang.Thread.sleep(Thread.java:338)
 at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:360)
 at ch4.ThreadState$Blocked.run(ThreadState.java:48)
 - locked <0x00000007d5aec4b0> (a java.lang.Class for ch4.ThreadState$Blocked)
 at java.lang.Thread.run(Thread.java:722)

 "WaitingThread" prio=6 tid=0x000000000dd80000 nid=0x1eb8 in Object.wait() [0x000000000f03f000]
 java.lang.Thread.State: WAITING (on object monitor)
 at java.lang.Object.wait(Native Method)
 - waiting on <0x00000007d5aea040> (a java.lang.Class for ch4.ThreadState$Waiting)
 at java.lang.Object.wait(Object.java:503)
 at ch4.ThreadState$Waiting.run(ThreadState.java:34)
 - locked <0x00000007d5aea040> (a java.lang.Class for ch4.ThreadState$Waiting)
 at java.lang.Thread.run(Thread.java:722)

 "TimeWaitingThread" prio=6 tid=0x000000000dcd8800 nid=0x231c waiting on condition [0x000000000ef1f000]
 java.lang.Thread.State: TIMED_WAITING (sleeping)
 at java.lang.Thread.sleep(Native Method)
 at java.lang.Thread.sleep(Thread.java:338)
 at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:360)
 at ch4.ThreadState$TimeWaiting.run(ThreadState.java:21)
 at java.lang.Thread.run(Thread.java:722)
 */


