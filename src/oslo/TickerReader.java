package oslo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Takes input from a text file and feeds it to a ticker
 * @author Jeremy Bassi
 */
public class TickerReader 
{
    public static final String OPEN = "res/ticks/open.txt";
    
    private Scanner sc;
    
    public TickerReader() {}
    
    private void initScanner(String path)
    {
        try
        {
            sc = new Scanner(new File(path)); //TODO Test in jar subject to change
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public ArrayList<Tick> read(String path)
    {
        initScanner(path);
        
        ArrayList<Tick> ticks = new ArrayList<>();
        
        String line = null;

        while(sc.hasNextLine())
        {
            line = sc.nextLine();

            //NB format
            ticks.add(new Tick(Integer.parseInt(line.substring(0, 5).trim()), line.substring(5)));
        }
        
        //sorting
        Collections.sort(ticks);

        return ticks;
    }
}
