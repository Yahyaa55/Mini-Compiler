package net.mips.compiler;
import java.io.IOException;
import java.util.ArrayList;


public class ScannerWS extends Scanner {
	
	ArrayList<Symboles> tablesymb;
	int placesymb;
	int val;
	
	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public ArrayList<Symboles> getTablesymb() {
		return tablesymb;
	}

	public void setTablesymb(ArrayList<Symboles> tablesymb) {
		this.tablesymb = tablesymb;
	}

	public int getPlacesymb() {
		return placesymb;
	}

	public void setPlacesymb(int placesymb) {
		this.placesymb = placesymb;
	}

	public ScannerWS(String file_name) throws IOException, ErreurCompilation {
		super(file_name);
		tablesymb = new ArrayList<Symboles>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initMotsCles() {
		tablesymb.add(new Symboles(Tokens.PROGRAM_TOKEN,"program"));     //  <--- Error
		tablesymb.add(new Symboles(Tokens.BEGIN_TOKEN,"Begin"));
		tablesymb.add(new Symboles(Tokens.CONST_TOKEN,"const"));
		tablesymb.add(new Symboles(Tokens.DO_TOKEN,"do"));
		tablesymb.add(new Symboles(Tokens.END_TOKEN,"end"));
		tablesymb.add(new Symboles(Tokens.IF_TOKEN,"if"));
		tablesymb.add(new Symboles(Tokens.THEN_TOKEN,"then"));
		tablesymb.add(new Symboles(Tokens.READ_TOKEN,"read"));
		tablesymb.add(new Symboles(Tokens.VAR_TOKEN,"var"));
		tablesymb.add(new Symboles(Tokens.WRITE_TOKEN,"write"));
		tablesymb.add(new Symboles(Tokens.WHILE_TOKEN,"while"));
	}

	@Override
	public void Codage_Lex() {
		String nom_test = symb_cour.getName();
		for(Symboles smp : tablesymb) {
			if(nom_test.equalsIgnoreCase(smp.getName())) {
				symb_cour.setToken(smp.getToken());
				return;
			}
		}
		symb_cour.setToken(Tokens.ID_TOKEN);
	}
	
	public void LIRE_NOMBRE() throws IOException {
		super.LIRE_NOMBRE();
		val=Integer.parseInt(symb_cour.name);
	}
	
	public void entrerSymb(ClassIdf c) {
		Symboles s=new Symboles();
		s.setName(symb_cour.name);
		s.setToken(symb_cour.token);
		s.setClasse(c);
		
		tablesymb.add(s);
	}
	
	public void chercherSymb() {
		String nom1=symb_cour.name;
		for(int i=0;i<tablesymb.size();i++) {
			String nom2=tablesymb.get(i).name;
			if (nom1.equalsIgnoreCase(nom2)) {
				placesymb=i;
				return;
			}
		}
		placesymb=-1;
	}

}
