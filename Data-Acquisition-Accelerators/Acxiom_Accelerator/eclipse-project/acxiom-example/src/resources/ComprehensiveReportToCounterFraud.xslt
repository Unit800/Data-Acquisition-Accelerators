<?xml version="1.0" encoding="UTF-8"?>
<!-- Copyright (c) 2015 IBM Corp. All rights reserved. This program and the 
	accompanying materials are made available under the terms of the Eclipse 
	Public License v1.0 which accompanies this distribution, and is available 
	at http://www.eclipse.org/legal/epl-v10.html Contributors: IBM Corp - initial 
	API and implementation and initial documentation -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:ns0="http://schemas.acxiom.com/v201110/us/idod/findpeople/response"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="ns0 xs">
	<xsl:output method="xml" encoding="UTF-8" indent="yes" />
	<xsl:template match="/">
		<QueryResult xmlns:ns2="http://www.i2group.com/Schemas/2011-03-03/ModelSchema"
			xmlns:ns1="http://www.i2group.com/Schemas/2013-10-03/ModelData/Provenance">
			<xsl:attribute name="xsi:noNamespaceSchemaLocation"
				namespace="http://www.w3.org/2001/XMLSchema-instance">C:/AcxiomTest/schema/gold-schema/schema4.xsd</xsl:attribute>
			<xsl:variable name="searchID" select="//MatchResults/SearchID" />
			<Partys>
				<Party>
					<Cards>
						<Card>
							<CardId>1</CardId>
							<xsl:for-each select="//MatchResults/SearchID">
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of select="$searchID" />
											</String>
											<String>DPPA:PLACEHOLDER</String>
											<String>GLBA:PLACEHOLDER</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<Party_Id>
									<xsl:value-of select="$searchID" />
								</Party_Id>
								<xsl:for-each
									select="//MatchResults/MatchResult/Addresses/NameAddressData/Address/Name">
									<xsl:variable name="First" select="First" />
									<xsl:variable name="Last" select="Last" />
									<Party_SystemId>
										<xsl:value-of select="concat($First, ' ', $Last)" />
									</Party_SystemId>
								</xsl:for-each>
							</xsl:for-each>
						</Card>
					</Cards>
					<ItemId>
						<xsl:value-of select="$searchID" />
					</ItemId>
					<SecurityTagIds>
						<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
						<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
					</SecurityTagIds>
				</Party>
				<xsl:for-each select="//MatchResults/MatchResult/Relatives">
					<Party>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of select="IndividualId2" />
											</String>
											<String>DPPA:PLACEHOLDER</String>
											<String>GLBA:PLACEHOLDER</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<Party_Id>
									<xsl:value-of select="IndividualId2" />
								</Party_Id>
								<Party_SystemId>
									<xsl:for-each select="NameAddressData/Name">
										<xsl:variable name="First" select="First" />
										<xsl:variable name="Last" select="Last" />
										<xsl:value-of select="concat($First, ' ', $Last)" />
									</xsl:for-each>
								</Party_SystemId>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of select="IndividualId2" />
						</ItemId>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party>
				</xsl:for-each>
			</Partys>
			<Identifications>
				<xsl:for-each select="//MatchResults/MatchResult/SocialSecurityNumbers">
					<xsl:variable name="ssn" select="Ssn" />
					<Identification>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Identification</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($ssn, 'SSN'), generate-id($ssn))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<Identification_Description>
									<xsl:variable name="state" select="StateOfIssue" />
									<xsl:value-of select="concat('SSN',' State Issued: ', $state)" />
								</Identification_Description>
								<Identification_Id>
									<xsl:value-of select="$ssn" />
								</Identification_Id>
								<Identification_Type>SSN</Identification_Type>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of select="concat(concat($ssn, 'SSN'), generate-id($ssn))" />
						</ItemId>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Identification>
				</xsl:for-each>
			</Identifications>
			<Individuals>
				<xsl:for-each select="//MatchResults/MatchResult/Addresses/AssociativeData">
					<xsl:variable name="DOB" select="DateofBirth" />
					<xsl:variable name="Gender" select="Gender" />
					<Individual>
						<Cards>
							<Card>
								<CardId>1</CardId>

								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Individual</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($searchID,'Individual'), generate-id($Gender))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<SecurityTagIds>
									<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
									<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
								</SecurityTagIds>
								<Individual_BirthDate>
									<xsl:value-of
										select="concat(concat(concat(concat(concat(substring($DOB, '1', '4'), '-'), substring($DOB, '5', '2')), '-'), '01'), 'T00:00:00.000-00:00')" />
								</Individual_BirthDate>
								<Individual_Gender>
									<xsl:value-of select="Gender" />
								</Individual_Gender>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($searchID,'Individual'), generate-id($Gender))" />
						</ItemId>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Individual>
				</xsl:for-each>
			</Individuals>
			<Names>
				<xsl:for-each
					select="//MatchResults/MatchResult/Addresses/NameAddressData/Address/Name">
					<Name>
						<xsl:variable name="First" select="First" />
						<xsl:variable name="Last" select="Last" />
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Name</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($First, $Last), generate-id($First))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>

								<Name_Text>
									<xsl:value-of select="concat($First,' ' , $Last)" />
								</Name_Text>
								<Name_Type>Name</Name_Type>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($First, $Last), generate-id($First))" />
						</ItemId>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Name>
				</xsl:for-each>
				<xsl:for-each select="//MatchResults/MatchResult/Aliases/Alias">
					<Name>
					<xsl:variable name="First" select="FirstName" />
					<xsl:variable name="Last" select="LastName" />
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Name</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($First, $Last), generate-id($First))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<Name_Text>
									<xsl:value-of select="concat($First,' ' , $Last)" />
								</Name_Text>
								<Name_Type>Alias</Name_Type>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($First, $Last), generate-id($First))" />
						</ItemId>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Name>
				</xsl:for-each>
			</Names>
			<StreetAddresss>
				<xsl:for-each
					select="//MatchResults/MatchResult/Addresses/NameAddressData/Address/Address">
					<xsl:variable name="Zip" select="Zip" />
					<xsl:variable name="StreetSuffix" select="StreetSuffix" />
					<xsl:variable name="Street" select="Street" />
					<StreetAddress>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">StreetAddress</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($searchID, 'address'), generate-id($Zip))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<StreetAddress_City>
									<xsl:value-of select="City" />
								</StreetAddress_City>
								<StreetAddress_Code>
									<xsl:value-of select="Zip" />
								</StreetAddress_Code>
								<StreetAddress_County>
									<xsl:value-of select="CountyName" />
								</StreetAddress_County>
								<StreetAddress_Line1>
									<xsl:value-of select="concat($Street , ' ' , $StreetSuffix)" />
								</StreetAddress_Line1>
								<StreetAddress_Number>
									<xsl:value-of select="PrimaryNumber" />
								</StreetAddress_Number>
								<StreetAddress_State>
									<xsl:value-of select="State" />
								</StreetAddress_State>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($searchID, 'address'), generate-id($Zip))" />
						</ItemId>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</StreetAddress>
				</xsl:for-each>
			</StreetAddresss>
			<ContactPoints>
				<xsl:for-each select="//MatchResults/MatchResult/Phones">
					<xsl:variable name="Number" select="PhoneNumber" />
					<xsl:variable name="issueID" select="CardIssuerId" />
					<ContactPoint>
						<Cards>
							<Card>
								<CardId>1</CardId>

								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">ContactPoint</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat( concat($searchID,'Phone'),generate-id($Number))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<ContactPoint_Description>
									<xsl:value-of select="PhoneTypeDescription" />
								</ContactPoint_Description>
								<ContactPoint_Text>
									<xsl:value-of select="concat($issueID, '-' , $Number)" />
								</ContactPoint_Text>
								<ContactPoint_Type>Phone</ContactPoint_Type>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat( concat($searchID,'Phone'),generate-id($Number))" />
						</ItemId>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</ContactPoint>
				</xsl:for-each>
			</ContactPoints>
			<Party_Names>
				<xsl:for-each
					select="//MatchResults/MatchResult/Addresses/NameAddressData/Address/Name">
					<xsl:variable name="FirstLink" select="First" />
					<xsl:variable name="LastLink" select="Last" />
					<Party_Name>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_Name</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat($searchID,$FirstLink, generate-id($FirstLink))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat($searchID,$FirstLink, generate-id($FirstLink))" />
						</ItemId>
						<LinkFromEndId>
							<xsl:value-of select="$searchID" />
						</LinkFromEndId>
						<LinkToEndId>
							<xsl:value-of
								select="concat(concat($FirstLink, $LastLink), generate-id($FirstLink))" />
						</LinkToEndId>
						<LinkDirection>WITH</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party_Name>
				</xsl:for-each>
				<xsl:for-each select="//MatchResults/MatchResult/Aliases/Alias">
					<xsl:variable name="FirstLink" select="FirstName" />
					<xsl:variable name="LastLink" select="LastName" />
					<Party_Name>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_Name</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat($searchID,$FirstLink, generate-id($FirstLink))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat($searchID,$FirstLink, generate-id($FirstLink))" />
						</ItemId>
						<LinkFromEndId>
							<xsl:value-of select="$searchID" />
						</LinkFromEndId>
						<LinkToEndId>
							<xsl:value-of
								select="concat(concat($FirstLink, $LastLink), generate-id($FirstLink))" />
						</LinkToEndId>
						<LinkDirection>WITH</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party_Name>
				</xsl:for-each>
			</Party_Names>
			<Party_StreetAddresss>
				<xsl:for-each
					select="//MatchResults/MatchResult/Addresses/NameAddressData/Address/Address">
					<xsl:variable name="Zip" select="Zip" />
					<xsl:variable name="LastLink" select="LastName" />
					<Party_StreetAddress>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_StreetAddress</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of select="concat($searchID,$Zip, generate-id($Zip))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of select="concat($searchID,$Zip, generate-id($Zip))" />
						</ItemId>
						<LinkFromEndId>
							<xsl:value-of select="$searchID" />
						</LinkFromEndId>
						<LinkToEndId>
							<xsl:value-of
								select="concat(concat($searchID, 'address'), generate-id($Zip))" />
						</LinkToEndId>
						<LinkDirection>WITH</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party_StreetAddress>
				</xsl:for-each>
			</Party_StreetAddresss>
			<Party_ContactPoints>
				<xsl:for-each select="//MatchResults/MatchResult/Phones ">
					<xsl:variable name="Number" select="PhoneNumber" />
					<Party_ContactPoint>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_ContactPoint</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($searchID,'PhoneLink'), generate-id($Number))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($searchID,'PhoneLink'), generate-id($Number))" />
						</ItemId>
						<LinkFromEndId>
							<xsl:value-of select="$searchID" />
						</LinkFromEndId>
						<LinkToEndId>
							<xsl:value-of
								select="concat( concat($searchID,'Phone'),generate-id($Number))" />
						</LinkToEndId>
						<LinkDirection>WITH</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party_ContactPoint>
				</xsl:for-each>
			</Party_ContactPoints>
			<Party_Partys>
				<xsl:for-each select="//MatchResults/MatchResult/Relatives">
					<xsl:variable name="id" select="IndividualId2" />
					<Party_Party>
						<Cards>
							<Card>
								<CardId>
									1
								</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_Party</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($searchID,'LINKTO',$id), generate-id($id))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($searchID,'LINKTO',$id), generate-id($id))" />
						</ItemId>
						<LinkFromEndId>
							<xsl:value-of select="$searchID" />
						</LinkFromEndId>
						<LinkToEndId>
							<xsl:value-of select="$id" />
						</LinkToEndId>
						<LinkDirection>BOTH</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
						<Party_Party_Description>
							<xsl:value-of select="PossibleRelationship" />
						</Party_Party_Description>
					</Party_Party>
				</xsl:for-each>
			</Party_Partys>
			<Party_Identifications>
				<xsl:for-each select="//MatchResults/MatchResult/SocialSecurityNumbers">
					<xsl:variable name="ssn" select="Ssn" />
					<Party_Identification>
						<Cards>
							<Card>
								<CardId>
									1
								</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_Identification</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($ssn, 'SSNLINK'), generate-id($ssn))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($ssn, 'SSNLINK'), generate-id($ssn))" />
						</ItemId>
						<LinkFromEndId>
							<xsl:value-of select="$searchID" />
						</LinkFromEndId>
						<LinkToEndId>
							<xsl:value-of select="concat(concat($ssn, 'SSN'), generate-id($ssn))" />
						</LinkToEndId>
						<LinkDirection>WITH</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party_Identification>
				</xsl:for-each>
			</Party_Identifications>
			<Party_Individuals>
				<xsl:for-each select="//MatchResults/MatchResult/Addresses/AssociativeData">
					<xsl:variable name="Gender" select="Gender" />
					<Party_Individual>
						<Cards>
							<Card>
								<CardId>
									1
								</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_Individual</xsl:attribute>
										<Key>
											<String>
												<xsl:value-of
													select="concat(concat($searchID,'IndividualLink'), generate-id($Gender))" />
											</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<ItemId>
							<xsl:value-of
								select="concat(concat($searchID,'IndividualLink'), generate-id($Gender))" />
						</ItemId>
						<LinkFromEndId>
							<xsl:value-of select="$searchID" />
						</LinkFromEndId>
						<LinkToEndId>
							<xsl:value-of
								select="concat(concat($searchID,'Individual'), generate-id($Gender))" />
						</LinkToEndId>
						<LinkDirection>WITH</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party_Individual>
				</xsl:for-each>
			</Party_Individuals>
		</QueryResult>
	</xsl:template>
</xsl:stylesheet> 