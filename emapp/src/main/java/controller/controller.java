
package controller;

import java.io.IOException;
import java.util.Properties;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import static utils.Constants.*;
import model.Employee;
import model.DBActions;
/**
 *
 * @author melaniemarques
 */
public class controller extends HttpServlet {
    DBActions dba;
    InputStream input;
    String dbUrl = "";
    String dbUser = "";
    String dbPwd = "";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        Properties prop = new Properties();
        input = getServletContext().getResourceAsStream(PROP_FILE_PATH);
        prop.load(input);
        
        dbUrl = prop.getProperty("dbUrl");
        dbUser = prop.getProperty("dbUser");
        dbPwd = prop.getProperty("dbPwd");
        dba = new DBActions(dbUrl, dbUser, dbPwd);
        
        
        if (request.getParameter("action") == null) {
            request.getRequestDispatcher("WEB-INF/errorPage.jsp").forward(request, response);
            
        }else{       
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        switch(action){
            case "Login" :                     
                Employee userInput = new Employee();
                userInput.setName(request.getParameter("loginField"));
                userInput.setFirstname(request.getParameter("pwdField"));
            
                if (dba.checkCredentials(userInput)) {
                    request.setAttribute("empList", dba.getEmployees(QUERY_SEL_EMPLOYEES));
                    session.setAttribute("user", userInput);
                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                } else {
                    request.setAttribute("errKey", ERR_MESSAGE);
                    request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
                }
                break;
            case "Delete":
                String id = request.getParameter("check");
                dba.deleteEmployee(id);                
                request.setAttribute("empList", dba.getEmployees(QUERY_SEL_EMPLOYEES));
                request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                break;
            case "Details":
                String idD = request.getParameter("check"); 
                if(idD != null){
                    session.setAttribute("employeeChecked",dba.getEmployeeDetails(idD));              
                    request.getRequestDispatcher(JSP_ADD).forward(request, response);
                }else{
                    request.setAttribute("empList", dba.getEmployees(QUERY_SEL_EMPLOYEES));
                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                }
                break;
            case "Add":
                request.getRequestDispatcher(JSP_ADD).forward(request, response);
                break;
            case "Save":  
                Employee addEmployee = new Employee();
                addEmployee.setName(request.getParameter("inputNom"));
                addEmployee.setFirstname(request.getParameter("inputPrenom"));
                addEmployee.setHomePhone(request.getParameter("inputTelDom"));
                addEmployee.setMobilePhone(request.getParameter("inputTelMob"));
                addEmployee.setWorkPhone(request.getParameter("inputTelPro"));
                addEmployee.setAdress(request.getParameter("inputAdresse"));
                addEmployee.setPostalCode(request.getParameter("inputCodePostal"));
                addEmployee.setCity(request.getParameter("inputVille"));
                addEmployee.setEmail(request.getParameter("inputAdresseMail"));
                
                if(session.getAttribute("employeeChecked")!=null){                  
                    dba.updateEmployee(((Employee) session.getAttribute("employeeChecked")).getId(), addEmployee);
                    session.removeAttribute("employeeChecked");
                }else{                               
                    dba.createEmployee(addEmployee);              
                }
                request.setAttribute("empList", dba.getEmployees(QUERY_SEL_EMPLOYEES)); 
                request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);               
                break;
            case "Cancel":
                if(session.getAttribute("employeeChecked")!=null){                  
                    session.removeAttribute("employeeChecked");
                }
                request.setAttribute("empList", dba.getEmployees(QUERY_SEL_EMPLOYEES));
                request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
            default:
                request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
        }      
        }

        }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
