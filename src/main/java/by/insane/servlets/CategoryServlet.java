/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.servlets;

import by.insane.gigabyte.orm.Category;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Andriy
 */
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();
        System.out.println("Path category: " + path);
        String forwardURL = "/faces/default.xhtml";
        if (path != null) {
            String[] parseCategory = path.split("/");
            String cidID = "";
            String gidID = "";
            boolean search = false;

            for (int i = 0; i < parseCategory.length; i++) {
                if (parseCategory[i].equals("cid")) {
                    cidID = parseCategory[i + 1];
                } else if (parseCategory[i].equals("gid")) {
                    gidID = parseCategory[i + 1];
                }else if (parseCategory[i].equals("search")){
                    search = true;
                    break;
                }
            }

            if (!gidID.equals("")) {
                System.out.println("__________________________________________Gid: " + gidID);
                try {
                    Integer.parseInt(gidID);

                    request.setAttribute("productId", gidID);
                    System.out.println("__________________________________________Gid22: " + gidID);
                    forwardURL = "/faces/product.xhtml?productId=" + gidID;
                } catch (Exception e) {
                }
            } else if(!cidID.equals("")){
                try {
                    Integer.parseInt(cidID);
                    request.setAttribute("categoryId", cidID);
                    forwardURL = "/faces/category.xhtml";
                } catch (Exception e) {
                }
            }else if(search){
                String str = request.getParameter("q");
                request.setAttribute("findText", str);
                forwardURL = "/faces/search.xhtml";
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
