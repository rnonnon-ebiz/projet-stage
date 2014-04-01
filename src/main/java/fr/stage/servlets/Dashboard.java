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

import fr.stage.dao.CompanyDAO;
import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Computer;
import fr.stage.domainClasses.Page;
import fr.stage.service.FactoryDAO;
import fr.stage.utils.ServletUtils;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    private static int LIMIT_PER_PAGE_DEF = 10;

    private static final long serialVersionUID = 1L;

    private List<Company> companiesList;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
	super();
	initServ();
    }

    private void initServ() {
	try {
	    companiesList = CompanyDAO.getInstance().findAll();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    private Page readRequest(HttpServletRequest request) {
	Page page = new Page();
	page.setComputerPerPage(LIMIT_PER_PAGE_DEF);
	String nameFilterParam = request.getParameter("search");
	String pageParam = request.getParameter("page");
	if (nameFilterParam != null) {
	    page.setNameFilter(nameFilterParam);
	}
	// compute TOTAL Res + max Pages
	int total = FactoryDAO.getComputerDAOInstance().count(
		page.getNameFilter());
	page.setTotalRes(total);
	page.computeMaxPages();

	Byte orderBy = 0;
	try {
	    orderBy = Byte.parseByte(request.getParameter("orderBy"));
	}
	catch (NumberFormatException e) {
	    logger.error("orderBy Parse Error");
	}
	page.setOrderBy(orderBy);
	// Go To
	int goTo = 1;
	try {
	    goTo = Integer.parseInt(pageParam);
	}
	catch (NullPointerException | NumberFormatException e) {
	    logger.error("goTo Parse Error");
	}
	page.setCurrentPage(goTo);

	List<Computer> computersList = (List<Computer>) FactoryDAO
		.getComputerDAOInstance().find(page);
	page.setComputersList(computersList);

	return page;
    }

    private void setRequest(HttpServletRequest request, Page page) {
	request.setAttribute("page", page);
	request.setAttribute("companiesList", companiesList);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	Page page = readRequest(request);
	setRequest(request, page);
	this.getServletContext()
		.getRequestDispatcher(ServletUtils.PAGE_URI + "dashboard.jsp")
		.forward(request, response);
    }

    private void processDelete(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	logger.info("processDelete");
	String computerToDelete = request.getParameter("computerToDelete");
	try {
	    long id = Long.parseLong(computerToDelete);
	    FactoryDAO.getComputerDAOInstance().delete(id);
	    Page page = readRequest(request);
	    setRequest(request, page);
	    this.getServletContext()
		    .getRequestDispatcher(
			    ServletUtils.PAGE_URI + "dashboard.jsp")
		    .forward(request, response);
	}
	catch (NullPointerException | NumberFormatException e) {

	}
    }

    private void processUpdate(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	logger.info("processUpdate");
	request.setAttribute("companiesList", companiesList);
	String computerToUpdate = request.getParameter("computerToUpdate");
	try {
	    long id = Long.parseLong(computerToUpdate);
	    Computer computer = FactoryDAO.getComputerDAOInstance().find(id);
	    request.setAttribute("computer", computer);
	    // logger.info("computerToUpdate :  " + computer);
	    this.getServletContext()
		    .getRequestDispatcher(
			    ServletUtils.PAGE_URI + "addComputer.jsp")
		    .forward(request, response);
	}
	catch (NullPointerException | NumberFormatException e) {

	}
    }

    private void readPostRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	processDelete(request, response);
	processUpdate(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	readPostRequest(request, response);
    }
}
