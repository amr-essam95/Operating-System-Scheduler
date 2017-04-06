/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package my.classes;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
import javax.swing.JButton; 
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
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
    int size=0;
    public GanttChart(JPanel p){

        panel = p;
//        panel.setTitle("Gantt Chart For The Processes");
        JLabel label = new JLabel("Gantt Chart For The Processes:");
        label.setLocation(30,30);
        label.setSize(300,50);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(label);
    }
    // This function takes a vector containing name of the process and the duration
    public void run(LinkedList p,float totalDuration){
        int location =0;
//        System.out.println("hello amr");
//        JButton x = new JButton("p1");
//        x.setSize(50,50);
//        x.setLocation(50,100);
//        panel.add(x);
//        int size =0;
//        size=0;
//        Timer timer;
        
        for (int i=0;i<2000;i+=50)
        {
            JButton block1 = new JButton("P1");
            block1.setSize(50,50);
            block1.setLocation(50+i,100);
            location = 50 +i+100;
            panel.add(block1);
            panel.setSize(location+100,400);

        }
//        timer = new Timer(20,new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                size =size +1;
//                x.setSize(size,50);
//                
//            }
//         
//            
//        });
//       x.addChangeListener(new ChangeListener() {
//      public void stateChanged(ChangeEvent ev) {
//        System.out.println("ChangeEvent!");
//        if (x.getWidth()>50)
//            timer.stop();
//      }

//            
//    });

        

        
    }
}
