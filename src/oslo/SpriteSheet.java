package oslo;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * SpriteSheet class independently created for ease of use and control
 * @author Jeremy Bassi
 */
public class SpriteSheet 
{
    //width and height of the SPRITES as defined by user
    private int width;
    private int height;
    
    //x and y spaces between each sprite
    private int xSpace;
    private int ySpace;
    
    private int xMargin;
    private int yMargin;
    
    //idea taken from original spritesheet class
    private BufferedImage[][] subImages;
    
    private BufferedImage source;
    
    /**
     * Creates a spritesheet from the image path with sprites of defined size
     * @param path - URL to image
     * @param width - width of sprites in pixels
     * @param height - height of sprites in pixels
     */
    public SpriteSheet(String path, int width, int height)
    {
        this(path, width, height, 0, 0, 0, 0);
    }
    
    /**
     * Creates a spritesheet from the image path with sprites of defined size separated
     * by spaces of defined size.
     * @param path - URL to image
     * @param width - width of sprites in pixels
     * @param height - height of sprites in pixels
     * @param xSpace - horizontal space between each sprite
     * @param ySpace - vertical space between each sprite
     */
    public SpriteSheet(String path, int width, int height, int xSpace, int ySpace)
    {
        this(path, width, height, xSpace, ySpace, 0, 0);
    }
    
    /**
     * Creates a spritesheet from the image path with sprites of defined size separated
     * by spaces of defined size, with margins of defined size.
     * @param path - URL to image
     * @param width - width of sprites in pixels
     * @param height - height of sprites in pixels
     * @param xSpace - horizontal space between each sprite
     * @param ySpace - vertical space between each sprite
     * @param xMargin - horizontal margin at the beginning of the sheet
     * @param yMargin - vertical margin at the beginning of the sheet
     */
    public SpriteSheet(String path, int width, int height, int xSpace, int ySpace, int xMargin, int yMargin)
    {
        this.width = width;
        this.height = height;
        this.xSpace = xSpace;
        this.ySpace = ySpace;
        this.xMargin = xMargin;
        this.yMargin = yMargin;
        
        try 
        {
            source = ImageIO.read(SpriteSheet.class.getResource(path));
        } catch (IOException ex) 
        {
            System.err.println(ex.getMessage());
        }
        
        subImages = new BufferedImage[(source.getWidth() - xMargin) / (width + xSpace)][(source.getHeight() - yMargin) / (height + ySpace)];
        
        int x = 0;
        int y = 0;
        
        for (int i = xMargin; i < source.getWidth(); i += width + xSpace)
        {
            for (int j = yMargin; j < source.getHeight(); j += height + ySpace)
            {
                subImages[x][y++] = source.getSubimage(i, j, width, height);
            }
            x++;
            y = 0;
        }
    }
    
    
    
    /**
     * Retrieves the sprite at the desired location.
     * @param x - x location in sprites
     * @param y - y location in sprites
     * @return sprite image
     */
    public Image getSprite(int x, int y)
    {
        //return source.getSubImage(x * (width + xSpace) + xMargin, y * (height + ySpace) + yMargin, width, height);
        return subImages[x][y];
    }
}
