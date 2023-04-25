<!DOC TYPE html>

<%

    Integer suppliers_rows_updated = (Integer) session.getAttribute("suppliers_rows_updated");
    Integer parts_rows_updated = (Integer) session.getAttribute("parts_rows_updated");
    Integer jobs_rows_updated = (Integer) session.getAttribute("jobs_rows_updated");
    Integer shipments_rows_updated = (Integer) session.getAttribute("shipments_rows_updated");

    if (suppliers_rows_updated == null) suppliers_rows_updated = 0;
    if (parts_rows_updated == null) parts_rows_updated = 0;
    if (jobs_rows_updated == null) jobs_rows_updated = 0;
    if (shipments_rows_updated == null) shipments_rows_updated = 0;

    String shipments_results = (String) session.getAttribute("shipments_results");
    if (shipments_results == null) shipments_results = " ";

%>
<html>
    <head>
        <title>DataEntryHome</title>
        <style type="text/css">
                <!--
                BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;font-size:12px;}
                H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;}
                PRE, TT {border: 1px dotted #525D76}
                A {color : black;}A.name {color : black;}
                -->
            </style>
    </head>
    <body>
    <h1>Welcome to DataEntryHome</h1>
    <hr>
    <h3>Supplier</h3>
    <form action="/3Tier/Suppliers" method="post">
        <input type="text" name="snum" placeholder="snum" />
        <input type="text" name="sname" placeholder="sname" />
        <input type="text" name="status" placeholder="status" />
        <input type="text" name="city" placeholder="city" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>
    </form>
    <div id="suppliers_results">
        <%=suppliers_rows_updated%> rows updated.
    </div>
    <hr>
    <h3>Parts</h3>
    <form action="/3Tier/Parts" method="post">
        <input type="text" name="pnum" placeholder="snum" />
        <input type="text" name="pname" placeholder="sname" />
        <input type="text" name="color" placeholder="color" />
        <input type="text" name="weight" placeholder="weight" />
        <input type="text" name="city" placeholder="city" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>
    </form>
    <div id="parts_results">
        <%=parts_rows_updated%> rows updated.
    </div>
    <hr>
    <h3>Jobs</h3>
    <form action="/3Tier/Jobs" method="post">
        <input type="text" name="jnum" placeholder="jnum" />
        <input type="text" name="jname" placeholder="jname" />
        <input type="text" name="numworkers" placeholder="numworkers" />
        <input type="text" name="city" placeholder="city" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>
    </form>
    <div id="jobs_results">
        <%=jobs_rows_updated%> rows updated.
    </div>
    <hr>
    <h3>Shipments</h3>
    <form action="/3Tier/Shipments" method="post">
        <input type="text" name="snum" placeholder="snum" />
        <input type="text" name="pnum" placeholder="pnum" />
        <input type="text" name="jnum" placeholder="jnum" />
        <input type="text" name="quantity" placeholder="quantity" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>
    </form>
    <div id="shipments_results">
        <p><%=shipments_results%></p>
        <%=shipments_rows_updated%> rows updated.
    </div>
    <hr>
    </body>
</html>