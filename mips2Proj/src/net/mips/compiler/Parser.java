package net.mips.compiler;

import java.io.IOException;

public class Parser {
	Scanner scan;

	public Scanner getScan() {
		return scan;
	}

	public void setScan(Scanner scan) {
		this.scan = scan;
	}

	public Parser(String file_name) throws IOException, ErreurCompilation {
		scan = new Scanner(file_name);
	}
	public void TEST_ACCEPT(Tokens t,CodesErr c) throws IOException, ErreurCompilation {
		if(scan.symb_cour.token == t)
			scan.SYMB_SUIV();
		else
			throw new ErreurSyntaxique(c);
	}
	
	public void consts() throws IOException, ErreurCompilation {
		do {
			TEST_ACCEPT(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
			TEST_ACCEPT(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			TEST_ACCEPT(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
			TEST_ACCEPT(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
			TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		}while(scan.symb_cour.token==Tokens.ID_TOKEN);
	}
	
	public void vars() throws IOException, ErreurCompilation {
		do {
			if(scan.symb_cour.token==Tokens.VIR_TOKEN)
				TEST_ACCEPT(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
			TEST_ACCEPT(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
			TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		}while(scan.symb_cour.token==Tokens.VIR_TOKEN);
	}
	
	public void insts() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.BEGIN_TOKEN, CodesErr.BEGIN_ERR);
		//inst();
		TEST_ACCEPT(Tokens.END_TOKEN, CodesErr.END_ERR);
	}
	
	public void BLOCK() throws IOException, ErreurCompilation {
		consts();
		vars();
		insts();
	}
	
	public void AFFEC() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		TEST_ACCEPT(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
		expr();
	}
	
	public void fact() throws IOException, ErreurCompilation {
		switch (scan.symb_cour.token) {
		case ID_TOKEN :
			TEST_ACCEPT(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			break;
		case NUM_TOKEN :
			TEST_ACCEPT(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
			break;
		default:
			TEST_ACCEPT(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
			expr();
			TEST_ACCEPT(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
			break;
		}
	}
	
	public void cond() throws IOException, ErreurCompilation {
		expr();
		relop();
		expr();
	}
	
	public void LIRE() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.READ_TOKEN, CodesErr.READ_ERR);
		TEST_ACCEPT(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
		TEST_ACCEPT(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		while (scan.symb_cour.token==Tokens.VIR_TOKEN) {
			TEST_ACCEPT(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
			TEST_ACCEPT(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		}
		TEST_ACCEPT(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
		TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
	}
	public void ECRIRE() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
		TEST_ACCEPT(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
		expr();
		while (scan.symb_cour.token==Tokens.VIR_TOKEN) {
			TEST_ACCEPT(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
			expr();
		}
		TEST_ACCEPT(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
		TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
	}
	
	public void TANTQUE() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.WHILE_TOKEN, CodesErr.WHILE_ERR);
		cond();
		TEST_ACCEPT(Tokens.DO_TOKEN, CodesErr.DO_ERR);
		//inst();
	}
	
	public void SI() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.IF_TOKEN, CodesErr.IF_ERR);
		cond();
		TEST_ACCEPT(Tokens.THEN_TOKEN, CodesErr.THEN_ERR);
		//inst();
	}
	public void inst() throws IOException, ErreurCompilation {
		switch (scan.symb_cour.token) {
		case BEGIN_TOKEN :
			insts();
			break;
		case ID_TOKEN :
			AFFEC();
			break;
		case IF_TOKEN :
			SI();
			break;
		case WHILE_TOKEN :
			TANTQUE();
			break;
		case WRITE_TOKEN :
			ECRIRE();
			break;
		case READ_TOKEN :
			LIRE();
			break;
		default:
			throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
		}
	}
	
	public void mulop() throws IOException, ErreurCompilation {
		switch (scan.symb_cour.token) {
		case MUL_TOKEN :
			TEST_ACCEPT(Tokens.MUL_TOKEN, CodesErr.MUL_ERR);
			break;
		case DIV_TOKEN :
			TEST_ACCEPT(Tokens.DIV_TOKEN, CodesErr.DIV_ERR);
			break;
		default:
			break;
		}
	}
	
	public void addop() throws IOException, ErreurCompilation {
		switch (scan.symb_cour.token) {
		case PLUS_TOKEN :
			TEST_ACCEPT(Tokens.PLUS_TOKEN, CodesErr.PLUS_ERR);
			break;
		case MOINS_TOKEN :
			TEST_ACCEPT(Tokens.MOINS_TOKEN, CodesErr.MOINS_ERR);
			break;
		default:
			break;
		}
	}
	
	public void relop() throws IOException, ErreurCompilation {
		switch (scan.symb_cour.token) {
		case EG_TOKEN :
			TEST_ACCEPT(Tokens.EG_TOKEN, CodesErr.EG_ERR);
			break;
		case DIFF_TOKEN :
			TEST_ACCEPT(Tokens.DIFF_TOKEN, CodesErr.DIFF_ERR);
			break;
		case INF_TOKEN :
			TEST_ACCEPT(Tokens.INF_TOKEN, CodesErr.INF_ERR);
			break;
		case SUP_TOKEN :
			TEST_ACCEPT(Tokens.SUP_TOKEN, CodesErr.SUP_ERR);
			break;
		case INFEG_TOKEN :
			TEST_ACCEPT(Tokens.INFEG_TOKEN, CodesErr.INFEG_ERR);
			break;
		case SUPEG_TOKEN :
			TEST_ACCEPT(Tokens.SUPEG_TOKEN, CodesErr.SUPEG_ERR);
			break;
		default:
			break;
		}
	}
	
	public void term() throws IOException, ErreurCompilation {
		fact();
		while(scan.symb_cour.token==Tokens.DIV_TOKEN || scan.symb_cour.token==Tokens.MUL_TOKEN) {
			mulop();
			fact();
		}
	}
	
	public void expr() throws IOException, ErreurCompilation {
		term();
		while(scan.symb_cour.token==Tokens.MOINS_TOKEN || scan.symb_cour.token==Tokens.PLUS_TOKEN) {
			addop();
			term();
		}
	}
	
	public void program() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
		TEST_ACCEPT(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		BLOCK();
		TEST_ACCEPT(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
	}
	
	public static void main(String args[]) throws IOException,ErreurCompilation{
		Parser par = new Parser("C:\\Users\\yahya\\git\\test11.p");
		par.scan.initMotsCles();
		par.scan.LIRE_CAR();
		while(par.scan.getCarCour()!=Scanner.EOF) {
			par.scan.SYMB_SUIV();
			System.out.println(par.scan.getSymb_cour().getToken());
		}
		if(par.scan.getCarCour()==Scanner.EOF)
			par.scan.SYMB_SUIV();
			System.out.println(par.scan.getSymb_cour().getToken());
	}
}