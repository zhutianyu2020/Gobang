package GameFrame;

import javax.swing.JOptionPane;

class WhoWin //谁赢了  如果白棋胜利的话返回字符串:"白棋(2)胜利"，如果黑棋胜利的话返回字符串:"黑棋(1)胜利",如果没有人胜利的话返回字符串:"没有人胜利"
{
	public WhoWin(GameFrame frame) {
		victory(AI.Map,frame);
	}
	//  返回string   胜利     地图     
	public void victory(int map[][],GameFrame frame)
	{
		//按 —— 检测是否存在胜利(五子连珠)(五元组式搜索)
		for(int h=0;h<15;h++)//行
		{
			for(int l=0;l<=10;l++)//列
			{
				int key=map[h][l];//保存五元组的第一个点棋的颜色
				if(key==0)continue;//没有棋子
				if(map[h][l+1]==key && map[h][l+2]==key && map[h][l+3]==key && map[h][l+4]==key)//存在五子连珠
				{
					String rtu=key==1?"黑棋胜利":"白棋胜利";
					JOptionPane.showMessageDialog(frame,rtu);
					GamePanel.winflag=key==1?1:2;
				}
			}
		}
		//按 | 检测是否存在胜利(五子连珠)
		for(int h=0;h<=10;h++)//行
		{
			for(int l=0;l<15;l++)//列
			{
				int key=map[h][l];//保存五元组的第一个点棋的颜色
				if(key==0)continue;
				if(map[h+1][l]==key && map[h+2][l]==key && map[h+3][l]==key && map[h+4][l]==key)//存在五子连珠
				{
					String rtu= key==1?"黑棋胜利":"白棋胜利";
					JOptionPane.showMessageDialog(frame,rtu);
					GamePanel.winflag=key==1?1:2;
					GamePanel.gameFlag=false;
				}
			}
		}
		//按 \ 检测是否存在胜利(五子连珠)
		for(int h=0;h<=10;h++)//行
		{
			for(int l=0;l<=10;l++)//列
			{
				int key=map[h][l];//保存五元组的第一个点棋的颜色
				if(key==0)continue;
				if(map[h+1][l+1]==key && map[h+2][l+2]==key && map[h+3][l+3]==key && map[h+4][l+4]==key)//存在五子连珠
				{
					String rtu=key==1?"黑棋胜利":"白棋胜利";
					
					JOptionPane.showMessageDialog(frame,rtu);
					GamePanel.winflag=key==1?1:2;
					GamePanel.gameFlag=false;
				}
			}
		}
		//按 / 检测是否存在胜利(五子连珠)
		for(int h=0;h<=10;h++)//行
		{
			for(int l=4;l<15;l++)//列
			{
				int key=map[h][l];//保存五元组的第一个点棋的颜色
				if(key==0)continue;
				if(map[h+1][l-1]==key && map[h+2][l-2]==key && map[h+3][l-3]==key && map[h+4][l-4]==key)//存在五子连珠
				{
					String rtu= key==1?"黑棋胜利":"白棋胜利";
					JOptionPane.showMessageDialog(frame,rtu);
					GamePanel.winflag=key==1?1:2;
					GamePanel.gameFlag=false;
				}
			}
		}
		//JOptionPane.showMessageDialog(frame,"没有人胜利");
	}
}
