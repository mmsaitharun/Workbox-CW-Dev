<?xml version="1.0" encoding="UTF-8"?>
<public-part
		version="1.0.5"
		xmlns="http://xml.sap.com/2002/11/PublicPart"
		xmlns:IDX="urn:sap.com:PublicPart:1.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://xml.sap.com/2002/11/PublicPart ppdef.xsd">
	<name>lib_PP</name>
	<purpose>compilation</purpose>
	<entities>
		<entity-ref>
			<dc-ref>
				<name>inc/ext/libs</name>
				<vendor>incture.com</vendor>
			</dc-ref>
			<pp-ref>api</pp-ref>
			<entity-ref-id>ebc805c1-25bd-11e7-8d2f-00059a3c7a00</entity-ref-id>
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
