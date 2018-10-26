/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.ejb.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.login.ejb.jpa.CompanyFacade;
import py.edu.columbia.tcc.login.ejb.jpa.SysParamFacade;
import py.edu.columbia.tcc.login.model.Company;
import py.edu.columbia.tcc.login.model.SysParam;

/**
 *
 * @author tid
 */
@Singleton
@Startup
public class LoginMaps {

    private static final Logger log = LoggerFactory.getLogger(LoginMaps.class);

    @Inject
    private SysParamFacade paramEJB;
    
    @Inject
    private CompanyFacade compEJB;


    //Colecciones que solo se consultan una vez
    private ConcurrentHashMap<String, SysParam> mParams;
    private ConcurrentHashMap<String, Company> mComps;

    public LoginMaps() {
    }

    @PostConstruct
    private void initialized() {
        try {
            loadSysParam();
            loadSysComp();
        } catch (Exception ex) {
            log.error("Error al inicializar los maps de la aplicación.", ex);
        }
    }

    public SysParam findSysParamByKey(String key) throws Exception {
        SysParam sp = null;

        if (mParams != null && !mParams.isEmpty()) {
            sp = mParams.get(key);
        }

        if (sp == null) {
            log.error("El parámetro no existe, clave: {}", key);

            throw new Exception(String.format("El parámetro de sistema no existe,  clave: %s", key));
        }
        return sp;
    }

    private void loadSysParam() throws Exception {
        List<SysParam> l = null;

        try {
            l = paramEJB.listSysParam();
            mParams = new ConcurrentHashMap<>();

            for (SysParam param : l) {
                paramEJB.detached(param);


                mParams.put(param.getKey(),
                        param);
            }

            log.info("Se cargó el Map de Parámetros en DCorS, tamaño: " + mParams.size());
        } catch (Exception ex) {
            throw new Exception("No se pudo crear el mapa con valores de parámetros generales de la aplicación DCorS.", ex);
        }
    }
    
    public void loadSysComp() throws Exception{
        List<Company> l = null;

        try {
            l = compEJB.findAll();
            mComps = new ConcurrentHashMap<>();

            for (Company company : l) {
                compEJB.detached(company);

                mComps.put(company.getIdCompany().toString(),
                        company);
            }

            log.info("Se cargó el Map de Parámetros en DCorS, tamaño: " + mParams.size());
        } catch (Exception ex) {
            throw new Exception("No se pudo crear el mapa con valores de parámetros generales de la aplicación.", ex);
        }
    }

    public Company findCompanyByInitial(String initial) throws Exception {
        Company comp = null;

        //modificado 201610090
        if (mComps != null && !mComps.isEmpty()) {
            comp = mComps.get("1");
        }

        if (comp == null) {
            log.error("El código de la empresa no existe, codigo: {}", initial);

            throw new Exception("Código de empresa no existe, codigo: " + initial);
        }

        return comp;
    }
}
