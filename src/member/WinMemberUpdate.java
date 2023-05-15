package member;

import java.awt.EventQueue;

import javax.swing.JDialog;

public class WinMemberUpdate extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMemberUpdate dialog = new WinMemberUpdate();
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
	public WinMemberUpdate() {
		setBounds(100, 100, 622, 484);
		setTitle("ȸ�� ����â");

	}

	public WinMemberUpdate(int type) {
		this();
		Member member = new Member(type);
		getContentPane().add(member);
	}

}
