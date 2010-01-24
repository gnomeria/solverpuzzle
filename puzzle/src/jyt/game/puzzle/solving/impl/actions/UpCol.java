/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl.actions;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;

/**
 * Restores collapsed elements from a column.<br>
 * Ups the column fill.length times, filling down with fill, first element up, last element down.<br>
 * Created on 24 janv. 2010.<br>
 * @author jtoumit
 */
public class UpCol<T> implements IAction<T>
{
	private int x;
	private int y;
	private T[] mFill;

	public UpCol(int pX, int pY, T[] pFill)
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
			new UpOne<T>(x, y + mFill.length - 1, mFill[i]).doAction(pPuzzle);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAction<T> getReverseAction(Puzzle<T> pPuzzle)
	{
		T[] fill = (T[])new Object[mFill.length];
		for (int i = 0; i < fill.length; i++)
		{
			fill[fill.length - i - 1] = pPuzzle.get(x, i);
		}
		return new CollapseCol<T>(x, y, fill);
	}

}
