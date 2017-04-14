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
public class SJFNonPreemptive extends Scheduler {
     public void schedule(double time){
        double timer = time ;
        Collections.sort(processList, new Comparator <Process>() {      // Sort according to arrival time
            public int compare(Process p1, Process p2) {
                return Double.compare(p1.getArrival(),p2.getArrival());
            }
        });
        LinkedList<Process> temp = new LinkedList<Process>();
        LinkedList<Process> arrived = new LinkedList<Process>();
        boolean enter = false;
        Iterator it = processList.iterator();
        Process p = null;
        while(enter || it.hasNext()){
            if (enter){
                enter = false;
            }
            else{
                p = (Process)it.next();
            }
            if ( p.getArrival() > timer ){
                Process idle = new Process();
                double idleTime = p.getArrival() - timer ;
                idle.setBurst(idleTime);
                idle.setName("Idle");
                idle.setRemainingTime(idleTime);
                temp.add(idle);
                timer = timer + idleTime;
                enter = true;
            }
            else{
                arrived.add(p);
                System.out.println(p.getName());
                while (it.hasNext()){
                    p = (Process)it.next();
                    if ( p.getArrival() <= timer ){
                        arrived.add(p);
                    }
                    else{
                        enter = true;
                        break;
                    }
                }
            }
            // sort by shortest and if 2 processes have same burst then check arrival time
            Collections.sort(arrived, new Comparator <Process>() {      
                public int compare(Process p1, Process p2) {
                    if (p1.getBurst() < p2.getBurst())
                        return -1;
                    else if (p1.getBurst() > p2.getBurst())
                        return 1;
                    else{
                        if (p1.getArrival() < p2.getArrival())
                            return -1;
                        else if (p1.getArrival() > p2.getArrival())
                            return 1;
                        else
                            return 0;
                    }
                }
            });
            Iterator tempit = arrived.iterator();
            if (tempit.hasNext()){
                Process dequeued = (Process)tempit.next();
                System.out.println(p.getArrival());
                dequeued.setWaitingTime(timer - dequeued.getArrival());
                temp.add(dequeued);
                timer += dequeued.getBurst();
                arrived.removeFirst();
            }
        }
        Iterator tempit = arrived.iterator();
        while(tempit.hasNext()){
            Process dequeued = (Process)tempit.next();
            dequeued.setWaitingTime(timer - dequeued.getArrival());
            temp.add(dequeued);
            timer += dequeued.getBurst();
        }
        
        processList = temp;
        it = processList.iterator();
        double counter = 0;
        double sum = 0;
        while(it.hasNext()){
            Process ptemp = (Process)it.next();
            if(!ptemp.getName().equals("Idle")){
                sum += ptemp.getWaitingTime();
                counter ++;
            }
        }
         setAvgWaiting(sum/counter);
    }
    public void scheduleWithInterrupt(LinkedList<Process> l,double time){
        
    }

}
