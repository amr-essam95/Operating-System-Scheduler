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
import java.util.Queue;

/**
 *
 * @author amr
 */
public class RoundRobin extends Scheduler {
    public void schedule(double time){
        double timer = time;
        boolean enter = false;
        Collections.sort(processList, new Comparator <Process>() {      // Sort according to arrival time
            public int compare(Process p1, Process p2) {
                return Double.compare(p1.getArrival(),p2.getArrival());
            }
        });
        Iterator it = processList.iterator();
        Queue<Process> q = new LinkedList<Process>();
        LinkedList temp = new LinkedList();
        Process p =null;
        while (enter||it.hasNext()){
            if (!enter){
                p = (Process)it.next();
            }
            enter = false;
        /*  
            Case if at time = timer no process has arrived and the queue of processes is empty
            In this case we add an idle process until the arrival of the first process
        */
            if(( p.getArrival() > timer )&&(q.isEmpty())){  
                Process idle = new Process();
                idle.setName("Idle");
                double idleTime = p.getArrival() - timer ;
                idle.setBurst(idleTime);
                idle.setRemainingTime(idleTime);
                temp.add(idle);
                timer = timer + idleTime;
                if(p.getBurst()<= quantum){
                    p.setRemainingTime(p.getBurst());
//                    p.setWaitingTime(timer - p.getArrival());
                    temp.add(p);
                    timer += p.getBurst();
                }
                else{
                    double burst = p.getBurst();
                    Process ptemp = p;
                    ptemp.setBurst(quantum);
                    ptemp.setRemainingTime(quantum);
//                    ptemp.setWaitingTime(timer - p.getArrival());
                    temp.add(ptemp);
                    ptemp.setBurst(burst - quantum);
                    ptemp.setRemainingTime(burst - quantum);
                    q.add(ptemp);
                    timer += quantum;
                }
            }
            else if ( p.getArrival() > timer ){
                // In this case you have processes in the queue and have a new process
                // so run the first one in the queue and add the new process to the queue
                boolean finished = false;
                while ( p.getArrival() > timer && !finished){
                    Process dequeued = q.poll();
                    if (dequeued.getBurst() <= quantum){
                        dequeued.setRemainingTime(dequeued.getBurst());
                        temp.add(dequeued);
                        timer += dequeued.getBurst();
                    }
                    else{
                        double burst = dequeued.getBurst();
                        dequeued.setBurst(quantum);
                        dequeued.setRemainingTime(quantum);
                        temp.add(dequeued);
                        dequeued.setBurst(burst-quantum);
                        dequeued.setRemainingTime(burst-quantum);
                        q.add(dequeued);
                        timer += quantum;
                    }
                    if (q.isEmpty()){
                        finished = true;
                    }
                }
                if (finished){
                    Process idle = new Process();
                    idle.setName("Idle");
                    double idleTime = p.getArrival() - timer ;
                    idle.setBurst(idleTime);
                    idle.setRemainingTime(idleTime);
                    temp.add(idle);
                    timer += idleTime;
                }
                p.setRemainingTime(p.getBurst());
                q.add (p);
            }
            else if (p.getArrival()<= timer && !q.isEmpty()){
                p.setRemainingTime(p.getBurst());
                q.add(p);
                while(it.hasNext()){
                    Process tempP = (Process)it.next();
                    if (tempP.getArrival() <= timer){
                        q.add(tempP);
                    }
                    else{
                        enter = true;
                        p = tempP;
                        break;
                    }
                }
                Process dequeued = q.poll();
                if (dequeued.getBurst() <= quantum){
                    dequeued.setRemainingTime(dequeued.getBurst());
                    temp.add(dequeued);
                    timer += dequeued.getBurst();
                }
                else{   
                    double burst = dequeued.getBurst();
                    dequeued.setBurst(quantum);
                    dequeued.setRemainingTime(quantum);
                    temp.add(dequeued);
                    dequeued.setBurst(burst-quantum);
                    dequeued.setRemainingTime(dequeued.getBurst());
                    q.add(dequeued);
                    timer += quantum;
                }    
            }
            else if(p.getArrival()<=timer){
                // Case if no processes in the queue and one or more processes come at that arrival time
                // put all of them in the queue and take the first one
                p.setRemainingTime(p.getBurst());
                q.add(p);
                while(it.hasNext()){
                    Process tempP = (Process)it.next();
                    if (tempP.getArrival() <= timer){
                        q.add(tempP);
                    }
                    else{
                        enter = true;
                        p = tempP;
                        break;
                    }
                } 
                Process dequeued = q.poll();
                if (dequeued.getBurst()<= quantum){
                    dequeued.setRemainingTime(dequeued.getBurst());
                    temp.add(dequeued);
                    timer += dequeued.getBurst();
                }
                else{
                    double burst = dequeued.getBurst();
                    dequeued.setRemainingTime(quantum);
                    dequeued.setBurst(quantum);
                    temp.add(dequeued);
                    dequeued.setBurst(burst - quantum);
                    dequeued.setRemainingTime(burst - quantum);
                    q.add(dequeued);
                    timer += quantum;
                }
            }
        
        }
        while(!q.isEmpty()){
            Process dequeued = q.poll();
            double burst = dequeued.getBurst();
            if(burst <= quantum){
                dequeued.setRemainingTime(burst);
                temp.add(dequeued);
                timer += burst;
            }
            else{
                dequeued.setBurst(quantum);
                dequeued.setRemainingTime(quantum);
                temp.add(dequeued);
                dequeued.setBurst(burst-quantum);
                dequeued.setBurst(burst-quantum);
                q.add(dequeued);
                timer += quantum;
            }
                
        }
        processList = temp;
    }
    public void scheduleWithInterrupt(LinkedList<Process> l,double time){
        
    }
    
}
