package hw2_p2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *  This is the image class that contains some methods to read and perform filtering on the image of a pgm file
 * @author Zheng
 *
 */
public class Image {

	public static final int MAXVAL = 255;
	public int[][] image;
	public int height;
	public int width;

	/**
	 * The Constructor for Image class
	 * 
	 * @param filePath: A String indicates the file path to the file
	 * @throws Exception Illegal PGM file exception OR cannot find path location
	 */
	public Image(String filePath) throws Exception {
		WrapperForImage imgData = readPGM(filePath);
		this.image = imgData.image;
		this.height = imgData.y;
		this.width = imgData.x;
	}

	/**
	 * Returns a wrapper class containing image information
	 * 
	 * @param fileName
	 * 
	 * @return a wrapper class containing pixel information, height and width
	 *         information
	 * 
	 * @throws Exception
	 */
	public WrapperForImage readPGM(String fileName) throws Exception {

		int width;
		int height;
		int maxVal;
		int[][] img;
		try {
			Scanner filefinder = new Scanner(new File(fileName));
			String magicNumber = filefinder.next();
			if (!magicNumber.equals("P2")) {
				filefinder.close();
				throw new Exception("Illegal PGM file");
			}

			filefinder.nextLine();
			filefinder.nextLine();
			width = filefinder.nextInt();
			height = filefinder.nextInt();
			maxVal = filefinder.nextInt();

			img = new int[height][width];

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					img[y][x] = filefinder.nextInt();
				}
			}

			filefinder.close();

		} catch (IOException e) {
			throw new Exception("Cannot find the specified file in path");
		}

		return new WrapperForImage(img, width, height);
	}

	/**
	 * This method applies a linear filter to the image with time difference of 1.
	 * This method will overwrite the image data.
	 */
	public void applyLinearFilter() {
		int[][] originalImg = copyArray(this.image);
		
		
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {

				int tempDiffX = 0;
				int tempDiffY = 0;

				if (j == 0) {
					tempDiffX = originalImg[i][j + 1] - 2 * originalImg[i][j];
					if (i == 0)
						// Upper Left Corner
						tempDiffY = originalImg[i + 1][j] - 2 * originalImg[i][j];
					else if (i == this.height - 1)
						// Lower Left Corner
						tempDiffY = -2 * originalImg[i][j] + originalImg[i - 1][j];
					else
						// Left Edge
						tempDiffY = originalImg[i + 1][j] - 2 * originalImg[i][j] + originalImg[i - 1][j];

				} else if (j == this.width - 1) {
					tempDiffX = -2 * originalImg[i][j] + originalImg[i][j - 1];
					if (i == 0)
						// Upper Right Corner
						tempDiffY = originalImg[i + 1][j] - 2 * originalImg[i][j];
					else if (i == this.height - 1)
						// Lower right Corner
						tempDiffY = -2 * originalImg[i][j] + originalImg[i - 1][j];
					else
						// Right edge
						tempDiffY = originalImg[i + 1][j] - 2 * originalImg[i][j] + originalImg[i - 1][j];

				} else if (i == 0 && j != 0 && j != this.width - 1) {
					// Upper edge
					tempDiffX = originalImg[i][j + 1] - 2 * originalImg[i][j] + originalImg[i][j - 1];
					tempDiffY = originalImg[i + 1][j] - 2 * originalImg[i][j];
				} else if (i == this.height - 1 && j != 0 && j != this.width - 1) {
					// Lower Edge
					tempDiffX = originalImg[i][j + 1] - 2 * originalImg[i][j] + originalImg[i][j - 1];
					tempDiffY = -2 * originalImg[i][j] + originalImg[i - 1][j];
				} else {
					tempDiffX = originalImg[i][j + 1] - 2 * originalImg[i][j] + originalImg[i][j - 1];
					tempDiffY = originalImg[i + 1][j] - 2 * originalImg[i][j] + originalImg[i - 1][j];
				}

				this.image[i][j] = tempDiffX + tempDiffY + originalImg[i][j];
				if (this.image[i][j] < 0)
					this.image[i][j] = 0;
			}
		}

	}

	/**
	 * This returns a
	 * 
	 * @param x
	 * @param y
	 * @param stdv
	 * @return
	 */
	public double guassianKernel(int x, int y, double stdv) {
		double toReturn = 1.0 / (2.0 * Math.PI * Math.pow(stdv, 2));
		double power = -(x * x + y * y) / (2 * stdv * stdv);
		toReturn = toReturn * Math.exp(power);
		return toReturn;
	}

	/**
	 * This method handles writting to a PGM file
	 * @param fileName
	 */
	public void writeToFile(String fileName) {
		try {
			PrintWriter out = new PrintWriter(fileName);
			StringBuilder toPrint = new StringBuilder();
			toPrint.append("P2 \n");
			toPrint.append("# Created by IrfanView\n");
			toPrint.append(Integer.toString(width) + " " + Integer.toString(height) + "\n");
			toPrint.append(Integer.toString(MAXVAL) + "\n");
			for (int i = 0; i < this.height; i++) {
				for (int j = 0; j < this.width; j++) {
					toPrint.append(Integer.toString(this.image[i][j]) + " ");
				}
				toPrint.append("\n");
			}
			String finalString = toPrint.toString();
			out.print(finalString);
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Copies an 2D int array
	 * @param toCopy 2D int Array
	 * @return 2D int array
	 */
	public int[][] copyArray(int[][] toCopy){
		int[][] toReturn = new int[toCopy.length][toCopy[0].length];
		for (int i = 0; i < toCopy.length; i ++) {
			for (int j = 0; j < toCopy[0].length; j ++) {
				toReturn[i][j] = toCopy[i][j];
			}
		}
		
		return toReturn;
	}
	
	/**
	 * A wrapper class for returning image information, containing pixel information, height and width
	 * @author Zheng
	 *
	 */
	class WrapperForImage {
		int[][] image;
		int x;
		int y;

		public WrapperForImage(int[][] img, int x, int y) {
			this.image = img;
			this.x = x;
			this.y = y;
		}

	}
}
