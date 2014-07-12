/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gigabyte;

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
@FacesValidator("registerFormValidator")
public class RegisterFormValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        //String password = value.toString();
        UIInput uiInputConfirmPassword = (UIInput) uic.getAttributes()
                .get("passowrd");
        String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
                .toString();
        System.out.println(confirmPassword + "  confirmPasswordconfirmPasswordconfirmPasswordconfirmPassword");
    }

}
