/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl.actions;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;

public class Fill<T> implements IAction<T>
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
	public Fill(int pX, int pY, T pFill)
	{
		super();
		x = pX;
		y = pY;
		mFill = pFill;
	}

	@Override
	public void doAction(Puzzle<T> pPuzzle)
	{
		pPuzzle.set(x, y, mFill);
	}

	@Override
	public IAction<T> getReverseAction(Puzzle<T> pPuzzle)
	{
		return new Fill<T>(x, y, pPuzzle.get(x, y));
	}

}
