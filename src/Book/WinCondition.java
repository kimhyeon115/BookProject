package Book;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinCondition extends JDialog {
	private JTextField tfPrice1;
	private JTextField tfPrice2;
	private JTable table;
	private int type;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinCondition dialog = new WinCondition();
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
	public WinCondition() {
		setTitle("\uAC80\uC0C9\uD560 \uC870\uAC74 \uC785\uB825");
		setBounds(100, 100, 455, 572);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAC00\uACA9\uC774");
		lblNewLabel.setBounds(83, 45, 57, 15);
		getContentPane().add(lblNewLabel);
		
		tfPrice1 = new JTextField();
		tfPrice1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPrice2.requestFocus();
				}				
			}
		});
		tfPrice1.setBounds(152, 42, 116, 21);
		getContentPane().add(tfPrice1);
		tfPrice1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\uC774\uC0C1\uC774\uACE0");
		lblNewLabel_1.setBounds(280, 45, 57, 15);
		getContentPane().add(lblNewLabel_1);
		
		tfPrice2 = new JTextField();
		tfPrice2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					SearchISBN();
				}
			}
		});
		tfPrice2.setBounds(152, 90, 116, 21);
		getContentPane().add(tfPrice2);
		tfPrice2.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("\uBBF8\uB9CC\uC778 \uAC83");
		lblNewLabel_1_1.setBounds(313, 93, 57, 15);
		getContentPane().add(lblNewLabel_1_1);
		
		JButton btnSearch = new JButton("\uAC80\uC0C9");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchISBN();
			}
		});
		btnSearch.setBounds(152, 138, 116, 23);
		getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();
				if(row == -1) {
					
				} else {
					if(JOptionPane.showConfirmDialog(null, "선택 페이지 이동..") == JOptionPane.YES_OPTION) {
						String sISBN = table.getValueAt(row, 0).toString();
						if(type==2) {
							WinBookDelete winBookDelete = new WinBookDelete(sISBN);
							winBookDelete.setModal(true);
							winBookDelete.setVisible(true);
						} else if(type == 3) {
							WinBookUpdate winBookUpdate = new WinBookUpdate(sISBN);
							winBookUpdate.setModal(true);
							winBookUpdate.setVisible(true);
					    }
						dispose();
				    }				
			    }
			}
		});
		
		scrollPane.setBounds(12, 182, 415, 341);
		getContentPane().add(scrollPane);
		
		String columnNames[] = { "ISBN", "Title", "Discount" };
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);

	}
	
	public WinCondition(int number) {
		this();
		type = number;
	}

	protected void SearchISBN() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");			
			String sql = "SELECT isbn, title, discount FROM booktbl WHERE discount>=? AND discount<=? ORDER BY discount DESC";
			
			PreparedStatement pstmt = con.prepareStatement(sql);			
			pstmt.setString(1, tfPrice1.getText());
			pstmt.setString(2, tfPrice2.getText());			
			ResultSet rs = pstmt.executeQuery();
			
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
			while(rs.next()) {
				Vector<String> vector = new Vector<String>();
				vector.add(rs.getString("isbn"));
				vector.add(rs.getString("title"));
				vector.add(rs.getString("discount"));
				dtm.addRow(vector);			
			}			
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
}
