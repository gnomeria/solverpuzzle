/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import jyt.game.kadokado.binary.help.Collapser;
import jyt.game.kadokado.binary.help.Combination;
import jyt.game.kadokado.binary.help.CombinationSearcher;
import jyt.game.kadokado.binary.help.Element;
import jyt.game.kadokado.binary.help.IPuzzleAnalyzer;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzer4Blocks;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerCombiner;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerDistances;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerExp;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerOrphans;
import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.actions.RotationClockWise;

public class HintCanvas extends Canvas
{
	private static final int ZOOM_FACTOR = 3;
	private Puzzle<Element> mPuzzle = null;
	private Combination[] mCombinations = null;
	private PuzzleCanvas mPuzzleCanvas;
	private int mCurrentCombination = -1;

	public HintCanvas(PuzzleCanvas pPuzzleCanvas)
	{
		super();
		mPuzzleCanvas = pPuzzleCanvas;
		addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseMoved(MouseEvent pE)
			{
				if ((pE.getModifiers() & MouseEvent.MOUSE_EXITED) != 0)
					setCurrentCombination(-1);
				else
				{
					if (mPuzzle != null)
					{
						int interval = ZOOM_FACTOR * mPuzzle.getHeight() + 10;
						int combinationNumber = pE.getY() / interval;

						if (mCombinations.length > combinationNumber)
							setCurrentCombination(combinationNumber);
						else
							setCurrentCombination(-1);
					}
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent pE)
			{
			}
		});
	}
	public void setPuzzle(Puzzle<Element> pPuzzle)
	{
		mPuzzle = new Puzzle<Element>(pPuzzle);
//		mCombinations = new CombinationSearcher(new PuzzleAnalyzerDistances()).search(mPuzzle);
		IPuzzleAnalyzer analyzer = new PuzzleAnalyzerCombiner(new IPuzzleAnalyzer[]
				{
					new PuzzleAnalyzerDistances(),
					new PuzzleAnalyzerExp(new PuzzleAnalyzer4Blocks(50), 3.28),
					new PuzzleAnalyzerExp(new PuzzleAnalyzerOrphans(50), 1.44)
				},
				new double[] {2.6, 77, 95});
		mCombinations = new CombinationSearcher(analyzer).search(mPuzzle);
	}

	public void setCurrentCombination(int pCurrentCombination)
	{
		if (mCurrentCombination != pCurrentCombination)
		{
			mCurrentCombination = pCurrentCombination;
			if (mCurrentCombination == -1)
				mPuzzleCanvas.setCurrentCombination(null);
			else
				mPuzzleCanvas.setCurrentCombination(mCombinations[mCurrentCombination]);
			repaint();
		}
	}

	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
		if (mCombinations != null)
		{
			int current = 0;
			int space = ZOOM_FACTOR * mPuzzle.getHeight() + 10;
			for (Combination combination : mCombinations)
			{
				Puzzle<Element> puzzle = new Puzzle<Element>(mPuzzle);
				ActionManager<Element> am = new ActionManager<Element>(puzzle, new Collapser());
				for (RotationClockWise<Element> rotation : combination.getRotations())
					am.executeActionAndCollapse(rotation);

				for (int x = 0; x < puzzle.getWidth(); x++)
				{
					for (int y = 0; y < puzzle.getHeight(); y++)
					{
						if (puzzle.get(x, y) != null)
							pG.setColor(puzzle.get(x, y).getColor());
						else
							pG.setColor(Color.black);
						pG.fillRect(x * ZOOM_FACTOR, y * ZOOM_FACTOR + current * space, ZOOM_FACTOR, ZOOM_FACTOR);
					}
				}
				pG.setColor(Color.black);
				pG.drawString(String.valueOf(combination.getFitness()), ZOOM_FACTOR * puzzle.getWidth() + 10, current * space + 15);
				if (mCurrentCombination == current)
					pG.drawRect(0, current * space, ZOOM_FACTOR * puzzle.getWidth() + 50, space);
				current++;
				if (current >= 10)
					break;
			}
		}
	}
}
