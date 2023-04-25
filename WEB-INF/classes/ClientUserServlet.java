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
        String message = "";

        String ret = null;
        try {
            if (query.toLowerCase().contains("select")) {
                conn.select(query);
                ret = Converter.convert(conn.getResultSet());
            } else {
                conn.update(query);
            }
        } catch (SQLException ex) {
            message = ex.getMessage();
        }

        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/clientHome.jsp");

        session.setAttribute("query", query);
        session.setAttribute("tableContent", ret);
        session.setAttribute("message", message);
        dispatcher.forward(req, resp);
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