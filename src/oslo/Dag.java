/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oslo;

import level.WaterTile;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 *
 * @author Jeremy Bassi
 */
public class Dag implements Renderable
{
    public static final float WALK_SPEED = 0.05f;
    public static final float SWIM_SPEED = 0.02f;
    public static final float DIAGONAL_FACTOR = .7f;
    
    public static final int SPRITE_WIDTH = 8;
    public static final int SPRITE_HEIGHT = 8;
    public static final int ANIM_DURATION = 300;
    
    private float x;
    private float y;
    
    private SpriteSet spriteSet, walking, swimming, dark;
    private Animation sprite;
    
    private Input input;
    private Map map;
    
    private float moveSpeed;
    
    public Dag(Input input, Map map)
    {
        this.input = input;
        this.map = map;
        
        walking = new SpriteSet(0);
        swimming = new SpriteSet(1);
        dark = new SpriteSet(2);
        
        spriteSet = walking;
        
        
        //REST OF INITIALISATION
        moveSpeed = WALK_SPEED;
        
        if (inWater())
        {
            spriteSet = swimming;
        }
        else if (inDark())
        {
            spriteSet = dark;
            System.out.println("true" + map.getLightTileAt(0, 0).getAlpha());
        }

        sprite = spriteSet.getSprite(SpriteSet.DOWN);
    }
    
    public void update(int delta)
    {
        float dx = x;
        float dy = y;
        
        if (inWater())
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
        
        if ((input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_DOWN)) || (input.isKeyDown(Input.KEY_LEFT) && input.isKeyDown(Input.KEY_RIGHT)))
        {
            //nothing
        }
        else if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
        {
            sprite = spriteSet.getSprite(SpriteSet.UP);
            sprite.update(delta);
            
            if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
            {
                dx -= delta * DIAGONAL_FACTOR * moveSpeed;
                dy -= delta * DIAGONAL_FACTOR * moveSpeed;
            }
            else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
            {
                dx += delta * DIAGONAL_FACTOR * moveSpeed;
                dy -= delta * DIAGONAL_FACTOR * moveSpeed;
            }
            else
            {
                dy -= delta * moveSpeed;
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
        {
            sprite = spriteSet.getSprite(SpriteSet.DOWN);
            sprite.update(delta);
            
            if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
            {
                dx -= delta * DIAGONAL_FACTOR * moveSpeed;
                dy += delta * DIAGONAL_FACTOR * moveSpeed;
            }
            else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
            {
                dx += delta * DIAGONAL_FACTOR * moveSpeed;
                dy += delta * DIAGONAL_FACTOR * moveSpeed;
            }
            else
            {
                dy += delta * moveSpeed;
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
        {
            sprite = spriteSet.getSprite(SpriteSet.LEFT);
            sprite.update(delta);
            dx -= delta * moveSpeed;
        }
        else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
        {
            sprite = spriteSet.getSprite(SpriteSet.RIGHT);
            sprite.update(delta);
            dx += delta * moveSpeed;
        }
        
        if (canMove(dx, dy))
        {
            x = dx;
            y = dy;
        }
        
        if (inWater())
        {
            moveSpeed = SWIM_SPEED;
        }
        else
        {
            moveSpeed = WALK_SPEED;
        }
    }
    
    private boolean canMove(float x, float y)
    {
        if (x < 0 || x + SPRITE_WIDTH > map.getPixelWidth() || y < 0 || y + SPRITE_HEIGHT > map.getPixelHeight())
        {
            return false;
        }
        
        //corner checking
        boolean UL = map.getTileAt((int) x, (int) y).canPass();
        boolean BR = map.getTileAt((int) (x + SPRITE_WIDTH), (int) (y + SPRITE_HEIGHT)).canPass();
        boolean UR = map.getTileAt((int) (x + SPRITE_WIDTH), (int) y).canPass();
        boolean BL = map.getTileAt((int) (x), (int) (y + SPRITE_HEIGHT)).canPass();
        
        return UL && BR && UR && BL;
    }
    
    @Override
    public void render(float x, float y)
    {
        sprite.draw((int) x,(int) y);
    }
    
    @Override
    public float getX()
    {
        return x;
    }
    
    @Override
    public float getY()
    {
        return y;
    }
    
    public float getCenterX()
    {
        return x + (SPRITE_WIDTH / 2);
    }
    
    public float getCenterY()
    {
        return y + (SPRITE_HEIGHT / 2);
    }
    
    private boolean inWater()
    {
        return map.getTileAt((int) getCenterX(), (int) getCenterY()) instanceof WaterTile;
    }
    
    private boolean inDark()
    {
        return map.getLightTileAt((int) getCenterX(), (int) getCenterY()).getAlpha() > .95f;
    }
        
}
