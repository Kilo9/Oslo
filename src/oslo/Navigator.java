package oslo;

import entity.Jonas;
import java.util.ArrayList;
import level.Level;

/**
 *
 * @author Jeremy Bassi
 */
public class Navigator 
{
    //TODO Level pack with directory and class which pulls the next level
    public static final String[] MAIN_LEVELS = {"Inar", "Raghar", "Arendal"};
    
    private ArrayList<Level> levels;
    
    private Level current;
    private int index;
    
    //stop at end of level
    private boolean update;
    private boolean between;
    
    public Navigator()
    {        
        levels = new ArrayList<>();
        
        for (String s : MAIN_LEVELS)
        {
            levels.add(new Level(s));
        }
        
        index = 0;
        current = levels.get(index); 
        update = true;
        
        current.loadLevel();
    }
    
    public void render()
    {
        current.render();
        
        if (!update)
        {
            current.renderTickers();
        }
    }
    
    public void update(int delta)
    {
        if (!between && current.getStatus() == Jonas.WIN)
        {
            between = true;
            update = false;
            current.closeTicker().start();
        }
        
        if (current.closeTicker().isDone())
        {
            update = true;
            loadNext();
        }
        
        if (update)
        {
            current.update(delta);
        }
        else
        {
            current.updateTickers();
        }
    }
    
    private void loadNext()
    {
        current = levels.get(++index);
        current.loadLevel();
    }
    
}
