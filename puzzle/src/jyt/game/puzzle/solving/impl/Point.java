/*
 * Created on 26 mars 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving.impl;

public class Point
{
	private int x;
	private int y;

	/**
	 * Created on 26 mars 2010 by jtoumit.<br>
	 * @param pX
	 * @param pY
	 */
	public Point(int pX, int pY)
	{
		super();
		x = pX;
		y = pY;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

	@Override
	public boolean equals(Object pObj)
	{
		if (pObj == this)
			return true;
		else if (pObj instanceof Point)
		{
			Point point = (Point)pObj;
			return (x == point.x) && (y == point.y);
		}
		else
			return false;
	}
}
