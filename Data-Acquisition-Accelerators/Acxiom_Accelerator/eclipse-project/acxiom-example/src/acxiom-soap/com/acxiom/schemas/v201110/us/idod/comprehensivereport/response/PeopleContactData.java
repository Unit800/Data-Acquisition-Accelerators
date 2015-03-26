//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:27 AM(foreman)- (JAXB RI IBM 2.2.3-07/05/2013 05:22 AM(foreman)-)
//


package com.acxiom.schemas.v201110.us.idod.comprehensivereport.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PeopleContactData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PeopleContactData">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.acxiom.com/v201110/us/idod/comprehensivereport/response}PeopleData">
 *       &lt;sequence>
 *         &lt;element name="Email1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fax1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fax2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FaxExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MobilePhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pager" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RestrictedUseInfo" type="{http://schemas.acxiom.com/v201110/us/idod/comprehensivereport/response}PeopleContactRestrictedUseInfo" minOccurs="0"/>
 *         &lt;element name="TollFreePhone1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TollFreePhone2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WorkPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WorkPhoneExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PeopleContactData", propOrder = {
    "email1",
    "email2",
    "email3",
    "email4",
    "fax1",
    "fax2",
    "faxExt",
    "mobilePhone",
    "pager",
    "restrictedUseInfo",
    "tollFreePhone1",
    "tollFreePhone2",
    "workPhone",
    "workPhoneExt"
})
public class PeopleContactData
    extends PeopleData
{

    @XmlElement(name = "Email1")
    protected String email1;
    @XmlElement(name = "Email2")
    protected String email2;
    @XmlElement(name = "Email3")
    protected String email3;
    @XmlElement(name = "Email4")
    protected String email4;
    @XmlElement(name = "Fax1")
    protected String fax1;
    @XmlElement(name = "Fax2")
    protected String fax2;
    @XmlElement(name = "FaxExt")
    protected String faxExt;
    @XmlElement(name = "MobilePhone")
    protected String mobilePhone;
    @XmlElement(name = "Pager")
    protected String pager;
    @XmlElement(name = "RestrictedUseInfo")
    protected PeopleContactRestrictedUseInfo restrictedUseInfo;
    @XmlElement(name = "TollFreePhone1")
    protected String tollFreePhone1;
    @XmlElement(name = "TollFreePhone2")
    protected String tollFreePhone2;
    @XmlElement(name = "WorkPhone")
    protected String workPhone;
    @XmlElement(name = "WorkPhoneExt")
    protected String workPhoneExt;

    /**
     * Gets the value of the email1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail1() {
        return email1;
    }

    /**
     * Sets the value of the email1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail1(String value) {
        this.email1 = value;
    }

    /**
     * Gets the value of the email2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail2() {
        return email2;
    }

    /**
     * Sets the value of the email2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail2(String value) {
        this.email2 = value;
    }

    /**
     * Gets the value of the email3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail3() {
        return email3;
    }

    /**
     * Sets the value of the email3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail3(String value) {
        this.email3 = value;
    }

    /**
     * Gets the value of the email4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail4() {
        return email4;
    }

    /**
     * Sets the value of the email4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail4(String value) {
        this.email4 = value;
    }

    /**
     * Gets the value of the fax1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax1() {
        return fax1;
    }

    /**
     * Sets the value of the fax1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax1(String value) {
        this.fax1 = value;
    }

    /**
     * Gets the value of the fax2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax2() {
        return fax2;
    }

    /**
     * Sets the value of the fax2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax2(String value) {
        this.fax2 = value;
    }

    /**
     * Gets the value of the faxExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaxExt() {
        return faxExt;
    }

    /**
     * Sets the value of the faxExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaxExt(String value) {
        this.faxExt = value;
    }

    /**
     * Gets the value of the mobilePhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets the value of the mobilePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilePhone(String value) {
        this.mobilePhone = value;
    }

    /**
     * Gets the value of the pager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPager() {
        return pager;
    }

    /**
     * Sets the value of the pager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPager(String value) {
        this.pager = value;
    }

    /**
     * Gets the value of the restrictedUseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PeopleContactRestrictedUseInfo }
     *     
     */
    public PeopleContactRestrictedUseInfo getRestrictedUseInfo() {
        return restrictedUseInfo;
    }

    /**
     * Sets the value of the restrictedUseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeopleContactRestrictedUseInfo }
     *     
     */
    public void setRestrictedUseInfo(PeopleContactRestrictedUseInfo value) {
        this.restrictedUseInfo = value;
    }

    /**
     * Gets the value of the tollFreePhone1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTollFreePhone1() {
        return tollFreePhone1;
    }

    /**
     * Sets the value of the tollFreePhone1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTollFreePhone1(String value) {
        this.tollFreePhone1 = value;
    }

    /**
     * Gets the value of the tollFreePhone2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTollFreePhone2() {
        return tollFreePhone2;
    }

    /**
     * Sets the value of the tollFreePhone2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTollFreePhone2(String value) {
        this.tollFreePhone2 = value;
    }

    /**
     * Gets the value of the workPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Sets the value of the workPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkPhone(String value) {
        this.workPhone = value;
    }

    /**
     * Gets the value of the workPhoneExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkPhoneExt() {
        return workPhoneExt;
    }

    /**
     * Sets the value of the workPhoneExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkPhoneExt(String value) {
        this.workPhoneExt = value;
    }

}