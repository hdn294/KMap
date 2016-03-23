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
	private int VARIABLE;
	

	public String getSimplifier(int[] arrMinTerms, int[] arrIgnorances, int variable) {
		String functionSimplify = "";
		VARIABLE = variable;
		
		NUMBER_OF_VARIABLE = getNumberOfVariable(arrMinTerms);
		MINTERMS = toBinary(arrMinTerms);
		IGNORANCE = toBinary(arrIgnorances);
		
		printArray(MINTERMS);
		printArray(IGNORANCE);

		functionSimplify = completeMethods();
		return functionSimplify;
	}

	private int getNumberOfVariable(int[] min_array) {
		int max = 0;
		int a = 0;

		while (min_array[a] != -1) {
			if (min_array[a] > max) 
			{
				max = min_array[a];
			}
			a++;
		}
		if (max == 0) return 0; 
		else if (max == 1) return 1;
		else if (max > 1 && max < 4) return 2; 
		else if (max >= 4 && max < 8) return 3; 
		else if (max >= 8 && max < 16) return 4; 
		else if (max >= 16 && max < 32) return 5; 
		else if (max >= 32 && max < 64) return 6; 
		else if (max >= 65 && max < 128) return 7; 
		else if (max >= 128 && max < 256) return 8;		
		else if (max >= 256 && max < 512) return 9; 
		else if (max >= 512 && max < 1024) return 10; 
		else if (max >= 1024 && max < 2048) return 11; 
		else if (max >= 2048 && max < 4096) return 12; 
		else if (max > 2048 && max < 8192) return 13; 
		else 
		{
			System.out.println("Only process upto the minterm less than 8191");
		}
		return -1;
	}
	
	private void printArray (String[] anArray)
	{
		for (int i = 0; i < anArray.length; i++)
		{
			System.out.print(anArray[i] + " ");
		}
		System.out.println();
	}

	private String[] toBinary(int[] min_array) {
		int a = 0;
		String[] return_minterms = new String[200];
		return_minterms = fill1DArray(return_minterms);

		while (min_array[a] != -1) {
			return_minterms[a] = toBinaryString(min_array[a]);
			a++;
		}
		return return_minterms;
	}

	private String completeMethods() {
		int a = 0, b = 0;
		String[][] final_pass = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];

		String[][] remaining_minterms = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		remaining_minterms = fillArrays(remaining_minterms);

		final_pass = fillArrays(final_pass);
		PRIME_CALC = fillArrays(PRIME_CALC);
		PASS_IGNORANCE = fill1DArray(PASS_IGNORANCE);
		SUB_MINTERMS = fill1DArray(SUB_MINTERMS);

		final_pass = fillMinterms(MINTERMS);		
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

		final_pass = fillMinterms(MINTERMS);

		for (int i = 0; i < final_pass.length; i++) {
			for (int j = 0; j < final_pass[i].length; j++) {
				if (final_pass[i][j] != "-1") {
					PRIME_CALC[b][0] = final_pass[i][j];
					b++;
				}
			}

		}
		PASS_IGNORANCE = fillDontCare(IGNORANCE);
		while (IS_COMPLETE != 'T') {
			final_pass = compareAdjacentMinterms(final_pass);
		}

		removeDuplicatePrimes();
		remaining_minterms = getEssentialPrimes();
		if (remaining_minterms[0][0] != "-1") {
			remaining_minterms = getPrimes(remaining_minterms);
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

	private String[][] fillMinterms(String[] input_min) {
		String temp_min;
		int a = 0, count;
		int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0, sevens = 0, eights = 0, nines = 0,
				tens = 0, elevens = 0, twelves = 0, thirteens = 0;
		String[][] group_wise = new String[100][100];
		fillArrays(group_wise);

		while (input_min[a] != "-1") {

			count = 0;
			temp_min = input_min[a];

			while (temp_min.length() < NUMBER_OF_VARIABLE) {
				temp_min = "0" + temp_min;
			}

			for (int i = 0; i < temp_min.length(); i++) {
				if (temp_min.charAt(i) == '1') {
					count++;
				}
			}

			if (count == 0) {
				group_wise[0][0] = temp_min;
			} else if (count == 1) {

				group_wise[1][ones] = temp_min;
				ones++;
			} else if (count == 2) {

				group_wise[2][twos] = temp_min;
				twos++;
			} else if (count == 3) {

				group_wise[3][threes] = temp_min;
				threes++;
			} else if (count == 4) {

				group_wise[4][fours] = temp_min;
				fours++;
			} else if (count == 5) {

				group_wise[5][fives] = temp_min;
				fives++;
			} else if (count == 6) {

				group_wise[6][sixes] = temp_min;
				sixes++;
			}

			else if (count == 7) {

				group_wise[7][sevens] = temp_min;
				sevens++;
			} else if (count == 8) {

				group_wise[8][eights] = temp_min;
				eights++;
			} else if (count == 9) {

				group_wise[9][nines] = temp_min;
				nines++;
			} else if (count == 10) {

				group_wise[10][tens] = temp_min;
				tens++;
			} else if (count == 11) {

				group_wise[11][elevens] = temp_min;
				elevens++;
			} else if (count == 12) {

				group_wise[12][twelves] = temp_min;
				twelves++;
			} else if (count == 13) {

				group_wise[12][thirteens] = temp_min;
				thirteens++;
			}

			a++;
		}

		return group_wise;
	}

	private String[] fillDontCare(String[] input_min) {
		String[] group_wise_dontcare = new String[ARRAY_MAGNITUDE];
		group_wise_dontcare = fill1DArray(group_wise_dontcare);
		String temp_min;
		int a = 0, b = 0;

		while (input_min[a] != "-1") {

			temp_min = input_min[a];

			while (temp_min.length() < NUMBER_OF_VARIABLE) {
				temp_min = "0" + temp_min;
			}

			group_wise_dontcare[b] = temp_min;
			b++;
			a++;
		}

		return group_wise_dontcare;
	}

	private String[][] compareAdjacentMinterms(String[][] group_wise) {
		String[][] all_combined = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		int count = 0, c = 0;
		int index = 0;
		int min_comp = 0, min_comp2 = 1, min_comp1 = 0, min_comp3 = 0;
		int index1 = 0, index2 = 0;

		for (int i = 0; i < ARRAY_MAGNITUDE; i++) {
			for (int j = 0; j < ARRAY_MAGNITUDE; j++) {
				all_combined[i][j] = "-1";
			}
		}
		while (c < group_wise.length) {
			if (group_wise[c][0] != "-1" && group_wise[c + 1][0] != "-1")
				count++;
			c++;
		}

		if (count >= 1) {
			count = 0;
			while (min_comp < group_wise.length - 1) {

				while (group_wise[min_comp][min_comp1] != "-1") {

					while (group_wise[min_comp2][min_comp3] != "-1") {

						for (int ij = 0; ij < NUMBER_OF_VARIABLE; ij++) {

							if (group_wise[min_comp][min_comp1].charAt(ij) != group_wise[min_comp2][min_comp3]
									.charAt(ij)) {
								count++;
								index = ij;
							}
						}

						if (count == 1) {

							all_combined[index1][index2] = group_wise[min_comp2][min_comp3].substring(0, index) + 'x'
									+ group_wise[min_comp2][min_comp3].substring(index + 1, NUMBER_OF_VARIABLE);

							primeFill(all_combined[index1][index2], group_wise[min_comp][min_comp1],
									group_wise[min_comp2][min_comp3]);
							index2++;

						}

						count = 0;
						min_comp3++;

					}

					min_comp3 = 0;

					min_comp1++;

				}

				min_comp++;
				min_comp2++;
				min_comp1 = 0;

				index1++;

				index2 = 0;
				min_comp3 = 0;

			}
			IS_FIRST_PRIME = 'F';
		}

		else
			IS_COMPLETE = 'T';
		return all_combined;
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
		int prime_calc_index = 0, current_index = 1, check_index = 2, temp_check_index = 0;

		while (PRIME_CALC[prime_calc_index][0] != "-1") {
			while (PRIME_CALC[prime_calc_index][current_index] != "-1") {
				check_index = current_index + 1;
				while (PRIME_CALC[prime_calc_index][check_index] != "-1") {
					temp_check_index = check_index;

					if (PRIME_CALC[prime_calc_index][current_index].equals(PRIME_CALC[prime_calc_index][check_index])) {

						while (PRIME_CALC[prime_calc_index][temp_check_index] != "-1") {
							PRIME_CALC[prime_calc_index][temp_check_index] = PRIME_CALC[prime_calc_index][temp_check_index
									+ 1];
							temp_check_index++;
						}

						PRIME_CALC[prime_calc_index][temp_check_index] = "-1";
						check_index--;
					}

					check_index++;
				}
				current_index++;
			}

			current_index = 1;
			check_index = 2;
			prime_calc_index++;
		}

	}

	private String[][] getEssentialPrimes() {
		int i = 0, j = 0;
		int a = 0, b = 0;
		SUB_MIN_INDEX = 0;
		String[] temp_back = new String[ARRAY_MAGNITUDE];
		temp_back = fill1DArray(temp_back);

		String[][] temp_prime_calc = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		temp_prime_calc = fillArrays(temp_prime_calc);

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
					temp_prime_calc[c][d] = PRIME_CALC[a][b];
					b++;
					d++;

				}
				c++;
			}
			a++;

			b = 0;
			d = 0;
		}

		return temp_prime_calc;

	}

	private String[][] getPrimes(String[][] temp_prime_calc) {
		int a = 0, b = 1, c = 0, d = 0, i = 0;
		int count = 0;
		String max_minterm;
		int max_index = 0;
		String[] primes = new String[ARRAY_MAGNITUDE];
		primes = fill1DArray(primes);

		int[] prime_count = new int[ARRAY_MAGNITUDE];
		prime_count = fill1DIntArray(prime_count);

		String[][] temp_prime_calc2 = new String[ARRAY_MAGNITUDE][ARRAY_MAGNITUDE];
		temp_prime_calc2 = fillArrays(temp_prime_calc2);

		while (temp_prime_calc[a][0] != "-1") {
			while (temp_prime_calc[a][b] != "-1") {
				primes[i] = temp_prime_calc[a][b];
				b++;
				i++;
			}
			a++;
		}

		a = 0;
		i = 0;
		while (primes[i] != "-1") {
			while (temp_prime_calc[a][0] != "-1") {
				while (temp_prime_calc[a][b] != "-1") {
					if (primes[i] == temp_prime_calc[a][b]) {
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
			prime_count[c] = count;
			count = 0;
			c++;
		}

		count = 0;
		c = 0;
		while (prime_count[c] != -1) {
			if (prime_count[c] > count) {
				count = prime_count[c];
				max_index = c;
			}
			c++;
		}
		a = 0;
		b = 1;
		max_minterm = primes[max_index];
		if (max_minterm != "-1") {
			SUB_MINTERMS[SUB_MIN_INDEX] = max_minterm;
			SUB_MIN_INDEX++;
		}

		while (temp_prime_calc[a][0] != "-1") {
			while (temp_prime_calc[a][b] != "-1") {
				if (temp_prime_calc[a][b].equals(max_minterm)) {
					temp_prime_calc[a][0] = "Y";
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
				if (PRIME_CALC[a][b].equals(max_minterm)) {
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
		while (temp_prime_calc[a][0] != "-1") {
			if (temp_prime_calc[a][0] != "Y") {
				while (temp_prime_calc[a][b] != "-1") {
					temp_prime_calc2[c][d] = temp_prime_calc[a][b];
					b++;
					d++;

				}
				c++;
			}
			a++;

			b = 0;
			d = 0;
		}

		return temp_prime_calc2;

	}

	private String giveOutput(String[] output_array) {
		int count = 0;
		String[] minimized_array = new String[ARRAY_MAGNITUDE];
		fill1DArray(minimized_array);
		int minimized_index = 0;
		String output_string = "";
		String temp_minterm = "";
		String temp_minterm_check = "";
		int i = 0;

		while (output_array[i] != "-1") {
			for (int ij = NUMBER_OF_VARIABLE - 1; ij >= 0; ij--) {
				if (output_array[i].charAt(ij) != 'x') {
					if (output_array[i].charAt(ij) == '0') {						
						temp_minterm_check = VARIABLE_NAME[ij] + "'";
					}
					if (output_array[i].charAt(ij) == '1') {						
						temp_minterm_check = VARIABLE_NAME[ij];
					}
					temp_minterm = temp_minterm + temp_minterm_check;

				}
			}

			i++;

			for (int j = 0; j < minimized_array.length; j++) {

				if (minimized_array[j].equals(temp_minterm)) {
					count++;
				}
			}

			if (count == 0) {
				minimized_array[minimized_index] = temp_minterm;
				minimized_index++;
			}
			temp_minterm = "";
			count = 0;

		}
		i = 0;

		minimized_index = 0;
		while (minimized_array[minimized_index] != "-1") {
			output_string = output_string + "+" + minimized_array[minimized_index];
			minimized_index++;
		}
		output_string = output_string.substring(1, output_string.length());
		return output_string;
	}

	/*
	private String[][] fillDontCares(String[][] filling_array) {
		int final_pass_dontcare_index = 0;
		while (PASS_IGNORANCE[final_pass_dontcare_index] != "-1") {
			for (int i = 0; i < filling_array.length; i++) {
				for (int j = 0; j < filling_array[i].length; j++) {
					if (PASS_IGNORANCE[final_pass_dontcare_index].equals(filling_array[i][j])) {
						filling_array[i][j] = "-1";

					}
				}
			}
			final_pass_dontcare_index++;
		}
		return filling_array;
	}
	*/

	private String[][] fillArrays(String[][] all_combined) {
		for (int i = 0; i < all_combined.length; i++) {
			for (int j = 0; j < all_combined[i].length; j++) {
				all_combined[i][j] = "-1";
			}
		}

		return all_combined;
	}

	/*
	private int[][] fillIntArrays(int[][] all_combined) {
		for (int i = 0; i < all_combined.length; i++) {
			for (int j = 0; j < all_combined[i].length; j++) {
				all_combined[i][j] = -1;
			}
		}

		return all_combined;
	}
	*/

	private String[] fill1DArray(String[] filling_array) {
		for (int i = 0; i < filling_array.length; i++) {
			filling_array[i] = "-1";
		}
		return filling_array;
	}

	private int[] fill1DIntArray(int[] temp_array) {

		for (int i = 0; i < temp_array.length; i++) {
			temp_array[i] = -1;
		}

		return temp_array;
	}

	private String toBinaryString(int aValue) {
		int quotient = aValue, remainder;
		String result = "";
		
		if (aValue == 0)
		{
			while (result.length() < VARIABLE)
			{
				result += "0";
			}
			return result;
		}
		
		if (aValue == 1)
		{
			result = "1";
			while (result.length() < VARIABLE)
			{
				result = "0" + result;
			}
			return result;
		}
			
		while (quotient != 0 && quotient != 1) {

			remainder = quotient % 2;
			quotient = quotient / 2;
			result = Integer.toString(remainder) + result;
		}
		
		return result;
	}

}
