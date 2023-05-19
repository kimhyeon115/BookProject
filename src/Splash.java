import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.thehowtotutorial.splashscreen.JSplash;

public class Splash {

	public static void main(String[] args) throws InterruptedException {
		JSplash splash = new JSplash(Splash.class.getResource("image/loading.png"),
				true, true, false, "V1", null, Color.RED, Color.BLACK);
		splash.splashOn();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select * from bookTBL";
			ResultSet rs = stmt.executeQuery(sql);
			
			int num=0;
			while(rs.next()) {
				splash.setProgress(num, rs.getString("title"));
				Thread.sleep(10);
				num++;			
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}		
		splash.splashOff();
		
		WinMain winMain = new WinMain();
		winMain.setModal(true);
		winMain.setVisible(true);
	}

}
