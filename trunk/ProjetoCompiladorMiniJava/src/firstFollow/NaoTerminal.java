package firstFollow;

import java.util.ArrayList;
import java.util.Iterator;

public class NaoTerminal {
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
		if(Reader.TERMINAIS.containsKey(aux[0])){
			return new Terminal(aux[0]);
		}else{
			NaoTerminal temp = Reader.NAOTERMINAIS.get(aux[0]);
			String prodTemp = Reader.GRAMATICA.get(temp.val);
			return temp.First(prodTemp);
		}
	}

	public void Firsts() {
		for(NaoTerminal nt : Reader.NAOTERMINAIS.values()){
			String aux [] = Reader.GRAMATICA.get(nt.val).split("\\|");
			for (int i = 0; i < aux.length; i++) {
				nt.getFirst().add(nt.First(aux[i]));
			}
		}
		
	}
	
	public void Follows(){
		Iterator<String> ite = Reader.NAOTERMINAIS.keySet().iterator();
		while(ite.hasNext()){
			NaoTerminal nt = Reader.NAOTERMINAIS.get(ite.next());
			nt.Follow();
		}
	}

	private Terminal Follow() {
		
		return new Terminal("");
	}
}
