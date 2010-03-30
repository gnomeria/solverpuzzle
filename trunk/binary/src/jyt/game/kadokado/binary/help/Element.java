/*
 * Created on 24 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.Color;

public class Element
{
	private Color mColor;
	private int mPoints;

	/**
	 * Created on 24 mars 2010 by jtoumit.<br>
	 * @param pColor
	 */
	public Element(Color pColor, int pPoints)
	{
		super();
		assert pColor != null;
		mColor = pColor;
		mPoints = pPoints;
	}

	public Color getColor()
	{
		return mColor;
	}

	public int getPoints()
	{
		return mPoints;
	}

	@Override
	public boolean equals(Object pObj)
	{
		if (pObj == this)
			return true;
		else if (pObj instanceof Element)
			return mColor.equals(((Element)pObj).getColor());
		else
			return false;
	}

	@Override
	public int hashCode()
	{
		return mColor.hashCode();
	}
}
