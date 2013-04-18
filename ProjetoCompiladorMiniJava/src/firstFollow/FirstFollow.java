package firstFollow;

import java.util.HashMap;
import java.util.Iterator;

public class FirstFollow {
	public static HashMap<String,Terminal> TE = new HashMap<String, Terminal>();
	public static HashMap<String,String> G = new HashMap<String, String>();
	public static HashMap<String, NaoTerminal> lista = new HashMap<String, NaoTerminal>();
	public static void initialize(){
		G.put("decl-seq", "decl,decl-seq'");
		G.put("decl-seq'", ";,decl-seq|#");
		G.put("decl", "s");
		
		lista.put("decl-seq", new NaoTerminal("decl-seq"));
		lista.put("decl-seq'", new NaoTerminal("decl-seq'"));
		lista.put("decl", new NaoTerminal("decl"));
		
		TE.put(";", new Terminal(";"));
		TE.put("s",  new Terminal(";"));
	}
	
	public static void main(String[] args) {
		initialize();
		Gramatica gramatica = new Gramatica();
		Iterator<String> ite = gramatica.NT.keySet().iterator();
		NaoTerminal nt = gramatica.NT.get(ite.next());
		nt.Firsts(gramatica);
	}
}
