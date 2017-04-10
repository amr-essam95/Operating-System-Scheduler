/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.classes;

import java.util.LinkedList;

/**
 *
 * @author amr
 */
abstract public class Scheduler {
    public static LinkedList<Process> processList = new LinkedList<Process>();
    public static LinkedList<Process> extraProcessList = new LinkedList<Process>();
    protected static int quantum;
    
   abstract public void schedule(double time);
   abstract public void scheduleWithInterrupt(LinkedList<Process> l,double time);
   public void setQuantum(int q){
       quantum = q;
   }
    
}
