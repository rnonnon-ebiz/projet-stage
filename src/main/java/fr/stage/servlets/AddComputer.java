package fr.stage.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.stage.dao.CompanyDAO;
import fr.stage.dao.ComputerDAO;
import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Computer;
import fr.stage.utils.DateUtils;
import fr.stage.utils.ServletUtils;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private List<Company> companiesList;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private ComputerDAO computerDAO;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
	super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		config.getServletContext());
	initServ();
    }

    private void initServ() {
	try {
	    companiesList = companyDAO.findAll();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    protected Computer fillComputer(HttpServletRequest request) {
	logger.debug("Fill Computer");
	Computer computer = new Computer();
	String name = request.getParameter("computerName");
	String introducedDate = request.getParameter("introduced");
	String discontinuedDate = request.getParameter("discontinued");
	computer.setName(name);
	if (!"".equals(introducedDate)) {
	    try {
		Date introduced = DateUtils.stringToDate(introducedDate);
		computer.setIntroducedDate(introduced);
	    }
	    catch (Exception e) {
	    }
	}
	if (!"".equals(discontinuedDate)) {
	    try {
		Date discontinued = DateUtils.stringToDate(discontinuedDate);
		computer.setDiscontinuedDate(discontinued);
	    }
	    catch (Exception e) {
	    }
	}
	Company c = null;
	try {
	    Long companyId = Long.parseLong(request.getParameter("company"));
	    c = new Company();
	    c.setId(companyId);
	}
	catch (NumberFormatException e) {

	}
	try {
	    Long computerId = Long
		    .parseLong(request.getParameter("computerId"));
	    computer.setId(computerId);
	}
	catch (NumberFormatException e) {

	}
	computer.setCompany(c);
	logger.info(computer.toString());
	return computer;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	logger.info("HERE   ");
	logger.info("Do GET");
	Computer computer = (Computer) request.getAttribute("computer");
	if (computer == null) {
	    computer = new Computer();
	}
	else {
	    logger.info("Update : " + computer.toString());
	}
	request.setAttribute("computer", computer);
	request.setAttribute("companiesList", companiesList);
	logger.debug("End Do GET");
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
	Computer computer = fillComputer(request);
	if (computer.getId() == 0) {
	    computerDAO.create(computer);
	}
	else {
	    computerDAO.update(computer);
	}
	response.sendRedirect("dashboard");
    }

}