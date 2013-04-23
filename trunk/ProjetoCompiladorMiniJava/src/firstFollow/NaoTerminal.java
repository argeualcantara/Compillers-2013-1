package firstFollow;

import java.util.ArrayList;
import java.util.Iterator;

public class NaoTerminal{
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
			if(nt.val.equals(Reader.SIMBOLO_INICIAL)){
				nt.follow.add(new Terminal("$"));
			}
			nt.Follow();
		}
	}

	private Terminal Follow() {
		Iterator<String> iteGra = Reader.GRAMATICA.keySet().iterator();
		while(iteGra.hasNext()){
			Regra2(iteGra);
		}
		return new Terminal("");
	}

	private void Regra2(Iterator<String> iteGra) {
		String [] prods = Reader.GRAMATICA.get(iteGra.next()).split("\\|");
		for (int i = 0; i < prods.length; i++) {
			String [] prod = prods[i].split(",");
			for (int j = 0; j < prod.length; j++) {
				while(j < prod.length && Reader.TERMINAIS.containsKey(prod[j])){
					j++;
				}
				if(j < prod.length){
					NaoTerminal ntAtual = Reader.NAOTERMINAIS.get(prod[j]);;
					
					for (int k = j+1; k < prod.length; k++) {
						if(Reader.TERMINAIS.containsKey(prod[k]) && !prod[k].equals("#")){
							if(!ntAtual.jaTiver(prod[k])){
								ntAtual.follow.add(new Terminal(prod[k]));
							}
						}else{
							NaoTerminal ntProx = Reader.NAOTERMINAIS.get(prod[k]);
							if(ntAtual.follow.size() == 0){
								addAllMenosVazio(ntAtual.follow, ntProx.first);
							}else{
								addAllNaoRepitidos(ntAtual.follow, ntProx.first);
							}
							if(!contemVazio(ntProx)){
								break;
							}
						}
					}
				}
			}
		}
	}

	private void addAllMenosVazio(ArrayList<Terminal> follow2, ArrayList<Terminal> first2) {
		ArrayList<Terminal> temp = new ArrayList<Terminal>();
		for (Terminal fi : first2) {
			if(!fi.getVal().equals("#")){
				temp.add(fi);
			}
		}
		follow2.addAll(temp);
		
	}

	private void addAllNaoRepitidos(ArrayList<Terminal> follow2, ArrayList<Terminal> first2) {
		ArrayList<Terminal> temp = new ArrayList<Terminal>();
		for (Terminal fl : follow2) {
			for (Terminal fi : first2) {
				if(!fl.getVal().equals(fi.getVal()) && !fi.getVal().equals("#")){
					temp.add(fi);
				}
			}
		}
		follow2.addAll(temp);
	}

	public boolean contemVazio(NaoTerminal nt) {
		boolean isFound = false;
		for(Terminal atual : nt.first){
			if(atual.getVal().equals("#")){
				isFound = true;
			}
		}
		return isFound;
	}
	
	public boolean jaTiver(String val) {
		boolean isFound = false;
		for(Terminal atual : this.follow){
			if(atual.getVal().equals(val)){
				isFound = true;
			}
		}
		return isFound;
	}
}
