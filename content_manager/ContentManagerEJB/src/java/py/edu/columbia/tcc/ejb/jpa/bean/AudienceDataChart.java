/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.bean;

/**
 *
 * @author tokio
 */
public class AudienceDataChart {
    private String reference;
    private Integer identifier;
    private String description;
    private Integer quantity;

    public AudienceDataChart() {
    }

    public AudienceDataChart(String reference, Integer identifier, String description, Integer quantity) {
        this.reference = reference;
        this.identifier = identifier;
        this.description = description;
        this.quantity = quantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
