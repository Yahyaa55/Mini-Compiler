package net.mips.compiler;

public class ErreurSementique extends ErreurCompilation{

	public ErreurSementique(CodesErr code) {
		super(code.getMsg());
	}

}
