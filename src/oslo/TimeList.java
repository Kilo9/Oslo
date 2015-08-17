package oslo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ArrayList wrapper for ordering with time
 * @author Jeremy Bassi
 */
public class TimeList 
{
    private ArrayList<Tick> ticks;
    
    public TimeList()
    {
        ticks = new ArrayList<>();
    }
    
    public TimeList(int capacity)
    {
        ticks = new ArrayList<>(capacity);
    }
    
    public void add(Tick t)
    {
        ticks.add(t);
        Collections.sort(ticks);
    }
    
    public Tick peek()
    {
        return ticks.get(0);
    }
    
    public Tick next()
    {
        return ticks.remove(0);
    }
    
    public void clear()
    {
        ticks.clear();
    }
    
    public boolean hasNext()
    {
        return ticks.size() > 0;
    }
}
