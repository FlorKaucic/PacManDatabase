package drop;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Drop {
	public static void main(String[] args) {	
		Properties prop = new Properties();
		InputStream input = null;

		Connection con = null;
		Statement st = null;

		try {
			input = new FileInputStream("sconfig.properties");
			prop.load(input);

			String url = "jdbc:mysql://"+prop.getProperty("dbhost")+":"+prop.getProperty("dbport")+"/";
			
			con = DriverManager.getConnection(url, prop.getProperty("dbuser"), prop.getProperty("dbpass"));
			st = con.createStatement();
			st.execute("DROP DATABASE IF EXISTS "+prop.getProperty("dbname"));
			JOptionPane.showMessageDialog(null, "La base de datos se borro del servidor, si existia.", "Exito", JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error al leer archivo", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error con la base de datos", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if(input!=null)
					input.close();
					if (st != null) {
						st.close();
					}
					if (con != null) {
						con.close();
					}
			} catch (IOException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error al cerrar conexiones", JOptionPane.ERROR_MESSAGE);
			}
		}		
	}
}
