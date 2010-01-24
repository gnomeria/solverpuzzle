/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import jyt.game.puzzle.solving.Puzzle;

public class KingdomHelp extends JFrame
{
	private Puzzle<Element> mPuzzle;
	final private PuzzleCanvas mPuzzleCanvas;

	public KingdomHelp() throws IOException
	{
		super();
		setBounds(0, 0, 300, 350);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		mPuzzle = new PuzzleBuilder().buildPuzzle();
		add(mPuzzleCanvas = new PuzzleCanvas(mPuzzle));
		JButton buttonRedraw = new JButton("Redraw");
		add(buttonRedraw);
		buttonRedraw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pE)
			{
				mPuzzleCanvas.setPuzzle(new PuzzleBuilder().buildPuzzle());
			}
		});
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
