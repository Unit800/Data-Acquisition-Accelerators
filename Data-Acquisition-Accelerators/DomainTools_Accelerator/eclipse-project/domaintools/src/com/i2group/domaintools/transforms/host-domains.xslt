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
	
	<xsl:param name="now" />

	<xsl:template match="whoisapi/response">
		<QueryResult>
			<Querys></Querys>
			<Addresss></Addresss>
			<Events></Events>
			<Vehicles></Vehicles>
			<Organizations></Organizations>
			<Persons></Persons>
			<Documents></Documents>
			<SurveillanceCameras></SurveillanceCameras>
			<CommunicationsDevices>
 				<xsl:apply-templates select="ip_addresses" />
			</CommunicationsDevices>
			<Propertys></Propertys>
			<Accounts></Accounts>
			<AlertFeedSubscriptions></AlertFeedSubscriptions>
			<InvolvedIns></InvolvedIns>
			<SubjectOfs></SubjectOfs>
			<Observeds></Observeds>
			<Communications></Communications>
			<Associates></Associates>
			<Transactions></Transactions>
			<Stolen_Recovereds></Stolen_Recovereds>
			<AccessTos></AccessTos>
			<MemberOfs></MemberOfs>
			<Employments></Employments>
			<SubsequentActions></SubsequentActions>
			<DuplicateOfs>
			<xsl:for-each select="ip_addresses">
				<xsl:variable name="ip_id" select="generate-id()" />
				<xsl:for-each select="domain_names">
					<xsl:call-template name="duplicate_of">
						<xsl:with-param name="from_id" select="generate-id()" />
						<xsl:with-param name="to_id" select="$ip_id" />
					</xsl:call-template>
				</xsl:for-each>
			</xsl:for-each>
			</DuplicateOfs>
		</QueryResult>
	</xsl:template>

	<xsl:template match="ip_addresses">
		<CommunicationsDevice>
			<ItemId><xsl:value-of select="generate-id()" /></ItemId>
			<xsl:call-template name="itemTemplate"/>
			<Cards>
				<Card>
                    <xsl:call-template name="cardProvenanceTemplate" />
					<CommunicationsDevice_IPAddress><xsl:value-of select="./ip_address" /></CommunicationsDevice_IPAddress>			
				</Card>
			</Cards>
		</CommunicationsDevice>
		<xsl:for-each select="domain_names">
			<CommunicationsDevice>
				<ItemId><xsl:value-of select="generate-id()" /></ItemId>
				<xsl:call-template name="itemTemplate" />
				<Cards>
					<Card>
	                    <xsl:call-template name="cardProvenanceTemplate" />
						<CommunicationsDevice_WebAddress><xsl:value-of select="current()" /></CommunicationsDevice_WebAddress>			
					</Card>
				</Cards>
			</CommunicationsDevice>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="duplicate_of">
		<xsl:param name="from_id" />
		<xsl:param name="to_id" />
		<DuplicateOf>
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
		</DuplicateOf>
	</xsl:template>
	
	<xsl:template name="cardProvenanceTemplate">
		<tns:CardProvenance>
			<OriginId Version="1" Type="IAP.DomainTools">
				<Key>
					<String>DomainTools host domains API</String>
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