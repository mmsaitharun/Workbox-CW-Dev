
package com.incture.pmc.poadapter.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for claimAndDelegate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="claimAndDelegate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taskInstanceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "claimAndDelegate", propOrder = {
    "taskInstanceId",
    "userId"
})
public class ClaimAndDelegate {

    protected String taskInstanceId;
    protected String userId;

    /**
     * Gets the value of the taskInstanceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    /**
     * Sets the value of the taskInstanceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskInstanceId(String value) {
        this.taskInstanceId = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
