<?xml version="1.0" encoding="UTF-8"?>
<public-part
		version="1.0.5"
		xmlns="http://xml.sap.com/2002/11/PublicPart"
		xmlns:IDX="urn:sap.com:PublicPart:1.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://xml.sap.com/2002/11/PublicPart ppdef.xsd">
	<name>Service_PP</name>
	<purpose>compilation</purpose>
	<entities>
		<entity-ref>
			<dc-ref>
				<name>pmc/po/adapter/services/ejb</name>
				<vendor>incture.com</vendor>
			</dc-ref>
			<pp-ref>client</pp-ref>
			<entity-ref-id>3888cc6c-4470-11e7-b04a-00059a3c7a00</entity-ref-id>
		</entity-ref>
	</entities>
	<access-control-list>
		<grant forwarding-allowed="true">
			<dc-ref>
				<name>*</name>
				<vendor>*</vendor>
			</dc-ref>
		</grant>
	</access-control-list>
</public-part>
