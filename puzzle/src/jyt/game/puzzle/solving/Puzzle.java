/*
 * Created on 20 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jyt.game.puzzle.solving.impl.Point;

public class Puzzle<T> implements Serializable
{
	private T[][] mGrid;
	private Map<T, List<Point>> mElementsMap = null;
	private Integer mTotalPoints = null;

	@SuppressWarnings("unchecked")
	public Puzzle(int pWidth, int pHeight)
	{
		super();
		mGrid = (T[][])new Object[pWidth][pHeight];
		for (int i = 0; i < mGrid.length; i++)
			for (int j = 0; j < mGrid[i].length; j++)
				mGrid[i][j] = null;
	}

	@SuppressWarnings("unchecked")
	public Puzzle(final Puzzle<? extends T> pPuzzle)
	{
		super();
		mGrid = (T[][])new Object[pPuzzle.getWidth()][pPuzzle.getHeight()];
		for (int i = 0; i < pPuzzle.mGrid.length; i++)
			for (int j = 0; j < pPuzzle.mGrid[i].length; j++)
				mGrid[i][j] = pPuzzle.mGrid[i][j];
	}

	public T get(Point pPoint)
	{
		return get(pPoint.getX(), pPoint.getY());
	}

	public T get(int x, int y)
	{
		return mGrid[x][y];
	}

	public void set(int x, int y, T pElement)
	{
		mGrid[x][y] = pElement;
		mElementsMap = null;
		mTotalPoints = null;
	}

	public void set(Point pPoint, T pElement)
	{
		set(pPoint.getX(), pPoint.getY(), pElement);
	}

	public int getWidth()
	{
		return mGrid.length;
	}
	
	public int getHeight()
	{
		return mGrid[0].length;
	}

	public Map<T, List<Point>> getElementsMap()
	{
		if (mElementsMap == null)
		// If it was reset, recompute the cache
		{
			mElementsMap = new HashMap<T, List<Point>>();
			for (int x = 0; x < getWidth(); x++)
			{
				for (int y = 0; y < getHeight(); y++)
				{
					T element = get(x, y);
					if (element != null)
					{
						List<Point> elements = mElementsMap.get(element);
						if (elements == null)
							mElementsMap.put(element, elements = new ArrayList<Point>());
						elements.add(new Point(x, y));
					}
				}
			}
		}
		return mElementsMap;
	}

	public int getTotalPoints()
	{
		if (mTotalPoints == null)
		{
			int total = 0;
			for (List<Point> list : getElementsMap().values())
				total += list.size();
			mTotalPoints = total;
		}
		return mTotalPoints;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mGrid[0].length; i++)
		{
			for (int j = 0; j < mGrid.length; j++)
			{
				sb.append(mGrid[j][i] == null ? ' ' : mGrid[j][i].toString().charAt(0));
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}
