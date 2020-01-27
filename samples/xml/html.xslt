<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<h2>Molecule</h2>
				<table border="1">
					<tr bgcolor="lightgray">
						<th align="left">Name</th>
					</tr>
					<xsl:for-each select="/compound/molecule">
						<tr>
							<td>
								<xsl:value-of select="@name"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<h3>
					Atoms of Molecule (
					<xsl:value-of select="count(/compound/molecule/atom)"/>
					)
				</h3>
				<table border="1">
					<tr>
						<th align="center">Symbol</th>
						<th align="center">Bondid</th>
					</tr>
					<xsl:for-each select="/compound/molecule/atom">
						<tr>
							<td>
								<xsl:value-of select="@name"/>
							</td>
							<td>
								<xsl:value-of select="@bondid"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<h3>
					Bonds (
					<xsl:value-of select="count(/compound/molecule/atom)"/>
					)
				</h3>
				<table border="1">
					<tr>
						<th align="center" bgcolor="lightblue">From</th>
						<th align="center" bgcolor="lightblue">Type</th>
						<th align="center" bgcolor="lightblue">To</th>
					</tr>
					<xsl:for-each select="/compound/molecule/bond">
						<tr>
							<td>
								<xsl:value-of select="@from"/>
							</td>
							<xsl:if test="@valency = 1">
								<td>
									<b>-</b>
								</td>
							</xsl:if>
							<xsl:if test="@valency = 2">
								<td>
									<b>=</b>
								</td>
							</xsl:if>
							<xsl:if test="@valency = 3">
								<td>
									<b>#</b>
								</td>
							</xsl:if>
							<td>
								<xsl:value-of select="@to"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
