import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Menu{
    private Map map;
    private int mx,my;
    private Button[] btns;
    private Button back;
    private boolean inst;
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont,medFont,smallFont;
    public Menu(){
	map = new Map("menubg.png");
	titleColor = new Color(0,204,204);
	titleFont = new Font("Arial",Font.PLAIN,40);
	medFont = new Font("Arial",Font.PLAIN,25);
	smallFont = new Font("Arial",Font.PLAIN,15);
	btns = new Button[]{
	    new Button(GamePanel.WIDTH/2-80,350,160,50,"Play"),
	    new Button(GamePanel.WIDTH/2-80,410,160,50,"Instructions"),
	    new Button(GamePanel.WIDTH/2-80,470,160,50,"Exit")
	};
	back = new Button(GamePanel.WIDTH/2-50,GamePanel.HEIGHT/2+200,100,50,"Back");
	inst = false;
    }

    public void update(){
 map.setPos(mx,my);
    }

    public void draw(Graphics2D g){
	map.draw(g);
	if(inst){
	    int midwidth = GamePanel.WIDTH/2, midheight = GamePanel.HEIGHT/2;
	    g.setColor(Color.gray);
	    g.fillRoundRect(midwidth-400,midheight-300,800,600,50,50);
	    g.setColor(Color.black);
	    g.setFont(titleFont);
	    g.drawString("Instructions",midwidth-g.getFontMetrics().stringWidth("Instructions")/2,180);
	    g.setFont(medFont);
	    g.drawString("Survival Mode",midwidth-g.getFontMetrics().stringWidth("Survival Mode")/2,220);
	    g.setFont(smallFont);
	    g.drawString("        In survival mode, the objective is to live as long as possible. The player contolled organism can move through the",midwidth-380,250);
	    g.drawString("environment, eating berries and other organisms in order to replenish their life bar, which depletes due to enemy attacks",midwidth-380,280);
	    g.drawString("and slowly depletes over time due to hunger. As you consume other organisms, their DNA will be added to yours,",midwidth-380,310);
	    g.drawString("improving stats such as speed. TIP: Slow down and avoid others, as hunger scales up faster then the other stats,",midwidth-380,340);
	    g.drawString("so your organism will starve if it becomes too big. Try to use berries instead.",midwidth-380,370);
	    g.drawString("        Controls: Move the organism by shifting the mouse cursor and attack by clicking the mouse when the player's",midwidth-380,400);
	    g.drawString("image overlaps another organism's.",midwidth-380,430);
	    g.setFont(medFont);
	    g.drawString("God Mode",midwidth-g.getFontMetrics().stringWidth("God Mode")/2,460);
	    g.setFont(smallFont);
	    g.drawString("        In god mode, you are given free rein over an environment of organisms. Change the rate of which berries and",midwidth-380,490);
	    g.drawString("enemies spawn, spawn individual enemies and berries, change the landscape with rocks to block organisms, and",midwidth-380,520);
	    g.drawString("destroy everything as you please.",midwidth-380,550);
	    g.drawString("        Controls: Interact with the buttons at the top of the screen to achieve various effects. Click on the screen",midwidth-380,580);
	    g.drawString("to spawn things. Scroll by moving the mouse cursor to the edge of the screen",midwidth-380,610);
	    back.draw(g);
	}
	else{
	    g.setColor(titleColor);
	    g.setFont(titleFont);
	    g.drawString("aMEba",(GamePanel.WIDTH-g.getFontMetrics().stringWidth("aMEba"))/2,150);
	    for(int i=0;i<btns.length;i++) btns[i].draw(g);
	}
    }
    public void click(int mx,int my){
	if(inst){
	    if(back.click(mx,my)) inst = false;
	}
	else{
	    for(int i=0;i<btns.length;i++){
		if(btns[i].click(mx,my)){
		    if(i == 0) GamePanel.setLevel(1);
		    else if(i == 1) inst = true;
		    else if(i == 2) System.exit(1);
		}
	    }
	}
    }

    public void mouse(int mx,int my){
 this.mx = mx;
 this.my = my;
    }
}

