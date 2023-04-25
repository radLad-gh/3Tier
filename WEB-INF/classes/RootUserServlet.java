import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class responsible for root user database access
 */
public class RootUserServlet extends HttpServlet {

    private final static String UPDATE_SUPPLIERS =
                    "UPDATE suppliers SET status = status + 5 " +
                    "WHERE snum in (" +
                        "SELECT snum " +
                            "FROM shipments " +
                            "WHERE quantity >= 100)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String query = req.getParameter("query");
        Properties props = retrieveCredentials("./webapps/Project4/WEB-INF/lib/root.properties");
        DBConnector conn = new DBConnector(props);

        PreparedStatement bl;
        int rowsUpdated;
        int blRowsUpdated;
        String results = "";
        String message = "";

        try {
            if (firstWordOf(query).equalsIgnoreCase("select")) {
                conn.select(query);
                results = Converter.convert(conn.getResultSet());
            } else {
                rowsUpdated = conn.update(query);
                if (query.toLowerCase().contains("insert into shipments")) { // business logic trigger
                    if (rowsUpdated != 0) {
                        bl = conn.getConnection().prepareStatement(UPDATE_SUPPLIERS);
                        blRowsUpdated = bl.executeUpdate();

                        if (blRowsUpdated != 0) {
                            message = "updated " + blRowsUpdated + " supplier(s)";
                        } else {
                            message = "unable to update supplier(s)";
                        }
                    } else {
                        message = "no records updated, bl not triggered";
                    }
                } else {
                    message = rowsUpdated + " rows updated";
                }
            }
        } catch (SQLException ex) {
            message = ex.getMessage();
        }

        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/rootHome.jsp");

        session.setAttribute("query", query);
        session.setAttribute("tableContent", results);
        session.setAttribute("message", message);
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