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
    public static LinkedList<Process> fixedProcessList = new LinkedList<Process>();
    // fixed process list hold the list of processes that the user has entered.
    public static LinkedList<Process> extraProcessList = new LinkedList<Process>();
    protected static int quantum;
    protected static double avgWaitingTime = 0;
    
   abstract public void schedule(double time);
   abstract public void scheduleWithInterrupt(LinkedList<Process> l,double time);
   public void setQuantum(int q){
       quantum = q;
   }
   public void setAvgWaiting(double a){
       avgWaitingTime = a;
   }
   public double getAvgWaiting(){
       return avgWaitingTime;
   }
   
    
}
