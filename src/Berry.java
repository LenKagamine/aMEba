import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class Berry extends MapObject{
	private int recover;
	
	public Berry(Map map,double x, double y){
		super(map,x,y);
		if(Math.random()*15 < 1){ //chance to become superberry
			recover = 120;
			try{
				img = ImageIO.read(getClass().getResourceAsStream("superberry.png")); //get image
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			recover = 20; //recover health
			try{
				img = ImageIO.read(getClass().getResourceAsStream("berry.png"));
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		width = img.getWidth();
		height = img.getHeight();
	}
	
	public void draw(Graphics2D g){ //draws berry
		mapx = map.getX();
		mapy = map.getY();

		if(x-mapx+width/2 > 0 && y-mapy+height/2 > 0 &&
		   x-mapx-width/2 < GamePanel.WIDTH && y-mapy-height/2 < GamePanel.HEIGHT)
			g.drawImage(img, (int)(x-mapx-width/2), (int)(y-mapy-height/2), null);
	}
	
	public int recoverHealth(){ //returns amount to recover
		return recover;
	}
}
