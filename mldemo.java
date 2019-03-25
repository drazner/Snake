import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class mldemo
{
    public static void main(String[] args)
    {
        FileReader filereader;
        
        int score = 100000;
        PrintWriter writer;
        ArrayList<PersonDate> hsp = new ArrayList<PersonDate>();
        
        
        //Person p1 = new Person("Brandon",score);
        try{
            if(score>lowestHighscore())
            {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                Date date = new Date();
                String r = dateFormat.format(date);
                
                JOptionPane.showMessageDialog(null,"New high score!");
                String name = JOptionPane.showInputDialog("Please enter name");
                PersonDate p1 = new PersonDate(name,score,r);
            
                filereader = new FileReader("Data.txt");
                BufferedReader bufferedreader = new BufferedReader(filereader);
                
                
                
                String line = bufferedreader.readLine();
                
                while(line!=null)
                {
                    //hslist.add(Integer.parseInt(line.split(":")[1]));
                    hsp.add(new PersonDate(line.split(":")[0],Integer.parseInt(line.split(":")[1]),line.split(":")[2]));
                    line = bufferedreader.readLine();
                }
                hsp.add(p1);
                //System.out.println(hsp);
                //sort it
                sort(hsp);
                //System.out.println(hsp);
                
                //delete last thing
                hsp.remove(hsp.size()-1);
                //System.out.println(hsp);
                //rewrite doc
                
                writer = new PrintWriter(new File("Data.txt"));
                for(PersonDate p:hsp)
                {
                    writer.println(p);
                }
                writer.close();
                
                displayhs();
            
            }
            else
                JOptionPane.showMessageDialog(null,"I'm sorry, but you did not get a highscore");
        }
        catch(IOException e)
        {
        }
        //System.out.println(hsp);
    }
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
    public static int lowestHighscore()
    {
        FileReader filereader;
        PrintWriter writer;
        int lowHighScore = 0;
        try{
            
            filereader = new FileReader("Data.txt");
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
    public static void displayhs()
    {
        FileReader filereader;
        
        try{
            filereader = new FileReader("Data.txt");
            
            BufferedReader bufferedreader = new BufferedReader(filereader);
            String str = "High Scores\n";
            int hscount = 1;
            String line = bufferedreader.readLine();
            while(line!=null)
            {
                //System.out.println(line);
                str += hscount +". " + line.split(":")[0] + ", " + line.split(":")[1] + ", " + line.split(":")[2] + "\n";
                hscount++;
                line = bufferedreader.readLine();
            }
            //JOptionPane.showMessageDialog(null,str);
            String[] options = {"OK","Reset"};
        
            int choice = JOptionPane.showOptionDialog(null,str,"High Scores",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
            if(choice == 0)
            {
                 JOptionPane.showMessageDialog(null,"Game will resume");
            }
            else
             {    //Reset was chosen
                 int n = JOptionPane.showConfirmDialog(null,"Are you sure you would like to reset all high scores?","Are you sure?",JOptionPane.YES_NO_OPTION);
                 if(n == 0)
                 {
                     JOptionPane.showMessageDialog(null,"Scores will reset");
                     PrintWriter writer;
                     writer = new PrintWriter(new File("Data.txt"));
                     for(int i=0;i<5;i++)
                        writer.println("null:0:n/a");
                     writer.close();
                 }
                 else
                     JOptionPane.showMessageDialog(null,"Game will resume");
            }
        }
        catch(IOException e)
        {
            System.out.println("There is an error");
        }
            
        
    }
}