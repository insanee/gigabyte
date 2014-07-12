/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.validators;

import by.insane.gigabyte.DataBaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Андрій
 */
@FacesValidator("validators.LoginValidator")
public class LoginValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String login = value.toString();

        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes().get("password");
        String password = uiInputConfirmPassword.getSubmittedValue()
                .toString();

        if (login == null || login.isEmpty() || password == null
                || password.isEmpty()) {
            return;
        }

        Statement statement = DataBaseConnection.getInstance().getStatement();
        boolean find = false;
        try {
            ResultSet result = statement.executeQuery("SELECT account_id, login, role, password FROM account");
            System.out.println("login: " + login + "  passowrd: " + password);

            while (result.next()) {

                if (result.getString("login").equals(login) && result.getString("password").equals(password)) {
                    find = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!find) {
            throw new ValidatorException(new FacesMessage(
                    "Invalid login."));
        }
    }

}
