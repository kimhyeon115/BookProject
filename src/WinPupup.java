import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class WinPupup extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinPupup dialog = new WinPupup();
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
	public WinPupup() {
		getContentPane().setBackground(new Color(255, 255, 0));
		setTitle("ÆË¾÷ ¸Þ´º ¿¬½À");
		setBounds(100, 100, 610, 402);
		
		JButton btnRed = new JButton("Red");
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().setBackground(Color.red);
			}
		});
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(getContentPane(), popupMenu);
		
		JMenuItem mnuRed = new JMenuItem("»¡°­");
		popupMenu.add(mnuRed);
		
		JMenuItem mnuGreen = new JMenuItem("ÃÊ·Ï");
		popupMenu.add(mnuGreen);
		
		JMenuItem mnuBlue = new JMenuItem("ÆÄ¶û");
		popupMenu.add(mnuBlue);
		getContentPane().add(btnRed, BorderLayout.NORTH);
		
		JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(btnRed, popupMenu_1);
		
		JMenuItem mnuYellow = new JMenuItem("³ë¶û");
		mnuYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().setBackground(Color.yellow);
			}
		});
		popupMenu_1.add(mnuYellow);
		
		JMenuItem mnuOrange = new JMenuItem("¿À·»Áö");
		popupMenu_1.add(mnuOrange);

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
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
