/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.classes;
/**
 *
 * @author amr
 */
public class Process {
    private String name;    // name of the process
    private double burst;   // burst time for the process
    private double arrival; // arrival time of the process
    private int priority;   // priority of the process in case of priority scheduling
    private double remainingTime = burst;
    private boolean isFinished = false;
    private double waitingTime = 0;     // holds the waiting time of each process 
    private double originalBurst ;
    
    public Process(){}
    public Process (Process x){
        name = x.name;
        burst = x.burst;
        arrival = x.arrival;
        priority = x.priority;
        remainingTime = x.remainingTime;
        isFinished = x.isFinished;
        waitingTime = x.waitingTime;
        originalBurst = x.originalBurst;
    }
    public double getOriginalBurst(){
        return originalBurst;
    }
    public double getWaitingTime(){
        return waitingTime;
    }
    public double getRemainingTime(){
        return remainingTime;
    }
    public boolean isFinished(){
        return isFinished;
    }
    public String getName(){
        return name;
    }
    public double getBurst(){
        return burst;
    }
    public double getArrival(){
        return arrival;
    }
    public int getPriority(){
        return priority;
    }
    public void setOriginalBurst(double o){
        originalBurst = o;
    }
    public void setWaitingTime(double w){
        waitingTime = w;
    }
    public void setName(String s){
        name = s;
    }
    public void setBurst(double b){
        burst = b;
    }
    public void setArrival(double a){
        arrival = a;
    }
    public void setPriority(int p){
        priority = p;
    }
    public void setRemainingTime(double r){
        remainingTime = r;
    }
    public void setIsFinished(boolean f){
        isFinished = f;
    }
  

    static class java {

        public java() {
        }
    }
}

