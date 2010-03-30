/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl.actions;

import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.Puzzle;

public class RotationClockWise<T> implements IAction<T>
{
	private int x;
	private int y;

	/**
	 * Created on 27 mars 2010 by jtoumit.<br>
	 * @param pX
	 * @param pY
	 */
	public RotationClockWise(int pX, int pY)
	{
		super();
		x = pX;
		y = pY;
	}

	public void doAction(Puzzle<T> pPuzzle)
	{
		T save = pPuzzle.get(x, y);
		pPuzzle.set(x, y, pPuzzle.get(x, y + 1));
		pPuzzle.set(x, y + 1, pPuzzle.get(x + 1, y + 1));
		pPuzzle.set(x + 1, y + 1, pPuzzle.get(x + 1, y));
		pPuzzle.set(x + 1, y, save);
	}

	@Override
	public IAction<T> getReverseAction(Puzzle<T> pPuzzle)
	{
		return new RotationCounterClockWise<T>(x, y);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
