
package com.incture.pmc.poadapter.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createSubstitutionProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createSubstitutionProfile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="profileDto" type="{http://incture.com/pmc/poadapter/services/}substitutionProfileDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createSubstitutionProfile", propOrder = {
    "profileDto"
})
public class CreateSubstitutionProfile {

    protected SubstitutionProfileDto profileDto;

    /**
     * Gets the value of the profileDto property.
     * 
     * @return
     *     possible object is
     *     {@link SubstitutionProfileDto }
     *     
     */
    public SubstitutionProfileDto getProfileDto() {
        return profileDto;
    }

    /**
     * Sets the value of the profileDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubstitutionProfileDto }
     *     
     */
    public void setProfileDto(SubstitutionProfileDto value) {
        this.profileDto = value;
    }

}
