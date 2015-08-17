package entity;

import level.DirectionalLightSource;
import level.DirtTile;
import level.FlagTile;
import level.FocusedLightSource;
import level.LightSource;
import level.Map;
import level.ObjectMap;
import level.PortalTile;
import level.WaterTile;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import oslo.Audio;
import oslo.Main;
import oslo.SpriteSet;

/**
 *
 * @author Jeremy Bassi
 */
public class Jonas extends LightEntity
{
    public static final float WALK_SPEED = 0.05f;
    public static final float SWIM_SPEED = 0.025f;
    public static final float DIRT_SPEED = 0.030f;
    public static final float DIAGONAL_FACTOR = .7f;
    public static final float DET_FACTOR = 1.0f;
    public static final float DET_RECOVER = .7f;
    
    public static final double START_SECONDS = 20;
    public static final double MAX_SECONDS = 30;
    
    public static final int FLASH_LUX = 100;
    public static final int SPRITE_WIDTH = 8;
    public static final int SPRITE_HEIGHT = 8;
    public static final int ANIM_DURATION = 300;
    public static final int FOCUS_FL_RAD = 6;
    public static final int DIR_FL_RAD = 5;
    public static final int FOCUS_FL_WIDTH = 2;
    public static final int START_TORCHES = 10;
    
    
    public static final int NORMAL = 0;
    public static final int WIN = 1;
    public static final int LOSE = 2;
        
    private SpriteSet spriteSet, walking, swimming, dark, darkSwimming, invis, invisSwimming;
    private Animation sprite;
    
    private Input input;
    private Map map;
    
    private FocusedLightSource flashlight;
    private DirectionalLightSource flashlight2;
    private LightSource light;
    
    private int angle;
    
    private boolean godMode;
    private boolean lightOn;
    private boolean action;
    private boolean portal;
    private boolean key;
    private boolean allCoins;
    private boolean invisible;
    private volatile boolean refill;
    
    private float moveSpeed;
    
    private int coins;
    private int direction;
    private int actionWait;
    private int torches;
    private int status;
    
    private double seconds;
    private long start;
    private long startRefill;
    
    private float detection;
    
    private float screenX;
    private float screenY;
    
    public Jonas(float x, float y, Input input, Map map, ObjectMap obj)
    {
        super(x, y, map, obj);
        
        this.input = input;
        this.map = map;
        
        portal = false;
        key = false;
        
        walking = new SpriteSet(0);
        swimming = new SpriteSet(1);
        dark = new SpriteSet(2);
        darkSwimming = new SpriteSet(3);
        invis = new SpriteSet(8);
        invisSwimming = new SpriteSet(9);
        
        spriteSet = walking;
        
        direction = SpriteSet.DOWN;
        actionWait = 0;
        detection = 0;
        status = 0;
        seconds = START_SECONDS;
        start = 0L;
        
        torches = START_TORCHES;
        
        action = false;
        allCoins = false;
        invisible = false;
        refill = false;
        
        //REST OF INITIALISATION
        moveSpeed = WALK_SPEED;
        
        if (inDark() && inWater())
        {
            spriteSet = darkSwimming;
        }
        else if (inWater())
        {
            spriteSet = swimming;
        }
        else if (inDark())
        {
            spriteSet = dark;
        }        

        sprite = spriteSet.getSprite(direction);
        
        angle = 4;
        
        lightOn = false;
        
        flashlight = new FocusedLightSource((int) getX(),(int) getY(), FLASH_LUX, FOCUS_FL_RAD, angle, FOCUS_FL_WIDTH);
        flashlight2 = new DirectionalLightSource((int) getX(),(int) getY(), FLASH_LUX, DIR_FL_RAD, angle);
        
        light = flashlight;
        
        //TODO change
        godMode = true;
    }
    
        
    public boolean isAction()
    {
        return action;
    }
    
    
    @Override
    public void update(int delta)
    {
        float dx = getX();
        float dy = getY();
        
        //----------------------DETECTION-------------------------
        float add =(1.0f - map.getLightTileAt((int) dx + SPRITE_WIDTH / 2,(int) dy + SPRITE_HEIGHT / 2).getAlpha() * DET_FACTOR);
        
        if (invisible)
        {
            add = 0;
        }
        
        if (add == 0)
        {
            detection -= DET_RECOVER;
            if (detection < 0)
            {
                detection = 0;
            }
        }
        else
        {
            detection += add;
            if (detection > 100)
            {
                detection = 100;
            }
        }       
        //----END DETECTION------------------------------------------------------
        
        //action calculations
        if (input.isKeyDown(Input.KEY_SPACE))
        {
            if (actionWait == 0)
            {
                Audio.tap();
                action = true;
                actionWait++;
            }
            else
            {
                action = false; 
                
                actionWait++;
                
                if (actionWait >= 30)
                {
                    actionWait = 0;
                }
            }
        }
        else
        {
            actionWait = 0;
            action = false;
        }
        
        
        //invisibility
        if (input.isKeyDown(Input.KEY_LSHIFT) && seconds > 0)
        {
            if (invisible == false && seconds > 0)
            {
                invisible = true;
                start = System.nanoTime();
            }
            if (seconds > 0)
            {
                seconds -= (double) ((System.nanoTime() - start) / 1000000000.0);
                start = System.nanoTime();
                
                if (seconds < 0)
                {
                    seconds = 0;
                }
            }
        }
        else
        {
            invisible = false;
        }
        
        
        if (input.isKeyPressed(Input.KEY_E))
        {
            setLight(!lightOn);
        }
        if (input.isKeyPressed(Input.KEY_R))
        {
            swapFlashlights();
        }
       
        if (invisible && inWater())
        {
            spriteSet = invisSwimming;
        }
        else if (invisible)
        {
            spriteSet = invis;
        }
        else if (inDark() && inWater())
        {
            spriteSet = darkSwimming;
        }
        else if (inWater())
        {
            spriteSet = swimming;
        }
        else if (inDark())
        {
            spriteSet = dark;
        }
        else
        {
            spriteSet = walking;
        }
        
        boolean isHandled = false;
        
        //TODO
        if (((input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))  && (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))) || ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) && (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))))
        {
            isHandled = true;
        }
        else if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) //UP------------------------------
        {
            angle = 0;
            direction = SpriteSet.UP;
            sprite = spriteSet.getSprite(direction);
            sprite.update(delta);
            
            if ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) && canMove(0, (int) getX(), (int) (getY() - delta * DIAGONAL_FACTOR * moveSpeed)) && canMove(3, (int) (getX() - delta * DIAGONAL_FACTOR * moveSpeed), (int) getY())) //UP LEFT
            {
                angle = 7;
                
                dx -= delta * DIAGONAL_FACTOR * moveSpeed;
                dy -= delta * DIAGONAL_FACTOR * moveSpeed;
                
                setY(dy);
                setX(dx);
                
                isHandled = true;
            }
            else if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) && canMove(0, (int) getX(), (int) (getY() - delta * DIAGONAL_FACTOR * moveSpeed)) && canMove(1, (int) (getX() + delta * DIAGONAL_FACTOR * moveSpeed), (int) getY()))//UP RIGHT
            {
                angle = 1;
                
                dx += delta * DIAGONAL_FACTOR * moveSpeed;
                dy -= delta * DIAGONAL_FACTOR * moveSpeed;
                
                setY(dy);
                setX(dx);
                
                isHandled = true;
            }
            else
            {    
                dy -= delta * moveSpeed;
                if (canMove(0, (int) dx, (int) dy))
                {
                    setY(dy);
                } 
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) //DOWN--------------------------
        {
            angle = 4;     
            direction = SpriteSet.DOWN;
            sprite = spriteSet.getSprite(direction);
            sprite.update(delta);
            
            if ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) && canMove(2, (int) getX(), (int) (getY() + delta * DIAGONAL_FACTOR * moveSpeed)) && canMove(3, (int) (getX() - delta * DIAGONAL_FACTOR * moveSpeed), (int) getY())) //DOWN LEFT
            {
                angle = 5;
                
                dx -= delta * DIAGONAL_FACTOR * moveSpeed;
                dy += delta * DIAGONAL_FACTOR * moveSpeed;
 
                setY(dy);
                setX(dx);
                
                isHandled = true;
            }
            else if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) && canMove(2, (int) getX(), (int) (getY()+ delta * DIAGONAL_FACTOR * moveSpeed)) && canMove(1, (int) (getX() + delta * DIAGONAL_FACTOR * moveSpeed), (int) getY())) //DOWN RIGHT
            {
                angle = 3;
                
                dx += delta * DIAGONAL_FACTOR * moveSpeed;
                dy += delta * DIAGONAL_FACTOR * moveSpeed;
                
                setY(dy);
                setX(dx);
                
                isHandled = true;
            }
            else
            {
                dy += delta * moveSpeed;
                if (canMove(2, (int) dx, (int) dy))
                {
                    setY(dy);
                } 
            }
        }
        
        if (!isHandled && (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))) //LEFT---------------------------
        {
            angle = 6;
            direction = SpriteSet.LEFT;
            sprite = spriteSet.getSprite(direction);
            sprite.update(delta);
            dx -= delta * moveSpeed;
            
            if (canMove(3, (int) dx, (int) dy))
            {
                setX(dx);
            }
        }
        else if (!isHandled && (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))) //RIGHT--------------------------
        {
            angle = 2;
            direction = SpriteSet.RIGHT;
            sprite = spriteSet.getSprite(direction);
            sprite.update(delta);
            dx += delta * moveSpeed;
            
            if (canMove(1, (int) dx, (int) dy))
            {
                setX(dx);
            }
        }
        else if (inWater())
        {
            sprite = spriteSet.getSprite(direction);
            sprite.update(delta);
        }
        else
        {
            sprite = spriteSet.getSprite(direction);
        }
        
        if (inWater())
        {
            moveSpeed = SWIM_SPEED;
        }
        else if (onDirt())
        {
            moveSpeed = DIRT_SPEED;
        }
        else
        {
            moveSpeed = WALK_SPEED;
        }
            
        //PORTAL MANAGEMENT
        if (map.getTileAt((int) (getX() + Jonas.SPRITE_WIDTH / 2), (int) (getY() + Jonas.SPRITE_HEIGHT / 2)) instanceof PortalTile && PortalTile.getPortals().size() > 1)
        {
            if (!portal)
            {
                portal = true;

                PortalTile p = (PortalTile) map.getTileAt((int) getX() + Jonas.SPRITE_WIDTH / 2, (int) getY() + Jonas.SPRITE_HEIGHT / 2);

                int n = p.getID() + 1;
                if (p.getID() + 1 >= PortalTile.getPortals().size())
                {
                    n = 0;
                }

                PortalTile next = PortalTile.getPortals().get(n);
                setX(next.getX());
                setY(next.getY());
                Audio.portal();
            } 
        }
        else 
        {
            portal = false;
        }
        //END PORTAL MANAGEMENT
        
        light.setMode(lightOn);
        ((DirectionalLightSource)light).setAngle(angle);
        light.setX((int) (getX() + (Main.TILE_WIDTH / 2)));
        light.setY((int) (getY() + (Main.TILE_HEIGHT / 2)));
    }
    
    public void setLight(boolean b)
    {
        lightOn = b;
    }
    
    public void swapFlashlights()
    {
        light.setMode(false);
        
        if (light == flashlight)
        {
            light = flashlight2;
        }
        else
        {
            light = flashlight;
        }
        
        light.setMode(lightOn);
    }
    
    @Override
    public void render(float x, float y)
    {
        sprite.draw((int) x,(int) y);
    }
    
    public float getCenterX()
    {
        return getX() + (SPRITE_WIDTH / 2);
    }
    
    public float getCenterY()
    {
        return getY() + (SPRITE_HEIGHT / 2);
    }

    @Override
    public LightSource getLightSource()
    {
        return light;
    }
    
    private boolean inWater()
    {
        return map.getTileAt((int) getCenterX(), (int) getCenterY()) instanceof WaterTile;
    }
    
    private boolean inDark()
    {
        return map.getLightTileAt((int) getCenterX(), (int) getCenterY()).getAlpha() > .95f;
    }
    
    private boolean onDirt()
    {
        return map.getTileAt((int) getCenterX(), (int) getCenterY()) instanceof DirtTile;
    }
    
    public void addCoin()
    {
        coins++;
    } 
    
    public int getCoins()
    {
        return coins;
    }

    /**
     * @return the dropTorch
     */
    public boolean isDropTorch() 
    {
        boolean b = input.isKeyPressed(Input.KEY_Q) && !inWater() && torches > 0;
        if (b)
        {
            torches--;
        }
        return b;
    }
    
    @Override
    public boolean show()
    {
        //used to bypass drawing in objmap
        return false;
    }

    public float getDetection() 
    {
        return detection;
    }
    
    public boolean hasKey()
    {
        return key;
    }
    
    public void giveKey()
    {
        key = true;
        Audio.beep();
    }

    /**
     * @return the torches
     */
    public int getTorches() {
        return torches;
    }
    
    public int status()
    {   
        if (!godMode && status == LOSE)
        {
            return status;
        }
        
        if (status == WIN)
        {
            return status;
        }
        
        if (detection == 100)
        {
            return status = LOSE;
        }
        else if (map.getTileAt((int) getX(),(int) getY()) instanceof FlagTile)
        {
            if (((FlagTile) map.getTileAt((int) getX(),(int) getY())).isUnlocked() && allCoins)
            {
                return status = WIN;
            }
        }

        return status = NORMAL;
    }
    
    public void setAllCoins(boolean b)
    {
        allCoins = b;
    }
    
    public int getSeconds()
    {
        return (int) seconds;
    }
    
    public void enemyTouch()
    {

    }

    @Override
    public void interact(Entity e) 
    {
        enemyTouch();
    }
    
    @Override
    public String toString()
    {
        return "Jonas";
    }
    
    public void refill()
    {
        if (seconds >= MAX_SECONDS)
        {
            refill = false;
            return;
        }
        
        if (!refill)
        {
            refill = true;
            startRefill = System.nanoTime();
            return;
        }
        
        seconds += (double) ((System.nanoTime() - startRefill) / 1000000000.0);
        startRefill = System.nanoTime();
    }
    
    public void stopRefill()
    {
        refill = false;
    }

    public void setScreenX(float xPos) 
    {
        screenX = xPos;
    }

    public void setScreenY(float yPos) 
    {
        screenY = yPos;
    }
    
    public float getScreenX()
    {
        return screenX;
    }
    
    public float getScreenY()
    {
        return screenY;
    }
}
