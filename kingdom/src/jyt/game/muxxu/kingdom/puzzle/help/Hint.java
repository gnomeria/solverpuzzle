/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import jyt.game.puzzle.solving.Result;

public class Hint
{
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Result<Element> mResult;

	/**
	 * Created on 24 janv. 2010 by jtoumit.<br>
	 * @param pX1
	 * @param pY1
	 * @param pX2
	 * @param pY2
	 * @param pResult
	 */
	public Hint(int pX1, int pY1, int pX2, int pY2, Result<Element> pResult)
	{
		super();
		x1 = pX1;
		y1 = pY1;
		x2 = pX2;
		y2 = pY2;
		mResult = pResult;
	}

	public int getX1()
	{
		return x1;
	}

	public int getY1()
	{
		return y1;
	}

	public int getX2()
	{
		return x2;
	}

	public int getY2()
	{
		return y2;
	}

	public int getNbReleased()
	{
		return mResult.nbReleased();
	}

	public Result<Element> getResult()
	{
		return mResult;
	}
}
