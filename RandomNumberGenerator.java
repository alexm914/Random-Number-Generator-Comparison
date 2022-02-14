package programmingProject2;
//****************************************************************************
// Farha Z., Alex McNulty
// Project 2 (COSC 211)
// Winter 2021
//
// RandomNumGenerator.java
//
// A Java application that will compare the randomness of the PRNG 
// class, the CSPRNG class, and the true random number generator 
// (TRNG) service provided by Random.org.
//****************************************************************************

import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class RandomNumberGenerator 
{
	static int gcd;
	static int num1;
	static int num2;

	static double[] allNums;
	static double PRNGsum = 0;
	static double TRNGsum = 0;
	
	// Farha and I both worked on the main method
	// print out frequency table, average, and standard deviation for both RNGs
	public static void main(String[] args) 
	{
		
		// code for CSPRNG
		double cryptoTotal = 0;
		
		double[] piEstimate = new double[30];
		
		// assign pi estimates to array
		for (int i = 0; i < 30; i++)
		{
			piEstimate[i] = CSPRNG.runCSPRNG();
			
			// add up pi estimates to find average	
			cryptoTotal += piEstimate[i];
		}

		// calculate average
		double cryptoAverage = cryptoTotal / 30;
		
		// print header for CSPRNG table
		System.out.println("\nCSPRNG Pi Estimate Frequency Distribution Table\n");
			
		
		// get the CSPRNG table
		CSPRNG.getFreqDistrTable(piEstimate);
		
		// print CSPRNG average
		System.out.print("\nAverage: ");
		System.out.printf("%.12f\n", cryptoAverage);
		
		// print CSPRNG standard deviation
		CSPRNG.getStandardDev(piEstimate, cryptoAverage);
		
		// print PRNG frequency distribution table, average, and standard deviation
		GetFrequencyTable("PRNG");
		System.out.println("\nAverage: " + PRNGsum / 30);
		System.out.println("Standard deviation: " + GetStandardDev(PRNGsum / 30) + "\n");

		// print TRNG frequency distribution table, average, and standard deviation
		GetFrequencyTable("TRNG");
		System.out.println("\nAverage: " + TRNGsum / 30);
		System.out.println("Standard deviation: " + GetStandardDev(TRNGsum / 30) + "\n");
	}

	// our professor supplied this method
	// Euclid's Algorithm to calculate gcd for PRNG & TRNG
	static int EuclidsAlgorithm(int n1, int n2) 
	{
		if (n2 == 0) 
		{
			return n1;
		}
		return EuclidsAlgorithm(n2, n1 % n2);
	}

	// Farha did this method
	// create 100 pairs of random numbers for PRNG and find gcd
	static int PRNGRandomGcd() 
	{
		int gcdNumPRNG = 0;
		Random rand = new Random();

		// generate 100 pairs of nums (1-100)
		for (int i = 0; i < 100; i++) {
			num1 = rand.nextInt(100) + 1;
			num2 = rand.nextInt(100) + 1;

			// find gcd of each pair
			gcd = EuclidsAlgorithm(num1, num2);
			// System.out.println(num1 + "\t " + num2 + "\t " + gcd);

			// find num instances of gcd = 1
			if (gcd == 1)
			{
				gcdNumPRNG += 1;
			}
		}
		return gcdNumPRNG;
	}

	// Farha and I both worked on the TRNG method
	// create random numbers for Random.org and find gcd
	static int TRNGRandomGcd() 
	{
		int gcdNumRandom = 0;
		// request a set of integers (100 pairs) from Random.org
		String address = "https://www.random.org/integers/?num=200&min=1&max=100&col=1&base=10&format=plain&rnd=new";
		Scanner in = null;

		try 
		{
			URL u = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			InputStream stream = connection.getInputStream();
			in = new Scanner(stream);

			while (in.hasNext()) 
			{
				num1 = in.nextInt();
				num2 = in.nextInt();

				// calculate gcd and count instances of gcd = 1;
				gcd = EuclidsAlgorithm(num1, num2);
				// System.out.println(num1 + "\t " + num2 + "\t " + gcd);

				if (gcd == 1) {
					gcdNumRandom += 1;
				}
			}

		} 
		catch (IOException ex) 
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			in.close();
		}
		return gcdNumRandom;
	}

	// Farha did this method
	// method to get frequency table of PRNG and TRNG
	static void GetFrequencyTable(String RNG) 
	{
		double piRNG;

		// arrays to store frequency of pi values
		ArrayList<Integer> arr = new ArrayList<Integer>();
		ArrayList<Double> piEstimate = new ArrayList<Double>();
		allNums = new double[30];

		if (RNG.equals("PRNG")) 
		{
			System.out.println("PRNG Pi Estimate Frequency Table");
		}
		else 
		{
			System.out.println("TRNG Pi Estimate Frequency Table");
		}

		// get 30 estimates of pi from PRNG or TRNG
		for (int i = 0; i < 30; i++) 
		{
			// Estimate values of pi from RNGs
			if (RNG.equals("PRNG")) 
			{
				piRNG = Math.sqrt(6 / (PRNGRandomGcd() / 100.00));
				PRNGsum += piRNG;
			}
			else
			{
				piRNG = Math.sqrt(6 / (TRNGRandomGcd() / 100.00));
				TRNGsum += piRNG;
			}
			// add new nums to piEstimate arrayList
			piEstimate.add(piRNG);
			allNums[i] = piRNG;

			// check if num already exists
			for (int j = 0; j < piEstimate.size(); j++)
			{
				if (piEstimate.get(piEstimate.size() - 1).equals(piEstimate.get(j)) && (piEstimate.size() - 1) != j)
				{
					arr.set(j, arr.get(j) + 1);
					piEstimate.remove(piEstimate.size() - 1);
				}
				else
				{
					// add 1 for each occurrence of a number
					arr.add(1);
				}
			}
		}
		// print out table headers
		System.out.printf("%.15s%21.12s%30.25s", "Pi Estimate:", "Frequency:", "Difference from Math.PI");
		System.out.println("\n_______________________________________________________________\n");
		
		// print out frequency table
		for (int i = 0; i < piEstimate.size(); i++)
		{
			System.out.printf("%.12f%10d%25.7f\n", piEstimate.get(i), arr.get(i), Math.abs(Math.PI - piEstimate.get(i)));
		}
	}

	// Farha wrote this method
	// method to get standard deviation of array
	static double GetStandardDev(double average)
	{
		double add = 0;
		for (int i = 0; i < allNums.length; i++)
		{
			add += Math.pow(allNums[i] - average, 2);
		}
		double SD = Math.sqrt(add / 30);
		return SD;
	}
}
