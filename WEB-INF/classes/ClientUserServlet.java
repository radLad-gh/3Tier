import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class responsible for root user database access
 */
public class ClientUserServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String query = req.getParameter("query");
        Properties props = retrieveCredentials("./webapps/3Tier/WEB-INF/lib/client.properties");
        DBConnector conn = new DBConnector(props);

        String ret = null;
        try {
            if (firstWordOf(query).equalsIgnoreCase("select")) {
                conn.select(query);
                ret = Converter.convert(conn.getResultSet());
            } else {
                ret = "Client user only allowed SELECT queries.";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/clientHome.jsp");

        session.setAttribute("query", query);
        session.setAttribute("tableContent", ret);
        dispatcher.forward(req, resp);
    }

    // "SELECT * FROM SUPPLIERS" => ["SELECT", "* FROM SUPPLIERS"]
    private String firstWordOf(String query) {
        String[] tmp = query.split(" ", 2);
        return tmp[0];
    }

    public Properties retrieveCredentials(String file) {
        Properties props = null;
        try {
            FileInputStream fin;
            props = new Properties();
            fin = new FileInputStream(file);
            props.load(fin);
        } catch (final IOException readErr) {
            System.err.println("Failed to load credentials");
        }

        return props;
    }
}