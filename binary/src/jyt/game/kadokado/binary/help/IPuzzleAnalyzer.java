/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.io.Serializable;

import jyt.game.puzzle.solving.Puzzle;

public interface IPuzzleAnalyzer extends Serializable
{
	double computeFitness(Puzzle<Element> pPuzzle);
	String description();
}
