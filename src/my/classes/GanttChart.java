/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package my.classes;
import javax.swing.JButton; 
import javax.swing.JPanel; 
/**
 *
 * @author amr
 */
public class GanttChart {
    
    public void draw(JPanel jpanel){
        JButton block1 = new JButton("P1");
        block1.setSize(50,50);
        block1.setLocation(50,100);
        jpanel.add(block1);
    }
}
