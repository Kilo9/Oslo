package oslo;

import org.newdawn.slick.Image;

/**
 *
 * @author Jeremy Bassi
 */
public class Coin implements Renderable
{
    private Image img;
    private float x, y;
    private int tick;
    
    public Coin(float x, float y)
    {
        img = Main.spriteSheet.getSprite(4, 0);
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(float x, float y) 
    {
        img.draw(x, y);
    }
    
    public void update(int delta)
    {
        tick++;
        
        if (tick == 30)
        {
            int r = (int) ((Math.random() * 10)) - 5;
            int s = (int) ((Math.random() * 10)) - 5;

            x += r;
            y += s;
            tick = 0;
        }
            
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
    
}
