/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;



public class TestPlayersGenetic extends JFrame
{
	private static final Random mRandom = new Random(System.currentTimeMillis());
	private Double[][] allResults;

	public TestPlayersGenetic()
	{
		super("Genetic testing for test players");
		setBounds(0, 0, 600, 350);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent pE)
			{
				System.exit(0);
			}
		});

	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		for (int i = 0; i < allResults.length; i++)
		{
			for (int j = 0; j < allResults[i].length; j++)
			{
				if (allResults[i][j] != null)
				{
					int c = 200 - i * 2;
					g.setColor(new Color(c, c, c));
					g.drawRect(j * 2, (int)(allResults[i][j].doubleValue() * 350 / 100000), 1, 1);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		TestPlayersGenetic frame = new TestPlayersGenetic();
		frame.allResults = new Double[100][];
		for (int i = 0; i < frame.allResults.length; i++)
			frame.allResults[i] = new Double[100];
		frame.setVisible(true);
		
		OneTest bestTest = null;
		int nbRun = 100;
		List<OneTest> individuals = new ArrayList<OneTest>();
		ExecutorService poolExecutor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 100; i++)
		{
			double[] weights = new double[22];
			for (int j = 0; j < weights.length; j++)
				weights[j] = mRandom.nextDouble() * 100;
			individuals.add(new OneTest(weights, nbRun));
		}
		long start = System.currentTimeMillis();
		for (int iRun = 0; iRun < 100; iRun++)
		{
			List<Future<OneTest>> futures = new ArrayList<Future<OneTest>>();
			System.out.println("Run " + iRun);
			for (OneTest oneTest : individuals)
				futures.add(poolExecutor.submit(oneTest, oneTest));
			int currentIndividual = 0;
			for (Future<OneTest> future : futures)
			{
				OneTest test = future.get();
				if ((bestTest == null) || (test.getResult() > bestTest.getResult()))
					bestTest = test;
				frame.allResults[iRun][currentIndividual++] = new Double(test.getResult());
				frame.repaint();
				System.out.print("*");
			}
			System.out.println();
			printCurrentResult(bestTest, iRun, 100, start);

			// Now seed the next generation
			// Sort in decreasing order and kill the worst while keeping the best
			Collections.sort(individuals, new Comparator<OneTest>()
			{
				public int compare(OneTest t1, OneTest t2)
				{
					return -new Integer(t1.getResult()).compareTo(new Integer(t2.getResult()));
				};
			});
			for (int iInd = 0; iInd < individuals.size(); iInd++)
			{
				if (iInd > individuals.size() * 0.9)
				// kill the 10% worst and feed them with random stuff
				{
					double[] weights;
					individuals.set(iInd, new OneTest(weights, nbRun));
				}
				else if (iInd > individuals.size() * 0.6)
				// kill the 40% worst and feed them with the 10% best
					individuals.set(iInd, new OneTest(shakeShake(individuals.get(mRandom.nextInt(10) * individuals.size() / 100).getWeights(), individuals, 50, 20, 20), nbRun));
				else if (iInd < individuals.size() / 50)
				// keep the 2% best
				{
					
				}
				else if (iInd < individuals.size() / 10)
				// shake the others a little until 10%
				{
					individuals.set(iInd, new OneTest(shakeShake(individuals.get(iInd).getWeights(), individuals, 80, 5, 5), nbRun));
				}
				else
				// Shake the middle a little
					individuals.set(iInd, new OneTest(shakeShake(individuals.get(iInd).getWeights(), individuals, 70, 10, 10), nbRun));
			}
		}
		poolExecutor.shutdown();
		System.out.println("Done");
	}

	private static double[] shakeShake(double[] pDouble, List<OneTest> pIndividuals, int pPercentKeep, int pPercentCopy, int pPercentRandom)
	{
		double[] res = new double[pDouble.length];
		for (int i = 0; i < res.length; i++)
		{
			int rand = mRandom.nextInt(100);
			if (rand < pPercentKeep)
				res[i] = pDouble[i];
			else if (rand > 100 - pPercentCopy)
			{
				int chooseOne = mRandom.nextInt(30) * pIndividuals.size() / 100;
				res[i] = pIndividuals.get(chooseOne).getWeights()[i];
			}
			else if (rand < pPercentKeep + pPercentRandom)
				res[i] = mRandom.nextDouble() * 100;
			else
				res[i] *= Math.min(100, Math.max(0, mRandom.nextDouble() * 2));
		}
		return res;
	}

	private static void printCurrentResult(OneTest pBestTest, int pCurrent, int pTotal, long pStart)
	{
		System.out.println(((System.currentTimeMillis() - pStart) / 1000) + "s, " + pCurrent + " / " + pTotal + ", currentBestHit: " + pBestTest.mResult + " (" + pBestTest.mMin + "-" + pBestTest.mMax + "), " + pBestTest.mStringRepresentation);
	}

}
