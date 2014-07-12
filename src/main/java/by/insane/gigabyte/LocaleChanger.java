/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gigabyte;

import javax.enterprise.context.*;
import java.io.*;
import java.util.Locale;
import javax.inject.Named;
import javax.faces.context.*;

/**
 *
 * @author insane
 */
@Named
@SessionScoped
public class LocaleChanger implements Serializable{
    public String ukrainianAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("UA"));
        return null;
    }
    public String englishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        return null;
    }
}
