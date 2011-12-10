import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Sum {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner in= new Scanner(new File("input.txt"));
		PrintWriter out = new PrintWriter("output.txt");
		out.println(in.nextInt() + in.nextInt());
		out.flush();
	}

}
