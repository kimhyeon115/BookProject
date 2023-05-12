import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class Book extends JPanel {
	private JTextField tfISBN;
	private JTextField tfTitle;
	private JTextField tfAuthor;
	private JTextField tfPdate;
	private JTextField tfDiscount;
	private JTextArea taDescription;
	private JLabel lblImage;
	private JButton btnType;
	private JButton btnDup;
	private JButton btnCalendar;
	private JComboBox cbPublisher;
	protected String filePath;
	private String strImage;
	
	public Book() {
		setBackground(new Color(192, 192, 192));
		setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					strImage = JOptionPane.showInputDialog("책의 URL을 입력하시오");
					lblImage.setText("<html><body><img src='" + strImage + "' width=200 height=250><body></html>");
				}
			}			
		});
		lblImage.setToolTipText("\uB354\uBE14 \uD074\uB9AD\uD574\uC11C \uADF8\uB9BC \uC120\uD0DD\uD558\uAE30");
		lblImage.setOpaque(true);
		lblImage.setBackground(Color.YELLOW);
		lblImage.setBounds(29, 35, 200, 250);
		add(lblImage);
		
		JLabel lblISBN = new JLabel("ISBN : ");
		lblISBN.setBounds(243, 35, 57, 15);
		add(lblISBN);
		
		tfISBN = new JTextField();
		tfISBN.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tfISBN.getText().length() == 13 && isDigit(tfISBN.getText()))
					changeEnComps();
				else
					changeDisComps();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					checkDup();
				}
			}
		});
	
		tfISBN.setColumns(10);
		tfISBN.setBounds(313, 32, 207, 21);
		add(tfISBN);
		
		JLabel lblTitle = new JLabel("책제목 : ");
		lblTitle.setBounds(241, 63, 57, 15);
		add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setEnabled(false);
		tfTitle.setColumns(10);
		tfTitle.setBounds(311, 60, 318, 21);
		add(tfTitle);
		
		JLabel lblAuthor = new JLabel("저자 : ");
		lblAuthor.setBounds(241, 94, 57, 15);
		add(lblAuthor);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(311, 91, 318, 21);
		add(tfAuthor);
		
		JLabel lblPublisher = new JLabel("출판사 : ");
		lblPublisher.setBounds(241, 122, 57, 15);
		add(lblPublisher);
		
		JLabel lblPdate = new JLabel("출판일 : ");
		lblPdate.setBounds(241, 150, 57, 15);
		add(lblPdate);
		
		tfPdate = new JTextField();
		tfPdate.setColumns(10);
		tfPdate.setBounds(311, 147, 209, 21);
		add(tfPdate);
		
		JLabel lblDiscount = new JLabel("가격 : ");
		lblDiscount.setBounds(241, 178, 57, 15);
		add(lblDiscount);
		
		tfDiscount = new JTextField();
		tfDiscount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {	// F2:00, F3:000, F4:0000
				// 키보드에서 키를 뗄때 실행되게해야함
				// 키보드에서 키를 누를때 실행할경우 데이터 입력보다 키값이 먼저 인식됨
				
				String amount = tfDiscount.getText();
				tfDiscount.setText(amount.replaceAll(",", ""));		// 가격 수정할경우 콤마제거
				
				String discount = tfDiscount.getText();
				if(e.getKeyCode() == KeyEvent.VK_F4) {
					tfDiscount.setText(discount+"0000");
				}else if(e.getKeyCode() == KeyEvent.VK_F3) {
					tfDiscount.setText(discount+"000");
				}else if(e.getKeyCode() == KeyEvent.VK_F2) {
					tfDiscount.setText(discount+"00");
				}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					amount = tfDiscount.getText();					
				}
				amount =  tfDiscount.getText();
				amount = amount.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");	// 가격 입력후 엔터키 클릭시 콤마 적용
				tfDiscount.setText(amount);	
			}
		});
		tfDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
		tfDiscount.setColumns(10);
		tfDiscount.setBounds(311, 175, 209, 21);
		add(tfDiscount);
		
		JLabel lblDescription = new JLabel("책 소개 : ");
		lblDescription.setBounds(29, 295, 57, 15);
		add(lblDescription);
		
		JScrollPane pane = new JScrollPane();
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBounds(29, 320, 612, 181);
		add(pane);
		
		taDescription = new JTextArea();
		taDescription.setLineWrap(true);
		pane.setViewportView(taDescription);
		
		btnType = new JButton("도서 ??");
		btnType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnType.getText().equals("도서 추가")) {
					insertBook();
				} else if(btnType.getText().equals("도서 삭제")) {
					if(JOptionPane.showConfirmDialog(null, "정말 삭제할까요?") == JOptionPane.YES_OPTION) {						
						deleteBook();						
					}
				} else if(btnType.getText().equals("도서 변경")) {
					if(JOptionPane.showConfirmDialog(null, "정말 변경할까요?") == JOptionPane.YES_OPTION) {
						updateBook();
					}
				}					
			}
		});
		btnType.setBounds(369, 219, 115, 66);
		add(btnType);
		
		btnDup = new JButton("\uC911\uBCF5\uD655\uC778...");
		btnDup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkDup();
			}
		});
		btnDup.setEnabled(false);
		btnDup.setBounds(532, 31, 97, 23);
		add(btnDup);
		
		btnCalendar = new JButton("\uB2EC\uB825...");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				
				tfPdate.setText(winCalendar.getDate());
			}
		});
		btnCalendar.setBounds(532, 146, 97, 23);
		add(btnCalendar);
		
		cbPublisher = new JComboBox();
		cbPublisher.setEditable(true);
		cbPublisher.setBounds(313, 118, 316, 23);
		add(cbPublisher);
		
		changeDisComps();
	}
	
	protected void updateBook() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			
			String sql = "UPDATE booktbl SET title=?, author=?, publisher=?, image=?, pDate=?, discount=?, description=? WHERE isbn=?";			
			PreparedStatement pstmt = con.prepareStatement(sql);			
			pstmt.setString(1, tfTitle.getText());
			pstmt.setString(2, tfAuthor.getText());
			pstmt.setString(3, cbPublisher.getSelectedItem().toString());
			pstmt.setString(4, strImage);
			pstmt.setString(5, tfPdate.getText());
			pstmt.setString(6, tfDiscount.getText());
			pstmt.setString(7, taDescription.getText());
			pstmt.setString(8, tfISBN.getText());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	protected void deleteBook() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			
			String sql = "DELETE FROM booktbl WHERE isbn=?";			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfISBN.getText());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}		
	}

	protected void insertBook() {
		// PreparedStatement 타입으로 DB 연결
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");	
			
			String sql = "insert into booktbl values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);	// PreparedStatement는 sql 우선 입력
			
			pstmt.setString(1,tfISBN.getText());
			pstmt.setString(2,tfTitle.getText());
			pstmt.setString(3,tfAuthor.getText());
			pstmt.setString(4,cbPublisher.getSelectedItem().toString());
			pstmt.setString(5,filePath.replaceAll("\\\\", "\\\\\\\\"));
			pstmt.setString(6,tfPdate.getText());
			pstmt.setInt(7,Integer.parseInt(tfDiscount.getText().replaceAll(",","")));
			pstmt.setString(8,taDescription.getText());
			
			pstmt.executeUpdate();		// 메소드내 sql 넣으면 중복으로 오류발생
				
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Statement 타입으로 DB 연결
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
//			Statement stmt = con.createStatement();	
//			String sISBN = tfISBN.getText();
//			String sTitle = tfTitle.getText();
//			String sAuthor = tfAuthor.getText();
//			String sPublisher = cbPublisher.getSelectedItem().toString();
//			String sFilePath = filePath.replaceAll("\\\\", "\\\\\\\\");
//			String sPdate = tfPdate.getText();
//			int sDiscount = Integer.parseInt(tfDiscount.getText().replaceAll(",",""));
//			String sDescription = taDescription.getText();
//			
//			String sql = "insert into booktbl values('"+sISBN+"','"+sTitle+"','"+sAuthor+"','"+sPublisher;
//			sql = sql +"','"+sFilePath+"','"+sPdate+"',"+sDiscount+",'"+sDescription+"')";
//			if(stmt.executeUpdate(sql) != 1)
//				JOptionPane.showMessageDialog(null, "질의어 오류인것 같습니다. \n확인하세요!!!");
//				
//		} catch (ClassNotFoundException | SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}

	protected void checkDup() {
				
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select count(*) from bookTBL where isbn = '" + tfISBN.getText() + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			String arrBook[] = new String[8];			
			while(rs.next()) {
				if(rs.getInt(1) == 0)
					tfTitle.requestFocus();
				else {
					tfISBN.setSelectionStart(0);						// 첫번째 글짜부터
					tfISBN.setSelectionEnd(tfISBN.getText().length());	// 마지막 글짜까지 블록 선택
					tfISBN.requestFocus();
				}
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	protected boolean isDigit(String strISBN) {
		
		return strISBN.matches("\\d*");			// 숫자로만 구성된 문자인지 확인 메소드
		
//		for(int i=0;i<strISBN.length();i++) {
//			if(strISBN.charAt(i) >= '0' && strISBN.charAt(i) <= '9')
//				continue;
//			else
//				return false;
//		}
//		return true;
	}
	
	protected void changeDisComps() {
		btnDup.setEnabled(false);
		tfTitle.setEnabled(false);
		tfAuthor.setEnabled(false);
		cbPublisher.setEnabled(false);
		tfPdate.setEnabled(false);
		tfDiscount.setEnabled(false);
		taDescription.setEnabled(false);
		btnCalendar.setEnabled(false);
		btnType.setEnabled(false);		
	}
	
	protected void changeEnComps() {
		btnDup.setEnabled(true);
		tfTitle.setEnabled(true);
		tfAuthor.setEnabled(true);
//		tfPublisher.setEnabled(true);
		cbPublisher.setEnabled(true);
		tfPdate.setEnabled(true);
		tfDiscount.setEnabled(true);
		taDescription.setEnabled(true);
		btnCalendar.setEnabled(true);
		btnType.setEnabled(true);
		
		showPublisher();
	}
	
	private void showPublisher() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select distinct publisher from bookTBL";
			ResultSet rs = stmt.executeQuery(sql);
			
			cbPublisher.removeAllItems();
			while(rs.next()) {
				cbPublisher.addItem(rs.getString("publisher"));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	public Book(String sISBN, String sTitle, String sAuthor, String sPublisher, String sImage, 
			String sPdate, String sDiscount, String sDescription) {
		this();		
		tfISBN.setText(sISBN);
		tfTitle.setText(sTitle);
		tfAuthor.setText(sAuthor);
		cbPublisher.setSelectedItem(sPublisher);
		tfPdate.setText(sPdate);
		tfDiscount.setText(sDiscount);
		taDescription.setText(sDescription);
		lblImage.setText("<html><body><img src='" + sImage + "' width=200 height=250></body></html>");
	}
	
	public Book(String[] arrBook) {
		this();
		changeEnComps();
		tfISBN.setText(arrBook[0]);
		tfTitle.setText(arrBook[1]);
		tfAuthor.setText(arrBook[2]);
		cbPublisher.setSelectedItem(arrBook[3]);
		tfPdate.setText(arrBook[5]);
		tfDiscount.setText(arrBook[6]);
		taDescription.setText(arrBook[7]);
		lblImage.setText("<html><body><img src='" + arrBook[4] + "' width=200 height=250></body></html>");
	}
	
	public Book(int number) {
		this();
		if(number==1) { //삽입
			btnType.setText("도서 추가");
		}else if(number==2) { //삭제
			btnType.setText("도서 삭제");
		}else if(number==3) { //변경
			btnType.setText("도서 변경");
		}
	}

	public Book(int number, String sISBN) {
		this(number);
		tfISBN.setText(sISBN);
		
		btnDup.setVisible(false);
		tfTitle.setEnabled(true);
		tfAuthor.setEnabled(true);
		cbPublisher.setEnabled(true);
		tfPdate.setEnabled(true);
		tfDiscount.setEnabled(true);
		taDescription.setEnabled(true);
		
		if(number==2)
			btnCalendar.setVisible(false);
		else if(number==3) {
			btnCalendar.setVisible(true);
			btnCalendar.setEnabled(true);
		}
		btnType.setEnabled(true);
		
		showRecord(sISBN);
	}

	private void showRecord(String sISBN) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select * from bookTBL where isbn='" + sISBN + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				tfTitle.setText(rs.getString("title"));
				tfAuthor.setText(rs.getString("author"));
				cbPublisher.setSelectedItem(rs.getString("publisher"));
				strImage = rs.getString("image");
				lblImage.setText("<html><body><img src='" + strImage + "' width=200 height=250></body></html>");
				tfPdate.setText(rs.getString("pDate"));
				tfDiscount.setText(rs.getString("discount"));
				taDescription.setText(rs.getString("description"));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
