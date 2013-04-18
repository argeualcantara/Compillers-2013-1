package firstFollow;

import java.util.ArrayList;
import java.util.HashMap;

public class FirstFollow {
	public static HashMap<String,Boolean> NT = null;
	public static HashMap<String,Boolean> TE = null;
	public static HashMap<String,String> G = null;

	public static void initialize(){
		G = new HashMap<String, String>();
		G.put("decl-seq", "decl,decl-seq'");
		G.put("decl-seq'", ";,decl-seq|#");
		G.put("decl", "s");
		
		NT = new HashMap<String, Boolean>();
		NT.put("decl-seq", true);
		NT.put("decl-seq'", true);
		NT.put("decl", true);
		
		TE = new HashMap<String, Boolean>();
		TE.put(";", true);
		TE.put("s", true);
	}
	
	public static void main(String[] args) {
		HashMap<String, NaoTerminal> lista = new HashMap<String, NaoTerminal>();
		lista.put("decl-seq", new NaoTerminal("decl-seq"));
		lista.put("decl-seq'", new NaoTerminal("decl-seq'"));
		lista.put("decl", new NaoTerminal("decl"));
		NaoTerminal.Firsts(lista);
		
	}
}
