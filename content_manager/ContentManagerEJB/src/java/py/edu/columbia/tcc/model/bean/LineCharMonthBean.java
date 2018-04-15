/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.bean;

/**
 *
 * @author tokio
 */
public class LineCharMonthBean {
    private String device;
    private String month;
    private Long quantity;

    public LineCharMonthBean() {
    }

    public LineCharMonthBean(String device, String month, Long quantity) {
        this.device = device;
        this.month = month;
        this.quantity = quantity;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
