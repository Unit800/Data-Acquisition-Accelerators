//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:27 AM(foreman)- (JAXB RI IBM 2.2.3-07/05/2013 05:22 AM(foreman)-)
//


package com.acxiom.schemas.v201110.us.idod.findpeople.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AnalysisOptionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnalysisOptionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AnalyzeDriversLicense" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AnalyzePhone" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AnalyzeSsn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AnalyzeZip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnalysisOptionsType", propOrder = {
    "analyzeDriversLicense",
    "analyzePhone",
    "analyzeSsn",
    "analyzeZip"
})
public class AnalysisOptionsType {

    @XmlElement(name = "AnalyzeDriversLicense", defaultValue = "false")
    protected boolean analyzeDriversLicense;
    @XmlElement(name = "AnalyzePhone", defaultValue = "false")
    protected boolean analyzePhone;
    @XmlElement(name = "AnalyzeSsn", defaultValue = "false")
    protected boolean analyzeSsn;
    @XmlElement(name = "AnalyzeZip", defaultValue = "false")
    protected boolean analyzeZip;

    /**
     * Gets the value of the analyzeDriversLicense property.
     * 
     */
    public boolean isAnalyzeDriversLicense() {
        return analyzeDriversLicense;
    }

    /**
     * Sets the value of the analyzeDriversLicense property.
     * 
     */
    public void setAnalyzeDriversLicense(boolean value) {
        this.analyzeDriversLicense = value;
    }

    /**
     * Gets the value of the analyzePhone property.
     * 
     */
    public boolean isAnalyzePhone() {
        return analyzePhone;
    }

    /**
     * Sets the value of the analyzePhone property.
     * 
     */
    public void setAnalyzePhone(boolean value) {
        this.analyzePhone = value;
    }

    /**
     * Gets the value of the analyzeSsn property.
     * 
     */
    public boolean isAnalyzeSsn() {
        return analyzeSsn;
    }

    /**
     * Sets the value of the analyzeSsn property.
     * 
     */
    public void setAnalyzeSsn(boolean value) {
        this.analyzeSsn = value;
    }

    /**
     * Gets the value of the analyzeZip property.
     * 
     */
    public boolean isAnalyzeZip() {
        return analyzeZip;
    }

    /**
     * Sets the value of the analyzeZip property.
     * 
     */
    public void setAnalyzeZip(boolean value) {
        this.analyzeZip = value;
    }

}
