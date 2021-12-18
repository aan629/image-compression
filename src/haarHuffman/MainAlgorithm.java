package haarHuffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.*;
import java.nio.file.Files;
import java.text.DecimalFormat;

import java.awt.image.*;

import javax.imageio.ImageIO;

public class MainAlgorithm {

	float toBeDivided[][];

	public MainAlgorithm(String imageDir, String fileDir, String encodingDummy) {
		System.out.println("Encoding Process Started!!");
		long startTime = System.nanoTime();
		// 1. Open an image & Read content of image
		BufferedImage image;
		try {
			
			//System.out.println("Starting to read image");
			image = ImageIO.read(new File(imageDir));
			//System.out.println("Finishing to read image");
			
			//System.out.println(image.getHeight());

			// 2. Convert Buffered image to 2D Array
			//System.out.println("Starting 2D Array Conversion");
			float[][] rawData = compute(image); // some code to read raw bytes from image file
			//System.out.println("Finishing 2D Array Conversion");
			
			//System.out.println(rawData.length+" "+rawData[0].length);
			//System.out.println("Before:");
			//System.out.println(Arrays.deepToString(rawData));
			
			/*
			  float rawData[][]={ {78, 74, 68, 58, 48, 39, 31, 24}, {77, 75, 70, 62, 57,
			  49, 44, 38}, {71, 72, 70, 67, 65, 61, 63, 58}, {63, 65, 69, 70, 75, 81, 87,
			  84}, {54, 60, 67, 75, 90, 109, 117, 122}, {43, 52, 63, 83, 109, 138, 161,
			  169}, {35, 48, 64, 88, 117, 161, 196, 212}, {27, 40, 58, 85, 122, 169, 212,
			  232} };
			*/

			// 3. Implement Haar Wavelet Transform (optimizedly)
			//System.out.println("Starting Haar Wavelet");
			HaarWavelet transform = new HaarWavelet(rawData);
			//System.out.println("Finished Haar Wavelet");
			// System.out.println(transform.nInteractions);
			//System.out.println("Haar Wavelet: ");
			//System.out.println(Arrays.deepToString(transform.finalArray));

			// 3.5. Convert the 2D array into 1D array
			//System.out.println("Starting 1D Array Conversion");
			//ArrayList<Float> inLine = return2DInLine(rawData);
			ArrayList<Integer> inLine = return2DInLine(transform.finalArray);
			//System.out.println("Finishing 1D Array Conversion");
			/*
			//Save it for test purposes - Before
			FileWriter f1 = new FileWriter(new File(fileDir+"a"));
			f1.write(return2DInLine(rawData).toString());
			f1.close();
			
			//Save it for test purposes - After
			FileWriter f2 = new FileWriter(new File(fileDir+"b"));
			f2.write(return2DInLine(transform.finalArray).toString());
			f2.close();
			 */
			//System.out.println(transform.nInteractions);
			//System.out.println(return2DInLine(transform.finalArray).toString());

			// 4. Implement Huffman Encoding
			
			//System.out.println("Starting Huffman Encoding");
			HuffmanEncode hEncode = new HuffmanEncode(inLine.toString()+";"+transform.nInteractions);
			//System.out.println("Finishing Huffman Encoding");
			// 5. Generate the Output
			/*
			 * Save Huffman
			 * 
			 */
			
			
			//System.out.println("Starting saving process");
			/*
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(fileDir)));
			outputStream.writeObject(compress(generateOutput(hEncode)));
			
			for(String str: hEncode.test) {
				outputStream.writeObject(compress(str));
			}
			outputStream.close();
			*/
			
			//converting from byte to String    
			//System.out.println(Integer.toBinaryString((b+256)%256));
			/*
			FileWriter f0 = new FileWriter(new File(fileDir/*+"t"*//*));
			f0.write(generateOutput(hEncode));
			for(String str: hEncode.test) {
				f0.write((byte)(int)Integer.valueOf(str, 2));
			}
			f0.close();
			*/
			
			FileWriter f0 = new FileWriter(new File(fileDir+"t"));
			f0.write(generateOutput(hEncode));
			for(String str: hEncode.test) {
				f0.write(str);
			}
			f0.close();
			
	        GzipJava.compressGZIP(new File(fileDir+"t"), new File(fileDir));
	        
	        //Remove the t file... (optional)
	        Files.deleteIfExists(new File(fileDir+"t").toPath());
			
			System.out.println("Encoding Process Finished!!");
			final double seconds = ((double) (System.nanoTime() - startTime) / 1000000000);
			System.out.println("Time Elapsed: " + new DecimalFormat("#.##########").format(seconds) + "s.");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MainAlgorithm(String encodedFileDir, String fileImageOutput) {
		System.out.println("Decoding Process Started!!");
		long startTime = System.nanoTime();
		// 6. Read the Output
		//BufferedReader fr = null;
	
		try {
			GzipJava.decompressGzip(new File(encodedFileDir), new File(encodedFileDir+"t"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scanner fr = null;
		try {
			//fr = new BufferedReader(new FileReader(new File(encodedFileDir)));
			fr = new Scanner(new File(encodedFileDir+"t"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
        //Remove the t file... (optional)
        try {
			Files.deleteIfExists(new File(encodedFileDir+"t").toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 7. Implement Huffman Decoding
		ArrayList<String> huffDecoded = huffmanDecode(fr);

		// 7.4. Separate the values from the Decoding
		//oneDString = !huffDecoded.get( [0-huffDecoded.size()-1[ );
		//nInteractionX = huffDecoded.get(huffDecoded.size()-1);
		
		// 7.8. Convert the 1D array into 2D array
		int[][] loool = returnInLine2D(huffDecoded);
		
		//System.out.println("After 1D to 2D:");
		//System.out.println(Arrays.deepToString(loool));
		//System.out.println(loool.length);

		// 8. Implement Haar Wavelet Inversion
		HaarWavelet inversion = new HaarWavelet(loool, Integer.parseInt(huffDecoded.get(huffDecoded.size()-1)));
		
		 //System.out.println("Haar Wavelet after Inverse: ");
		 //System.out.println(Arrays.deepToString(inversion.finalArray));
		 
		// 9. Save the Output Image. Finish
		saveImage(inversion.finalArray, fileImageOutput);
		System.out.println("Decoding Process Finished!!");
		final double seconds = ((double) (System.nanoTime() - startTime) / 1000000000);
		System.out.println("Time Elapsed: " + new DecimalFormat("#.##########").format(seconds) + "s.");
		
	}

	/*
	 * private void fixMatrixForHaarWavelet(float[][] rawData) {
	 * 
	 * 
	 * // 1. Find the max length between X and Y; // 2. Compare if max(X,Y) is 2^Z
	 * length, and if not get the next 2^Z length; // 3. Create matrix by 2^Z
	 * length; // 3.1. Create a new array with the 2^Z length and add the original
	 * matrix; // 3.2. Add the original matrix inside the new array; // 3.3. Fill
	 * the remaining based on an answer; // 4. We will use this new array to
	 * transform until 2; // 5. Convert the result haar wavelet transformations and
	 * convert the 2D array into a 1D array; // 6. Encode using Huffman and output
	 * to a file // 7. Decode the Huffman by reading the file // 8. Convert the 1D
	 * array result into a 2D array // 9. Get the original haar wavelet
	 * transformation based on // 10. Get the original X and Y, delete the remaining
	 * part, and then output the original file.
	 * 
	 * 
	 * //1. Find X length and Y length int x = rawData.length; int y =
	 * rawData[0].length;
	 * 
	 * //2 Compare which is larger if (x > y) System.out.println("X is larger");
	 * else if (x < y) System.out.println("Y is larger"); else
	 * System.out.println("They are equal");
	 * 
	 * //3. Get the maximum value and get the 2^Z power
	 * 
	 * System.out.println(Math.max(x, y)); int k=0; for (int i=1;
	 * Math.pow(2,i)<Math.max(x, y); i++) { k=i; } if (Math.pow(2,k) < Math.max(x,
	 * y)) k++;
	 * 
	 * System.out.println("Next 2^Z: "+Math.pow(2,k));
	 * 
	 * if (x == y && y == k)
	 * System.out.println("Nothing to be done. Ready for Haar Wavelet."); else {
	 * System.out.println("Gonna transform into a 2^Z haar wavelet matrix"); //4.
	 * Create new matrix based on 2^Z power float[][] lol = new float[k][k]; /* //5.
	 * add the original content plus the other part of the missing content...
	 * 
	 * //5.1. from 0,0 to X,Y for (int i = 0; i < rawData.length; i++) { for (int j
	 * = 0; j < rawData[i].length; j++) { lol[i][j] = rawData[i][j]; } }
	 * 
	 * //5.2. Validations for the parts that needs to be added: 1 int l, m; if (x <
	 * k) { l=0; m=0; for (int i = x; i < k; i++) { for (int j = 0; j < y; j++) {
	 * lol[i][j] = rawData[i][j]; m++; } l++; m=0; } }
	 * 
	 * //6. return matrix return lol;
	 * 
	 * } }
	 */

	private void saveImage(int[][] finalArray, String dir) {
		BufferedImage image = new BufferedImage(finalArray.length, finalArray[0].length, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < finalArray.length; x++) {
			for (int y = 0; y < finalArray[x].length; y++) {
				image.setRGB(x, y, (int) finalArray[x][y]);
			}
		}

		File ImageFile = new File(dir + ".png");
		try {
			ImageIO.write(image, "png", ImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Integer> return2DInLine(int[][] rawData) {
		ArrayList<Integer> ordenado = new ArrayList<Integer>();
		for (int i = 0; i < rawData.length; i++) {
			for (int j = 0; j < rawData[i].length; j++) {
				ordenado.add(rawData[i][j]);
			}
		}
		return ordenado;
	}

	private int[][] returnInLine2D(ArrayList<String> txt) {
		double size = Math.sqrt(txt.size()-1);
		// System.out.println("E o tamanho dele: "+size);
		int firstBlock[][] = new int[(int) size][(int) size];
		int k = 0;
		for (int i = 0; i < firstBlock.length; i++) {
			for (int j = 0; j < firstBlock[i].length; j++) {
				firstBlock[i][j] = Integer.parseInt(txt.get(k));
				k++;
			}
		}
		return firstBlock;
	}

	public static float[][] compute(BufferedImage img) {
		try {
			// BufferedImage img= ImageIO.read(file);
			int w = img.getData().getWidth(), h = img.getData().getHeight();
			float pixels[][] = new float[w][h];
			for(int i = 0; i < w; i++)
			    for(int j = 0; j < h; j++)
			    	pixels[i][j] = img.getRGB(i, j);

			return pixels;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<String> huffmanDecode(Scanner fr) {
		HuffmanDecode tree = new HuffmanDecode();
		String temp = fr.nextLine();
		// System.out.println(temp);

		if (temp.charAt(0) == ' ' && temp.charAt(1) == ' ') {
			tree.add(' ', temp.trim());
		} else
			tree.add(temp.charAt(0), temp.substring(1));
		while (fr.hasNext()) {
			temp = fr.nextLine();
			// System.out.println(temp);
			if (temp.equals("**")) {
				break;
			} else
				tree.add(temp.charAt(0), temp.substring(1));
		}
		ArrayList<String> test = new ArrayList<String>();
		test.add(fr.nextLine());
		//String codeword = fr.nextLine();
		return tree.getDecodedMessage(test);
	}

	/*
	 * public void main(String[] args) throws IOException {
	 * 
	 * //BufferedImage hugeImage = ImageIO.read(Main.class.getResource(
	 * "/home/user/Imagens/h&h/test2.tiff")); BufferedImage hugeImage =
	 * ImageIO.read(new File("/home/user/Imagens/h&h/test6.jpg"));
	 * //double[][] data = new double[][];
	 * System.out.println(hugeImage.getHeight());
	 * //hugeImage.getData().getPixels(0,0,bufImgs.getWidth(),bufImgs.getHeight(),
	 * data[i]); /* System.out.println("Testing convertTo2DUsingGetRGB:"); for (int
	 * i = 0; i < 10; i++) { long startTime = System.nanoTime(); int[][] result =
	 * convertTo2DUsingGetRGB(hugeImage); long endTime = System.nanoTime();
	 * System.out.println(String.format("%-2d: %s", (i + 1), toString(endTime -
	 * startTime))); }
	 * 
	 * System.out.println("");
	 * 
	 * System.out.println("Testing convertTo2DWithoutUsingGetRGB:"); for (int i = 0;
	 * i < 10; i++) { long startTime = System.nanoTime(); int[][] result =
	 * convertTo2DWithoutUsingGetRGB(hugeImage); long endTime = System.nanoTime();
	 * System.out.println(String.format("%-2d: %s", (i + 1), toString(endTime -
	 * startTime))); }
	 */
	/*
	 * int[][] result = convertTo2DWithoutUsingGetRGB(hugeImage);
	 * System.out.println(Arrays.deepToString(result));
	 * 
	 * 
	 * }
	 * 
	 * private int[][] convertTo2DUsingGetRGB(BufferedImage image) { int width =
	 * image.getWidth(); int height = image.getHeight(); int[][] result = new
	 * int[height][width];
	 * 
	 * for (int row = 0; row < height; row++) { for (int col = 0; col < width;
	 * col++) { result[row][col] = image.getRGB(col, row); } }
	 * 
	 * return result; }
	 * 
	 * private int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
	 * 
	 * final byte[] pixels = ((DataBufferByte)
	 * image.getRaster().getDataBuffer()).getData(); final int width =
	 * image.getWidth(); final int height = image.getHeight(); final boolean
	 * hasAlphaChannel = image.getAlphaRaster() != null;
	 * 
	 * int[][] result = new int[height][width]; if (hasAlphaChannel) { final int
	 * pixelLength = 4; for (int pixel = 0, row = 0, col = 0; pixel < pixels.length;
	 * pixel += pixelLength) { int argb = 0; argb += (((int) pixels[pixel] & 0xff)
	 * << 24); // alpha argb += ((int) pixels[pixel + 1] & 0xff); // blue argb +=
	 * (((int) pixels[pixel + 2] & 0xff) << 8); // green argb += (((int)
	 * pixels[pixel + 3] & 0xff) << 16); // red result[row][col] = argb; col++; if
	 * (col == width) { col = 0; row++; } } } else { final int pixelLength = 3; for
	 * (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel +=
	 * pixelLength) { int argb = 0; argb += -16777216; // 255 alpha argb += ((int)
	 * pixels[pixel] & 0xff); // blue argb += (((int) pixels[pixel + 1] & 0xff) <<
	 * 8); // green argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
	 * result[row][col] = argb; col++; if (col == width) { col = 0; row++; } } }
	 * 
	 * return result; }
	 * 
	 * private String toString(long nanoSecs) { int minutes = (int) (nanoSecs /
	 * 60000000000.0); int seconds = (int) (nanoSecs / 1000000000.0) - (minutes *
	 * 60); int millisecs = (int) ( ((nanoSecs / 1000000000.0) - (seconds + minutes
	 * * 60)) * 1000);
	 * 
	 * 
	 * if (minutes == 0 && seconds == 0) return millisecs + "ms"; else if (minutes
	 * == 0 && millisecs == 0) return seconds + "s"; else if (seconds == 0 &&
	 * millisecs == 0) return minutes + "min"; else if (minutes == 0) return seconds
	 * + "s " + millisecs + "ms"; else if (seconds == 0) return minutes + "min " +
	 * millisecs + "ms"; else if (millisecs == 0) return minutes + "min " + seconds
	 * + "s";
	 * 
	 * return minutes + "min " + seconds + "s " + millisecs + "ms"; }
	 */

	private String generateOutput(HuffmanEncode encoded) {
		return encoded.huffmanTree + "**" + "\n";
	}
	

	public static byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data.getBytes());
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}
	
	public static String decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();
}
	
	

}
