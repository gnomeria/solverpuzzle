/*
 * Created on 20 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

/**
 * Evaluates the current puzzle and returns a double between 0 and 1.
 * Created on 20 janv. 2010.<br>
 * @author jtoumit
 * @param <T>
 */
public interface IEvaluation<T>
{
	double evaluate(Puzzle<T> pPuzzle);
}
