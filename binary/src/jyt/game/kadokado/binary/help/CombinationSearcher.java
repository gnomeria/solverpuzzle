/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.Result;
import jyt.game.puzzle.solving.impl.actions.RotationClockWise;

public class CombinationSearcher
{
	private IPuzzleAnalyzer mPuzzleAnalyzer;
	private ActionManager<Element> mActionManager;
	private List<Combination> mCombinations;
	private Stack<RotationClockWise<Element>> mCurrentStack;

	/**
	 * Created on 28 mars 2010 by jtoumit.<br>
	 * @param pPuzzleAnalyzer
	 */
	public CombinationSearcher(IPuzzleAnalyzer pPuzzleAnalyzer)
	{
		super();
		mPuzzleAnalyzer = pPuzzleAnalyzer;
	}

	public synchronized Combination[] search(Puzzle<Element> pPuzzle)
	{
		mCombinations = new ArrayList<Combination>();
		mActionManager = new ActionManager<Element>(pPuzzle, new Collapser());
		mCurrentStack = new Stack<RotationClockWise<Element>>();
		search(pPuzzle, 1);
		Collections.sort(mCombinations, new Comparator<Combination>()
		{
			@Override
			public int compare(Combination pO1, Combination pO2)
			{
				return new Double(pO1.getFitness()).compareTo(new Double(pO2.getFitness()));
			}
		});

		return mCombinations.toArray(new Combination[mCombinations.size()]);
	}

	private void search(Puzzle<Element> pPuzzle, int pIterations)
	{
		for (int x = 0; x < pPuzzle.getWidth() - 1; x++)
		{
			for (int y = 0; y < pPuzzle.getHeight() - 1; y++)
			{
				RotationClockWise<Element> rotation = new RotationClockWise<Element>(x, y);
				mCurrentStack.push(rotation);
				Result<Element> collapsed = mActionManager.executeActionAndCollapse(rotation);
				if (collapsed.nbReleased() == 0)
				// No group found, if we can, search further
				{
					if (pIterations < 3)
						search(pPuzzle, pIterations + 1);
				}
				else
				// We found a group, add the combination
				{
					mCombinations.add(new Combination(createArray(), mPuzzleAnalyzer.computeFitness(pPuzzle)));
				}
				mActionManager.rollBack();
				mCurrentStack.pop();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private RotationClockWise<Element>[] createArray()
	{
		return mCurrentStack.toArray((RotationClockWise<Element>[])new RotationClockWise<?>[mCurrentStack.size()]);
	}
}
