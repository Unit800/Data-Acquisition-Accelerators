<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2015 IBM Corp.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

Contributors:
   IBM Corp - initial API and implementation and initial documentation
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:tns="http://www.i2group.com/Schemas/2013-10-03/ModelData/Provenance">
	<xsl:output method="xml" encoding="UTF-8" indent="yes" />

	<xsl:param name="domain" />
	<xsl:param name="now" />
	
	<xsl:variable name="domain_id" select="generate-id()" />
	
	<xsl:template match="whoisapi/response">
		<QueryResult>
			<Querys></Querys>
			<Addresss></Addresss>
			<Events></Events>
			<Vehicles></Vehicles>
			<Organizations>
				<xsl:for-each select="history">
					<Organization>
						<xsl:call-template name="itemTemplate" />
						<ItemId><xsl:value-of select="generate-id(whois/registrant)" /></ItemId>
						<Cards>
							<Card>
								<xsl:call-template name="cardProvenanceTemplate" />
								<Organization_OrganizationName><xsl:value-of select="whois/registrant" /></Organization_OrganizationName>
							</Card>
						</Cards>
					</Organization>
				</xsl:for-each>
			</Organizations>
			<Persons></Persons>
			<Documents>
				<xsl:for-each select="history">
					<Document>
						<xsl:call-template name="itemTemplate" />
						<ItemId><xsl:value-of select="generate-id(whois/record)" /></ItemId>
						<Cards>
							<Card>
								<xsl:call-template name="cardProvenanceTemplate" />
								<Document_DocumentDetails>
									<Document_DocumentTitle><xsl:value-of select="$domain" /></Document_DocumentTitle>
									<Document_DocumentDescription>Whois Record for <xsl:value-of select="$domain" /> as of <xsl:value-of select="date" />.</Document_DocumentDescription>
									<Document_DocumentSource>DomainTools Whois History</Document_DocumentSource>
								</Document_DocumentDetails>
								<Document_AdditionalInformation><xsl:value-of select="whois/record" /></Document_AdditionalInformation>
							</Card>
						</Cards>
					</Document>
				</xsl:for-each>
			</Documents>
			<SurveillanceCameras></SurveillanceCameras>
			<CommunicationsDevices>
				<CommunicationsDevice>
					<xsl:call-template name="itemTemplate" />
					<ItemId><xsl:value-of select="$domain_id" /></ItemId>
					<Cards>
						<Card>
							<xsl:call-template name="cardProvenanceTemplate" />
							<CommunicationsDevice_WebAddress><xsl:value-of select="$domain" /></CommunicationsDevice_WebAddress>
						</Card>
					</Cards>
				</CommunicationsDevice>
			</CommunicationsDevices>
			<Propertys></Propertys>
			<Accounts></Accounts>
			<AlertFeedSubscriptions></AlertFeedSubscriptions>
			<InvolvedIns></InvolvedIns>
			<SubjectOfs>
				<xsl:for-each select="history">
					<xsl:call-template name="subject_of">
						<xsl:with-param name="from_id" select="generate-id(whois/registrant)" />
						<xsl:with-param name="to_id" select="generate-id(whois/record)" />
					</xsl:call-template>
					<xsl:call-template name="subject_of">
						<xsl:with-param name="from_id" select="$domain_id" />
						<xsl:with-param name="to_id" select="generate-id(whois/record)" />
					</xsl:call-template>
				</xsl:for-each>
			</SubjectOfs>
			<Observeds></Observeds>
			<Communications></Communications>
			<Associates></Associates>
			<Transactions></Transactions>
			<Stolen_Recovereds></Stolen_Recovereds>
			<AccessTos>
				<xsl:for-each select="history">
					<xsl:call-template name="access_to">
						<xsl:with-param name="from_id" select="generate-id(whois/registrant)" />
						<xsl:with-param name="to_id" select="$domain_id" />
					</xsl:call-template>
				</xsl:for-each>
			</AccessTos>
			<MemberOfs></MemberOfs>
			<Employments></Employments>
			<SubsequentActions></SubsequentActions>
			<DuplicateOfs></DuplicateOfs>
		</QueryResult>
	</xsl:template>
	
	<xsl:template name="subject_of">
		<xsl:param name="from_id" />
		<xsl:param name="to_id" />
		<SubjectOf>
			<xsl:call-template name="itemTemplate" />
			<LinkFromEndId><xsl:value-of select="$from_id" /></LinkFromEndId>
			<LinkToEndId><xsl:value-of select="$to_id" /></LinkToEndId>
			<LinkDirection>WITH</LinkDirection>
			<LinkStrength>Confirmed</LinkStrength>
			<Cards>
				<Card>
					<xsl:call-template name="cardProvenanceTemplate" />
				</Card>
			</Cards>
		</SubjectOf>
	</xsl:template>
	
	<xsl:template name="access_to">
		<xsl:param name="from_id" />
		<xsl:param name="to_id" />
		<AccessTo>
			<xsl:call-template name="itemTemplate" />
			<LinkFromEndId><xsl:value-of select="$from_id" /></LinkFromEndId>
			<LinkToEndId><xsl:value-of select="$to_id" /></LinkToEndId>
			<LinkDirection>WITH</LinkDirection>
			<LinkStrength>Confirmed</LinkStrength>
			<Cards>
				<Card>
					<xsl:call-template name="cardProvenanceTemplate" />
				</Card>
			</Cards>
		</AccessTo>
	</xsl:template>
	
	<xsl:template name="cardProvenanceTemplate">
		<tns:CardProvenance>
			<OriginId Version="1" Type="IAP.DomainTools">
				<Key>
					<String>DomainTools whois history API</String>
				</Key>
			</OriginId>
			<RetrievalBlocks></RetrievalBlocks>
		</tns:CardProvenance>
	</xsl:template>
	
	<xsl:template name="itemTemplate">
		<SecurityTagIds>
			<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
			<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
		</SecurityTagIds>
		<PropertyBags>
			<PropertyBag>
				<DateTimes>
					<DateTime><xsl:value-of select="$now" /></DateTime>
					<DateTime><xsl:value-of select="$now" /></DateTime>
				</DateTimes>
			</PropertyBag>
		</PropertyBags>
	</xsl:template>

</xsl:stylesheet>