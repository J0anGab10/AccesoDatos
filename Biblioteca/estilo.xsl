<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="UTF-8"/>
<xsl:template match="/">
<html><head><title>Catalogo de libros</title></head><body><h1>Catalogo de libros</h1><ul>
<xsl:for-each select="//*[local-name()='libro']">
<li><b>Titulo: </b><xsl:value-of select="*[local-name()='titulo' or local-name()='title'][1]"/>
<br/><b>Autor: </b><xsl:value-of select="*[local-name()='autor' or local-name()='author'][1]"/>
</li>
</xsl:for-each>
</ul></body></html>
</xsl:template>
</xsl:stylesheet>