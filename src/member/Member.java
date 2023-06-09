package member;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.WinCalendar;
import util.WinSearchDoro;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Member extends JPanel {
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfEmail;
	private JTextField tfMobile;
	private JTextField tfBirth;
	private JTextField tfAddress;
	private JTextField tfAddressDetail;
	private JTextField tfSolarBirth;
	private JPasswordField tfPassword;
	private JPasswordField tfPassword_1;
	private JButton btnMemberChoice;
	
	private int number;  // type 값 : 1(등록) 2(탈퇴) 3(변경) 4 (검색)
	private JComboBox cbEmail;
	private JCheckBox chkSolarLunar;
	private String path;
	private JLabel lblPic;
	private JButton btnDup;
	private String strEmail;
	private int lsType;
	private String strAddress;
	private String strBirth;
	private String strMobile;
	private String strName;
	private String strID;
	private String oldPath;
	protected String newPath;
	private JButton btnSearchAddress;

	/**
	 * Create the dialog.
	 */
	public Member() {
		setBounds(100, 100, 560, 424);
		setLayout(null);
		
		lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser chooser = new JFileChooser("c:/rlagus/");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지파일","gif","jpg","png","bmp");
					chooser.setFileFilter(filter);
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						path = chooser.getSelectedFile().getPath();
						ImageIcon icon = new ImageIcon(path);
						Image image = icon.getImage();
						image = image.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
						icon = new ImageIcon(image);
						lblPic.setIcon(icon);
						path = path.replaceAll("\\\\","\\\\\\\\\\\\\\\\" );
						
						newPath = path;
					}
				}
			}
		});				
		lblPic.setBackground(Color.GRAY);
		lblPic.setOpaque(true);
		lblPic.setBounds(25, 24, 144, 186);
		add(lblPic);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(206, 27, 70, 15);
		add(lblId);
		
		tfId = new JTextField();
		tfId.setBackground(new Color(192, 192, 192));
		tfId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(number == 1) { //회원 등록
						
						if(isDup()) {
							JOptionPane.showMessageDialog(null, "이미 존재합니다");
							tfId.setSelectionStart(0);
							tfId.setSelectionEnd(tfId.getText().length());
							tfId.requestFocus();
						}
						else
							tfPassword.requestFocus();
						
					}else if(number == 2 || number == 3 || number == 4)
						showRecord(tfId.getText(), 1);	// id(primary key)를 조회하여 보여준다.
				}
			}
		});
		tfId.setBounds(291, 24, 116, 21);
		add(tfId);
		tfId.setColumns(10);
		
		btnDup = new JButton("중복확인");	
		btnDup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 오직 회원 가입에서만 사용된다.
				if(isDup()) {
					JOptionPane.showMessageDialog(null, "이미 존재합니다.");
					tfId.setSelectionStart(0);
					tfId.setSelectionEnd(tfId.getText().length());
					tfId.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다.");
					tfPassword.requestFocus();
				}
			}			
		});
		btnDup.setBounds(438, 27, 97, 23);
		add(btnDup);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(206, 61, 70, 15);
		add(lblPassword);
		
		JLabel lblPassword_1 = new JLabel("Password \uD655\uC778");
		lblPassword_1.setBounds(206, 94, 97, 15);
		add(lblPassword_1);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(206, 125, 70, 15);
		add(lblName);
					
		tfName = new JTextField();
		tfName.setBackground(new Color(255, 255, 0));
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(number==1) {
						tfEmail.requestFocus();
					} else { // 이름을 찾아 선택하여 자세히 보여주기(id)
						ShowList showList = new ShowList(tfName.getText(),1);	// 1: 이름, 2: 전화번호일부
						showList.setModal(true);
						showList.setVisible(true);
						
						showRecord(showList.getID(), 1);  // ID: 1, Email:2
					}
				}
			}
		});
		tfName.setColumns(10);
		tfName.setBounds(291, 122, 116, 21);
		add(tfName);		
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(206, 156, 70, 15);
		add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(number==1)
						tfMobile.requestFocus();
					else { 
						String sEmail = tfEmail.getText() + "@" + cbEmail.getSelectedItem();
						showRecord(sEmail, 2);  // 이메일 검색
					}
				}
			}
		});
		tfEmail.setBackground(new Color(192, 192, 192));
		tfEmail.setColumns(10);
		tfEmail.setBounds(291, 153, 116, 21);
		add(tfEmail);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(206, 192, 70, 15);
		add(lblMobile);
		
		tfMobile = new JTextField();
		tfMobile.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(number==1) {  //회원 가입
						if(isCorrect(tfMobile.getText()))
							tfBirth.requestFocus();
						else {
							tfMobile.setSelectionStart(0);
							tfMobile.setSelectionEnd(tfMobile.getText().length());
							tfMobile.requestFocus();
						}
					}else { // 전화번호의 일부를 가지고 ID 찾아 자세히 보여주기(id)
						ShowList showList = new ShowList(tfMobile.getText(),2); // 1: 이름 2: 전화번호
						showList.setModal(true);
						showList.setVisible(true);
						
						showRecord(showList.getID(), 1);  // ID: 1, Email:2  => PK
					}
				}				
			}
		});
		tfMobile.setColumns(10);
		tfMobile.setBounds(291, 189, 116, 21);
		add(tfMobile);
		
		JLabel lblBirth = new JLabel("Birth");
		lblBirth.setBounds(132, 253, 32, 15);
		add(lblBirth);		
		
		tfBirth = new JTextField();
		tfBirth.setColumns(10);
		tfBirth.setBounds(163, 250, 116, 21);
		add(tfBirth);
		
		chkSolarLunar = new JCheckBox("양력");
		chkSolarLunar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(chkSolarLunar.isSelected()) {
					chkSolarLunar.setText("음력");
					tfSolarBirth.setText("나중에...");
				}else {
					chkSolarLunar.setText("양력");
					tfSolarBirth.setText(tfBirth.getText());
				}
				tfAddress.requestFocus();
			}
		});
		chkSolarLunar.setBounds(67, 249, 57, 23);
		add(chkSolarLunar);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(75, 299, 57, 15);
		add(lblAddress);
		
		tfAddress = new JTextField();
		tfAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(tfAddress.getText().toString().length() == 0) {
						btnSearchAddress.requestFocus();
					} else {
						tfAddressDetail.requestFocus();
					}
				}
			}
		});
		tfAddress.setColumns(10);
		tfAddress.setBounds(150, 296, 257, 21);
		add(tfAddress);
		
		btnSearchAddress = new JButton("주소 찾기...");
		btnSearchAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinSearchDoro winSearchDoro = new WinSearchDoro();
				winSearchDoro.setModal(true);
				winSearchDoro.setVisible(true);
				
				tfAddress.setText(winSearchDoro.getAddress());
				tfAddressDetail.requestFocus();
			}
		});
		btnSearchAddress.setBounds(438, 295, 97, 53);
		add(btnSearchAddress);
		
		tfAddressDetail = new JTextField();
		tfAddressDetail.setColumns(10);
		tfAddressDetail.setBounds(150, 327, 257, 21);
		add(tfAddressDetail);				
		
		JButton btnCalendar = new JButton("달력 선택...");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfBirth.setText(winCalendar.getDate());
				tfSolarBirth.setText(winCalendar.getDate());
				
				tfAddress.requestFocus();
			}
		});
		btnCalendar.setBounds(291, 249, 97, 23);
		add(btnCalendar);				
		
		JLabel lblAt = new JLabel("@");
		lblAt.setBounds(419, 156, 18, 15);
		add(lblAt);
		
		cbEmail = new JComboBox();
		cbEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cbEmail.getSelectedItem().toString().equals("직접 입력"))
					tfMobile.requestFocus();
				else {
					cbEmail.setSelectedItem("");
					cbEmail.requestFocus();
				}
					
			}
		});
		cbEmail.setModel(new DefaultComboBoxModel(new String[] {"naver.com", "google.com", "daum.net", "korea.com", "paran.com", "ici.re.kr", "sorry.com", "happy.net", "직접 입력"}));
		cbEmail.setEditable(true);
		cbEmail.setBounds(438, 152, 97, 23);
		add(cbEmail);
		
		tfSolarBirth = new JTextField();
		tfSolarBirth.setBounds(438, 250, 97, 21);
		add(tfSolarBirth);
		tfSolarBirth.setColumns(10);
		
		JLabel lblAddress_1 = new JLabel("\uC0C1\uC138\uC8FC\uC18C");
		lblAddress_1.setBounds(75, 333, 57, 15);
		add(lblAddress_1);
		
		tfPassword = new JPasswordField();
		tfPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String pw2 = new String(tfPassword_1.getPassword());
					tfPassword_1.setSelectionStart(0);
					tfPassword_1.setSelectionEnd(pw2.length());
					tfPassword_1.requestFocus();
				}
			}
		});
		tfPassword.setBounds(291, 58, 116, 21);
		add(tfPassword);
		
		tfPassword_1 = new JPasswordField();
		tfPassword_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String pw1 = new String(tfPassword.getPassword());
					String pw2 = new String(tfPassword_1.getPassword());
					if(pw1.equals(pw2)) {
					tfName.requestFocus();
					} else {
						JOptionPane.showMessageDialog(null, "암호가 일치하지 않습니다");
						tfPassword.setSelectionStart(0);
						tfPassword.setSelectionEnd(pw1.length());
						tfPassword.requestFocus();
					}
				}
			}
		});
		tfPassword_1.setBounds(291, 91, 116, 21);
		add(tfPassword_1);
		
		JLabel lblBirth_1 = new JLabel("\uC62C\uD574");
		lblBirth_1.setBounds(400, 253, 32, 15);
		add(lblBirth_1);
		
		btnMemberChoice = new JButton("회원 등록");  // "회원 등록/변경/탈퇴/검색"으로 변경된다.
		btnMemberChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(number == 1) {
					insertRecord();
				}else if(number == 2) {
					deleteRecord();
				}else if(number == 3) {
					if(isChanged()) {
						updateRecord();
					}
				}
			}
		});
		btnMemberChoice.setBounds(228, 362, 116, 37);
		add(btnMemberChoice);
	}
	
	protected void updateRecord() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "update memberTBL set name='" + tfName.getText() + "', email='";
			sql = sql + tfEmail.getText() + "@" + cbEmail.getSelectedItem() + "', mobile='";
			sql = sql + tfMobile.getText() + "', birth='" + tfBirth.getText() + "', address='";			
			sql = sql + tfAddress.getText() + tfAddressDetail.getText() + "', pic='";
			sql = sql + path + "', Istype=";
			sql = sql + (chkSolarLunar.isSelected() ? 1 : 0) + " where id='";
			sql = sql + tfId.getText() + "'";
			
			stmt.executeUpdate(sql);
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}		
	}

	protected boolean isChanged() {
		boolean bChanged = false;		
		int value = chkSolarLunar.isSelected() ? 1 : 0;
		
		if(!strName.equals(tfName.getText()))
			bChanged = true;
		else if(!strMobile.equals(tfMobile.getText()))
			bChanged = true;
		else if(!strBirth.equals(tfBirth.getText()))
			bChanged = true;
		else if(!strAddress.equals(tfAddress.getText() + tfAddressDetail.getText()))
			bChanged = true;
		else if(!strEmail.equals(tfEmail.getText() + "@" + cbEmail.getSelectedItem()))
			bChanged = true;			
		else if(!oldPath.equals(newPath))
			bChanged = true;
		else if(value != lsType)
			bChanged = true;
		return bChanged;
	   }
	
	protected boolean isDup() {
		boolean bDup = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select count(*) from memberTBL where id='" + tfId.getText() + "'";			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				if(rs.getInt(1) == 1)  // id가 기본키이기 때문에 숫자 개수(count()): 1개
					bDup = true;
				else 
					bDup = false;
			}		
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return bDup;
	}
	
	protected void showRecord(String sValue, int type) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "";
			if(type == 1) // id
				sql = "select * from memberTBL where id='" + sValue + "'";
			else if(type == 2) // email
				sql = "select * from memberTBL where email='" + sValue + "'";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				strID = rs.getString("id");
				tfId.setText(strID);
				
				//tfPassword는 삭제를 결정했을 때 인증 수단으로 써 보자.
				strName = rs.getString("name");
				tfName.setText(strName);
				
				strMobile = rs.getString("mobile");
				tfMobile.setText(strMobile);
				
				strBirth = rs.getString("birth");
				tfBirth.setText(strBirth);
				
				strAddress = rs.getString("address"); 
				tfAddress.setText(strAddress);
				
				lsType = rs.getInt("IsType");				
				
				if(lsType == 0)
					chkSolarLunar.setSelected(false);
				else
					chkSolarLunar.setSelected(true);
				
				strEmail = rs.getString("email");
				tfEmail.setText(strEmail.substring(0,strEmail.indexOf("@")));
				cbEmail.setSelectedItem(strEmail.substring(strEmail.indexOf("@")+1));
				
				//=====================
				
				path = rs.getString("pic");
				oldPath = path;
				
				ImageIcon icon = new ImageIcon(path);
				Image image = icon.getImage();
				image = image.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				lblPic.setIcon(icon);
				path = path.replaceAll("\\\\","\\\\\\\\\\\\\\\\" );
				
			}			
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}	
		
	}
	
	protected void deleteRecord() {
		if( JOptionPane.showConfirmDialog(null, "정말 삭제할까요?") == JOptionPane.YES_OPTION) {
			if(isCorrect()) {  // 암호가 일치하면
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
					Statement stmt = con.createStatement();
					
					String sql = "delete from memberTBL where id='" + tfId.getText() + "'";				
					stmt.executeUpdate(sql);  // 레코드 삽입
					
					
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "암호가 틀렸습니다");
			}
		}
	}
	
	private boolean isCorrect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select password from memberTBL where id='" + tfId.getText() + "'";
			ResultSet rs = stmt.executeQuery(sql);
			String tablePassword="";
			while(rs.next()) {
				tablePassword = rs.getString("password");
			}
			String userPassword = new String(tfPassword.getPassword());
			
			if(userPassword.equals(tablePassword))
				return true;
			else 
				return false;
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}
	
	protected void insertRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "insert into memberTBL values('" + tfId.getText() + "','";
			sql = sql + new String(tfPassword.getPassword()) + "','" + tfName.getText() + "','";
			sql = sql + tfEmail.getText() + "@" + cbEmail.getSelectedItem() + "','";
			sql = sql + tfMobile.getText() + "','" + tfBirth.getText() + "'," ;
			if(chkSolarLunar.isSelected()) // 양력(0), 음력(1)
				sql = sql + "1,'";
			else
				sql = sql + "0,'";
			sql = sql + tfAddress.getText() + " " + tfAddressDetail.getText() + "','";
			sql = sql + path + "')";
			
			stmt.executeUpdate(sql);  // 레코드 삽입
			
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}

	public Member(int type) {
		this();
		if(type == 1) {
			btnMemberChoice.setText("회원 가입");
			btnDup.setVisible(true);
		}
		else if(type == 2) {
			btnMemberChoice.setText("회원 탈퇴");
			btnDup.setVisible(false);
		}
		else if(type == 3) {
			btnMemberChoice.setText("회원 변경");
			btnDup.setVisible(false);
		}
		else if(type == 4)
			btnMemberChoice.setText("회원 검색");
		
		number = type;
	}
	
	public Member(String strID, String strPW, String strName, 
			String strEmail, String strMobile, String strBirth,
			String strLsType, String strAddress, String strPic) {
		this();
		tfId.setText(strID);
		tfPassword.setText(strPW);
		tfName.setText(strName);
		
		String arrEmail[] = strEmail.split("@");
		tfEmail.setText(arrEmail[0]);
		cbEmail.setSelectedItem(arrEmail[1]);
		
		tfMobile.setText(strMobile);
		tfBirth.setText(strBirth);
		
		if(strLsType.equals("0"))
			chkSolarLunar.setSelected(false);
		else
			chkSolarLunar.setSelected(true);
		tfAddress.setText(strAddress);
				
		ImageIcon icon = new ImageIcon(strPic);
		Image image = icon.getImage();
		image = image.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		lblPic.setIcon(icon);
	}

	protected boolean isCorrect(String text) {
		text = text.replace("-", "");
		if(text.length() == 8)
			text = "010" + text;
		
		text = text.substring(0,3) + "-" + text.substring(3,7) + "-" + text.substring(7);
		
		if(text.length()==13) {
			tfMobile.setText(text);
			return true;
		}else {			
			return false;
		}
	}

	protected int getCountHyphen(String text) {
		int count = 0;
		for(int i=0; i<text.length();i++)
			if(text.charAt(i) == '-')
				count++;
		
		return count;
	}	
}
