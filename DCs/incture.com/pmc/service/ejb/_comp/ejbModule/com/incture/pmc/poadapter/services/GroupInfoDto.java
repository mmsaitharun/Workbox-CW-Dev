
package com.incture.pmc.poadapter.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for groupInfoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="groupInfoDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="groupDiscription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="groupUniqName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groupInfoDto", propOrder = {
    "groupDiscription",
    "groupUniqName"
})
public class GroupInfoDto {

    protected String groupDiscription;
    protected String groupUniqName;

    /**
     * Gets the value of the groupDiscription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupDiscription() {
        return groupDiscription;
    }

    /**
     * Sets the value of the groupDiscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupDiscription(String value) {
        this.groupDiscription = value;
    }

    /**
     * Gets the value of the groupUniqName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupUniqName() {
        return groupUniqName;
    }

    /**
     * Sets the value of the groupUniqName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupUniqName(String value) {
        this.groupUniqName = value;
    }

}
