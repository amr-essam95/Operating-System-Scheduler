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
public class FCFS extends Scheduler{
    
    public void schedule(double time){
        Collections.sort(processList, new Comparator <Process>() {      // Sort according to arrival time

        public int compare(Process p1, Process p2) {
            return Double.compare(p1.getArrival(),p2.getArrival());
            
        }
    });
    Iterator it = processList.iterator();
    double timer = time;
    LinkedList temp = new LinkedList();
    while (it.hasNext()){
        Process p = (Process) it.next();
        if (p.getArrival() <= timer){                                   // Case if there is available process to schedule                     
            temp.add(p);
            p.setWaitingTime(timer - p.getArrival());
            timer += p.getBurst();
        }
        else {                                                          // Case if no avaialable processes yet
            Process idle = new Process();
            double idleTime = p.getArrival()-timer;
            idle.setBurst(idleTime);
            idle.setName("Idle");
            idle.setRemainingTime(idleTime);
            temp.add(idle);                                             // Add idle state 
            timer = timer + idleTime;
            p.setWaitingTime(timer - p.getArrival());
            temp.add(p);
            timer += p.getBurst();
        }
    }
    processList = temp;                                                 //Update process list with list after scheduling
    it = processList.iterator();
    double waiting = 0;
    int counter = 0;
    while(it.hasNext()){
        Process p = (Process)it.next();
        if (!p.getName().equals("Idle")){
            waiting += p.getWaitingTime();
            counter ++;
        }
    }
    setAvgWaiting(waiting/counter);
    }
    
    public void scheduleWithInterrupt(LinkedList<Process> l,double time){
        if (l==null||!l.isEmpty())
            return ;
        Iterator it = l.iterator();
        while (it.hasNext()){
            Process p = (Process) it.next();
            if (p.getName().equals("Idle")){
                p.setRemainingTime(p.getBurst());
                p.setIsFinished(false);
                processList.remove(p);

            }
            else if ( p.isFinished()){
                p.setRemainingTime(p.getBurst());
                p.setIsFinished(false);
                processList.remove(p);
            }
            else{
                p.setBurst(p.getRemainingTime());
            }
        }
        it = extraProcessList.iterator();
        while(it.hasNext()){
            Process p = (Process) it.next();
            processList.add(p);
        }
        schedule(time/1000.0);
    }
}
