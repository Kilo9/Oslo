package entity;

/**
 * Generic class for all item types
 * @author Jeremy Bassi
 */
public abstract class Item extends Renderable
{
    /**
     * Handle interaction between npc and human entities
     * @param e - the entity with which to interact
     */
    public abstract void interact(Entity e);
    
    public boolean contact(Entity e)
    {
        int half = (Jonas.SPRITE_WIDTH / 2);
        float ex = e.getX() + half;
        float ey = e.getY() + half ;
        float tx = getX();
        float ty = getY();
        float bx = tx + 2 * half;
        float by = ty + 2 * half;
        
        return (ex >= tx && ex <= bx) && (ey >= ty && ey <= by);  
    }
}
