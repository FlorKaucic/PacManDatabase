package create;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Create {

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
			st.execute("CREATE DATABASE IF NOT EXISTS "+prop.getProperty("dbname"));
			st.close();
			con.close();
			con = DriverManager.getConnection(url+prop.getProperty("dbname"), prop.getProperty("dbuser"), prop.getProperty("dbpass"));
			st = con.createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS tbl_user("
					+ "id int(11) NOT NULL AUTO_INCREMENT,"
					+ "username varchar(25) NOT NULL,"
					+ "password varchar(20) NOT NULL,"
					+ "nickname varchar(35) NOT NULL,"
					+ "won int(11) NOT NULL,"
					+ "lost int(11) NOT NULL,"
					+ "enabled boolean NOT NULL,"
					+ "PRIMARY KEY (id));");
			JOptionPane.showMessageDialog(null, "La base de datos se creo en el servidor, si no existia.", "Exito", JOptionPane.PLAIN_MESSAGE);
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
