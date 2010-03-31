/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import jyt.game.puzzle.solving.impl.Point;

public class RatedCombination implements Comparable<RatedCombination>
{
	private double mRating;
	private Point mPoint1;
	private Point mPoint2;

	/**
	 * Created on 30 mars 2010 by jtoumit.<br>
	 * @param pRating
	 * @param pPoint1
	 * @param pPoint2
	 */
	public RatedCombination(double pRating, Point pPoint1, Point pPoint2)
	{
		super();
		mRating = pRating;
		mPoint1 = pPoint1;
		mPoint2 = pPoint2;
	}

	public Point getPoint1()
	{
		return mPoint1;
	}
	public Point getPoint2()
	{
		return mPoint2;
	}
	public double getRating()
	{
		return mRating;
	}

	@Override
	public boolean equals(Object pObj)
	{
		if (pObj == null)
			return false;
		if (pObj == this)
			return true;
		if (pObj instanceof RatedCombination)
		{
			RatedCombination ratedCombination = (RatedCombination)pObj;
			return mPoint1.equals(ratedCombination.mPoint1) && mPoint2.equals(ratedCombination.mPoint2);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return mPoint1.hashCode() | mPoint2.hashCode();
	}

	@Override
	public int compareTo(RatedCombination pO)
	{
		return new Double(mRating).compareTo(new Double(pO.mRating));
	}
}
