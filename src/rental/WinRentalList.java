package rental;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WinRentalList extends JDialog {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinRentalList dialog = new WinRentalList();
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
	public WinRentalList() {
		setTitle("\uB300\uC5EC\uB41C \uCC45\uB4E4");
		setBounds(100, 100, 744, 537);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
//		String columnNames[] = {"번호","제목","이름","대여 날짜"};
		String columnNames[] = {"IDX","ISBN","ID","RentDate","ReturnDate"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		table = new JTable();
		scrollPane.setViewportView(table);
		showRecords();
	}
	
	private void showRecords() {
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqldb","root","1234");
			Statement stmt = con.createStatement();
			String sql = "slect R,idx, B.tithle, M.name, R.rentDate from rentaltbl R";
			sql = sql + "INNER JOIN booktbl B ON R.isbn=B.isbn";
			sql = sql + " INNER JOIN membertbl M ON R.id=M.id";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Vector<String> vector = new Vector<>();
				for(int i=1; i<=4; i++) {
					vector.add(rs.getString(i));
				dtm.addRow(vector);				
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
