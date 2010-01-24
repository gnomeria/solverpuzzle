/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl.actions;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;

public class UpRow<T> implements IAction<T>
{
	private int x;
	private int y;
	private T[] mFill;

	/**
	 * Created on 24 janv. 2010 by jtoumit.<br>
	 * @param pX
	 * @param pY
	 * @param pFill
	 */
	public UpRow(int pX, int pY, T[] pFill)
	{
		super();
		x = pX;
		y = pY;
		mFill = pFill;
	}

	@Override
	public void doAction(Puzzle<T> pPuzzle)
	{
		for (int i = 0; i < mFill.length; i++)
		{
			new UpOne<T>(x + i, y, mFill[i]).doAction(pPuzzle);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAction<T> getReverseAction(Puzzle<T> pPuzzle)
	{
		T[] fill = (T[])new Object[mFill.length];
		for (int i = 0; i < fill.length; i++)
		{
			fill[i] = pPuzzle.get(x + i, 0);
		}
		return new CollapseRow<T>(x, y, fill);
	}

}
