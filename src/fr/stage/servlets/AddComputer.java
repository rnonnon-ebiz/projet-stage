package fr.stage.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.dao.CompanyDAO;
import fr.stage.dao.ComputerDAO;
import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Computer;
import fr.stage.utils.DateUtils;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Computer computer;

    private List<Company> companiesList;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
	super();
	initServ();

    }

    private void initServ() {
	computer = new Computer();
	try {
	    companiesList = CompanyDAO.getInstance().findAll();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    protected void fillComputer(HttpServletRequest request) {
	String name = request.getParameter("name");
	String introducedDate = request.getParameter("introduced");
	String discontinuedDate = request.getParameter("discontinued");
	int companyId = Integer.parseInt(request.getParameter("company"));

	computer.setName(name);
	if (!introducedDate.equals("")) {
	    try {
		Date introduced = DateUtils.stringToDate(introducedDate);
		computer.setIntroducedDate(introduced);
	    }
	    catch (Exception e) {
	    }
	}
	if (!discontinuedDate.equals("")) {
	    try {
		Date discontinued = DateUtils.stringToDate(discontinuedDate);
		computer.setDiscontinuedDate(discontinued);
	    }
	    catch (Exception e) {
	    }
	}
	Company c = new Company();
	c.setId(companyId);
	computer.setCompany(c);
	logger.info(computer.toString());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	request.setAttribute("companiesList", companiesList);
	this.getServletContext()
		.getRequestDispatcher(ServletUtils.PAGE_URI + "addComputer.jsp")
		.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	request.setAttribute("companiesList", companiesList);
	fillComputer(request);
	ComputerDAO.getInstance().create(computer);
	response.sendRedirect("dashboard");
    }

}
