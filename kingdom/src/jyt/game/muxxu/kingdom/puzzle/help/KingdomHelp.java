/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import jyt.game.puzzle.solving.Puzzle;

public class KingdomHelp extends JFrame
{
	private Puzzle<Element> mPuzzle;

	public KingdomHelp() throws IOException
	{
		super();
		setBounds(0, 0, 300, 330);
		mPuzzle = new PuzzleBuilder().buildPuzzle();
		add(new PuzzleCanvas(mPuzzle));
	}

	public static void main(String[] args) throws IOException
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

}
