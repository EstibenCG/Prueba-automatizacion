package co.com.AutoFacebook.models;

public class CredencialesInicioSesion {
    private String usuario;
    private String clave;

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }

    public void credencialesInicioSesion(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
}