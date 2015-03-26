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
			<Partys>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
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
												<xsl:value-of select="IndividualId" />
											</String>
											<String>DPPA:PLACEHOLDER</String>
											<String>GLBA:PLACEHOLDER</String>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<Party_Id>
									<xsl:for-each select="IndividualId">
										<xsl:value-of select="string(.)" />
									</xsl:for-each>
								</Party_Id>
								<Party_SystemId>
									<xsl:for-each select="Name/Name">
										<xsl:variable name="First" select="First" />
										<xsl:variable name="Last" select="Last" />
										<xsl:value-of select="concat($First, ' ', $Last)" />
									</xsl:for-each>
								</Party_SystemId>
							</Card>
						</Cards>
						<xsl:for-each select="IndividualId">
							<ItemId>
								<xsl:value-of select="string(.)" />
							</ItemId>
						</xsl:for-each>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
					</Party>
				</xsl:for-each>
			</Partys>
			<Names>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var10_cur" select="." />
					<xsl:for-each select="Name">
						<xsl:variable name="var9_cur" select="." />
						<xsl:for-each select="Name">
							<xsl:variable name="var8_cur" select="." />
							<xsl:variable name="First" select="FirstName" />
							<xsl:variable name="Last" select="LastName" />
							<Name>
								<Cards>
									<Card>
										<CardId>1</CardId>
										<ns1:CardProvenance>
											<RetrievalBlocks>
												<xsl:value-of select="/.." />
											</RetrievalBlocks>
											<OriginId>
												<xsl:attribute name="Version">1</xsl:attribute>
												<xsl:attribute name="Type">Name</xsl:attribute>
												<Key>
													<xsl:for-each select="$var10_cur/IndividualId">
														<String>
															<xsl:value-of
																select="concat(concat(string(.), 'name'), generate-id($var9_cur))" />
														</String>
													</xsl:for-each>
												</Key>
											</OriginId>
										</ns1:CardProvenance>
										<xsl:for-each select="Last">
											<xsl:variable name="var7_cur" select="." />
											<xsl:for-each select="$var8_cur/First">
												<xsl:variable name="var1_Middle" select="$var8_cur/Middle" />
												<xsl:variable name="var2_GenerationalSuffix"
													select="$var8_cur/GenerationalSuffix" />
												<xsl:variable name="var3_Middle">
													<xsl:if test="$var8_cur/Middle">
														<xsl:value-of select="'1'" />
													</xsl:if>
												</xsl:variable>
												<xsl:variable name="var4_result">
													<xsl:choose>
														<xsl:when
															test="string(boolean(string($var3_Middle))) != 'false'">
															<xsl:value-of select="string($var1_Middle)" />
														</xsl:when>
														<xsl:otherwise>
															<xsl:value-of select="' '" />
														</xsl:otherwise>
													</xsl:choose>
												</xsl:variable>
												<xsl:variable name="var5_GenerationalSuffix">
													<xsl:if test="$var8_cur/GenerationalSuffix">
														<xsl:value-of select="'1'" />
													</xsl:if>
												</xsl:variable>
												<xsl:variable name="var6_result">
													<xsl:choose>
														<xsl:when
															test="string(boolean(string($var5_GenerationalSuffix))) != 'false'">
															<xsl:value-of select="string($var2_GenerationalSuffix)" />
														</xsl:when>
														<xsl:otherwise>
															<xsl:value-of select="' '" />
														</xsl:otherwise>
													</xsl:choose>
												</xsl:variable>
												<Name_Text>
													<xsl:value-of select="concat($var7_cur, ', ', string(.))" />
												</Name_Text>
											</xsl:for-each>
										</xsl:for-each>
										<Name_Type>Last, First, Middle, Gen</Name_Type>
									</Card>
								</Cards>
								<xsl:for-each select="$var10_cur/IndividualId">
									<ItemId>
										<xsl:value-of
											select="concat(concat(string(.), 'name'), generate-id($var9_cur))" />
									</ItemId>
								</xsl:for-each>
								<SecurityTagIds>
									<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
									<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
								</SecurityTagIds>
							</Name>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:for-each>
			</Names>
			<Individuals>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<Individual>
						<xsl:for-each select="IndividualId">
							<ItemId>
								<xsl:value-of select="concat(string(.), 'Individual')" />
							</ItemId>
						</xsl:for-each>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
										<xsl:value-of select="/.." />
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Individual</xsl:attribute>
										<Key>
											<xsl:for-each select="IndividualId">
												<String>
													<xsl:value-of select="string(.)" />
												</String>
											</xsl:for-each>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
								<xsl:for-each select="DOB/DateOfBirth">
									<xsl:variable name="var11_resultof_cast" select="string(.)" />
									<Individual_BirthDate>
										<xsl:value-of
											select="concat(concat(concat(concat(concat(substring($var11_resultof_cast, '1', '4'), '-'), substring($var11_resultof_cast, '5', '2')), '-'), '01'), 'T00:00:00.000-00:00')" />
									</Individual_BirthDate>
								</xsl:for-each>
								<xsl:for-each select="Gender">
									<Individual_Gender>
										<xsl:value-of select="string(.)" />
									</Individual_Gender>
								</xsl:for-each>
							</Card>
						</Cards>
					</Individual>
				</xsl:for-each>
			</Individuals>
			<Identifications>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var23_cur" select="." />
					<xsl:for-each select="SSN">
						<xsl:variable name="var22_cur" select="." />
						<xsl:for-each select="SSN">
							<xsl:variable name="var21_cur" select="." />
							<xsl:variable name="var12_resultof_cast" select="string(.)" />
							<Identification>
								<xsl:for-each select="$var23_cur/IndividualId">
									<ItemId>
										<xsl:value-of
											select="concat(concat(string(.), 'SSN'), generate-id($var21_cur))" />
									</ItemId>
								</xsl:for-each>
								<SecurityTagIds>
									<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
									<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
								</SecurityTagIds>
								<Cards>
									<Card>
										<CardId>1</CardId>
										<ns1:CardProvenance>
											<RetrievalBlocks>
												<xsl:value-of select="/.." />
											</RetrievalBlocks>
											<OriginId>
												<xsl:attribute name="Version">1</xsl:attribute>
												<xsl:attribute name="Type">Identification</xsl:attribute>
												<Key>
													<String>
														<xsl:value-of select="$var12_resultof_cast" />
													</String>
												</Key>
											</OriginId>
										</ns1:CardProvenance>
										<xsl:for-each select="$var22_cur/SourceCode">
											<xsl:variable name="var20_cur" select="." />
											<xsl:for-each select="$var22_cur/MaxYearOfIssue">
												<xsl:variable name="var19_cur" select="." />
												<xsl:for-each select="$var22_cur/NumberInvalidSsns">
													<xsl:variable name="var18_cur" select="." />
													<xsl:for-each select="$var22_cur/NumberOfIndividualsWithSsn">
														<xsl:variable name="var17_cur" select="." />
														<xsl:for-each select="$var22_cur/NumberUniqueSsns">
															<xsl:variable name="var16_cur" select="." />
															<xsl:for-each select="$var22_cur/NumberValidSsns">
																<xsl:variable name="var15_cur" select="." />
																<xsl:for-each select="$var22_cur/SsnXlScore">
																	<xsl:variable name="var14_cur" select="." />
																	<xsl:for-each select="$var22_cur/StateOfIssue">
																		<xsl:variable name="var13_cur" select="." />
																		<xsl:for-each select="$var22_cur/ValidSsnFlag">
																			<Identification_Description>
																				<xsl:value-of
																					select="concat(concat(concat(concat(concat(concat(concat(concat(concat(concat(concat(concat(concat(concat(concat(concat(concat('SourceCode: ', string(floor(number(string($var20_cur))))), '  MaxYearOfIssue: '), string($var19_cur)), '  NumerInvalidSsns: '), string(floor(number(string($var18_cur))))), '  NumberOfIndividuals: '), string(floor(number(string($var17_cur))))), '  NumberUniqueSsns: '), string(floor(number(string($var16_cur))))), '  NumberValidSsns: '), string(floor(number(string($var15_cur))))), '  SsnXIScore: '), string(floor(number(string($var14_cur))))), '  StateOfIssue: '), string($var13_cur)), '  ValidSsnFlag: '), string(.))" />
																			</Identification_Description>
																		</xsl:for-each>
																	</xsl:for-each>
																</xsl:for-each>
															</xsl:for-each>
														</xsl:for-each>
													</xsl:for-each>
												</xsl:for-each>
											</xsl:for-each>
										</xsl:for-each>
										<Identification_Id>
											<xsl:value-of select="$var12_resultof_cast" />
										</Identification_Id>
										<Identification_Type>SSN</Identification_Type>
									</Card>
								</Cards>
							</Identification>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:for-each>
			</Identifications>
			<StreetAddresss>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var28_cur" select="." />
					<xsl:for-each select="Address">
						<xsl:variable name="var27_cur" select="." />
						<StreetAddress>
							<xsl:for-each select="$var28_cur/IndividualId">
								<ItemId>
									<xsl:value-of
										select="concat(concat(string(.), 'address'), generate-id($var27_cur))" />
								</ItemId>
							</xsl:for-each>
							<SecurityTagIds>
								<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
								<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
							</SecurityTagIds>
							<Cards>
								<Card>
									<CardId>1</CardId>
									<ns1:CardProvenance>
										<RetrievalBlocks>
											<xsl:value-of select="/.." />
										</RetrievalBlocks>
										<OriginId>
											<xsl:attribute name="Version">1</xsl:attribute>
											<xsl:attribute name="Type">StreetAddress</xsl:attribute>
											<Key>
												<xsl:for-each select="$var28_cur/IndividualId">
													<String>
														<xsl:value-of
															select="concat(concat(string(.), 'address'), generate-id($var27_cur))" />
													</String>
												</xsl:for-each>
											</Key>
										</OriginId>
									</ns1:CardProvenance>
									<xsl:for-each select="City">
										<StreetAddress_City>
											<xsl:value-of select="string(.)" />
										</StreetAddress_City>
									</xsl:for-each>
									<xsl:for-each select="Zip">
										<xsl:variable name="var24_cur" select="." />
										<xsl:for-each select="$var27_cur/Zip4">
											<StreetAddress_Code>
												<xsl:value-of
													select="concat(concat(string($var24_cur), '-'), string(.))" />
											</StreetAddress_Code>
										</xsl:for-each>
									</xsl:for-each>
									<xsl:for-each select="County">
										<StreetAddress_County>
											<xsl:value-of select="string(.)" />
										</StreetAddress_County>
									</xsl:for-each>
									<xsl:for-each select="PrimaryNumber">
										<xsl:variable name="var26_cur" select="." />
										<xsl:for-each select="$var27_cur/Street">
											<xsl:variable name="var25_cur" select="." />
											<xsl:for-each select="$var27_cur/StreetSuffix">
												<StreetAddress_Line1>
													<xsl:value-of
														select="concat(concat(concat(concat(string($var26_cur), ' '), string($var25_cur)), ' '), string(.))" />
												</StreetAddress_Line1>
											</xsl:for-each>
										</xsl:for-each>
									</xsl:for-each>
									<xsl:for-each select="PrimaryNumber">
										<StreetAddress_Number>
											<xsl:value-of select="string(.)" />
										</StreetAddress_Number>
									</xsl:for-each>
									<xsl:for-each select="State">
										<StreetAddress_State>
											<xsl:value-of select="string(.)" />
										</StreetAddress_State>
									</xsl:for-each>
								</Card>
							</Cards>
						</StreetAddress>
					</xsl:for-each>
				</xsl:for-each>
			</StreetAddresss>
			<ContactPoints>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var31_cur" select="." />
					<xsl:for-each select="Phones/Phone">
						<xsl:variable name="var30_cur" select="." />
						<ContactPoint>
							<xsl:for-each select="$var31_cur/IndividualId">
								<ItemId>
									<xsl:value-of
										select="concat(concat(string(.), 'Phone'), generate-id($var30_cur))" />
								</ItemId>
							</xsl:for-each>
							<SecurityTagIds>
								<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
								<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
							</SecurityTagIds>
							<Cards>
								<Card>
									<CardId>1</CardId>
									<ns1:CardProvenance>
										<RetrievalBlocks>
											<xsl:value-of select="/.." />
										</RetrievalBlocks>
										<OriginId>
											<xsl:attribute name="Version">1</xsl:attribute>
											<xsl:attribute name="Type">ContactPoint</xsl:attribute>
										</OriginId>
									</ns1:CardProvenance>
									<xsl:for-each select="NXXType">
										<ContactPoint_Description>
											<xsl:value-of select="string(.)" />
										</ContactPoint_Description>
									</xsl:for-each>
									<xsl:for-each select="AreaCode">
										<xsl:variable name="var29_cur" select="." />
										<xsl:for-each select="$var30_cur/PhoneNumber">
											<ContactPoint_Text>
												<xsl:value-of
													select="concat(concat(string($var29_cur), '-'), string(.))" />
											</ContactPoint_Text>
										</xsl:for-each>
									</xsl:for-each>
									<ContactPoint_Type>Phone</ContactPoint_Type>
								</Card>
							</Cards>
						</ContactPoint>
					</xsl:for-each>
				</xsl:for-each>
			</ContactPoints>
			<Party_Names>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var35_cur" select="." />
					<xsl:for-each select="Name">
						<xsl:variable name="var34_cur" select="." />
						<xsl:for-each select="Name">
							<Party_Name>
								<xsl:for-each select="$var35_cur/IndividualId">
									<xsl:variable name="var32_resultof_cast" select="string(.)" />
									<ItemId>
										<xsl:value-of
											select="concat(concat($var32_resultof_cast, concat(concat($var32_resultof_cast, 'name'), generate-id($var34_cur))), 'PartyName')" />
									</ItemId>
								</xsl:for-each>
								<SecurityTagIds>
									<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
									<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
								</SecurityTagIds>
								<xsl:for-each select="$var35_cur/IndividualId">
									<LinkFromEndId>
										<xsl:value-of select="string(.)" />
									</LinkFromEndId>
								</xsl:for-each>
								<xsl:for-each select="$var35_cur/IndividualId">
									<LinkToEndId>
										<xsl:value-of
											select="concat(concat(string(.), 'name'), generate-id($var34_cur))" />
									</LinkToEndId>
								</xsl:for-each>
								<LinkDirection>NONE</LinkDirection>
								<LinkStrength>Confirmed</LinkStrength>
								<Cards>
									<Card>
										<CardId>1</CardId>
										<ns1:CardProvenance>
											<RetrievalBlocks>
												<xsl:value-of select="/.." />
											</RetrievalBlocks>
											<OriginId>
												<xsl:attribute name="Version">1</xsl:attribute>
												<xsl:attribute name="Type">Party_Name</xsl:attribute>
												<Key>
													<xsl:for-each select="$var35_cur/IndividualId">
														<xsl:variable name="var33_resultof_cast"
															select="string(.)" />
														<String>
															<xsl:value-of
																select="concat(concat($var33_resultof_cast, concat(concat($var33_resultof_cast, 'name'), generate-id($var34_cur))), 'PartyName')" />
														</String>
													</xsl:for-each>
												</Key>
											</OriginId>
										</ns1:CardProvenance>
									</Card>
								</Cards>
							</Party_Name>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:for-each>
			</Party_Names>
			<Party_StreetAddresss>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var39_cur" select="." />
					<xsl:for-each select="Address">
						<xsl:variable name="var38_cur" select="." />
						<Party_StreetAddress>
							<Cards>
								<Card>
									<CardId>1</CardId>
									<ns1:CardProvenance>
										<RetrievalBlocks>
											<xsl:value-of select="/.." />
										</RetrievalBlocks>
										<OriginId>
											<xsl:attribute name="Version">1</xsl:attribute>
											<xsl:attribute name="Type">Party_StreetAddress</xsl:attribute>
											<Key>
												<xsl:for-each select="$var39_cur/IndividualId">
													<xsl:variable name="var37_resultof_cast"
														select="string(.)" />
													<String>
														<xsl:value-of
															select="concat($var37_resultof_cast, concat(concat($var37_resultof_cast, 'address'), generate-id($var38_cur)))" />
													</String>
												</xsl:for-each>
											</Key>
										</OriginId>
									</ns1:CardProvenance>
								</Card>
							</Cards>
							<xsl:for-each select="$var39_cur/IndividualId">
								<LinkFromEndId>
									<xsl:value-of select="string(.)" />
								</LinkFromEndId>
							</xsl:for-each>
							<xsl:for-each select="$var39_cur/IndividualId">
								<LinkToEndId>
									<xsl:value-of
										select="concat(concat(string(.), 'address'), generate-id($var38_cur))" />
								</LinkToEndId>
							</xsl:for-each>
							<LinkDirection>NONE</LinkDirection>
							<LinkStrength>Confirmed</LinkStrength>
							<xsl:for-each select="$var39_cur/IndividualId">
								<xsl:variable name="var36_resultof_cast" select="string(.)" />
								<ItemId>
									<xsl:value-of
										select="concat($var36_resultof_cast, concat(concat($var36_resultof_cast, 'address'), generate-id($var38_cur)))" />
								</ItemId>
							</xsl:for-each>
							<SecurityTagIds>
								<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
								<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
							</SecurityTagIds>
						</Party_StreetAddress>
					</xsl:for-each>
				</xsl:for-each>
			</Party_StreetAddresss>
			<Party_Identifications>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var43_cur" select="." />
					<xsl:for-each select="SSN/SSN">
						<xsl:variable name="var42_cur" select="." />
						<Party_Identification>
							<Cards>
								<Card>
									<CardId>1</CardId>
									<ns1:CardProvenance>
										<RetrievalBlocks>
											<xsl:value-of select="/.." />
										</RetrievalBlocks>
										<OriginId>
											<xsl:attribute name="Version">1</xsl:attribute>
											<xsl:attribute name="Type">Party_Identification</xsl:attribute>
											<Key>
												<xsl:for-each select="$var43_cur/IndividualId">
													<xsl:variable name="var41_resultof_cast"
														select="string(.)" />
													<String>
														<xsl:value-of
															select="concat($var41_resultof_cast, concat(concat($var41_resultof_cast, 'SSN'), generate-id($var42_cur)))" />
													</String>
												</xsl:for-each>
											</Key>
										</OriginId>
									</ns1:CardProvenance>
								</Card>
							</Cards>
							<xsl:for-each select="$var43_cur/IndividualId">
								<LinkFromEndId>
									<xsl:value-of select="string(.)" />
								</LinkFromEndId>
							</xsl:for-each>
							<xsl:for-each select="$var43_cur/IndividualId">
								<LinkToEndId>
									<xsl:value-of
										select="concat(concat(string(.), 'SSN'), generate-id($var42_cur))" />
								</LinkToEndId>
							</xsl:for-each>
							<LinkDirection>NONE</LinkDirection>
							<LinkStrength>Confirmed</LinkStrength>
							<xsl:for-each select="$var43_cur/IndividualId">
								<xsl:variable name="var40_resultof_cast" select="string(.)" />
								<ItemId>
									<xsl:value-of
										select="concat($var40_resultof_cast, concat(concat($var40_resultof_cast, 'SSN'), generate-id($var42_cur)))" />
								</ItemId>
							</xsl:for-each>
							<SecurityTagIds>
								<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
								<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
							</SecurityTagIds>
						</Party_Identification>
					</xsl:for-each>
				</xsl:for-each>
			</Party_Identifications>
			<Party_ContactPoints>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<xsl:variable name="var47_cur" select="." />
					<xsl:for-each select="Phones/Phone">
						<xsl:variable name="var46_cur" select="." />
						<Party_ContactPoint>
							<Cards>
								<Card>
									<CardId>1</CardId>
									<ns1:CardProvenance>
										<RetrievalBlocks>
											<xsl:value-of select="/.." />
										</RetrievalBlocks>
										<OriginId>
											<xsl:attribute name="Version">1</xsl:attribute>
											<xsl:attribute name="Type">Party_ContactPoint</xsl:attribute>
											<Key>
												<xsl:for-each select="$var47_cur/IndividualId">
													<xsl:variable name="var45_resultof_cast"
														select="string(.)" />
													<String>
														<xsl:value-of
															select="concat($var45_resultof_cast, concat(concat($var45_resultof_cast, 'Phone'), generate-id($var46_cur)))" />
													</String>
												</xsl:for-each>
											</Key>
										</OriginId>
									</ns1:CardProvenance>
								</Card>
							</Cards>
							<xsl:for-each select="$var47_cur/IndividualId">
								<LinkFromEndId>
									<xsl:value-of select="concat(string(.), 'Party')" />
								</LinkFromEndId>
							</xsl:for-each>
							<xsl:for-each select="$var47_cur/IndividualId">
								<LinkToEndId>
									<xsl:value-of
										select="concat(concat(string(.), 'Phone'), generate-id($var46_cur))" />
								</LinkToEndId>
							</xsl:for-each>
							<LinkDirection>NONE</LinkDirection>
							<LinkStrength>Confirmed</LinkStrength>
							<xsl:for-each select="$var47_cur/IndividualId">
								<xsl:variable name="var44_resultof_cast" select="string(.)" />
								<ItemId>
									<xsl:value-of
										select="concat($var44_resultof_cast, concat(concat($var44_resultof_cast, 'Phone'), generate-id($var46_cur)))" />
								</ItemId>
							</xsl:for-each>
							<SecurityTagIds>
								<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
								<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
							</SecurityTagIds>
						</Party_ContactPoint>
					</xsl:for-each>
				</xsl:for-each>
			</Party_ContactPoints>
			<Party_Individuals>
				<xsl:for-each select="ns0:responseType/MatchResults/Individuals/Individual">
					<Party_Individual>
						<Cards>
							<Card>
								<CardId>1</CardId>
								<ns1:CardProvenance>
									<RetrievalBlocks>
										<xsl:value-of select="/.." />
									</RetrievalBlocks>
									<OriginId>
										<xsl:attribute name="Version">1</xsl:attribute>
										<xsl:attribute name="Type">Party_Individual</xsl:attribute>
										<Key>
											<xsl:for-each select="IndividualId">
												<xsl:variable name="var49_resultof_cast"
													select="string(.)" />
												<String>
													<xsl:value-of
														select="concat($var49_resultof_cast, concat($var49_resultof_cast, 'Individual'))" />
												</String>
											</xsl:for-each>
										</Key>
									</OriginId>
								</ns1:CardProvenance>
							</Card>
						</Cards>
						<xsl:for-each select="IndividualId">
							<xsl:variable name="var48_resultof_cast" select="string(.)" />
							<ItemId>
								<xsl:value-of
									select="concat($var48_resultof_cast, concat($var48_resultof_cast, 'Individual'))" />
							</ItemId>
						</xsl:for-each>
						<SecurityTagIds>
							<SecurityTagId>d3cdf9a0-1164-11e3-8ffd-0800200c9a66</SecurityTagId>
							<SecurityTagId>6f0a69d4-6edd-40ec-a372-c6db33262a58</SecurityTagId>
						</SecurityTagIds>
						<xsl:for-each select="IndividualId">
							<LinkFromEndId>
								<xsl:value-of select="string(.)" />
							</LinkFromEndId>
						</xsl:for-each>
						<xsl:for-each select="IndividualId">
							<LinkToEndId>
								<xsl:value-of select="concat(string(.), 'Individual')" />
							</LinkToEndId>
						</xsl:for-each>
						<LinkDirection>NONE</LinkDirection>
						<LinkStrength>Confirmed</LinkStrength>
					</Party_Individual>
				</xsl:for-each>
			</Party_Individuals>
		</QueryResult>
	</xsl:template>
</xsl:stylesheet>
