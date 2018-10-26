/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import javax.ejb.Stateless;
import py.edu.columbia.tcc.model.chartData.ChartPanel;
import py.edu.columbia.tcc.model.chartData.FilteredDevice;

/**
 *
 * @author tid
 */
@Stateless
public class FilteredDeviceFacade extends AbstractFacade<FilteredDevice> {
    
    public FilteredDeviceFacade(){
        super(FilteredDevice.class);
    }
}