package fr.stage.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.dao.ComputerDAO;
import fr.stage.dao.ComputerDAOPaginationFilter;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    private static int LIMIT_PER_PAGE_DEF = 10;

    private static final long serialVersionUID = 1L;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
	super();
	logger.debug("init Dashboard");
    }

    private ComputerDAOPaginationFilter readRequest(HttpServletRequest request) {
	String nameFilterParam = request.getParameter("search");
	String pageParam = request.getParameter("page");
	int page;
	String nameFilter;
	if (nameFilterParam != null) {
	    nameFilter = nameFilterParam;
	}
	else {
	    nameFilter = "";
	}
	try {
	    page = Integer.parseInt(pageParam);
	}
	catch (NullPointerException | NumberFormatException e) {
	    page = 1;
	}
	ComputerDAOPaginationFilter computerDAOPaginationFilter = new ComputerDAOPaginationFilter(
		nameFilter, LIMIT_PER_PAGE_DEF, 0);
	int backPage = page - 1;
	if (backPage >= computerDAOPaginationFilter.getMaxPages()
		|| backPage < 0)
	    backPage = 0;
	computerDAOPaginationFilter.goTo(backPage);
	return computerDAOPaginationFilter;
    }

    private void setRequest(HttpServletRequest request,
	    ComputerDAOPaginationFilter computerDAOPaginationFilter) {
	request.setAttribute("computerDAOPaginationFilter",
		computerDAOPaginationFilter);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	ComputerDAOPaginationFilter dao = readRequest(request);
	setRequest(request, dao);
	this.getServletContext()
		.getRequestDispatcher(ServletUtils.PAGE_URI + "dashboard.jsp")
		.forward(request, response);
    }

    private void processDelete(HttpServletRequest request) {
	try {
	    int computerToDelete = Integer.parseInt(request
		    .getParameter("computerToDelete"));
	    ComputerDAO.getInstance().delete(computerToDelete);
	}
	catch (NullPointerException | NumberFormatException | SQLException e) {

	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	processDelete(request);
	ComputerDAOPaginationFilter dao = readRequest(request);
	setRequest(request, dao);
	this.getServletContext()
		.getRequestDispatcher(ServletUtils.PAGE_URI + "dashboard.jsp")
		.forward(request, response);
    }
}
