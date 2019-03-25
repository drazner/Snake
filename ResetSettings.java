import java.io.*;
import javax.swing.*;
/**
 * Write a description of class ResetSettings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ResetSettings
{
    public static void main(String[] args)
    {
        
        PrintWriter writer;
        try
        {
            writer = new PrintWriter(new File("settings.txt"));
            int[] r = {0,2,0,0};
            for(int x:r)
                writer.println(x);
            writer.close();
            JOptionPane.showMessageDialog(null,"Settings have been reset");
        }
        catch(IOException e)
        {}
        
    }
}
