
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
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import Book.WinBookDelete;
import Book.WinBookDetail;
import Book.WinBookDetails;
import Book.WinBookInsert;
import Book.WinBookUpdate;
import Book.WinCondition;
import member.WinMemberAdd;
import member.WinMemberRemove;
import member.WinMemberSelect;
import member.WinMemberUpdate;

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
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class WinMain extends JDialog {
	private JTable table;
	private JTextField tfMobile;

	public WinMain() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				
				String columns[] = {"ISBN","제목","저자","출판사","이미지URL","출판일","가격","책 소개","권수"};
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				dtm.setColumnIdentifiers(columns);
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
		setTitle("도서 관리 프로그램");
		setBounds(100, 100, 993, 634);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnuFile = new JMenu("File");
		mnuFile.setMnemonic('F');
		menuBar.add(mnuFile);
		
		JMenuItem mnuExit = new JMenuItem("Exit");
		mnuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnuFile.add(mnuExit);
		
		JMenu mnuBookManger = new JMenu("도서관리");
		menuBar.add(mnuBookManger);
		
		JMenuItem mnuBookAdd = new JMenuItem("도서 등록...");
		mnuBookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookInsert winBookInsert = new WinBookInsert();
				winBookInsert.setModal(true);
				winBookInsert.setVisible(true);
			}
		});
		mnuBookManger.add(mnuBookAdd);
		
		JMenuItem mnuBookRemove = new JMenuItem("도서 삭제...");
		mnuBookRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();				
				if(row == -1) {
					WinCondition winCondition = new WinCondition(2);
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
		
		JMenuItem mnuBookUpdate = new JMenuItem("도서 변경...");
		mnuBookUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int row = table.getSelectedRow();				
				if(row == -1) {
					WinCondition winCondition = new WinCondition(3);
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
		
		JMenuItem mnuBookSelect = new JMenuItem("도서 조회...");
		mnuBookManger.add(mnuBookSelect);
		
		mnuBookManger.addSeparator();
		
		JMenuItem mnuAllShow = new JMenuItem("모든 책 보기...");
		mnuAllShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookDetails winBookDetails = new WinBookDetails();
				winBookDetails.setModal(true);
				winBookDetails.setVisible(true);
			}
		});
		mnuBookManger.add(mnuAllShow);
		
		JMenu mnMemberManager = new JMenu("회원관리");
		menuBar.add(mnMemberManager);
		
		JMenuItem mnuMemberAdd = new JMenuItem("회원 가입...");
		mnuMemberAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberAdd winmemberAdd = new WinMemberAdd(1);
				winmemberAdd.setModal(true);
				winmemberAdd.setVisible(true);
			}
		});
		mnMemberManager.add(mnuMemberAdd);
		
		JMenuItem mnuMemberRemove = new JMenuItem("회원 삭제...");
		mnuMemberRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberRemove winMemberRemove = new WinMemberRemove(2);
				winMemberRemove.setModal(true);
				winMemberRemove.setVisible(true);
			}
		});
		mnMemberManager.add(mnuMemberRemove);
		
		JMenuItem mnuMemberUpdate = new JMenuItem("회원 변경...");
		mnuMemberUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberUpdate winMemberUpdate = new WinMemberUpdate(3);
				winMemberUpdate.setModal(true);
				winMemberUpdate.setVisible(true);
			}
		});
		mnMemberManager.add(mnuMemberUpdate);
		
		JMenuItem mnuMemberSelect = new JMenuItem("회원 조회...");
		mnuMemberSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberSelect winMemberSelect = new WinMemberSelect(4);
				winMemberSelect.setModal(true);
				winMemberSelect.setVisible(true);		
			}
		});
		mnMemberManager.add(mnuMemberSelect);
		
		JSeparator separator = new JSeparator();
		mnMemberManager.add(separator);
		
		JMenuItem mnuMemberAllShow = new JMenuItem("모든 회원 보기...");
		mnMemberManager.add(mnuMemberAllShow);
		
		JMenu mnNewMenu = new JMenu("Help");
		mnNewMenu.setMnemonic('H');
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("회사 정보...");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "2023 © rights");
			}
		});
		mnNewMenu.add(mntmNewMenuItem_4);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnMemberSearch = new JButton("");
		btnMemberSearch.setIcon(new ImageIcon(WinMain.class.getResource("/image/searchUser.png")));
		btnMemberSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnBookAdd = new JButton("");
		btnBookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookInsert winBookInsert = new WinBookInsert();
				winBookInsert.setModal(true);
				winBookInsert.setVisible(true);
			}
		});
		btnBookAdd.setIcon(new ImageIcon(WinMain.class.getResource("/image/bookadd.png")));
		toolBar.add(btnBookAdd);
		
		JButton btnBookUpdate = new JButton("");
		btnBookUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();				
				if(row == -1) {
					WinCondition winCondition = new WinCondition(3);
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
		
		JButton btnBookRemove = new JButton("");
		btnBookRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();				
				if(row == -1) {
					WinCondition winCondition = new WinCondition(2);
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
		btnBookRemove.setIcon(new ImageIcon(WinMain.class.getResource("/image/bookremove.png")));
		toolBar.add(btnBookRemove);
		btnBookUpdate.setIcon(new ImageIcon(WinMain.class.getResource("/image/bookupdate.png")));
		toolBar.add(btnBookUpdate);
		
		JButton btnBookSearch = new JButton("");
		btnBookSearch.setIcon(new ImageIcon(WinMain.class.getResource("/image/booksearch.png")));
		toolBar.add(btnBookSearch);
		
		toolBar.addSeparator();
		
		tfMobile = new JTextField();
		toolBar.add(tfMobile);
		tfMobile.setColumns(10);
		
		toolBar.addSeparator();
		
		JButton btnMemberAdd = new JButton("");
		btnMemberAdd.setIcon(new ImageIcon(WinMain.class.getResource("/image/addUser.png")));
		toolBar.add(btnMemberAdd);
		
		JButton btnMemberRemove = new JButton("");
		btnMemberRemove.setIcon(new ImageIcon(WinMain.class.getResource("/image/delUser.png")));
		toolBar.add(btnMemberRemove);
		
		JButton btnMemberUpdate = new JButton("");
		btnMemberUpdate.setIcon(new ImageIcon(WinMain.class.getResource("/image/showUser.png")));
		toolBar.add(btnMemberUpdate);
		toolBar.add(btnMemberSearch);
				
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
//		String columns[] = {"ISBN","제목","저자","출판사","이미지URL","출판일","가격","책 소개"};
//		DefaultTableModel dtm = new DefaultTableModel(columns,0);
		table = new JTable();
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem mnuDetail = new JMenuItem("자세히...");
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
		
		JMenuItem mnuDelete = new JMenuItem("삭제...");
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
		
		JMenuItem mnuUpdate = new JMenuItem("변경...");
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
		
		JMenuItem mnuRent = new JMenuItem("대여...");
		mnuRent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinRentalSearch winRentalSearch = new WinRentalSearch();
				winRentalSearch.setModal(true);
				winRentalSearch.setVisible(true);
				
				// id를 전달 받으면 rentaltbl에 isbn, xxxdate 추가하기
			}
		});
		popupMenu.add(mnuRent);
		
		scrollPane.setViewportView(table);
		
	}
	
	public void showMembersByMobile() {
		String columnNames[] = {"ID","이름","이메일","전화번호","주소"};
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setColumnIdentifiers(columnNames);
		dtm.setRowCount(0);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select * from bookTBL where like '%" + tfMobile.getText() + "%'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Vector<String> vector = new Vector<>();
				vector.add(rs.getString("id"));
				vector.add(rs.getString("name"));
				vector.add(rs.getString("email"));
				vector.add(rs.getString("mobile"));
				vector.add(rs.getString("address"));
				dtm.addRow(vector);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}		
	}

	public WinMain(DefaultTableModel dtm) {
		this();
		table.setModel(dtm);
//		showRecords(dtm);		// 화면 구성한 후 showRecords()함수를 이용하여 테이블에 출력
		
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
				for(int i=1; i<=9; i++)
					vector.add(rs.getString(i));
				dtm.addRow(vector);
			}
		} catch (ClassNotFoundException | SQLException e1) {
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
					
					if(!source.isRowSelected(row))  // 행이 선택되지 않았다면 그 행을 선택한다.
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
