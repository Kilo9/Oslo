package oslo;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author Jeremy Bassi
 */
public class Audio 
{
    public static final String BEEP_PATH = "res/sounds/beep.wav";
    public static final String COIN_PATH = "res/sounds/coin.wav";
    public static final String HIT_PATH = "res/sounds/hit.wav";
    public static final String PORTAL_PATH = "res/sounds/portal.wav";
    public static final String TAP_PATH = "res/sounds/tap.wav";
    public static final String SIGNAL_PATH = "res/sounds/signal.wav";
    
    private static Sound beep;
    private static Sound coin;
    private static Sound hit;
    private static Sound portal;
    private static Sound tap;
    private static Sound signal;
    
    static
    {
        try
        {
            beep = new Sound(BEEP_PATH);
            coin = new Sound(COIN_PATH);
            hit = new Sound(HIT_PATH);
            portal = new Sound(PORTAL_PATH);
            tap = new Sound(TAP_PATH);
            signal = new Sound(SIGNAL_PATH);
        } 
        catch (SlickException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public static void beep()
    {
        beep.play();
    }
    
    public static void coin()
    {
        coin.play();
    }
    
    public static void hit()
    {
        hit.play();
    }
    
    public static void signal()
    {
        signal.play();
    }
    
    public static void portal()
    {
        portal.play();
    }
    
    public static void tap()
    {
        tap.play();
    }
}
