/*
 * Created on 21 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.util.Stack;

public class ActionManager<T>
{
	private Stack<T> mRollback = new Stack<T>();
	private Puzzle<T> mPuzzle;

	/**
	 * Created on 21 janv. 2010 by jtoumit.<br>
	 * @param pPuzzle
	 */
	public ActionManager(Puzzle<T> pPuzzle)
	{
		super();
		mPuzzle = pPuzzle;
	}

	public void executeAction(IAction<T> pAction)
	{
		// TODO Auto-generated method stub

	}
}
