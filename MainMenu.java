import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class MainMenu {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainMenu() { initialize(); }

	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 253);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setLookAndFeel(); // Makes the UI look modern if the user is running on Windows
		
		Database.connect(); // Establish connection to database
		setupClosingDBConnection(); // Handles closing the database connection if the user closes the program
		
		createDemo1Button();
		createDemo2Button();
		createDemo3Button();
	}
	
	// Makes the UI look modern if the user is running on Windows
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
	
	// Handles closing the database connection if the user closes the program
	public static void setupClosingDBConnection() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try { Database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
				} catch (SQLException e) { e.printStackTrace(); }
	        }
	    }, "Shutdown-thread"));
	}
	
	public void createDemo1Button() {
		JButton demo1Button = new JButton("Go to Button/Label/TextField Demo Page");
		demo1Button.setBounds(62, 53, 310, 29);
		frame.getContentPane().add(demo1Button);
		demo1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToButtonLabelTextFieldDemoPage();
			}
		});
	}
	
	public void createDemo2Button() {
		JButton demo2Button = new JButton("Go to Combo Box Demo Page");
		demo2Button.setBounds(62, 94, 310, 29);
		frame.getContentPane().add(demo2Button);
		demo2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToComboBoxDemoPage();
			}
		});
	}
	
	public void createDemo3Button() {
		JButton demo3Button = new JButton("Go to Table Demo Page");
		demo3Button.setBounds(62, 135, 310, 29);
		frame.getContentPane().add(demo3Button);
		demo3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToTablePage();
			}
		});
	}
	
	public void goToButtonLabelTextFieldDemoPage() {
		frame.dispose();
		new ButtonLabelTextFieldDemo();
	}
	
	public void goToComboBoxDemoPage() {
		frame.dispose();
		new ComboBoxDemo();
	}
	
	public void goToTablePage() {
		frame.dispose();
		new TableDemo();
	}
}
