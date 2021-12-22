package GameFrame;

class thread extends Thread{

	public void run() {
	
		while(true) {
			
			if(clock.isBlack&&GamePanel.gameFlag) {
				clock.BlackTime++;
			}
			else if(GamePanel.gameFlag) {
				clock.WhiteTime++;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}

public class clock {
	public static int BlackTime=0;
	public static int WhiteTime=0;
	public static boolean isBlack=false;
	public static boolean isWhite=false;
	public static thread clock=new thread();
}
