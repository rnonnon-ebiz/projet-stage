package fr.stage.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.dao.ComputerDAO;
import fr.stage.dao.ComputerDAOPagination;
import fr.stage.domainClasses.Computer;
import fr.stage.service.ServiceDAO;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    private static int LIMIT_PER_PAGE = 10;

    private static final long serialVersionUID = 1L;

    private List<Computer> computersList;

    private int nComputerFound;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ComputerDAOPagination computerDAOPagination;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
	super();
	nComputerFound = ServiceDAO.getComputerDAOInstance().count();
	computerDAOPagination = new ComputerDAOPagination(LIMIT_PER_PAGE);
    }

    public void filterByName(String name) {
	String condition = "WHERE cr.name REGEXP \"^" + name + ".*\"";
	try {
	    computersList = ServiceDAO.getComputerDAOInstance().findAll(
		    condition);
	    logger.info(computersList.size() + " computers found");
	}
	catch (SQLException e) {
	    computersList = null;
	    logger.error("ERROR DASHBOARD ", e);
	    e.printStackTrace();
	}
    }

    public void findAllComputer() {
	computersList = computerDAOPagination.findAll();
	logger.info(computersList.size() + " computers found");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	String nameFilter = request.getParameter("search");
	logger.info(nameFilter);
	if (nameFilter != null) {
	    filterByName(nameFilter);
	}
	else {
	    findAllComputer();
	}
	request.setAttribute("computersList", computersList);
	request.setAttribute("nComputerFound", nComputerFound);
	// Computer cp = new Computer();
	// System.out.println(cp);
	this.getServletContext()
		.getRequestDispatcher(ServletUtils.PAGE_URI + "dashboard.jsp")
		.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	int computerId = Integer.parseInt(request
		.getParameter("computerToDelete"));
	try {
	    ComputerDAO.getInstance().delete(computerId);
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	response.sendRedirect("dashboard");
    }

}
