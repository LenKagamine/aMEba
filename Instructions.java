import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class Instructions extends JEditorPane implements MouseListener
{
    private static String title = "Instructions";
    private static String title1 = "\n\nSurvival Mode:";
    private static String survivalmode = "\n\nIn survival mode, the objective is to live as long as possible. The player contolled organism can move through the environment, eating berries" +
    " and other organisms in order to replenish their life bar, which depletes due to enemy attacks and slowly depletes over time due to hunger. As you consume other organisms, their DNA will" +
    " be added to yours, improving stats such as speed. " +
    " TIP: Slow down and avoid others, as hunger scales up faster then the other stats, so your organism will starve if it becomes too big. Try to use berries instead.";
    private static String controls1 = "\nControls: Move the organism by shifting the mouse cursor and attack by clicking the mouse when the player's image overlaps another organism's.";
    private static String title2 = "\n\nGod Mode";
    private static String godmode = "\n\nIn god mode, you are given free rein over an environment of organisms. Change the rate of which berries and enemies spawn, spawn individual enemies and berries," +
    " change the landscape with rocks to block organisms, and destroy everything as you please. ";
    private static String controls2 = "\nControls: Interact with the buttons at the top of the screen to achieve various effects. Click on the screen to spawn things. Scroll by moving the mouse cursor to"+
    " the edge of the screen";
    private static JButton Return = new JButton ("Return");
    JFrame frame = new JFrame ();

    public Instructions ()
    {
	Container content = frame.getContentPane ();

	StyleContext context = new StyleContext ();
	StyledDocument document = new DefaultStyledDocument (context);

	Return.setBounds (850, 30, 110, 30); //sets up return button
	Return.addMouseListener (this);

	Style style = context.getStyle (StyleContext.DEFAULT_STYLE);

	try //displays text
	{
	    StyleConstants.setFontFamily (style, "Tahoma");
	    StyleConstants.setFontSize (style, 20);
	    StyleConstants.setBold (style, true);
	    StyleConstants.setUnderline (style, false);
	    StyleConstants.setBackground (style, Color.black);
	    StyleConstants.setForeground (style, Color.white);
	    document.insertString (document.getLength (), title, style);
	}
	catch (BadLocationException e)
	{
	}

	try
	{
	    StyleConstants.setSpaceAbove (style, 3);
	    StyleConstants.setBold (style, false);
	    StyleConstants.setUnderline (style, true);
	    StyleConstants.setFontSize (style, 16);
	    document.insertString (document.getLength (), title1, style);
	    StyleConstants.setUnderline (style, false);
	    document.insertString (document.getLength (), survivalmode, style);
	    document.insertString (document.getLength (), controls1, style);
	    StyleConstants.setUnderline (style, true);
	    document.insertString (document.getLength (), title2, style);
	    StyleConstants.setUnderline (style, false);
	    document.insertString (document.getLength (), godmode, style);
	    document.insertString (document.getLength (), controls2, style);
	}
	catch (BadLocationException e)
	{
	}

	JTextPane textPane = new JTextPane (document);
	textPane.setEditable (false);
	textPane.setBackground (Color.black);

	JScrollPane scrollPane = new JScrollPane (textPane);
	content.add (scrollPane, BorderLayout.CENTER);
	//textPane.setCaretPosition (10);//boo
	textPane.add (Return);

	//frame.setLayout (null);

	//frame.pack();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.setSize (1000, 500);
	frame.setVisible (true);
	frame.setLocationRelativeTo (null);
    }


    public void mousePressed (MouseEvent e)
    {
    }


    public void mouseReleased (MouseEvent e)
    {
    }


    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mouseClicked (MouseEvent e)
    {
	if (e.getSource () == Return)
	{
	    frame.setVisible (false);
	}
    }
}

