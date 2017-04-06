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
    float time =0;
    int size=0;
    int location = 50;
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
    public void drawPeriod(int duration , String name){
        JButton b = new JButton(name);
        b.setSize(0,50);
        b.setLocation(location,100);
        panel.add(b);
        size = 0;
        Timer timer = new Timer();
        int sizeOfPeriod ;
        sizeOfPeriod = duration*50;
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                if (size > sizeOfPeriod)
                    timer.cancel();
                if (size %25 ==0){
//                    System.out.println(location);
                    String ti = Float.toString(time);
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
                panel.setSize( location+300,300 );
                panel.setPreferredSize(panel.getSize());
            }
};
    timer.schedule(myTask, 10, 10);
    }
    // This function takes a vector containing name of the process and the duration
//    LinkedList p,float totalDuration
    public void run(){
//        panel.setLayout(new SpringLayout());
        drawPeriod(30,"P1");


    }
}
