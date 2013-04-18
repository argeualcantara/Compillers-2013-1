package firstFollow;

import java.util.ArrayList;
import java.util.HashMap;

public class NaoTerminal {
	public static HashMap<String,Boolean> NT = null;
	public static HashMap<String,Boolean> TE = null;
	public static HashMap<String,String> G = null;
	public String val;
	public ArrayList<Terminal> first = new ArrayList<Terminal>();
	public ArrayList<Terminal> follow = new ArrayList<Terminal>();

	public NaoTerminal(String val){
		this();
		this.val = val;
	}
	
	public NaoTerminal(){
		super();
		
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
	
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public ArrayList<Terminal> getFirst() {
		return first;
	}
	public void setFirst(ArrayList<Terminal> first) {
		this.first = first;
	}
	public ArrayList<Terminal> getFollow() {
		return follow;
	}
	public void setFollow(ArrayList<Terminal> follow) {
		this.follow = follow;
	}
	
	public Terminal First(HashMap<String, NaoTerminal> lista, int i){
		String [] prodAux = G.get(this.val).split("\\|");
		String aux [] = prodAux[i].split(",");
		if(TE.containsKey(aux[0]) || aux[0].equals("#")){
			this.getFirst().add(new Terminal(aux[0]));
			System.out.println(aux[0]+"\n");
			return new Terminal(aux[0]);
		}else{
			NaoTerminal temp = lista.get(aux[0]);
			temp.getFirst().add(temp.First(lista, i));
		}
		return new Terminal("");
	}

	public static void Firsts(HashMap<String, NaoTerminal> lista) {
		for(NaoTerminal nt : lista.values()){
			System.out.println("FIRST("+nt.val+"):");
			String aux [] = G.get(nt.val).split("\\|");
			for (int i = 0; i < aux.length; i++) {
				nt.First(lista, i);
			}
		}
		
	}
}
