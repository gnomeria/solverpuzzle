/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

public interface ICollapser<T>
{
	void collapse(Puzzle<T> pPuzzle, ActionManager<T> pActionManager, ICollapseListener<T> pCollapseListener);
}
