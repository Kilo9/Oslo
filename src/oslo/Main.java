package oslo;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * First game using Slick2D library. It will be a short, simple
 * game using a tiled map.
 * @author Jeremy Bassi
 */
public class Main extends BasicGame
{
    //NOTE: all static final variables contained in main class for organization 
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    public static final int TILE_HEIGHT = 8; //maybe change using tiled map methods
    public static final int TILE_WIDTH = 8; //see above
    
    public static final float XSCALE = 4.0f;
    public static final float YSCALE = 4.0f;

    public static final String TILE_SHEET_PATH = "res/tex/texsheet.png";
    public static final String SPRITE_SHEET_PATH = "res/sprites/spritesheet.png";
    
    public static SpriteSheet textureSheet;
    public static SpriteSheet spriteSheet;
    
    public static final String TITLE = "Oslo Pre-Alpha";
    
    private float mapXOffs;
    private float mapYOffs;
    
    private Dag dag;
    
    private Map map;
    
    //exp
    private Coin coin;
    
    public Main()
    {
        super(TITLE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try
        {
            AppGameContainer gc = new AppGameContainer(new Main());
            gc.setDisplayMode(WIDTH, HEIGHT, false);
            gc.setTargetFrameRate(60);
            gc.setClearEachFrame(true);
            gc.setIcons(new String[] {"res/sprites/icon16.png", "res/sprites/icon32.png"});
            //gc.setShowFPS(false);
            gc.setVSync(true);
            //gc.setSmoothDeltas(true);
            gc.start();
        } catch (SlickException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void init(GameContainer gc) throws SlickException 
    {
        textureSheet = new SpriteSheet(TILE_SHEET_PATH, TILE_WIDTH, TILE_HEIGHT);
        spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, Dag.SPRITE_WIDTH, Dag.SPRITE_HEIGHT);
        
        map = new MapReader().read("res/maps/Arendal.png");

        //TODO Reconsider where lighting is activated
        map.setLightMapAlpha(1.0f);
        map.createLightSource(100, 50, 100, 10);
        map.createLightSource(120, 80, 50, 10);
        map.renderLighting();
        
        dag = new Dag(gc.getInput(), map);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException 
    {
        dag.update(delta);
        map.update(delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException 
    {
        g.scale(XSCALE, YSCALE); //scale
        g.setClip(0, 0, WIDTH, HEIGHT); //clip all unnecessary drawing
        renderScene();
    }
    
    private void renderScene()
    {       
        float x = dag.getX(); //position of dag relative to MAP
        float y = dag.getY(); // ^
        
        float xBound = (WIDTH / (2 * XSCALE)) - (Dag.SPRITE_WIDTH/ 2);
        float yBound = (HEIGHT / (2 * YSCALE)) - (Dag.SPRITE_HEIGHT/ 2);
        
        float xMiddle = xBound; //set to xbound because they are essentially the same value
        float yMiddle = yBound; //same as ^
        
        mapXOffs = 0;
        mapYOffs = 0;
        
        float xPos = 0; //position of dag relative to SCREEN
        float yPos = 0; // ^
        
        //System.out.println(x + " " + y);
        if (x > xBound && x < map.getPixelWidth() - (xBound + Dag.SPRITE_WIDTH))
        {
            xPos = xMiddle;
            mapXOffs = x - xBound;
            //System.out.println(map.getPixelWidth() + " " + xBound);
        }
        else if (x <= xBound)
        {
            xPos = x;
            mapXOffs = 0;
        }
        else if (x >= (map.getPixelWidth()  - (xBound + Dag.SPRITE_WIDTH)))
        {
            xPos = xMiddle + (x - (map.getPixelWidth() - (xBound + Dag.SPRITE_WIDTH)));
            mapXOffs = map.getPixelWidth() - (WIDTH / XSCALE);
        }
        
        if (y > yBound && y < map.getPixelHeight() - (yBound + Dag.SPRITE_HEIGHT))
        {
            yPos = yMiddle;
            mapYOffs = y - yBound;
        }
        else if (y <= yBound)
        {
            yPos = y;
            mapYOffs = 0;
        }
        else if (y >= (map.getPixelHeight()  - (yBound + Dag.SPRITE_HEIGHT)))
        {
            yPos = yMiddle + (y - (map.getPixelHeight() - (yBound + Dag.SPRITE_HEIGHT)));
            mapYOffs = map.getPixelHeight() - (HEIGHT / YSCALE);
        }

        map.renderMap(-mapXOffs, -mapYOffs);
        //dag.render(xPos, yPos);
        map.renderLightMap(-mapXOffs, -mapYOffs);
        dag.render(xPos, yPos);
    }
    
    private void renderObject(Renderable obj)
    {
        obj.render(obj.getX() - mapXOffs, obj.getY() -  mapYOffs);
    }
}
