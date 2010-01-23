/*
 * Created on 21 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help.actions;

import jyt.game.muxxu.kingdom.puzzle.help.Action;
import jyt.game.muxxu.kingdom.puzzle.help.IAction;
import jyt.game.muxxu.kingdom.puzzle.help.Puzzle;

public class Invert<T> implements IAction<T>
{
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	/**
	 * Created on 21 janv. 2010 by jtoumit.<br>
	 * @param pX1
	 * @param pY1
	 * @param pX2
	 * @param pY2
	 */
	public Invert(int pX1, int pY1, int pX2, int pY2)
	{
		super();
		x1 = pX1;
		y1 = pY1;
		x2 = pX2;
		y2 = pY2;
	}
	public int getX1()
	{
		return x1;
	}
	public int getY1()
	{
		return y1;
	}
	public int getX2()
	{
		return x2;
	}
	public int getY2()
	{
		return y2;
	}
	@Override
	public void doAction(Puzzle<T> pPuzzle)
	{
		T save = pPuzzle.get(x1, y1);
		pPuzzle.set(x1, y1, pPuzzle.get(x2, y2));
		pPuzzle.set(x2, y2, save);
	}
}
