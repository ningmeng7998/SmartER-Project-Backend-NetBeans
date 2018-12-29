/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartER;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "EUSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eusage.findAll", query = "SELECT e FROM Eusage e")
    , @NamedQuery(name = "Eusage.findByUsageid", query = "SELECT e FROM Eusage e WHERE e.usageid = :usageid")
    , @NamedQuery(name = "Eusage.findByUsedate", query = "SELECT e FROM Eusage e WHERE e.usedate = :usedate")
    , @NamedQuery(name = "Eusage.findByUsehour", query = "SELECT e FROM Eusage e WHERE e.usehour = :usehour")
    , @NamedQuery(name = "Eusage.findByFridgeusage", query = "SELECT e FROM Eusage e WHERE e.fridgeusage = :fridgeusage")
    , @NamedQuery(name = "Eusage.findByAcusage", query = "SELECT e FROM Eusage e WHERE e.acusage = :acusage")
    , @NamedQuery(name = "Eusage.findByWmusage", query = "SELECT e FROM Eusage e WHERE e.wmusage = :wmusage")
    , @NamedQuery(name = "Eusage.findByTemp", query = "SELECT e FROM Eusage e WHERE e.temp = :temp")
    , @NamedQuery(name = "Eusage.findByFirstnameANDPostcode",query ="SELECT u FROM Eusage u WHERE u.resid.firstname = :firstname AND u.resid.postcode = :postcode")  
})
public class Eusage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USAGEID")
    private Integer usageid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USEDATE")
    @Temporal(TemporalType.DATE)
    private Date usedate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USEHOUR")
    private int usehour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FRIDGEUSAGE")
    private double fridgeusage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACUSAGE")
    private double acusage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WMUSAGE")
    private double wmusage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMP")
    private double temp;
    @JoinColumn(name = "RESID", referencedColumnName = "RESID")
    @ManyToOne(optional = false)
    private Resident resid;

    public Eusage() {
    }

    public Eusage(Integer usageid) {
        this.usageid = usageid;
    }

    public Eusage(Integer usageid, Date usedate, int usehour, double fridgeusage, double acusage, double wmusage, double temp) {
        this.usageid = usageid;
        this.usedate = usedate;
        this.usehour = usehour;
        this.fridgeusage = fridgeusage;
        this.acusage = acusage;
        this.wmusage = wmusage;
        this.temp = temp;
    }

    public Integer getUsageid() {
        return usageid;
    }

    public void setUsageid(Integer usageid) {
        this.usageid = usageid;
    }

    public Date getUsedate() {
        return usedate;
    }

    public void setUsedate(Date usedate) {
        this.usedate = usedate;
    }

    public int getUsehour() {
        return usehour;
    }

    public void setUsehour(int usehour) {
        this.usehour = usehour;
    }

    public double getFridgeusage() {
        return fridgeusage;
    }

    public void setFridgeusage(double fridgeusage) {
        this.fridgeusage = fridgeusage;
    }

    public double getAcusage() {
        return acusage;
    }

    public void setAcusage(double acusage) {
        this.acusage = acusage;
    }

    public double getWmusage() {
        return wmusage;
    }

    public void setWmusage(double wmusage) {
        this.wmusage = wmusage;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usageid != null ? usageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eusage)) {
            return false;
        }
        Eusage other = (Eusage) object;
        if ((this.usageid == null && other.usageid != null) || (this.usageid != null && !this.usageid.equals(other.usageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SmartER.Eusage[ usageid=" + usageid + " ]";
    }
    
}
