package br.com.sba.model;

public enum TipoUsuario {
	ADM("Administrador"),
	Leitor("Leitor");

	 private final String tipoUsuario;

    private TipoUsuario(String s) {
        tipoUsuario = s;
    }

    public boolean equalName(String outroTipo)
    {
        return (outroTipo == null)? false : tipoUsuario.equals(outroTipo);
    }

    public String toString()
    {
        return tipoUsuario;
    }
}
