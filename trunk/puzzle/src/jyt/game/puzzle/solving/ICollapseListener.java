/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

public interface ICollapseListener<T>
{
	void collapsing(T pType, int pNb);
}
