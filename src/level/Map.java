package level;

import entity.Item;
import java.util.ArrayList;
import oslo.Main;

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
    
    private ArrayList<LightSource> lights;
    
    public Map(Tile[][] tiles)
    {
        tileWidth = Main.TILE_WIDTH;
        tileHeight = Main.TILE_HEIGHT;
        
        width = tiles.length;
        height = tiles[0].length;
        
        this.tiles = tiles;
        lighting = new LightingTile[width][height];
        
        lights = new ArrayList<>();

        
        stdLight = new LightingTile().getAlpha();
        
        //Create the lighting field
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                lighting[i][j] = new LightingTile();
            }
        }
    }
    
    public void setLightArray(ArrayList<LightSource> arr)
    {
        lights = arr;
    }
    
    public void addLightSources(ArrayList<LightSource> arr)
    {
        for (LightSource l : arr)
        {
            addLightSource(l);
        }
    }
    
    public void lightFlush()
    {
        for (LightingTile[] arr : lighting)
        {
            for (LightingTile t : arr)
            {
                t.setAlpha(stdLight);
            }
        }
    }
    
    public void addLightSource(LightSource l)
    {
        lights.add(l);
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
                tiles[i][j].render(((i * tileWidth) + (int) x), ((j * tileHeight) + (int) y));
            }
        }
    }
    
    public void setLightMapAlpha(float alpha)
    {
        stdLight = alpha;
        
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                lighting[i][j].setAlpha(alpha);
            }
        }
    }
    
    public boolean removeLightSource(LightSource l)
    {
        return lights.remove(l);
    }
    
    public void renderLighting()
    {
        lightFlush();
        
        for (LightSource l : lights)
        {
            if (l.isOn())
            {
                if (l instanceof FocusedLightSource)
                {
                    renderFocusedLightSource((FocusedLightSource) l);
                }
                else if (l instanceof DirectionalLightSource)
                {
                    renderDirectionalLightSource((DirectionalLightSource) l);
                }
                else
                {
                    renderLightSource(l);
                }
            }
        }
    }
    
    private void renderDirectionalLightSource(DirectionalLightSource l)
    {
        int radius = l.getRadius();
        int lux = l.getLux();
        int x = l.getX();
        int y = l.getY();
        int angle = l.getAngle();
        
        //lux bound limit
        if (lux < 0) 
        {
            lux = 0;
        }
        else if (lux > 100)
        {
            lux = 100;
        }
        
        //angle bound limit
        if (angle < 0)
        {
            angle = 0;
        }
        else if (angle > 7)
        {
            angle = (int) (Math.random() * 8);
        }
        
        float baseAlpha = -.01f * lux + 1.0f;
        float decr = (1.0f - baseAlpha) / (radius);
        
        int dx = 1;
        int dy = 1;
        
        if (angle % 2 == 0) //even angles 
        {
            if (angle == DirectionalLightSource.LEFT)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * -i));
                        int yf = (int) (y + (Main.TILE_HEIGHT * -j));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));

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

                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * -i));
                        int yf = (int) (y + (Main.TILE_HEIGHT * j));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
            else if (angle == DirectionalLightSource.RIGHT)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * i));
                        int yf = (int) (y + (Main.TILE_HEIGHT * -j));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));

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

                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * i));
                        int yf = (int) (y + (Main.TILE_HEIGHT * j));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
            else if (angle == DirectionalLightSource.UP)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * -j));
                        int yf = (int) (y + (Main.TILE_HEIGHT * -i));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));

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

                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * j));
                        int yf = (int) (y + (Main.TILE_HEIGHT * -i));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
            else //DOWN
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * j));
                        int yf = (int) (y + (Main.TILE_HEIGHT * i));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));

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

                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * -j));
                        int yf = (int) (y + (Main.TILE_HEIGHT * i));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null || i + j >= i + i) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
        }
        else //ODD ANGLES
        {
            if (angle == DirectionalLightSource.DOWN_RIGHT)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        if (j == 0 && i == 0)
                        {
                            continue;
                        }
                        int xf = (int) (x + (Main.TILE_WIDTH * j));
                        int yf = (int) (y + (Main.TILE_HEIGHT * i));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
            else if (angle == DirectionalLightSource.UP_RIGHT)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        if (j == 0 && i == 0)
                        {
                            continue;
                        }
                        int xf = (int) (x + (Main.TILE_WIDTH * i));
                        int yf = (int) (y + (Main.TILE_HEIGHT * -j));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
            else if (angle == DirectionalLightSource.DOWN_LEFT)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        if (j == 0 && i == 0)
                        {
                            continue;
                        }
                        int xf = (int) (x + (Main.TILE_WIDTH * -i));
                        int yf = (int) (y + (Main.TILE_HEIGHT * j));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
            else if (angle == DirectionalLightSource.UP_LEFT)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= radius; j++)
                    {
                        if (j == 0 && i == 0)
                        {
                            continue;
                        }
                        int xf = (int) (x + (Main.TILE_WIDTH * -i));
                        int yf = (int) (y + (Main.TILE_HEIGHT * -j));

                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        if (t == null) //check if tile exists!!
                        {
                            continue;
                        }

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));

                        //set the alpha of the tile, brightest light at loc det. brightness
                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
            
        }
        
        /*
        for (int i = 0; i <= radius; i++)
        {
            for (int j = 0; j <= radius; j++)
            {
                int xf = (int) (x + (Main.TILE_WIDTH * i));
                int yf = (int) (y + (Main.TILE_HEIGHT * -j));
                
                //get the light tile at the pos
                LightingTile t = getLightTileAt(xf, yf);
                
                if (t == null || i + j >= i + i) //check if tile exists!!
                {
                    continue;
                }
                
                //distance between source and tile
                double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));
                
                //set the alpha of the tile, brightest light at loc det. brightness
                float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
                
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
        
        for (int i = 0; i <= radius; i++)
        {
            for (int j = 0; j <= radius; j++)
            {
                int xf = (int) (x + (Main.TILE_WIDTH * i));
                int yf = (int) (y + (Main.TILE_HEIGHT * j));
                
                //get the light tile at the pos
                LightingTile t = getLightTileAt(xf, yf);
                
                if (t == null || i + j >= i + i) //check if tile exists!!
                {
                    continue;
                }
                
                //distance between source and tile
                double dis = Math.sqrt(Math.pow(i,2) + Math.pow(j,2));
                
                //set the alpha of the tile, brightest light at loc det. brightness
                float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
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
        } */
    }
    
    private void renderFocusedLightSource(FocusedLightSource l)
    {
        int radius = l.getRadius();
        int lux = l.getLux();
        int x = l.getX();
        int y = l.getY();
        int angle = l.getAngle();
        int width = l.getWidth();
        
        //lux bound limit
        if (lux < 0) 
        {
            lux = 0;
        }
        else if (lux > 100)
        {
            lux = 100;
        }
        
        //angle bound limit
        if (angle < 0)
        {
            angle = 0;
        }
        if (angle > 7)
        {
            angle = (int) (Math.random() * 8);
        }
        
        float baseAlpha = -.01f * lux + 1.0f;
        float decr = (1.0f - baseAlpha) / radius;
        
        if (angle % 2 == 0) //even
        {
            int dy = 1;
            int dx = 1;
            int iStart = (-width + 1);
            int iFinish = width - 1;
            int jStart = 0;
            int jFinish = radius;
            
            if (angle == DirectionalLightSource.UP)
            {
                dy = -1;
            }
            else if (angle == DirectionalLightSource.RIGHT)
            {
                int temp = iStart;
                int temp0 = iFinish;
                iStart = jStart;
                iFinish = jFinish;
                jStart = temp;
                jFinish = temp0;
                
            }
            else if (angle == DirectionalLightSource.LEFT)
            {
                dx = -1;
                int temp = iStart;
                int temp0 = iFinish;
                iStart = jStart;
                iFinish = jFinish;
                jStart = temp;
                jFinish = temp0;
            }
            
            //adjust so light is beyond source
            if (angle == DirectionalLightSource.LEFT || angle == DirectionalLightSource.RIGHT)
            {
                iStart++;
            }
            else
            {
                jStart++;
            }
            
            for (int i = iStart; i <= iFinish; i++)
            {
                for (int j = jStart; j <= jFinish; j++)
                {   
                    int xf = (int) (x + (Main.TILE_WIDTH * i * dx));
                    int yf = (int) (y + (Main.TILE_HEIGHT * j * dy));
                    
                    if (getLightTileAt(xf, yf) == null)
                    {
                        continue;
                    }
                    
                    //get the light tile at the pos
                    LightingTile t = getLightTileAt(xf, yf);
                    
                    //distance between source and tile
                    double dis = Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
                    
                    float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));
                    
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
        else //all other cases
        {
            int dx = 1;
            int dy = 1;
            
            if (angle == DirectionalLightSource.UP_RIGHT)
            {
                dy = -1;
            }
            else if (angle == DirectionalLightSource.UP_LEFT)
            {
                dy = -1;
                dx = -1;
            }
            else if (angle == DirectionalLightSource.DOWN_LEFT)
            {
                dx = -1;
            }
            
            for (int m = 0; m <= width - 1; m++)
            {
                for (int i = 0; i <= radius; i++)
                {
                    for (int j = 0; j <= width - 1; j++)
                    {
                        int xf = (int) (x + (Main.TILE_WIDTH * i * dx)) + (Main.TILE_WIDTH * m * dx);
                        int yf = (int) (y + (Main.TILE_HEIGHT * i * dy)) + (Main.TILE_WIDTH * j * dy);
                        
                        if (getLightTileAt(xf, yf) == null || (xf == x && yf == y))
                        {
                            continue;
                        }
                        
                        //get the light tile at the pos
                        LightingTile t = getLightTileAt(xf, yf);

                        //distance between source and tile
                        double dis = Math.sqrt(Math.pow(i + m,2) + Math.pow(i + j,2));

                        float k = (float) (stdLight - ((1.0f - baseAlpha) - (dis * decr)));

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
        lightSweep();
    }
    
    public void lightSweep()
    {
        for (LightSource l : lights)
        {
            if (l.isRemoved())
            {
                lights.remove(l);
            }
        }
    }
    
    public Tile getTileAt(int x, int y)
    {   
        //bounds checking
        if (x < 0 || x >= getPixelWidth() || y < 0 || y >= getPixelHeight())
        {
            return null;
        }
        
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
    
    public int getPixelWidth()
    {
        return width * tileWidth;
    }
    
    public int getPixelHeight()
    {
        return height * tileHeight;
    }
    
    public Item[] getItems()
    {
        Item[] arr = new Item[getWidth() * getHeight()];
        
        int i = 0;
        
        for (Tile[] a : tiles)
        {
            for (Tile t : a) 
            {
                if (t instanceof ItemTile)
                {
                    arr[i++] = ((ItemTile) t).getItem();
                }      
            }
        }
        
        return arr;
    }
}
