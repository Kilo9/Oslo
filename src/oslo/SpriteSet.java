package oslo;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 * Creates, holds, and manages a set of sprites
 * @author Jeremy Bassi
 */
public class SpriteSet 
{
    private Animation[] anims; //down, up, left, right
    
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
            
    
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
            
            anims[i] = new Animation(frames, Dag.ANIM_DURATION, false);
        }   
    }
    
    public Animation getSprite(int a)
    {
        return anims[a];
    }
}
