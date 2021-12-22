package GameFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gamebutton {
	GamePanel panel;
	JFrame frame;
	public Gamebutton(GamePanel panel,JFrame frame) {
		this.panel=panel;
		this.frame=frame;
		JButton start=new JButton("开始游戏");
		 // JLabel statt1=new JLabel("开始游戏"); //也可以用文本框，设置好了会好看点，JButton太丑了
		// start.setBackground(Color.lightGray);    //改变按钮颜色，都太丑了，改了更难看
		start.setBounds(670, 10, 90, 35);		  //设置按钮位置
		
		start.addActionListener((e)->{
			if(GamePanel.mytype!=0)
				panel.setGameFlag(true);
		}); //lambda 表达式 start监听器
		
		
		JButton setting=new JButton("游戏设置");
		setting.setBounds(670, 60, 90, 35);
		setting.addActionListener((e)->{   
			GameDialog dialog=new GameDialog(frame);
			dialog.setTitle("游戏设置");
			dialog.setModal(true);                       // 对话框设置为模态的（阻塞模式）
			dialog.setSize(400,400);
			dialog.setVisible(true);
		}); //lambda 表达式 setting监听器
		
		
		
		
		JButton instructions=new JButton("游戏说明");
		instructions.setBounds(670, 110, 90, 35);
		
		
		JButton retract=new JButton("悔棋");
		retract.setBounds(670, 160, 90, 35);
		retract.addActionListener((e)->{
			GamePanel.IsRestract=true;
		});
		
		
		
		JButton restart=new JButton("重新开始");
		
		panel.add(start);
		panel.add(setting);
		panel.add(instructions);
		panel.add(retract);
	}
	

}
