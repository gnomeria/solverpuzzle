/*
 * Created on 21 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ActionManager<T> implements ICollapseListener<T>
{
	private Stack<Stack<IAction<T>>> mRollback = new Stack<Stack<IAction<T>>>();
	private Puzzle<T> mPuzzle;
	private ICollapser<T> mCollapser;
	private Result<T> mResult;
	private List<IActionListener<T>> mActionListeners = new ArrayList<IActionListener<T>>();

	/**
	 * Created on 23 janv. 2010 by jtoumit.<br>
	 * @param pPuzzle
	 * @param pCollapser
	 */
	public ActionManager(Puzzle<T> pPuzzle, ICollapser<T> pCollapser)
	{
		super();
		mPuzzle = pPuzzle;
		mCollapser = pCollapser;
	}

	public void addActionListener(IActionListener<T> pActionListener)
	{
		mActionListeners.add(pActionListener);
	}

	public void removeActionListener(IActionListener<T> pActionListener)
	{
		mActionListeners.remove(pActionListener);
	}

	public Result<T> executeActionAndCollapse(IAction<T> pAction)
	{
		mRollback.push(new Stack<IAction<T>>());
		mResult = new Result<T>();
		executeAction(pAction);
		mCollapser.collapse(mPuzzle, this, this);
		return mResult;
	}

	public void executeAction(IAction<T> pAction)
	{
		for (IActionListener<T> pActionListener : mActionListeners)
			pActionListener.actionWillExecute(pAction);

		mRollback.peek().push(pAction.getReverseAction(mPuzzle));
		pAction.doAction(mPuzzle);

		for (IActionListener<T> pActionListener : mActionListeners)
			pActionListener.actionExecuted(pAction);
	}

	public void rollBack()
	{
		if (mRollback.size() == 0)
			throw new IllegalStateException("Could not rollback: nothing to rollback");
		Stack<IAction<T>> actions = mRollback.pop();
		while (!actions.isEmpty())
		{
			IAction<T> action = actions.pop();
			for (IActionListener<T> pActionListener : mActionListeners)
				pActionListener.actionWillExecute(action);
			action.doAction(mPuzzle);
			for (IActionListener<T> pActionListener : mActionListeners)
				pActionListener.actionExecuted(action);
		}
	}

	@Override
	public void collapsing(T pType, int pNb)
	{
		mResult.release(pNb, pType);
	}
}
