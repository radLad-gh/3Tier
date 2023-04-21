<!DOC TYPE html>
<html>
    <head>
        <title>RootHome</title>
        <link type="text/css" rel="stylesheet" href="styles.css">
    </head>
    <body>
    <h1>Welcome to root home</h1>
    <form action="/3Tier/rootuserservlet" method="post">
        <label for="input">Query:</label>
		<input type="text" id="query" name="query">
		<br>
		<button type="submit" name="submit" value="execute">Execute</button>
		<button type="reset">Reset</button>
		<button type="button" onclick="clearInput()">Clear</button>
    </form>
    <div id="result"></div>
    </body>
</html>
