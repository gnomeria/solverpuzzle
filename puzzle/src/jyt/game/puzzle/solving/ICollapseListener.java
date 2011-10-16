/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

import java.io.Serializable;

public interface ICollapseListener<T> extends Serializable
{
	void collapsing(T pType, int pNb);
}
