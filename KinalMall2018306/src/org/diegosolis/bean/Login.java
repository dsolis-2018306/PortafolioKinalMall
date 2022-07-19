
package org.diegosolis.bean;


public class Login {
    private String usuarioMaster;
    private String passwordLog;

    public Login() {
    }
    
    public Login(String usuarioMaster, String passwordLog) {
        this.usuarioMaster = usuarioMaster;
        this.passwordLog = passwordLog;
    }

    public String getUsuarioMaster() {
        return usuarioMaster;
    }

    public void setUsuarioMaster(String usuarioMaster) {
        this.usuarioMaster = usuarioMaster;
    }

    public String getPasswordLog() {
        return passwordLog;
    }

    public void setPasswordLog(String passwordLog) {
        this.passwordLog = passwordLog;
    }
    
}
