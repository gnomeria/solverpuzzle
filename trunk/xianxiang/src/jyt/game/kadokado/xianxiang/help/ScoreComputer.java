/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

public class ScoreComputer
{
	public static final int[] sScores = new int[] {1, 50, 300, 1000};
	private int mTotal = 0;

	public void addScore(int pScore)
	{
		mTotal += pScore;
	}

	public void removeScore(int pScore)
	{
		mTotal -= pScore;
	}

	public int computeScore(Element pElement1, Element pElement2)
	{
		return sScores[pElement1.fitness(pElement2)];
	}

	public int getCurrentScore()
	{
		return mTotal;
	}
}
