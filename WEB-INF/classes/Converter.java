import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Converter {

    // Encodes an html table into a string
    public static synchronized String convert(ResultSet res) throws SQLException {
        StringBuilder html = new StringBuilder();
        ResultSetMetaData md = res.getMetaData();
        int columnCount = md.getColumnCount();

        // Encoding the column names
        html.append("<tr>");
        for(int i = 1; i<=columnCount; i++) {
            html.append("<td>");
            html.append(md.getColumnName(i));
            html.append("</td>");
        }
        html.append("</tr>");

        // Encoding the result set tuples
        while (res.next()) {
            html.append("<tr>");
            for(int i = 1; i<=columnCount; i++) {
                html.append("<td>");
                html.append(res.getString(i));
                html.append("</td>");
            }
            html.append("</tr>");
        }

        return html.toString();
    }
}
