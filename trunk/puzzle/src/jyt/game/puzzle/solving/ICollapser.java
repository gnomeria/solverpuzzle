/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

import java.io.Serializable;

public interface ICollapser<T> extends Serializable
{
	void collapse(Puzzle<T> pPuzzle, ActionManager<T> pActionManager, ICollapseListener<T> pCollapseListener);
}
