package GameFrame;

import java.awt.EventQueue;

//主类
public class Playgame {
	public static void main(String args[])
	{
		clock.clock.start();
		System.out.print(clock.clock.isAlive());
		EventQueue.invokeLater(()->{
			GameFrame frame=new GameFrame();
		    background_image image=new background_image(frame);
			GamePanel panel=new GamePanel(frame);
			frame.add(panel);
			frame.setVisible(true);
		});
	
	}
}
