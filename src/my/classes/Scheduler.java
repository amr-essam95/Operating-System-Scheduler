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
    
   abstract public void schedule();
    
    
}
