/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

import java.io.Serializable;

public interface IActionListener<T> extends Serializable
{
	void actionWillExecute(IAction<T> pAction);
	void actionExecuted(IAction<T> pAction);
}
