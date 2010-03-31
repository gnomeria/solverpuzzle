/*
 * Created on 24 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;
import jyt.game.puzzle.solving.impl.actions.RotationClockWise;

public class PuzzleCanvas extends Canvas
{
	private Puzzle<Element> mPuzzle;
	private Combination mCurrentCombination = null;
	private static final int SQUARE_SIZE = 40;

	public PuzzleCanvas(Puzzle<Element> pPuzzle)
	{
		super();
		setSize(40*5, 40*5);
		mPuzzle = pPuzzle;
	}

	public void setPuzzle(Puzzle<Element> pPuzzle)
	{
		mPuzzle = pPuzzle;
		mCurrentCombination = null;
	}

	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
		pG.setColor(Color.black);
		pG.fillRect(0, 0, getWidth(), getHeight());
		if (mPuzzle != null)
		{
			for (int x = 0; x < mPuzzle.getWidth(); x++)
			{
				for (int y = 0; y < mPuzzle.getHeight(); y++)
				{
					if (mPuzzle.get(x, y) != null)
					{
						pG.setColor(mPuzzle.get(x, y).getColor());
						pG.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
					}
				}
			}
			Zone[] zones = PuzzleAnalyzerHelper.getZones(mPuzzle);
			pG.setColor(Color.black);
			for (Zone zone : zones)
			{
				for (Point point : zone.getPoints())
				{
					pG.drawLine(point.getX() * SQUARE_SIZE, point.getY() * SQUARE_SIZE, (point.getX() + 1) * SQUARE_SIZE, (point.getY() + 1) * SQUARE_SIZE);
					pG.drawLine((point.getX() + 1) * SQUARE_SIZE, point.getY() * SQUARE_SIZE, point.getX() * SQUARE_SIZE, (point.getY() + 1) * SQUARE_SIZE);
				}
			}
			if (mCurrentCombination != null)
			{
				pG.setColor(Color.black);
				int current = 1;
				for (RotationClockWise<Element> rotation : mCurrentCombination.getRotations())
				{
					pG.drawString(String.valueOf(current), rotation.getX() * SQUARE_SIZE + SQUARE_SIZE - 5, rotation.getY() * SQUARE_SIZE + SQUARE_SIZE - 5);
					current++;
				}
			}
		}
	}

	public void setCurrentCombination(Combination pCurrentCombination)
	{
		if (mCurrentCombination != pCurrentCombination)
		{
			mCurrentCombination = pCurrentCombination;
			repaint();
		}
	}
}
