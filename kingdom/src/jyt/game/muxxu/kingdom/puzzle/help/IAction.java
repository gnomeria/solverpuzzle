/*
 * Created on 21 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

public interface IAction<T>
{
	void doAction(Puzzle<T> pPuzzle);
	/**
	 * This method returns the action needed to revert the puzzle to the state it was before executing this action.<br>
	 * This method should be called BEFORE executing the action, and no other action should be performed on the puzzle before this action is executed.<br>
	 * Created by jtoumit on 21 janv. 2010.<br>
	 * @return
	 */
	IAction<T> getReverseAction();
}
