package imgMain.controls;

import imgMain.JxReader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import listeners.ActListener;
import listeners.Mouse;

public class JxMenuBar extends JMenuBar{

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private final JxReader parent;
	private final Color transparent = new Color(0, 0, 0, 0);
	
	private final JPanel pnlMenuJPanel = new JPanel();
	private static JLabel lblExit;
	private static JLabel lblMaximize;
	private static JLabel lblMinimize;
	private static JLabel lblIcon;
	private final JMenuBar pnlMenuItems = new JMenuBar();
	
	private JxMenu menuScreen;
	private JCheckBoxPair menuPrimaryScreen;
	private JCheckBoxPair menuSecondaryScreen;
	
	private JCheckBoxPair zoomFifty;
	private JCheckBoxPair zoomSixtySix;
	private JCheckBoxPair zoomOneHundred;
	private JCheckBoxPair zoomAuto;
	private JCheckBoxPair zoomOneHundredAndFifty;
	private JCheckBoxPair zoomTwoHundred;
	
	private JxMenu menuDisk;
	private JCheckBoxPair menuRAMMode;
	private JCheckBoxPair menuDiskMode;
	
	    
	private final JLabel label = new JLabel("");
	
	public JxMenuBar(JxReader parent){
		
		this.parent = parent;
		
		try{
			img = ImageIO.read(this.getClass().getResource("/images/border.png"));
		}catch (Exception ex){
			img = null;
		}
		
		setButtons();
		instantiateMenuItems();
		
		menuPrimaryScreen.setSelected(true);
		
		this.setForeground(Color.white);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new BorderLayout());
		this.addMouseMotionListener(new Mouse(this.parent));
		
		pnlMenuJPanel.add(lblMinimize);
		pnlMenuJPanel.add(lblMaximize);
		pnlMenuJPanel.add(lblExit);	

		pnlMenuJPanel.setBackground(transparent);
		pnlMenuItems.setBackground(transparent);
	
		pnlMenuItems.setForeground(Color.white);
		pnlMenuItems.setLayout(new FlowLayout());
		pnlMenuItems.setBorder(BorderFactory.createEmptyBorder());

		label.setForeground(Color.white);

		this.add(label, BorderLayout.CENTER);
		this.add(pnlMenuJPanel, BorderLayout.EAST);
		this.add(pnlMenuItems, BorderLayout.WEST);
		
		pnlMenuJPanel.setLocation(150,5);
		pnlMenuJPanel.validate();
		pnlMenuJPanel.updateUI();
		
	}
	
	private void setButtons(){
		try {
			Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
			Mouse mouse = new Mouse(this.parent);
			
			URL imageURL = this.getClass().getResource("/images/close_focused_prelight.png");		
			lblExit = setLabel(imageURL, handCursor, "Exit", mouse);
			
			imageURL = this.getClass().getResource("/images/maximize_focused_prelight.png");
			lblMaximize = setLabel(imageURL, handCursor, "Fullscreen", mouse);
			
			imageURL = this.getClass().getResource("/images/minimize_focused_prelight.png");
			lblMinimize = setLabel(imageURL, handCursor, "Minimize", mouse);

			imageURL = this.getClass().getResource("/images/icon.png");
			lblIcon = setLabel(imageURL, new Cursor(Cursor.DEFAULT_CURSOR), "Icon", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel setLabel(URL imageURL, Cursor handCursor, String tag, Mouse mouse){
		JLabel newButton = new JLabel(new ImageIcon(imageURL));
		newButton.setBackground(transparent);
		newButton.setForeground(transparent);
		newButton.setBorder(BorderFactory.createEmptyBorder());
		newButton.setName(tag);
		newButton.addMouseListener(new ActListener(this.parent));
		newButton.setCursor(handCursor);
		newButton.setFocusable(false);
		newButton.addMouseListener(mouse);
		return newButton;
	}
	
	private void instantiateMenuItem(ButtonGroup bg, JCheckBoxMenuItem item, JxMenu menu){
		bg.add(item);
		item.addActionListener(new ActListener(this.parent));
		menu.add(item);
	}
	
	public void setRAMSelected(boolean selected){
		
		menuRAMMode.setSelected(selected);
		menuDiskMode.setSelected(!selected);
		
	}
	
	public void setScreenPrimary(boolean selected){
		
		menuPrimaryScreen.setSelected(selected);
		menuSecondaryScreen.setSelected(!selected);
		
	}
	
	public boolean isRAMMode(){
		return menuRAMMode.isSelected();
	}

	private void instantiateMenuItems(){
		
		JxMenu menuFile = new JxMenu(this.parent, img, true);
		JxMenu menuView = new JxMenu(this.parent, img, true);
		menuScreen = new JxMenu(this.parent, img, false);
		JxMenu menuZoom = new JxMenu(this.parent, img, false);
		JxMenu menuSettings = new JxMenu(this.parent, img, true);
		menuDisk = new JxMenu(this.parent, img, false);
		JxMenu menuAbout = new JxMenu(this.parent, img, true);

		menuRAMMode = new JCheckBoxPair("RAM mode", 0, this.parent, img);
		menuDiskMode = new JCheckBoxPair("Disk mode", 0, this.parent, img);
		menuPrimaryScreen = new JCheckBoxPair("Primary", 0, this.parent, img);
		menuSecondaryScreen = new JCheckBoxPair("Secondary", 0, this.parent, img);

		JxMenuItem menuItemExit = new JxMenuItem(this.parent, img);
		JxMenuItem menuItemArchive = new JxMenuItem(this.parent, img);
		JxMenuItem menuItemFolder = new JxMenuItem(this.parent, img);
		JxMenuItem menuFullscreen = new JxMenuItem(this.parent, img);
		
		JxMenuItem menuItemAboutAuthor = new JxMenuItem(this.parent, img);
		JxMenuItem menuItemAboutProgram = new JxMenuItem(this.parent, img);
		JxMenuItem menuItemAboutHotkeys = new JxMenuItem(this.parent, img);
		
		
		if (GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length > 1){
			ButtonGroup radioScreen = new ButtonGroup();
			instantiateMenuItem(radioScreen, menuPrimaryScreen, menuScreen);
			instantiateMenuItem(radioScreen, menuSecondaryScreen, menuScreen);
			menuView.add(menuScreen);
		}
		
		pnlMenuItems.add(lblIcon);
		pnlMenuItems.add(menuFile);
		pnlMenuItems.add(menuView);
		pnlMenuItems.add(menuSettings);
		pnlMenuItems.add(menuAbout);
		
		menuFile.add(menuItemArchive);
		menuFile.add(menuItemFolder);
		menuFile.add(menuItemExit);
		menuView.add(menuFullscreen);
		menuAbout.add(menuItemAboutAuthor);
		menuAbout.add(menuItemAboutProgram);
		menuAbout.add(menuItemAboutHotkeys);
		
		menuFile.setText("File");
		menuView.setText("View");
		menuScreen.setText("Screen");
		menuSettings.setText("Settings");
		menuDisk.setText("Disk mode");
		menuAbout.setText("About");
		
		menuItemArchive.setText("Open Archive");
		menuItemFolder.setText("Open Folder");
		menuItemExit.setText("Exit");
		menuItemAboutAuthor.setText("Author");
		menuItemAboutProgram.setText("Program");
		menuItemAboutHotkeys.setText("Hotkeys");
		menuFullscreen.setText("Fullscreen");
		menuZoom.setText("Zoom");
		
		zoomFifty = new JCheckBoxPair("50%", 0.5, this.parent, img);
		zoomSixtySix = new JCheckBoxPair("66.7%", 0.67, this.parent, img);
		zoomOneHundred = new JCheckBoxPair("100%", 1, this.parent, img);
		zoomAuto = new JCheckBoxPair("Auto", -1, this.parent, img);
		zoomOneHundredAndFifty = new JCheckBoxPair("150%", 1.5, this.parent, img);
		zoomTwoHundred = new JCheckBoxPair("200%", 2, this.parent, img);
		JCheckBoxMenuItem[] checkArray = {zoomFifty, zoomSixtySix, zoomOneHundred, 
				zoomAuto, zoomOneHundredAndFifty, zoomTwoHundred};

		ButtonGroup radioZoom = new ButtonGroup();
		for (JCheckBoxMenuItem check : checkArray){
			instantiateMenuItem(radioZoom, check, menuZoom);
		}
		menuView.add(menuZoom);
		zoomAuto.setSelected(true);
		
		menuFile.setMnemonic('F');
		menuItemArchive.setMnemonic('R');
		menuItemFolder.setMnemonic('O');
		menuItemExit.setMnemonic('E');
		menuView.setMnemonic('V');
		menuFullscreen.setMnemonic('U');
		
		ButtonGroup radioDisk = new ButtonGroup();
		menuSettings.add(menuDisk);
		radioDisk.add(menuRAMMode);
		menuDisk.add(menuRAMMode);
		radioDisk.add(menuDiskMode);
		menuDisk.add(menuDiskMode);
		
	}
	
	public double getZoom(){
		double ratio = -1;
		JCheckBoxPair[] checkArray = {zoomFifty, zoomSixtySix, zoomOneHundred, 
				zoomAuto, zoomOneHundredAndFifty, zoomTwoHundred};
		for (JCheckBoxPair check : checkArray){
			if (check.isSelected()){
				ratio = check.getValue();
				break;
			}
		}
		return ratio;
	}
	
	public void setLabelText(String text){
		this.label.setText(text);
	}
	
	public static void setMaxImage(String location){
		lblMaximize.setIcon(new ImageIcon(location.getClass().getResource(location)));
	}
	
	public static void setMinImage(String location){
		lblMinimize.setIcon(new ImageIcon(location.getClass().getResource(location)));
	}
	
	public static void setCloseImage(String location){
		lblExit.setIcon(new ImageIcon(location.getClass().getResource(location)));
	}
	
	@Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
	
	
	
}
