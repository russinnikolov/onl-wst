

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ToPrint {

	private final String port;

	public ToPrint(String port) {
		this.port = port;
	}

	public void printLabel(String ar) throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream(port);
		PrintStream ps = new PrintStream(os);
		ps.println(ar);
		System.out.println(ar);
		ps.print("\f");
		ps.close();
	}

}
