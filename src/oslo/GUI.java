package oslo;

import entity.Jonas;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;

/**
 *
 * @author Jeremy Bassi
 */
public class GUI 
{
    private static final String METER_PATH = "res/gui/meter.png";
    private static final String COIN_PATH = "res/gui/coin.png";
    private static final String FONT_PATH = "res/other/font.png";
    private static final String BAR_PATH = "res/gui/bar.png";
    private static final String KEY_PATH = "res/gui/key.png";
    private static final String KEY_LINE_PATH = "res/gui/keyline.png";
    private static final String TORCH_PATH = "res/gui/torch.png";
    private static final String INVIS_PATH = "res/gui/invis.png";
    
    private Image meter;
    private Image coin;
    private Image bar;
    private Image torch;
    private Image keyLine;
    private Image key;
    private Image invis;
    private static SpriteSheetFont font;
    
    private Jonas j;
    
    private int coins;
    private int torches;
    private int seconds;
    private int totalTorches;
    private int totalSeconds;
    private int detection;
    
    private int mWidth;
    private int mHeight;
    private int mX;
    private int mY;
    private int cX;
    private int cY;
    private int tX;
    private int tY;
    private int sX;
    private int sY;
    private int altMX;
    private int altMY;
    private int altCX;
    private int altCY;
    private int altTX;
    private int altTY;
    private int altSX;
    private int altSY;
    private int totalCoins;
    
    
    private ArrayList<Ticker> tickers;
    
    private boolean signal;
    
    public GUI(Jonas j, int coins)
    {
        this.j = j;
        
        this.coins = j.getCoins();
        detection = (int) j.getDetection();
        totalCoins = coins;
        totalTorches = torches = j.getTorches();
        seconds = j.getSeconds();
        totalSeconds = j.getSeconds();
        
        signal = false;
        
        tickers = new ArrayList<>();
        
        try
        {
            meter = new Image(METER_PATH);
            coin = new Image(COIN_PATH);
            bar = new Image(BAR_PATH);
            key = new Image(KEY_PATH);
            keyLine = new Image(KEY_LINE_PATH);
            torch = new Image(TORCH_PATH);
            invis = new Image(INVIS_PATH);
            
            meter.setFilter(Image.FILTER_NEAREST);
            coin.setFilter(Image.FILTER_NEAREST);
            bar.setFilter(Image.FILTER_NEAREST);
            key.setFilter(Image.FILTER_NEAREST);
            keyLine.setFilter(Image.FILTER_NEAREST);
            torch.setFilter(Image.FILTER_NEAREST);
            invis.setFilter(Image.FILTER_NEAREST);
            
            font = new SpriteSheetFont(new SpriteSheet(FONT_PATH, 8, 8), ' ');
        } catch (SlickException e)
        {
            System.err.println(e.getMessage());
        }
        
        /* TICKER TESTING
        ticker = new Ticker(0, 0);
        tr = new TickerReader();
        ticker.add(tr.read(TickerReader.OPEN));
        ticker.start();
        tickers.add(ticker);
        */
        
        
        //Calculations for placement of GUI elements
        //TODO documentation and elim hard code
        mWidth = (int) (Main.WIDTH / (2 * Main.XSCALE));
        mHeight = Main.TILE_HEIGHT;
        
        //meter ----
        mX = (int) (((Main.WIDTH / Main.XSCALE) - (mWidth)) / 2);
        mY = (int) ((Main.HEIGHT / Main.YSCALE) - (mHeight));
        
        //coin ----
        cX = (int) ((Main.WIDTH / Main.XSCALE) - (coin.getWidth() + Main.TILE_WIDTH * 3));
        cY = (int) (0);
        
        //torch ----
        tX = (int) ((Main.WIDTH/ Main.XSCALE) - (coin.getWidth() + Main.TILE_WIDTH * 7));
        tY = (int) (0);
        
        //seconds ----
        sX = (int) ((Main.WIDTH/ Main.XSCALE) - (coin.getWidth() + Main.TILE_WIDTH * 10));
        sY = (int) (0);
        
        //alt meter
        altMX = (int) (0);
        altMY = (int) 0;
        
        //alt coin 
        altCX = (int) (mX + Main.TILE_WIDTH * 8);
        altCY = (int) ((Main.HEIGHT / Main.YSCALE) - (mHeight * 2));
        
        //alt torch
        altTX = (int) (mX + Main.TILE_WIDTH * 4);
        altTY = (int) ((Main.HEIGHT / Main.YSCALE) - (mHeight * 2));
        
        //alt seconds
        altSX = (int) (mX+ Main.TILE_WIDTH * 1);
        altSY = (int) ((Main.HEIGHT / Main.YSCALE) - (mHeight * 2));
    }
    
    public void addTicker(Ticker t)
    {
        tickers.add(t);
    }
    
    public void sweepTickers()
    {
        for (int i = 0; i < tickers.size(); i++)
        {
            if (tickers.get(i).isDone())
            {
                tickers.remove(i--);
            }
        }
    }
    
    public void render()
    {
        //control rendering of GUI elements : bar & stats
        if (bottomContact())
        {
            barDraw(altMX, altMY);
            statDraw(cX, cY, tX, tY, sX, sY);
        }
        else if (topContact())
        {
            barDraw(mX, mY);
            statDraw(altCX, altCY, altTX, altTY, altSX, altSY);
        }
        else 
        {
            barDraw(mX, mY);
            statDraw(cX, cY, tX, tY, sX, sY);
        }     
        
        sweepTickers();
        renderTickers();
    }
    
    public void renderTickers()
    {
        int a = 0;
        for (Ticker t : tickers)
        {
            if (t.isRunning() && t.getCurrent() != null)
            {
                int lim = Math.min((int) ((((Main.HEIGHT / Main.YSCALE) - t.getY()) / Main.TILE_WIDTH) - 2), slice(t.getCurrent(), t.getX()).length);
                
                for (int i = 0; i < lim; i++)
                {
                    font.drawString(t.getX(), (a * Main.TILE_HEIGHT) + 1 + t.getY() + Main.TILE_HEIGHT * (i + 1) + i * 1, slice(t.getCurrent(), t.getX())[i]);
                }
                
                a += lim;
            }
        }
    }
    
    public void updateTickers()
    {
        for (Ticker t : tickers)
        {
            if (t.hasNext())
            {
                t.next();
            }
        }
    }
    
    private String[] slice(String s, int x)
    {
        //amount of letters on one line ***
        int length = (int) ((Main.WIDTH / Main.XSCALE) - x) / Main.TILE_WIDTH;
        
        if (s.length() <= length)
        {
            return new String[] {s};
        }
        
        String[] arr = new String[(int) (s.length() / length) + 1];
        
        boolean b = true;
        String str = s;
        int i = 0;
        
        while(b)
        {
            arr[i] = str.substring(0, length);
            str = s.substring(++i * length);
            if (str.length() < length)
            {
                b = false;
                arr[i] = str;
            }
        }
        
        return arr;
    }
    
    public void update(int delta)
    {
        coins = j.getCoins();
        detection = (int) j.getDetection();
        torches = j.getTorches();
        seconds = j.getSeconds();
        
        //ticker
        updateTickers();
        
        if (!signal && coins == totalCoins)
        {
            signal = true;
            Audio.signal();
            j.setAllCoins(true);
        }
    } 
    
    public static void drawString(int x, int y, String s)
    {
        font.drawString(x, y, s.toUpperCase());
    }

    public void resize() 
    {
        mWidth = (int) (Main.WIDTH / (2 * Main.XSCALE));
        mHeight = Main.TILE_HEIGHT;
        mX = (int) (((Main.WIDTH / Main.XSCALE) - (mWidth)) / 2);
        mY = (int) ((Main.HEIGHT / Main.YSCALE) - (mHeight));
        cX = (int) ((Main.WIDTH / Main.XSCALE) - (coin.getWidth() + Main.TILE_WIDTH * 3));
        cY = (int) (0);
    }
    
    private boolean bottomContact()
    {
        return ((j.getScreenX() + Jonas.SPRITE_WIDTH - 1 > mX) && (j.getScreenX() < mX + mWidth) && (j.getScreenY() + Jonas.SPRITE_HEIGHT - 1> mY));
    }
    
    private boolean topContact()
    {
        return ((j.getScreenX() + Jonas.SPRITE_WIDTH  - 1 > sX) && (j.getScreenY() + 1 < coin.getHeight()));
    }
    
    private void barDraw(int x, int y)
    {
        int width = (int) (mWidth * (detection / 100.0f));
        int bWidth = (int) (bar.getWidth() * (detection / 100.0f));
        
        meter.draw(x, y, mWidth, mHeight);
        bar.draw(x, y, x + width, y + mHeight, 0, 0, bWidth, bar.getHeight()); //dont understand why, but it works!
    }
    
    private void statDraw(int cX, int cY, int tX, int tY, int sX, int sY)
    {
        coin.draw(cX, cY);
        
        torch.draw(tX, tY);
        
        invis.draw(sX, sY);
        
        if (j.hasKey())
        {
            key.draw(cX - Main.TILE_WIDTH, cY);
        }
        else
        {
            keyLine.draw(cX - Main.TILE_WIDTH, cY);
        }
        
        if (totalCoins < 10)
        {
            font.drawString(cX + 3 * Main.TILE_WIDTH, cY, Integer.toString(totalCoins), Color.darkGray);
        }
        else if (totalCoins < 100)
        {
            font.drawString(cX + 2 * Main.TILE_WIDTH, cY, Integer.toString(totalCoins), Color.darkGray);
        }
        else
        {
            font.drawString(cX + Main.TILE_WIDTH, cY, Integer.toString(totalCoins), Color.darkGray);
        }
        
        if (coins < 10)
        {
            font.drawString(cX + 3 * Main.TILE_WIDTH, cY, Integer.toString(coins));
        }
        else if (coins < 100)
        {
            font.drawString(cX + 2 * Main.TILE_WIDTH, cY, Integer.toString(coins));
        }
        else
        {
            font.drawString(cX + Main.TILE_WIDTH, cY, Integer.toString(coins));
        }
        
        if (totalTorches < 10)
        {
            font.drawString(tX + 2 * Main.TILE_WIDTH, tY, Integer.toString(totalTorches), Color.darkGray);
        }
        else
        {
            font.drawString(tX + Main.TILE_WIDTH, tY, Integer.toString(totalTorches), Color.darkGray);
        }
        
        if (torches < 10)
        {
            font.drawString(tX + 2 * Main.TILE_WIDTH, tY, Integer.toString(torches));
        }
        else
        {
            font.drawString(tX + Main.TILE_WIDTH, tY, Integer.toString(torches));
        }
        
        if (totalSeconds < 10)
        {
            font.drawString(sX + 2 * Main.TILE_WIDTH, tY, Integer.toString(totalSeconds), Color.darkGray);
        }
        else
        {
            font.drawString(sX + Main.TILE_WIDTH, sY, Integer.toString(totalSeconds), Color.darkGray);
        }
        
        if (seconds < 10)
        {
            font.drawString(sX + 2 * Main.TILE_WIDTH, sY, Integer.toString(seconds));
        }
        else
        {
            font.drawString(sX + Main.TILE_WIDTH, sY, Integer.toString(seconds));
        }
    }
}
