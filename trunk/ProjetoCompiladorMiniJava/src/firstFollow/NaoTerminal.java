package firstFollow;

import java.util.ArrayList;
import java.util.HashMap;

public class NaoTerminal {
	private static HashMap<String, NaoTerminal> lista = new HashMap<String, NaoTerminal>();
	public HashMap<String,Boolean> TE = null;
	public HashMap<String,String> G = null;
	public String val;
	public ArrayList<Terminal> first = new ArrayList<Terminal>();
	public ArrayList<Terminal> follow = new ArrayList<Terminal>();

	public NaoTerminal(String val){
		this();
		this.val = val;
	}
	
	public NaoTerminal(){
		super();
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
	
	public Terminal First(String valor){
		String aux [] = valor.split(",");
		if(TE.containsKey(aux[0])){
			this.getFirst().add(new Terminal(aux[0]));
			System.out.println(aux[0]);
			return new Terminal(aux[0]);
		}else{
			NaoTerminal temp = getLista().get(aux[0]);
			String prodTemp = G.get(temp.val);
			temp.G = this.G;
			temp.setLista(this.getLista());
			temp.TE = this.TE;
			temp.getFirst().add(temp.First(prodTemp));
		}
		return new Terminal("");
	}

	public void Firsts(Gramatica gramatica) {
		for(NaoTerminal nt : gramatica.NT.values()){
			nt.G = gramatica.G;
			nt.TE = gramatica.TE;
			nt.setLista(gramatica.NT);
			System.out.println("FIRST("+nt.val+"):");
			String aux [] = nt.G.get(nt.val).split("\\|");
			for (int i = 0; i < aux.length; i++) {
				
				nt.First(aux[i]);
			}
		}
		
	}

	public HashMap<String, NaoTerminal> getLista() {
		return lista;
	}

	public void setLista(HashMap<String, NaoTerminal> lista) {
		NaoTerminal.lista = lista;
	}
}
