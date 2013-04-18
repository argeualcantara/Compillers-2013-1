package firstFollow;

import java.util.Iterator;

public class FirstFollow {
	public static void main(String[] args) {
		Gramatica gramatica = new Gramatica();
		Iterator<String> ite = gramatica.NT.keySet().iterator();
		NaoTerminal nt = gramatica.NT.get(ite.next());
		nt.Firsts(gramatica);
		for (Iterator<String> iterator = gramatica.NT.keySet().iterator(); iterator.hasNext();) {
			String aux = iterator.next();
			NaoTerminal naot = gramatica.NT.get(aux);
			System.out.println("First("+naot.val+"): ");
			for (Terminal tt : naot.first) {
				System.out.println(tt.getVal());
			}
		}
	}
}
