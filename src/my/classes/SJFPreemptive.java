/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.classes;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import static my.classes.Scheduler.processList;

/**
 *
 * @author amr
 */
public class SJFPreemptive extends Scheduler {
    public void schedule(double time){
        double sum = 0;
        double timer = time ;
        Collections.sort(processList, new Comparator <Process>() {      // Sort according to arrival time
            public int compare(Process p1, Process p2) {
                return Double.compare(p1.getArrival(),p2.getArrival());
            }
        });
        LinkedList<Process> temp = new LinkedList<Process>();
        LinkedList<Process> arrived = new LinkedList<Process>();
        LinkedList<Process> arrivedInBurst = new LinkedList<Process>();
        boolean enter = false;
        Iterator it = processList.iterator();
        Process p = null;
        while(it.hasNext()){
                p = (Process)it.next();
            if ( p.getArrival() > timer ){
                Process idle = new Process();
                double idleTime = p.getArrival() - timer ;
                idle.setBurst(idleTime);
                idle.setName("Idle");
                idle.setRemainingTime(idleTime);
                temp.add(idle);
                timer = timer + idleTime;
            }
            else{
                boolean added = true;
                arrived.add(p);
                it.remove();
                it = processList.iterator();
                while (it.hasNext()){
                    p = (Process)it.next();
                    if ( p.getArrival() <= timer ){
                        arrived.add(p);
                        it.remove();
                        added = true;
                    }
                    else{
                        enter = true;
                        break;
                    }
                }
            }
            Collections.sort(arrived, new Comparator <Process>() {      
                public int compare(Process p1, Process p2) {
                    if (p1.getBurst()< p2.getBurst())
                        return -1;
                    else if (p1.getBurst()> p2.getBurst())
                        return 1;
                    else{
                        if (p1.getArrival() < p2.getArrival())
                            return -1;
                        else if (p1.getArrival() > p2.getArrival())
                            return 1;
                        else
                            return 0;
                    }
//                    return Double.compare(p1.getBurst(),p2.getBurst());
                }
            });
            Iterator tempit = arrived.iterator();
            boolean notInterrupted = false;
            if (tempit.hasNext()){
                Process dequeued = (Process)tempit.next();
                double assumedTimeOfFinish = timer + dequeued.getBurst();
                Process t;
                it = processList.iterator();
                while(it.hasNext()){
                    t = (Process)it.next();
                    if (t.getArrival() < assumedTimeOfFinish){
                        arrivedInBurst.add(t);
                    }
                    else{
                        break;
                    }
                }
                Collections.sort(arrivedInBurst, new Comparator <Process>() {      // Sort according to arrival time
                    public int compare(Process p1, Process p2) {
                        return Double.compare(p1.getArrival(),p2.getArrival());
                    }
                });
                Iterator arriveIt = arrivedInBurst.iterator();
                notInterrupted = true;
                while (arriveIt.hasNext()){
                    Process tempArrive = (Process)arriveIt.next();
                    if(tempArrive.getBurst()< (dequeued.getBurst()+ timer - tempArrive.getArrival())){
                        double partialBurst = tempArrive.getArrival() - timer;
                        Process x = new Process(dequeued);
                        x.setBurst(partialBurst);
                        temp.add(x);
                        timer += partialBurst;
                        dequeued.setBurst(dequeued.getOriginalBurst()- partialBurst);
                        notInterrupted = false;
                        break;
                    }
                }
                if(notInterrupted){
                    temp.add(dequeued);
                    double waitingTime = timer +dequeued.getBurst() - dequeued.getArrival() - dequeued.getOriginalBurst();
                    timer += dequeued.getBurst();
                    sum += waitingTime;
                }
                
            }
            if (notInterrupted)
                arrived.removeFirst();
            it = processList.iterator();
            arrivedInBurst.clear();
        }
        it = arrived.iterator();
        while(it.hasNext()){
            Process rem = (Process)it.next();
            temp.add(rem);
            double waitingTime = timer + rem.getBurst() - rem.getArrival() - rem.getOriginalBurst();
            sum += waitingTime;
            timer += rem.getBurst();
        }
        int size = fixedProcessList.size();
        setAvgWaiting(sum/size);
        processList = temp;
    }
   public void scheduleWithInterrupt(LinkedList<Process> l,double time){
   }
}
