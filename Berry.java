import java.awt.*;
import javax.imageio.ImageIO;

public class Berry extends MapObject{
    private int recover;
    public Berry(Map map,double x, double y){
	super(map,x,y);
	if(Math.random()*15 < 1){
	    recover = 120;
	    try{
		img = ImageIO.read(getClass().getResourceAsStream("superberry.png"));
	    } catch(Exception e){
		e.printStackTrace();
	    }
	}
	else{
	    recover = 20;
	    try{
		img = ImageIO.read(getClass().getResourceAsStream("berry.png"));
	    } catch(Exception e){
		e.printStackTrace();
	    }
	}
	width = img.getWidth();
	height = img.getHeight();
    }
    public void draw(Graphics2D g){
	mapx = map.getX();
	mapy = map.getY();
	
	if(x-mapx+width/2 > 0 && y-mapy+height/2 > 0 && x-mapx-width/2 < GamePanel.WIDTH && y-mapy-height/2 < GamePanel.HEIGHT)
	    g.drawImage(img, (int)(x-mapx-width/2), (int)(y-mapy-height/2), null);
    }
    public int recoverHealth(){
	return recover;
    }
}
