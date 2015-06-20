package game.Main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Game{
	
	public static final String TITLE = "Snake";
	public static int WIDTH;
	public static int HEIGHT;
	public static int SIDEBAR_WIDTH;
	
	public static void main(String[] args) {
		JFrame  f = new JFrame(TITLE);
		try{
		resolution();
		}catch(Exception e){ 
			e.printStackTrace(); 
			throw new IllegalArgumentException("We couldn't figure your Screen Size. Try again or contact!"); 
		}
		f.setContentPane(new GamePanel() );
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	private static void resolution() throws Exception{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		SIDEBAR_WIDTH = d.height / 6;
		WIDTH =  (d.height /3) * 2 + SIDEBAR_WIDTH;
		HEIGHT = (d.height /3) *2;
	}

}
