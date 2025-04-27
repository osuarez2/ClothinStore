package storeFront;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;

public class storeMainMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					storeMainMenu window = new storeMainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public storeMainMenu() {
		initialize();
	}

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Database.connect();
		setupClosingDBConnection();
		
		accountButton();
		cartButton();
		mensButton();
		womensButton();
		kidsButton();
		couponButton();
		
	
		
	}
	
	//shutdown disconnect
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try { Database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
	        }
	    }, "Shutdown-thread"));
	}
	
	public void setupLookandFeel()
	{
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowLookAndFeel");
		} catch (Exception e) {}
	}
	
	private void accountButton()
	{
		JButton accountButton = new JButton("My Account");
		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToMyAccount();
			}
		});
		accountButton.setBounds(367, 1, 107, 23);
		frame.getContentPane().add(accountButton);
	}
	
	private void cartButton()
	{
		JButton cartButton = new JButton("My Cart");
		cartButton.setBounds(269, 1, 89, 23);
		frame.getContentPane().add(cartButton);
	}
	
	private void mensButton()
	{
		JButton mens_Button = new JButton("Men's Clothing");
		mens_Button.setBounds(171, 69, 153, 23);
		frame.getContentPane().add(mens_Button);
	}
	
	private void womensButton()
	{
		JButton womens_Button = new JButton("Women's Clothing");
		womens_Button.setBounds(171, 115, 153, 23);
		frame.getContentPane().add(womens_Button);
	}
	
	private void kidsButton()
	{
		JButton child_Button = new JButton("Children's Clothing");
		child_Button.setBounds(171, 166, 153, 23);
		frame.getContentPane().add(child_Button);
	}
	
	private void couponButton()
	{
		JButton coupon_button = new JButton("Today's Deals");
		coupon_button.setBounds(171, 210, 153, 23);
		frame.getContentPane().add(coupon_button);
		{
			JLabel lblNewLabel = new JLabel("Welcome to [Clothing Store]!");
			lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
			lblNewLabel.setBounds(0, 1, 240, 28);
			frame.getContentPane().add(lblNewLabel);
		}
	}
	
	public void goToMyAccount() 
	{
		frame.dispose();
		new myAccount();
	}
	
	
}
