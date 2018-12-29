/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartER.service;

import SmartER.Credential;
import SmartER.Resident;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@Path("smarter.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "SmartERPU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Credential entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credential find(@PathParam("id") String id) {
        return super.find(id);
    }
    
   @GET
    @Path("findByResid/{resid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray findByResid(@PathParam("resid") int resid) { 
        List<Object[]> queryList = em.createQuery("SELECT  u.resid.resid, u.regidate, u.username, "
                + "u.passwordhash From Credential AS u where u.resid.resid = :resid ",                 
                Object[].class).setParameter("resid",resid).getResultList(); 
        List<Object[]> newQueryList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");       
        String dateString = null;
        for(Object[] obj : queryList){
            try{
                dateString = df.format(obj[1]);  
                obj[1] = dateString;
                  
                    newQueryList.add(obj);
                              
            }
            catch (Exception ex ){
                System.out.println(ex);
            }
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();   
                for (Object[] row : newQueryList) {             
                    JsonObject consumptionObject = Json.createObjectBuilder()  
                            .add("resid",(int)row[0])
                            .add("regidate", (String)row[1])                          
                            .add("username", (String)row[2])
                            .add("passwordhash",(String)row[3]).build();             
                    arrayBuilder.add(consumptionObject);        
                }                
                JsonArray jArray = arrayBuilder.build();        
                return jArray;    
    }
    
    @GET
    @Path("findByPasswordhash/{passwordhash}")     
    @Produces({"application/json"})     
    public List<Credential> findByPasswordhash(@PathParam("passwordhash") String passwordhash) {         
        Query query = em.createNamedQuery("Credential.findByPasswordhash");         
        query.setParameter("passwordhash", passwordhash);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByRegidate/{regidate}")     
    @Produces({"application/json"})     
    public List<Credential> findByRegidate(@PathParam("regidate") String regidate) { 
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");        
        Query query = em.createNamedQuery("Credential.findByRegidate");             
         try
        {
            Date date = df.parse(regidate);                    
            query.setParameter("regidate", date);                 
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return query.getResultList();  
    } 
    
    

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
