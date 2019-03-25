import java.io.*;
import javax.swing.*;
/**
 * Write a description of class resetScores here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class resetScores
{
     public static void main(String[] args)
     {
         //JOptionPane.showMessageDialog(null,"Scores will reset");
         PrintWriter writer;
         String[] level = {"timetrail.txt","easy.txt","medium.txt","hard.txt","youwillnotsurvive.txt","ghost.txt","troll.txt"};
         for(String x: level)
         {
             try{
             writer = new PrintWriter(new File(x));
             for(int i=0;i<5;i++)
                writer.println("null:0:n/a");
             writer.close();
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(null,"File " + x + " not found");
            }
         }
         JOptionPane.showMessageDialog(null,"Scores have been reset");;
     }
}

