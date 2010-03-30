/*
 * Created on 24 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MatchBuilder extends JFrame
{
	private static Color[][] FOUND;

	public static void main(String[] args) throws AWTException
	{
		Robot robot = new Robot();
		//anchor: 64, 389, 77, 404
		//441,358, 472,379
		// new anchor = 599, 516, 613, 533
		System.out.println("	private static final Color[][] MATCH = new Color[][]");
		System.out.println("	{");
		int startY = 358;
		int endY = 379;
		int startX = 441;
		int endX = 472;
		FOUND = new Color[endY - startY][];
		
		for (int y = startY; y < endY; y++)
		{
			FOUND[y - startY] = new Color[endX - startX];
			System.out.print("		new Color[] {");
			for (int x = startX; x < endX; x++)
			{
				Color color = robot.getPixelColor(x, y);
				FOUND[y - startY][x - startX] = color;
				System.out.print("new Color(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "),");
			}
			System.out.println("},");
		}
		System.out.println("	};");
		MatchBuilder matchBuilder = new MatchBuilder();
		matchBuilder.setVisible(true);
		matchBuilder.setSize(300, 300);
		matchBuilder.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent pE)
			{
				System.exit(0);
			}
		});
	}
	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
		for (int y = 0; y < FOUND.length; y++)
		{
			for (int x = 0; x < FOUND[y].length; x++)
			{
				pG.setColor(FOUND[y][x]);
				pG.fillRect(x + 10, y + 100, 1, 1);
			}
		}
	}
}
