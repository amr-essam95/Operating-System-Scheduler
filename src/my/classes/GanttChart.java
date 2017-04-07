/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package my.classes;
import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton; 
import javax.swing.JLabel;
import javax.swing.JPanel; 

public class GanttChart extends Thread
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
    public boolean draw(double sizeOfPeriod){
  
        if (size > sizeOfPeriod){                                   // As long as the size is less than the intended size
            String ti = String.format( "%.2f", (time-20)/1000.0 );  // labels are in seconds
            time -= 20.0;
            JLabel l = new JLabel(ti);
            panel.add(l);
            l.setLocation(location,125);
            l.setSize(50,50);
            l.setFont(new Font("Tahoma", Font.PLAIN, 10));
            return false;
        }
        b.setSize(size,50);
        size+=1;
        time += 20;
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
        while(it.hasNext()){
            Process p =  (Process) it.next() ;
            b = new JButton(p.getName());
            b.setSize(0,50);
            b.setLocation(location,75);
            panel.add(b);
            size = 0;
            double sizeOfPeriod ;
            sizeOfPeriod = p.getBurst()*50;
            while (true)
            {
                if (state == 0)
                    pause();
                else if (state == 2)
                    return ;
                boolean x = draw(sizeOfPeriod);
                if ( x==false)
                    break;
                try {
                    sleep(18);                  // It was supposed to be 20 but it was slow
                } catch (InterruptedException ex) {
                    Logger.getLogger(GanttChart.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                
        }
    }
    public synchronized void pause(){
        String ti = "Interrupted at : ";
        ti += String.format( "%.2f", time/60.0 );
        interrupter = new JLabel(ti);
        panel.add(interrupter);
        interrupter.setLocation(25,200);
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
        if (!process.isEmpty())         
            process.clear();            // clear the list of processes
    }
    public synchronized int getStatus(){
        return state;
    }
    
}
