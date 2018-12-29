/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartER.service;

import SmartER.Eusage;
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
@Path("smarter.eusage")
public class EusageFacadeREST extends AbstractFacade<Eusage> {

    @PersistenceContext(unitName = "SmartERPU")
    private EntityManager em;

    public EusageFacadeREST() {
        super(Eusage.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Eusage entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Eusage entity) {
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
    public Eusage find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    //Task3 Question(1)
     @GET
    @Path("findByResid/{resid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray findByResid(@PathParam("resid") int resid) { 
        List<Object[]> queryList = em.createQuery("SELECT  u.resid.resid, u.usedate, "
                + "u.usehour, u.fridgeusage, u.acusage, u.wmusage, u.temp "
                + "From Eusage AS u where u.resid.resid = :resid ",                 
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
                            .add("date", (String)row[1])                          
                            .add("usehour", (int)row[2])
                            .add("fridge",(double)row[3])
                            .add("acusage",(double)row[4])
                            .add("wmusage",(double)row[5])
                            .add("temp",(double)row[6]).build();             
                    arrayBuilder.add(consumptionObject);        
                }                
                JsonArray jArray = arrayBuilder.build();        
                return jArray;    
    }
    
    @GET
    @Path("findByUsedate/{usedate}")     
    @Produces({"application/json"})     
    public List<Eusage> findByUsedate(@PathParam("usedate") String usedate) { 
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");        
        Query query = em.createNamedQuery("Eusage.findByUsedate");                  
         try
        {
            Date date = df.parse(usedate);                    
            query.setParameter("usedate", date);                   
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return query.getResultList();  
    } 
    
    @GET
    @Path("findByUsehour/{usehour}")     
    @Produces({"application/json"})     
    public List<Eusage> findByUsehour(@PathParam("usehour") int usehour) {         
        Query query = em.createNamedQuery("Eusage.findByUsehour");         
        query.setParameter("usehour", usehour);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByFridgeusage/{fridgeusage}")     
    @Produces({"application/json"})     
    public List<Eusage> findByFridgeusage(@PathParam("fridgeusage") double fridgeusage) {         
        Query query = em.createNamedQuery("Eusage.findByFridgeusage");         
        query.setParameter("fridgeusage", fridgeusage);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByAcusage/{acusage}")     
    @Produces({"application/json"})     
    public List<Eusage> findByAcusage(@PathParam("acusage") double acusage) {         
        Query query = em.createNamedQuery("Eusage.findByAcusage");         
        query.setParameter("acusage", acusage);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByWmusage/{wmusage}")     
    @Produces({"application/json"})     
    public List<Eusage> findByWmusage(@PathParam("wmusage") double wmusage) {         
        Query query = em.createNamedQuery("Eusage.findByWmusage");         
        query.setParameter("wmusage", wmusage);         
        return query.getResultList();     
    } 
    
    @GET
    @Path("findByTemp/{temp}")     
    @Produces({"application/json"})     
    public List<Eusage> findByTemp(@PathParam("temp") double temp) {         
        Query query = em.createNamedQuery("Eusage.findByTemp");         
        query.setParameter("temp", temp);         
        return query.getResultList();     
    }   
    
    //task3 Question(3)
    @GET    
    @Path("findByFirstnameANDSurname/{firstname}/{surname}")     
    @Produces({"application/json"})     
    public List<Eusage> findByFirstnameANDSurname(@PathParam("firstname") String firstname, @PathParam("surname") String surname) {                
        TypedQuery<Eusage> q = em.createQuery("SELECT u FROM Eusage u WHERE u.resid.firstname = :firstname AND u.resid.surname = :surname", Eusage.class);         
        q.setParameter("firstname", firstname);    
        q.setParameter("surname",surname);  
        return q.getResultList();    
    }
    //task3 Question(4)
    @GET    
    @Path("findByFirstnameANDPostcode/{firstname}/{postcode}")     
    @Produces({"application/json"})     
    public List<Eusage> findByFirstnameANDPostcode(@PathParam("firstname") String firstname, @PathParam("postcode") String postcode) {                
        Query q = em.createNamedQuery("Eusage.findByFirstnameANDPostcode");         
        q.setParameter("firstname", firstname);    
        q.setParameter("postcode",postcode);    
        return q.getResultList();    
    }
    
    //Task4 Question(1)
    @GET
    @Path("findByAppName/{resid},{appName}/{usedate}/{usehour}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray AllAppHourlyPowerUsage(@PathParam("resid") int resid,@PathParam("appName") String appName,@PathParam("usedate") String usedate, @PathParam("usehour") int usehour) { 
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
         try
        {
            newDate = df.parse(usedate);                        
        }
        catch(ParseException e){
            e.printStackTrace();
        }        
        List<Object[]> queryList = em.createQuery("SELECT u.resid.resid, u.usedate, u.usehour, u.fridgeusage, u.acusage, u.wmusage From Eusage AS u where u.resid.resid = :resid AND u.usedate = :usedate AND u.usehour = :usehour",                 
                Object[].class).setParameter("resid",resid).setParameter("usedate",newDate).setParameter("usehour", usehour).getResultList(); 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
                switch(appName)
        {
        case "fridge":
            for (Object[] row : queryList) {             
                JsonObject usageObject = Json.createObjectBuilder()
                        .add("fridgeusage", (double)row[3]).build();             
                arrayBuilder.add(usageObject);        
            }   
                break;
        case "washing machine":
            for (Object[] row : queryList) {             
                       JsonObject usageObject = Json.createObjectBuilder()                        
                               .add("acusage", (double)row[4]).build();             
                       arrayBuilder.add(usageObject);        
                   } 
            break;
        case "air conditon":
            for (Object[] row : queryList) {             
                       JsonObject usageObject = Json.createObjectBuilder()                       
                               .add("wmusage",(double)row[5]).build();             
                       arrayBuilder.add(usageObject);        
                   }     
           break;        
        }     
                JsonArray jArray = arrayBuilder.build();        
                return jArray; 
    }
    
    //Task4 Question(2) 
    @GET
    @Path("findByAllAppHourlyPowerUsage/{resid}/{usedate}/{usehour}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray AllAppHourlyPowerUsage(@PathParam("resid") int resid, @PathParam("usedate") String usedate, @PathParam("usehour") int usehour) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
        try
        {
            newDate = df.parse(usedate);                                          
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        List<Object[]> queryList = em.createQuery("SELECT u.resid.resid, u.usedate, u.usehour,u.fridgeusage, u.wmusage, u.acusage From Eusage AS u where u.resid.resid = :resid AND u.usedate = :usedate AND u.usehour = :usehour"
                ,Object[].class).setParameter("resid",resid).setParameter("usedate",newDate).setParameter("usehour",usehour).getResultList(); 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
                for (Object[] obj : queryList) {             
                    JsonObject usageObject = Json.createObjectBuilder()  
                            .add("fridge",(double)obj[3])
                            .add("washing machine",(double)obj[4])
                            .add("air-condition", (double)obj[5]).build();             
                    arrayBuilder.add(usageObject);        
                }        
                JsonArray jArray = arrayBuilder.build();        
                return jArray; 
    }
    
    //Task4 Question(3)
    @GET
    @Path("findByAllResAllAppHourlyPowerUsage/{usedate}/{usehour}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray AllResAllAppHourlyPowerUsage(@PathParam("usedate") String usedate, @PathParam("usehour") int usehour) { 
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
         try
        {
            newDate = df.parse(usedate);                               
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        List<Object[]> queryList = em.createQuery("SELECT u.resid.resid, u.usedate,u.usehour, u.resid.address, u.resid.postcode, sum(u.fridgeusage + u.wmusage + u.acusage) as totalusage from Eusage u where u.usedate = :usedate AND u.usehour = :usehour group by u.resid, u.usedate, u.usehour, u.resid.address, u.resid.postcode ",                 
                Object[].class).setParameter("usedate",newDate).setParameter("usehour",usehour).getResultList();        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
                for (Object[] row : queryList) {             
                    JsonObject usageObject = Json.createObjectBuilder().                          
                            add("resident id", (int)row[0])                          
                           .add("address", (String)row[3])                          
                           .add("postcode",(String)row[4])
                           .add("total Usage",(double)row[5])
                            .build();             
                    arrayBuilder.add(usageObject);        
                }        
                JsonArray jArray = arrayBuilder.build();        
                return jArray; 
    }
    
    //Task4 Question(4)   
    @GET
    @Path("findByHighestHourlyPowerConsumption/{resid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray HighestHourlyPowerConsumption(@PathParam("resid") int resid) { 
        List<Object[]> queryList = em.createQuery("SELECT  u.resid.resid, u.usedate, u.usehour, sum(u.fridgeusage + u.acusage + u.wmusage) as totalusage From Eusage AS u where u.resid.resid = :resid group by u.resid, u.usedate, u.usehour",                 
                Object[].class).setParameter("resid",resid).getResultList(); 
        List<Object[]> newQueryList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");       
        String dateString = null;
        double totalUsage = 0;
        double highestUsage = 0;
        for(Object[] obj : queryList){
            try{
                dateString = df.format(obj[1]);  
                obj[1] = dateString;
                totalUsage = (Double) obj[3];                
                if(totalUsage > highestUsage)
                {
                    highestUsage = totalUsage;
                    newQueryList.add(obj);
                }                    
            }
            catch (Exception ex ){
                System.out.println(ex);
            }
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();   
                for (Object[] row : newQueryList) {             
                    JsonObject consumptionObject = Json.createObjectBuilder()  
                            .add("residentId",(int)row[0])
                            .add("date", (String)row[1])                          
                            .add("hour", (int)row[2])
                            .add("totalusage",(double)row[3]).build();             
                    arrayBuilder.add(consumptionObject);        
                }                
                JsonArray jArray = arrayBuilder.build();        
                return jArray;    
    }
    //Task5 Question(5)  
    @GET
    @Path("findByDailyUsageOfApp/{resid}/{usedate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray DailyUsageOfApp(@PathParam("resid") int resid, @PathParam("usedate") String usedate) { 
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
         try
        {
            newDate = df.parse(usedate);                                             
        }
        catch(ParseException e){
            e.printStackTrace();  
        }
        List<Object[]> queryList = em.createQuery("SELECT u.resid.resid, u.usedate, sum(u.fridgeusage), sum(u.wmusage), sum(u.acusage) From Eusage AS u where u.resid.resid = :resid AND u.usedate = :usedate group by u.resid, u.usedate",                 
                Object[].class).setParameter("resid",resid).setParameter("usedate",newDate).getResultList();         
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();  
                for (Object[] row : queryList) {             
                    JsonObject timeObject = Json.createObjectBuilder()                         
                            .add("resident ID", (int)row[0])                          
                            .add("fridge", (double)row[2])
                            .add("aircon",(double)row[3])
                            .add("washingmachine",(double)row[4]).build();             
                    arrayBuilder.add(timeObject);        
                }        
                JsonArray jArray = arrayBuilder.build();        
                return jArray; 
    }  

  //Task 5 question(6)
    @GET
    @Path("findByDailyOrHourlyUsageOfApp/{resid}/{usedate}/{viewVariable}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray DailyUsageOfApp(@PathParam("resid") int resid, @PathParam("usedate") String usedate, @PathParam("viewVariable") String viewVariable) { 
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
         try
        {
            newDate = df.parse(usedate);                                             
        }
        catch(ParseException e){
            e.printStackTrace();  
        }
          JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();  
         switch(viewVariable)
         {
             case "hourly":
                List<Object[]> queryList1 = em.createQuery("SELECT u.resid.resid, sum(u.fridgeusage + u.wmusage + u.acusage) as usage,u.temp, u.usedate,u.usehour From Eusage AS u where u.resid.resid = :resid AND u.usedate = :usedate group by u.resid, u.usedate, u.usehour, u.temp",                 
                Object[].class).setParameter("resid",resid).setParameter("usedate",newDate).getResultList();   
                
                for (Object[] row : queryList1) {    
                    String dateString = df.format(row[3]);
                    row[3] = dateString;
                    JsonObject timeObject = Json.createObjectBuilder()                         
                            .add("resident ID", (int)row[0])                          
                            .add("usage", (double)row[1])
                            .add("temp",(double)row[2])
                            .add("date",(String)row[3])
                            .add("time",(int)row[4]).build();             
                    arrayBuilder.add(timeObject);        
                }        
                 break;
             case "daily":
                List<Object[]> queryList2 = em.createQuery("SELECT u.resid.resid,sum(u.fridgeusage + u.wmusage + u.acusage) as usage, avg(u.temp) From Eusage AS u where u.resid.resid = :resid AND u.usedate = :usedate group by u.resid",                 
                Object[].class).setParameter("resid",resid).setParameter("usedate",newDate).getResultList();         
         
                for (Object[] row : queryList2) {             
                    JsonObject timeObject = Json.createObjectBuilder()                         
                            .add("resident ID", (int)row[0])                          
                            .add("usage", (double)row[1])
                            .add("temperature",(double)row[2]).build();             
                    arrayBuilder.add(timeObject);        
                }        
                 break;
         }
        
                JsonArray jArray = arrayBuilder.build();        
                return jArray; 
    }
    
    @GET
    @Path("findValueOfMostCurrentMonth/{resid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray findValueOfMostCurrentMonth(@PathParam("resid") int resid) { 
        List<Object[]> queryList = em.createQuery("SELECT  u.resid.resid, u.usedate,"
                + "sum(u.fridgeusage + u.acusage + u.wmusage) as totalusage, avg(u.temp) "
                + "From Eusage AS u where u.resid.resid = :resid group by u.resid, u.usedate",                 
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
                            .add("residentId",(int)row[0])
                            .add("usedate", (String)row[1])
                            .add("usage",(double)row[2])
                            .add("temp",(double)row[3]).build();             
                    arrayBuilder.add(consumptionObject);        
                }                
                JsonArray jArray = arrayBuilder.build();        
                return jArray;    
    }
    
     @GET
    @Path("findMostRecentTotalDailyUsage/{resid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray findMostRecentTotalDailyUsage(@PathParam("resid") int resid) { 
        List<Object[]> queryList = em.createQuery("SELECT  u.resid.resid, u.usedate, "
                + "sum(u.fridgeusage + u.acusage + u.wmusage) as totalusage, avg(u.temp) as temp From Eusage AS u "
                + "where u.resid.resid = :resid group by u.resid, u.usedate order by u.resid.resid,u.usedate desc ",                 
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
                            .add("usedate", (String)row[1])
                            .add("usage",(double)row[2])
                            .add("temp",(double)row[3]).build();             
                    arrayBuilder.add(consumptionObject);        
                }                
                JsonArray jArray = arrayBuilder.build();        
                return jArray;    
    }
    
    
     @GET
    @Path("findMostRecentTotalHourlyUsage/{resid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JsonArray findMostRecentTotalHourlyUsage(@PathParam("resid") int resid) { 
        List<Object[]> queryList = em.createQuery("SELECT  u.resid.resid, u.usedate,u.usehour, "
                + "sum(u.fridgeusage + u.acusage + u.wmusage) as totalusage From Eusage AS u "
                + "where u.resid.resid = :resid group by u.resid, u.usedate, u.usehour "
                + "order by u.resid.resid,u.usedate desc, u.usehour desc ",                 
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
                            .add("date", (String)row[1])
                            .add("usehour",(int)row[2])
                            .add("usage",(double)row[3]).build();             
                    arrayBuilder.add(consumptionObject);        
                }                
                JsonArray jArray = arrayBuilder.build();        
                return jArray;    
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Eusage> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Eusage> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
