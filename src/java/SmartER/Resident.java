/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartER;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "RESIDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resident.findAll", query = "SELECT r FROM Resident r")
    , @NamedQuery(name = "Resident.findByResid", query = "SELECT r FROM Resident r WHERE r.resid = :resid")
    , @NamedQuery(name = "Resident.findByFirstname", query = "SELECT r FROM Resident r WHERE r.firstname = :firstname")
    , @NamedQuery(name = "Resident.findBySurname", query = "SELECT r FROM Resident r WHERE r.surname = :surname")
    , @NamedQuery(name = "Resident.findByDob", query = "SELECT r FROM Resident r WHERE r.dob = :dob")
    , @NamedQuery(name = "Resident.findByAddress", query = "SELECT r FROM Resident r WHERE r.address = :address")
    , @NamedQuery(name = "Resident.findByPostcode", query = "SELECT r FROM Resident r WHERE r.postcode = :postcode")
    , @NamedQuery(name = "Resident.findByEmail", query = "SELECT r FROM Resident r WHERE r.email = :email")
    , @NamedQuery(name = "Resident.findByMobile", query = "SELECT r FROM Resident r WHERE r.mobile = :mobile")
    , @NamedQuery(name = "Resident.findByResidentnum", query = "SELECT r FROM Resident r WHERE r.residentnum = :residentnum")
    , @NamedQuery(name = "Resident.findByEprovider", query = "SELECT r FROM Resident r WHERE r.eprovider = :eprovider")})
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESID")
    private Integer resid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SURNAME")
    private String surname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSTCODE")
    private String postcode;
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "MOBILE")
    private String mobile;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESIDENTNUM")
    private int residentnum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EPROVIDER")
    private String eprovider;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resid")
    private Collection<Credential> credentialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resid")
    private Collection<Eusage> eusageCollection;

    public Resident() {
    }

    public Resident(Integer resid) {
        this.resid = resid;
    }

    public Resident(Integer resid, String firstname, String surname, Date dob, String address, String postcode,String email, String mobile, int residentnum, String eprovider) {
        this.resid = resid;
        this.firstname = firstname;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        this.mobile = mobile;
        this.residentnum = residentnum;
        this.eprovider = eprovider;
    }

    public Integer getResid() {
        return resid;
    }

    public void setResid(Integer resid) {
        this.resid = resid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getResidentnum() {
        return residentnum;
    }

    public void setResidentnum(int residentnum) {
        this.residentnum = residentnum;
    }

    public String getEprovider() {
        return eprovider;
    }

    public void setEprovider(String eprovider) {
        this.eprovider = eprovider;
    }

    @XmlTransient
    public Collection<Credential> getCredentialCollection() {
        return credentialCollection;
    }

    public void setCredentialCollection(Collection<Credential> credentialCollection) {
        this.credentialCollection = credentialCollection;
    }

    @XmlTransient
    public Collection<Eusage> getEusageCollection() {
        return eusageCollection;
    }

    public void setEusageCollection(Collection<Eusage> eusageCollection) {
        this.eusageCollection = eusageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resid != null ? resid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resident)) {
            return false;
        }
        Resident other = (Resident) object;
        if ((this.resid == null && other.resid != null) || (this.resid != null && !this.resid.equals(other.resid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SmartER.Resident[ resid=" + resid + " ]";
    }
    
}
