
package org.diegosolis.bean;


public class Clientes {
    private int codigoCliente;
    private String nombresClientes;
    private String apellidosClientes;
    private String telefonoCliente;
    private String direccionCliente;
    private String emailCliente;
    private int codigoLocal;
    private int codigoAdministracion;
    private int codigoTipoCliente;

    public Clientes() {
    }

    public Clientes(int codigoCliente, String nombresCliente, String apellidosClientes, String telefonoCliente, String direccionCliente, String emailCliente, int codigoLocal, int codigoAdministracion, int codigoTipoCliente) {
        this.codigoCliente = codigoCliente;
        this.nombresClientes = nombresCliente;
        this.apellidosClientes = apellidosClientes;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.emailCliente = emailCliente;
        this.codigoLocal = codigoLocal;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombresCliente() {
        return nombresClientes;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresClientes = nombresCliente;
    }

    public String getApellidosClientes() {
        return apellidosClientes;
    }

    public void setApellidosClientes(String apellidosClientes) {
        this.apellidosClientes = apellidosClientes;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }
    public String toString(){
        return getCodigoCliente()+ " | " + getNombresCliente()+ " | " + getApellidosClientes();
    }
}
