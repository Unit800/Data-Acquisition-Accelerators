//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:27 AM(foreman)- (JAXB RI IBM 2.2.3-07/05/2013 05:22 AM(foreman)-)
//


package com.acxiom.schemas.v201110.us.idod.comprehensivereport.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PeopleNameAddressRestrictedUseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PeopleNameAddressRestrictedUseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddressRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AddressSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NameRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NameSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PeopleNameAddressRestrictedUseInfo", propOrder = {
    "addressRestrictedUseCode",
    "addressSourceCode",
    "nameRestrictedUseCode",
    "nameSourceCode"
})
public class PeopleNameAddressRestrictedUseInfo {

    @XmlElement(name = "AddressRestrictedUseCode")
    protected Long addressRestrictedUseCode;
    @XmlElement(name = "AddressSourceCode")
    protected Long addressSourceCode;
    @XmlElement(name = "NameRestrictedUseCode")
    protected Long nameRestrictedUseCode;
    @XmlElement(name = "NameSourceCode")
    protected Long nameSourceCode;

    /**
     * Gets the value of the addressRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAddressRestrictedUseCode() {
        return addressRestrictedUseCode;
    }

    /**
     * Sets the value of the addressRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAddressRestrictedUseCode(Long value) {
        this.addressRestrictedUseCode = value;
    }

    /**
     * Gets the value of the addressSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAddressSourceCode() {
        return addressSourceCode;
    }

    /**
     * Sets the value of the addressSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAddressSourceCode(Long value) {
        this.addressSourceCode = value;
    }

    /**
     * Gets the value of the nameRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNameRestrictedUseCode() {
        return nameRestrictedUseCode;
    }

    /**
     * Sets the value of the nameRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNameRestrictedUseCode(Long value) {
        this.nameRestrictedUseCode = value;
    }

    /**
     * Gets the value of the nameSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNameSourceCode() {
        return nameSourceCode;
    }

    /**
     * Sets the value of the nameSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNameSourceCode(Long value) {
        this.nameSourceCode = value;
    }

}
