package hw2_p2;

import hw2_p2.Image.WrapperForGradient;

public class Problem2_Main {
	
	public static void main(String[] args) throws Exception {
		Image img = new Image("foot.pgm");
		WrapperForGradient[][] dispose = img.findGradient();
		int iterns = 20;
		for (int i = 0; i < iterns; i ++) {
			img.applyLinearFilter();
		}
		
		img.writeToFile("test.pgm");
	}
}
