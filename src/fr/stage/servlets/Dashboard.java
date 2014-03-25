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

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    private static int LIMIT_PER_PAGE = 10;

    private static final long serialVersionUID = 1L;

    private List<Computer> computersList;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ComputerDAOPagination computerDAOPagination;

    private String conditionPagination;

    private String nameFilter;

    private int page;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
	super();
	computerDAOPagination = null;
	conditionPagination = "";
    }

    public void filterByName(String name) {
	conditionPagination = "WHERE cr.name REGEXP \"^" + name + ".*\" ";
    }

    public void findAllComputer() {
	conditionPagination = "";
    }

    public void goTo(int page) {
	computersList = computerDAOPagination.goTo(page);
    }

    private void readRequest(HttpServletRequest request) {
	String nameFilterParam = request.getParameter("search");
	String pageParam = request.getParameter("page");
	nameFilter = nameFilterParam;
	if (nameFilterParam != null) {
	    nameFilter = nameFilterParam;
	}
	else {
	    nameFilter = (String) (request.getAttribute("searchName"));
	}
	if (pageParam != null) {
	    page = Integer.parseInt(pageParam);
	}
	else {
	    page = 0;
	}
    }

    private void setRequest(HttpServletRequest request) {
	int maxPage = computerDAOPagination.getMaxPage();
	request.setAttribute("maxPages", maxPage - 1);
	request.setAttribute("computersList", computersList);
	request.setAttribute("nComputerFound",
		computerDAOPagination.getnComputers());
    }

    private void process() {
	StringBuilder conditionPaginationTMP = new StringBuilder();
	if (nameFilter != null) {
	    conditionPaginationTMP.append("WHERE cr.name REGEXP \"^");
	    conditionPaginationTMP.append(nameFilter);
	    conditionPaginationTMP.append(".*\" ");
	}
	conditionPagination = conditionPaginationTMP.toString();
	computerDAOPagination = new ComputerDAOPagination(LIMIT_PER_PAGE, 0,
		conditionPagination);
	computersList = computerDAOPagination.goTo(page);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	readRequest(request);
	process();
	setRequest(request);
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
