package fr.stage.webservice;

import java.rmi.RemoteException;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.stage.domain.Computer;
import fr.stage.domain.InputPage;
import fr.stage.service.ComputerService;

@WebService
@Component
@Path("/computer")
public class ComputerWebService  {

    @Autowired
    ComputerService computerService;

    @GET
    @Path("/count/{nameFilter}/")
    public int count(@PathParam("nameFilter") String nameFilter) throws RemoteException{
	return computerService.count(nameFilter);
    }

    @GET
    @Path("/count/")
    public int count() throws RemoteException{
	return computerService.count("");
    }

    @POST
    @Path("/create/")
    public void create(Computer computer) throws RemoteException{
	computerService.create(computer);
    }

    @GET
    @Path("/find/{id}/")
    @Produces("application/xml")
    public Computer find(@PathParam("id")Long id) throws RemoteException{
	return computerService.find(id);
    }

    @GET
    @Path("/findAll/")
    @Produces("application/xml")
    public List<Computer> findAll(InputPage page) throws RemoteException{
	return computerService.find(page).getContent();
    }

    @POST
    @Path("/update/")
    public void update(Computer computer) throws RemoteException{
	computerService.update(computer);
    }

    @DELETE
    @Path("/delete/{id}/")
    public void delete(@PathParam("id")Long id) throws RemoteException{
	computerService.delete(id);
    }
}
