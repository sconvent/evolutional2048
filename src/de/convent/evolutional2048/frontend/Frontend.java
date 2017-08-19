package de.convent.evolutional2048.frontend;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Frontend extends JPanel
{
	private FrontendPanel panel;

	public Frontend()
	{
		JFrame frontendFrame = new JFrame();
		frontendFrame.setTitle("evolutional2048");
		frontendFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frontendFrame.setSize(340, 400);
		frontendFrame.setResizable(false);
		frontendFrame.add(new FrontendPanel());
		frontendFrame.setLocationRelativeTo(null);
		frontendFrame.setVisible(true);
	}
}
