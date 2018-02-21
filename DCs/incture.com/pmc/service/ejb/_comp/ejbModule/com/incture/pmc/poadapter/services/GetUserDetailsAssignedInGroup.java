
package com.incture.pmc.poadapter.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUserDetailsAssignedInGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUserDetailsAssignedInGroup"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getUserDetailsAssignedInGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUserDetailsAssignedInGroup", propOrder = {
    "getUserDetailsAssignedInGroup"
})
public class GetUserDetailsAssignedInGroup {

    protected String getUserDetailsAssignedInGroup;

    /**
     * Gets the value of the getUserDetailsAssignedInGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetUserDetailsAssignedInGroup() {
        return getUserDetailsAssignedInGroup;
    }

    /**
     * Sets the value of the getUserDetailsAssignedInGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetUserDetailsAssignedInGroup(String value) {
        this.getUserDetailsAssignedInGroup = value;
    }

}
