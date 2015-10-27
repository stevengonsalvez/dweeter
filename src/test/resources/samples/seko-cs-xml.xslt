<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:env="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wsdl='http://dcswebservice/wsdl' exclude-result-prefixes="env fn wsdl xs">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<response>
			<requestId>8f3ba6f4-5c70-46ec-83af-0d5334953e55</requestId>
			<xsl:for-each select="env:Envelope/env:Body/wsdl:runUserQueryResponse/result/results">
				<updates>
					<productSkuNumber>
						<xsl:value-of select="row[1]"/>
					</productSkuNumber>
					<locationId>21</locationId>
					<operation>
						<name>set</name>
						<value><xsl:value-of select="row[2]"/></value>
					</operation>
					<reasonCode>NEWSTOCK</reasonCode>
					<userNote>New bags</userNote>
				</updates>
			</xsl:for-each>
		</response>
	</xsl:template>
</xsl:stylesheet>
