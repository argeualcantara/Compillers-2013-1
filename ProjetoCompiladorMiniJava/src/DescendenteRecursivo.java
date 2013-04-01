import java.util.List;


public class DescendenteRecursivo {
	private static List<Registro> lista = null;
	
	public static void MainClass() throws Exception{
		Registro reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("class")){
			throw new Exception("reserved word CLASS expected");
		}
		reg = lista.remove(0);
		if(!reg.type.equalsIgnoreCase("Identificador")){
			throw new Exception("CLASS name expected");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("{")){
			throw new Exception("{ expected");
		}
		reg = lista.remove(0);
		
		if(reg.valor.equalsIgnoreCase("public")){
			reg = lista.remove(0);
			if(reg.valor.equalsIgnoreCase("static")){
				reg = lista.remove(0);
				if(!reg.valor.equalsIgnoreCase("void")){
					throw new Exception("Main method definition invalid: [public] [static] void main (String [] )");
				}
			}
		}else if(reg.valor.equalsIgnoreCase("static")){
			reg = lista.remove(0);
			if(!reg.valor.equalsIgnoreCase("void")){
				throw new Exception("Main method definition invalid: [public] [static] void main (String [] )");
			}
		}else if(!reg.valor.equalsIgnoreCase("void")){
			throw new Exception("Main method definition invalid: [public] [static] void main (String [] )");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("main")){
			throw new Exception("Main method definition invalid: [public] [static] void main (String [] )");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("(")){
			throw new Exception("( expected");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("string")){
			throw new Exception("String type expected");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("[")){
			throw new Exception("[ expected");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("]")){
			throw new Exception("] expected");
		}
		reg = lista.remove(0);
		if(!reg.type.equalsIgnoreCase("Identificador")){
			throw new Exception("Parameter name expected");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase(")")){
			throw new Exception(") expected");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("{")){
			throw new Exception("{ expected");
		}
		//Main Class definition
		Statement();
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("}")){
			throw new Exception("} expected");
		}
		reg = lista.remove(0);
		if(!reg.valor.equalsIgnoreCase("}")){
			throw new Exception("} expected");
		}
	}
	
	public static void Statement() throws Exception {
		Registro reg = lista.remove(0);
		if(reg.valor.equalsIgnoreCase("{")){
			boolean statement = true;
			while(statement){
				Statement();
				if(lista.get(1).valor.equals("}")){
					statement = false;
				}
			}
			reg = lista.remove(0);
			if(!reg.valor.equalsIgnoreCase("}")){
				throw new Exception("} expected");
			}
		}else if(reg.valor.equalsIgnoreCase("IF")){
			reg = lista.remove(0);
			if(reg.valor.equals("(")){
				Expression();
				reg = lista.remove(0);
				if(reg.valor.equals(")")){
					Statement();
					if(lista.get(0).valor.equalsIgnoreCase("ELSE")){
						Statement();
					}
				}else{
					throw new Exception(") expected");
				}
			}else{
				throw new Exception("( expected");
			}
		}else if(reg.valor.equalsIgnoreCase("WHILE")){
			reg = lista.remove(0);
			if(reg.valor.equals("(")){
				Expression();
				reg = lista.remove(0);
				if(reg.valor.equals(")")){
					Statement();
				}else{
					throw new Exception(") expected");
				}
			}else{
				throw new Exception("( expected");
			}
		}else if(reg.valor.equalsIgnoreCase("System.out.println")){
			reg = lista.remove(0);
			if(reg.valor.equalsIgnoreCase("(")){
				Expression();
				reg = lista.remove(0);
				if(reg.valor.equalsIgnoreCase(")")){
					reg = lista.remove(0);
					if(!reg.valor.equals(";")){
						throw new Exception("; expected");
					}
				}else{
					throw new Exception(") expected");
				}
			}else{
				throw new Exception("( expected");
			}
		}else if(reg.type.equalsIgnoreCase("Identificador")){
			reg = lista.remove(0);
			if(reg.valor.equals("=")){
				Expression();
				reg = lista.remove(0);
				if(!reg.valor.equals(";")){
					throw new Exception("; expected");
				}
			}else if(reg.valor.equals("[")){
				Expression();
				reg = lista.remove(0);
				if(reg.valor.equals("]")){
					reg = lista.remove(0);
					if(reg.valor.equals("=")){
						Expression();
						reg = lista.remove(0);
						if(!reg.valor.equals(";")){
							throw new Exception("; expected");
						}
					}else{
						throw new Exception("= expected");
					}
				}else{
					throw new Exception("] expected");
				}
			}else{
				throw new Exception("= or [ expected");
			}
		}else if(reg.valor.equalsIgnoreCase("int") || reg.valor.equalsIgnoreCase("boolean") || reg.valor.equalsIgnoreCase("String")){
			VarDeclaration(reg);
		}else{
			throw new Exception("Sintaxe incorreta, Statement expected");
		}
	}

	private static void VarDeclaration(Registro reg) throws Exception {
		Type(reg);
		reg = lista.remove(0);
		if(!reg.type.equalsIgnoreCase("Identificador")){
			throw new Exception("variable name expected");
		}
		reg = lista.remove(0);
		if(reg.valor.equals("=")){
			Expression();
			reg = lista.remove(0);
			if(!reg.valor.equals(";")){
				throw new Exception("; expected");
			}
		}else if(!reg.valor.equals(";")){
			throw new Exception("; expected");
		}
	}

	private static void Type(Registro reg) throws Exception {
		if(reg.valor.equalsIgnoreCase("int")){
			reg = lista.get(0);
			if(reg.valor.equals("[")){
				reg = lista.remove(0);
				reg = lista.get(0);
				if(!reg.valor.equals("]")){
					throw new Exception("] expected");
				}
			}
		}
		if(!reg.valor.equalsIgnoreCase("boolean") && !reg.valor.equalsIgnoreCase("String")){
			throw new Exception("variable type expected");
		}
	}

	private static void Expression() throws Exception {
		Registro reg = lista.remove(0);
		if(reg.type.equalsIgnoreCase("Identificador")){
			
		}else if(reg.valor.equalsIgnoreCase("true")){
			
		}else if(reg.valor.equalsIgnoreCase("false")){
			
		}else if(reg.valor.equalsIgnoreCase("new")){
			reg = lista.remove(0);
			if(reg.valor.equals("int")){
				reg = lista.remove(0);
				if(reg.valor.equals("[")){
					Expression();
					reg = lista.remove(0);
					if(!reg.valor.equals("]")){
						throw new Exception("] expected");
					}
				}else{
					throw new Exception("[ expected");
				}
			}else{
				throw new Exception("int type expected");
			}
		}else if(reg.valor.equals("!")){
			Expression();
		}else if(reg.valor.equals("(")){
			Expression();
			reg = lista.remove(0);
			if(!reg.valor.equals(")")){
				throw new Exception(") expected");
			}
		}else if(reg.type.equalsIgnoreCase("Numero Literal")){
			
		}else if(reg.valor.equalsIgnoreCase('"'+"")){
			reg = lista.remove(0);
			if(reg.type.equalsIgnoreCase("String Literal")){
				reg = lista.remove(0);
				if(!reg.valor.equals('"'+"")){
					throw new Exception('"'+" expected");
				}
			}
		}
		
	}

	public static void main(String[] args) {
		lista = Transdutor.lerGramatica();
//		Transdutor.imprimirResultados();
		try {
			for (Registro reg : lista) {
				if(reg.type.equalsIgnoreCase("Nao pertence a gramatica")){
					throw new Exception("Codigo contem elementos que nao pertencem a gramatica");
				}
			}
			MainClass();
			System.out.println("Programa com sintaxe correta");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Sintaxe incorreta");
		}
	}
}
