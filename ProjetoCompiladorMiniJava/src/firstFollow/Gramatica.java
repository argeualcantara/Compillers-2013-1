package firstFollow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Gramatica {
	public HashMap<String,String> G = new HashMap<String, String>();
	public HashMap<String,Boolean> TE = new HashMap<String, Boolean>();
	public HashMap<String, NaoTerminal> NT = new HashMap<String, NaoTerminal>();
	
	@SuppressWarnings("resource")
	public Gramatica(){
		super();
		BufferedReader br = null;
		try {
			
			String so = System.getProperties().get("os.name").toString();
			String GrammarPath = "";
			String TerminalPath = "";
			if(so.toLowerCase().startsWith("win")){
				GrammarPath = "C:\\Users\\730543255\\workspace\\ProjetoCompiladorMiniJava\\src\\firstFollow\\gramatica.txt";
				TerminalPath = "C:\\Users\\730543255\\workspace\\ProjetoCompiladorMiniJava\\src\\firstFollow\\terminais.txt";
			}else{
				GrammarPath = "";
				TerminalPath = "";
			}
			
//			JOptionPane.showMessageDialog(null, "Escolha o arquivo que contem a gramatica");
//			JFileChooser fileChooser = new JFileChooser("");
//			fileChooser.showOpenDialog(null);
			File file = new File(GrammarPath);
			
			br = new BufferedReader(new FileReader(file));
			String read = "";
			while((read = br.readLine()) != null){
				String s [] = read.split("=");
				G.put(s[0].trim(), s[1].trim());
				NT.put(s[0].trim(), new NaoTerminal(s[0].trim()));
			}
			
//			JOptionPane.showMessageDialog(null, "Escolha o arquivo que contem os terminais");
//			fileChooser = new JFileChooser("");
//			fileChooser.showOpenDialog(null);
//			file = fileChooser.getSelectedFile();
			file = new File(TerminalPath);
			br = new BufferedReader(new FileReader(file));
			read = "";
			while((read = br.readLine()) != null){
				if(read != null && read.trim().length() > 0){
					TE.put(read.trim(), true);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
