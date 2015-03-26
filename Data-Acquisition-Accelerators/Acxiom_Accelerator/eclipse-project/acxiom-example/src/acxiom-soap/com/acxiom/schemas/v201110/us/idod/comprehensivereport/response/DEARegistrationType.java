//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:27 AM(foreman)- (JAXB RI IBM 2.2.3-07/05/2013 05:22 AM(foreman)-)
//


package com.acxiom.schemas.v201110.us.idod.comprehensivereport.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DEARegistrationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DEARegistrationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DrugSchedules" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LicenseDateExpires" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LicenseNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LicenseOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LicenseType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LicenseTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DEARegistrationType", propOrder = {
    "drugSchedules",
    "licenseDateExpires",
    "licenseNumber",
    "licenseOrder",
    "licenseType",
    "licenseTypeCode"
})
public class DEARegistrationType {

    @XmlElement(name = "DrugSchedules")
    protected String drugSchedules;
    @XmlElement(name = "LicenseDateExpires")
    protected String licenseDateExpires;
    @XmlElement(name = "LicenseNumber")
    protected String licenseNumber;
    @XmlElement(name = "LicenseOrder")
    protected String licenseOrder;
    @XmlElement(name = "LicenseType")
    protected String licenseType;
    @XmlElement(name = "LicenseTypeCode")
    protected String licenseTypeCode;

    /**
     * Gets the value of the drugSchedules property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrugSchedules() {
        return drugSchedules;
    }

    /**
     * Sets the value of the drugSchedules property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrugSchedules(String value) {
        this.drugSchedules = value;
    }

    /**
     * Gets the value of the licenseDateExpires property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseDateExpires() {
        return licenseDateExpires;
    }

    /**
     * Sets the value of the licenseDateExpires property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseDateExpires(String value) {
        this.licenseDateExpires = value;
    }

    /**
     * Gets the value of the licenseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Sets the value of the licenseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseNumber(String value) {
        this.licenseNumber = value;
    }

    /**
     * Gets the value of the licenseOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseOrder() {
        return licenseOrder;
    }

    /**
     * Sets the value of the licenseOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseOrder(String value) {
        this.licenseOrder = value;
    }

    /**
     * Gets the value of the licenseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseType() {
        return licenseType;
    }

    /**
     * Sets the value of the licenseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseType(String value) {
        this.licenseType = value;
    }

    /**
     * Gets the value of the licenseTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseTypeCode() {
        return licenseTypeCode;
    }

    /**
     * Sets the value of the licenseTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseTypeCode(String value) {
        this.licenseTypeCode = value;
    }

}