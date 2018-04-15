package py.edu.columbia.tcc.controller.view.admin;

import py.edu.columbia.tcc.login.ejb.jpa.UserFacade;
import py.edu.columbia.tcc.login.model.User;
import py.edu.columbia.tcc.controller.session.GDMSession;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.login.common.AESSymetricCrypto;
import py.edu.columbia.tcc.login.ejb.jpa.RoleFacade;
import py.edu.columbia.tcc.login.model.Role;

/**
 *
 * @author tokio
 */
@ViewScoped
@Named
public class AccessManagement implements Serializable {

    private final Logger log = LoggerFactory.getLogger(AccessManagement.class);

    @Inject
    private GDMSession sessionEJB;

    @Inject
    private UserFacade userEJB;
    
    @Inject
    private RoleFacade roleEJB;
    
    @Inject @New
    private User newUser;
    
    @Inject @New
    private Role selectedRole;
    
    @Inject @New
    private Role selectedRoleEdit;
    
    private List<User> listUsers;
    private List<Role> listRoles;
    
    private String comfirmPassword;

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public List<Role> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Role> listRoles) {
        this.listRoles = listRoles;
    }

    public String getComfirmPassword() {
        return comfirmPassword;
    }

    public void setComfirmPassword(String comfirmPassword) {
        this.comfirmPassword = comfirmPassword;
    }

    public Role getSelectedRoleEdit() {
        return selectedRoleEdit;
    }

    public void setSelectedRoleEdit(Role selectedRoleEdit) {
        this.selectedRoleEdit = selectedRoleEdit;
    }

    public AccessManagement(){
    }
    
    @PostConstruct
    public void init() {
        updateRoleList();
    }

    
    // metodos de usuario
    public void createUser(){
        try {
            if(!equalPassword()){
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "Las constraseñas no coinciden");
                return;
            }
            newUser.setIdCompany(sessionEJB.getDefaultCompany());
            newUser.setUserAdd(BigInteger.valueOf(sessionEJB.getUser().getIdUser()));
            newUser.setIdRole(selectedRole);
            newUser.setPassword(AESSymetricCrypto.encriptInSHA512HEX(newUser.getPassword()));
            userEJB.create(newUser);
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "Usuario registrado correctamente!");
            clearUserData();
        } catch (Exception ex) {
            log.info("Error al crear usuario; "+ex.getMessage());
        }
    }
    
    public boolean editUser(User selectedUser){
        try{
            if(selectedRoleEdit != null && selectedRoleEdit.getIdRole() > 0){
                selectedUser.setIdRole(selectedRoleEdit);
            }
            userEJB.edit(selectedUser);
        } catch(Exception ex){
            log.info("Error al editar usuario; "+ex.getMessage());
            return false;
        }
        return true;
    }
    
    public void updateUserList(){
        try{
            listUsers = userEJB.listUsers();
        } catch(Exception ex){
            log.info("Error al actualizar listado de usuarios; "+ex.getMessage());
        }
    }
    
    public void clearUserData(){
        try {
            newUser = new User();
            comfirmPassword = "";
        } catch (Exception ex) {
            log.info("Error al limpiar formulario de usuarios; "+ex.getMessage());
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        User selectedUser = (User) event.getObject();
        if(!editUser(selectedUser)){
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención", "Usuario no pudo ser editado!");
            return;
        }
        MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Edición exitosa", "Usuario editado correctamente!");
    }
     
    public void onRowCancel(RowEditEvent event) {
        MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, " ", "Edición cancelada!");
    }
    
  
    public void updateRoleList(){
        try{
            listRoles = roleEJB.findAll();
        } catch(Exception ex){
            log.info("Error al actualizar listado de roles; "+ex.getMessage());
        }
    }
    
    public boolean equalPassword(){
        return newUser.getPassword().equals(comfirmPassword);
    }
}
