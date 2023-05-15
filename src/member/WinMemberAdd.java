package member;

import java.awt.EventQueue;

import javax.swing.JDialog;

public class WinMemberAdd extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMemberAdd dialog = new WinMemberAdd();
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
	public WinMemberAdd() {
		setBounds(100, 100, 622, 484);
		setTitle("ȸ�� ����â");		
	}

	public WinMemberAdd(int type) {
		this();
		Member member = new Member(type);
		getContentPane().add(member);
	}

}
