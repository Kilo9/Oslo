package entity;

import level.LightSource;

/**
 *
 * @author Jeremy Bassi
 */
public abstract class LightItem extends Item
{
    public abstract LightSource getLightSource();
}
