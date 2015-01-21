import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Rock extends MapObject{
	private static BufferedImage img;
	public Rock(Map map,double x,double y){
		super(map,x,y);
		try{
			img = ImageIO.read(getClass().getResourceAsStream("rock.png"));
			boxwidth = width = img.getWidth();
			boxheight = height = img.getHeight();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g){
		mapx = map.getX();
		mapy = map.getY();
		if(x-mapx+width/2 > 0 && y-mapy+height/2 > 0 && x-mapx-width/2 < GamePanel.WIDTH && y-mapy-height/2 < GamePanel.HEIGHT)
			g.drawImage(img,(int)(x-mapx-width/2),(int)(y-mapy-height/2),width,height,null);
	}
	public boolean checkStuck(double x, double y){
		return (x >= this.x-80 && x <= this.x+80 && y >= this.y-80 && y <= this.y+80);
	}
}
