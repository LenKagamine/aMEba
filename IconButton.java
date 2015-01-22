import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;

public class IconButton{
	private int x,y,width,height;
	private BufferedImage image;
	public IconButton(int x,int y,String img){
		this.x = x; //Set coordinates of button
		this.y = y;
		try{
			image = ImageIO.read(getClass().getResourceAsStream(img));
		} catch(Exception e){}
		width = image.getWidth(); //Get width and height
		height = image.getHeight();
	}
	public void draw(Graphics2D g){
		g.drawImage(image,x,y,null); //Draw image of button
	}
	public boolean click(int mx,int my){
		return (new Rectangle(x,y,width,height)).contains(new Point(mx,my)); //Check if mouse clicked on button
	}
}
