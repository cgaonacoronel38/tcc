package py.edu.columbia.tcc.controller.view.admin;

import py.edu.columbia.tcc.login.ejb.jpa.CompanyFacade;
import py.edu.columbia.tcc.login.model.Company;
import py.edu.columbia.tcc.login.model.Role;
import py.edu.columbia.tcc.login.model.User;
import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.login.ejb.jpa.UserFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.login.ejb.jpa.RoleFacade;

@ManagedBean
@ViewScoped
public class EditUser implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(EditUser.class);

    @Inject
    private CompanyFacade companyEJB;

    @Inject
    private UserFacade userEJB;
    
    @Inject
    private RoleFacade roleEJB;

    private String userName;

    private User selectedUser = new User();
    private List<Company> listCompany;
    private List<User> listUser;
    private List<Role> listRoles;
    private List<Role> selectedRoles;
    private DualListModel<Role> groups;
    
    @PostConstruct
    public void init() {
        try {
            listRoles = roleEJB.findAll();
            selectedRoles = new ArrayList<>();
            
            groups = new DualListModel<>(listRoles, selectedRoles);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EditUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Company> getListCompany() {
        if(listCompany == null){
            listCompany = companyEJB.findAll();
        }
        return listCompany;
    }

    public void setListCompany(List<Company> listCompany) {
        this.listCompany = listCompany;
    }

    public DualListModel<Role> getGroups() {
        return groups;
    }

    public void setGroups(DualListModel<Role> groups) {
        this.groups = groups;
    }

    public List<Role> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Role> listRoles) {
        this.listRoles = listRoles;
    }

    public List<Role> getSelectedRoles() {
        if(selectedRoles == null){
            selectedRoles = new ArrayList<>();
        }
        return selectedRoles;
    }

    public void setSelectedRoles(List<Role> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public List<User> getListUser() {
        if(listUser == null){
            listUsers();
        }
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    public void findUser() {
        try {
            selectedUser = userEJB.findByUsername(userName);
        } catch (Exception ex) {
            log.error("Error al listar usuarios " + ex.getLocalizedMessage());
        }
    }
    
    public void initCreate(){
        selectedUser = new User();
    }

    public void create() {
        /*if(selectedUser != null){
            try {
                selectedUser.setPassword(userEJB.encript(selectedUser.getPassword()));
                selectedUser.setAudadddate(new Date());
                selectedUser.setUserPK(selectedUserPk);
                userEJB.create(selectedUser);
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO,
                    "Inserción exitosa!",
                    "El usuario "+selectedUser.getUsername()+" ha sido creado exitosamente!");
                selectedUser = new User();
                selectedUserPk = new UserPK();
                listUsers();
            } catch (Exception ex) {
                log.error("Error al crear usuario "+ex.getLocalizedMessage());
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                    "Error!",
                    "Ha surgido un error al crear el usuario!");
            } 
        } */
        try{
            System.out.println("Imprimiendo valores de grupos");
            List<Role> lista = groups.getTarget();
            System.out.println("Cantidad de registros: "+lista.size());
            for(Role r : lista){
                System.out.println("Role: "+r.getDescription());
            }
        } catch(Exception e){
            log.error("Error al listar grupos: "+e.getLocalizedMessage());
        }
    }

    public void editUser(User user) {
        try {
            userEJB.edit(user);
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO,
                    "Usuario editado exitosamente!",
                    " ");
        } catch (Exception ex) {
            log.error("Error al editar usuario " + ex.getLocalizedMessage());
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                    "Error al editar estado del usuario!",
                    "Consulte con soporte técnico");
        }
    }

    public void listUsers(){
        try {
            listUser = userEJB.listUsers();
        } catch (Exception ex) {
            log.error("Error al listar suuarios "+ex.getLocalizedMessage());
        }
    }
}
