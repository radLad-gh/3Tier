import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class responsible for root user database access
 */
public class DataEntryServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String ctx = req.getContextPath();
        Properties props = retrieveCredentials("./webapps/3Tier" +
                "/WEB-INF/lib/dataentry.properties");
        DBConnector conn = new DBConnector(props);
        String ret = null;

        try {
            conn.select(req.getParameter("query"));
            if (conn.getRowCount() <= 0) {
                resp.setStatus(500);
                resp.sendRedirect(ctx + "/errorpage.jsp");
            }
            ResultSet res = conn.getResultSet();
            ret = Converter.convert(res);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        conn.disconnect();

        HttpSession session = req.getSession();
        session.setAttribute("tableContent", ret);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
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
            JOptionPane.showMessageDialog(null, readErr.getMessage(), "Could not load credentials from file", JOptionPane.ERROR_MESSAGE);
        }

        return props;
    }
}