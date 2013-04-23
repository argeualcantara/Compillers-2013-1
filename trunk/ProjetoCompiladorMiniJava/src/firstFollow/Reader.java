package firstFollow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Reader {
	public static String SIMBOLO_INICIAL = "";;
	public static LinkedHashMap<String, String> GRAMATICA = new LinkedHashMap<String, String>();
	public static HashMap<String,Boolean> TERMINAIS = new HashMap<String, Boolean>();
	public static LinkedHashMap<String, NaoTerminal> NAOTERMINAIS = new LinkedHashMap<String, NaoTerminal>();
	
	@SuppressWarnings("resource")
	public static void popularGramatica() {
		BufferedReader br = null;
		try {
			
			String so = System.getProperties().get("os.name").toString();
			String GrammarPath = "";
			String TerminalPath = "";
			if(so.toLowerCase().startsWith("win")){
				GrammarPath = "C:\\Users\\Argeu\\Ferramentas\\Java\\workspace\\ProjetoCompiladorMiniJava\\src\\firstFollow\\gramatica.txt";
				TerminalPath = "C:\\Users\\Argeu\\Ferramentas\\Java\\workspace\\ProjetoCompiladorMiniJava\\src\\firstFollow\\terminais.txt";
			}else{
				GrammarPath = "/home/argeu/Tools/Java/workspace/ProjetoCompiladorMiniJava/src/firstFollow/gramatica.txt";
				TerminalPath = "/home/argeu/Tools/Java/workspace/ProjetoCompiladorMiniJava/src/firstFollow/terminais.txt";
			}
			File file = new File(GrammarPath);
			br = new BufferedReader(new FileReader(file));
			String read = "";
			int i = 0;
			while((read = br.readLine()) != null){
				String s [] = read.split("=");
				if(i == 0){
					i++;
					SIMBOLO_INICIAL = s[0].trim();
				}
				GRAMATICA.put(s[0].trim(), s[1].trim());
				NAOTERMINAIS.put(s[0].trim(), new NaoTerminal(s[0].trim()));
			}
			file = new File(TerminalPath);
			br = new BufferedReader(new FileReader(file));
			read = "";
			while((read = br.readLine()) != null){
				if(read != null && read.trim().length() > 0){
					TERMINAIS.put(read.trim(), true);
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
					e.printStackTrace();
				}
			}
		}
	}
}
