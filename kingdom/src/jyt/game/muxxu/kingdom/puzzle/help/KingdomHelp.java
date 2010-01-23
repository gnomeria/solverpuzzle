/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class KingdomHelp extends JFrame
{
	public KingdomHelp()
	{
		super();
		setBounds(0, 0, 300, 330);
		new PuzzleBuilder().buildPuzzle();
	}

	public static void main(String[] args)
	{
		KingdomHelp kingdomHelp = new KingdomHelp();
		kingdomHelp.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent pE)
			{
				System.exit(0);
			}
		});
		kingdomHelp.setVisible(true);
	}

	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
		pG.setColor(Color.BLACK);
		for (int x = 0; x < PuzzleBuilder.RECTANGLE_SIZE; x++)
		{
			for (int y = 0 ; y < PuzzleBuilder.RECTANGLE_SIZE; y++)
			{
				if (PuzzleBuilder.zob[x + y * PuzzleBuilder.RECTANGLE_SIZE] == 1)
					pG.drawRect(x+5, y+30, 1, 1);
			}
		}
	}
}
