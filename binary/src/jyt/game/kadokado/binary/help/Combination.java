/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.io.Serializable;

import jyt.game.puzzle.solving.impl.actions.RotationClockWise;

public class Combination implements Serializable
{
	private RotationClockWise<Element>[] mRotations;
	private double mFitness;

	/**
	 * Created on 27 mars 2010 by jtoumit.<br>
	 * @param pRotations
	 * @param pFitness
	 */
	public Combination(RotationClockWise<Element>[] pRotations, double pFitness)
	{
		super();
		mRotations = pRotations;
		mFitness = pFitness;
	}

	public double getFitness()
	{
		return mFitness;
	}

	public RotationClockWise<Element>[] getRotations()
	{
		return mRotations;
	}
}
