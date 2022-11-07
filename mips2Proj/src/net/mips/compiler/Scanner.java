package net.mips.compiler;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Scanner {
	public ArrayList<Symboles> motsCles;
	public Symboles symb_cour;
	public char carCour;
	public FileReader fluxSour;
	final static int EOF = '\0';
		//Constructeur :
	public Scanner(String file_name) throws IOException, ErreurCompilation {
		File file = new File(file_name);
		if(!file.exists()) 
			throw new ErreurLexicale(CodesErr.FIC_VIDE_ERR);
		else
			fluxSour = new FileReader(file_name);
			motsCles = new ArrayList<Symboles>();
	}
	public ArrayList<Symboles> getMotsCles() {
		return motsCles;
	}
	public void setMotsCles(ArrayList<Symboles> motsCles) {
		this.motsCles = motsCles;
	}
	public Symboles getSymb_cour() {
		return symb_cour;
	}
	public void setSymb_cour(Symboles symb_cour) {
		this.symb_cour = symb_cour;
	}
	public char getCarCour() {
		return carCour;
	}
	public void setCarCour(char carCour) {
		this.carCour = carCour;
	}
	public FileReader getFluxSour() {
		return fluxSour;
	}
	public void setFluxSour(FileReader fluxSour) {
		this.fluxSour = fluxSour;
	}
	
	public void initMotsCles() {
		motsCles.add(new Symboles(Tokens.PROGRAM_TOKEN,"program"));
		motsCles.add(new Symboles(Tokens.BEGIN_TOKEN,"Begin"));
		motsCles.add(new Symboles(Tokens.CONST_TOKEN,"const"));
		motsCles.add(new Symboles(Tokens.DO_TOKEN,"do"));
		motsCles.add(new Symboles(Tokens.END_TOKEN,"end"));
		motsCles.add(new Symboles(Tokens.IF_TOKEN,"if"));
		motsCles.add(new Symboles(Tokens.THEN_TOKEN,"then"));
		motsCles.add(new Symboles(Tokens.READ_TOKEN,"read"));
		motsCles.add(new Symboles(Tokens.VAR_TOKEN,"var"));
		motsCles.add(new Symboles(Tokens.WRITE_TOKEN,"write"));
		motsCles.add(new Symboles(Tokens.WHILE_TOKEN,"while"));
	}
	
	public void Codage_Lex() {
		String nom_test = symb_cour.getName();
		for(Symboles smp : motsCles) {
			if(nom_test.equalsIgnoreCase(smp.getName())) {
				symb_cour.setToken(smp.getToken());
				return;
			}
		}
		symb_cour.setToken(Tokens.ID_TOKEN);
	}
	
	public void LIRE_CAR() throws IOException{
		if(fluxSour.ready())
			carCour = (char) fluxSour.read();
		else
			carCour = EOF;
	}
	
	public void LIRE_NOMBRE() throws IOException {
		do {
			symb_cour.setName(symb_cour.getName()+carCour);
			LIRE_CAR();
		}while(Character.isDigit(carCour));
		symb_cour.setToken(Tokens.NUM_TOKEN);
	}
	
	public void LIRE_MOT() throws IOException {
		do {
			symb_cour.setName(symb_cour.getName()+carCour);
			LIRE_CAR();
		}while(Character.isLetter(carCour));
		Codage_Lex();
	}
	
	public void SYMB_SUIV() throws IOException, ErreurCompilation {
		symb_cour = new Symboles();
		while(Character.isWhitespace(carCour))
			LIRE_CAR();
		if(Character.isLetter(carCour))
			LIRE_MOT();
		else if(Character.isDigit(carCour))
			LIRE_NOMBRE();
		else
			switch(carCour) {
			case '+':
				symb_cour.setToken(Tokens.PLUS_TOKEN);
				LIRE_CAR();
				break;
			case '-':
				symb_cour.setToken(Tokens.MOINS_TOKEN);
				LIRE_CAR();
				break;
			case '*':
				symb_cour.setToken(Tokens.MUL_TOKEN);
				LIRE_CAR();
				break;
			case '/':
				symb_cour.setToken(Tokens.DIV_TOKEN);
				LIRE_CAR();
				break;
			case '(':
				symb_cour.setToken(Tokens.PARG_TOKEN);
				LIRE_CAR();
				break;
			case ')':
				symb_cour.setToken(Tokens.PARD_TOKEN);
				LIRE_CAR();
				break;
			case '.':
				symb_cour.setToken(Tokens.PNT_TOKEN);
				LIRE_CAR();
				break;
			case ',':
				symb_cour.setToken(Tokens.VIR_TOKEN);
				LIRE_CAR();
				break;
			case ';':
				symb_cour.setToken(Tokens.PVIR_TOKEN);
				LIRE_CAR();
				break;
			case '=':
				symb_cour.setToken(Tokens.EG_TOKEN);
				LIRE_CAR();
				break;
			case EOF:
				symb_cour.setToken(Tokens.EOF_TOKEN);
				break;
			case ':':
				LIRE_CAR();
				switch(carCour) {
				case '=':
					symb_cour.setToken(Tokens.AFFEC_TOKEN);
					LIRE_CAR();
					break;
				default:
					symb_cour.setToken(Tokens.ERR_TOKEN);
					throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
				}
				break;
			case '>':
				LIRE_CAR();
				switch(carCour) {
				case '=':
					symb_cour.setToken(Tokens.SUPEG_TOKEN);
					LIRE_CAR();
					break;
				default:
					symb_cour.setToken(Tokens.SUP_TOKEN);
					break;
				}
				break;
			case '<':
				LIRE_CAR();
				switch(carCour) {
				case '=':
					symb_cour.setToken(Tokens.INFEG_TOKEN);
					LIRE_CAR();
					break;
				case '>':
					symb_cour.setToken(Tokens.DIFF_TOKEN);
					LIRE_CAR();
					break;
				default:
					symb_cour.setToken(Tokens.INF_TOKEN);
					break;
				}
				break;
			default:
				throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
			}
	}
	
	public static void main(String args[]) throws IOException,ErreurCompilation{
		Scanner scan = new Scanner("C:\\Users\\21267\\eclipse-workspace\\mips2Proj\\bin\\net\\test11.p");
		scan.initMotsCles();
		scan.LIRE_CAR();
		while(scan.getCarCour()!=EOF) {
			scan.SYMB_SUIV();
			System.out.println(scan.getSymb_cour().getToken());
		}
		if(scan.getCarCour()==EOF)
			scan.SYMB_SUIV();
			System.out.println(scan.getSymb_cour().getToken());
	}
}
