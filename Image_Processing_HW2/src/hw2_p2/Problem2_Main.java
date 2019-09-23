package hw2_p2;

public class Problem2_Main {
	
	public static void main(String[] args) throws Exception {
		Image img = new Image("foot.pgm");
		img.applyLinearFilter(); // T = 1
		img.applyLinearFilter(); // T = 2
		img.writeToFile("test.pgm");
	}
}
