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
				<xsl:apply-templates select="domains[string-length() != 0]" />
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
			<DuplicateOfs></DuplicateOfs>
		</QueryResult>
	</xsl:template>

	<xsl:template match="domains">
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
	</xsl:template>
	
	<xsl:template name="cardProvenanceTemplate">
		<tns:CardProvenance>
			<OriginId Version="1" Type="IAP.DomainTools">
				<Key>
					<String>DomainTools reverse whois API</String>
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