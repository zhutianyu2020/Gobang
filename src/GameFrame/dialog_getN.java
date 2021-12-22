package GameFrame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class dialog_getN extends JDialog{
	JTextField input=new JTextField(10);
	public dialog_getN(JFrame owner){
		super(owner);
		JPanel getN=new JPanel();
		getN.setLayout(null);
		JLabel label=new JLabel("请输入打点个数N=");
		label.setBounds(10,10,120,20);
		label.setVisible(true);
		
		input.setBounds(140, 10,100, 20);
		
		JButton confirm =new JButton("确定");
		confirm.setBounds(100, 100, 80, 30);
		confirm.addActionListener((e)->{
			try {
				GamePanel.n=Integer.parseInt(input.getText());
				this.setVisible(false);
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(owner,"数字输入格式错误，请重新输入");
			}
		});
		
		getN.add(label);
		getN.add(input);
		getN.add(confirm);
		this.add(getN);
	}

}
