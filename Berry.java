import java.awt.*;
import javax.imageio.ImageIO;

public class Berry extends MapObject{ //purple berries give an amount of health back, red berries heal much more
    private int recover;
    private static Image[] berries;
    private double mapx,mapy;
    public Berry(Map map,double x, double y){
	super(map,x,y);
	if(Math.random()*15 < 1){ //super
	    recover = 120;
	    try{
		img = ImageIO.read(getClass().getResourceAsStream("superberry.png"));
	    } catch(Exception e){
		e.printStackTrace();
	    }
	}
	else{
	    recover = 12;
	    try{
		img = ImageIO.read(getClass().getResourceAsStream("berry.png"));
	    } catch(Exception e){
		e.printStackTrace();
	    }

	}
	width = img.getWidth();
	height = img.getHeight();
    }
    public void draw(Graphics g){
	mapx = map.getX();
	mapy = map.getY();
	g.drawImage(img, (int)(x-mapx-width/2), (int)(y-mapy-height/2), null);
    }
    public int recoverHealth(){
	return recover;
    }
}
