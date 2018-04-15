/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.ejb.jpa;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.login.common.AESSymetricCrypto;
import py.edu.columbia.tcc.login.model.User;

@Stateless
public class UserFacade extends AbstractFacade<User> {

    public UserFacade() {
        super(User.class);
    }

    public void updateLastSignInNATIVE(int idcompany, int idsysuser) throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE system_handler.user SET last_sign_in = CURRENT_TIMESTAMP ");
            sql.append(" WHERE id_user = ?1 ");
            sql.append("   AND id_company = ?2");

            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, idsysuser);
            q.setParameter(2, idcompany);

            if (q.executeUpdate() == 0) {
                throw new Exception("No se actualizó la fecha de acceso al sistema.");
            }
        } catch (Exception ex) {
            throw new Exception("Error al actualizar fecha de acceso de usuario, idsysuser: " + idsysuser, ex);
        }
    }

    public User findByUsername(String username) throws Exception {
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT su ");
            jpql.append("  FROM User su ");
            jpql.append(" WHERE su.userName = ?1 ");

            Query q = getEntityManager().createQuery(jpql.toString());
            q.setParameter(1, username);

            List l = q.getResultList();

            if (l.isEmpty()) {
                return null;
            } else {
                return (User) l.get(0);
            }
        } catch (Exception ex) {
            throw new Exception("Error al buscar el usuario por nombre, username: " + username, ex);
        }
    }

    public boolean validPassword(String usernameToChange, String newPassword) throws Exception {
        String pwdEncript = null;

        try {
            //Encriptación valida para WildFly jdbcrealm
            pwdEncript = AESSymetricCrypto.encriptInSHA512HEX2(newPassword);

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id_user ");
            sql.append(" FROM system_handler.user ");
            sql.append(" WHERE user_name = ?1 ");
            sql.append(" AND password = ?2");

            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, usernameToChange);
            q.setParameter(2, pwdEncript);

            List l = q.getResultList();

            if (l.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            throw new Exception("Error al validar la actual contraseña, username: " + usernameToChange, ex);
        }
    }

    /**
     * Cambiar contraseña.
     *
     * @param sysuser Usuario quien realiza el cambio de contraseña.
     * @param usernameToChange Username del cual se va a cambiar el contraseña.
     * @param newPassword Nueva contraseña.
     *
     * @throws Exception
     */
    public void changePassword(User sysuser, String usernameToChange, String newPassword) throws Exception {
        changePassword(sysuser.getIdUser(),
                usernameToChange,
                newPassword);
    }

    /**
     * Cambiar contraseña.
     *
     * @param idsysuser Identificador del usuario que realiza el cambio de
     * contraseña
     * @param usernameToChange Username del cual se va a cambiar el contraseña.
     * @param newPassword Nueva contraseña.
     *
     * @throws Exception
     */
    public void changePassword(int idsysuser, String usernameToChange, String newPassword) throws Exception {
        String pwdEncript = null;

        try {
            //Encriptación valida para WildFly jdbcrealm
            pwdEncript = AESSymetricCrypto.encriptInSHA512HEX2(newPassword);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE system_handler.user SET password = ?1, ");
            sql.append(" WHERE user_name = ?2");

            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, pwdEncript);
            q.setParameter(2, usernameToChange);

            if (q.executeUpdate() == 0) {
                throw new Exception("No se encontró el usuario para actualizar su contraseña.");
            }
        } catch (Exception ex) {
            throw new Exception("Error al intentar cambiar la contraseña, quien cambia: " + idsysuser + " , username cambiado: " + usernameToChange, ex);
        }
    }
    
    public List<User> listUsers() throws Exception {
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT su ");
            jpql.append("  FROM User su ");

            Query q = getEntityManager().createQuery(jpql.toString());

            return q.getResultList();
        } catch (Exception ex) {
            throw new Exception("Error al buscar el usuario por nombre, username: " + ex.getLocalizedMessage());
        }
    }
}
