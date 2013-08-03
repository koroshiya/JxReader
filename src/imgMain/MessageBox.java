package imgMain;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MessageBox extends JOptionPane{

	private static final long serialVersionUID = 1L;
	private JDialog frame;
	private JTextArea label;
	private JPanel panel;
	
	public MessageBox(){
		frame = new JDialog();
		frame.setSize(400, 200);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		label = new JTextArea();
		label.setEditable(false);
		label.setBackground(new Color(0, 0, 0, 0));
		label.setFocusable(false);
		
		label.setLayout(new BorderLayout());
		label.setLineWrap(true);
		label.setWrapStyleWord(true);
		
		panel.add(label, BorderLayout.CENTER);
		frame.add(panel);
		//frame.setAlwaysOnTop(true);
		
	}
	
	public void show(JPanel panel2, Object message) {
		frame.setTitle("JxReader");
		label.setText(message.toString());
		label.setLocation(frame.getWidth() / 2 - label.getWidth() / 2,
				frame.getHeight() / 2 - label.getHeight() / 2);
		frame.setLocation(panel2.getX() + panel2.getWidth() / 2,
				panel2.getY() + panel2.getHeight() / 2);
		
		frame.setVisible(true);
		frame.requestFocusInWindow();
		frame.setModal(true);
	}

}
