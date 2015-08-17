package oslo;

import entity.Jonas;
import java.io.File;
import level.Level;
import level.Map;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    //aspect ratio 4:3
    
    public static final int TILE_HEIGHT = 8; //maybe change using tiled map methods
    public static final int TILE_WIDTH = 8; //see above
    
    public static final float XSCALE = 4.0f;
    public static final float YSCALE = 4.0f;

    public static final String TILE_SHEET_PATH = "res/tex/TexSheet.png";
    public static final String SPRITE_SHEET_PATH = "res/sprites/Spritesheet.png";
    
    public static SpriteSheet textureSheet;
    public static SpriteSheet spriteSheet;
    public static Input input;
    
    public static final String TITLE = "Oslo Pre-Alpha";
    
    private static AppGameContainer gc;
    
    private Level level;
    private Navigator nav;
    
    public Main()
    {
        super(TITLE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.setProperty("java.library.path", "libs");

        System.setProperty("org.lwjgl.librarypath", new File("libs/natives").getAbsolutePath());
        try
        {
            gc = new AppGameContainer(new Main());
            gc.setDisplayMode(WIDTH, HEIGHT, false);
            gc.setTargetFrameRate(60);
            //gc.setClearEachFrame(false);  //unsure of effect
            gc.setIcons(new String[] {"res/other/icon16.png", "res/other/icon32.png"});
            //gc.setIcons(new String[] {"res/other/icon.png"});
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
        spriteSheet = new SpriteSheet(SPRITE_SHEET_PATH, Jonas.SPRITE_WIDTH, Jonas.SPRITE_HEIGHT);
        
        input = gc.getInput();
        
        nav = new Navigator();
        //resizeToMap(level.getMap()); //experimental when combined with gui/text
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException 
    {
        nav.update(delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException 
    { 
        g.scale(XSCALE, YSCALE);
        g.setClip(0, 0, WIDTH, HEIGHT); //clip all unnecessary drawing
        nav.render();
    }
    
    /**
     * resizes the game to the map if the map is smaller than the display
     * @param map 
     */
    private void resizeToMap(Map map)
    {
        int width = (int) (map.getPixelWidth() * XSCALE);
        int height = (int) (map.getPixelHeight() * YSCALE);
        
        boolean change = false;
        
        if (width < WIDTH)
        {
            WIDTH = width;
            change = true;
        }
        if (height < HEIGHT)
        {
            HEIGHT = height;
            change = true;
        }
        
        if (change)
        {
            try 
            {
                gc.setDisplayMode(WIDTH, HEIGHT, false);
            } catch (SlickException ex) 
            {
                System.err.println(ex.getMessage());
            }
            
            level.resize();
        }  
    }
    
    public static Input getInput()
    {
        return input;
    }
}
