import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;


public class Items {

	private JFrame frame;
	private JTextField Name;
	private JTextField Price;
	private JTextField Quantity;
	private JTextField sizeField;
	private JComboBox<String> deptCombo;
	private JTable inventoryTable;

	
	public Items() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Manage Inventory");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial Black", Font.BOLD, 20));
        lblTitle.setBounds(300, 10, 400, 30);
        frame.getContentPane().add(lblTitle);

        JLabel lblName = new JLabel("Item Name");
        lblName.setBounds(50, 60, 100, 20);
        frame.getContentPane().add(lblName);

        Name = new JTextField();
        Name.setBounds(150, 60, 120, 20);
        frame.getContentPane().add(Name);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setBounds(50, 100, 100, 20);
        frame.getContentPane().add(lblPrice);

        Price = new JTextField();
        Price.setBounds(150, 100, 120, 20);
        frame.getContentPane().add(Price);

        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setBounds(50, 140, 100, 20);
        frame.getContentPane().add(lblQuantity);

        Quantity = new JTextField();
        Quantity.setBounds(150, 140, 120, 20);
        frame.getContentPane().add(Quantity);

        JLabel lblSize = new JLabel("Size");
        lblSize.setBounds(50, 180, 100, 20);
        frame.getContentPane().add(lblSize);

        sizeField = new JTextField();
        sizeField.setBounds(150, 180, 120, 20);
        frame.getContentPane().add(sizeField);

        JLabel lblDept = new JLabel("Department");
        lblDept.setBounds(50, 220, 100, 20);
        frame.getContentPane().add(lblDept);

        String[] departments = {"Mens", "Womens", "Kids"};
        deptCombo = new JComboBox<>(departments);
        deptCombo.setBounds(150, 220, 120, 20);
        frame.getContentPane().add(deptCombo);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(50, 260, 100, 30);
        frame.getContentPane().add(btnAdd);

        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(170, 260, 150, 30);
        frame.getContentPane().add(btnDelete);

        // Table section
        inventoryTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBounds(320, 60, 640, 400);
        frame.getContentPane().add(scrollPane);

        // Load inventory data
        loadInventoryTable();

        // Add item
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCustomItem();
                loadInventoryTable(); // Refresh table
            }
        });

        // Delete selected item
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem();
                loadInventoryTable(); // Refresh table
            }
        });

        // Optional nav buttons
        createSignOutButton();
        createBackToDashboardButton();

        frame.setVisible(true);
    }

    private void loadInventoryTable() {
        try {
            String query = "SELECT i.item_id AS ID, i.item_name AS Name, i.item_size AS Size, i.item_price AS Price, i.in_stock AS Quantity, d.dept_name AS Department " +
                           "FROM inventory i JOIN department d ON i.dept_id = d.dept_id";
            PreparedStatement stm = Database.connection.prepareStatement(query);
            ResultSet result = stm.executeQuery();
            inventoryTable.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCustomItem() {
        try {
            String query = "INSERT INTO inventory (item_name, item_size, item_price, in_stock, dept_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = Database.connection.prepareStatement(query);
            stm.setString(1, Name.getText());
            stm.setString(2, sizeField.getText());
            stm.setString(3, Price.getText());
            stm.setString(4, Quantity.getText());

            String selectedDept = (String) deptCombo.getSelectedItem();
            int deptId = selectedDept.equals("Mens") ? 1 : selectedDept.equals("Womens") ? 2 : 3;
            stm.setInt(5, deptId);

            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "The new item was added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            Object idValue = inventoryTable.getValueAt(selectedRow, 0); // First column is ID
            try {
                String query = "DELETE FROM inventory WHERE item_id = ?";
                PreparedStatement stm = Database.connection.prepareStatement(query);
                stm.setObject(1, idValue);
                stm.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item deleted.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to delete.");
        }
    }

    public void createSignOutButton() {
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setBounds(850, 10, 100, 30);
        frame.getContentPane().add(signOutButton);
        signOutButton.addActionListener(e -> {
            frame.dispose();
            new SignIn();
        });
    }

    public void createBackToDashboardButton() {
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(10, 520, 180, 30);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();
            new EmployeeDashboard();
        });
    }
}