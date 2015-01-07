import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Map{
    private int viewx,viewy;
    private int targetx,targety;
    private BufferedImage bg;
    public Map(){
	viewx = viewy = 0;
	try{
	    bg = ImageIO.read(getClass().getResourceAsStream("bg.png"));
	} catch(Exception e){
	    e.printStackTrace();
	}
    }
    public void update(){
	if(targetx<50)
    }
    public void draw(Graphics g){
	g.drawImage(bg.getSubimage(viewx,viewy,GamePanel.WIDTH,GamePanel.HEIGHT),viewx,viewy,GamePanel.WIDTH,GamePanel.HEIGHT,null);
    }
    public void setTarget(int px,int py){
	targetx = px;
	targety = py; 
    }
}
