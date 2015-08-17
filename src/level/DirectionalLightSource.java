package level;

/**
 * 
 * @author Jeremy Bassi
 */
public class DirectionalLightSource extends LightSource
{
    //Up = 0 and then 45 deg incrs CW
    public static final int UP = 0;
    public static final int UP_RIGHT = 1;
    public static final int RIGHT = 2;
    public static final int DOWN_RIGHT = 3;
    public static final int DOWN = 4;
    public static final int DOWN_LEFT = 5;
    public static final int LEFT = 6;
    public static final int UP_LEFT = 7;
    
    /**
     * int value from 0 to 7 representing the angle at which the light will be shining. 
     */
    private int angle; 
    
    public DirectionalLightSource(int x, int y, int lux, int radius, int angle)
    {
        super(x, y, lux, radius);
        this.angle = angle;
    }

    /**
     * @return the angle
     */
    public int getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(int angle) {
        this.angle = angle;
    }
}
