package oslo;

import entity.Jonas;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 * Creates, holds, and manages a set of sprites for a character
 * @author Jeremy Bassi
 */
public class SpriteSet 
{
    private Animation[] anims; //down, up, left, right
    
    //class constants for the direction of the sprite to be used in other classes
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
            
    /**
     * Cleanly and easily creates a SpriteSet based on a row number indicating
     * the row where the sprites are stored on the spritesheet.
     * @param y 
     */
    public SpriteSet(int y)
    {
        anims = new Animation[4];
        int fpa = 2; //frames per anim
        Image[] frames = new Image[fpa];
        
        for (int i = 0; i < anims.length; i++)
        {
            for (int j = 0; j < fpa; j++)
            {
                frames[j] = Main.spriteSheet.getSprite(i * 2 + j, y);
            }
            
            anims[i] = new Animation(frames, Jonas.ANIM_DURATION, false);
        }   
    }

    /**
     * Used to retrieve the animation in the user-specified direction
     * @param a - number corresponding to class constants determining direction
     * @return Animation in the given direction
     */
    public Animation getSprite(int a)
    {
        return anims[a];
    }
}
