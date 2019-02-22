package frameUtil;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameTools {
	public static void initFrame(JFrame frame,int width,int height){
		Toolkit tools=Toolkit.getDefaultToolkit();
		Dimension dimension=tools.getScreenSize();
		int x=(int) dimension.getWidth();
		int y=(int) dimension.getHeight();
		frame.setBounds((x-width)/2,(y-height)/2, width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
}
