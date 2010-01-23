/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl.actions;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;

public class UpOne<T> implements IAction<T>
{
	private int x;
	private int y;
	private T mFill;

	/**
	 * Created on 23 janv. 2010 by jtoumit.<br>
	 * @param pX
	 * @param pY
	 * @param pFill
	 */
	public UpOne(int pX, int pY, T pFill)
	{
		super();
		x = pX;
		y = pY;
		mFill = pFill;
	}

	@Override
	public void doAction(Puzzle<T> pPuzzle)
	{
		for (int i = 0; i < y; i++)
			pPuzzle.set(x, i, pPuzzle.get(x, i + 1));
		pPuzzle.set(x, y, mFill);
	}

	@Override
	public IAction<T> getReverseAction(Puzzle<T> pPuzzle)
	{
		return new CollapseOne<T>(x, y, pPuzzle.get(x, 0));
	}
}
