<!DOC TYPE html>
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
    <form action="/3Tier/Suppliers" method="post">
        <input type="text" name="snum" placeholder="snum" />
        <input type="text" name="sname" placeholder="sname" />
        <input type="text" name="status" placeholder="status" />
        <input type="text" name="city" placeholder="city" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>

    </form>
    <form action="/3Tier/Parts" method="post">
        <input type="text" name="pnum" placeholder="snum" />
        <input type="text" name="pname" placeholder="sname" />
        <input type="text" name="color" placeholder="color" />
        <input type="text" name="weight" placeholder="weight" />
        <input type="text" name="city" placeholder="city" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>

    </form>
    <form action="/3Tier/Jobs" method="post">
        <input type="text" name="jnum" placeholder="jnum" />
        <input type="text" name="jname" placeholder="jname" />
        <input type="text" name="numworkers" placeholder="numworkers" />
        <input type="text" name="city" placeholder="city" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>

    </form>
    <form action="/3Tier/Shipments" method="post">
        <input type="text" name="snum" placeholder="snum" />
        <input type="text" name="pnum" placeholder="pnum" />
        <input type="text" name="jnum" placeholder="jnum" />
        <input type="text" name="quantity" placeholder="quantity" />
        <input type="submit" value="Enter Record" />
        <button type="button" onclick="clearInput()">Clear</button>

    </form>
    </body>
</html>