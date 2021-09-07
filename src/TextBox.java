import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

@SuppressWarnings("serial")
public class TextBox extends JTextArea {
	public void readFile(String filepath) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String line;
		
		//set null to remove previous text
		this.setText(null);
		
		while ((line = reader.readLine()) != null) {
		    this.append(line + "\n");
		}
		reader.close();
	}
}
