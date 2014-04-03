package fr.stage.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import fr.stage.dto.CompanyDTO;
import fr.stage.dto.ComputerDTO;
import fr.stage.mapper.CompanyMapper;
import fr.stage.mapper.ComputerMapper;
import fr.stage.service.CompanyService;
import fr.stage.service.ComputerService;
import fr.stage.util.ServletUtil;
import fr.stage.validator.ComputerValidator;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private List<CompanyDTO> companiesList;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CompanyService companyService;

    @Autowired
    ComputerService computerService;

    @Autowired
    ComputerValidator computerValidator;

    @Autowired
    ComputerMapper computerMapper;

    @Autowired
    CompanyMapper companyMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
	super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	initServ();
    }

    private void initServ() {
	List<Company> companies = companyService.findAll();
	companiesList = new ArrayList<CompanyDTO>();
	for (Company comp : companies) {
	    companiesList.add(companyMapper.toCompanyDTO(comp));
	}
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ComputerDTO computerDTO = (ComputerDTO) request.getAttribute("computer");
	// First page launch
	if (computerDTO == null) {
	    System.out.println("Add page");
	    computerDTO = new ComputerDTO();
	}
	request.setAttribute("computer", computerDTO);
	request.setAttribute("companiesList", companiesList);
	this.getServletContext().getRequestDispatcher(ServletUtil.PAGE_URI + "addComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ComputerDTO computerDTO = new ComputerDTO();
	computerDTO.setName(request.getParameter("name"));
	computerDTO.setIntroducedDate(request.getParameter("introduced"));
	computerDTO.setDiscontinuedDate(request.getParameter("discontinued"));
	computerDTO.setCompany(request.getParameter("company"));

	byte errorCode = computerValidator.validForCreate(computerDTO);
	// Null Computer Or Company ERROR: Shouldn't produce
	// Fatal ERROR
	if ((errorCode & 0x01) == 0x01 || (errorCode & 0x10) == 0x10) {
	    System.out.println("error code : " + Byte.toString(errorCode));
	    throw new RuntimeException();
	}
	// If no error
	else if (errorCode == 0) {
	    // Store in DB
	    Computer computer = computerMapper.toComputer(computerDTO);
	    computerService.create(computer);
	    response.sendRedirect("dashboard?AddStatus=success");
	}
	// Error but not fatal
	else {
	    request.setAttribute("errorCode", errorCode);
	    request.setAttribute("computer", computerDTO);
	    request.setAttribute("companiesList", companiesList);
	    this.getServletContext().getRequestDispatcher(ServletUtil.PAGE_URI + "addComputer.jsp").forward(request, response);
	}
    }

}
