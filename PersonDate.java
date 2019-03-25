
/**
 * Write a description of class Person here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PersonDate
{
    
    private int score;
    private String name;
    private String date;
    /**
     * Constructs a new Person whose name is null and has no score
     */
    public PersonDate()
    {
        name = null;
        score = 0;
    }
    /**
     * Constructs a new person with a given name and score
     * @param n The name of the person
     * @param s The score of the person
     */
    public PersonDate(String n,int s,String d)
    {
        name = n;
        score = s;
        date = d;
    }
    
    /**
     * Returns the score of the person
     * @return the score of the person
     */
    public int getScore()
    {
        return score;
    }
    /**
     * Returns the name of the person
     * @return the name of the person
     */
    public String getName()
    {
        return name;
    }
    public String getDate()
    {
        return date;
    }
    /**
     * Returns:
     * 1 if score is greater
     * 0 if scores are equal
     * -1 if score is less than
     * @return the index of comparison
     */
    public int compareTo(PersonDate other)
    {
        if(getScore() > other.getScore())
            return 1;
        else if(getScore() == other.getScore())
            return 0;
        else if(getScore() < other.getScore())
            return -1;
        else return 0;
    }
    /**
     * Returns a string representation of the person class in the format of name:score
     * @return a person in the name:score format
     */
    public String toString()
    {
        return getName()+":"+getScore()+":"+getDate();
    }
}
