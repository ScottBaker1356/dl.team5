<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:json="http://json.org/">
  <xsl:template match="node() | @*">
    <xsl:copy>
      <xsl:apply-templates select="node() | @*"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="
        //phoneNumbers |
        //emailAddresses

    ">
    <xsl:copy>
      <xsl:apply-templates select="@*"/>
      <xsl:attribute name="json:force-array">true</xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="
    /data/id[text()] |
    /data/policyNumber[text()] |
    //limits//name[text()]
    ">
    <xsl:copy>
      <xsl:apply-templates select="@*"/>
      <xsl:attribute name="json:force-string">true</xsl:attribute>
      <xsl:apply-templates select="node()"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>