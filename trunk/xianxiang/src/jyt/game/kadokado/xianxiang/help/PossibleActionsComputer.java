/*
 * Created on 31 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.Iterator;

import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class PossibleActionsComputer implements Iterator<ActionMatch>
{
	private int mSrcX = -1;
	private int mSrcY = 0;
	private int mDestX;
	private int mDestY;
	private Puzzle<Element> mPuzzle;
	private ScoreComputer mScoreComputer;

	/**
	 * Created on 31 mars 2010 by jtoumit.<br>
	 * @param pPuzzle
	 * @param pScoreComputer
	 */
	public PossibleActionsComputer(Puzzle<Element> pPuzzle, ScoreComputer pScoreComputer)
	{
		super();
		assert pPuzzle != null;
		assert pScoreComputer != null;

		mPuzzle = pPuzzle;
		// We don't want any destination so that we will increment X and find the first valid source;
		mDestX = mPuzzle.getWidth();
		mDestY = mPuzzle.getHeight();
		mScoreComputer = pScoreComputer;
		computeNext();
	}

	@Override
	public boolean hasNext()
	{
		return mSrcX != -1;
	}

	private void computeNext()
	{
		// Let's loop on destination and if not found on sources
		do
		{
			// Increment the destination X and see if we can do something about it
			mDestX++;
			if (mDestX < mPuzzle.getWidth())
			// This X is correct
			{
				if (mPuzzle.get(mDestX, mDestY) == null)
				// There is no element there, we can't do an action on nothing!
					continue;

				// try to find if there is a path from the source to this destination
				// Let's try the line first, we'll try the row next
				boolean foundObstacle = false;
				if (mDestX > mSrcX)
				{
					for (int x = mSrcX + 1; x <= mDestX; x++)
					{
						if (mPuzzle.get(x, mSrcY) != null)
						{
							foundObstacle = true;
							break;
						}
					}
				}
				else if (mDestX < mSrcX)
				{
					for (int x = mSrcX - 1; x >= mDestX; x--)
					{
						if (mPuzzle.get(x, mSrcY) != null)
						{
							foundObstacle = true;
							break;
						}
					}
				}
				if (!foundObstacle)
					// From the end of the line, try to reach the point on the row
					for (int y = mSrcY + 1; y < mDestY; y++)
					{
						if (mPuzzle.get(mDestX, y) != null)
						{
							foundObstacle = true;
							break;
						}
					}

				if (foundObstacle)
				// Try to find another path using the column first
				{
					foundObstacle = false;
					// Now try the column first
					for (int y = mSrcY + 1; y <= mDestY; y++)
					{
						if (mPuzzle.get(mSrcX, y) != null)
						{
							foundObstacle = true;
							break;
						}
					}
					if (!foundObstacle)
					{
						if (mDestX > mSrcX)
						{
							// From this column, follow the line now
							for (int x = mSrcX + 1; x < mDestX; x++)
							{
								if (mPuzzle.get(x, mDestY) != null)
								{
									foundObstacle = true;
									break;
								}
							}
						}
						else if (mDestX < mSrcX)
						{
							for (int x = mSrcX - 1; x > mDestX; x--)
							{
								if (mPuzzle.get(x, mDestY) != null)
								{
									foundObstacle = true;
									break;
								}
							}
						}
					}
					if (foundObstacle)
						continue;
				}

				// We have found no obstacle: go ahead and give back the result;
				break;
			}
			else
			// Woops we reached the end... let's increment Y and see if we can use it 
			{
				// Let's prepare for the next row
				mDestX = -1;
				mDestY++;
				if (mDestY >= mPuzzle.getHeight())
				// No more destinations at all, we've reached the end of the puzzle
				{
					do
					{
						// Increment the source
						mSrcX++;
						if (mSrcX >= mPuzzle.getWidth())
						// We reached the end of the line, increment Y...
						{
							mSrcX = 0;
							mSrcY++;
							if (mSrcY >= mPuzzle.getHeight())
							// We've reached the end of the puzzle in the sources. Nothing else to do here, we're done
							{
								mSrcX = -1;
								break;
							}
						}
						// The source is valid, initialize the destination to search for it
						mDestX = mSrcX;
						mDestY = mSrcY;
					}
					// Loop until we find some non null element at this place
					while (mPuzzle.get(mSrcX, mSrcY) == null);
					if (mSrcX == -1)
					// This means that we've reached the end
						break;
				}
			}
		}
		while (true);
	}

	@Override
	public ActionMatch next()
	{
		if (!hasNext())
			return null;

		ActionMatch actionMatch = new ActionMatch(new Point(mSrcX, mSrcY), new Point(mDestX, mDestY), mScoreComputer);
		computeNext();
		return actionMatch;
	}

	@Override
	public void remove()
	{
		throw new RuntimeException("Not implemented");
	}
}
