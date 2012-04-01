/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jyt.game.muxxu.kingdom.puzzle.help.Element;
import jyt.game.muxxu.kingdom.puzzle.help.PuzzleBuilder;
import jyt.game.puzzle.solving.Puzzle;

public class KingdomHelp extends JFrame
{
	private Puzzle<Element> mPuzzle;
	final private PuzzleCanvas mPuzzleCanvas;

	public KingdomHelp() throws IOException
	{
		super();
		setAlwaysOnTop(true);
		setBounds(0, 0, 600, 350);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel inside = new JPanel();
		inside.setLayout(new BoxLayout(inside, BoxLayout.X_AXIS));
		mPuzzle = new PuzzleBuilder().buildPuzzle();
		inside.add(mPuzzleCanvas = new PuzzleCanvas(mPuzzle));
		inside.add(new PreferencePanel());
		HintListenerCanvas hintListenerCanvas = new HintListenerCanvas();
		inside.add(hintListenerCanvas);
		mPuzzleCanvas.addHintListener(hintListenerCanvas);
		add(inside);
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
