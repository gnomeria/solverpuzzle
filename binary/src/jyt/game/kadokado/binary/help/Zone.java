/*
 * Created on 26 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.io.Serializable;

import jyt.game.puzzle.solving.impl.Point;

public class Zone implements Serializable
{
	private Point[] mPoints;

	/**
	 * Created on 26 mars 2010 by jtoumit.<br>
	 * @param pPoints
	 */
	public Zone(Point[] pPoints)
	{
		super();
		mPoints = pPoints;
	}

	public Point[] getPoints()
	{
		return mPoints;
	}
}
