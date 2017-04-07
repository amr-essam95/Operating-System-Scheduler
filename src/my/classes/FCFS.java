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
    
    public void schedule(){
        Collections.sort(processList, new Comparator <Process>() {

        public int compare(Process p1, Process p2) {
            return Double.compare(p1.getArrival(),p2.getArrival());
            
        }
    });
    Iterator it = processList.iterator();
    double timer = 0;
    LinkedList temp = new LinkedList();
//    while (it.hasNext()){
//        Process p = (Process) it.next();
//        if (p.getArrival() <= timer){
//            temp.add(p);
//            timer += p.getBurst();
//        }
//        else {
//            
//        }
//        
//    }
    }
}
