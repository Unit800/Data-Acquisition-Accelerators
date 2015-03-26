//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:27 AM(foreman)- (JAXB RI IBM 2.2.3-07/05/2013 05:22 AM(foreman)-)
//


package com.acxiom.schemas.v201110.us.idod.findpeople.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeceasedInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeceasedInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeceasedFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeceasedInfoDetail" type="{http://schemas.acxiom.com/v201110/us/idod/findpeople/response}DeceasedInfoDetailType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeceasedInfoType", propOrder = {
    "deceasedFlag",
    "deceasedInfoDetail"
})
public class DeceasedInfoType {

    @XmlElement(name = "DeceasedFlag")
    protected String deceasedFlag;
    @XmlElement(name = "DeceasedInfoDetail")
    protected List<DeceasedInfoDetailType> deceasedInfoDetail;

    /**
     * Gets the value of the deceasedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeceasedFlag() {
        return deceasedFlag;
    }

    /**
     * Sets the value of the deceasedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeceasedFlag(String value) {
        this.deceasedFlag = value;
    }

    /**
     * Gets the value of the deceasedInfoDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deceasedInfoDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeceasedInfoDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeceasedInfoDetailType }
     * 
     * 
     */
    public List<DeceasedInfoDetailType> getDeceasedInfoDetail() {
        if (deceasedInfoDetail == null) {
            deceasedInfoDetail = new ArrayList<DeceasedInfoDetailType>();
        }
        return this.deceasedInfoDetail;
    }

}