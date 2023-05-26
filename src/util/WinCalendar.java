package util;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Font;

public class WinCalendar extends JDialog {

	private JPanel panelCalendar;
	private JComboBox cbMonth;
	private JComboBox cbYear;
	private JButton btnNextMonth;
	private JButton btnPrevMonth;
	private JButton btnNextYear;
	private JButton btnPrevYear;
	
	private int gYear;
	private int gMonth;
	private int gDay;
	
	
	private String selectedDate;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinCalendar dialog = new WinCalendar();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getDate() {
		return selectedDate;
	}

	/**
	 * Create the dialog.
	 */
	public WinCalendar() {
		setTitle("�޷�(1923��~2123��)");
		setBounds(100, 100, 450, 392);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		panelCalendar = new JPanel();
		getContentPane().add(panelCalendar, BorderLayout.CENTER);
		panelCalendar.setLayout(new GridLayout(0, 7, 2, 2));
		
		
		JButton btnRun = new JButton("�޷º���");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();				
			}
		});
		
		cbMonth = new JComboBox();
		cbMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();
			}
		});
		
		btnPrevMonth = new JButton("<");
		btnPrevMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(cbYear.getSelectedItem().toString());
				int month = Integer.parseInt(cbMonth.getSelectedItem().toString());
				month--;		
				if(month==0) {
					year--;
					month=12;
				}
				cbYear.setSelectedItem(year);
				cbMonth.setSelectedIndex(month-1);
				showCalendar();
			}
		});
		
		btnPrevYear = new JButton("<<");
		btnPrevYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(cbYear.getSelectedItem().toString());
				
				year--;
				if(year == 1923)
					year=193;
				
				cbYear.setSelectedItem(year);
				
				showCalendar();
			}
		});
		panel.add(btnPrevYear);
		panel.add(btnPrevMonth);
		
		cbYear = new JComboBox();
		cbYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();
			}
		});
		panel.add(cbYear);
		cbMonth.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		panel.add(cbMonth);
		panel.add(btnRun);
		
		btnNextMonth = new JButton(">");
		btnNextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(cbYear.getSelectedItem().toString());
				int month = Integer.parseInt(cbMonth.getSelectedItem().toString());
				month++;		
				if(month==13) {
					year++;
					month=1;
				}
				cbYear.setSelectedItem(year);
				cbMonth.setSelectedIndex(month-1);	
				
				showCalendar();
			}
		});
		panel.add(btnNextMonth);
		
		btnNextYear = new JButton(">>");
		btnNextYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = Integer.parseInt(cbYear.getSelectedItem().toString());
				
				year++;
				if(year == 2123)
					year=2123;
				
				cbYear.setSelectedItem(year);
				
				showCalendar();
			}
		});
		panel.add(btnNextYear);

		for(int year=1923;year<=2123;year++)
			cbYear.addItem(year);
		
		// ���� ������ ���� �˾ƿ���
		Calendar today = Calendar.getInstance();		
		gYear = today.get(Calendar.YEAR);
		gMonth = today.get(Calendar.MONTH) + 1; //0~11 => 1~12
		gDay = today.get(Calendar.DAY_OF_MONTH);
		  
		cbYear.setSelectedItem(gYear);
		cbMonth.setSelectedItem(Integer.toString(gMonth));
		//cbMonth.setSelectedIndex(today.get(Calendar.MONTH));
	}

	protected void showCalendar() {
		// ��ư �� �����
		Component []componentList = panelCalendar.getComponents();
		for(Component c: componentList) {
			if(c instanceof JButton )
				panelCalendar.remove(c);
		}
		panelCalendar.revalidate();
		panelCalendar.repaint();
		
		// �Ͽ���~����� ��ư �����Ѵ�.
//		JButton btn1 = new JButton("�Ͽ���");	panelCalendar.add(btn1);	
//		JButton btn2 = new JButton("������");	panelCalendar.add(btn2);
//		JButton btn3 = new JButton("ȭ����");	panelCalendar.add(btn3);
//		JButton btn4 = new JButton("������");	panelCalendar.add(btn4);
//		JButton btn5 = new JButton("�����");	panelCalendar.add(btn5);
//		JButton btn6 = new JButton("�ݿ���");	panelCalendar.add(btn6);
//		JButton btn7 = new JButton("�����");	panelCalendar.add(btn7);
		
//		String yoil[] = {"��","��","ȭ","��","��","��","��"};
//		for(int i=0;i<yoil.length;i++) {
//			JButton btn = new JButton(yoil[i] + "����");	
//			panelCalendar.add(btn);
//		}
		
		 String yoil = "�Ͽ�ȭ�������";
	      for(int i=0;i<yoil.length();i++) {
	         JButton btn = new JButton(yoil.substring(i,i+1));   
	         btn.setBackground(Color.GREEN);
	         btn.setForeground(new Color(0,0,255));
	         //btn.setEnabled(false);
	         btn.setFont(new Font("����", Font.BOLD, 16));
	         panelCalendar.add(btn);
	      }

	    int Month[] = {31,28,31,30,31,30,31,31,30,31,30,31};
	    int year = Integer.parseInt(cbYear.getSelectedItem().toString());
		int month = Integer.parseInt(cbMonth.getSelectedItem().toString());
		int sum = 0;
		
		// �ش��ϴ� ������������ ���� ���Ͻÿ�.(1923.1.1~2022.12.31)
		for(int i=1923;i<year;i++) {
			if(i%4==0 && i%100 !=0 || i%400==0)
				sum = sum + 366;
			else
				sum = sum + 365;
		}
		// �ش��ϴ� ���������� ��¥ ���� ���� ���Ͻÿ�. 
		for(int i=0;i<month-1;i++) {
			if(i==1 && (year%4==0 && year%100!=0 || year%400==0))
				sum = sum + ++Month[i];
			else
				sum = sum + Month[i];
		}		
	    
	    // 1923�⵵ 1�� 1���� ������ ������(1)���� �����̴�.
	      
	    int start = 1;
	    start = (start + sum ) % 7;
	    
	    for(int i=1;i<=start;i++) {  
			JButton btn = new JButton("");
			panelCalendar.add(btn);			
			btn.setVisible(false);
		}
	    
	      
		// �ش��ϴ� ���� ������ ��¥���� ��ư�� �����Ѵ�.
		
		int last = Month[month-1];
		if(month==2 &&  (year%4==0 && year%100!=0 || year%400==0) )
			last++;
		for(int i=1;i<=last;i++) {  // 1�Ϻ��� �ش���� ������ ��¥�� ����Ѵ�.			
			JButton btn = new JButton(i + "");
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn1 = (JButton)e.getSource();
					
					int day = Integer.parseInt(btn1.getText());
					
					selectedDate = year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;
					
					System.out.println(selectedDate);
					dispose();
				}
			});
			if(year == gYear && month == gMonth	&& i == gDay)
				btn.setBackground(Color.red);
			panelCalendar.add(btn);			
			panelCalendar.revalidate();
		}
	}

}
