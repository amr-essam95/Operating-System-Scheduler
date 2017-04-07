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
            p.setArrival(timer);
            timer += p.getBurst();
            System.out.println("ok");
        }
        else {                                                          // Case if no avaialable processes yet
            Process idle = new Process();
            idle.setArrival(timer);
            double idleTime = p.getArrival()-timer;
            idle.setBurst(idleTime);
            idle.setName("Idle");
            temp.add(idle);                                             // Add idle state 
            p.setArrival(timer+idleTime);
            temp.add(p);
            timer = timer + idleTime + p.getBurst();
        }
    }
    processList = temp;                                                 //Update process list with list after scheduling
    }
    
    public void scheduleWithInterrupt(LinkedList<Process> l,double time){
        Iterator it = l.iterator();
        while (it.hasNext()){
            Process p = (Process) it.next();
            if (p.getName().equals("Idle")){
                processList.remove(p);
                p.setRemainingTime(p.getBurst());
                p.setIsFinished(false);
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
        schedule(0);
    }
}
