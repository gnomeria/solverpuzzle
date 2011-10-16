/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.io.Serializable;
import java.util.Random;

import jyt.game.puzzle.solving.Puzzle;

public class PuzzleRefill implements Serializable
{
	private ScoreComputer mScoreComputer;
	private Random mRandom = new Random(System.currentTimeMillis());
	private final static int[] SCORES = new int[] {0, 0, 0, 0, 2500, 5000, 7500, 13000, 24000, 36000};

	/**
	 * Created on 28 mars 2010 by jtoumit.<br>
	 * @param pScoreComputer
	 */
	public PuzzleRefill(ScoreComputer pScoreComputer)
	{
		super();
		mScoreComputer = pScoreComputer;
	}

	public void refill(Puzzle<Element> pPuzzle)
	{
		int currentMax = 0;
		int score = 0;
		while ((mScoreComputer.getCurrentScore() > SCORES[score++]) && (score < SCORES.length))
			currentMax++;
		for (int x = 0; x < pPuzzle.getWidth(); x++)
		{
			for (int y = 0; y < pPuzzle.getHeight(); y++)
			{
				if (pPuzzle.get(x, y) == null)
				{
					int chosen = mRandom.nextInt(currentMax);
					pPuzzle.set(x, y, new Element(PuzzleBuilderRandom.COLORS[chosen], PuzzleBuilderRandom.POINTS[chosen]));
				}
			}
		}
	}
}
