<?xml version="1.0" encoding="UTF-8"?>
<public-part
		version="1.0.5"
		xmlns="http://xml.sap.com/2002/11/PublicPart"
		xmlns:IDX="urn:sap.com:PublicPart:1.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://xml.sap.com/2002/11/PublicPart ppdef.xsd">
	<name>test</name>
	<purpose>compilation</purpose>
	<entities>
		<entity-ref>
			<dc-ref>
				<name>tc/bpem/facade/ejb</name>
				<vendor>sap.com</vendor>
			</dc-ref>
			<pp-ref>test</pp-ref>
			<entity-ref-id>062e2438-1efd-11e0-c9e6-0019992f7faa</entity-ref-id>
		</entity-ref>
	</entities>
	<access-control-list>
		<grant forwarding-allowed="false">
			<dc-ref>
				<name>tc/bpem/test/scenario/auto</name>
				<vendor>test.sap.com</vendor>
			</dc-ref>
		</grant>
		<grant forwarding-allowed="false">
			<dc-ref>
				<name>tc/bpem/test/scenario/auto/ear</name>
				<vendor>test.sap.com</vendor>
			</dc-ref>
		</grant>
	</access-control-list>
</public-part>
