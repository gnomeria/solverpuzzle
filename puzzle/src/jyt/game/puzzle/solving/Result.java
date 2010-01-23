/*
 * Created on 20 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

import java.util.ArrayList;
import java.util.List;

public class Result<T>
{
	private List<Integer> mBlockSize;
	private List<T> mElement;

	public Result()
	{
		super();
		mBlockSize = new ArrayList<Integer>();
		mElement = new ArrayList<T>();
	}

	public void release(int pNb, T pElement)
	{
		mBlockSize.add(pNb);
		mElement.add(pElement);
	}

	public int nbReleased()
	{
		return mBlockSize.size();
	}

	public int getBlockSize(int i)
	{
		return mBlockSize.get(i);
	}

	public T getElement(int i)
	{
		return mElement.get(i);
	}
}
