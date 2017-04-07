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
  

    static class java {

        public java() {
        }
    }
}

