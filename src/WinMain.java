import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

public class WinMain extends JDialog {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMain dialog = new WinMain();
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
	public WinMain() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				showRecords(dtm);				
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {				
			}
		});
		setTitle("���� ���� ���α׷�");
		setBounds(100, 100, 1178, 634);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnuFile = new JMenu("File");
		mnuFile.setMnemonic('F');
		menuBar.add(mnuFile);
		
		JMenuItem mnuExit = new JMenuItem("Exit");
		mnuFile.add(mnuExit);
		
		JMenu mnuBookManger = new JMenu("��������");
		menuBar.add(mnuBookManger);
		
		JMenuItem mnuBookAdd = new JMenuItem("���� ���...");
		mnuBookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookInsert winBookInsert = new WinBookInsert();
				winBookInsert.setModal(true);
				winBookInsert.setVisible(true);
			}
		});
		mnuBookManger.add(mnuBookAdd);
		
		JMenuItem mnuBookRemove = new JMenuItem("���� ����...");
		mnuBookRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();				
				if(row == -1) {
					WinCondition winCondition = new WinCondition();
					winCondition.setModal(true);
					winCondition.setVisible(true);					
				} else {
					String sISBN = table.getValueAt(row, 0).toString();
					WinBookDelete winBookDelete = new WinBookDelete(sISBN);
					winBookDelete.setModal(true);
					winBookDelete.setVisible(true);
				}
			}
		});
		mnuBookManger.add(mnuBookRemove);
		
		JMenuItem mnuBookUpdate = new JMenuItem("���� ����...");
		mnuBookUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int row = table.getSelectedRow();				
				if(row == -1) {
					WinCondition winCondition = new WinCondition();
					winCondition.setModal(true);
					winCondition.setVisible(true);	
				} else {
					String sISBN = table.getValueAt(row, 0).toString();	
					WinBookUpdate winBookUpdate = new WinBookUpdate();
					winBookUpdate.setModal(true);
					winBookUpdate.setVisible(true);
				}				
			}
		});
		mnuBookManger.add(mnuBookUpdate);
		
		JMenuItem mnuBookSelect = new JMenuItem("���� ��ȸ...");
		mnuBookManger.add(mnuBookSelect);
		
		mnuBookManger.addSeparator();
		
		JMenuItem mnuAllShow = new JMenuItem("��� å ����...");
		mnuAllShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookDetails winBookDetails = new WinBookDetails();
				winBookDetails.setModal(true);
				winBookDetails.setVisible(true);
			}
		});
		mnuBookManger.add(mnuAllShow);
		
		JMenu mnNewMenu = new JMenu("Help");
		mnNewMenu.setMnemonic('H');
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("ȸ�� ����...");
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("����");
		toolBar.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("���");
		toolBar.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("����");
		toolBar.add(btnNewButton_2);
				
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String columns[] = {"ISBN","����","����","���ǻ�","�̹���URL","������","����","å �Ұ�"};
		DefaultTableModel dtm = new DefaultTableModel(columns,0);
		table = new JTable(dtm);		
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem mnuDetail = new JMenuItem("�ڼ���...");
		mnuDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String sISBN = table.getValueAt(row, 0).toString();
				
				WinBookDetail winBookDetail;
				try {
					winBookDetail = new WinBookDetail(sISBN);
					winBookDetail.setModal(true);
					winBookDetail.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		popupMenu.add(mnuDetail);
		
		JMenuItem mnuDelete = new JMenuItem("����...");
		mnuDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String sISBN = table.getValueAt(row, 0).toString();
				WinBookDelete winBookDelete = new WinBookDelete(sISBN);
				winBookDelete.setModal(true);
				winBookDelete.setVisible(true);				
			}
		});
		popupMenu.add(mnuDelete);
		
		JMenuItem mnuUpdate = new JMenuItem("����...");
		mnuUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String sISBN = table.getValueAt(row, 0).toString();
				WinBookUpdate winBookUpdate = new WinBookUpdate(sISBN);
				winBookUpdate.setModal(true);
				winBookUpdate.setVisible(true);
			}
		});
		popupMenu.add(mnuUpdate);
		
		scrollPane.setViewportView(table);			
		
		showRecords(dtm);

	}

	private void showRecords(DefaultTableModel dtm) {
		dtm.setRowCount(0);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select * from bookTBL order by title asc";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Vector<String> vector = new Vector<>();
				for(int i=1; i<=8; i++)
					vector.add(rs.getString(i));
				dtm.addRow(vector);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JTable source = (JTable)e.getSource();
					int row = source.rowAtPoint(e.getPoint());
					int column = source.columnAtPoint(e.getPoint());
					
					if(!source.isRowSelected(row))  // ���� ���õ��� �ʾҴٸ� �� ���� �����Ѵ�.
						source.changeSelection(row, column, false, false);
					
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
}
