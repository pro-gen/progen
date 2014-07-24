<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:variable name="cvsweb">http://doc.ece.uci.edu/cvs/viewcvs.cgi/Zen/packages/src/</xsl:variable>

<xsl:template match="pmd">
<html>
<head>
    <title>PMD Report</title>
    <style type="text/css">
        body, td, select, input, li{
          font-family: Verdana, Helvetica, Arial, sans-serif;
          font-size: 13px;
        }
        h1 {
          border: 1px solid #999;
          color: #900;
          background-color: #ddd;
          font-weight:900;
          font-size: xx-large;
        }
        h2 {
          border: 1px solid #999;
          color: #900;
          background-color: #ddd;
          font-weight:900;
          font-size: x-large;
        }
        h3 {
          padding: 4px 4px 4px 6px;
          border: 1px solid #aaa;
          color: #900;
          background-color: #eee;
          font-weight: normal;
          font-size: large;
        }
        h4 {
          padding: 4px 4px 4px 6px;
          border: 1px solid #bbb;
          color: #900;
          background-color: #fff;
          font-weight: normal;
          font-size: large;
        }
        h5 {
          padding: 4px 4px 4px 6px;
          color: #900;
          font-size: normal;
        }
        p {
          line-height: 1.3em;
          font-size: small;
        }
        table.bodyTable th {
          color: white;
          background-color: #bbb;
          text-align: left;
          font-weight: bold;
        }
        table.bodyTable td {
          text-align: center;
        }
        table.bodyTable td.desc {
          text-align: left;
        }
        table.bodyTable th, table.bodyTable td {
          padding: 2px 4px 2px 4px;
          font-size: 13;
        }
        table.bodyTable tr.a {
          background-color: #ddd;
        }
        table.bodyTable tr.b {
          background-color: #eee;
        }
        .p1 { background:#FF9999; padding: 2px 4px 2px 4px; }
        .p2 { background:#FFCC66; padding: 2px 4px 2px 4px; }
        .p3 { background:#FFFF99; padding: 2px 4px 2px 4px; }
        .p4 { background:#99FF99; padding: 2px 4px 2px 4px; }
        .p5 { background:#9999FF; padding: 2px 4px 2px 4px; }

    </style>
</head>
<body>
    <div class="section">
      <h1>PMD Results</h1>
      <p>The following document contains the results of <a href="http://pmd.sourceforge.net">PMD <b><xsl:value-of select="//pmd/@version"/></b></a> on <xsl:value-of select="//pmd/@timestamp"/>.</p>
    </div>
    <hr/>
    <div class="section">
      <h2>Summary</h2>
      <table border="0" class="bodyTable">
        <tr class="a">
          <th>Files</th>
          <th>Total</th>
          <th>Blocker</th>
          <th>Critical</th>
          <th>Mayor</th>
          <th>minor</th>
          <th>info</th>
        </tr>
        <tr class="b">
          <td><xsl:value-of select="count(//file)"/></td>
          <td><xsl:value-of select="count(//violation)"/></td>
          <td><div class="p1"><xsl:value-of select="count(//violation[@priority = 1])"/></div></td>
          <td><div class="p2"><xsl:value-of select="count(//violation[@priority = 2])"/></div></td>
          <td><div class="p3"><xsl:value-of select="count(//violation[@priority = 3])"/></div></td>
          <td><div class="p4"><xsl:value-of select="count(//violation[@priority = 4])"/></div></td>
          <td><div class="p5"><xsl:value-of select="count(//violation[@priority = 5])"/></div></td>
        </tr>
      </table>
    </div>
    <hr/>
    <div class="section">
      <h2>Files</h2>
      <table border="0" class="bodyTable">
        <tr class="a">
          <th>Files</th>
          <th>Violations</th>
          <th>Blocker</th>
          <th>Critical</th>
          <th>Mayor</th>
          <th>minor</th>
          <th>info</th>
        </tr>
        <xsl:for-each select="file">
          <xsl:sort data-type="number" order="descending" select="count(violation)"/>
          <tr>
            <xsl:attribute name="class">
              <xsl:if test="position() mod 2 = 0">a</xsl:if>
              <xsl:if test="position() mod 2 != 0">b</xsl:if>
            </xsl:attribute>
            <td class="desc">
              <a>
                <xsl:attribute name="href">
                  <xsl:value-of disable-output-escaping="yes" select="concat('#', translate(substring-before(substring-after(@name, '/src/main/java/'), '.java'), '/', '.'))"/>
                </xsl:attribute>
                <xsl:value-of disable-output-escaping="yes" select="translate(substring-before(substring-after(@name, '/src/main/java/'), '.java'), '/', '.')"/>
              </a>
            </td>
            <td><div> <xsl:value-of select="count(violation)"/></div></td>
            <td><div class="p1"> <xsl:value-of select="count(violation[@priority = '1'])"/></div></td>
            <td><div class="p2"> <xsl:value-of select="count(violation[@priority = '2'])"/></div></td>
            <td><div class="p3"> <xsl:value-of select="count(violation[@priority = '3'])"/></div></td>
            <td><div class="p4"> <xsl:value-of select="count(violation[@priority = '4'])"/></div></td>
            <td><div class="p5"> <xsl:value-of select="count(violation[@priority = '5'])"/></div></td>
          </tr>
          </xsl:for-each>
      </table>
    </div>
    <hr/>

    <div class="section">
      <h2>Details</h2>
      <xsl:for-each select="file">
        <xsl:sort data-type="number" order="descending" select="count(violation)"/>
        <xsl:variable name="filename" select="@name"/>
    		<h3>
          <xsl:attribute name="id">
              <xsl:value-of disable-output-escaping="yes" select="translate(substring-before(substring-after(@name, '/src/main/java/'), '.java'), '/', '.')"/>
          </xsl:attribute>
          <xsl:value-of disable-output-escaping="yes" select="translate(substring-before(substring-after(@name, '/src/main/java/'), '.java'), '/', '.')"/>
        </h3>
        <table border="0" class="bodyTable">
          <tr class="a">
          	<th>Priority</th>
            <th>Begin Line</th>
            <th align="left">Description</th>
          </tr>
    	    <xsl:for-each select="violation">
    	    	<xsl:sort data-type="number" order="descending" select="@priority"/>
    		    <tr>
              <xsl:attribute name="class">
                <xsl:if test="position() mod 2 = 0">a</xsl:if>
                <xsl:if test="position() mod 2 != 0">b</xsl:if>
              </xsl:attribute>
      		    <td>
                <div>
                  <xsl:attribute name="class">
                    <xsl:value-of select="concat('p', @priority)"/>
                  </xsl:attribute>
                  <xsl:value-of select='@priority'/>
                </div>
      		    </td>
        			<td>
                <xsl:value-of disable-output-escaping="yes" select="@beginline"/>
              </td>
    			    <td class="desc" width="100%">
                <xsl:value-of disable-output-escaping="yes" select="."/>
              </td>
    		    </tr>
    	    </xsl:for-each>
        </table>
        <br/>
      </xsl:for-each>
    </div>
</body>
</html>
</xsl:template>

</xsl:stylesheet>
