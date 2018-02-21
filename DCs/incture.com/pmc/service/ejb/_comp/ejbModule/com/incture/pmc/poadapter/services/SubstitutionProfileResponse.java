
package com.incture.pmc.poadapter.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for substitutionProfileResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="substitutionProfileResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="profiles" type="{http://incture.com/pmc/poadapter/services/}substitutionProfileDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="responseMessage" type="{http://incture.com/pmc/poadapter/services/}responseDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "substitutionProfileResponse", propOrder = {
    "profiles",
    "responseMessage"
})
@XmlRootElement
public class SubstitutionProfileResponse {

    @XmlElement(nillable = true)
    protected List<SubstitutionProfileDto> profiles;
    protected ResponseDto responseMessage;

    /**
     * Gets the value of the profiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubstitutionProfileDto }
     * 
     * 
     */
    public List<SubstitutionProfileDto> getProfiles() {
        if (profiles == null) {
            profiles = new ArrayList<SubstitutionProfileDto>();
        }
        return this.profiles;
    }

    /**
     * Gets the value of the responseMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseDto }
     *     
     */
    public ResponseDto getResponseMessage() {
        return responseMessage;
    }

    /**
     * Sets the value of the responseMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseDto }
     *     
     */
    public void setResponseMessage(ResponseDto value) {
        this.responseMessage = value;
    }

}
