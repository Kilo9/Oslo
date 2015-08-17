package level;

import entity.Item;
import entity.Jonas;
import java.io.File;
import oslo.GUI;
import oslo.Main;
import oslo.Ticker;
import oslo.TickerReader;

/**
 * Class representing a complete level with a map and objects
 * @author Jeremy Bassi
 */
public class Level
{
    public static final String PATH = "res/levels/";
    public static final String MAP_NAME = "map";
    public static final String OBJ_NAME = "obj";
    public static final String OPEN_NAME = "open.txt";
    public static final String CLOSE_NAME = "close.txt";
    
    //initialization
    private Map map;
    private ObjectMap objMap;
    private MapReader mr;
    private ObjectMapReader omr;
    private String name;
    private Jonas jonas;
    private GUI gui;
    private Ticker open;
    private Ticker close;
    
    private int coins;
    
    //rendering
    float mapXOffs;
    float mapYOffs;
    
    
    
    public Level(String levelName)
    {   
        name = levelName;
    }
    
    public boolean loadLevel()
    {
        File[] folder = new File(PATH + name).listFiles();
        
        String mapPath, objPath;
        mapPath = objPath = "";
        
        for (File f : folder)
        {
            if (f.getName().startsWith(MAP_NAME))
            {
                mapPath = f.getPath();
            }
            else if (f.getName().startsWith(OBJ_NAME))
            {
                objPath = f.getPath();
            }
        }
        
        if (mapPath.equals("") || objPath.equals(""))
        {
            return false;
        }
        
        mr = new MapReader();      
        map = mr.read(mapPath);
        
        omr = new ObjectMapReader(map); 
        objMap = omr.read(objPath);  
        jonas = omr.getJonas();
        
        Item[] items = map.getItems();
        
        for (Item i : items)
        {
            objMap.add(i);
        }
        
        map.setLightMapAlpha(1.0f); 
        
        map.setLightArray(objMap.getLightArray()); 
        
        //Flashlight TODO simplify
        map.addLightSource(jonas.getLightSource());
        jonas.swapFlashlights();
        map.addLightSource(jonas.getLightSource());
        jonas.swapFlashlights();
        
        coins = objMap.getCoins();
        gui = new GUI(jonas, coins);
        
        open = getOpen();
        close = getClose();
        
        
        gui.addTicker(open);
        gui.addTicker(close);
        
        open.start();
        
        return true;
    }
    
    public void render()
    {
        renderScene(); 
        gui.render();
    }
    
    public void update(int delta)
    {
        //for window drag problems
        if (delta > 50) 
        {
            delta = 50;
        }
        
        objMap.update(delta);
        objMap.sweep(); 
        map.update(delta);
        gui.update(delta);
        //System.out.println(jonas.status());
    }
    
    public void updateTickers()
    {
        gui.updateTickers();
    }

    private void renderScene()
    {       
        float x = jonas.getX(); //position of dag relative to MAP
        float y = jonas.getY(); // ^
        
        float xBound = (Main.WIDTH / (2 * Main.XSCALE)) - (Jonas.SPRITE_WIDTH/ 2);
        float yBound = (Main.HEIGHT / (2 * Main.YSCALE)) - (Jonas.SPRITE_HEIGHT/ 2);
        
        float xMiddle = xBound; //set to xbound because they are essentially the same value
        float yMiddle = yBound; //same as ^
        
        mapXOffs = 0;
        mapYOffs = 0;
        
        float xPos = 0; //position of dag relative to SCREEN
        float yPos = 0; // ^
        
        if (x > xBound && x < map.getPixelWidth() - (xBound + Jonas.SPRITE_WIDTH))
        {
            xPos = xMiddle;
            mapXOffs = x - xBound;
        }
        else if (x <= xBound)
        {
            xPos = x;
            mapXOffs = 0;
        }
        else if (x >= (map.getPixelWidth()  - (xBound + Jonas.SPRITE_WIDTH)))
        {
            xPos = xMiddle + (x - (map.getPixelWidth() - (xBound + Jonas.SPRITE_WIDTH)));
            mapXOffs = map.getPixelWidth() - (Main.WIDTH / Main.XSCALE);
        }
        
        if (y > yBound && y < map.getPixelHeight() - (yBound + Jonas.SPRITE_HEIGHT))
        {
            yPos = yMiddle;
            mapYOffs = y - yBound;
        }
        else if (y <= yBound)
        {
            yPos = y;
            mapYOffs = 0;
        }
        else if (y >= (map.getPixelHeight()  - (yBound + Jonas.SPRITE_HEIGHT)))
        {
            yPos = yMiddle + (y - (map.getPixelHeight() - (yBound + Jonas.SPRITE_HEIGHT)));
            mapYOffs = map.getPixelHeight() - (Main.HEIGHT / Main.YSCALE);
        }
        
        map.renderMap(-mapXOffs, -mapYOffs);
        
        objMap.render(-mapXOffs, -mapYOffs);
        
        map.renderLighting();
        
        map.renderLightMap(-mapXOffs, -mapYOffs);
        
        objMap.renderLightEntities(-mapXOffs, -mapYOffs);
        
        jonas.setScreenX(xPos);
        jonas.setScreenY(yPos);
        jonas.render(xPos, yPos);
    }
    
    public Map getMap()
    {
        return map;
    }

    public void resize() 
    {
        gui.resize();
    }
    
    public int getStatus()
    {
        return jonas.status();
    }
    
    private Ticker getOpen()
    {
        return getTicker(OPEN_NAME);
    }
    
    private Ticker getClose()
    {
        return getTicker(CLOSE_NAME);
    }
    
    private Ticker getTicker(String s)
    {
        Ticker t = new Ticker(0, 0);
        t.add(new TickerReader().read(PATH + name + "/" + s));
        return t;
    }
    
    public Ticker openTicker()
    {
        return open;
    }
    
    public Ticker closeTicker()
    {
        return close;
    }

    public void renderTickers() 
    {
        gui.renderTickers();
    }
}
