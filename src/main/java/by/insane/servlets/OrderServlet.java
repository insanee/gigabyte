/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Андрій
 */
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        
        if (path == null) {
            request.setAttribute("showOrders", "true");
        } else {
            request.setAttribute("showOrders", "false");
            int id = 0;
            try{
            id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1, path.length()));
            }catch(NumberFormatException e){
                
            }
            System.out.println("Path: " + path.substring(path.lastIndexOf("/") + 1, path.length()) + " " + path);
            request.setAttribute("orderId", id);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/faces/orders.xhtml");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
