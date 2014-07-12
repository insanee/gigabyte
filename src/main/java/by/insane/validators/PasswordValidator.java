/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.validators;

/**
 *
 * @author Андрій
 */
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validators.passwordValidator")
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String password = value.toString();

        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes().get("conffirmPassword");
        String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
                .toString();

        if (password == null || password.isEmpty() || confirmPassword == null
                || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException(new FacesMessage(
                    "Password must match confirm password."));
        }
    }

    private UIComponent findComponent(String id, UIComponent where) {
        if (where == null) {
            return null;
        } else if (where.getId().equals(id)) {
            return where;
        } else {
            List<UIComponent> childrenList = where.getChildren();
            if (childrenList == null || childrenList.isEmpty()) {
                return null;
            }
            for (UIComponent child : childrenList) {
                UIComponent result = null;
                result = findComponent(id, child);
                if (result != null) {
                    return result;
                }
                return null;
            }

        }
        return null;
    }

}
