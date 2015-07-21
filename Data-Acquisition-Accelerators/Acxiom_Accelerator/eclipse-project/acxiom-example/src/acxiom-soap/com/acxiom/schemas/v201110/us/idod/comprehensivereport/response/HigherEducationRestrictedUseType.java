//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:27 AM(foreman)- (JAXB RI IBM 2.2.3-07/05/2013 05:22 AM(foreman)-)
//


package com.acxiom.schemas.v201110.us.idod.comprehensivereport.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HigherEducationRestrictedUseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HigherEducationRestrictedUseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AcademicClassificationRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AcademicClassificationSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CommuterRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CommuterSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CompileYearRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CompileYearSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="FieldOfStudyRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="FieldOfStudySourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="OnCampusRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="OnCampusSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="OriginationRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="OriginationSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="RestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="SchoolNameRestrictedUseCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="SchoolNameSourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="SourceCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HigherEducationRestrictedUseType", propOrder = {
    "academicClassificationRestrictedUseCode",
    "academicClassificationSourceCode",
    "commuterRestrictedUseCode",
    "commuterSourceCode",
    "compileYearRestrictedUseCode",
    "compileYearSourceCode",
    "fieldOfStudyRestrictedUseCode",
    "fieldOfStudySourceCode",
    "onCampusRestrictedUseCode",
    "onCampusSourceCode",
    "originationRestrictedUseCode",
    "originationSourceCode",
    "restrictedUseCode",
    "schoolNameRestrictedUseCode",
    "schoolNameSourceCode",
    "sourceCode"
})
public class HigherEducationRestrictedUseType {

    @XmlElement(name = "AcademicClassificationRestrictedUseCode")
    protected Long academicClassificationRestrictedUseCode;
    @XmlElement(name = "AcademicClassificationSourceCode")
    protected Long academicClassificationSourceCode;
    @XmlElement(name = "CommuterRestrictedUseCode")
    protected Long commuterRestrictedUseCode;
    @XmlElement(name = "CommuterSourceCode")
    protected Long commuterSourceCode;
    @XmlElement(name = "CompileYearRestrictedUseCode")
    protected Long compileYearRestrictedUseCode;
    @XmlElement(name = "CompileYearSourceCode")
    protected Long compileYearSourceCode;
    @XmlElement(name = "FieldOfStudyRestrictedUseCode")
    protected Long fieldOfStudyRestrictedUseCode;
    @XmlElement(name = "FieldOfStudySourceCode")
    protected Long fieldOfStudySourceCode;
    @XmlElement(name = "OnCampusRestrictedUseCode")
    protected Long onCampusRestrictedUseCode;
    @XmlElement(name = "OnCampusSourceCode")
    protected Long onCampusSourceCode;
    @XmlElement(name = "OriginationRestrictedUseCode")
    protected Long originationRestrictedUseCode;
    @XmlElement(name = "OriginationSourceCode")
    protected Long originationSourceCode;
    @XmlElement(name = "RestrictedUseCode")
    protected Long restrictedUseCode;
    @XmlElement(name = "SchoolNameRestrictedUseCode")
    protected Long schoolNameRestrictedUseCode;
    @XmlElement(name = "SchoolNameSourceCode")
    protected Long schoolNameSourceCode;
    @XmlElement(name = "SourceCode")
    protected Long sourceCode;

    /**
     * Gets the value of the academicClassificationRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcademicClassificationRestrictedUseCode() {
        return academicClassificationRestrictedUseCode;
    }

    /**
     * Sets the value of the academicClassificationRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcademicClassificationRestrictedUseCode(Long value) {
        this.academicClassificationRestrictedUseCode = value;
    }

    /**
     * Gets the value of the academicClassificationSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcademicClassificationSourceCode() {
        return academicClassificationSourceCode;
    }

    /**
     * Sets the value of the academicClassificationSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcademicClassificationSourceCode(Long value) {
        this.academicClassificationSourceCode = value;
    }

    /**
     * Gets the value of the commuterRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCommuterRestrictedUseCode() {
        return commuterRestrictedUseCode;
    }

    /**
     * Sets the value of the commuterRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCommuterRestrictedUseCode(Long value) {
        this.commuterRestrictedUseCode = value;
    }

    /**
     * Gets the value of the commuterSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCommuterSourceCode() {
        return commuterSourceCode;
    }

    /**
     * Sets the value of the commuterSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCommuterSourceCode(Long value) {
        this.commuterSourceCode = value;
    }

    /**
     * Gets the value of the compileYearRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCompileYearRestrictedUseCode() {
        return compileYearRestrictedUseCode;
    }

    /**
     * Sets the value of the compileYearRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCompileYearRestrictedUseCode(Long value) {
        this.compileYearRestrictedUseCode = value;
    }

    /**
     * Gets the value of the compileYearSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCompileYearSourceCode() {
        return compileYearSourceCode;
    }

    /**
     * Sets the value of the compileYearSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCompileYearSourceCode(Long value) {
        this.compileYearSourceCode = value;
    }

    /**
     * Gets the value of the fieldOfStudyRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFieldOfStudyRestrictedUseCode() {
        return fieldOfStudyRestrictedUseCode;
    }

    /**
     * Sets the value of the fieldOfStudyRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFieldOfStudyRestrictedUseCode(Long value) {
        this.fieldOfStudyRestrictedUseCode = value;
    }

    /**
     * Gets the value of the fieldOfStudySourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFieldOfStudySourceCode() {
        return fieldOfStudySourceCode;
    }

    /**
     * Sets the value of the fieldOfStudySourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFieldOfStudySourceCode(Long value) {
        this.fieldOfStudySourceCode = value;
    }

    /**
     * Gets the value of the onCampusRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOnCampusRestrictedUseCode() {
        return onCampusRestrictedUseCode;
    }

    /**
     * Sets the value of the onCampusRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOnCampusRestrictedUseCode(Long value) {
        this.onCampusRestrictedUseCode = value;
    }

    /**
     * Gets the value of the onCampusSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOnCampusSourceCode() {
        return onCampusSourceCode;
    }

    /**
     * Sets the value of the onCampusSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOnCampusSourceCode(Long value) {
        this.onCampusSourceCode = value;
    }

    /**
     * Gets the value of the originationRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOriginationRestrictedUseCode() {
        return originationRestrictedUseCode;
    }

    /**
     * Sets the value of the originationRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOriginationRestrictedUseCode(Long value) {
        this.originationRestrictedUseCode = value;
    }

    /**
     * Gets the value of the originationSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOriginationSourceCode() {
        return originationSourceCode;
    }

    /**
     * Sets the value of the originationSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOriginationSourceCode(Long value) {
        this.originationSourceCode = value;
    }

    /**
     * Gets the value of the restrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRestrictedUseCode() {
        return restrictedUseCode;
    }

    /**
     * Sets the value of the restrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRestrictedUseCode(Long value) {
        this.restrictedUseCode = value;
    }

    /**
     * Gets the value of the schoolNameRestrictedUseCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSchoolNameRestrictedUseCode() {
        return schoolNameRestrictedUseCode;
    }

    /**
     * Sets the value of the schoolNameRestrictedUseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSchoolNameRestrictedUseCode(Long value) {
        this.schoolNameRestrictedUseCode = value;
    }

    /**
     * Gets the value of the schoolNameSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSchoolNameSourceCode() {
        return schoolNameSourceCode;
    }

    /**
     * Sets the value of the schoolNameSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSchoolNameSourceCode(Long value) {
        this.schoolNameSourceCode = value;
    }

    /**
     * Gets the value of the sourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSourceCode() {
        return sourceCode;
    }

    /**
     * Sets the value of the sourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSourceCode(Long value) {
        this.sourceCode = value;
    }

}