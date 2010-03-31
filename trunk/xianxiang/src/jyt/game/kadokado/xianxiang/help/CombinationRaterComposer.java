/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jyt.game.puzzle.solving.Puzzle;

public class CombinationRaterComposer implements ICombinationRater
{
	private ICombinationRater[] mCombinationRaters;
	private double[] mWeights;

	/**
	 * Created on 30 mars 2010 by jtoumit.<br>
	 * @param pCombinationRaters
	 * @param pWeights
	 */
	public CombinationRaterComposer(ICombinationRater[] pCombinationRaters, double[] pWeights)
	{
		super();
		mCombinationRaters = pCombinationRaters;
		mWeights = pWeights;
	}

	@Override
	public RatedCombination[] getCombinations(Puzzle<Element> pPuzzle)
	{
		Map<RatedCombination, List<Double>> ratedCombinations = new HashMap<RatedCombination, List<Double>>();
		boolean first = true;
		for (ICombinationRater combinationRater : mCombinationRaters)
		{
			RatedCombination[] combinations = combinationRater.getCombinations(pPuzzle);
			Arrays.sort(combinations);
			if (first)
			{
				for (RatedCombination ratedCombination : combinations)
				{
					List<Double> list = new ArrayList<Double>();
					ratedCombinations.put(ratedCombination, list);
					list.add(ratedCombination.getRating());
				}
			}
			else
			{
				int nb = 1;
				for (RatedCombination ratedCombination : combinations)
				{
					List<Double> list = ratedCombinations.get(ratedCombination);
					if (list != null)
						if (list.size() == nb)
							list.add(ratedCombination.getRating());
						else
						// Remove this one as all ratings weren't given for this combinations by all combination raters
							ratedCombinations.remove(ratedCombination);
					nb++;
				}
			}
		}
		List<RatedCombination> keptCombinations = new ArrayList<RatedCombination>();
		for (RatedCombination ratedCombination : ratedCombinations.keySet())
		{
			double result = 0;
			List<Double> list = ratedCombinations.get(ratedCombination);
			for (int i = 0; i < mWeights.length; i++)
				result += mWeights[i] * list.get(i);
			keptCombinations.add(new RatedCombination(result, ratedCombination.getPoint1(), ratedCombination.getPoint2()));
		}
		return keptCombinations.toArray(new RatedCombination[keptCombinations.size()]);
	}
}
