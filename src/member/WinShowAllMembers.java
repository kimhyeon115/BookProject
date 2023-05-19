package member;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
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
		
		Member[] member = new Member[10];
		person.setPreferredSize(new Dimension(520, 3600));
		for(int i=0; i<member.length; i++) {			
			member[i] = new Member();
			person.add(member[i]);
		}
		JScrollPane jsp = new JScrollPane(person,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		container.add(jsp);
	}
}
