/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartER.service;

import SmartER.Resident;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Administrator
 */
@Stateless
@Path("smarter.resident")
public class ResidentFacadeREST extends AbstractFacade<Resident> {

    @PersistenceContext(unitName = "SmartERPU")
    private EntityManager em;

    public ResidentFacadeREST() {
        super(Resident.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Resident entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Resident entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Resident find(@PathParam("id") Integer id) {
        return super.find(id);
    } 
    
    //Task3 Question(1)
    @GET
    @Path("findByFirstname/{firstname}")     
    @Produces({"application/json"})     
    public List<Resident> findByFirstname(@PathParam("firstname") String firstname) {         
        Query query = em.createNamedQuery("Resident.findByFirstname");         
        query.setParameter("firstname", firstname);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findBySurname/{surname}")     
    @Produces({"application/json"})     
    public List<Resident> findBySurname(@PathParam("surname") String surname) {         
        Query query = em.createNamedQuery("Resident.findBySurname");         
        query.setParameter("surname", surname);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByDob/{dob}")     
    @Produces({"application/json"})     
    public List<Resident> findByDob(@PathParam("dob") String dob) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Query query = em.createNamedQuery("Resident.findByDob"); 
        try
        {
            Date date = df.parse(dob);                    
            query.setParameter("dob", date);                   
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return query.getResultList();  
    }
    
    @GET
    @Path("findByAddress/{address}")     
    @Produces({"application/json"})     
    public List<Resident> findByAddress(@PathParam("address") String address) {         
        Query query = em.createNamedQuery("Resident.findByAddress");         
        query.setParameter("address", address);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByPostcode/{postcode}")     
    @Produces({"application/json"})     
    public List<Resident> findByPostcode(@PathParam("postcode") String postcode) {         
        Query query = em.createNamedQuery("Resident.findByPostcode");         
        query.setParameter("postcode", postcode);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByEmail/{email}")     
    @Produces({"application/json"})     
    public List<Resident> findByEmail(@PathParam("email") String email) {         
        Query query = em.createNamedQuery("Resident.findByEmail");         
        query.setParameter("email", email);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByMobile/{mobile}")     
    @Produces({"application/json"})     
    public List<Resident> findByMobile(@PathParam("mobile") String mobile) {         
        Query query = em.createNamedQuery("Resident.findByMobile");         
        query.setParameter("mobile", mobile);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByResidentnum/{residentnum}")     
    @Produces({"application/json"})     
    public List<Resident> findByResidentnum(@PathParam("residentnum") int residentnum) {         
        Query query = em.createNamedQuery("Resident.findByResidentnum");         
        query.setParameter("residentnum", residentnum);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByEprovider/{eprovider}")     
    @Produces({"application/json"})     
    public List<Resident> findByEprovider(@PathParam("eprovider") String eprovider) {         
        Query query = em.createNamedQuery("Resident.findByEprovider");         
        query.setParameter("eprovider", eprovider);   
        return query.getResultList();         
    }    
    
    //Task3 Question(2)
    @GET     
    @Path("findByFirstnameANDPostcode/{firstname}/{postcode}")     
    @Produces({"application/json"})    
    public List<Resident> findByFirstnameANDPostcode(@PathParam("firstname") String firstname, @PathParam ("postcode")String postcode){         
        TypedQuery<Resident> query = em.createQuery("SELECT r FROM Resident r WHERE r.firstname = :firstname AND r.postcode = :postcode", Resident.class);         
        query.setParameter("firstname",firstname);
       query.setParameter("postcode",postcode);
        return query.getResultList();    
     } 

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Resident> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Resident> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
