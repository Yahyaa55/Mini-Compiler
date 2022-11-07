package net.mips.compiler;

public enum CodesErr {
	CAR_INC_ERR ("Symbole inconnu"),
	FIC_VIDE_ERR ("Erreur d'ouverture de fichier"),
	PROGRAM_ERR("Mot clé PROGRAM attendu !"),
	ID_ERR("Identificateur attendu !"),
	PVIR_ERR("Symbole ; attendu !"),
	NUM_ERR("Numero attendu !"),
	PLUS_ERR("Symbole + attendu !"),
	MOINS_ERR("Symbole - attendu !"),
	MUL_ERR("Symbole * attendu !"),
	DIV_ERR("Symbole / attendu !"),
	EG_ERR("Symbole == attendu !"),
	DIFF_ERR("Symbole != attendu !"),
	INF_ERR("Symbole < attendu !"),
	SUP_ERR("Symbole > attendu !"),
	INFEG_ERR("Symbole <= attendu !"),
	SUPEG_ERR("Symbole >= attendu !"),
	PARG_ERR("Symbole ( attendu !"),
	PARD_ERR("Symbole ) attendu !"),
	VIR_ERR("Symbole , attendu !"),
	PNT_ERR("Symbole . attendu !"),
	AFFEC_ERR("Symbole = attendu !"),
	BEGIN_ERR("Mot clé BEGIN attendu !"),
	END_ERR("Mot clé END attendu !"),
	IF_ERR("Mot clé IF attendu !"),
	WHILE_ERR("Mot clé WHILE attendu !"),
	THEN_ERR("Mot clé THEN attendu !"),
	DO_ERR("Mot clé DO attendu !"),
	WRITE_ERR("Mot clé WRITE attendu !"),
	READ_ERR("Mot clé READ attendu !"),
	CONST_ERR("Mot clé CONST attendu !"),
	VAR_ERR("Mot clé VAR attendu !");
	private String msg;

	private CodesErr(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
