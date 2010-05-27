/*
 * Created on 1 avr. 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingWorker;

import jyt.game.kadokado.xianxiang.help.ActionMatch;
import jyt.game.kadokado.xianxiang.help.CombinationRaterBestPotential;
import jyt.game.kadokado.xianxiang.help.Element;
import jyt.game.kadokado.xianxiang.help.ICombinationRater;
import jyt.game.kadokado.xianxiang.help.IPlayer;
import jyt.game.kadokado.xianxiang.help.PlayerWithRater;
import jyt.game.kadokado.xianxiang.help.ScoreComputer;
import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.IActionListener;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.CollapserLazy;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleCanvas extends Canvas
{
	private static final int DOTS_SIZE = 3;
	private Puzzle<Element> mPuzzle;
	private boolean mPlayed = false;
	private Point mPoint1 = null;
	private Point mPoint2 = null;
	private static final int SQUARE_SIZE = 40;
	private static final int INSIDE = 15;
	private static final int INSIDE_WIDTH = SQUARE_SIZE - INSIDE * 2;
	private static final int INSIDE_HEIGHT = SQUARE_SIZE - INSIDE * 2;

	public PuzzleCanvas(Puzzle<Element> pPuzzle)
	{
		super();
		setSize(40*5, 40*5);
		mPuzzle = pPuzzle;
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent pE)
			{
				super.mouseClicked(pE);
				new SwingWorker<Object, Object>()
				{
					
					@Override
					protected Object doInBackground() throws Exception
					{
						ScoreComputer scoreComputer = new ScoreComputer();
						ActionManager<Element> actionManager = new ActionManager<Element>(mPuzzle, new CollapserLazy<Element>());
//						ICombinationRater combinationRater = new CombinationRaterBestCurrentMove(scoreComputer);
						ICombinationRater combinationRater = new CombinationRaterBestPotential(scoreComputer);
						IPlayer player = new PlayerWithRater(combinationRater, actionManager, scoreComputer);
						actionManager.addActionListener(new IActionListener<Element>()
						{
							@Override
							public void actionWillExecute(IAction<Element> pAction)
							{
								if (pAction instanceof ActionMatch)
								{
									ActionMatch match = (ActionMatch)pAction;
									mPoint1 = match.getPoint1();
									mPoint2 = match.getPoint2();
									repaint();
									try
									{
										Thread.sleep(15000);
									}
									catch (InterruptedException e)
									{
										e.printStackTrace();
									}
								}
							}
							@Override
							public void actionExecuted(IAction<Element> pAction)
							{
								mPoint1 = null;
								mPoint2 = null;
								repaint();
							}
						});
						player.play(mPuzzle);
						return null;
					}
				}.execute();
			}
		});
	}

	public void setPuzzle(Puzzle<Element> pPuzzle)
	{
		mPuzzle = pPuzzle;
		mPlayed = false;
		mPoint1 = null;
		mPoint2 = null;
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
					Element currentElement = mPuzzle.get(x, y);
					if (currentElement != null)
					{
						pG.setColor(currentElement.getColour().getColor());
						int curx = x * SQUARE_SIZE;
						int cury = y * SQUARE_SIZE;
						switch (currentElement.getGeometry())
						{
							case RECTANGLE:
								pG.fillRect(curx, cury, SQUARE_SIZE, SQUARE_SIZE);
								break;
							case ROUND:
								pG.fillOval(curx, cury, SQUARE_SIZE, SQUARE_SIZE);
								break;
							case TRIANGLE:
								pG.fillPolygon(new int[] {curx, curx + SQUARE_SIZE, curx + SQUARE_SIZE / 2}, new int[] {cury, cury, cury + SQUARE_SIZE}, 3);
								break;
						}
						pG.setColor(Color.white);
						switch (currentElement.getInsidePattern())
						{
							case CROSS:
								pG.drawLine(curx + INSIDE, cury + INSIDE, curx + SQUARE_SIZE - INSIDE, cury + SQUARE_SIZE - INSIDE);
								pG.drawLine(curx + SQUARE_SIZE - INSIDE, cury + INSIDE, curx + INSIDE, cury + SQUARE_SIZE - INSIDE);
								break;

							case DOTS:
								pG.fillOval(curx + INSIDE, cury + INSIDE, DOTS_SIZE, DOTS_SIZE);
								pG.fillOval(curx + SQUARE_SIZE - INSIDE, cury + INSIDE, DOTS_SIZE, DOTS_SIZE);
								pG.fillOval(curx + SQUARE_SIZE - INSIDE, cury + SQUARE_SIZE - INSIDE, DOTS_SIZE, DOTS_SIZE);
								pG.fillOval(curx + INSIDE, cury + SQUARE_SIZE - INSIDE, DOTS_SIZE, DOTS_SIZE);
								break;

							case RECTANGLE:
								pG.drawRect(curx + INSIDE, cury + INSIDE, INSIDE_WIDTH, INSIDE_HEIGHT);
								break;

							case TRIANGLE:
								pG.drawPolygon(new int[] {curx + SQUARE_SIZE / 2, curx + INSIDE, curx + SQUARE_SIZE - INSIDE}, new int[] {cury + INSIDE, cury + SQUARE_SIZE - INSIDE, cury + SQUARE_SIZE - INSIDE}, 3);
								break;

							case WAVES:
								pG.drawArc(curx + INSIDE, cury + INSIDE, INSIDE_WIDTH / 2, INSIDE_HEIGHT / 2, 90, -90);
								break;

							default:
								break;
						}
					}
				}
			}
			Point p1 = mPoint1;
			if (p1 != null)
			{
				pG.setColor(Color.white);
				pG.drawRect(p1.getX() * SQUARE_SIZE, p1.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
			Point p2 = mPoint2;
			if (p2 != null)
			{
				pG.setColor(Color.white);
				pG.drawRect(p2.getX() * SQUARE_SIZE, p2.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}

}
