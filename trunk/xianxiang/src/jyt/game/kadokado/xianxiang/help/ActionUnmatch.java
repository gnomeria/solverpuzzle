/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class ActionUnmatch implements IAction<Element>
{
	private Point mPoint1;
	private Point mPoint2;
	private Element mElement1;
	private Element mElement2;
	private ScoreComputer mScoreComputer;

	/**
	 * Created on 30 mars 2010 by jtoumit.<br>
	 * @param pPoint1
	 * @param pPoint2
	 * @param pElement1
	 * @param pElement2
	 * @param pScoreComputer
	 */
	public ActionUnmatch(Point pPoint1, Point pPoint2, Element pElement1, Element pElement2, ScoreComputer pScoreComputer)
	{
		super();
		mPoint1 = pPoint1;
		mPoint2 = pPoint2;
		mElement1 = pElement1;
		mElement2 = pElement2;
		mScoreComputer = pScoreComputer;
	}

	@Override
	public void doAction(Puzzle<Element> pPuzzle)
	{
		mScoreComputer.removeScore(mScoreComputer.computeScore(mElement1, mElement2));
		pPuzzle.set(mPoint1, mElement1);
		pPuzzle.set(mPoint2, mElement2);
	}

	@Override
	public IAction<Element> getReverseAction(Puzzle<Element> pPuzzle)
	{
		return new ActionMatch(mPoint1, mPoint2, mScoreComputer);
	}

}
