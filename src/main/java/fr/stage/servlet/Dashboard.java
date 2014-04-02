package fr.stage.servlet;

import java.io.IOException;
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

import fr.stage.domain.Company;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.service.CompanyService;
import fr.stage.service.ComputerService;
import fr.stage.util.ServletUtils;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    private static int LIMIT_PER_PAGE_DEF = 10;

    private static final long serialVersionUID = 1L;

    private List<Company> companiesList;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CompanyService companyService;

    @Autowired
    ComputerService computerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		config.getServletContext());
	initServ();
    }

    public Dashboard() {
	super();
    }

    private void initServ() {
	companiesList = companyService.findAll();
    }

    private Page readRequest(HttpServletRequest request) {
	// initServ();
	Page page = new Page();
	page.setComputerPerPage(LIMIT_PER_PAGE_DEF);
	String nameFilterParam = request.getParameter("search");
	String pageParam = request.getParameter("page");
	if (nameFilterParam != null) {
	    page.setNameFilter(nameFilterParam);
	}
	// compute TOTAL Res + max Pages
	int total = computerService.count(page.getNameFilter());
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

	List<Computer> computersList = computerService.find(page);
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
	    computerService.delete(id);
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
	    Computer computer = computerService.find(id);
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
