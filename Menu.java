import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Menu{
    private Map map;
    private int mx,my;
    private Button[] btns;
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont;
    public Menu(){
	map = new Map("menubg.png");
	titleColor = new Color(0,204,204);
	titleFont = new Font("Arial",Font.PLAIN,28);
	btns = new Button[]{
	    new Button(GamePanel.WIDTH/2-50,400,100,50,"Play"),
	    new Button(GamePanel.WIDTH/2-50,460,100,50,"Exit")
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
		if(i==0) GamePanel.startGame();
		else if(i==1) System.exit(1);
	    }
	}
    }
    public void mouse(int mx,int my){
	this.mx = mx;
	this.my = my;
    }
}
