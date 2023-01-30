package net.mips.compiler;

public class Symboles {
	Tokens token;
	public String name;
	ClassIdf classe;
	
	public Symboles(Tokens token, String name) {
		this.token = token;
		this.name = name;
	}
	public Symboles() {
		this.name="";
	}
	public Tokens getToken() {
		return token;
	}
	public void setToken(Tokens token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ClassIdf getClasse() {
		return classe;
	}
	public void setClasse(ClassIdf classe) {
		this.classe = classe;
	}
	
}
