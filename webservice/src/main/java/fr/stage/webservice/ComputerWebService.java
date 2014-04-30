package fr.stage.webservice;

import java.rmi.RemoteException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.stage.domain.Computer;
import fr.stage.domain.InputPage;
import fr.stage.service.ComputerService;

@WebService(serviceName = "ComputerService")
@Component
public class ComputerWebService  {

    @Autowired
    ComputerService computerService;

    @WebMethod
    public int count(@WebParam(name="nameFilter")String nameFilter) throws RemoteException{
	return computerService.count(nameFilter);
    }
    @WebMethod
    public void create(@WebParam(name="computer")Computer computer) throws RemoteException{
	computerService.create(computer);
    }
    @WebMethod
    public Computer find(@WebParam(name="id")Long id) throws RemoteException{
	return computerService.find(id);
    }
    @WebMethod
    public List<Computer> findAll(@WebParam(name="page")InputPage page) throws RemoteException{
	return computerService.find(page).getContent();
    }
    @WebMethod
    public void update(@WebParam(name="computer")Computer computer) throws RemoteException{
	computerService.update(computer);
    }
    @WebMethod
    public boolean delete(@WebParam(name="id")Long id) throws RemoteException{
	return computerService.delete(id);
    }
}
