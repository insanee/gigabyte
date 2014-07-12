/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.insane.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Андрій
 */
@FacesValidator("validators.PhoneValidator")
public class PhoneValidator implements Validator{
 private static final String PHONE_PATTERN = "^[0-9]+$";

    private Pattern pattern;
    private Matcher matcher;

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("Phone validation failed.",
                            "You stupid idiot...");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).setAttribute("path", "create");
            throw new ValidatorException(msg);

        }
    }
    
}
