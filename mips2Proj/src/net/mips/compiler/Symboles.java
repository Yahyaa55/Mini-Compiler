package net.mips.compiler;

public class Symboles {
	Tokens token;
	public String name;
	public Symboles(Tokens token, String name) {
		super();
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
}
