import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Checkout extends JFrame {

    private JTable cartTable;
    private DefaultTableModel tableModel;
    private List<CartItem> cartItems;

    public Checkout() {
        cartItems = loadSampleCart();  // Hardcoded demo cart
        setupUI();
    }

    private void setupUI() {
        setTitle("Shopping Cart");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Shopping Cart", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Item Name", "Size", "Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(tableModel);
        refreshTable();
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Item");
        JButton increaseButton = new JButton("Increase Quantity");
        JButton decreaseButton = new JButton("Decrease Quantity");
        JButton orderButton = new JButton("Place Order");

        deleteButton.addActionListener(e -> deleteSelectedItem());
        increaseButton.addActionListener(e -> adjustQuantity(1));
        decreaseButton.addActionListener(e -> adjustQuantity(-1));
        orderButton.addActionListener(e -> placeOrder());

        buttonPanel.add(deleteButton);
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);
        buttonPanel.add(orderButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private List<CartItem> loadSampleCart() {
        List<CartItem> items = new ArrayList<>();
        CartItem item1 = new CartItem(1, "T-Shirt", "M", 19.99, 10);
        item1.setQuantity(2);
        items.add(item1);

        CartItem item2 = new CartItem(2, "Jeans", "32", 39.99, 5);
        item2.setQuantity(1);
        items.add(item2);

        return items;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (CartItem item : cartItems) {
            double total = item.getPrice() * item.getQuantity();
            tableModel.addRow(new Object[]{
                item.getName(),
                item.getSize(),
                "$" + item.getPrice(),
                item.getQuantity(),
                "$" + String.format("%.2f", total)
            });
        }
    }

    private void deleteSelectedItem() {
        int row = cartTable.getSelectedRow();
        if (row >= 0) {
            cartItems.remove(row);
            refreshTable();
        }
    }

    private void adjustQuantity(int delta) {
        int row = cartTable.getSelectedRow();
        if (row >= 0) {
            CartItem item = cartItems.get(row);
            int newQty = item.getQuantity() + delta;
            if (newQty > 0 && newQty <= item.getInStock()) {
                item.setQuantity(newQty);
                refreshTable();
            }
        }
    }

    private void placeOrder() {
        try {
            int userId = GlobalSession.getUserId();  // Simulated session method
            String address = fetchUserAddress(userId);
            if (address == null) {
                JOptionPane.showMessageDialog(this, "No shipping address found.");
                return;
            }

            Connection conn = Database.connection;
            conn.setAutoCommit(false);

            double totalPrice = cartItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();

            // Insert into orders
            String orderSql = "INSERT INTO orders (user_id, total_price, order_status, shipping_address) VALUES (?, ?, 'Placed', ?)";
            PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setString(2, String.format("%.2f", totalPrice));
            orderStmt.setString(3, address);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            if (!rs.next()) {
                conn.rollback();
                throw new SQLException("Failed to get order ID.");
            }
            int orderId = rs.getInt(1);

            // Insert into items_in_order
            String itemSql = "INSERT INTO items_in_order (order_id, item_id, item_quantity, total_price, order_status, shipping_address) VALUES (?, ?, ?, ?, 'Placed', ?)";
            PreparedStatement itemStmt = conn.prepareStatement(itemSql);
            for (CartItem item : cartItems) {
                double itemTotal = item.getPrice() * item.getQuantity();
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getItemId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setString(4, String.format("%.2f", itemTotal));
                itemStmt.setString(5, address);
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();
            conn.commit();

            JOptionPane.showMessageDialog(this, "Order placed successfully!");
            cartItems.clear();
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Order placement failed.");
        }
    }

    private String fetchUserAddress(int userId) {
        try {
            String sql = "SELECT user_address FROM users WHERE user_id = ?";
            PreparedStatement stmt = Database.connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("user_address");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
   

}
