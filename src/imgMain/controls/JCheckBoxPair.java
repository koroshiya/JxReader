package imgMain.controls;

import imgMain.JxReader;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
//import javax.swing.JFrame;

public class JCheckBoxPair extends JCheckBoxMenuItem {

	private static final long serialVersionUID = 1L;
	
	//private final JxReader parent;
	//private final JFrame jFrame;
	//private final Image icon;
	private double value;
	
	public JCheckBoxPair(String text, double newValue, JxReader parent, Image ico){
		super(text);
		//this.parent = parent;
		//this.icon = ico;
		//this.jFrame = this.parent.getFrame();
		//setOpaque(false);
		//setForeground(Color.white);
		setBorder(BorderFactory.createEmptyBorder());
		setValue(newValue);
	}
	
	public void setValue(double newValue){
		this.value = newValue;
	}
	
	public double getValue(){
		return this.value;
	}
	
	@Override
	public void paintComponent(Graphics g){
		//if (icon != null && jFrame != null){
			//g.drawImage(icon, 0, 0, this.getWidth(), this.getHeight(), this);
		//}
        super.paintComponent(g);
    }

}
