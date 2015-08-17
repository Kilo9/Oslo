package oslo;

import java.util.ArrayList;

/**
 *
 * @author Jeremy Bassi
 */
public class Ticker 
{
    public static final String EXIT = "{done}";
    
    private long start;
    
    private int x;
    private int y;
    
    private volatile boolean running;
    private boolean done;
    
    private TimeList ticks;
    private String current;

    public Ticker(int x, int y)
    {
        start = 0l;
        
        this.x = x;
        this.y = y;
        
        ticks = new TimeList();
        
        current = null;
        
        running = false;
        done = false;
    }
            
    public void start()
    {
        start = System.nanoTime();
        running = true;
    }
    
    public void stop()
    {
        if (running)
        {
            current = null;
            running = false;
            done = true;
        } 
    }
    
    public boolean hasNext()
    {
        return running && ticks.hasNext() && ticks.peek().time() < getTime();
    }
    
    public void next()
    {
        Tick t = ticks.next();
        
        if (t.message().trim().equalsIgnoreCase(EXIT))
        {
            current = null;
            stop();
            return;
        }
        
        current = t.message();
    }
    
    public int getTime()
    {
        return (int) ((System.nanoTime() - start) / 1000000000); //brill
    }
    
    public void add(Tick t)
    {
        ticks.add(t);
    }
    
    public void add(ArrayList<Tick> t)
    {
        for (Tick ti : t)
        {
            add(ti);
        }
    }
    
    public String getCurrent()
    {
        return current;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public boolean isRunning()
    {
        return running;
    }
    
    public boolean isDone()
    {
        return done;
    }
}
