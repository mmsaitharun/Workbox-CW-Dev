
package com.incture.pmc.poadapter.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for taskModelDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="taskModelDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taskModelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="taskModelName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taskModelDto", propOrder = {
    "taskModelId",
    "taskModelName"
})
@XmlRootElement
public class TaskModelDto {

    protected String taskModelId;
    protected String taskModelName;

    /**
     * Gets the value of the taskModelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskModelId() {
        return taskModelId;
    }

    /**
     * Sets the value of the taskModelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskModelId(String value) {
        this.taskModelId = value;
    }

    /**
     * Gets the value of the taskModelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskModelName() {
        return taskModelName;
    }

    /**
     * Sets the value of the taskModelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskModelName(String value) {
        this.taskModelName = value;
    }

}
