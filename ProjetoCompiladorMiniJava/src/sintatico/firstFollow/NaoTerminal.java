package sintatico.firstFollow;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 * @author argeu
 *
 */
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
		if(Reader.TERMINAIS.containsKey(aux[0].trim())){
			return new Terminal(aux[0]);
		}else{
			NaoTerminal temp = Reader.NAOTERMINAIS.get(aux[0].trim());
			String prodTemp = Reader.GRAMATICA.get(temp.val);
			return temp.First(prodTemp);
		}
	}

	public void Firsts() {
		for(NaoTerminal nt : Reader.NAOTERMINAIS.values()){
			String aux [] = Reader.GRAMATICA.get(nt.val).split("\\|");
			for (int i = 0; i < aux.length; i++) {
				if(i==1){
					System.out.println();
				}
				Terminal tt = nt.First(aux[i]);
				nt.getFirst().add(tt);
			}
		}
		
	}
	
	public void Follows(){
		Regra1();
		Regra2();
		System.out.println("FIM REGRA 2");
		Regra3();
	}

	private void Regra1(){
		Iterator<String> ite = Reader.NAOTERMINAIS.keySet().iterator();
		while(ite.hasNext()){
			NaoTerminal nt = Reader.NAOTERMINAIS.get(ite.next());
			if(nt.val.equals(Reader.SIMBOLO_INICIAL)){
				nt.follow.add(new Terminal("$"));
				break;
			}
		}
	}
	
	private void Regra2() {
		Iterator<String> iteGra = Reader.GRAMATICA.keySet().iterator();
		while(iteGra.hasNext()){
			String [] prods = Reader.GRAMATICA.get(iteGra.next()).split("\\|");
			for (int i = 0; i < prods.length; i++) {
				String [] prod = prods[i].split(",");
				for (int j = 0; j < prod.length; j++) {
					while(j < prod.length && Reader.TERMINAIS.containsKey(prod[j].trim())){
						j++;
					}
					if(j < prod.length){
						NaoTerminal ntAtual = Reader.NAOTERMINAIS.get(prod[j]);;
						for (int k = j+1; k < prod.length; k++) {
							if(Reader.TERMINAIS.containsKey(prod[k].trim()) && !prod[k].trim().equals("#")){
								if(!ntAtual.jaTiver(prod[k].trim())){
									ntAtual.follow.add(new Terminal(prod[k].trim()));
								}
							}else{
								NaoTerminal ntProx = Reader.NAOTERMINAIS.get(prod[k].trim());
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
	}
	
	private void Regra3(){
		Iterator<String> iteGra = Reader.GRAMATICA.keySet().iterator();
		while(iteGra.hasNext()){
			String prodAtual = iteGra.next();
			String [] prods = Reader.GRAMATICA.get(prodAtual.trim()).split("\\|");
			NaoTerminal ntAtual = Reader.NAOTERMINAIS.get(prodAtual.trim());
			for (int i = 0; i < prods.length; i++) {
				String [] prod = prods[i].split(",");
				for (int k = prod.length-1; k > -1; k--) {
					if(!Reader.TERMINAIS.containsKey(prod[k].trim()) && !prod[k].trim().equals("#")){
						NaoTerminal ntProx = Reader.NAOTERMINAIS.get(prod[k].trim());
						if(ntProx.follow.size() == 0){
							addAllMenosVazio(ntProx.follow, ntAtual.follow);
						}else{
							addAllNaoRepitidos(ntProx.follow, ntAtual.follow);
						}
						if(!contemVazio(ntProx)){
							break;
						}
					}
				}
			}
		}
	}

	private void addAllMenosVazio(ArrayList<Terminal> adicionado, ArrayList<Terminal> adicionando) {
		ArrayList<Terminal> temp = new ArrayList<Terminal>();
		for (Terminal fi : adicionando) {
			if(!fi.getVal().equals("#")){
				temp.add(fi);
			}
		}
		adicionado.addAll(temp);
		
	}

	private void addAllNaoRepitidos(ArrayList<Terminal> adicionado, ArrayList<Terminal> adicionando) {
		ArrayList<Terminal> temp = new ArrayList<Terminal>();
		for (Terminal existente : adicionado) {
			for (Terminal inexistente : adicionando) {
				if(!existente.getVal().trim().equals(inexistente.getVal().trim()) && !inexistente.getVal().trim().equals("#")){
					temp.add(inexistente);
					break;
				}
			}
		}
		
		adicionado.addAll(temp);
	}

	public boolean contemVazio(NaoTerminal nt) {
		boolean isFound = false;
		for(Terminal atual : nt.first){
			if(atual.getVal().equals("#")){
				isFound = true;
				break;
			}
		}
		return isFound;
	}
	
	public boolean jaTiver(String val) {
		boolean isFound = false;
		for(Terminal atual : this.follow){
			if(atual.getVal().trim().equals(val.trim())){
				isFound = true;
			}
		}
		return isFound;
	}
}
