package programmingProject2;

/* Winter 2021
 * COSC 211  
 * Elsa Poh
 * Farha Zindah & Alex McNulty
 */

import java.security.SecureRandom;
import java.util.Scanner;

public class CSPRNG 
{
		// I'm doing CSPRNG
		private static SecureRandom randomInt = new SecureRandom();
		
		private static int cryptoNum1;
		private static int placeHolder1;
		private static int cryptoNum2;
		private static int placeHolder2;
		private static int cryptoGCD;
		private static int cryptoCount = 0;
		private static double percentGCD = 0;
		private static double estimatePi;
		private static int[] frequency = new int[30];
		private static int visited = -1;
		
		
		// Euclid's Algorithm to calculate gcd
		public static int EuclidsAlgorithm(int n1, int n2) 
		{
			if (n2 == 0) 
			{
				return n1;
			}
			return EuclidsAlgorithm(n2, n1 % n2);
		}
		
		
		public static double runCSPRNG()
		{			
			for (int i = 0; i < 100; i++)
			{

				placeHolder1 = randomInt.nextInt(100) + 1;
				
				placeHolder2 = randomInt.nextInt(100) + 1;
				
				
				cryptoNum2 = placeHolder2;
				cryptoNum1 = placeHolder1;
				
				cryptoGCD = EuclidsAlgorithm(cryptoNum1, cryptoNum2);
				
//				System.out.println(cryptoNum1 + "\t" + cryptoNum2 + "\t" + cryptoGCD);
				
					
					if (cryptoGCD == 1)
					{
						cryptoCount++;
						// get estimated value of pi
						
						percentGCD = cryptoCount / 100.0;
						estimatePi = Math.sqrt(6.0 / percentGCD);
						percentGCD = 0;
					}
//					System.out.print(array[row][column] + "\t");
				}
			
			// reset cryptoCount
			cryptoCount = 0;
							
			return estimatePi;
	}
		
		
		public static void getFreqDistrTable(double[] array)
		{
			System.out.printf("%.15s%21.12s%30.25s", "Pi Estimate", "Frequency", "Difference from Math.PI");
			System.out.println("\n______________________________________________________________\n");
			double[] cryptoPiDif = new double[30];

			for (int i = 0; i < array.length; i++)
			{
				int count = 1;		
				
				for (int j = i + 1; j < array.length; j++)
				{
					if (array[i] == array[j])
					{
						count++;
						frequency[j] = visited;
						
					}				

				}
				if (frequency[i] != visited) 
				{
					frequency[i] = count;
					cryptoPiDif[i] = Math.abs(Math.PI - array[i]);
				}
			
			}
			
			for (int i = 0; i < frequency.length; i++)
			{
				if (frequency[i] != visited)
				{
					
					System.out.printf("%.12f%10d%24.7f\n", array[i], frequency[i], cryptoPiDif[i]);
				}
			}
			
		}
		public static void getStandardDev(double[] array, double mean)
		{
			// used khan academy to figure out formula: 
			// https://www.khanacademy.org/math/statistics-probability/summarizing-quantitative-data/variance-standard-deviation-population/a/calculating-standard-deviation-step-by-step
			double deviation = 0;
			double sum = 0;
			double divide = 0;
			double standardDev = 0;
			
			for (int i = 0; i < array.length; i++)
			{
				// find distance of each data point from the mean and square
				deviation = Math.pow((Math.abs(array[i] - mean)), 2);
				
				// add up the deviations
				sum += deviation;
			}
			
			// divide by number of data points
			divide = sum / 30;
				
			// find standard deviation
			standardDev = Math.sqrt(divide);
			
			System.out.print("Standard deviation: " + standardDev + "\n\n");

		}
}
