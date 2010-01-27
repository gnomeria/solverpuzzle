/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jyt.game.puzzle.solving.Puzzle;

public class PuzzleCanvas extends Canvas
{
	private Puzzle<Element> mPuzzle;
	private List<Hint> mHints = new ArrayList<Hint>();
	private List<IHintListener> mHintListeners = new ArrayList<IHintListener>();

	/**
	 * Created on 24 janv. 2010 by jtoumit.<br>
	 * @param pPuzzle
	 * @throws IOException 
	 */
	public PuzzleCanvas(Puzzle<Element> pPuzzle) throws IOException
	{
		super();
		setSize(PuzzleBuilder.RECTANGLE_SIZE, PuzzleBuilder.RECTANGLE_SIZE);
		setPuzzle(pPuzzle);
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent pEvent)
			{
				int x = pEvent.getX() / PuzzleBuilder.SQUARE_SIZE;
				int y = pEvent.getY() / PuzzleBuilder.SQUARE_SIZE;
				boolean found = false;
				for (Hint hint : mHints)
				{
					if ((hint.getX1() == x) && (hint.getY1() == y))
					// First hint square
					{
						int xInSquare = pEvent.getX() - x * PuzzleBuilder.SQUARE_SIZE;
						int yInSquare = pEvent.getY() - y * PuzzleBuilder.SQUARE_SIZE;
						if (hint.getX1() == hint.getX2())
						// Down
						{
							if ((yInSquare > PuzzleBuilder.SQUARE_SIZE * 3 / 4) && (xInSquare > PuzzleBuilder.SQUARE_SIZE / 4) && (xInSquare < PuzzleBuilder.SQUARE_SIZE * 3 / 4))
							{
								notifyHintListeners(hint);
								found = true;
								break;
							}
						}
						else
						// Right
						{
							if ((xInSquare > PuzzleBuilder.SQUARE_SIZE * 3 / 4) && (yInSquare > PuzzleBuilder.SQUARE_SIZE / 4) && (yInSquare < PuzzleBuilder.SQUARE_SIZE * 3 / 4))
							{
								notifyHintListeners(hint);
								found = true;
								break;
							}
						}
					}
				}
				if (!found)
					notifyHintListeners(null);
				super.mouseMoved(pEvent);
			}
		});
	}

	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
		if (mPuzzle != null)
		{
			for (int x = 0; x < mPuzzle.getWidth(); x++)
			{
				for (int y = 0; y < mPuzzle.getHeight(); y++)
				{
					pG.drawImage(ImageHelper.getImage(mPuzzle.get(x, y)), x * PuzzleBuilder.SQUARE_SIZE, y * PuzzleBuilder.SQUARE_SIZE, this);
				}
			}
			for (Hint hint : mHints)
			{
				pG.setColor(new Color(Math.min(255, hint.getNbReleased() * 50), 0, 0));
				pG.drawLine(hint.getX1() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2, hint.getY1() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2, hint.getX2() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2, hint.getY2() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2);
			}
		}
	}

	public void setPuzzle(Puzzle<Element> pPuzzle)
	{
		mPuzzle = pPuzzle;
		mHints.clear();
		notifyHintListeners(null);
		HintDiscoverer discoverer = new HintDiscoverer(mPuzzle);
		mHints.addAll(discoverer.getHints());
		repaint();
	}

	private void notifyHintListeners(Hint pHint)
	{
		for (IHintListener hintListener : mHintListeners)
		{
			hintListener.setHint(pHint);
		}
	}

	public void addHintListener(IHintListener pHintListener)
	{
		mHintListeners.add(pHintListener);
	}
	public void removeHintListener(IHintListener pHintListener)
	{
		mHintListeners.remove(pHintListener);
	}
}
