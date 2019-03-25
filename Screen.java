/*
 * Thing to add/fix
 * 
 * Powerup for making snake shorter (chomp sound clip) (or so that you have the item and can press a button (maybe like 'i') to use it)
 * Mystery box dot
 * Jar file
 * 
 * cloud data storage
 * start menu GUI
 * classic mode dots
 * high scores reset
 * NETBEANS!!!
 * if you pause and quit and you have a highscore, maybe record the high score
 * 
 * If you mute and then the bclip changes (ie you get the star and the star music should start) and then you try and unmute it, it will crash
 */
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sun.audio.*;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;

import java.util.Random;
import java.util.*;
import javax.swing.JFrame;
import java.io.*;

import java.net.URL;
import javax.sound.sampled.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.text.*;
/**
 * This class designs the Screen for the Snake Game
 * @author Brent Drazner
 * @author Brandon Domash
 * @version 11.5.2
 * @since 2014-05-30
 */
public class Screen extends JPanel implements Runnable, KeyListener
{

    
    public static final int WIDTH=1410, HEIGHT=780;
    public static final Location topLeft = new Location(150,150);           //These Locations represent the corners of the grid and were referenced to draw certain components of the snake game
    public static final Location topRight = new Location(1260,150);
    public static final Location bottomLeft = new Location(150,630);
    public static final Location bottomRight = new Location(1260,630);
    public Thread thread;
    private boolean running = false;
    private int a = (int)(Math.random()*(1110/15))*15 + 150; 
    private int b = (int)(Math.random()*(480/15))*15 + 150;
    private ArrayList<Location> list = new ArrayList<Location>();
    private ArrayList<Location> allLocations = new ArrayList<Location>();
    private Location head = new Location();
    private Location dot = new Location(a,b);
    private ArrayList<Location> dots = new ArrayList<Location>();
    private String dotColor = "RED";
    private String lastpowerUp = "";
    private double powerupTimer = 0;
    private boolean down = true;
    private boolean up = false;
    private boolean left = false;
    private boolean right = false;
    private String text;
    private String text2;
    private String text3;
    private String text4;
    private String text5;    
    private String text6;
    private String text7;
    private int numDotsOnBoard = 0;
    private int scoreCount = 0;
    //private int totalScore = 0;
    private double timer = 0;
    private double timer2 = 0;
    private double timer3 = 0;
    private boolean pause = false;
    //private int defaultDiff = 1;
    private String[] difficultyOption = {"EASY","MEDIUM","HARD","YOU WILL NOT SURVIVE","GHOST","TROLL"};
    private String difficult;// = (String)JOptionPane.showInputDialog(null,"Choose your difficulty","Choose Difficulty",JOptionPane.QUESTION_MESSAGE,null,difficultyOption,difficultyOption[defaultDiff]);
    private String[] colorOption = {"GREEN","BLUE","CYAN","GRAY","MAGENTA","ORANGE","PINK","RED","WHITE","YELLOW"};
    private String color = "GREEN"; //= (String)JOptionPane.showInputDialog(null,"Select a color for your Snake","Choose Color",JOptionPane.QUESTION_MESSAGE,null,colorOption,colorOption[0]);
    private boolean specials;
    private boolean waitingToReset;       
    private boolean start;
    private Clip bclip;
    private Clip pclip;
    private Clip sclip;
    private Clip hclip,lclip,startClip;
    
    private int totalScore = 0;
    private int highscore;
    private String text8;
    private String hsname;
    private String hsdisplay;
    
    private BufferedImage image;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage image4;
    private BufferedImage image5;
    private BufferedImage image6;
    private BufferedImage image7,image8;
    private boolean mute = false;
    //private boolean hasFire = false;
    private String text9,text10;
    private BufferedImage image9;
    private BufferedImage image10,image11,image12,image13,image14;
    
    private boolean modeT = false;
    private boolean hasItem = false;
    private boolean redShellOn;
    private Location shellLoc;
    private double tempScore = 0;
    /**
     * Creates a screen for the snake game
     */
    public Screen()
    {       
        FileReader filereader;
        BufferedReader reader;
        PrintWriter writer;
        String line;
        int defaultColor;
        int defaultDiff;
        int defaultMode;
        int defaultOps;
        try{
            filereader = new FileReader("settings.txt");
            reader = new BufferedReader(filereader);
            
            //if(reader.readLine() !=null)
            line = reader.readLine();
            defaultMode = Integer.parseInt(line);
            line = reader.readLine();
            defaultDiff = Integer.parseInt(line);
            line = reader.readLine();
            defaultColor = Integer.parseInt(line);
            line = reader.readLine();
            defaultOps = Integer.parseInt(line);
            String[] options = {"Regular Mode", "Time Trial"};
            int n = -1;
            n = JOptionPane.showOptionDialog(null,"Choose your mode","Mode Selection",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[defaultMode]);
            if(n==0)
                modeT = false;
            else if(n==1)
            {
                modeT = true;
                difficult = "HARD";
                
                timer2=5.0;
            }
            else if(n==-1)
            {
                resetSettings();
                System.exit(0);
            }
            if(!modeT)
            {
                difficult = (String)JOptionPane.showInputDialog(null,"Choose your difficulty","Choose Difficulty",JOptionPane.QUESTION_MESSAGE,null,difficultyOption,difficultyOption[defaultDiff]);
                if(difficult == null)
                {    
                    resetSettings();
                    System.exit(0);
                }
            }
            int a = 2;
            int b = 0;
            int c = 0;
            int d = 0;
            writer = new PrintWriter(new File("settings.txt"));
            for(int i = 0;i<difficultyOption.length;i++)
            {
                if(difficultyOption[i].equalsIgnoreCase(difficult))
                {
                    a = i;
                    //writer.println(i);
                    //System.out.println(i);
                }
            }
            //On ghost the color will automatically be black so you shouldn't be allowed to select a color
            if(!difficult.equals("GHOST") && difficult != null && color != null )
            {
                color = (String)JOptionPane.showInputDialog(null,"Select a color for your Snake","Choose Color",JOptionPane.QUESTION_MESSAGE,null,colorOption,colorOption[defaultColor]);
                if(color == null)
                {
                    resetSettings();
                    System.exit(0);
                    
                }
                for(int i = 0;i<colorOption.length;i++)
                {
                    if(colorOption[i].equalsIgnoreCase(color))
                    {
                        //writer = new PrintWriter(new File("settings.txt"));
                        //writer.println(i);
                        //System.out.println(i);
                        b = i;
                    }
                }
                
                int restart = -1;
                String[] ops = {"Yes", "No"};
                String str1 = "Would you like to play with special power ups and challenges?\nIf yes is selected, different items will occasionally appear which have temporary special effects:\n\n\nBlue Shell: No boundaries\nStar: snake cannot die\nGolden Mushroom: increases head size of snake\nGhost: turns the snake invisible\nPow Block: allows the snake to travel through itself\nThree Mushrooms: double points\nBanana: reverses the keys";
                String str2 = "Would you like to play with special power ups and challenges?\nIf yes is selected, different items will occasionally appear which have temporary special effects:\n\n\nBlue Shell: No boundaries\nStar: snake cannot die, timer stops\nGolden Mushroom: adds 3 extra seconds to timer when collected\nIce Crystal: pauses timer\nPow Block: allows the snake to travel through itself\nThree Mushrooms: double points\nGreen Mushroom: increases head size of state";
                //int restart = JOptionPane.showOptionDialog(null,"Choose your mode","Mode Selection",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[defaultMode]);
                if(!modeT)
                    restart = JOptionPane.showOptionDialog(null,str1,"Specials?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,ops,ops[defaultOps]);
                    //restart = JOptionPane.showConfirmDialog(null,"Would you like to play with special power ups and challenges?\nIf yes is selected, different items will occasionally appear which have temporary special effects:\n\n\nBlue Shell: No boundaries\nStar: snake cannot die\nGolden Mushroom: increases head size of snake\nGhost: turns the snake invisible\nPow Block: allows the snake to travel through itself\nThree Mushrooms: double points\nBanana: reverses the keys" ,"Specials?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                else
                    restart = JOptionPane.showOptionDialog(null,str2,"Specials?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,ops,ops[defaultOps]);
                    //restart = JOptionPane.showConfirmDialog(null,"Would you like to play with special power ups and challenges?\nIf yes is selected, different items will occasionally appear which have temporary special effects:\n\n\nBlue Shell: No boundaries\nStar: snake cannot die, timer stops\nGolden Mushroom: adds 3 extra seconds to timer when collected\nIce Crystal: pauses timer\nPow Block: allows the snake to travel through itself\nThree Mushrooms: double points\nGreen Mushroom: increases head size of state" ,"Specials?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                
                if(restart == 1) 
                     specials = false;
                else if(restart ==0)                               
                     specials = true;
                else if(restart == -1)
                {
                    resetSettings();
                    System.exit(0);
                }
                
                d = restart;
            }
            if(modeT)
                c = 1;
            
            int[] ab = {c,a,b,d};
            for(int x: ab)
                writer.println(x);
            writer.close();
            //Makes sure the cancel option works
            if(color == null)
            {
                resetSettings();
                System.exit(0);
            }
        }
        catch(IOException e){}   
        try {                
           image = ImageIO.read(new File("Star.png"));
           image2 = ImageIO.read(new File("Mushroom.png"));
           image3 = ImageIO.read(new File("Red Mushroom.png"));
           image4 = ImageIO.read(new File("Blue Shell.png"));
           image5 = ImageIO.read(new File("Boo.png"));
           image6 = ImageIO.read(new File("Three Mushrooms.png"));
           image7 = ImageIO.read(new File("Pow Block.png"));
           image8 = ImageIO.read(new File("Banana.png"));
           image9 = ImageIO.read(new File("Ice Crystal.png"));
           image10 = ImageIO.read(new File("Unmute.png"));
           image11 = ImageIO.read(new File("Mute.png"));
           image12 = ImageIO.read(new File("Green Mushroom.png"));
           image13 = ImageIO.read(new File("Red Shell.png"));
           image14 = ImageIO.read(new File("Red Shell Big.png"));
        } catch (IOException ex) {
            // handle exception...
        }
        try{
            URL url = this.getClass().getClassLoader().getResource("Coin.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                         // Get a sound clip resource.
            bclip = AudioSystem.getClip();
            
            //URL url2 = this.getClass().getClassLoader().getResource("Relaxing Elevator Music.wav");
                        
            //AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(url2);
                        // Get a sound clip resource.
            pclip = AudioSystem.getClip();
            
                        // Open audio clip and load samples from the audio input stream.
            //clip2.open(audioIn2);    
        }catch(Exception e){}
        
        try{ 
                //bclip.stop();
                
                URL url = this.getClass().getClassLoader().getResource("Refreshing Elevator Music.wav");
                        
                 AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                        // Get a sound clip resource.
                 pclip = AudioSystem.getClip();
            
                        // Open audio clip and load samples from the audio input stream.
                //pclip.open(audioIn);    
                
                }catch(Exception e){}
       
        
        //Makes sure the cancel option works
        
        
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        //Creates a list of all locations on the grid
        for(int b = 150; b<630; b+= 15)
        {
            for(int a = 150; a< 1260; a+= 15)
            {
               allLocations.add(new Location(a,b));
            }
        }      
        startSnake();
        
        //Makes sure the Dot does not originally start on the snake
        boolean go = true;
        while(go)
        {
            if(snakeOnDot())
            {
                //dots.remove(0);
                
                randomize();
                dot.setA(a);
                dot.setB(b);      
                
                //dots.add(dot);
            }
            else
                go = false;
        }
        
        start();
    }
    /**
     * Resets the default settings of the game so that the difficulty is medium and the color is green
     */
    public void resetSettings()
    {
        PrintWriter writer;
        try
        {
            writer = new PrintWriter(new File("settings.txt"));
            int[] r = {0,2,0,0};
            for(int x:r)
                writer.println(x);
            writer.close();
        }
        catch(IOException e)
        {}
    }
    /**
     * Returns the level that the user is playing in text document form
     * @return the name of the text document that corresponds to the level of the user
     */
    public String getLevel()
    {
        if(modeT)
        {
            return "timetrail.txt";
        }
        else if(difficult.equals("EASY"))
        {
            return "easy.txt";
        }
        else if(difficult.equals("MEDIUM"))
        {
            return "medium.txt";
        }
        else if(difficult.equals("HARD"))
        {
            return "hard.txt";
        }
        else if(difficult.equals("YOU WILL NOT SURVIVE"))
        {
            return "youwillnotsurvive.txt";
        }
        else if(difficult.equals("GHOST"))
        {
            return "ghost.txt";
        }
        else if(difficult.equals("TROLL"))
        {
            return "troll.txt";
        }
        else
            return "easy.txt";
    }
    /**
     * Prompts the user if a high score is achieved. Records the name and score of the user, and rewrites and sorts the high score list.
     */
    public void highscore()
    {
        FileReader filereader;
        
        PrintWriter writer;
        ArrayList<PersonDate> hsp = new ArrayList<PersonDate>();
        

        try{
            if(totalScore>lowestHighscore() || (int)timer3>lowestHighscore())
            {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                Date date = new Date();
                String r = dateFormat.format(date);
                
                
                JOptionPane.showMessageDialog(null,"New high score!");
                String name = JOptionPane.showInputDialog("Please enter name");
                while(name == null)
                {
                   name = JOptionPane.showInputDialog("Please enter name");
                }
                while(name.length() > 18)
                    name = JOptionPane.showInputDialog(null,"Name too long\nPlease enter your name");
                while(name.length()==0)
                    name = JOptionPane.showInputDialog("Please enter name");
                 //if(name == null)
                 //{
                     //resetSettings();
                     //System.exit(0);
                     
                 //}
                String[] invalidchar = {"~","`","!","@","#","$","%","^","&","*","=","+","-","_",":",";","|","?","<",">","/","'","[","]","{","}","1","2","3","4","5","6","7","8","9","0",".",","};
                                                                                                                                    String[] bannedWords ={"fuck","shit","asshole","bitch","nigger","nigga","cunt","cock","douche","fag","penis","pussy","whore"};
                for(int i =0; i<invalidchar.length;i++)
                {
                    if(name.contains(invalidchar[i]))
                    {
                        name = JOptionPane.showInputDialog("Invalid name, please only use letters\nPlease enter name");
                        i = -1;
                    }
                }
                for(int i =0; i<bannedWords.length;i++)
                {
                    
                    if(name.replaceAll("\\s+","").toLowerCase().contains(bannedWords[i]))
                    {
                        name = JOptionPane.showInputDialog("No potty mouth\nPlease enter name");
                        i = -1;
                    }
                }
                PersonDate p1;
                if(!modeT)
                    p1 = new PersonDate(name,totalScore,r);
                else
                    p1 = new PersonDate(name,(int)timer3,r);
                filereader = new FileReader(getLevel());
                BufferedReader bufferedreader = new BufferedReader(filereader);
                
                
                
                String line = bufferedreader.readLine();
                
                while(line!=null)
                {
                    //hslist.add(Integer.parseInt(line.split(":")[1]));
                    hsp.add(new PersonDate(line.split(":")[0],Integer.parseInt(line.split(":")[1]),line.split(":")[2]));
                    line = bufferedreader.readLine();
                }
                hsp.add(p1);
                
                //sort it
                sort(hsp);
                
                
                //delete last thing
                hsp.remove(hsp.size()-1);
                
                //rewrite doc
                writer = new PrintWriter(new File(getLevel()));
                for(PersonDate p:hsp)
                {
                    writer.println(p);
                }
                writer.close();
                
                //displayhs();
            
            }
        }
        catch(IOException e)
        {
        }
    }
    /**
     * Helper method for the high scores. This takes in a list of Persons and will sort the list from highest to lowest score
     * @param arr The ArrayList of Persons to be sorted
     * @return The sorted list of Persons from highest to lowest score
     */
    public static ArrayList<PersonDate> sort(ArrayList<PersonDate> arr)
    {         
        for (int i = 0; i < arr.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.size(); j++)
                if (arr.get(j).compareTo(arr.get(index))>0) 
                    index = j;
               
            PersonDate smallerNumber = arr.get(index);  
            arr.set(index,arr.get(i));
            arr.set(i,smallerNumber);
        }
        return arr;
    }
    /**
     * Returns the lowest of the five highscores for the user's level
     * @return the lowest highscores for the user's level
     */
    public int lowestHighscore()
    {
        FileReader filereader;
        PrintWriter writer;
        int lowHighScore = 0;
        try{
            
            filereader = new FileReader(getLevel());
            BufferedReader bufferedreader = new BufferedReader(filereader);
            
            
            String finalLine ="";
            for(int i = 0;i<5;i++)
            {
                finalLine = bufferedreader.readLine();
            }
            lowHighScore = Integer.parseInt(finalLine.split(":")[1]);
            
        }
        catch(IOException e){}
        return lowHighScore;
    }
    /**
     * Displays the high scores in a neat and tidy JOptionPane
     */
    public void displayhs()
    {
        FileReader filereader;
        //System.out.println(dateFormat.format(date));
        try{
            filereader = new FileReader(getLevel());
            BufferedReader bufferedreader = new BufferedReader(filereader);
            
            String str = "High Scores\n";
            int hscount = 1;
            String line = bufferedreader.readLine();
            while(line!=null)
            {
                str += hscount +". " + line.split(":")[0] + ", " + line.split(":")[1] + ", " + line.split(":")[2] + "\n";
                hscount++;
                line = bufferedreader.readLine();
            }
            //JOptionPane.showMessageDialog(null,str);
            String[] options = {"OK","Reset"};
        
            int choice = JOptionPane.showOptionDialog(null,str,"High Scores",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
            if(choice == 0)
            {
                 //JOptionPane.showMessageDialog(null,"Game will resume");
            }
            else
             {    //Reset was chosen
                 int n = JOptionPane.showConfirmDialog(null,"Are you sure you would like to reset all high scores?","Are you sure?",JOptionPane.YES_NO_OPTION);
                 if(n == 0)
                 {
                     JOptionPane.showMessageDialog(null,"Scores will reset");
                     PrintWriter writer;
                     writer = new PrintWriter(new File(getLevel()));
                     for(int i=0;i<5;i++)
                        writer.println("null:0:n/a");
                     writer.close();
                 }
                 else
                     JOptionPane.showMessageDialog(null,"Scores will NOT reset");
            }
        }
        catch(IOException e)
        {
            System.out.println("There is an error");
        }
    }

    /**
     * Sets the Color and Difficulty
     * @param col The color to be set
     * @param dif The difficulty to be set
     */
    public void setOptions(String col, String dif)
    {
        difficult = dif;
        color = col;
    }
    /**
     * Returns the color of the Snake
     * @return the color of the Snake
     */
    public String getCol()
    {
        return color;
    }
    /**
     * Returns the difficulty of the game
     * @return the difficutly of the game
     */
    public String getDif()
    {
        return difficult;
    }
    /**
     * Determines whether or not the dot and the snake share a Location
     * @return true if the snake and the dot share a Location, otherwise false
     */
    public boolean snakeOnDot()
    {
        for(int i=0 ; i<list.size() ; i++)
        {
            if(dot.isEqual(list.get(i)))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns whether or not the snake head is touching the dot when the yellow power up is in effect
     * @return true if the wide snake head is on the dot. False if the snake head is not on the dot
     */
    public boolean wideSnakeOnDot()
    {
        if(up || down)
        {
            //Location p = new Location(head.getA()-30,head.getB());
            //Location m = new Location(head.getA()-15,head.getB());
            //Location n = new Location(head.getA()+15,head.getB());
            //Location o = new Location(head.getA()+30,head.getB());           
            //if(dot.isEqual(m) || dot.isEqual(n) || dot.isEqual(o) || head.isEqual(p))
            //    return true;
            
            if(Math.abs(head.getA()-dot.getA())<50 && (head.getB()-dot.getB() == 0))
            {
                return true;
            }            
        }     
        else if(left || right)
        {
            //Location p = new Location(head.getA(),head.getB()-30);
            //Location m = new Location(head.getA(),head.getB()-15);
            //Location n = new Location(head.getA(),head.getB()+15);
            //Location o = new Location(head.getA(),head.getB()+30);            
            //if(dot.isEqual(m) || dot.isEqual(n) || dot.isEqual(o) || head.isEqual(p))
            //    return true;
            
            
            if(Math.abs(head.getB()-dot.getB())<50 && (head.getA()-dot.getA() == 0))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * Starts the snake game
     */
    public void start()
    {
        text = "Game will start in: 3";
        text2 = "Total Score: ";
        text3 = "Dots Collected: ";
        if(!modeT)
            text4 = "Time Elapsed: ";
        else
            text4 = "Time Remaining: 5.0 seconds ";
        text5 = "Press the spacebar to pause";
        text8 = "Press h to see high scores";
        text9 = "Press m to mute";
        text6 = "Power Up: " + " Time Remaining: ";
        
        //text8 = hsdisplay();
        running = true;
        thread = new Thread(this, "Game Loop");
        thread.start();
    }
    /**
     * Creates new, random numbers. Used to determine the new location of the dot
     */
    public void randomize()
    {
        a = (int)(Math.random()*(1110/15))*15 + 150; 
        b = (int)(Math.random()*(480/15))*15 + 150;       
        
        if(!specials || difficult.equals("GHOST"))
        {
            dotColor = "RED";
        }
        else
        {        
                double c = Math.random();   
                if(c < .65 || powerupTimer > 0 || hasItem)
                {
                    dotColor = "RED";
                }
                else if(c>=.65 && c<.70 )
                
                {
                    //dotColor = "RED SHELL";
                    dotColor = "RED";
                }
                else if(c>=.70 && c<.75)
                {
                    dotColor = "TROLL";
                }
                else if(c>=.75 && c <=.8)
                {
                    dotColor = "DOUBLE";
                }
                else if(c>.8 && c<=.85 && powerupTimer <= 0)
                {
                    dotColor = "BLUE";            
                }
                else if(c>.85 && c<=.90 && powerupTimer <= 0)
                {
                    if(!difficult.equalsIgnoreCase("YOU WILL NOT SURVIVE"))
                    {    
                        if(modeT)
                            dotColor = "CRYSTAL";
                        else
                            dotColor = "BLACK";
                    }
                    else
                        dotColor = "RED";
                }
                else if(c>.90 && c<=.93 && powerupTimer <= 0)
                {
                    dotColor = "RAINBOW";            
                }
                else if(c>.93 && c<=.97 && powerupTimer <= 0)
                {
                    dotColor = "YELLOW";            
                }
                else if(c>.97 && c<=1.0 && powerupTimer <= 0)
                {
                    dotColor = "WHITE";            
                }          
                else if(c>1.0 && c<=1.0 && powerupTimer <= 0)
                {
                    dotColor = "PURPLE";
                }            
        }
    }
    /**
     * Determines what happens when each key is pressed. The directional arrows will change the direction of the snake,
     * while the space bar will pause the game
     * @param e The key that is pressed
     */
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !(lastpowerUp.equals("BLACK")&& powerupTimer>0) && start)
        {
            if(!pause)
            {
                pause = true;
                repaint(); 
                bclip.stop(); 
                if(powerupTimer > 0 && (lastpowerUp.equals("RAINBOW")))
                    sclip.stop();
                //pauseSong();
                if(!mute)
                {
                    pclip.start();
                    pclip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                
                int unpause = JOptionPane.showConfirmDialog(null,"You have paused the game\nWould you like to continue?","Pause",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                
                if(unpause == JOptionPane.YES_OPTION) 
                {
                    pause = false;  
                    if(!mute)
                        pclip.stop();
                    if(!mute && powerupTimer > 0 && (lastpowerUp.equals("RAINBOW")))
                        sclip.start();
                    else
                    {
                        if(!mute)
                        {
                            bclip.start();
                            bclip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                    }
                }
                else
                {
                    resetSettings();
                    System.exit(0);
                    
                }
            }             
        }
        if(e.getKeyCode() == KeyEvent.VK_Z && hasItem)
        {
            hasItem = false;
            redShellOn = true;
            shellLoc = new Location(head.getA(),head.getB());
            //algorithm here;
        }
        if(e.getKeyCode() == KeyEvent.VK_H && !(lastpowerUp.equals("BLACK")&& powerupTimer>0) && start)
        {
            pause = true;
            repaint(); 
            bclip.stop(); 
            if(powerupTimer > 0 && (lastpowerUp.equals("RAINBOW")))
                sclip.stop();
                //pauseSong();
            pclip.start();
            pclip.loop(Clip.LOOP_CONTINUOUSLY);
            displayhs();
            int unpause = JOptionPane.showConfirmDialog(null,"Would you like to continue?","Pause",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            
            if(unpause == JOptionPane.YES_OPTION) 
            {
                pause = false;  
                pclip.stop();
                if(powerupTimer > 0 && (lastpowerUp.equals("RAINBOW")))
                    sclip.start();
                else
                {
                    bclip.start();
                    bclip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
            else
            {
                resetSettings();
                System.exit(0);
                
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_M)
        {
            if(!mute)
            {
                if(!start)
                {
                    startClip.stop();
                }
                if(powerupTimer>0 && (lastpowerUp.equals("RAINBOW")))
                    sclip.stop();
                else
                    bclip.stop();
                    
                mute = true; 
            }
            else
            {
                
                if(powerupTimer>0 && (lastpowerUp.equals("RAINBOW")))
                    sclip.start();
                else
                    bclip.start();
                    
                mute = false;
            }
        }
        if(!outOfBounds() && !crash() && timer>.1 && !difficult.equals("TROLL") && !(lastpowerUp.equals("TROLL") && powerupTimer >0 && !modeT))
        {
            if(e.getKeyCode() == KeyEvent.VK_UP && !down) //up = 38
            {
                up = true;
                down = false;
                left = false;
                right = false;
              
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN && !up) //down = 40
            {
                down = true;
                up = false;
                left = false;
                right = false;
                
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT && !right) //left = 37
            {
                left = true;
                up = false;
                down = false;
                right = false;
                                
            }
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !left) //right = 39
            {
                right = true;
                up = false;
                down = false;
                left = false;
               
            }             
                    
            timer = 0;
        }
        else if(!outOfBounds()  && !crash() && timer>.1) //Switches all the keys because troll mode was selected
        {
            if(e.getKeyCode() == KeyEvent.VK_UP && !up) //up = 38
            {
                up = false;
                down = true;
                left = false;
                right = false;
              
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN && !down) //down = 40
            {
                                
                down = false;
                up = true;
                left = false;
                right = false;
               
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT && !left) //left = 37
            {
                left = false;
                up = false;
                down = false;
                right = true;
                
            }
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !right) //right = 39
            {
                right = false;
                up = false;
                down = false;
                left = true;
                
            }             
                     
            timer = 0;
        }        
    }
    /**
     * Would determine what to do when a certain key is released. However, in the Snake game nothing happens when a key is released,
     * so this method does absolutely nothing. The method is needed for the keyListener interface, however.
     * @param e The key that is being released
     */
    public void keyReleased(KeyEvent e)
    {
     
    }
    /**
     * Would determine what to do when a certain key is typed. However, in the Snake game nothing happens when a key is typed,
     * so this method does absolutely nothing. The method is needed for the keyListener interface, however.
     * @param e The key that is being typed
     */
    public void keyTyped(KeyEvent e)
    {
        
    }    
    /**
     * Calls the move method and creates the timer functionality
     */
    public void tick()
    {
       
       move();
       if(redShellOn)
           moveShell();
       timer += .1;
       
       //adjusts the time based on the difficulty level
       if(!pause)
       {
           if(difficult.equals("EASY"))
           {
                if(lastpowerUp.equals("RAINBOW") && powerupTimer > 0) 
                {
                    timer2 += .045;
                    powerupTimer -=.045;
                }
                else
                {
                    timer2 += .067;
                    if(powerupTimer > 0)
                    {
                        powerupTimer -= .067;
                    }
                }
           }
           else if(modeT)
           {
               if(snakeOnDot() || wideSnakeOnDot())
               {
                   tempScore = timer2;
               }
               if(!((lastpowerUp.equals("RAINBOW")||lastpowerUp.equals("CRYSTAL")) && powerupTimer>0))
                    timer2 -= .025;
               //add here
               
               //System.out.println("Temp score: " + tempScore + "\nTimer2: " + timer2 + "\n");
               if(tempScore -10 +3 < timer2)
                   timer3 +=.025;
               if(lastpowerUp.equals("DOUBLE") && powerupTimer > 0)
               {
                   if(tempScore -20 + 6 < timer2)
                       timer3 += .025;
               }
               if(powerupTimer > 0)
               {
                   powerupTimer -= .025;
               }
           }
           else if(difficult.equals("MEDIUM")||difficult.equals("GHOST")|| difficult.equals("TROLL"))
           {
                timer2 += .045;
                if(powerupTimer > 0)
                {
                    powerupTimer -= .045;
                }
            }
           else if(difficult.equals("HARD"))
           {
                timer2 += .03;
                if(powerupTimer > 0)
                {
                    powerupTimer -= .03;
                }
            }
           else if(difficult.equals("YOU WILL NOT SURVIVE"))
           {
                timer2 += .01;
                if(powerupTimer > 0)
                {
                    powerupTimer -= .01;
                }
            }
                
                
        }       
    }
    /**
     * Draws over the Snake in the locations that are no longer in use, using the color of the gridlines,
     * effectively erasing the old locations of the Snake
     * @param, g The Graphics used to draw the snake
     * @param a The x coordinate to be erased
     * @param b The y coordinate to be erased
     */
    public void erase(Graphics g, int a, int b)
    {
        g.setColor(Color.BLACK);
        g.fillRect(a,b,15,15); 
        
        
        Color transGray = new Color(55,55,55);
        g.setColor(transGray);
        
        g.drawLine(a,b,a,b+15);
        g.drawLine(a,b,a+15,b);
        g.drawLine(a+15,b,a+15,b+15);
        g.drawLine(a,b+15,a+15,b+15);        
    } 
    /**
     * Moves the snake based off the direction that the Snake is going
     */
    public void move()
    {
        if(!pause)
        {
            if(up)
            {             
               list.remove(0);                             
               head = new Location(head.getA(),head.getB()-15);               
               list.add(head);          
            }
            else if(down)
            {
               list.remove(0);                          
               head = new Location(head.getA(),head.getB()+15);               
               list.add(head);              
            }
            else if(left)
            {
               list.remove(0);                          
               head = new Location(head.getA()-15,head.getB());         
               list.add(head);
            }
            else if(right)
            {
               list.remove(0);                 
               head = new Location(head.getA()+15,head.getB());                
               list.add(head);              
            }
                                  
            repaint();
        }
    }
    public void moveShell()
    {
        
        boolean shellUp = false, shellDown = false, shellLeft = false, shellRight = false;
        if(up)
        {
            shellUp = true;
            shellDown = false;
            shellLeft = false;
            shellRight = false;
        }
        else if(down)
        {
            shellDown = true;
            shellUp = false;
            shellLeft = false;
            shellRight = false;
        }
        else if(left)
        {
            shellLeft = true;
            shellDown = false;
            shellUp = false;
            shellRight = false;
        }
        else if(right)
        {
            shellRight = true;
            shellLeft = false;
            shellDown = false;
            shellUp = false;
        }
        if(!pause)
        {
            if(shellUp)
            {             
               shellLoc = new Location(shellLoc.getA(),shellLoc.getB()-15);         
            }
            else if(shellDown)
            {                       
               shellLoc = new Location(shellLoc.getA(),shellLoc.getB()+15);                         
            }
            else if(shellLeft)
            {                
               shellLoc = new Location(shellLoc.getA()-15,shellLoc.getB());         
            }
            else if(shellRight)
            {         
               shellLoc = new Location(shellLoc.getA()+15,shellLoc.getB());                         
            }
                                  
            repaint();
        }
    }
    
    /**
     * Returns the number of segments in the Snake
     * @return the number of segments in the Snake
     */
    public int getSnakeSize()
    {
        return list.size();
    }
    /**
     * Creates a new Snake, which contains 3 segments and whose head starts at the Location (705,195)
     */
    public void startSnake()
    {
        int thisA = (150+1260)/2;           //705
        int thisB = (150+630)/2 -195;       //195
        
        for(int i=0 ; i<3; i++)
        {
            list.add(new Location(thisA,thisB));
            //drawSegment(g,thisA,thisB);
            thisB += 15;
            
        }
        head.setA(thisA);
        head.setB(thisB-15); //to adjust for the extra +15 at the end of the last for loop run through        
    }    
    /**
     * Draws a segment of the snake, using the color chosen by the user, or the color of the grid lines if the user has selected "Ghost" mode
     * @param g Graphics used to draw the segments
     * @param a The x coordinate of the segment to be drawn
     * @param b The y coordinate of the segment to be drawn
     */
    public void drawSegment(Graphics g, int a, int b)
    {        
        //Draws the outline of the snake based on the selected color
        
        if(lastpowerUp.equals("BLACK") && powerupTimer > 0)
        {
            Color transGray = new Color(55,55,55);
            g.setColor(transGray);
            //g.setColor(Color.BLACK);
        }
        else if(!lastpowerUp.equals("RAINBOW"))
        {
            if(difficult.equals("GHOST"))
                g.setColor(new Color(55,55,55));
            else if(color.equals("WHITE"))
                g.setColor(Color.WHITE); 
            else if(color.equals("GREEN"))
                g.setColor(Color.GREEN);  //Green outline
            else if(color.equals("BLUE"))
                g.setColor(Color.BLUE); 
            else if(color.equals("CYAN"))
                g.setColor(Color.CYAN);         
            else if(color.equals("GRAY"))
                g.setColor(Color.GRAY);
            else if(color.equals("YELLOW"))
                g.setColor(Color.YELLOW); 
            else if(color.equals("MAGENTA"))
                g.setColor(Color.MAGENTA); 
            else if(color.equals("ORANGE"))
                g.setColor(Color.ORANGE);    
            else if(color.equals("PINK"))
                g.setColor(Color.PINK); 
            else if(color.equals("RED"))
                g.setColor(Color.RED); 
        }
        else if(lastpowerUp.equals("RAINBOW") && powerupTimer > 0)
        {
            int x = (int)(Math.random()*250);
            int y = (int)(Math.random()*250);
            int z = (int)(Math.random()*250);
            
            Color randomColor = new Color(x,y,z);
            g.setColor(randomColor);
        }
  
        g.drawLine(a,b,a,b+15);
        g.drawLine(a,b,a+15,b);
        g.drawLine(a+15,b,a+15,b+15);
        g.drawLine(a,b+15,a+15,b+15);         
    }
    /**
     * Draws a head of the snake, using the color chosen by the user, or black if the user has selected "Ghost" mode
     * @param g Graphics used to draw the head
     * @param a The x coordinate of the head to be drawn
     * @param b The y coordinate of the head to be drawn
     */
    public void drawHead(Graphics g, int a, int b)
    {
        //draws the outline of the head of the snake based on the selected color
        
        if(lastpowerUp.equals("BLACK") && powerupTimer > 0)
        {
            Color transGray = new Color(55,55,55);
            g.setColor(transGray);
        }
        else if(!lastpowerUp.equals("RAINBOW"))
        {            
            if(difficult.equals("GHOST"))
                g.setColor(Color.BLACK);
            else if(color.equals("WHITE"))
                g.setColor(Color.WHITE); 
            else if(color.equals("GREEN"))
                g.setColor(Color.GREEN);  //Green outline
            else if(color.equals("BLUE"))
                g.setColor(Color.BLUE); 
            else if(color.equals("CYAN"))
                g.setColor(Color.CYAN);         
            else if(color.equals("GRAY"))
                g.setColor(Color.GRAY);
            else if(color.equals("YELLOW"))
                g.setColor(Color.YELLOW); 
            else if(color.equals("MAGENTA"))
                g.setColor(Color.MAGENTA); 
            else if(color.equals("ORANGE"))
                g.setColor(Color.ORANGE);    
            else if(color.equals("PINK"))
                g.setColor(Color.PINK); 
            else if(color.equals("RED"))
                g.setColor(Color.RED); 
        }
        else if(lastpowerUp.equals("RAINBOW") && powerupTimer > 0)
        {
            int x = (int)(Math.random()*250);
            int y = (int)(Math.random()*250);
            int z = (int)(Math.random()*250);
            
            Color randomColor = new Color(x,y,z);
            g.setColor(randomColor);
        }
        
               
        if((lastpowerUp.equals("YELLOW") && powerupTimer > 0 && !modeT) || (modeT && lastpowerUp.equals("TROLL") && powerupTimer >0))
        {
            if(up || down)
            {
                g.drawLine(a-37,b,a+52,b);
                g.drawLine(a-37,b,a-37,b+15);
                g.drawLine(a+52,b,a+52,b+15);
                g.drawLine(a-37,b+15,a+52,b+15);
            }
            else if(left || right)
            {
                g.drawLine(a,b-37,a+15,b-37);
                g.drawLine(a,b-37,a,b+52);
                g.drawLine(a+15,b-37,a+15,b+52);
                g.drawLine(a,b+52,a+15,b+52);
            }
        }
        else
        {
            g.drawLine(a,b,a,b+15);
            g.drawLine(a,b,a+15,b);
            g.drawLine(a+15,b,a+15,b+15);
            g.drawLine(a,b+15,a+15,b+15);
        }         
        
    }
    /**
     * Adds a segment to the end of the Snake. Is called when the Snake collects a dot, making the length of the Snake longer
     */
    public void addSegment()
    {       
        Location tail = new Location();
        
        if(up)
        {
            tail.setA(list.get(0).getA());
            tail.setB(list.get(0).getB()+15);
        }
        else if(down)
        {
            tail.setA(list.get(0).getA());
            tail.setB(list.get(0).getB()-15);
        }
        else if(left)
        {
            tail.setA(list.get(0).getA()+15);
            tail.setB(list.get(0).getB());
        }
        else if(right)
        {
            tail.setA(list.get(0).getA()-15);
            tail.setB(list.get(0).getB());
        }        
        
        ArrayList<Location> temp = new ArrayList<Location>();
        
                
        temp.add(tail);
        
        for(int i=0 ; i<list.size() ; i++)
        {
            temp.add(list.get(i));
        }
        list = temp;                
    }
    /**
     * Draws the entire snake, using the locations of the snake held within list
     * @param g Graphics used to draw the snake
     */
    public void drawSnake(Graphics g)
    {
        for(int i=0 ; i<list.size()-1 ; i++)
        {
            drawSegment(g,list.get(i).getA(),list.get(i).getB());            
        }
        drawHead(g,head.getA(),head.getB());
    }
    /**
     * Draws the outside border of the game in green
     * @param g Graphics used to draw the border
     */
    public void drawGrid(Graphics g)
    {
        //g.setColor(Color.GREEN);
        
        
        if(lastpowerUp.equals("RAINBOW") && powerupTimer > 0)
        {
            int x = (int)(Math.random()*250);
            int y = (int)(Math.random()*250);
            int z = (int)(Math.random()*250);
            
            Color randomColor = new Color(x,y,z);
            g.setColor(randomColor);   
            
            g.drawLine(150,150,1260,150);
            g.drawLine(150,150,150,630);
            g.drawLine(150,630,1260,630);
            g.drawLine(1260,150,1260,630);
        }
        else
        {           
            if(lastpowerUp.equals("BLUE") && powerupTimer > 0)
            {
                Color transGray = new Color(55,55,55);
                g.setColor(transGray);   
            }            
            else if(difficult.equals("GHOST"))
                g.setColor(Color.WHITE);
            else if(color.equals("GREEN"))
                g.setColor(Color.GREEN);  //Green outline
            else if(color.equals("BLUE"))
                g.setColor(Color.BLUE); 
            else if(color.equals("CYAN"))
                g.setColor(Color.CYAN); 
            else if(color.equals("WHITE"))
                g.setColor(Color.WHITE); 
            else if(color.equals("GRAY"))
                g.setColor(Color.GRAY);
            else if(color.equals("YELLOW"))
                g.setColor(Color.YELLOW); 
            else if(color.equals("MAGENTA"))
                g.setColor(Color.MAGENTA); 
            else if(color.equals("ORANGE"))
                g.setColor(Color.ORANGE);    
            else if(color.equals("PINK"))
                g.setColor(Color.PINK); 
            else if(color.equals("RED"))
                g.setColor(Color.RED); 
            
            
            g.drawLine(150,150,1260,150);
            g.drawLine(150,150,150,630);
            g.drawLine(150,630,1260,630);
            g.drawLine(1260,150,1260,630);
        }
    }
    /**
     * Sets a timer, used to restrict how often the user can change the direction of the snake. 
     */
    public void timer()
    {
        timer += .1;       
    }
    /**
     * Draws the dow in a random location
     * @param a The random horizontal coordinate of the dot
     * @param b The random vertical coodinate of the dot
     */
    public void drawDot(Graphics g, int a, int b)
    {
        if(dotColor.equals("RED"))
        {
            if(lastpowerUp.equals("YELLOW") && powerupTimer >0 && modeT)
                g.drawImage(image2,a+1,b+1,null);
            //g.setColor(Color.RED);
            
            else if(difficult.equals("GHOST"))
                g.drawImage(image9,a+1,b+1,null);
            else
                g.drawImage(image3,a+1,b+1,null);
            
        }
        else if(dotColor.equals("BLUE"))
        {
            //g.setColor(Color.BLUE); 
            g.drawImage(image4,a+1,b+1,null);
        }
        else if(dotColor.equals("CRYSTAL"))
        {
            g.drawImage(image9,a+1,b+1,null);          
        }
        else if(dotColor.equals("YELLOW"))
        {
            //g.setColor(Color.YELLOW)
            g.drawImage(image2,a+1,b+1,null);
        }
        else if(dotColor.equals("BLACK"))
        {
            //g.setColor(Color.BLACK);  
            g.drawImage(image5,a+1,b+1,null);
        }
        else if(dotColor.equals("WHITE"))
        {
            g.setColor(Color.WHITE);
        }
        else if(dotColor.equals("DOUBLE"))
        {
            g.drawImage(image6,a+1,b+1,null);
        }
        else if(dotColor.equals("TROLL"))
        {
            if(!modeT)
                g.drawImage(image8,a+1,b+1,null);
            else
               g.drawImage(image12,a+1,b+1,null); 
        }
        else if(dotColor.equals("RED SHELL"))
        {
            g.drawImage(image13,a+1,b+1,null); 
        }
        else if(dotColor.equals("RAINBOW"))
        {
            int x = (int)(Math.random()*250);
            int y = (int)(Math.random()*250);
            int z = (int)(Math.random()*250);
            
            Color randomColor = new Color(x,y,z);
            g.setColor(randomColor);          
            //for(int i =0; i<15;i++)
                //g.drawArc(a,b,i,i,0,360);
            g.drawImage(image,a+1,b+1,null);
        }
        
        
        //if(!(dotColor.equals("RAINBOW") || dotColor.equals("YELLOW") || dotColor.equals("RED") || dotColor.equals("BLUE") || dotColor.equals("BLACK") || dotColor.equals("DOUBLE") || dotColor.equals("WHITE") || dotColor.equals("TROLL")))
        //{
            //g.fillOval(a,b,15,15);
            //g.setColor(Color.GREEN);
            //g.drawArc(a,b,15,15,0,360);
        //}
        
        if(dotColor.equals("WHITE"))
        {
            g.drawImage(image7,a+1,b+1,null);
//             g.setColor(Color.BLACK);
//             g.drawLine(a,b,a+15,b+15);
//             g.drawLine(a+15,b,a,b+15);
        }
        
        numDotsOnBoard += 1;
    }
    /**
     * Paints the screen
     * @param g The Graphics used to paint the screen
     */
    public void paint(Graphics g)
    {              
        super.paintComponent(g);    //Prevents bugs in the top left corner
        

        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);    
                
        
        //Draws the light gray gridlines
        
        Color transGray = new Color(55,55,55);
        g.setColor(transGray); 
       
         for (int i = 150; i <= 630; i+= 15)
             g.drawLine(150, i, 1260, i);
         for (int i = 150; i <= 1260; i+= 15)
             g.drawLine(i, 150, i, 630);
        
         if(lastpowerUp.equals("RED SHELL") && hasItem)
        {
            g.setFont(new Font("Times New Roman",Font.BOLD, 20));
            g.setColor(Color.GREEN);
            g.drawImage(image14,600,50,null);
            //g.setColor(Color.WHITE);
           
            text10 = "Item";
            g.drawString(text10, 598, 40);
            g.setFont(new Font("Times New Roman",Font.BOLD, 13));
            g.drawString("Press 'z' to use",580,97);
            g.setFont(new Font("Times New Roman",Font.BOLD, 40));
            //g.setColor(Color.WHITE);
        }   
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman",Font.BOLD, 40));
        g.drawString(text,350,100);
        
        g.setFont(new Font("Calibri",Font.PLAIN, 20));
        g.drawString(text2, 800, 30);
        g.drawString(text3, 800, 75);
        g.drawString(text4, 800, 120);
        g.drawString(text5, 550, 675);  
        g.drawString(text8, 150, 675);
        if(mute)
        {
            text9 = "Press m to unmute";
            g.drawImage(image11,1190,30,null);
        }
        else
        {
            text9 = "Press m to mute";
            g.drawImage(image10,1190,30,null);
        }
        g.drawString(text9, 950, 675);
        
        
        
        g.setFont(new Font("Calibri",Font.PLAIN, 25));
        if(powerupTimer > 0)
        {        
            text6 = "Power Up: ";
            if(lastpowerUp.equals("BLUE"))
                text6 += "No Bounds";
            else if(lastpowerUp.equals("CRYSTAL"))
                text6 += "Timer Paused";
            else if(lastpowerUp.equals("RAINBOW"))
                text6 += "Invincibility";
            else if(lastpowerUp.equals("YELLOW"))
            {
                if(!modeT)
                    text6 += "Larger Snake Head";
                 else
                    text6 += "Additional Time Added";
            }
            else if(lastpowerUp.equals("DOUBLE"))
                text6 += "Double Points";
            else if(lastpowerUp.equals("BLACK"))
            {
                text6 += "Ghost";
                if(!modeT)
                {
                    text5 = "No pausing now MWAHAHA";
                    text8 = "";
                }
            }
            else if(lastpowerUp.equals("WHITE"))
                text6 += "No Crashing";
            else if(lastpowerUp.equals("TROLL"))
            {
                if(!modeT)
                    text6 += "Switched Keys";
                else
                    text6 += "Larger Snake Head";
            }
           
            text7 = "Time Remaining: " + ((int)(powerupTimer*10))/10.0 + " seconds";
                
            g.drawString(text6, 100, 45);
            g.drawString(text7, 100, 105);  
            
            if(redShellOn)
                g.drawImage(image13,shellLoc.getA(),shellLoc.getB(),null);
        }
        else
        {
            text5 = "Press the spacebar to pause";
            text8 = "Press h to see high scores";
        }
                    
        if(list.size()>0)
            erase(g,list.get(0).getA(), list.get(0).getB());
        
        
        drawGrid(g);
        drawSnake(g);
            
        
        //Draws the dot
        drawDot(g,dot.getA(),dot.getB());       
        
        //for(int i=0 ; i<dots.size() ; i++)
        //{
        //    drawDot(g,dots.get(i).getA(),dots.get(i).getB());
        //}
        
       
        
        //if(dot.isEqual(head) || (lastpowerUp.equals("YELLOW") && powerupTimer > 0 && wideSnakeOnDot()))
        //{
        //    if(lastpowerUp.equals("PURPLE") && powerupTimer > 0)
        //    {
        //         randomize();
        //         Location dot2 = new Location(a,b);
        //         drawDot(g,dot.getA(),dot2.getB());
        //         
        //         randomize();
        //         Location dot3 = new Location(a,b);
        //         drawDot(g,dot.getA(),dot3.getB());
        //    }
        //}
        
     
        
        
        //drawSnake(g);
        
        //This mess turns the snake white after you die in ghost mode            
        if(outOfBounds() || crash() || pause)
        {
          if(difficult.equals("GHOST"))
          {
                for(int i=0 ;i< list.size()-1 ;i++)
                {
                    //drawSegment(g, list.get(i).getA(),list.get(i).getB());
                    int a = list.get(i).getA();
                    int b = list.get(i).getB();
                    g.setColor(Color.WHITE);        
                    
                    g.drawLine(a,b,a,b+15);
                    g.drawLine(a,b,a+15,b);
                    g.drawLine(a+15,b,a+15,b+15);
                    g.drawLine(a,b+15,a+15,b+15);
                }
                int a = head.getA();
                int b = head.getB();
                g.setColor(Color.GRAY);        
                    
                g.drawLine(a,b,a,b+15);
                g.drawLine(a,b,a+15,b);
                g.drawLine(a+15,b,a+15,b+15);
                g.drawLine(a,b+15,a+15,b+15);
          }
          else
          {
              
              for(int i=0 ;i< list.size() ;i++)
                {                    
                    int a = list.get(i).getA();
                    int b = list.get(i).getB();
                    
                    if(!(powerupTimer>0 && lastpowerUp.equals("RAINBOW")))
                    {              
                        if(color.equals("GREEN"))
                            g.setColor(Color.GREEN);  //Green outline
                        else if(color.equals("BLUE"))
                            g.setColor(Color.BLUE); 
                        else if(color.equals("CYAN"))
                            g.setColor(Color.CYAN); 
                        else if(color.equals("WHITE"))
                            g.setColor(Color.WHITE); 
                        else if(color.equals("GRAY"))
                            g.setColor(Color.GRAY);
                        else if(color.equals("YELLOW"))
                            g.setColor(Color.YELLOW); 
                        else if(color.equals("MAGENTA"))
                            g.setColor(Color.MAGENTA); 
                        else if(color.equals("ORANGE"))
                            g.setColor(Color.ORANGE);    
                        else if(color.equals("PINK"))
                            g.setColor(Color.PINK); 
                        else if(color.equals("RED"))
                            g.setColor(Color.RED); 
                    }
                    
                    g.fillRect(a,b,15,15);                   
                    
                }
              for(int i=0 ; i<list.size() ; i++)
              {
                  int a = list.get(i).getA();
                  int b = list.get(i).getB();
                  
                  g.setColor(Color.BLACK);
                  g.drawLine(a,b,a,b+15);
                  g.drawLine(a,b,a+15,b);
                  g.drawLine(a+15,b,a+15,b+15);
                  g.drawLine(a,b+15,a+15,b+15);
              }
              if((lastpowerUp.equals("YELLOW") && powerupTimer>0 && !modeT) || (lastpowerUp.equals("TROLL") && powerupTimer>0 && modeT) )
              {
                  if(color.equals("GREEN"))
                        g.setColor(Color.GREEN);  //Green outline
                    else if(color.equals("BLUE"))
                        g.setColor(Color.BLUE); 
                    else if(color.equals("CYAN"))
                        g.setColor(Color.CYAN); 
                    else if(color.equals("WHITE"))
                        g.setColor(Color.WHITE); 
                    else if(color.equals("GRAY"))
                        g.setColor(Color.GRAY);
                    else if(color.equals("YELLOW"))
                        g.setColor(Color.YELLOW); 
                    else if(color.equals("MAGENTA"))
                        g.setColor(Color.MAGENTA); 
                    else if(color.equals("ORANGE"))
                        g.setColor(Color.ORANGE);    
                    else if(color.equals("PINK"))
                        g.setColor(Color.PINK); 
                    else if(color.equals("RED"))
                        g.setColor(Color.RED);
                  int a = head.getA();
                  int b = head.getB();
                  if(up || down)
                  {
                        g.fillRect(a-37,b,89,15);
                        g.setColor(Color.BLACK);
                        g.drawLine(a-37,b,a+52,b);
                        g.drawLine(a-37,b,a-37,b+15);
                        g.drawLine(a+52,b,a+52,b+15);
                        g.drawLine(a-37,b+15,a+52,b+15);
                           
                  }
                  else if(left || right)
                  {
                       g.fillRect(a,b-37,15,89); 
                       g.setColor(Color.BLACK);
                       g.drawLine(a,b-37,a+15,b-37);
                       g.drawLine(a,b-37,a,b+52);
                       g.drawLine(a+15,b-37,a+15,b+52);
                       g.drawLine(a,b+52,a+15,b+52);
                  }     
              }
              
          }
        }
        if(redShellOn)
        {
            //head.getA();
            //dot.getA();
            //edit
        }
    }
    
    /**
     * Determines if the Snake has crashed into itself
     * @return true if the Snake has crashed into itself, otherwise false
     */
    public boolean crash()
    {
        if(modeT)
        {
            if(timer2<=0)
                return true;
            else if((lastpowerUp.equals("WHITE") || lastpowerUp.equals("RAINBOW")) && powerupTimer > 0)
            {
                return false;
            }
            else
            {
                for(int i = 0; i < list.size()-1; i++)
                {
                    if(list.get(i).isEqual(head))
                        return true;
                }
                return false;
            }
        }
        else
        {
            if((lastpowerUp.equals("WHITE") || lastpowerUp.equals("RAINBOW")) && powerupTimer > 0)
            {
                return false;
            }
            else
            {
                for(int i = 0; i < list.size()-1; i++)
                {
                    if(list.get(i).isEqual(head))
                        return true;
                }
                return false;
            }
        }
        
        
    }
    /**
     * Determines if the Snake has gone out of bounds
     * @return true if the Snake has gone out of bounds, otherwise false
     */
    public boolean outOfBounds()
    {
        if((lastpowerUp.equals("BLUE") || lastpowerUp.equals("RAINBOW")) && powerupTimer > 0)
        {
            return false;
        }
        
        if(head.getA() < 150 || head.getA() >= 1260)
            return true;
        else if(head.getB() <150 || head.getB() >=630)
            return true;
        else 
            return false;
            
            
    }    
    
    /**
     * Plays the sound when the user loses
     */
    public void endSound()
    {
        if(!mute)
        {
        try {
        if(lastpowerUp.equals("RAINBOW"))
        {
            sclip.stop();
            bclip.start();
            bclip.loop(Clip.LOOP_CONTINUOUSLY);
        }
             // Open an audio input stream.
        URL url = this.getClass().getClassLoader().getResource("Silence.wav");   
        
        if(lastpowerUp.equals("BLUE"))
            url = this.getClass().getClassLoader().getResource("Out Of Bounds Stop.wav");
        else if(lastpowerUp.equals("YELLOW")||lastpowerUp.equals("DOUBLE")  || lastpowerUp.equals("WHITE") || (lastpowerUp.equals("TROLL") && modeT))
            url = this.getClass().getClassLoader().getResource("Mushroom Down.wav") ;
        else if(lastpowerUp.equals("BLACK") || lastpowerUp.equals("TROLL"))
            url = this.getClass().getClassLoader().getResource("Invisible Off.wav");
        
        
        
         
         
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
        Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
        clip.open(audioIn);         
         
        clip.start();
      
        
        
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
         
      }        
      }
    }   
    /**
     * Plays the correct song when a powerup is collected
     */
    public void specialSong()
    {
      if(!mute)
      {
         try {
       
        bclip.stop();
        
        // Open an audio input stream.
        //URL url = this.getClass().getClassLoader().getResource("Glitzville.wav");   
        //if(lastpowerUp.equals("RAINBOW"))
            URL url = this.getClass().getClassLoader().getResource("Mario Kart Starman.wav");
        
        
          
        
         
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
        sclip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
        sclip.open(audioIn);         
        
        sclip.start();
        
        
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }      
    }
    }    
    
    /**
     * Plays the song with the game is paused
     */
    public void pauseSong()
    {
        if(!mute)
        {
        try{ 
                //bclip.stop();
                
                URL url = this.getClass().getClassLoader().getResource("Refreshing Elevator Music.wav");
                        
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                        // Get a sound clip resource.
                pclip = AudioSystem.getClip();
            
                        // Open audio clip and load samples from the audio input stream.
                pclip.open(audioIn);    
                pclip.start();
                pclip.loop(Clip.LOOP_CONTINUOUSLY);
                }catch(Exception e){}
            }
    }
    /**
     * Plays the sound when a coin is collected
     */
    public void coinSound()
    {
       if(!mute)
       {
        try {
         // Open an audio input stream.
        URL url = this.getClass().getClassLoader().getResource("Coin.wav");           
        
        if(dotColor.equals("BLUE"))
            url = this.getClass().getClassLoader().getResource("Out Of Bounds Start.wav");
        else if(dotColor.equals("CRYSTAL"))
            url = this.getClass().getClassLoader().getResource("Crystal Noise.wav");
        else if(dotColor.equals("YELLOW"))
            url = this.getClass().getClassLoader().getResource("Mushroom Up.wav");
        else if(dotColor.equals("BLACK"))
            url = this.getClass().getClassLoader().getResource("Invisible On.wav");
        else if(dotColor.equals("RAINBOW"))
            url = this.getClass().getClassLoader().getResource("Sonic Ring.wav");
        else if(powerupTimer>0 && lastpowerUp.equals("CRYSTAL"))
            url = this.getClass().getClassLoader().getResource("Coin.wav");   
        else if(powerupTimer > 0 && (lastpowerUp.equals("DOUBLE")|| (lastpowerUp.equals("YELLOW") && modeT)))
            url = this.getClass().getClassLoader().getResource("DK.wav");
        else if(powerupTimer >0 && !lastpowerUp.equals("BLACK"))
            url = this.getClass().getClassLoader().getResource("Boop.wav");
        else if(powerupTimer >0 && lastpowerUp.equals("BLACK") && !modeT)
            url = this.getClass().getClassLoader().getResource("Secret Sound.wav");
        else if(dotColor.equals("DOUBLE"))
            url = this.getClass().getClassLoader().getResource("Double Points.wav");
        else if(dotColor.equals("WHITE"))
            url = this.getClass().getClassLoader().getResource("Kaboom.wav");
        else if(dotColor.equals("TROLL"))
        {
             if(!modeT)
                url = this.getClass().getClassLoader().getResource("Toad Sound.wav");
             else 
                url = this.getClass().getClassLoader().getResource("Mushroom Up.wav");
        }
        if(difficult.equals("GHOST"))
            url = this.getClass().getClassLoader().getResource("Crystal Noise.wav");
       
        double c = Math.random(); 
        //if(c>.9)
        // {
        //    url = this.getClass().getClassLoader().getResource("");
        //}
         
         
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
        Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
        clip.open(audioIn);         
         
        clip.start();
      
        
        
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
    }
    }
    /**
     * Plays the countdown sound at the beginning of the game
     */
    public  void startSound()
    
    {try
            {                 
                URL url = this.getClass().getClassLoader().getResource("Mario Kart Start 2.wav");
                  
                   
                                      
                
                
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                 // Get a sound clip resource.
                startClip = AudioSystem.getClip();
                 // Open audio clip and load samples from the audio input stream.
                startClip.open(audioIn);         
                 
                startClip.start();
                
                
            } 
            catch (UnsupportedAudioFileException e)
            {
                 e.printStackTrace();
            } 
            catch (IOException e) 
            {
                 e.printStackTrace();
            } 
            catch (LineUnavailableException e) 
            {
                 e.printStackTrace();
            }        
        
    }
    /**
     * Plays the sound when a high score is achieved
     */
    public void highscorenoise()
    {
        if(!mute)
            {
            try
            {                 
                URL url = this.getClass().getClassLoader().getResource("Darkmoon Caverns.wav");
                double c = Math.random();
                if(c<=.25)
                    url = this.getClass().getClassLoader().getResource("Mario Kart Win.wav");
                else if(c<=.50)
                    url = this.getClass().getClassLoader().getResource("Pirate Lagoon.wav");
                else if(c<=.75)
                    url = this.getClass().getClassLoader().getResource("Crescent Island.wav");   
                   
                                      
                
                
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                 // Get a sound clip resource.
                hclip = AudioSystem.getClip();
                 // Open audio clip and load samples from the audio input stream.
                hclip.open(audioIn);         
                 
                hclip.start();
                hclip.loop(Clip.LOOP_CONTINUOUSLY);
                
            } 
            catch (UnsupportedAudioFileException e)
            {
                 e.printStackTrace();
            } 
            catch (IOException e) 
            {
                 e.printStackTrace();
            } 
            catch (LineUnavailableException e) 
            {
                 e.printStackTrace();
            }        
        }
    }
    /**
     * Plays the sound when the user loses
     */
    public void loseNoise()
    {
        if(!mute)
        {
        try {                 
                        URL url = this.getClass().getClassLoader().getResource("Doh.wav");   
                        
                        double c = Math.random();
                        
                    if(crash())
                    {
                        if(c>.20 && c<.45)
                            url = this.getClass().getClassLoader().getResource("Doh2.wav");
                        else if(c>=.45 && c<.75)
                            url = this.getClass().getClassLoader().getResource("Doh3.wav");
                        else if(c>=.75 && c<=1)
                            url = this.getClass().getClassLoader().getResource("Doh4.wav");
                    }
                
                    else if(outOfBounds())
                    //else
                    {
                        url = this.getClass().getClassLoader().getResource("MM Game Over.wav");
                        if(c>.50 && c<=.75)
                            url = this.getClass().getClassLoader().getResource("SM3 Game Over.wav");
                        if(c>.75 && c<=1)
                            url = this.getClass().getClassLoader().getResource("DK Game Over.wav");
                      }
                    if(modeT)
                    {
                        url = this.getClass().getClassLoader().getResource("MM Game Over.wav");
                        if(c>.50 && c<=.75)
                            url = this.getClass().getClassLoader().getResource("SM3 Game Over.wav");
                        if(c>.75 && c<=1)
                            url = this.getClass().getClassLoader().getResource("DK Game Over.wav");
                    }
                    
                        if(difficult.equals("GHOST"))
                        {
                            //if(outOfBounds())
                                url = this.getClass().getClassLoader().getResource("PM Game Over.wav");
                            //else if(crash())
                            //    url = this.getClass().getClassLoader().getResource("PM2 Game Over.wav");
                        }
                         
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                         // Get a sound clip resource.
                        lclip = AudioSystem.getClip();
                         // Open audio clip and load samples from the audio input stream.
                        lclip.open(audioIn);         
                         
                        lclip.start();
                        
                        
                      } catch (UnsupportedAudioFileException e) {
                         e.printStackTrace();
                      } catch (IOException e) {
                         e.printStackTrace();
                      } catch (LineUnavailableException e) {
                         e.printStackTrace();
                      }        
                    }
    }
    /**
     * This methods tests out the sound clips
     */
    public void soundClipTest()
    {
      //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //this.setTitle("Test Sound Clip");
      //this.setSize(300, 200);
      //this.setVisible(true);
      
      try {
         // Open an audio input stream.
        URL url = this.getClass().getClassLoader().getResource("Silence.wav");
        if(difficult.equals("YOU WILL NOT SURVIVE"))   
            url = this.getClass().getClassLoader().getResource("Koopa Bros.wav");        
        else if(difficult.equals("HARD"))
            url = this.getClass().getClassLoader().getResource("Epic One Piece.wav");//"Stand Up Be Strong.wav"); "Halo.wav" 
        else if(difficult.equals("MEDIUM"))
            url = this.getClass().getClassLoader().getResource("Epic One Piece.wav");
        else if(difficult.equals("EASY"))
            url = this.getClass().getClassLoader().getResource("Bowsers Castle.wav");
        else if(difficult.equals("GHOST"))
            url = this.getClass().getClassLoader().getResource("Paper Mario- Crystal Palace Crawl.wav");
        else if(difficult.equals("TROLL"))
            url = this.getClass().getClassLoader().getResource("Troll Song.wav"); //"Mario Kart 64.wav"
                 
         
         
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
        bclip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
        bclip.open(audioIn);
        
         
        //try{Thread.sleep(2000);} catch(InterruptedException e) {}
         if(!mute)
         {
            bclip.start();
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
            bclip.loop(Clip.LOOP_CONTINUOUSLY);
            
        }
        if(pause)
        {
            try{bclip.wait();} catch(InterruptedException e) {}
            
            URL url2 = this.getClass().getClassLoader().getResource("Refreshing Elevator Music.wav");
            
            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(url2);
            // Get a sound clip resource.
            Clip pclip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            pclip.open(audioIn2);
            
            if(!mute)
                pclip.start();            
        }
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
    
    }
    /**
     * Runs snake game. Begins the game with a countdown, then proceeds to run the game
     * Sets the speed of the snake
     * Chooses a new location for the dot when it has been collected by the snake
     * Keeps track of and displays the score, time elapsed, and dots collected
     * Will stop the game when the snake has crashed or is out of bounds
     */
    public void run()    
    {
        //Runs the countdown before the game starts
        
        if(!mute)
            try{startSound();}catch(Exception e){}
        for(int i = 3; i > 0; i--)
        {
            text = "Game will start in: " +i;
            repaint();
            try{Thread.sleep(1000);}catch(InterruptedException e){}
        }        
        text ="";
        start = true;
        
        soundClipTest();
        
        while(running)
        {
            tick();
            timer();  
                        
            try
            {
                if(modeT)
                    Thread.sleep(25);
                else if(difficult.equals("EASY"))
                {
                    if(lastpowerUp.equals("RAINBOW") && powerupTimer >0)
                        Thread.sleep(45);
                    else
                        Thread.sleep(67);
                }
                else if(difficult.equals("MEDIUM")||difficult.equals("GHOST")|| difficult.equals("TROLL"))
                    Thread.sleep(45);
                else if(difficult.equals("HARD"))
                    Thread.sleep(30);
                else if(difficult.equals("YOU WILL NOT SURVIVE"))
                    Thread.sleep(10);
            }                     
            catch(InterruptedException e) {}
            
            if(pause)
            {
                pauseSong();
            }
            
            if(powerupTimer>.1 && powerupTimer<.21)
            {
                endSound();
            }

            
            if(dot.isEqual(head) || (lastpowerUp.equals("YELLOW") && powerupTimer > 0 && wideSnakeOnDot() && !modeT) ||(lastpowerUp.equals("TROLL") && powerupTimer > 0 && wideSnakeOnDot() && modeT))
            {
                coinSound();
                
                for(int k = 0; k<1 ; k++)
                {
                    addSegment();
                }
                
                if(!dotColor.equals("RED"))
                {
                    if(dotColor.equals("BLUE"))
                    {
                        if(!modeT)
                            powerupTimer = 15.0;
                        else
                            powerupTimer = 10.0;
                        lastpowerUp = "BLUE";
                    }
                    else if(dotColor.equals("PURPLE"))
                    {
                        powerupTimer = 15.0;
                        lastpowerUp = "PURPLE";
                    }
                    else if(dotColor.equals("RAINBOW"))
                    {
                        if(!modeT)
                            powerupTimer = 10.0 + (int)(Math.random()*10);
                        else
                            powerupTimer = 5.0 + (int)(Math.random()*5);
                        //powerupTimer = 10.0;
                        lastpowerUp = "RAINBOW";
                        
                        specialSong();
                    }
                    else if(dotColor.equals("YELLOW"))
                    {
                        if(!modeT)
                            powerupTimer = 15.0;
                        else
                            powerupTimer = 10.0;
                        lastpowerUp = "YELLOW";               
                    }
                    else if(dotColor.equals("BLACK"))
                    {
                        powerupTimer = 10.0;                         
                        lastpowerUp = "BLACK";
                    }
                    else if(dotColor.equals("WHITE"))
                    {
                        if(!modeT)
                            powerupTimer = 20.0;
                        else
                            powerupTimer = 10.0;
                        lastpowerUp = "WHITE";
                        
                        //specialSong();
                    }
                    else if(dotColor.equals("DOUBLE"))
                    {
                        lastpowerUp = "DOUBLE";
                        if(!modeT)
                            powerupTimer = 20.0;
                        else
                            powerupTimer = 10.0;
                    }
                    else if(dotColor.equals("TROLL"))
                    {
                        lastpowerUp = "TROLL";
                        if(!modeT)
                            powerupTimer = 15.0;
                        else
                            powerupTimer = 10.0;
                    }
                    else if(dotColor.equals("CRYSTAL"))
                    {
                        lastpowerUp = "CRYSTAL";
                        powerupTimer = 10.0;
                    }
                    else if(dotColor.equals("RED SHELL"))
                    {
                        lastpowerUp = "RED SHELL";
                        hasItem = true;
                    }
                }
                

                               
                
                randomize();
                dot.setA(a);
                dot.setB(b);    
                
                boolean go = true;
                while(go)
                {
                    if(snakeOnDot())
                    {
                        //dots.remove(0);
                        
                        randomize();
                        dot.setA(a);
                        dot.setB(b);      
                        
                        //dots.add(dot);
                    }
                    else
                        go = false;
                }
                
                //dots.add(dot);
                
                
                //Calculates the score based on the dots collected and the time taken to get them
                if(!modeT)
                {
                    if(lastpowerUp.equals("DOUBLE") && powerupTimer > 0)
                        totalScore += 2*(100-5*timer2);
                    else if(timer2<18)
                        totalScore += 100 - 5*timer2;
                    else
                        totalScore += 10;
                    if(!difficult.equalsIgnoreCase("YOU WILL NOT SURVIVE"))
                    {
                        if(lastpowerUp.equals("BLACK") && powerupTimer > 0)
                            totalScore += 1000;
                        else if(lastpowerUp.equals("RAINBOW") && powerupTimer > 0)
                            totalScore += 100;
                    }
                }
                
               
                scoreCount += 1;
                if(modeT)
                {
                    if(lastpowerUp.equals("YELLOW") && powerupTimer > 0)
                        timer2 +=6;
                    else    
                        timer2+=3;
                }
                else
                    timer2 = 0;
                
            }         
            if(!modeT)     
                text2 = "Total Score: " + totalScore;
            else
                text2 = "Total Score: " + (int)(timer3);
            text3 = "Dots Collected: " + scoreCount;     
            
            if(!modeT)
            {
                if(timer2 == 1)
                    text4 = "Time Elapsed: " + ((int)(timer2*10))/10.0 + "  second"; 
                else
                    text4 = "Time Elapsed: " + ((int)(timer2*10))/10.0 + " seconds";
            }
            else
            {
               if(timer2 == 1)
                    text4 = "Time Remaining: " + ((int)(timer2*10))/10.0 + "  second"; 
               else
                    text4 = "Time Remaining: " + ((int)(timer2*10))/10.0 + " seconds";
            }
            if(outOfBounds() || crash())
            {                          
                if(totalScore > lowestHighscore() || (int)timer3 > lowestHighscore())
                {
                    highscorenoise();
                    //PLAY OTHER SOUND CLIP HERE
                    //My suggestion: https://www.youtube.com/watch?v=G2vA6Dngzhs because this music is awesome and deserves to be somewhere
                }
                else   
                    loseNoise();
                
                running = false;
                bclip.stop();
                if(powerupTimer > 0 && (lastpowerUp.equals("RAINBOW")))
                    sclip.stop();
                
                                
                reset();       
            }
        }     
    }
    /**
     * Determines what happens after the user loses. If the user selects to play again, will restart the game
     */
    public void reset()    
    {
       if(waitingToReset)
           return;
       //.out.println(totalScore);
       waitingToReset = true;
       
       //String name = JOptionPane.showInputDialog("What is your name?");
       //user.setName(name);
       
       
       
       highscore();
       displayhs();
       String high = "";
       int restart;
       if(!modeT)
            restart = JOptionPane.showConfirmDialog(null,"              You Died\n         Final Score:   " + totalScore + "\n       Dots Collected:     " + scoreCount + "\nWould you like to restart?","GAME OVER",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       else
            restart = JOptionPane.showConfirmDialog(null,"              You Died\n         Final Score:   " + (int)timer3 + "\n       Dots Collected:     " + scoreCount + "\nWould you like to restart?","GAME OVER",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       if(restart == JOptionPane.CLOSED_OPTION)
       {
            resetSettings();
            System.exit(0);
       }
       if(restart == JOptionPane.NO_OPTION ) 
            {
                resetSettings();
                System.exit(0);
            }
       else
       {
           if((totalScore > lowestHighscore() || (int)timer3 > lowestHighscore()) && !mute )
           {
               hclip.stop();
               hclip.close();
           }
           else if(totalScore > lowestHighscore() || (int)timer3 > lowestHighscore())
               hclip.close();
               
           else if (!mute)
           {
               //lclip.stop();
               if(lclip!=null)
                   lclip.close();
               
               //sclip.close();
            }
           else
                ;
            //lclip.close();
            
           if(!mute)
            {
               list.clear();
               bclip.close();
               pclip.close();
           }
           //sclip.close();
           if(mute)
             bclip.close();
           if(hclip!=null)
             hclip.close();
          
           Frame f = new Frame();
           JFrame frame = (JFrame) SwingUtilities.getRoot(this);   
           frame.setVisible(false);
           //Frame.reset();
           //frame.dispose();
       }  
            
        /*
         * if(waitingToReset)
            return;
       waitingToReset = true;
       int restart = JOptionPane.showConfirmDialog(null,"              You Died\n         Final Score:   " + totalScore + "\n       Dots Collected:     " + scoreCount + "\nWould you like to restart?","GAME OVER",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       if(restart == JOptionPane.NO_OPTION) 
            System.exit(0);
       else
       {               
            JFrame frame = (JFrame) SwingUtilities.getRoot(this);   
            frame.setVisible(false);
            Frame.reset();
       }
         */
    } 
}

// 
//  if(lastpowerUp.equals("PURPLE") && dots.size()>1)
//             {
//                 for(int i=0 ; i<dots.size() ; i++)
//                 {
//                     if(dots.get(i).isEqual(head))
//                     {
//                             dots.remove(dots.get(i));
//                         
//                             randomize();
//                             Location dot2 = new Location(a,b);
//                             randomize();
//                             Location dot3 = new Location(a,b);
//                             
//                             dots.add(dot2);
//                             dots.add(dot3);    
//                             
//                             addSegment();
//                             
//                                 if(timer2<18)
//                                     totalScore += 100 - 5*timer2;
//                                 else
//                                     totalScore += 10;
//                                 
//                             
//                                 scoreCount += 1;
//                                 timer2 = 0;
//         
//                     }
//                 }
//           }