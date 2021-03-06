//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:27 AM(foreman)- (JAXB RI IBM 2.2.3-07/05/2013 05:22 AM(foreman)-)
//


package com.acxiom.schemas.v201110.us.idod.comprehensivereport.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PeopleAssociativeRestrictedUseInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PeopleAssociativeRestrictedUseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeceasedRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DeceasedSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DobRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DobSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="EthnicityRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="EthnicitySourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="EyeColorRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="EyeColorSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="HairColorRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="HairColorSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="HeightRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="HeightSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="HighSchoolGradYrRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="HighSchoolGradYrSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PeopleAssociativeRestrictedUseInfo", propOrder = {
    "deceasedRestrictedUseCode",
    "deceasedSourceCode",
    "dobRestrictedUseCode",
    "dobSourceCode",
    "ethnicityRestrictedUseCode",
    "ethnicitySourceCode",
    "eyeColorRestrictedUseCode",
    "eyeColorSourceCode",
    "hairColorRestrictedUseCode",
    "hairColorSourceCode",
    "heightRestrictedUseCode",
    "heightSourceCode",
    "highSchoolGradYrRestrictedUseCode",
    "highSchoolGradYrSourceCode"
})
public class PeopleAssociativeRestrictedUseInfo {

    @XmlElement(name = "DeceasedRestrictedUseCode")
    protected Long deceasedRestrictedUseCode;
    @XmlElement(name = "DeceasedSourceCode")
    protected Long deceasedSourceCode;
    @XmlElement(name = "DobRestrictedUseCode")
    protected Long dobRestrictedUseCode;
    @XmlElement(name = "DobSourceCode")
    protected Long dobSourceCode;
    @XmlElement(name = "EthnicityRestrictedUseCode")
    protected Long ethnicityRestrictedUseCode;
    @XmlElement(name = "EthnicitySourceCode")
    protected Long ethnicitySourceCode;
    @XmlElement(name = "EyeColorRestrictedUseCode")
    protected Long eyeColorRestrictedUseCode;
    @XmlElement(name = "EyeColorSourceCode")
    protected Long eyeColorSourceCode;
    @XmlElement(name = "HairColorRestrictedUseCode")
    protected Long hairColorRestrictedUseCode;
    @XmlElement(name = "HairColorSourceCode")
    protected Long hairColorSourceCode;
    @XmlElement(name = "HeightRestrictedUseCode")
    protected Long heightRestrictedUseCode;
    @XmlElement(name = "HeightSourceCode")
    protected Long heightSourceCode;
    @XmlElement(name = "HighSchoolGradYrRestrictedUseCode")
    protected Long highSchoolGradYrRestrictedUseCode;
    @XmlElement(name = "HighSchoolGradYrSourceCode")
    protected Long highSchoolGradYrSourceCode;

    /**
     * Gets the value of the deceasedRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDeceasedRestrictedUseCode() {
        return deceasedRestrictedUseCode;
    }

    /**
     * Sets the value of the deceasedRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDeceasedRestrictedUseCode(Long value) {
        this.deceasedRestrictedUseCode = value;
    }

    /**
     * Gets the value of the deceasedSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDeceasedSourceCode() {
        return deceasedSourceCode;
    }

    /**
     * Sets the value of the deceasedSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDeceasedSourceCode(Long value) {
        this.deceasedSourceCode = value;
    }

    /**
     * Gets the value of the dobRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDobRestrictedUseCode() {
        return dobRestrictedUseCode;
    }

    /**
     * Sets the value of the dobRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDobRestrictedUseCode(Long value) {
        this.dobRestrictedUseCode = value;
    }

    /**
     * Gets the value of the dobSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDobSourceCode() {
        return dobSourceCode;
    }

    /**
     * Sets the value of the dobSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDobSourceCode(Long value) {
        this.dobSourceCode = value;
    }

    /**
     * Gets the value of the ethnicityRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEthnicityRestrictedUseCode() {
        return ethnicityRestrictedUseCode;
    }

    /**
     * Sets the value of the ethnicityRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEthnicityRestrictedUseCode(Long value) {
        this.ethnicityRestrictedUseCode = value;
    }

    /**
     * Gets the value of the ethnicitySourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEthnicitySourceCode() {
        return ethnicitySourceCode;
    }

    /**
     * Sets the value of the ethnicitySourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEthnicitySourceCode(Long value) {
        this.ethnicitySourceCode = value;
    }

    /**
     * Gets the value of the eyeColorRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEyeColorRestrictedUseCode() {
        return eyeColorRestrictedUseCode;
    }

    /**
     * Sets the value of the eyeColorRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEyeColorRestrictedUseCode(Long value) {
        this.eyeColorRestrictedUseCode = value;
    }

    /**
     * Gets the value of the eyeColorSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEyeColorSourceCode() {
        return eyeColorSourceCode;
    }

    /**
     * Sets the value of the eyeColorSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEyeColorSourceCode(Long value) {
        this.eyeColorSourceCode = value;
    }

    /**
     * Gets the value of the hairColorRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHairColorRestrictedUseCode() {
        return hairColorRestrictedUseCode;
    }

    /**
     * Sets the value of the hairColorRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHairColorRestrictedUseCode(Long value) {
        this.hairColorRestrictedUseCode = value;
    }

    /**
     * Gets the value of the hairColorSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHairColorSourceCode() {
        return hairColorSourceCode;
    }

    /**
     * Sets the value of the hairColorSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHairColorSourceCode(Long value) {
        this.hairColorSourceCode = value;
    }

    /**
     * Gets the value of the heightRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHeightRestrictedUseCode() {
        return heightRestrictedUseCode;
    }

    /**
     * Sets the value of the heightRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHeightRestrictedUseCode(Long value) {
        this.heightRestrictedUseCode = value;
    }

    /**
     * Gets the value of the heightSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHeightSourceCode() {
        return heightSourceCode;
    }

    /**
     * Sets the value of the heightSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHeightSourceCode(Long value) {
        this.heightSourceCode = value;
    }

    /**
     * Gets the value of the highSchoolGradYrRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHighSchoolGradYrRestrictedUseCode() {
        return highSchoolGradYrRestrictedUseCode;
    }

    /**
     * Sets the value of the highSchoolGradYrRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHighSchoolGradYrRestrictedUseCode(Long value) {
        this.highSchoolGradYrRestrictedUseCode = value;
    }

    /**
     * Gets the value of the highSchoolGradYrSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHighSchoolGradYrSourceCode() {
        return highSchoolGradYrSourceCode;
    }

    /**
     * Sets the value of the highSchoolGradYrSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHighSchoolGradYrSourceCode(Long value) {
        this.highSchoolGradYrSourceCode = value;
    }

}
