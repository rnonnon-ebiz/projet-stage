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
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {

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
    public EditComputer() {
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
	// Read id
	String id = request.getParameter("id");
	String regex = "^\\d\\d*$";
	Computer computer = null;
	// Valid it
	if (id.matches(regex)) {
	    long idLong = Integer.parseInt(id);
	    // Search for computer with that id
	    computer = computerService.find(idLong);
	}
	// Create DTO from computer found / or from null
	ComputerDTO computerDTO = computerMapper.toComputerDTO(computer);
	// FATAL! User shouldn't access this page without having something to
	// modify (id error)
	if (computerDTO == null) {
	    throw new RuntimeException();
	}
	// Set Attributes to fill blanks
	request.setAttribute("computer", computerDTO);
	request.setAttribute("companiesList", companiesList);
	this.getServletContext().getRequestDispatcher(ServletUtil.PAGE_URI + "editComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ComputerDTO computerDTO = new ComputerDTO();
	computerDTO.setId(request.getParameter("id"));
	computerDTO.setName(request.getParameter("name"));
	computerDTO.setIntroducedDate(request.getParameter("introduced"));
	computerDTO.setDiscontinuedDate(request.getParameter("discontinued"));
	computerDTO.setCompany(request.getParameter("company"));

	byte errorCode = computerValidator.validForUpdate(computerDTO);
	// Null Computer / Company ERROR / Null id: Shouldn't produce
	// Fatal ERROR
	if ((errorCode & 0x01) == 0x01 || (errorCode & 0x10) == 0x10 || (errorCode & 0x20) == 0x20) {
	    System.out.println("error code : " + Byte.toString(errorCode));
	    throw new RuntimeException();
	}
	// If no error
	else if (errorCode == 0) {
	    // Update in DB
	    Computer computer = computerMapper.toComputer(computerDTO);
	    computerService.update(computer);
	    // Redirect to Dashboard
	    response.sendRedirect("dashboard?UpdateStatus=success");
	}
	// Error but not fatal
	else {
	    request.setAttribute("errorCode", errorCode);
	    request.setAttribute("computer", computerDTO);
	    request.setAttribute("companiesList", companiesList);
	    // Restore view with errors
	    this.getServletContext().getRequestDispatcher(ServletUtil.PAGE_URI + "editComputer.jsp").forward(request, response);
	}
    }
}
