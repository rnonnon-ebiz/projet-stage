package fr.stage.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.stage.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/deleteComputer")
public class DeleteComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
	super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.sendRedirect("dashboard");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// Read id
	String id = request.getParameter("id");
	String regex = "^\\d\\d*$";
	// Valid it
	if (id.matches(regex)) {
	    long idLong = Integer.parseInt(id);
	    // Search for computer with that id
	    if (computerService.delete(idLong)) {
		// If 1 row has been deleted
		response.sendRedirect("dashboard?DeleteStatus=success");
	    }
	    else {
		response.sendRedirect("dashboard?DeleteStatus=error");
	    }
	}
    }

}
