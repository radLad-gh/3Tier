import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.Properties;

/**
 * Class responsible for root user database access
 */
public class DataEntryServlet extends HttpServlet {

    public DataEntryServlet() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String ctx = req.getContextPath();
        Properties props = retrieveCredentials(ctx + "/WEB-INF/lib/dataentry.properties");
        DBConnector conn = new DBConnector(props);

        ResultSetMetaData meta = conn.query(req.getParameter("query"));
        if (meta == null) {
            resp.setStatus(500);
            resp.sendRedirect(ctx + "/errorpage.jsp");
        }
        String res = conn.getResultSet();
        conn.disconnect();
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