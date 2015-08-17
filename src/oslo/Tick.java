package oslo;

/**
 * Ticks are messages with a time for display
 * --for use with Ticker class
 * @author Jeremy Bassi
 */
public class Tick implements Comparable<Tick>
{
    private int time;
    private String message;
    
    public Tick(int time, String message)
    {
        this.time = time;
        this.message = message;
    }
    
    public int time()
    {
        return time;
    }
    
    public String message()
    {
        return message.toUpperCase();
    } 

    @Override
    public int compareTo(Tick t) 
    {
        return time - t.time();
    }
    
    @Override
    public String toString()
    {
        return "{ " + time() + ", " + message() + " }";
    }
}
