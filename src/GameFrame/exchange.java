package GameFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class exchange {
	static int option=2;
	public  static void exchange(GameFrame myframe) {
		if(GamePanel.mytype==2) {
			AI.Map[7][7]=1;
			AI.Map[6][7]=2;
			AI.Map[6][8]=1;
			Pointer pointer1=GamePanel.pointers[7][7];
			Pointer pointer2=GamePanel.pointers[6][7];
			Pointer pointer3=GamePanel.pointers[6][8];
			
			Chess chess1=new Chess(pointer1.getX(),pointer1.getY(),1);
			GamePanel.num++;
			chess1.num=GamePanel.num;
			GamePanel.chesses.add(chess1);
			pointer1.setType(1);
			
			GamePanel.record.add(String.valueOf('B')+String.valueOf('(')+String.valueOf((char)(7+1+64))+","+(15-7)+
			               String.valueOf(')'));
			GamePanel.record.add(String.valueOf(';'));
					
			Chess chess2=new Chess(pointer2.getX(),pointer2.getY(),2);
			GamePanel.num++;
			chess2.num=GamePanel.num;
			GamePanel.chesses.add(chess2);
			pointer2.setType(2);
			
			GamePanel.record.add(String.valueOf('W')+String.valueOf('(')+String.valueOf((char)(7+1+64))+","+(15-6)+
			               String.valueOf(')'));
	         GamePanel.record.add(String.valueOf(';'));
			
			Chess chess3=new Chess(pointer3.getX(),pointer3.getY(),1);
			GamePanel.num++;
			chess3.num=GamePanel.num;
			GamePanel.chesses.add(chess3);
			pointer2.setType(1);
			
			GamePanel.record.add(String.valueOf('B')+String.valueOf('(')+String.valueOf((char)(8+1+64))+","+(15-6)+
		               String.valueOf(')'));
			GamePanel.record.add(String.valueOf(';'));
			
			clock.isBlack=false;   
			 option=JOptionPane.showConfirmDialog(myframe,"是否选择三手交换?");
			
			if(option==JOptionPane.YES_OPTION) {
				System.out.println("对方选择三手交换");
				 GamePanel.mytype=1;
				 GamePanel.Aitype=2;
				 
				 JOptionPane.showMessageDialog(myframe,"请您打"+GamePanel.n+"个点");
				 GamePanel.isFiveDA=3;
			}
		}
	}
}











