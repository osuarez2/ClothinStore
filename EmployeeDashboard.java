
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeDashboard {

	private JFrame frame;


	
	public EmployeeDashboard() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 320);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome, " + GlobalSession.getUserName());
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel.setBounds(324, 0, 242, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Customer Accounts");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAccountPage();
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton.setBounds(0, 44, 199, 21);
		frame.getContentPane().add(btnNewButton);
		
		
		
		JButton btnNewButton_1 = new JButton("Orders");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToOrdersListPage();
			}
		});
		
		btnNewButton_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton_1.setBounds(0, 96, 199, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		
		
		JButton btnNewButton_2 = new JButton("Items");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToItemPage();
			}
		});
		btnNewButton_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnNewButton_2.setBounds(0, 150, 199, 21);
		frame.getContentPane().add(btnNewButton_2);
		
		
		frame.setVisible(true);
	}
	public void goToItemPage() {
        frame.dispose();
        new Items();

	}
	
	public void goToOrdersListPage() {
		frame.dispose();
		new OrdersList();
	}

	public void goToAccountPage() {
		frame.dispose();
		new AccountsList();
	}
	
	

}