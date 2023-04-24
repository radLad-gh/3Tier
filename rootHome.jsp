<!DOC TYPE html>

<%
    String query = (String) session.getAttribute("query");
    if (query == null) query = "select * from suppliers";

    String tableContent = (String) session.getAttribute("tableContent");
    if (tableContent == null) tableContent = "null";
%>
<html>
    <head>
        <title>RootHome</title>
        <style type="text/css">
            <!--
            BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;font-size:12px;}
            H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;}
            PRE, TT {border: 1px dotted #525D76}
            A {color : black;}A.name {color : black;}
            -->
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"> </script>
        <script type="text/javascript">
            function clearInput() {
                $("query").html("");
            }
        </script>
    </head>
    <body>
    <h1>Welcome to RootHome</h1>
    <form action="/3Tier/RootUserApp" method="post">
        <textarea id="query" name="query" cols = 60 rows=8><%=query%></textarea>
        <br><br>
		<button type="submit" name="submit" value="execute">Execute</button>
		<button type="reset">Reset</button>
		<button type="button" onclick="javascript:clearInput()">Clear</button>
    </form>
    <hr>
    <center>
        <p>
            <b class="main">Database Results:</b><br>
            <table id="data">
                <%=tableContent%>
            </table>
        </p>
    </center>
    </body>
</html>