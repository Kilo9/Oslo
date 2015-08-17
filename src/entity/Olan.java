package entity;

import level.FocusedLightSource;
import level.LightSource;
import level.Map;
import level.ObjectMap;
import level.WaterTile;
import org.newdawn.slick.Animation;
import oslo.Main;
import oslo.SpriteSet;

/**
 *
 * @author Jeremy Bassi
 */
public class Olan extends LightEntity
{
    private static int num;
    
    public static final float WALK_SPEED = 0.03f;
    public static final int TIMER_INT = 150;
    public static final int L_LUX = 100;
    public static final int L_RAD = 5;
    public static final int L_WIDTH = 2;
    
    private SpriteSet walking;
    private Animation sprite;
    private FocusedLightSource light;
    
    private int timer;
    private int dir;
    private int id;
    
    public Olan(float x, float y, Map map, ObjectMap obj)
    {
        super(x, y, map, obj);
        dir = 0;
        timer = 0;
        walking = new SpriteSet(4);
        sprite = walking.getSprite(SpriteSet.DOWN);
        
        id = num++;
        
        //light init
        light = new FocusedLightSource((int) (x + Main.TILE_WIDTH / 2), (int) (y + Main.TILE_HEIGHT / 2), L_LUX, L_RAD, 4, L_WIDTH);
    }

    @Override
    public void update(int delta)
    {
        float d = delta * WALK_SPEED;
        
        timer++;

        if (timer == TIMER_INT)
        {
            timer = 0;
            dir = (int) (Math.random() * 4);
        }
        
        if (dir == 0)
        {
            if (canMove((int) getX(), (int) (getY() - d)))
            {
                moveUp(delta);
                sprite.update(delta);
            }
            else
            {
                dir = randDir(0);
            }
        }
        if (dir == 1)
        {
            if (canMove((int) (getX() + d), (int) (getY())))
            {
                moveRight(delta);
                sprite.update(delta);
            }
            else
            {
                dir = randDir(1);
            }
        }
        if (dir == 2)
        {
            if (canMove((int) getX(), (int) (getY() + d)))
            {
                moveDown(delta);
                sprite.update(delta);
            }
            else
            {
                dir = randDir(2);
            }
        }
        if (dir == 3)
        {
            if (canMove((int) (getX() - d),(int) ( getY())))
            {
                moveLeft(delta);
                sprite.update(delta);
            }
            else
            {
                dir = randDir(3);
            }
        }
    }
    
    @Override
    public void moveLeft(int delta)
    {
        setX(getX() - delta * WALK_SPEED);
        light.setAngle(6);
        light.setX((int) (getX() + (Main.TILE_WIDTH / 2)));
        sprite = walking.getSprite(SpriteSet.LEFT);
    }
    
    @Override
    public void moveRight(int delta)
    {
        setX(getX() + delta * WALK_SPEED);
        light.setAngle(2);
        light.setX((int) (getX() + (Main.TILE_WIDTH / 2)));
        sprite = walking.getSprite(SpriteSet.RIGHT);
    }
    
    @Override
    public void moveUp(int delta)
    {
        setY(getY() - delta * WALK_SPEED);
        light.setAngle(0);
        light.setY((int) (getY() + (Main.TILE_HEIGHT / 2)));
        sprite = walking.getSprite(SpriteSet.UP);
    }
    
    @Override
    public void moveDown(int delta)
    {
        setY(getY() + delta * WALK_SPEED);
        light.setAngle(4);
        light.setY((int) (getY() + (Main.TILE_HEIGHT / 2)));
        sprite = walking.getSprite(SpriteSet.DOWN);
    }
    
    @Override
    public void render(float x, float y)
    {
        sprite.draw(x, y);
    } 

    @Override
    public LightSource getLightSource() 
    {
        return light;
    }
    
    private int randDir(int out)
    {
        int i = -1;
        
        do
        {
            i = (int) (Math.random() * 4);
        } while (i == out);
        
        return i;
    }
    
    @Override
    public boolean canMove(int x, int y)
    {
        boolean water = false;
        
        boolean UL = getMap().getTileAt((int) x, (int) y) != null && getMap().getTileAt((int) x, (int) y) instanceof WaterTile;
        boolean BR = getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH), (int) (y + Jonas.SPRITE_HEIGHT)) != null && getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH), (int) (y + Jonas.SPRITE_HEIGHT)) instanceof WaterTile;
        boolean UR = getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH), (int) y) != null && getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH), (int) y) instanceof WaterTile;
        boolean BL = getMap().getTileAt((int) (x), (int) (y + Jonas.SPRITE_HEIGHT)) != null && getMap().getTileAt((int) (x), (int) (y + Jonas.SPRITE_HEIGHT)) instanceof WaterTile;
        
        water = UL || BR || UR || BL;
        
        return super.canMove(x, y) && !water;
    }
    
    @Override
    public boolean show()
    {
        return true;
    }

    @Override
    public void interact(Entity e) 
    {
        if (e instanceof Jonas)
        {
            ((Jonas) e).enemyTouch();
        }
    }
    
    @Override 
    public String toString()
    {
        return "Olan " + id;
    }
}
