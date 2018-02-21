
package com.incture.pmc.poadapter.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createRule"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ruleDto" type="{http://incture.com/pmc/poadapter/services/}substitutionRuleDto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createRule", propOrder = {
    "ruleDto"
})
public class CreateRule {

    protected SubstitutionRuleDto ruleDto;

    /**
     * Gets the value of the ruleDto property.
     * 
     * @return
     *     possible object is
     *     {@link SubstitutionRuleDto }
     *     
     */
    public SubstitutionRuleDto getRuleDto() {
        return ruleDto;
    }

    /**
     * Sets the value of the ruleDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubstitutionRuleDto }
     *     
     */
    public void setRuleDto(SubstitutionRuleDto value) {
        this.ruleDto = value;
    }

}
