import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Menu{
    private BufferedImage bg;
    private double bgx=0,bgy=0;
    private double speed;
    private int mx,my;
    private int xmax,ymax;
    private int bgwidth,bgheight;
    private Button[] btns;
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont;
    public Menu(){
	try{
	    bg = ImageIO.read(getClass().getResourceAsStream("menubg.png"));
	    bgx = bgy = 0;
	    bgwidth = bg.getWidth();
	    bgheight = bg.getHeight();
	} catch(Exception e){
	    e.printStackTrace();
	}
	speed = 0.1;
	xmax = bgwidth-GamePanel.WIDTH;
	ymax = bgheight-GamePanel.HEIGHT;
	titleColor = new Color(0,204,204);
	titleFont = new Font("Arial",Font.PLAIN,28);
	btns = new Button[]{
	    new Button(GamePanel.WIDTH/2-50,400,100,50,"Play"),
	    new Button(GamePanel.WIDTH/2-50,460,100,50,"Exit")
	};
    }
    public void update(){
	if(mx<150) bgx += (mx-150)*speed;
	else if(mx>GamePanel.WIDTH-150) bgx += (mx-GamePanel.WIDTH+150)*speed;
	if(my<150) bgy += (my-150)*speed;
	if(my>GamePanel.HEIGHT-150) bgy += (my-GamePanel.HEIGHT+150)*speed;      
	if(bgx<0) bgx = 0;
	if(bgx>xmax) bgx = xmax;
	if(bgy<0) bgy = 0;
	if(bgy>ymax) bgy = ymax;
    }
    public void draw(Graphics2D g){
	g.drawImage(bg.getSubimage((int)bgx,(int)bgy,GamePanel.WIDTH,GamePanel.HEIGHT),0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
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
