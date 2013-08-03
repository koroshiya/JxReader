package imgMain.controls;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.sf.javavp8decoder.imageio.WebPImageReaderSpi;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

public class JImage {

	private int height;
	private int width;
	private Dimension size;
	private String name;
	private BufferedImage i;
	private JLabel label;
    
    private void setImage(BufferedImage i, String name) {
    	setImage(i, i.getWidth(), i.getHeight(), name);
    }
    
    public JImage(InputStream in) {
        this(in, "Default");
    }
    
    public JImage(InputStream in, String name) {
		try {
			i = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
        this.width = i.getWidth();
        this.height = i.getHeight();
        this.name = name;
        this.size = new Dimension(width, height);
    }
    
    private void setImage(BufferedImage i, int width, int height, String name) {
        this.i = i;
        this.width = width;
        this.height = height;
        this.name = name;
        this.size = new Dimension(width, height);
    }
    
    public JImage(String line){
    	
    	try{
			if (line.endsWith(".gif")){
				addGIF(line);
			}else if (line.endsWith(".psd")){
				addPSD(line);
			}else if (line.endsWith(".webp")){
				addWebP(line);
			}else{
				addGenericImage(line);
			}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}

    }
    
    private void addGenericImage(String line) throws IOException{
    	File f = new File(line);
    	BufferedImage image = ImageIO.read(f);
    	setImage(image, f.getName());
    	//setImage(Sanselan.getBufferedImage(f, null), f.getName());
    }
    
    private void addPSD(String line) throws IOException, ImageReadException{
    	File f = new File(line);
    	setImage(Sanselan.getBufferedImage(f, null), f.getName());
    }
    
	private void addGIF(String line){
	  Icon icon = new ImageIcon(line);
      label = new JLabel(icon);
      this.i = null;
      this.width = icon.getIconWidth();
      this.height = icon.getIconHeight();
      this.name = line.substring(line.lastIndexOf('/') + 1);
      this.size = new Dimension(width, height);
	}
    
	private void addWebP(String line) throws IOException{
		IIORegistry r = IIORegistry.getDefaultInstance();
		WebPImageReaderSpi s = new WebPImageReaderSpi();
		r.registerServiceProvider(s);
	  Icon icon = new ImageIcon(decodeWebP(new File(line)));
      label = new JLabel(icon);
      this.i = null;
      this.width = icon.getIconWidth();
      this.height = icon.getIconHeight();
      this.name = line.substring(line.lastIndexOf('/') + 1);
      this.size = new Dimension(width, height);
	}
	
	private BufferedImage decodeWebP(File f) throws IOException{
		
		Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix("WEBP");
		ImageReader imageReader = (ImageReader) readers.next();
		ImageInputStream iis = ImageIO.createImageInputStream(f);
		imageReader.setInput(iis, false);
		BufferedImage bi = imageReader.read(0);
		return bi;
		
	}

    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public JImage getJImage(){return this;}
    
    public BufferedImage getImage(){
    	if (i != null){
    		return i;
    	}else{
    		return (BufferedImage) ((ImageIcon) label.getIcon()).getImage();
    	}
    }
    
    public JLabel getLabel(){
    	if (i != null){
    		return new JLabel(new ImageIcon(i));
    	}else{
    		return label;
    	}
    }
    
    public String getName(){return this.name;}
    public Dimension getSize(){return this.size;}
    
	public JLabel getResizedImage(double zoom, int intX, int sideInsets){
		
		double width = zoom != 1 ? (double)this.width * zoom : intX - sideInsets - 2;
		double height = ((width / (double)this.width) * (double)this.height);
		JLabel newLabel = null;

		if (i != null){
			newLabel = new JLabel(new ImageIcon(i.getScaledInstance((int)width, (int)height, 0)));
		}else{
			newLabel = new JLabel(new ImageIcon(((ImageIcon) label.getIcon()).getImage().getScaledInstance((int)width, (int)height, 0))); //may need fixing later
		}
		
		newLabel.setPreferredSize(new Dimension((int)width, (int)height));
		newLabel.setSize((int)width, (int)height);
		newLabel.validate();
		newLabel.updateUI();
		return newLabel;
		
	}
    
    public void setWidth(int width){this.width = width;}
    public void setHeight(int height){this.height = height;}
    public void setSize(int width, int height){
    	this.width = width;
    	this.height = height;
    	this.size = new Dimension(width, height);
    }
    public void dispose(){
    	i.flush();
    }

}