/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

public interface IActionListener<T>
{
	void actionWillExecute(IAction<T> pAction);
	void actionExecuted(IAction<T> pAction);
}
