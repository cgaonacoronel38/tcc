/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.setup;

/**
 *
 * @author tid
 */
public class AppSetup {
    private String puMaster;
    private String puSlave;
    private boolean master;
    
    /**
     * Unidad de persistencia Master
     * 
     * @return 
     */
    public String getPuMaster() {
        return puMaster;
    }

    public void setPuMaster(String puMaster) {
        this.puMaster = puMaster;
    }

    /**
     * Unidad de persistencia Esclava
     * 
     * @return 
     */
    public String getPuSlave() {
        return puSlave;
    }

    public void setPuSlave(String puSlave) {
        this.puSlave = puSlave;
    }

    /**
     * Indica si el servidor o el app esta corriendo como master.
     * 
     * @return 
     */
    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n\nConfiguración General\n");
        sb.append("=============================\n");
        sb.append(" - Unidad de persistencia Master: ").append(puMaster).append("\n");
        sb.append(" - Unidad de persistencia Esclavo: ").append(puSlave).append("\n");
        sb.append(" - Servidor master: ").append(master).append("\n");
        
        return sb.toString();
    }
}
