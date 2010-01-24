/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl.actions;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;

/**
 * Collapses <code>fill.length</code> elements from [x, y] to [x, y+fill.length].<br>
 * Fills the column with fill[], first element will be down, last element will be up.<br>
 * Created on 24 janv. 2010.<br>
 * @author jtoumit
 */
public class CollapseCol<T> implements IAction<T>
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
	public CollapseCol(int pX, int pY, T[] pFill)
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
			new CollapseOne<T>(x, y + mFill.length - 1, mFill[i]).doAction(pPuzzle);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAction<T> getReverseAction(Puzzle<T> pPuzzle)
	{
		T[] fill = (T[])new Object[mFill.length];
		for (int i = 0; i < fill.length; i++)
		{
			fill[fill.length - i - 1] = pPuzzle.get(x, y + i);
		}
		return new UpCol<T>(x, y, fill);
	}

}
