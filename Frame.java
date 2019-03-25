 
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFrame;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import sun.audio.*;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
/**
 * This class designs the Frame of the game
 * @author Brent Drazner
 * @author Brandon Domash
 * @version 11.5.2
 * @since 2014-07-06
 */
public class Frame extends JFrame 
{
    private static Frame f;
    private String color;
    private String difficult;
    private Screen s;
    
    /**
     * Creates a new frame with a black background
     */
    public Frame()   
    {
        s = new Screen();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        
        setForeground(Color.BLACK);
        setBackground(Color.BLACK);
        
        setTitle("Snake");       
        
        init();
    }   
    /**
     * Adds the screen JPanel to the frame and proceeds to run the snake game. Centers the frame and does the dirty work to make the frame look good.
     */
    public void init()
    {
        setLayout(new GridLayout(1,1,0,0));  
        
        //Screen s = new Screen();        
        addKeyListener(s);
        add(s);  
        
        pack();
                
        
        setFocusable(true);
        requestFocusInWindow();
        setLocationRelativeTo(null);    //Centers frame on middle of screen        
        setVisible(true);        
        //s.run();     
      
        color = s.getCol();
        difficult = s.getDif();
    }
    /**
     * Resets the frame
     */
    public static void reset()      
    {
        //try{startSound();} catch(Exception e){System.out.println(e);}
        
        f = new Frame();        
    }
    /**
     * The main method
     */
    public static void main(String[] theory)
    throws Exception
    {
        
            
        
        
//         String gongFile = "elevator.wav";
//         InputStream in = new FileInputStream(gongFile);
//          
//         // create an audiostream from the inputstream
//         AudioStream audioStream = new AudioStream(in);
//          
//         // play the audio clip with the audioplayer class
//         AudioPlayer.player.start(audioStream);
//         //    
        //startSound();         
        
        
        //playSound();
        
                
          
          
        Frame f = new Frame();       
        
        
       
        
        //if(s.getPause())
        // {
        //    try{playSound();
        //   }catch(Exception e){System.out.println(e);} 
        //}
    }    
}


















