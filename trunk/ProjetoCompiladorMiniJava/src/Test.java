import java.util.HashMap;



public class Test {
	public HashMap<String, Boolean> RESERVADO = new HashMap<String, Boolean>();
	public HashMap<String, Boolean> OPERADOR = new HashMap<String, Boolean>();
	
	String Identifier = "(A-Z | a-z)(a-z | A-Z | 0-9 | _)*";
	String NumberLiteral = "(0-9)*";
	
	public void Initialize(){
		// PALAVRAS RESERVADAS
		RESERVADO.put("if", true);
		RESERVADO.put("else", true);
		RESERVADO.put("while", true);
		RESERVADO.put("this", true);
		RESERVADO.put("class", true);
		RESERVADO.put("public", true);
		RESERVADO.put("static", true);
		RESERVADO.put("void", true);
		RESERVADO.put("main", true);
		RESERVADO.put("String", true);
		RESERVADO.put("int", true);
		RESERVADO.put("boolean", true);
		RESERVADO.put("true", true);
		RESERVADO.put("false", true);
		
		//OPERADOR
		OPERADOR.put("!", true);
		OPERADOR.put("+", true);
		OPERADOR.put("-", true);
		OPERADOR.put("/", true);
		OPERADOR.put("*", true);
		OPERADOR.put("<", true);
		OPERADOR.put(">", true);
		OPERADOR.put("=", true);
		OPERADOR.put("&", true);
		OPERADOR.put("|", true);
		OPERADOR.put(".", true);
		OPERADOR.put(",", true);
		OPERADOR.put("{", true);
		OPERADOR.put("}", true);
		OPERADOR.put(";", true);
	}
	
	public static void main(String[] args) {
	}
}
