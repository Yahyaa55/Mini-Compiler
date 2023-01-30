package net.mips.compiler;

import java.io.IOException;

public class ParserWS extends Parser {

	public ParserWS(String file_name) throws IOException, ErreurCompilation {
		super(file_name);
		setScan(new ScannerWS(file_name));
	}
	
	public void testInsere(Tokens t, ClassIdf cl, CodesErr cr) throws IOException, ErreurCompilation {
		if(getScan().symb_cour.token==t) {
			((ScannerWS)getScan()).chercherSymb();
			System.out.println(((ScannerWS)getScan()).getPlacesymb());
			if(((ScannerWS)getScan()).getPlacesymb()!=-1)
				throw new ErreurSementique(CodesErr.DBL_DECL_ERR);
			((ScannerWS)getScan()).entrerSymb(cl);
			System.out.println(scan.symb_cour.getToken() + " : " + scan.symb_cour.getName()); 
			getScan().SYMB_SUIV();
		}
		else throw new ErreurSyntaxique(cr);
	}
	
	public void testCherche (Tokens t, CodesErr cr) throws IOException, ErreurCompilation{
		if(getScan().symb_cour.token==t) {
			((ScannerWS)getScan()).chercherSymb();
			int pos = ((ScannerWS)getScan()).getPlacesymb();
			if(pos==-1)
				throw new ErreurSementique(CodesErr.NON_DECL_ERR);
			Symboles s = ((ScannerWS)getScan()).getTablesymb().get(pos);
			if(s.classe==ClassIdf.PROGRAM)
				throw new ErreurSementique(CodesErr.PROG_USE_ERR);
			getScan().SYMB_SUIV();
		}
		else throw new ErreurSyntaxique(cr);
	}
	
	public void program() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
		testInsere(Tokens.ID_TOKEN, ClassIdf.PROGRAM, CodesErr.ID_ERR);
		TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		BLOCK();
		TEST_ACCEPT(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
	}
	
	/*public void block() throws IOException, ErreurCompilation {
		consts();
		vars();
		insts();
	}*/
	
	public void consts() throws IOException, ErreurCompilation {
		switch(getScan().getSymb_cour().getToken()) {
		case CONST_TOKEN:
			// System.out.println(getScan().getSymb_cour().getToken() + " :: " + getScan().getSymb_cour().getToken());
			getScan().SYMB_SUIV();
			testInsere(Tokens.ID_TOKEN,ClassIdf.CONSTANTE,  CodesErr.ID_ERR);
			TEST_ACCEPT(Tokens.EG_TOKEN, CodesErr.EG_ERR);
			TEST_ACCEPT(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
			TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
			while (getScan().getSymb_cour().getToken()==Tokens.ID_TOKEN) {
				testInsere(Tokens.ID_TOKEN,ClassIdf.CONSTANTE,  CodesErr.ID_ERR);
				TEST_ACCEPT(Tokens.EG_TOKEN, CodesErr.EG_ERR);
				TEST_ACCEPT(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
				TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
			}
			break;
		case VAR_TOKEN:
			break;
		case BEGIN_TOKEN:
			break;
		default:
			throw new ErreurSyntaxique(CodesErr.CONST_ERR);
		}
	}

	public void vars() throws IOException, ErreurCompilation {
		switch (getScan().getSymb_cour().getToken()) {
		case VAR_TOKEN:
			getScan().SYMB_SUIV();
			testInsere(Tokens.ID_TOKEN,ClassIdf.VARIABLE,  CodesErr.ID_ERR);
			while (getScan().getSymb_cour().getToken()==Tokens.VIR_TOKEN){
				getScan().SYMB_SUIV();
				testInsere(Tokens.ID_TOKEN,ClassIdf.VARIABLE,  CodesErr.ID_ERR);
			}
			TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
			break;
		case BEGIN_TOKEN:
			//epsilon
			break;
		default:
			throw new ErreurSyntaxique(CodesErr.VAR_ERR);
		}
	}
	
	public void AFFEC() throws ErreurCompilation, IOException {
		System.out.println("i am in affec parserWS");
		testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		int p=((ScannerWS)getScan()).getPlacesymb();
		Symboles s=((ScannerWS)getScan()).getTablesymb().get(p);
		if (s.getClasse()==ClassIdf.CONSTANTE)
			throw new ErreurSementique(CodesErr.CONST_MODIF_ERR);
		
		TEST_ACCEPT(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
		expr();
	}

	public void LIRE() throws ErreurCompilation, IOException {
		TEST_ACCEPT(Tokens.READ_TOKEN, CodesErr.READ_ERR);
		TEST_ACCEPT(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
		testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		
		int p=((ScannerWS)getScan()).getPlacesymb();
		Symboles s=((ScannerWS)getScan()).getTablesymb().get(p);
		if (s.getClasse()==ClassIdf.CONSTANTE)
			throw new ErreurSementique(CodesErr.CONST_MODIF_ERR);
		
		while(getScan().getSymb_cour().getToken()==Tokens.VIR_TOKEN) {
			getScan().SYMB_SUIV();
			testCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			p=((ScannerWS)getScan()).getPlacesymb();
			s=((ScannerWS)getScan()).getTablesymb().get(p);
			if (s.getClasse()==ClassIdf.CONSTANTE)
				throw new ErreurSementique(CodesErr.CONST_MODIF_ERR);
		}
		TEST_ACCEPT(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
	}
	
	public void fact() throws IOException, ErreurCompilation {
		switch(getScan().getSymb_cour().getToken()) {
		case ID_TOKEN:
			testCherche(Tokens.ID_TOKEN,  CodesErr.ID_ERR);
			break;
		case NUM_TOKEN:
			getScan().SYMB_SUIV();
			break;
		case PARG_TOKEN:
			getScan().SYMB_SUIV();
			expr();
			TEST_ACCEPT(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
		default:
			throw new ErreurSyntaxique(CodesErr.FACT_ERR);
		}
	}
	
	public static void main(String [] args) 
			 throws IOException, ErreurCompilation {
		ParserWS parse=new ParserWS("Test22.p");
		parse.getScan().initMotsCles();
		parse.getScan().LIRE_CAR();
		parse.getScan().SYMB_SUIV();
		parse.program();
		if (parse.getScan().getSymb_cour().getToken()==Tokens.EOF_TOKEN) { 
			System.out.println("Analyse semantique reussie");
		}
		else
			throw new ErreurSyntaxique(CodesErr.EOF_ERR);
		/*for(int i =0; i< ((ScannerWS)(parse.getScan())).tablesymb.size();i++)
		 MADE BY( String name == HANIN)
		System.out.println(((ScannerWS)(parse.getScan())).tablesymb.get(i).name);*/
	}

}
