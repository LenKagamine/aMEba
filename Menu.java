import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Menu{
    private Map map;
    private int mx,my;
    private Button[] btns;
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont;
    private boolean instruct;
    private JPanel gamepanel;
    public Menu(JPanel gamepanel){
	map = new Map("menubg.png");
	this.gamepanel = gamepanel;
	titleColor = new Color(0,204,204);
	titleFont = new Font("Arial",Font.PLAIN,28);
	instruct = false;
	btns = new Button[]{
	    new Button(GamePanel.WIDTH/2-80,350,160,50,"Play"),
	    new Button(GamePanel.WIDTH/2-80,410,160,50,"Instructions"),
	    new Button(GamePanel.WIDTH/2-80,470,160,50,"Exit")
	};
    }

    public void update(){
	map.setPos(mx,my);
    }

    public void draw(Graphics2D g){
	map.draw(g);
	g.setColor(titleColor);
	g.setFont(titleFont);
	g.drawString("aMEba",(GamePanel.WIDTH-g.getFontMetrics().stringWidth("aMEba"))/2,70);
	for(int i=0;i<btns.length;i++) btns[i].draw(g);
    }

    public void click(int mx,int my){
	for(int i=0;i<btns.length;i++){
	    if(btns[i].click(mx,my)){
		if(i==0) GamePanel.setLevel(1);
		else if (i == 1)
		{
		    if (instruct == false) //only open instructions once (to prevent more and more windows)
			gamepanel.add (new Instructions ());
		    instruct = true;
		}
		else if(i==2) System.exit(1);
	    }
	}
    }

    public void mouse(int mx,int my){
	this.mx = mx;
	this.my = my;
    }
}

