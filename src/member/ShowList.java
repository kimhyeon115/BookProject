package member;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowList extends JDialog {
	private JTable table;
	private String gID;
	
	public String getID() {
		return gID;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowList dialog = new ShowList("헬로우");
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
	public ShowList() {
		setTitle("검색된 이름들");
		setBounds(100, 100, 591, 408);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String columns[] = {"ID", "Email", "Mobile", "Address"};
		DefaultTableModel dtm = new DefaultTableModel(columns,0);
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sID = table.getValueAt(table.getSelectedRow(), 0).toString();
				gID = sID;
				dispose();
			}
		});
		scrollPane.setViewportView(table);

		
	}

	public ShowList(String name) {
		this();
		setTitle("검색된 이름들(" + name + ")");
		showRecords(name);
	}

	private void showRecords(String name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select id, email, mobile, address from memberTBL where name='" + name + "'";
			ResultSet rs = stmt.executeQuery(sql);
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			while(rs.next()) {
				Vector <String> vector = new Vector<>();
				for(int i=1; i<=4; i++)
					vector.add(rs.getString(i));
				dtm.addRow(vector);
			}						
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}

}
