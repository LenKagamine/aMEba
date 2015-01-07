import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Berry //purple berries give an amount of health back, red berries heal completely
{
    private int recover;
    private int x, y;
    private Image berry;
    private boolean superness;
    private static Image[] berries;
    public Berry (int x, int y)
    {
	this.x = x;
	this.y = y;
	superness = false;
	try{
       
	    berries[0] = ImageIO.read(getClass().getResourceAsStream("berry.png"));
	    berries[1] = ImageIO.read(getClass().getResourceAsStream("superberry.png"));
	} catch(Exception e){
	    e.printStackTrace();
	}
	if(Math.random()*10 < 1){
	    superness = true;
	    berry = berries[1];
	}
	else berry = berries[0];
    }

    public void draw (Graphics g){
	g.drawImage(berry, x, y, null);
    }
}
