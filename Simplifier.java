import java.util.Scanner;

public class Simplifier {
	private int ARRAY_MAGNITUDE = 20;
	private String[] MINTERMS = new String[ARRAY_MAGNITUDE];
	private String[] IGNORANCE = new String[ARRAY_MAGNITUDE];
	private String[] PASS_IGNORANCE = new String[ARRAY_MAGNITUDE];
	private String[][] PRIME_CALC = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];

	private String[] SUB_MINTERMS = new String[ARRAY_MAGNITUDE];
	private int SUB_MIN_INDEX = 0;
	private char IS_COMPLETE = 'F';
	private char IS_FIRST_PRIME = 'T';
	private int NUMBER_OF_VARIABLE = 0;
	
	private String[] VARIABLE_NAME = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N"};
	

	public String getSimplifier(int[] arrMinTerms, int[] arrIgnorances, int variable) {
		String functionSimplify = "";
		NUMBER_OF_VARIABLE = variable;
		
		
		MINTERMS = toBinary(arrMinTerms);
		IGNORANCE = toBinary(arrIgnorances);

		functionSimplify = completeMethods();
		return functionSimplify;
	}

	
	private void printArray (String[] anArray)
	{
		for (int i = 0; i < anArray.length; i++)
		{
			System.out.print(anArray[i] + " ");
		}
		System.out.println();
	}

	private String[] toBinary(int[] minArray) {
		int a = 0;
		String[] returnMinTerms = new String[200];
		returnMinTerms = fill1DArray(returnMinTerms);

		while (minArray[a] != -1) {
			returnMinTerms[a] = toBinaryString(minArray[a]);
			a++;
		}
		return returnMinTerms;
	}

	private String completeMethods() {
		int a = 0, b = 0;
		String[][] finalPass = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];

		String[][] remainingMinterms = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		remainingMinterms = fillArrays(remainingMinterms);

		finalPass = fillArrays(finalPass);
		PRIME_CALC = fillArrays(PRIME_CALC);
		PASS_IGNORANCE = fill1DArray(PASS_IGNORANCE);
		SUB_MINTERMS = fill1DArray(SUB_MINTERMS);

		finalPass = fillMinterms(MINTERMS);		
		while (MINTERMS[a] != "-1") {
			a++;
		}

		while (IGNORANCE[b] != "-1") {
			MINTERMS[a] = IGNORANCE[b];
			a++;
			b++;
		}

		a = 0;
		b = 0;

		finalPass = fillMinterms(MINTERMS);

		for (int i = 0; i < finalPass.length; i++) {
			for (int j = 0; j < finalPass[i].length; j++) {
				if (finalPass[i][j] != "-1") {
					PRIME_CALC[b][0] = finalPass[i][j];
					b++;
				}
			}

		}
		PASS_IGNORANCE = fillDontCare(IGNORANCE);
		while (IS_COMPLETE != 'T') {
			finalPass = compareAdjacentMinterms(finalPass);
		}

		removeDuplicatePrimes();
		remainingMinterms = getEssentialPrimes();
		if (remainingMinterms[0][0] != "-1") {
			remainingMinterms = getPrimes(remainingMinterms);
		}

		a = 0;
		b = 0;

		while (PRIME_CALC[a][0] != "-1" && PRIME_CALC[a][0] != "D" && PRIME_CALC[a][0] != "Y") {
			SUB_MINTERMS[SUB_MIN_INDEX] = PRIME_CALC[a][0];
			SUB_MIN_INDEX++;
			a++;
		}

		return giveOutput(SUB_MINTERMS);
	}

	private String[][] fillMinterms(String[] arrMinterms) {
		String tempMinterm;
		int a = 0, count;
		int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0, sevens = 0, eights = 0, nines = 0,
				tens = 0, elevens = 0, twelves = 0, thirteens = 0;
		String[][] groupWise = new String[100][100];
		fillArrays(groupWise);

		while (arrMinterms[a] != "-1") {
			count = 0;
			tempMinterm = arrMinterms[a];

			while (tempMinterm.length() < NUMBER_OF_VARIABLE) {
				tempMinterm = "0" + tempMinterm;
			}

			for (int i = 0; i < tempMinterm.length(); i++) {
				if (tempMinterm.charAt(i) == '1') {
					count++;
				}
			}

			if (count == 0) {
				groupWise[0][0] = tempMinterm;
			} else if (count == 1) {
				groupWise[1][ones] = tempMinterm;
				ones++;
			} else if (count == 2) {
				groupWise[2][twos] = tempMinterm;
				twos++;
			} else if (count == 3) {
				groupWise[3][threes] = tempMinterm;
				threes++;
			} else if (count == 4) {
				groupWise[4][fours] = tempMinterm;
				fours++;
			} else if (count == 5) {
				groupWise[5][fives] = tempMinterm;
				fives++;
			} else if (count == 6) {
				groupWise[6][sixes] = tempMinterm;
				sixes++;
			} else if (count == 7) {
				groupWise[7][sevens] = tempMinterm;
				sevens++;
			} else if (count == 8) {
				groupWise[8][eights] = tempMinterm;
				eights++;
			} else if (count == 9) {
				groupWise[9][nines] = tempMinterm;
				nines++;
			} else if (count == 10) {
				groupWise[10][tens] = tempMinterm;
				tens++;
			} else if (count == 11) {
				groupWise[11][elevens] = tempMinterm;
				elevens++;
			} else if (count == 12) {
				groupWise[12][twelves] = tempMinterm;
				twelves++;
			} else if (count == 13) {
				groupWise[12][thirteens] = tempMinterm;
				thirteens++;
			}

			a++;
		}

		return groupWise;
	}

	private String[] fillDontCare(String[] arrMinterms) {
		String[] groupWiseIgnorance = new String[ARRAY_MAGNITUDE];
		groupWiseIgnorance = fill1DArray(groupWiseIgnorance);
		String tempMinterm;
		int a = 0, b = 0;

		while (arrMinterms[a] != "-1") {

			tempMinterm = arrMinterms[a];

			while (tempMinterm.length() < NUMBER_OF_VARIABLE) {
				tempMinterm = "0" + tempMinterm;
			}

			groupWiseIgnorance[b] = tempMinterm;
			b++;
			a++;
		}

		return groupWiseIgnorance;
	}

	private String[][] compareAdjacentMinterms(String[][] groupWise) {
		String[][] allArrayCombined = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		int count = 0, c = 0;
		int index = 0;
		int minComp = 0, minComp2 = 1, minComp1 = 0, minComp3 = 0;
		int index1 = 0, index2 = 0;

		for (int i = 0; i < ARRAY_MAGNITUDE; i++) {
			for (int j = 0; j < ARRAY_MAGNITUDE; j++) {
				allArrayCombined[i][j] = "-1";
			}
		}
		while (c < groupWise.length) {
			if (groupWise[c][0] != "-1" && groupWise[c + 1][0] != "-1")
				count++;
			c++;
		}

		if (count >= 1) {
			count = 0;
			while (minComp < groupWise.length - 1) {

				while (groupWise[minComp][minComp1] != "-1") {

					while (groupWise[minComp2][minComp3] != "-1") {

						for (int ij = 0; ij < NUMBER_OF_VARIABLE; ij++) {

							if (groupWise[minComp][minComp1].charAt(ij) != groupWise[minComp2][minComp3]
									.charAt(ij)) {
								count++;
								index = ij;
							}
						}

						if (count == 1) {

							allArrayCombined[index1][index2] = groupWise[minComp2][minComp3].substring(0, index) + 'x'
									+ groupWise[minComp2][minComp3].substring(index + 1, NUMBER_OF_VARIABLE);

							primeFill(allArrayCombined[index1][index2], groupWise[minComp][minComp1],
									groupWise[minComp2][minComp3]);
							index2++;

						}

						count = 0;
						minComp3++;

					}

					minComp3 = 0;

					minComp1++;

				}

				minComp++;
				minComp2++;
				minComp1 = 0;

				index1++;

				index2 = 0;
				minComp3 = 0;

			}
			IS_FIRST_PRIME = 'F';
		}

		else
			IS_COMPLETE = 'T';
		return allArrayCombined;
	}

	private void primeFill(String result, String op1, String op2) {
		int j = 1;
		if (IS_FIRST_PRIME == 'T') {
			for (int i = 0; i < PRIME_CALC.length; i++) {

				if (PRIME_CALC[i][0] == op1 || PRIME_CALC[i][0] == op2) {

					while (PRIME_CALC[i][j] != "-1") {
						j++;
					}
					PRIME_CALC[i][j] = result;
					j = 1;
				}

			}
		} else {

			for (int m = 0; m < PRIME_CALC.length; m++) {
				for (int n = 0; n < PRIME_CALC[m].length; n++) {
					if (PRIME_CALC[m][n] == op1 || PRIME_CALC[m][n] == op2) {

						PRIME_CALC[m][n] = result;

					}
				}

			}

		}
	}

	private void removeDuplicatePrimes() {
		int primeCalcIndex = 0, currentIndex = 1, checkIndex = 2, tempCheckIndex = 0;

		while (PRIME_CALC[primeCalcIndex][0] != "-1") {
			while (PRIME_CALC[primeCalcIndex][currentIndex] != "-1") {
				checkIndex = currentIndex + 1;
				while (PRIME_CALC[primeCalcIndex][checkIndex] != "-1") {
					tempCheckIndex = checkIndex;

					if (PRIME_CALC[primeCalcIndex][currentIndex].equals(PRIME_CALC[primeCalcIndex][checkIndex])) {

						while (PRIME_CALC[primeCalcIndex][tempCheckIndex] != "-1") {
							PRIME_CALC[primeCalcIndex][tempCheckIndex] = PRIME_CALC[primeCalcIndex][tempCheckIndex
									+ 1];
							tempCheckIndex++;
						}

						PRIME_CALC[primeCalcIndex][tempCheckIndex] = "-1";
						checkIndex--;
					}

					checkIndex++;
				}
				currentIndex++;
			}

			currentIndex = 1;
			checkIndex = 2;
			primeCalcIndex++;
		}

	}

	private String[][] getEssentialPrimes() {
		int i = 0, j = 0;
		int a = 0, b = 0;
		SUB_MIN_INDEX = 0;
		String[] tempBack = new String[ARRAY_MAGNITUDE];
		tempBack = fill1DArray(tempBack);

		String[][] tempPrimeCalc = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		tempPrimeCalc = fillArrays(tempPrimeCalc);

		while (PRIME_CALC[i][0] != "-1") {
			while (PASS_IGNORANCE[j] != "-1") {
				if (PRIME_CALC[i][0].equals(PASS_IGNORANCE[j])) {
					PRIME_CALC[i][0] = "D";
				}
				j++;
			}
			j = 0;
			i++;
		}

		i = 0;
		j = 0;
		while (PRIME_CALC[i][0] != "-1") {
			if (PRIME_CALC[i][2].equals("-1") && PRIME_CALC[i][0] != "D" && PRIME_CALC[i][1] != "-1") {
				SUB_MINTERMS[SUB_MIN_INDEX] = PRIME_CALC[i][1];
				SUB_MIN_INDEX++;

				while (PRIME_CALC[a][0] != "-1") {
					while (PRIME_CALC[a][b] != "-1") {
						if (PRIME_CALC[a][b].equals(PRIME_CALC[i][1])) {
							PRIME_CALC[a][0] = "Y";
						}

						b++;
					}
					b = 0;
					a++;
				}
				a = 0;
				b = 0;

			}
			i++;
		}

		a = 0;
		b = 0;
		int c = 0, d = 0;
		while (PRIME_CALC[a][0] != "-1") {
			if (PRIME_CALC[a][0] != "D" && PRIME_CALC[a][0] != "Y") {
				while (PRIME_CALC[a][b] != "-1") {
					tempPrimeCalc[c][d] = PRIME_CALC[a][b];
					b++;
					d++;

				}
				c++;
			}
			a++;

			b = 0;
			d = 0;
		}

		return tempPrimeCalc;

	}

	private String[][] getPrimes(String[][] tempPrimeCalc) {
		int a = 0, b = 1, c = 0, d = 0, i = 0;
		int count = 0;
		String maxMinterm;
		int maxIndex = 0;
		String[] primes = new String[ARRAY_MAGNITUDE];
		primes = fill1DArray(primes);

		int[] primeCount = new int[ARRAY_MAGNITUDE];
		primeCount = fill1DIntArray(primeCount);

		String[][] tempPrimeCalc2 = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		tempPrimeCalc2 = fillArrays(tempPrimeCalc2);

		while (tempPrimeCalc[a][0] != "-1") {
			while (tempPrimeCalc[a][b] != "-1") {
				primes[i] = tempPrimeCalc[a][b];
				b++;
				i++;
			}
			a++;
		}

		a = 0;
		i = 0;
		while (primes[i] != "-1") {
			while (tempPrimeCalc[a][0] != "-1") {
				while (tempPrimeCalc[a][b] != "-1") {
					if (primes[i] == tempPrimeCalc[a][b]) {
						count++;
						b = 1;
						break;
					}

					b++;
				}
				b = 1;
				a++;
			}
			a = 0;
			b = 1;
			i++;
			primeCount[c] = count;
			count = 0;
			c++;
		}

		count = 0;
		c = 0;
		while (primeCount[c] != -1) {
			if (primeCount[c] > count) {
				count = primeCount[c];
				maxIndex = c;
			}
			c++;
		}
		a = 0;
		b = 1;
		maxMinterm = primes[maxIndex];
		if (maxMinterm != "-1") {
			SUB_MINTERMS[SUB_MIN_INDEX] = maxMinterm;
			SUB_MIN_INDEX++;
		}

		while (tempPrimeCalc[a][0] != "-1") {
			while (tempPrimeCalc[a][b] != "-1") {
				if (tempPrimeCalc[a][b].equals(maxMinterm)) {
					tempPrimeCalc[a][0] = "Y";
				}

				b++;
			}
			b = 0;
			a++;
		}

		a = 0;
		b = 0;
		c = 0;
		d = 0;

		while (PRIME_CALC[a][0] != "-1") {
			while (PRIME_CALC[a][b] != "-1") {
				if (PRIME_CALC[a][b].equals(maxMinterm)) {
					PRIME_CALC[a][0] = "Y";
				}

				b++;
			}
			b = 0;
			a++;
		}

		a = 0;
		b = 0;
		c = 0;
		d = 0;
		while (tempPrimeCalc[a][0] != "-1") {
			if (tempPrimeCalc[a][0] != "Y") {
				while (tempPrimeCalc[a][b] != "-1") {
					tempPrimeCalc2[c][d] = tempPrimeCalc[a][b];
					b++;
					d++;

				}
				c++;
			}
			a++;

			b = 0;
			d = 0;
		}

		return tempPrimeCalc2;

	}

	private String giveOutput(String[] arrOutput) {
		int count = 0;
		String[] minimizedArray = new String[ARRAY_MAGNITUDE];
		fill1DArray(minimizedArray);
		int minimizedIndex = 0;
		String outputString = "";
		String tempMinterm = "";
		String tempMintermCheck = "";
		int i = 0;

		while (arrOutput[i] != "-1") {
			for (int ij = NUMBER_OF_VARIABLE - 1; ij >= 0; ij--) {
				if (arrOutput[i].charAt(ij) != 'x') {
					if (arrOutput[i].charAt(ij) == '0') {						
						tempMintermCheck = VARIABLE_NAME[ij] + "'";
					}
					if (arrOutput[i].charAt(ij) == '1') {						
						tempMintermCheck = VARIABLE_NAME[ij];
					}
					tempMinterm = tempMintermCheck + tempMinterm;

				}
			}

			i++;

			for (int j = 0; j < minimizedArray.length; j++) {

				if (minimizedArray[j].equals(tempMinterm)) {
					count++;
				}
			}

			if (count == 0) {
				minimizedArray[minimizedIndex] = tempMinterm;
				minimizedIndex++;
			}
			tempMinterm = "";
			count = 0;

		}
		i = 0;

		minimizedIndex = 0;
		while (minimizedArray[minimizedIndex] != "-1") {
			outputString = outputString + "+" + minimizedArray[minimizedIndex];
			minimizedIndex++;
		}
		outputString = outputString.substring(1, outputString.length());
		return outputString;
	}

	/*
	private String[][] fillDontCares(String[][] arrToFill) {
		int final_pass_dontcare_index = 0;
		while (PASS_IGNORANCE[final_pass_dontcare_index] != "-1") {
			for (int i = 0; i < arrToFill.length; i++) {
				for (int j = 0; j < arrToFill[i].length; j++) {
					if (PASS_IGNORANCE[final_pass_dontcare_index].equals(arrToFill[i][j])) {
						arrToFill[i][j] = "-1";

					}
				}
			}
			final_pass_dontcare_index++;
		}
		return arrToFill;
	}
	*/

	private String[][] fillArrays(String[][] allArrayCombined) {
		for (int i = 0; i < allArrayCombined.length; i++) {
			for (int j = 0; j < allArrayCombined[i].length; j++) {
				allArrayCombined[i][j] = "-1";
			}
		}

		return allArrayCombined;
	}

	/*
	private int[][] fillIntArrays(int[][] allArrayCombined) {
		for (int i = 0; i < allArrayCombined.length; i++) {
			for (int j = 0; j < allArrayCombined[i].length; j++) {
				allArrayCombined[i][j] = -1;
			}
		}

		return allArrayCombined;
	}
	*/

	private String[] fill1DArray(String[] arrToFill) {
		for (int i = 0; i < arrToFill.length; i++) {
			arrToFill[i] = "-1";
		}
		return arrToFill;
	}

	private int[] fill1DIntArray(int[] arrToFill) {

		for (int i = 0; i < arrToFill.length; i++) {
			arrToFill[i] = -1;
		}

		return arrToFill;
	}

	private String toBinaryString(int aValue) {
		int quotient = aValue, remainder;
		String result = "";
		
		if (aValue == 0)
		{
			while (result.length() < NUMBER_OF_VARIABLE)
			{
				result += "0";
			}
			return result;
		}
		
		if (aValue == 1)
		{
			result = "1";
			while (result.length() < NUMBER_OF_VARIABLE)
			{
				result = "0" + result;
			}
			return result;
		}
			
		while (quotient != 0) {

			remainder = quotient % 2;
			quotient = quotient / 2;
			result = Integer.toString(remainder) + result;
		}
		
		return result;
	}

}
