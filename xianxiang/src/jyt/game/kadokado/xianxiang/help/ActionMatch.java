/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class ActionMatch implements IAction<Element>
{
	private Point mPoint1;
	private Point mPoint2;
	private ScoreComputer mScoreComputer;

	/**
	 * Created on 30 mars 2010 by jtoumit.<br>
	 * @param pPoint1
	 * @param pPoint2
	 * @param pScoreComputer
	 */
	public ActionMatch(Point pPoint1, Point pPoint2, ScoreComputer pScoreComputer)
	{
		super();
		mPoint1 = pPoint1;
		mPoint2 = pPoint2;
		mScoreComputer = pScoreComputer;
	}

	@Override
	public void doAction(Puzzle<Element> pPuzzle)
	{
		mScoreComputer.addScore(mScoreComputer.computeScore(pPuzzle.get(mPoint1), pPuzzle.get(mPoint2)));
		pPuzzle.set(mPoint1, null);
		pPuzzle.set(mPoint2, null);
	}

	@Override
	public IAction<Element> getReverseAction(Puzzle<Element> pPuzzle)
	{
		return new ActionUnmatch(mPoint1, mPoint2, pPuzzle.get(mPoint1), pPuzzle.get(mPoint2), mScoreComputer);
	}

	public Point getPoint1()
	{
		return mPoint1;
	}
	public Point getPoint2()
	{
		return mPoint2;
	}
}
