package oslo;

import java.util.ArrayList;
import level.LightingTile;
import level.Tile;

/**
 *
 * @author Jeremy Bassi
 */
public class Map 
{
    private int width; //in tiles
    private int height; //in tiles
    
    private int tileWidth;
    private int tileHeight;
    
    private float stdLight;
    
    private Tile[][] tiles;
    private LightingTile[][] lighting;
    
    private ArrayList<LightSource> sources;
    
    public Map(Tile[][] tiles)
    {
        tileWidth = Main.TILE_WIDTH;
        tileHeight = Main.TILE_HEIGHT;
        height = tiles.length;
        width = tiles[0].length;
        this.tiles = tiles;
        lighting = new LightingTile[height][width];
        sources = new ArrayList<>();
        stdLight = new LightingTile().getAlpha();
        
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                lighting[i][j] = new LightingTile();
            }
        }
    }
    
    public Map()
    {
        tileWidth = Main.TILE_WIDTH;
        tileHeight = Main.TILE_HEIGHT;
        
        //random map for testing
        tiles = new Tile[30][30];

        height = tiles.length;
        width = tiles[0].length;
    }
    
    public void renderMap (float x, float y)
    {
        renderTiles(x, y, tiles);
    }
    
    public void renderLightMap (float x, float y)
    {
        renderTiles(x, y, lighting);
    }
    
    private void renderTiles (float x, float y, Tile[][] tiles)
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                tiles[i][j].render((float) ((i * tileWidth) + x), (float) ((j * tileHeight) + y));
            }
        }
    }
    
    public void setLightMapAlpha(float alpha)
    {
        stdLight = alpha;
        
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                lighting[i][j].setAlpha(alpha);
            }
        }
    }
    
    public void createLightSource(int x, int y, int lux, int radius)
    {
        sources.add(new LightSource(x, y, lux, radius));
    }
    
    public void renderLighting()
    {
        for (LightSource l : sources)
        {
            renderLightSource(l);
        }
    }
    
    /**
     * draw the LightSource object
     * @param x - the x position
     * @param y - the y position
     * @param lux - a positive int value indicating the brightness of the light 1-100
     */
    private void renderLightSource(LightSource l)
    {   
        int radius = l.getRadius();
        int lux = l.getLux();
        int x = l.getX();
        int y = l.getY();
        
        if (lux < 0) 
        {
            lux = 0;
        }
        else if (lux > 100)
        {
            lux = 100;
        }
        
        float baseAlpha = -.01f * lux + 1.0f;
        float decr = (1.0f - baseAlpha) / radius;
        
        for (int i = -radius; i <= radius; i++)
        {
            for (int j = -radius; j <= radius; j++)
            {
                //distance between source and tile
                double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));
                
                if (getLightTileAt((int) (x + (Main.TILE_WIDTH * i)),(int) (y + (Main.TILE_HEIGHT * j))) == null)
                {
                    continue;
                }
                
                //get the light tile at the pos
                LightingTile t = getLightTileAt((int) (x + (Main.TILE_WIDTH * i)),(int) (y + (Main.TILE_HEIGHT * j)));
                
                //set the alpha of the tile
                //t.setAlpha((float) (t.getAlpha() - (1.0 - (dis * decr))));
                
                float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
                //float k = (float) (t.getAlpha() - dis * decr);
                //float k = .5f;
                //System.out.println(x + " " + y + " " + lux + " " + radius);
                
                if (k < t.getAlpha())
                {
                    t.setAlpha(k);
                }
                
                //set alpha to 0 if it has been set below 0
                if (t.getAlpha() < 0f)
                {
                    t.setAlpha(0f);
                }    
                else if (t.getAlpha() > 1.0f)
                {
                    t.setAlpha(1.0f);
                }
            }
        }
    }
    
    public void update(int delta)
    {
        //update each animation tile type
        Tile.waterTile.update(delta);
    }
    
    public Tile getTileAt(int x, int y)
    {   
        return tiles[x / tileWidth][y / tileHeight];
    }
    
    public LightingTile getLightTileAt(int x, int y)
    {   
        if (x < 0 || x >= getPixelWidth() || y < 0 || y >= getPixelHeight())
        {
            return null;
        }
        
        return lighting[x / tileWidth][y / tileHeight];
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public float getPixelWidth()
    {
        return width * tileWidth;
    }
    
    public float getPixelHeight()
    {
        return height * tileHeight;
    }
}
