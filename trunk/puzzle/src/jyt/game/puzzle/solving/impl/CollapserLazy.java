/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.ICollapseListener;
import jyt.game.puzzle.solving.ICollapser;
import jyt.game.puzzle.solving.Puzzle;

public class CollapserLazy<T> implements ICollapser<T>
{
	@Override
	public void collapse(Puzzle<T> pPuzzle, ActionManager<T> pActionManager, ICollapseListener<T> pCollapseListener)
	{
		// Don't do anything, I'm lazy!
	}
}
