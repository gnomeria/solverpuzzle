/*
 * Created on 24 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jyt.game.puzzle.solving.Puzzle;

public class BinaryHelp extends JFrame
{
	private Puzzle<Element> mPuzzle;
	final private PuzzleCanvas mPuzzleCanvas;
	private final HintCanvas mHintCanvas;

	public BinaryHelp()
	{
		super();
		setBounds(0, 0, 600, 350);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel inside = new JPanel();
		inside.setLayout(new BoxLayout(inside, BoxLayout.X_AXIS));
//		mPuzzle = new PuzzleBuilderRandom().buildPuzzle();
		mPuzzle = new PuzzleBuilderFromKado().buildPuzzle();
		inside.add(mPuzzleCanvas = new PuzzleCanvas(mPuzzle));
		mHintCanvas = new HintCanvas(mPuzzleCanvas);
		inside.add(mHintCanvas);
		//mPuzzleCanvas.addHintListener(hintListenerCanvas);
		add(inside);
		JButton buttonRedraw = new JButton("Redraw");
		add(buttonRedraw);
		buttonRedraw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pE)
			{
				mPuzzleCanvas.setPuzzle(mPuzzle = new PuzzleBuilderFromKado().buildPuzzle());
				mPuzzleCanvas.repaint();
				mHintCanvas.setPuzzle(mPuzzle);
				mHintCanvas.repaint();
			}
		});
	}

	public static void main(String[] args)
	{
		BinaryHelp binaryHelp = new BinaryHelp();
		binaryHelp.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent pE)
			{
				System.exit(0);
			}
		});
		binaryHelp.setVisible(true);
	}
}
