package member;

import java.awt.EventQueue;

import javax.swing.JDialog;

public class WinMemberRemove extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMemberRemove dialog = new WinMemberRemove();
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
	public WinMemberRemove() {
		setBounds(100, 100, 622, 484);
		setTitle("È¸¿ø Å»ÅðÃ¢");
	}

	public WinMemberRemove(int type) {
		this();
		Member member = new Member(type);
		getContentPane().add(member);
	}

}
