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
import javax.sound.midi.Patch;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	private JButton btnSearchAddress;
	
	private int number;
	private JCheckBox chkSorlarLunar;
	protected String path;
	private JComboBox cbEmail;
	private JLabel lblPic;

	/**
	 * Create the dialog.
	 */
	public Member() {
		setBounds(100, 100, 583, 424);
		setLayout(null);
		
		lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser chooser = new JFileChooser("C:/rlagus/testimage/");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "png", "gif");
					chooser.setFileFilter(filter);
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						path = chooser.getSelectedFile().getPath();
						ImageIcon icon = new ImageIcon(path);
						Image image = icon.getImage();
						image = image.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
						icon = new ImageIcon(image);
						lblPic.setIcon(icon);
						path = path.replaceAll("\\\\", "\\\\\\\\");
					}					
				}
			}
		});
		lblPic.setOpaque(true);
		lblPic.setBackground(Color.GRAY);
		lblPic.setBounds(27, 27, 150, 200);
		add(lblPic);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(206, 27, 70, 15);
		add(lblId);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(206, 61, 70, 15);
		add(lblPassword);
		
		JLabel lblPassword_1 = new JLabel("Password \uD655\uC778");
		lblPassword_1.setBounds(206, 94, 97, 15);
		add(lblPassword_1);
		
		tfId = new JTextField();
		tfId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPassword.requestFocus();
				}
			}
		});
		tfId.setBounds(315, 27, 116, 21);
		add(tfId);
		tfId.setColumns(10);
		
		tfName = new JTextField();
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfEmail.requestFocus();
				}
			}
		});
		tfName.setColumns(10);
		tfName.setBounds(315, 122, 116, 21);
		add(tfName);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(206, 125, 70, 15);
		add(lblName);
		
		JButton btnNewButton = new JButton("\uC911\uBCF5\uD655\uC778");
		btnNewButton.setBounds(463, 27, 97, 23);
		add(btnNewButton);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(206, 156, 70, 15);
		add(lblEmail);
		
		tfEmail = new JTextField();
		tfEmail.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String sEmail = tfEmail.getText() + "@" + cbEmail.getSelectedItem();
					boolean bOne = false;
					int zero = 1;
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
						String sql = "SELECT count(*) FROM membertbl WHERE email=?";
						PreparedStatement pstmt = con.prepareStatement(sql);
						pstmt.setString(1, sEmail);
						ResultSet rs = pstmt.executeQuery();
						
						while(rs.next()) {
							if(rs.getInt(1) == 1) {
								bOne = true;
							} else if(rs.getInt(1) > 1) {
								bOne = false;
							} else {
								zero = 0;
							}
						}
						
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					if(zero == 1 && bOne) {
						showRecord(sEmail);
					}
				}
			}			
		});				
		tfEmail.setBackground(new Color(192, 192, 192));
		tfEmail.setColumns(10);
		tfEmail.setBounds(315, 153, 116, 21);
		add(tfEmail);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(206, 192, 70, 15);
		add(lblMobile);
		
		JLabel lblBirth = new JLabel("Birth");
		lblBirth.setBounds(140, 253, 32, 15);
		add(lblBirth);
		
		tfMobile = new JTextField();
		tfMobile.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(isCorrect(tfMobile.getText())) {
					tfBirth.requestFocus();
					} else {
						JOptionPane.showMessageDialog(null, "전화번호를 잘못 입력하였습니다");
						tfMobile.setSelectionStart(0);
						tfMobile.setSelectionEnd(tfMobile.getText().length());
					}
				}
			}
		});
		tfMobile.setColumns(10);
		tfMobile.setBounds(315, 189, 116, 21);
		add(tfMobile);
		
		tfBirth = new JTextField();
		tfBirth.setColumns(10);
		tfBirth.setBounds(184, 250, 116, 21);
		add(tfBirth);
		
		chkSorlarLunar = new JCheckBox("양력");
		chkSorlarLunar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(chkSorlarLunar.isSelected()) {
					chkSorlarLunar.setText("음력");
					tfSolarBirth.setText("나중에...");
				} else {
					chkSorlarLunar.setText("양력");
					tfSolarBirth.setText(tfBirth.getText());
				}
				tfAddress.requestFocus();
			}
		});
		
		chkSorlarLunar.setBounds(75, 249, 57, 23);
		add(chkSorlarLunar);
		
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
		tfAddress.setBounds(150, 296, 281, 21);
		add(tfAddress);
		
		btnSearchAddress = new JButton("\uC8FC\uC18C \uCC3E\uAE30...");
		btnSearchAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					CallSearchiDoro();
				}
			}
		});
		btnSearchAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CallSearchiDoro();
			}
		});
		btnSearchAddress.setBounds(463, 295, 97, 53);
		add(btnSearchAddress);
		
		tfAddressDetail = new JTextField();
		tfAddressDetail.setBounds(150, 327, 281, 21);
		add(tfAddressDetail);
		tfAddressDetail.setColumns(10);
		
		JButton btnCalendar = new JButton("\uB2EC\uB825 \uC120\uD0DD...");
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
		btnCalendar.setBounds(310, 249, 97, 23);
		add(btnCalendar);
		
		JLabel lblAt = new JLabel("@");
		lblAt.setBounds(443, 156, 18, 15);
		add(lblAt);
		
		cbEmail = new JComboBox();
		cbEmail.setEditable(true);
		cbEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cbEmail.getSelectedItem().toString().equals("직접입력")) {
					tfMobile.requestFocus();
				} else {
					cbEmail.setSelectedItem("");
					cbEmail.requestFocus();
				}
			}
		});
		
		cbEmail.setModel(new DefaultComboBoxModel(new String[] {"\uC9C1\uC811\uC785\uB825", "naver.com", "gmail.com", "daum.net", "ici.co.kr", "hotmail.com"}));
		cbEmail.setBounds(463, 152, 97, 23);
		add(cbEmail);
		
		tfSolarBirth = new JTextField();
		tfSolarBirth.setBounds(463, 250, 97, 21);
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
		tfPassword.setBounds(315, 58, 116, 21);
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
		tfPassword_1.setBounds(315, 91, 116, 21);
		add(tfPassword_1);
		
		JLabel lblBirth_1 = new JLabel("\uC62C\uD574");
		lblBirth_1.setBounds(419, 253, 32, 15);
		add(lblBirth_1);
		
		btnMemberChoice = new JButton("회원 등록");
		btnMemberChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(number == 1) {
					insertRecord();
				} else if(number ==2) {
					deleteRecord();
				}
			}
		});
		btnMemberChoice.setBounds(239, 368, 97, 32);
		add(btnMemberChoice);

	}

	protected void deleteRecord() {
		// TODO Auto-generated method stub
		
	}

	protected void showRecord(String sEmail) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			String sql = "SELECT * FROM membertbl WHERE email=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sEmail);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				tfId.setText(rs.getString("id"));
				tfName.setText(rs.getString("name"));
				tfMobile.setText(rs.getString("mobile"));
				tfBirth.setText(rs.getString("birth"));
				tfAddress.setText(rs.getString("address"));
				if(rs.getInt("IsType") == 0 ){
					chkSorlarLunar.setSelected(false);
				} else {
					chkSorlarLunar.setSelected(true);
				}
				
				path = rs.getString("pic");
				ImageIcon icon = new ImageIcon(path);
				Image image = icon.getImage();
				image = image.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				lblPic.setIcon(icon);
				path = path.replaceAll("\\\\", "\\\\\\\\");
			}			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}		
	}

	protected void insertRecord() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			String sql = "INSERT INTO membertbl VALUES(?,?,?,?,?,?,?,?,?) ";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfId.getText());
			pstmt.setString(2, tfPassword.getPassword().toString());
			pstmt.setString(3, tfName.getText());
			pstmt.setString(4, tfEmail.getText()+"@"+cbEmail.getSelectedItem());
			pstmt.setString(5, tfMobile.getText());
			pstmt.setString(6, tfBirth.getText());
			if(chkSorlarLunar.isSelected()) {
				pstmt.setInt(7, 1);
			} else {
				pstmt.setInt(7, 0);
			}			
			pstmt.setString(8, tfAddress.getText() + " " + tfAddressDetail.getText());
			pstmt.setString(9, path);
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}		
	}

	public Member(int type) {
		this();
		if(type == 1) {
			btnMemberChoice.setText("회원 가입");			
		} else if(type == 2) {
			btnMemberChoice.setText("회원 탈퇴");
		} else if(type == 3) {
			btnMemberChoice.setText("회원 변경");
		} else if(type == 4){
			btnMemberChoice.setText("회원 검색");
		}
		number = type;
	}

	protected boolean isCorrect(String text) {
		text = text.replace("-", "");
		if(text.length() == 8) {
			text = "010" + text;
		}
		text = text.substring(0,3) + "-" + text.substring(3,7) + "-" + text.substring(7);
		if(text.length() == 13) {
			tfMobile.setText(text);
			return true;
		} else {
			return false;
		}
	}
	
	public void CallSearchiDoro() {
		WinSearchDoro searchDoro = new WinSearchDoro();
		searchDoro.setModal(true);
		searchDoro.setVisible(true);
		tfAddress.setText(searchDoro.getAddress());
		
		tfAddressDetail.requestFocus();
	}
	
	protected int getCountHyphen(String text) {
		int count = 0;
		for(int i=0; i<text.length();i++)
			if(text.charAt(i) == '-')
				count++;
		
		return count;
	}
}
