package entity;

import level.LightSource;
import level.Map;
import level.ObjectMap;

/**
 *
 * @author Jeremy Bassi
 */
public abstract class LightEntity extends Entity
{
    public LightEntity(float x, float y, Map m, ObjectMap o)
    {
        super(x, y, m, o);
    }
    
    public abstract LightSource getLightSource();
    
    public abstract boolean show();
}
