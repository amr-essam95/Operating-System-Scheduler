/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.classes;
import java.awt.Font;
import java.awt.Insets;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton; 
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.border.EmptyBorder;

public class GanttChart extends Thread  // inheriting thread library
{
    LinkedList<Process> process;
    private Thread t;
    private JPanel panel;
    double time =0;
    int size=0;
    int location = 25;
    JButton b;
    JLabel interrupter;
    int state = 1;                      // 0 for waiting , 1 for running , 2 for interruption
    boolean isDrawn = false;
    boolean isNewProcess = false;
    Process lastProcess;
    
    public GanttChart(JPanel p){
        panel = p;   
    }
    public void initializeTimeLine(){
        double t = 0;
        String s = Double.toString(t);
        JLabel initialPlace = new JLabel(s);
        panel.add(initialPlace);
        initialPlace.setLocation(location,125);
        initialPlace.setSize(50,50);
        initialPlace.setFont(new Font("Tahoma", Font.PLAIN, 10));
    }
    public boolean draw(double sizeOfPeriod,double f1){
  
        if (size > sizeOfPeriod){                                   // As long as the size is less than the intended size
            String ti = String.format( "%.2f", (time-f1)/1000.0 );  // labels are in seconds
            time -= f1;
            JLabel l = new JLabel(ti);
            panel.add(l);
            l.setLocation(location,125);
            l.setSize(50,50);
            l.setFont(new Font("Tahoma", Font.PLAIN, 10));
            return false;
        }
//        b.setMargin(new Insets(0, 0, 0, 0));
//        b.setBorder(new EmptyBorder(0, 0, 0, 0));
        b.setSize(size,50);
        
//        System.out.println(size);
        size+=1;
        time += f1;
        location = location +1;
        panel.setSize( location+300,180 );
        panel.setPreferredSize(panel.getSize());
        return true;
    }
    public void run(){
        state = 1;                              // state 1 means thread is running
        Iterator it = process.iterator();
        panel.setVisible(true);                 
        initializeTimeLine();                   // create a label for the 0.0 in the timeline
        double factor =50;
        LinkedList lListCopy = new LinkedList();
        lListCopy.addAll(process);
        Collections.sort(lListCopy, new Comparator <Process>() {      // Sort according to arrival time
            public int compare(Process p1, Process p2) {
                return Double.compare(p1.getBurst(),p2.getBurst());
            }
        });
        Iterator sortIt = lListCopy.iterator();
        sortIt.hasNext();
        Process y = (Process)sortIt.next();
        if(y.getBurst()<0.01)
            factor = 30000;
        else if(y.getBurst()<0.1)
            factor = 5000;
        else if (y.getBurst()<0.5)
            factor = 500;
        
        while(it.hasNext()){
            Process p =  (Process) it.next() ;
            b = new JButton(p.getName());
            b.setSize(0,50);
            b.setFont(new Font("Tahoma", Font.PLAIN, 12));
            b.setLocation(location,75);
            panel.add(b);

            size = 0;
            double sizeOfPeriod ;
            sizeOfPeriod = p.getBurst()*factor;
//            System.out.println(sizeOfPeriod);
            while (true)
            {
                if (state == 0){
                    p.setIsFinished(false);
                    double remTime = p.getBurst() - (time/1000.0 - p.getArrival()) ;
                    p.setRemainingTime(remTime);
                    pause();
                    lastProcess = p;
                    if (isNewProcess){
                        it = process.iterator();
                        isNewProcess = false;
                        break;
                    }
                }
                else if (state == 2){
                    lastProcess = p;
                    return ;
                }
                boolean x = draw(sizeOfPeriod,1000/factor);
                if ( x==false){
                    p.setRemainingTime(0.0);
                    p.setIsFinished(true);
                    lastProcess = p;
//                    process.remove(p);
                    break;
                }
                double tempp = 900/factor;
                try {
                    sleep((long)tempp);                  // It was supposed to be 20 but it was slow
                } catch (InterruptedException ex) {
                    Logger.getLogger(GanttChart.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                
        }
    }
    public synchronized void pause(){
        String ti = "Interrupted at : ";
        ti += String.format( "%.2f", time/1000.0 );
        interrupter = new JLabel(ti);
        panel.add(interrupter);
        interrupter.setLocation(25,165);
        interrupter.setSize(300,50);
        interrupter.setFont(new Font("Georgia", Font.BOLD, 15));
        try {
            wait();
            System.out.println("helloooooooo");
        } catch (InterruptedException ex) {
            Logger.getLogger(GanttChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public synchronized void resumeDrawing(){
        state = 1;                      // state is 1 indicates that thread is running
        if (interrupter != null)
            interrupter.setVisible(false);  // removing the interrupt label when resuming execution
        notify();                       // notify to awake the thread again after the wait
    }
    public synchronized void setState(int s){
        state = s;
    }
    public synchronized void setProcess(LinkedList p){
        process = p;
    }
    public synchronized void setPanel(JPanel p){
        panel = p;
    }
    public synchronized void clearPanel(){
        panel.removeAll();              // Clear the gantt chart
        state = 2;                      // state 2 is for interrupting the run function if it was running
        if (process != null){
            if (!process.isEmpty())         
                process.clear();            // clear the list of processes
            }
    }
    public synchronized int getStatus(){
        return state;
    }
    public synchronized LinkedList getProcesses(){
        return process;
    }
    public synchronized void setIsDrawn(boolean d){
        isDrawn = d;
    }
    public synchronized boolean isDrawn(){
        return isDrawn;
    }
    public synchronized boolean isNewProcess(){
        return isNewProcess;
    }
    public synchronized void setIsNewProcess(boolean s){
        isNewProcess = s;
    }
    public synchronized double getTime(){
        return time;
    }
}
