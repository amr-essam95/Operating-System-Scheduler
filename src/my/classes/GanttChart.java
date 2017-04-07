/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package my.classes;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
import javax.swing.JButton; 
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author amr
 */
public class GanttChart extends Thread
{
    private Thread t;
    private JPanel panel;
    double time =0;
    int size=0;
    int location = 50;
    Timer timer;
    JButton b;
    JLabel interrupter;
    int state = 1;
    // state is 0 if thread is  not running 
    // state is 1 if thread is running
    public GanttChart(JPanel p){

        panel = p;
//        panel.setTitle("Gantt Chart For The Processes");
        JLabel label = new JLabel("Gantt Chart For The Processes:");
        label.setLocation(30,30);
        label.setSize(300,50);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(label);
    }
    // duration is in seconds
    public synchronized void drawPeriod(int duration , String name){
        JButton b = new JButton(name);
        b.setSize(0,50);
        b.setLocation(location,100);
        panel.add(b);
        size = 0;
        timer = new Timer();
        int sizeOfPeriod ;
        sizeOfPeriod = duration*50;
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                if (size > sizeOfPeriod)
                    timer.cancel();


                if (size %25 ==0){
                    System.out.println(location);
                    double temp = time / 60;
                    String ti = Double.toString(temp);
                    JLabel l = new JLabel(ti);
                    panel.add(l);
                    l.setLocation(location,150);
                    l.setSize(50,50);
                    l.setFont(new Font("Tahoma", Font.PLAIN, 10));

                    time += 0.5;
                }
                b.setSize(size,50);
                size+=1;
                location = location +1;
                panel.setSize( location+300,180 );
                panel.setPreferredSize(panel.getSize());
            }
            };
         timer.schedule(myTask, 10, 10);
        }
    public void initializeTimeLine(){
        double t = 0;
        String s = Double.toString(t);
        JLabel initialPlace = new JLabel(s);
        panel.add(initialPlace);
        initialPlace.setLocation(location,150);
        initialPlace.setSize(50,50);
        initialPlace.setFont(new Font("Tahoma", Font.PLAIN, 10));
        time+= 60.0/50.0;
    }
    public boolean draw(int duration , String name,double sizeOfPeriod){
  
        if (size > sizeOfPeriod){
            
            String ti = String.format( "%.2f", time/60.0 );
            JLabel l = new JLabel(ti);
            panel.add(l);
            l.setLocation(location,150);
            l.setSize(50,50);
            l.setFont(new Font("Tahoma", Font.PLAIN, 10));
            return false;
        }
        if (size %25 ==0 && size>0){
//            String ti = Double.toString(time);
//            JLabel l = new JLabel(ti);
//            panel.add(l);
//            l.setLocation(location,150);
//            l.setSize(50,50);
//            l.setFont(new Font("Tahoma", Font.PLAIN, 10));
//
//            time += 0.5;
        }
        b.setSize(size,50);
        size+=1;
        time += 60/50;
        location = location +1;
        panel.setSize( location+300,180 );
        panel.setPreferredSize(panel.getSize());
        return true;
    }

    public void run(){
        initializeTimeLine();
        for (int i=0 ;i <10; i++){
            b = new JButton("p1");
            b.setSize(0,50);
            b.setLocation(location,100);
            panel.add(b);
            size = 0;
            double sizeOfPeriod ;
            sizeOfPeriod = 0.65*50;
            while (true)
            {
                if (state == 0)
                    pause();
                boolean x = draw(10,"p",sizeOfPeriod);
                if ( x==false)
                    break;
                try {
                    sleep(18);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GanttChart.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                
        }
    }
    public synchronized void pause(){
        System.out.println("1");
        String ti = "Interrupted at : ";
        ti += String.format( "%.2f", time/60.0 );
        interrupter = new JLabel(ti);
        panel.add(interrupter);
        interrupter.setLocation(50,200);
        interrupter.setSize(300,50);
        interrupter.setFont(new Font("Tahoma", Font.PLAIN, 10));
        try {
            wait();
            System.out.println("helloooooooo");
        } catch (InterruptedException ex) {
            Logger.getLogger(GanttChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public synchronized void resumeDrawing(){
        state = 1;
        interrupter.setVisible(false);
        interrupter = null;
        notify();
    }
    public synchronized void setState(int s){
        state = s;
    }
    
}
