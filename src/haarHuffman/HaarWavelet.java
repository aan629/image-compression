package haarHuffman;

import java.util.ArrayList;

public class HaarWavelet {
	//Pointers Important for the whole structure
	public static int nInteractions;
	public static int[][] finalArray;
	
	//Pointers for internal use
	private static float[][] haarwavelado;
	
	public HaarWavelet(int[][] compressedData, int interactions) {
		//Rollback all process to the original form
		nInteractions = interactions;
		//in the end, leave the array on the universal pointer and only fish afterwards.
		finalArray = convertFloatToInt2D(revertHaarWaveletTransforms(convertIntToFloat2D(compressedData)));
	}
	
	public HaarWavelet(float[][] rawData) {
		//Transformation based on optimization
		finalArray = convertFloatToInt2D(optimizeHaarWaveletTransforms(rawData));
	}
	
	public HaarWavelet(float[][] rawData, String nInteractionsManipulado) {
		//Transformation based on optimization
		float[][] lol;
		lol = new float[rawData.length][rawData.length];
		haarwavelado = rawData;
		ArrayList<float[][]> x = new ArrayList<float[][]>();
		int i=0;
		while (i < Integer.parseInt(nInteractionsManipulado)) {
			lol = returnItFlat(haarwavelado, i);
			//System.out.println(haarwavelado.length);
			i++;
			if (i>0)
				x.add(lol);
		}
		nInteractions = i;
		//See how to join in a recursive way
				if (x.size() > 0) {
					float[][] finalize = x.get(0);
					if (x.size() > 1) {
						for (int w=0; w<x.size(); w++) {
							for (int e = 0; e < x.get(w).length; e++) {
								for (int r = 0; r < x.get(w)[e].length; r++) {
									finalize[e][r] = x.get(w)[e][r];
								}
							}			
						}
						finalArray = convertFloatToInt2D(finalize);	
					}
					else
						finalArray = convertFloatToInt2D(finalize);
				}
				else {
					finalArray = convertFloatToInt2D(lol);	
				}
		
	}
	
	/*
	public static void main(String[] args) {
		//by Given 2D Matrix
		/*
		float rawData[][]={
			    {1,2,3,4},
			    {5,6,7,8},
			    {9,10,11,12},
			    {13,14,15,16}
			};
		*/
		/*
		float rawData[][]={
			    {1,2,3,4,5,6},
			    {9,10,11,12,13,14},
			    {17,18,19,20,21,22},
			    {25,26,27,28,29,30},
			    {33,34,35,36,37,38},
			    {41,42,43,44,45,46}
			};
		*/
		/*
		float rawData[][]={
			    {1,2,3,4,5,6,7,8},
			    {9,10,11,12,13,14,15,16},
			    {17,18,19,20,21,22,23,24},
			    {25,26,27,28,29,30,31,32},
			    {33,34,35,36,37,38,39,40},
			    {41,42,43,44,45,46,4,48},
			    {49,50,51,52,53,54,55,56},
			    {57,58,59,60,61,62,63,64}
			};
		*/
		/*
		float rawData[][]={
			    {78, 74, 68, 58, 48, 39, 31, 24},
			    {77, 75, 70, 62, 57, 49, 44, 38},
			    {71, 72, 70, 67, 65, 61, 63, 58},
			    {63, 65, 69, 70, 75, 81, 87, 84},
			    {54, 60, 67, 75, 90, 109, 117, 122},
			    {43, 52, 63, 83, 109, 138, 161, 169},
			    {35, 48, 64, 88, 117, 161, 196, 212},
			    {27, 40, 58, 85, 122, 169, 212, 232}
			};
		*/
		/*
		float rawData[][]={
			    {1,2,   3,4,    5,6,7,8,     9,10,11,12,13,14,15,16},
			    {17,18, 19,20,  21,22,23,24, 25,26,27,28,29,30,31,32},
			    
			    {33,34, 35,36,  37,38,39,40, 41,42,43,44,45,46,47,48},
			    {49,50, 51,52,  53,54,55,56, 57,58,59,60,61,62,63,64},
			    
			    {65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80},
			    {81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96},
			    {97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112},
			    {113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128},
			    {129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144},
			    {145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160},
			    {161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176},
			    {177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192},
			    {193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208},
			    {209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224},
			    {225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240},
			    {241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256}
			};
		*/
		//Make complete transformation method
		/*
		rawData = inversion(transformation(rawData));
		System.out.println(Arrays.deepToString(rawData));
		//It Works.
		*/ 
		/*
		float[][] x = optimizeHaarWaveletTransforms(rawData);
		System.out.println(Arrays.deepToString(x));
		System.out.println("Numero de Iteractions: "+nInteractions);
		
		//Inverse part
		float[][] fx = revertHaarWaveletTransforms(x);
		System.out.println();
		System.out.println(Arrays.deepToString(fx));
		
	}
	*/
	
	private static float[][] horizontalTransformation(float[][] rawData) {
		float firstBlock[][] = new float[rawData.length][rawData.length];
		int l;
		l=0;
		for (int i = 0; i < rawData.length; i++) {
			//Addition
			for (int j = 0; j < rawData[i].length/2; j++) {
				firstBlock[i][j] = (rawData[i][l] + rawData[i][l+1])/2;
				l++;l++;
			}
			l=0;
			//Subtraction
			for (int j = rawData[i].length/2; j < rawData[i].length; j++) {
				//System.out.println("O Erro: "+rawData.length + " linha: "+i+" e coluna: "+l);
				firstBlock[i][j] = (rawData[i][l] - rawData[i][l+1])/2;
				l++;l++;
			}
			l=0;
		}
		return firstBlock;
	}
	
	private static float[][] verticalTransformation(float[][] rawData) {
		float firstBlock[][] = new float[rawData.length][rawData.length];
		int l;
		l=0;
		for (int i = 0; i < rawData.length; i++) {
			//Addition
			for (int j = 0; j < rawData[i].length/2; j++) {
				firstBlock[j][i] = (rawData[l][i] + rawData[l+1][i])/2;
				l++;l++;
			}
			l=0;
			//Subtraction
			for (int j = rawData[i].length/2; j < rawData[i].length; j++) {
				firstBlock[j][i] = (rawData[l][i] - rawData[l+1][i])/2;
				l++;l++;
			}
			l=0;
		}
		return firstBlock;
	}
	
	private static float[][] horizontalInversion(float[][] rawData) {
		float firstBlock[][] = new float[rawData.length][rawData.length];
		int l;
		l=0;
		for (int i = 0; i < rawData.length; i++) {
			for (int j = 0; j < rawData[i].length; j++) {
				if (isOdd(j)) {
					//Odd = Subtraction
					firstBlock[i][j] = rawData[i][l] - rawData[i][l+rawData.length/2];
					l++;
				}
				else {
					//Even = Addition
					firstBlock[i][j] = rawData[i][l] + rawData[i][l+rawData.length/2];
				}
				
			}
			l=0;
		}
		return firstBlock;		
	}
	
	private static float[][] verticalInversion(float[][] rawData) {
		float firstBlock[][] = new float[rawData.length][rawData.length];
		int l;
		l=0;
		for (int i = 0; i < rawData.length; i++) {
			for (int j = 0; j < rawData[i].length; j++) {
				if (isOdd(j)) {
					//Odd = Subtraction
					firstBlock[j][i] = rawData[l][i] - rawData[l+(rawData.length/2)][i];
					l++;
				}
				else {
					//Even = Addition
					firstBlock[j][i] = rawData[l][i] + rawData[l+(rawData.length/2)][i];
				}	
			}
			l=0;
		}
		return firstBlock;		
	}
	
	private static float[][] transformation(float[][] rawData) {
		//What is the junction of horizontal and vertical transformation
		//return horizontalTransformation(rawData);
		return verticalTransformation(horizontalTransformation(rawData));
	}
	
	private static float[][] inversion(float[][] rawData) {
		//If the transformation began with horizontal and ended with vertical, the reverse is even vertical and then horizontal.
		return horizontalInversion(verticalInversion(rawData));
	}
	
	private static boolean isOdd(int number) {
		if (number % 2 == 0) {
			  // even
			return false;
			} else {
			  // odd
				return true;
			}
	}
	/*
	private static ArrayList<Float> arrayToArrayList(float[][]lol) {
		
	}
	*/
	private static float[][] returnItFlat(float rawData[][], int lengthOriginal) {
		
		if (lengthOriginal < countGCDNumber(rawData.length, 0)) {
			
			if (lengthOriginal == 0) {
				haarwavelado = transformation(rawData);
				/*
				System.out.println("First Level of Transformation:");
				System.out.println(Arrays.deepToString(haarwavelado));
				*/
				return haarwavelado;
			}
			else {
		
		float firstBlock[][] = new float[rawData.length/2][rawData.length/2];
		float secondBlock[][] = new float[rawData.length/2][rawData.length/2];
		float thirdBlock[][] = new float[rawData.length/2][rawData.length/2];
		float fourthBlock[][] = new float[rawData.length/2][rawData.length/2];
		
		int k, l;
		//Retrieve the first block of data
		k=0;
		l=0;
		for (int i = 0; i < rawData.length/2; i++) {
			for (int j = 0; j < rawData[i].length/2; j++) {
				firstBlock[k][l] = rawData[i][j];
				l++;
			}
			k++;
			l=0;
		}
		
		
		//Retrieve the second block of data
		k=0;
		l=0;
		for (int i = 0; i < rawData.length/2; i++) {
			for (int j = rawData.length/2; j < rawData[i].length; j++) {
				secondBlock[k][l] = rawData[i][j];
				l++;
			}
			k++;
			l=0;
		}
		
		
		//Retrieve the third block of data
		//
		k=0;
		l=0;
		for (int i = rawData.length/2; i < rawData.length; i++) {
			for (int j = 0; j < rawData[i].length/2; j++) {
				thirdBlock[k][l] = rawData[i][j];
				l++;
			}
			k++;
			l=0;
		}
		
		
		//Retrieve the fourth block of data
		k=0;
		l=0;
		for (int i = rawData.length/2; i < rawData.length; i++) {
			for (int j = rawData.length/2; j < rawData[i].length; j++) {
				fourthBlock[k][l] = rawData[i][j];
				l++;
			}
			k++;
			l=0;
		}
		
		//Transforms the first block only
		/*
		System.out.println("First Block before Transformation");
		System.out.println(Arrays.deepToString(firstBlock));
		*/
		firstBlock = transformation(firstBlock);
		/*
		System.out.println("First Block after Transformation");
		System.out.println(Arrays.deepToString(firstBlock));
		
		System.out.println("Second Block after Transformation");
		System.out.println(Arrays.deepToString(secondBlock));
		
		System.out.println("Third Block after Transformation");
		System.out.println(Arrays.deepToString(thirdBlock));
		
		System.out.println("Fourth Block after Transformation");
		System.out.println(Arrays.deepToString(fourthBlock));
		*/
		
		//Create new array with new block and add to old one
		float newArray[][] = new float[rawData.length][rawData.length];
		//Retrieve the first block of data
		k=0;
		l=0;
		for (int i = 0; i < firstBlock.length; i++) {
			for (int j = 0; j < firstBlock[i].length; j++) {
				newArray[i][j] = firstBlock[k][l];
				l++;
			}
			k++;
			l=0;
		}
		
		
		//Retrieve the second block of data
		k=0;
		l=0;
		for (int i = 0; i < secondBlock.length; i++) {
			for (int j = firstBlock[i].length; j < firstBlock[i].length+secondBlock[i].length; j++) {
				newArray[i][j] = secondBlock[k][l];
				l++;
			}
			k++;
			l=0;
		}
		
		
		//Retrieve the third block of data
		//
		k=0;
		l=0;
		for (int i = firstBlock.length; i < firstBlock.length+thirdBlock.length; i++) {
			for (int j = 0; j < thirdBlock[k].length; j++) {
				newArray[i][j] = thirdBlock[k][l];
				l++;
			}
			k++;
			l=0;
		}
		
		
		//Retrieve the fourth block of data
		k=0;
		l=0;
		for (int i = firstBlock.length; i < firstBlock.length+fourthBlock.length; i++) {
			for (int j = firstBlock[k].length; j < firstBlock[k].length+fourthBlock[k].length; j++) {
				newArray[i][j] = fourthBlock[k][l];
				l++;
			}
			k++;
			l=0;
		}
		haarwavelado = firstBlock;
		//returns everything.
		return newArray;
			}
		}
		else
			return rawData;
	}
	
	private static float[][] optimizeHaarWaveletTransforms(float[][] rawData) {
		float[][] lol;
		ArrayList<float[][]> x = new ArrayList<float[][]>();
		if (isOdd(rawData.length) && isOdd(rawData[0].length)) {
			lol = new float[rawData.length-1][rawData.length-1];
			float[][] tmpx = new float[rawData.length-1][rawData.length-1];
			for (int i = 0; i < rawData.length-1; i++) {
				for (int j = 0; j < rawData[0].length-1; j++) {
					tmpx[i][j] = rawData[i][j];
					haarwavelado = tmpx;
				}
			}
		}
		else {
			if (isOdd(rawData.length)) {
				lol = new float[rawData.length-1][rawData[0].length];
				float[][] tmpx = new float[rawData.length-1][rawData[0].length];
				for (int i = 0; i < rawData.length-1; i++) {
					for (int j = 0; j < rawData[0].length; j++) {
						tmpx[i][j] = rawData[i][j];
						haarwavelado = tmpx;
					}
				}
			}
			else {
				if (isOdd(rawData[0].length)) {
					lol = new float[rawData.length][rawData[0].length-1];
					float[][] tmpx = new float[rawData.length][rawData[0].length-1];
					for (int i = 0; i < rawData.length; i++) {
						for (int j = 0; j < rawData[0].length-1; j++) {
							tmpx[i][j] = rawData[i][j];
							haarwavelado = tmpx;
						}
					}
				}
				else {
					//Is normal
					lol = new float[rawData.length][rawData.length];
					haarwavelado = rawData;
				}
			}
		}
		
		int i=0;
		//Bug is not supposed to last captured GCD, it fails. I jumped to the control for number of iterations.
		//while (haarwavelado.length > GCD(rawData.length)) {
		while (i < countGCDNumber(rawData.length, 0)) {
			lol = returnItFlat(haarwavelado, i);
			//System.out.println(haarwavelado.length);
			i++;
			//System.out.println(i+" "+countGCDNumber(rawData.length, 0));
			if (i>0)
				x.add(lol);
		}
		nInteractions = i;
		
				//Try to print all in
				/*
				System.out.println("Printing array in "+haarwavelado.length);
				for (int q=0; q<x.size(); q++)
					System.out.println(Arrays.deepToString(x.get(q)));
				System.out.println();
				*/
				//See how to join in a recursive way
				if (x.size() > 0) {
					float[][] finalize = x.get(0);
					if (x.size() > 1) {
						for (int w=0; w<x.size(); w++) {
							for (int e = 0; e < x.get(w).length; e++) {
								for (int r = 0; r < x.get(w)[e].length; r++) {
									finalize[e][r] = x.get(w)[e][r];
								}
							}			
						}
						return finalize;	
					}
					else
						return finalize;
				}
				else {
					return lol;	
				}
	}

private static float[][] revertHaarWaveletTransforms(float rawData[][]) {			
			if (nInteractions == 1) {
				haarwavelado = inversion(rawData);
				return haarwavelado;
			}
			else {
				//We will need to climb the mountain on the basis of interactions, only to get the firstblocks all together. neh.
				ArrayList<float[][]> z = new ArrayList<float[][]>();
				z.add(rawData);
				haarwavelado = rawData;
				for (int t=1; t<nInteractions; t++) {
					float firstBlock[][] = new float[haarwavelado.length/2][haarwavelado.length/2];
					int k, l;
					//Retrieve only first block of data
					k=0;
					l=0;
					for (int i = 0; i < haarwavelado.length/2; i++) {
						for (int j = 0; j < haarwavelado[i].length/2; j++) {
							firstBlock[k][l] = haarwavelado[i][j];
							l++;
						}
						k++;
						l=0;
					}
					haarwavelado = firstBlock;
					z.add(firstBlock);
				}
				/*
				//Try to print all in
				System.out.println("Printing inverse in "+haarwavelado.length);
				for (int q=0; q<z.size(); q++)
					System.out.println(Arrays.deepToString(z.get(q)));
				System.out.println();
				
				//Take the last one, make it inverse. Join the previous one, inverse. to 0, inverse. return.
				System.out.println("Length do ArrayList: "+z.size());
				*/
				for (int o=z.size()-1; o>=0; o--) {
					float[][] tmp = new float[z.get(o).length][z.get(o)[0].length];
					tmp = inversion(z.get(o));
					if (o-1 >= 0) {
					//Missing the previous
					float[][] tmpx = new float[z.get(o-1).length][z.get(o-1)[0].length];
					for (int e = 0; e < z.get(o-1).length; e++) {
						for (int r = 0; r < z.get(o-1)[e].length; r++) {
							tmpx[e][r] = z.get(o-1)[e][r];
						}
					}
					for (int e = 0; e < tmp.length; e++) {
						for (int r = 0; r < tmp[e].length; r++) {
							tmpx[e][r] = tmp[e][r];
						}
					}
					z.set(o-1, tmpx);
					}
					else {
						z.set(o, tmp);
					}
				}
				
				return z.get(0);
			}
	}
	/*
    private static int GCDNumber(int a) {
    	if (a == 2) return a;
    	if (a == 1) return a;
    	if (a%2 != 0) return a;
    	else return GCD(a/2);
    }
    */    
    private static int countGCDNumber(int a, int cntGCD) {
    	if (a == 1) {
    		return cntGCD-1;
    	}
    		
    	if (a%2 != 0) {
    		return cntGCD-1;
    	}
    	else {
    		cntGCD++;
    		return countGCDNumber(a/2, cntGCD);
    	}
    }
    
    private static int[][] convertFloatToInt2D(float[][] rawData) {
    	int[][] finalx = new int[rawData.length][rawData.length];
		for (int i = 0; i < rawData.length-1; i++) {
			for (int j = 0; j < rawData[0].length-1; j++) {
				finalx[i][j] = (int) rawData[i][j];
			}
		}
    	return finalx;
    }
    
    private static float[][] convertIntToFloat2D(int[][] rawData) {
    	float[][] finalx = new float[rawData.length][rawData.length];
		for (int i = 0; i < rawData.length-1; i++) {
			for (int j = 0; j < rawData[0].length-1; j++) {
				finalx[i][j] = (float) rawData[i][j];
			}
		}
    	return finalx;
    }
}