<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:env="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wsdl='http://dcswebservice/wsdl' exclude-result-prefixes="env fn wsdl xs">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:param name="requestId"/>
	<xsl:param name="sekoLocation"/>
	<xsl:template match="/">
		<response>
			<requestId>
				<xsl:value-of select="$requestId"/>
			</requestId>
			<xsl:for-each select="env:Envelope/env:Body/wsdl:runUserQueryResponse/result/results">
				<updates>
					<productSkuNumber>
						<xsl:value-of select="row[1]"/>
					</productSkuNumber>
					<locationId>
						<xsl:value-of select="$sekoLocation"/>
					</locationId>
					<operation>
						<name>set</name>
						<value>
							<xsl:choose>
								<xsl:when test="string(number(row[2])) = 'NaN' or starts-with(string(row[2]), '-')">0</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="abs(row[2])"/>
								</xsl:otherwise>
							</xsl:choose>
						</value>
					</operation>
					<reasonCode>OTHER</reasonCode>
					<userNote>Seko automatic stock level synchronisation</userNote>
				</updates>
			</xsl:for-each>
		</response>
	</xsl:template>
</xsl:stylesheet>
