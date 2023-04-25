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

public class ShipmentsInsertServlet extends HttpServlet {

    private final static String INSERT_INTO_SHIPMENTS = "INSERT INTO shipments VALUES(?,?,?,?)";
    private final static String UPDATE_SUPPLIERS = "UPDATE suppliers SET status = status + 5 WHERE snum = ?";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String snum = req.getParameter("snum");
        String pnum = req.getParameter("pnum");
        String jnum = req.getParameter("jnum");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Properties props = retrieveCredentials("./webapps/3Tier/WEB-INF/lib/dataentry.properties");
        DBConnector conn = new DBConnector(props);
        String message = "";
        int rowsUpdated = 0;
        int blRowsUpdated = 0;
        PreparedStatement ps;
        PreparedStatement bl;

        try {
            ps = conn.getConnection().prepareStatement(INSERT_INTO_SHIPMENTS);
            ps.setString(1, snum);
            ps.setString(2, pnum);
            ps.setString(3, jnum);
            ps.setInt(4, quantity);

            rowsUpdated = ps.executeUpdate();

            bl = conn.getConnection().prepareStatement(UPDATE_SUPPLIERS);
            bl.setString(1, snum);
            if (rowsUpdated != 0 && quantity >= 100) {
                blRowsUpdated = bl.executeUpdate();

                if (blRowsUpdated != 0) {
                    message = "updated " + blRowsUpdated + " supplier(s)";
                } else {
                    message = "unable to update supplier(s)";
                }
            } else if (rowsUpdated != 0) {
                message = "insert successful";
            } else {
                message = "no records updated";
            }

            ps.close();
            bl.close();
        } catch (SQLException sqlEx) {
            message = "Error executing the SQL statement: <br>" + sqlEx.getMessage();
        }

        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/dataentryHome.jsp");

        session.setAttribute("shipments_results", message);
        session.setAttribute("shipments_rows_updated", rowsUpdated);
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
