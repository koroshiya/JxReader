package imgMain.controls;

import imgMain.JxReader;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
//import javax.swing.JFrame;
import javax.swing.JMenuItem;

import listeners.ActListener;
import listeners.Mouse;

public class JxMenuItem extends JMenuItem{

	private static final long serialVersionUID = 1L;
	
	private final JxReader parent;
	//private final JFrame jFrame;
	//private final Image icon;
	
	public JxMenuItem(JxReader parent, Image ico){
		this.parent = parent;
		//this.icon = ico;
		//this.jFrame = this.parent.getFrame();
		//setOpaque(true);
		//setForeground(Color.white);
		setBorder(BorderFactory.createEmptyBorder());
		addMouseListener(new Mouse(this.parent));
	}
	
	@Override
	public void setText(String text){
		super.setText(text);

		setActionCommand(text);
		addActionListener(new ActListener(this.parent));
	}
	
	@Override
	public void paintComponent(Graphics g){
		//if (icon != null && jFrame != null){
		//	g.drawImage(icon, 0, 0, this.getWidth(), this.getHeight(), this);
		//}
        super.paintComponent(g);
        parent.repaint();
        
    }
	
	
	
}
