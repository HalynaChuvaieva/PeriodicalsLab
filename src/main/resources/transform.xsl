<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/Paper">
        <Paper>
            <NEWSPAPER>
                <xsl:copy-of select="Periodical[Type='NEWSPAPER']"/>
            </NEWSPAPER>
            <MAGAZINE>
                <xsl:copy-of select="Periodical[Type='MAGAZINE']"/>
            </MAGAZINE>
            <BOOKLET>
                <xsl:copy-of select="Periodical[Type='BOOKLET']"/>
            </BOOKLET>
        </Paper>
    </xsl:template>
</xsl:stylesheet>