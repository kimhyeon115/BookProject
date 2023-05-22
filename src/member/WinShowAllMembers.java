package member;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class WinShowAllMembers extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinShowAllMembers dialog = new WinShowAllMembers();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinShowAllMembers() {
		setTitle("\uBAA8\uB4E0 \uD68C\uC6D0 \uC815\uBCF4");
		setBounds(100, 100, 652, 540);
		getContentPane().setLayout(new BorderLayout(0, 0));

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		JPanel person = new JPanel();
		GridLayout gridLayout = new GridLayout(0, 1, 0, 10);
		person.setLayout(gridLayout);
		
		int RecordNumber = showMeTheRecords();
		
		Member[] member = new Member[RecordNumber];
		person.setPreferredSize(new Dimension(520, 360*RecordNumber));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select * from memberTBL";			
			ResultSet rs = stmt.executeQuery(sql);
			int idx=0;
			while(rs.next()) {
				String sID = rs.getString("id");
				String sPw = rs.getString("password");
				String sName = rs.getString("name");
				String sEmail = rs.getString("email"); 
				String sMobile = rs.getString("mobile");
				String sBirth = rs.getString("birth");
				String sLsType = rs.getString("IsType");
				String sAddress = rs.getString("address");
				String sPic = rs.getString("pic");
				member[idx] = new Member(sID,sPw,sName,sEmail,sMobile,sBirth,sLsType,sAddress,sPic);
				person.add(member[idx++]);
			}			
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}		
//		for(int i=0;i<member.length;i++) {			
//			member[i] = new Member("hello"+i,"hello","ȫ�浿","hello@daum.net","010-1234-1234","2000-01-20",
//					"1","��õ������ ������ �־ȵ� 11����","C:\\FileIO\\kakaoImage\\���׸�64.jpg.jpg" );
//			person.add(member[i]);
//		}
		
		JScrollPane jsp = new JScrollPane(person, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
				);
		container.add(jsp);
	}

	private int showMeTheRecords() {
		int number=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select count(*) from memberTBL";			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				number = rs.getInt(1);
			}			
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return number;
	}

}