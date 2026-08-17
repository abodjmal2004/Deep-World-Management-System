package Admin;

import deep_word.DB_deepword;
import javafx.beans.property.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Product {

    private IntegerProperty id;
    private StringProperty productName;
    private StringProperty typeProduct;
    private DoubleProperty price;
    private StringProperty imagePath;
    private StringProperty typeUser;

    // Constructor without ID (for add)
    public Product(String productName, String typeProduct, double price, String imagePath, String typeUser) {
        this.productName = new SimpleStringProperty(productName);
        this.typeProduct = new SimpleStringProperty(typeProduct);
        this.price = new SimpleDoubleProperty(price);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.typeUser = new SimpleStringProperty(typeUser);
    }

    // ✅ Constructor with ID (for fetch/update/delete)
    public Product(int id, String productName, String typeProduct, double price, String imagePath, String typeUser) {
        this.id = new SimpleIntegerProperty(id);
        this.productName = new SimpleStringProperty(productName);
        this.typeProduct = new SimpleStringProperty(typeProduct);
        this.price = new SimpleDoubleProperty(price);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.typeUser = new SimpleStringProperty(typeUser);
    }

    // Getter & Setter for id
    public Integer getId() {
        return id.get();
    }

    public void setId(int id) {
        if (this.id == null) {
            this.id = new SimpleIntegerProperty(id);
        } else {
            this.id.set(id);
        }
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getters and setters for other fields
    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getTypeProduct() {
        return typeProduct.get();
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct.set(typeProduct);
    }

    public StringProperty typeProductProperty() {
        return typeProduct;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public String getTypeUser() {
        return typeUser.get();
    }

    public void setTypeUser(String typeUser) {
        this.typeUser.set(typeUser);
    }

    public StringProperty typeUserProperty() {
        return typeUser;
    }

    // ✅ Add product to database
    public boolean addProductToDatabase(String tableName) {
        boolean success = false;
        String sql = "";

        if (tableName.equals("pdf_books")) {
            sql = "INSERT INTO pdf_books (product_name, type_product, price, image_path, type_users) VALUES (?, ?, ?, ?, ?)";
        } else if (tableName.equals("corses")) {
            sql = "INSERT INTO corses (product_name, type_product, price, image_path, type_users) VALUES (?, ?, ?, ?, ?)";
        }

        try (Connection conn = DB_deepword.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, getProductName());
            stmt.setString(2, getTypeProduct());
            stmt.setDouble(3, getPrice());
            stmt.setString(4, getImagePath());
            stmt.setString(5, getTypeUser());
            stmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    // ✅ Delete product from database
    public boolean deleteProductFromDatabase() {
        boolean success = false;
        String sql = typeProduct.get().equals("Course")
            ? "DELETE FROM corses WHERE id = ?"
            : "DELETE FROM pdf_books WHERE id = ?";

        try (Connection conn = DB_deepword.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, getId());
            stmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    // ✅ Update product in database
    public boolean updateProductInDatabase() {
        boolean success = false;
        String sql = typeProduct.get().equals("Course")
            ? "UPDATE corses SET product_name = ?, type_product = ?, price = ?, image_path = ?, type_users = ? WHERE id = ?"
            : "UPDATE pdf_books SET product_name = ?, type_product = ?, price = ?, image_path = ?, type_users = ? WHERE id = ?";

        try (Connection conn = DB_deepword.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, getProductName());
            stmt.setString(2, getTypeProduct());
            stmt.setDouble(3, getPrice());
            stmt.setString(4, getImagePath());
            stmt.setString(5, getTypeUser());
            stmt.setInt(6, getId());
            stmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    // ✅ Fetch all products from specified table
    public static List<Product> getAllProducts(String tableName) {
        List<Product> products = new ArrayList<>();
        String sql = "";

        if (tableName.equals("pdf_books")) {
            sql = "SELECT * FROM pdf_books";
        } else if (tableName.equals("corses")) {
            sql = "SELECT * FROM corses";
        }

        try (Connection conn = DB_deepword.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("product_name"),
                    rs.getString("type_product"),
                    rs.getDouble("price"),
                    rs.getString("image_path"),
                    rs.getString("type_users")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
