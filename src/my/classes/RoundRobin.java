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
        Collections.sort(processList, new Comparator <Process>() {      // Sort according to arrival time
            public int compare(Process p1, Process p2) {
                return Double.compare(p1.getArrival(),p2.getArrival());
            }
        });
        Iterator it = processList.iterator();
        Queue<Process> q = new LinkedList<Process>();
        LinkedList temp = new LinkedList();
        while (it.hasNext()){
            Process p = (Process)it.next();
            if(( p.getArrival() > timer )&&(q.isEmpty())){
                Process idle = new Process();
                idle.setName("Idle");
                double idleTime = p.getArrival() - timer ;
                idle.setBurst(idleTime);
                idle.setRemainingTime(idleTime);
                temp.add(idle);
                if(p.getBurst()<= quantum){
                    p.setRemainingTime(p.getBurst());
                    temp.add(p);
                    timer = timer + idleTime + p.getBurst();
                }
                else{
                    double burst = p.getBurst();
                    Process ptemp = p;
                    ptemp.setBurst(quantum);
                    ptemp.setRemainingTime(quantum);
                    temp.add(ptemp);
                    ptemp.setBurst(burst - quantum);
                    ptemp.setRemainingTime(burst - quantum);
                    q.add(ptemp);
                    timer = timer + idleTime + quantum;
                }
            }
            else if (p.getArrival() > timer){
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
            }
            else if (p.getArrival()<= timer && !q.isEmpty()){
                Process dequeued = q.poll();
                p.setRemainingTime(p.getBurst());
                q.add(p);
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
                if (p.getBurst()<= quantum){
                    p.setRemainingTime(p.getBurst());
                    temp.add(p);
                    timer += p.getBurst();
                }
                else{
                    Process ptemp = p;
                    double burst = p.getBurst();
                    ptemp.setRemainingTime(quantum);
                    ptemp.setBurst(quantum);
                    temp.add(p);
                    ptemp.setBurst(burst - quantum);
                    ptemp.setRemainingTime(burst - quantum);
                    q.add(ptemp);
                    timer += quantum;
                }
            }
        
        }
        while(!q.isEmpty()){
            Process dequeued = q.poll();
            double burst = dequeued.getBurst();
            if(dequeued.getBurst() <= quantum){
                dequeued.setRemainingTime(dequeued.getBurst());
                temp.add(dequeued);
                timer += dequeued.getBurst();
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
    }
    public void scheduleWithInterrupt(LinkedList<Process> l,double time){
        
    }
    
}
