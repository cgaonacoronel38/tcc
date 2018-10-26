/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.common;

/**
 *
 * @author tokio
 */
public enum WSRespondeStatus {
    OK      (0, "Registro exitoso"),
    REGISTER_ERROR   (1, "Se ha producido un error en el registro"),
    NOT_FOUND   (404, "No se ha encontrado el recurso solicitado");

    private final Integer statusCode;  
    private final String statusDescription;
    
    WSRespondeStatus(Integer _statusCode, String _statusDescription) {
        this.statusCode = _statusCode;
        this.statusDescription = _statusDescription;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
