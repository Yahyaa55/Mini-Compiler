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
		
	}
	
	public void program() throws IOException, ErreurCompilation {
		TEST_ACCEPT(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
		TEST_ACCEPT(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		TEST_ACCEPT(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		BLOCK();
		TEST_ACCEPT(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
	}
}