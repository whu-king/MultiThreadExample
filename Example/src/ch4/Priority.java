package ch4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * the example tell us the rightness of program
 * can not depend on the priority of thread
 * sometimes the operating system will ignore the
 * priority you set for thread

 */
public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String []args) throws InterruptedException {
        List<Job> jobs = new ArrayList<Job>();
        for(int i = 0; i < 10; i++){
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job,"Thread" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for(Job job : jobs){
            System.out.println("Job priority : " + job.priority + " Count : " + job.jobCount);
        }

    }

    static class Job implements Runnable{
        private int priority;
        private long jobCount;
        public Job(int priority){
            this.priority = priority;
        }
        public void run(){
            while(notStart){
                Thread.yield();
            }
            while(notEnd){
                Thread.yield();
                jobCount++;
            }
        }
    }
}

/**
 * result
 Job priority : 1 Count : 42070424
 Job priority : 1 Count : 72447131
 Job priority : 1 Count : 81913394
 Job priority : 1 Count : 105624245
 Job priority : 1 Count : 104425657
 Job priority : 10 Count : 47434615
 Job priority : 10 Count : 98500977
 Job priority : 10 Count : 88884955
 Job priority : 10 Count : 20880049
 Job priority : 10 Count : 67042358
 **/