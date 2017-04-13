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
        double sum = 0;
        int counter = 0;
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
                System.out.println(p.getName());
                System.out.println(p.getArrival());
                System.out.println(timer);
                idle.setBurst(idleTime);
                idle.setRemainingTime(idleTime);
                temp.add(idle);
                timer = timer + idleTime;
                if(p.getBurst()<= quantum){
                    p.setRemainingTime(p.getBurst());
//                    p.setWaitingTime(timer - p.getArrival());
                    temp.add(p);
                    timer += p.getBurst();
                    double waitingTime = timer - p.getArrival() - p.getOriginalBurst();
                    sum += waitingTime;
                    counter ++;
                    it.remove();
                    it = processList.iterator();
                }
                else{
                    double burst = p.getBurst();
                    Process ptemp = new Process(p);
                    ptemp.setBurst(quantum);
                    ptemp.setRemainingTime(quantum);
//                    ptemp.setWaitingTime(timer - p.getArrival());
                    temp.add(ptemp);
                    p.setBurst(burst - quantum);
                    p.setRemainingTime(burst - quantum);
                    q.add(p);
                    timer += quantum;
                    it.remove();
                    it = processList.iterator();
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
                        double waitingTime = timer - dequeued.getArrival() - dequeued.getOriginalBurst();
                        sum += waitingTime;
                        counter ++;
                        while(it.hasNext()){
                            Process tempP = (Process)it.next();
                            if (tempP.getArrival() <= (timer) ){
                                Process x = new Process(tempP);
                                q.add(x);
                                it.remove();
                            }
                            else{
        //                        enter = true;
                                it = processList.iterator();
        //                        p = tempP;
                                break;
                            }
                        } 
                    }
                    else{
                        double burst = dequeued.getBurst();
                        Process ptemp = new Process(dequeued);
                        ptemp.setBurst(quantum);
                        ptemp.setRemainingTime(quantum);
                        temp.add(ptemp);
                        dequeued.setBurst(burst-quantum);
                        dequeued.setRemainingTime(burst-quantum);
                        q.add(dequeued);
                        timer += quantum;
                        while(it.hasNext()){
                            Process tempP = (Process)it.next();
                            if (tempP.getArrival() <= (timer) ){
                                Process x = new Process(tempP);
                                q.add(x);
                                it.remove();
                            }
                            else{
        //                        enter = true;
                                it = processList.iterator();
        //                        p = tempP;
                                break;
                            }
                        } 
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
                processList.remove(p);
                it = processList.iterator();
            }
            else if (p.getArrival()<= timer && !q.isEmpty()){
                p.setRemainingTime(p.getBurst());
                q.add(p);
                it.remove();
                it = processList.iterator();
//                while(it.hasNext()){
//                    Process tempP = (Process)it.next();
//                    if (tempP.getArrival() <= timer){
//                        q.add(tempP);
//                    }
//                    else{
//                        enter = true;
//                        p = tempP;
//                        break;
//                    }
//                }
                Process dequeued = q.poll();
                if (dequeued.getBurst() <= quantum){
                    dequeued.setRemainingTime(dequeued.getBurst());
                    temp.add(dequeued);
                    timer += dequeued.getBurst();
                    while(it.hasNext()){
                        Process tempP = (Process)it.next();
                        if (tempP.getArrival() <= (timer ) ){
                            Process x = new Process(tempP);
                            q.add(x);
                            it.remove();
                        }
                        else{
    //                        enter = true;
                            it = processList.iterator();
    //                        p = tempP;
                            break;
                        }
                    } 
                    double waitingTime = timer - dequeued.getArrival() - dequeued.getOriginalBurst();
                    sum += waitingTime;
                    counter ++;
                }
                else{   
                    double burst = dequeued.getBurst();
                    Process ptemp = new Process(dequeued);
                    ptemp.setBurst(quantum);
                    ptemp.setRemainingTime(quantum);
                    temp.add(ptemp);
                    while(it.hasNext()){
                        Process tempP = (Process)it.next();
                        if (tempP.getArrival() <= (timer + quantum) ){
                            Process x = new Process(tempP);
                            q.add(x);
                            it.remove();
                        }
                        else{
    //                        enter = true;
                            it = processList.iterator();
    //                        p = tempP;
                            break;
                        }
                    } 
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
                it.remove();
                it = processList.iterator();
                Process dequeued = q.poll();
                if (dequeued.getBurst()<= quantum){
                    dequeued.setRemainingTime(dequeued.getBurst());
                    temp.add(dequeued);
                    while(it.hasNext()){
                        Process tempP = (Process)it.next();
                        if (tempP.getArrival() <= (timer + dequeued.getBurst()) ){
                            Process x = new Process(tempP);
                            q.add(x);
                            it.remove();
                        }
                        else{
    //                        enter = true;
                            it = processList.iterator();
    //                        p = tempP;
                            break;
                        }
                    } 
                    timer += dequeued.getBurst();
                    double waitingTime = timer - dequeued.getArrival() - dequeued.getOriginalBurst();
                    sum += waitingTime;
                    counter ++;
                    
            }
            else{
                double burst = dequeued.getBurst();
                Process ptemp = new Process(dequeued);
                ptemp.setRemainingTime(quantum);
                ptemp.setBurst(quantum);
                temp.add(ptemp);
                while(it.hasNext()){
                    Process tempP = (Process)it.next();
                    if (tempP.getArrival() <= ( timer + quantum )){
                        Process x = new Process(tempP);
                        q.add(x);
                        it.remove();
                    }
                    else{
//                        enter = true;
                        it = processList.iterator();
//                        p = tempP;
                        break;
                    }
                } 
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
                double waitingTime = timer - dequeued.getArrival() - dequeued.getOriginalBurst();
                sum += waitingTime;
                counter ++;
            }
            else{
                Process ptemp = new Process (dequeued);
                ptemp.setBurst(quantum);
                ptemp.setRemainingTime(quantum);
                temp.add(ptemp);
                dequeued.setBurst(burst-quantum);
                dequeued.setBurst(burst-quantum);
                q.add(dequeued);
                timer += quantum;
            }
                
        }
        setAvgWaiting(sum/counter);
        processList = temp;
    }
    public void scheduleWithInterrupt(LinkedList<Process> l,double time){
        
    }
    
}
