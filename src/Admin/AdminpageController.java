package Admin;

import deep_word.DB_deepword;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class AdminpageController {

    @FXML private AnchorPane anchorpan_dashbord_users;
    @FXML private AnchorPane anchorpan_dashbord_product;
    @FXML private Button dashbord_btn, productmang_btn, primeomusers_btn, customers_btn;
    @FXML private TableColumn<Product, String> product_name_c1, type_product_c2, type_users_c4;
    @FXML private TableColumn<Product, Double> price_c3;
    @FXML private TextField product_name_tx, type_users_tx, price_tx;
    @FXML private ImageView image_path_imgvew;
    @FXML private Button import_image_path_btn, add_btn, delet_btn, update_btn, clear_btn;
    @FXML private RadioButton corses_Radio, pdf_books_Radio;
    @FXML private TableView<Product> productTable;
    @FXML private Label massge_Label;

    private ToggleGroup typeGroup = new ToggleGroup();
    private String currentTable = "pdf_books";

    // Dashboard Labels
    @FXML private Label NumberofCustomers_lbl;
    @FXML private Label TodayIncome_lbl;
    @FXML private Label TotalIncome_lbl;
    @FXML private Label NumberofSoldProducts_lbl;

    // Charts
    @FXML private AreaChart<String, Number> areachart;  // مبيعات شهرية 
    @FXML private BarChart<String, Number> barchart;    // عدد المستخدمين 

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        corses_Radio.setToggleGroup(typeGroup);
        pdf_books_Radio.setToggleGroup(typeGroup);
        pdf_books_Radio.setSelected(true);

        anchorpan_dashbord_product.setVisible(true);
        anchorpan_dashbord_users.setVisible(false);

        loadProductData();
        productTable.setOnMouseClicked(event -> fillFormFromSelected());
        loadDashboardData();   
        loadChartsData();      
    }

    
    private void loadDashboardData() {
        try (Connection conn = DB_deepword.getConnection()) {
            Statement st = conn.createStatement();

            
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next()) NumberofCustomers_lbl.setText(String.valueOf(rs.getInt(1)));

            
            rs = st.executeQuery("SELECT SUM(total_amount) FROM orders WHERE DATE(order_date) = CURDATE()");
            if (rs.next()) TodayIncome_lbl.setText("$" + rs.getDouble(1));

            
            rs = st.executeQuery("SELECT SUM(total_amount) FROM orders");
            if (rs.next()) TotalIncome_lbl.setText("$" + rs.getDouble(1));

            
            rs = st.executeQuery("SELECT COUNT(*) FROM order_items");
            if (rs.next()) NumberofSoldProducts_lbl.setText(String.valueOf(rs.getInt(1)));

            
            loadChartsData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private void loadChartsData() {
        try {
            
            if (barchart != null) {
                barchart.getData().clear();
                barchart.getData().add(buildUsersTotalSeries());
            }
            
            if (areachart != null) {
                areachart.getData().clear();
                areachart.getData().add(buildFakeMonthlySalesSeries());
            }
        } catch (Exception ex) {
            ex.printStackTrace(); 
        }
    }

    /** يجلب العدد الإجمالي للمستخدمين من قاعدة البيانات. */
    private int getTotalUsers() {
        final String SQL = "SELECT COUNT(*) FROM users";
        try (Connection conn = DB_deepword.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("فشل جلب عدد المستخدمين.", e);
        }
    }

    
    private XYChart.Series<String, Number> buildUsersTotalSeries() {
        int total = getTotalUsers();
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.setName("عدد المستخدمين");
        s.getData().add(new XYChart.Data<>("Users", total));
        return s;
    }

    
    private XYChart.Series<String, Number> buildFakeMonthlySalesSeries() {
        int year = LocalDate.now().getYear();
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.setName("مبيعات " + year );

        String[] months = {"يناير","فبراير","مارس","أبريل","مايو","يونيو",
                           "يوليو","أغسطس","سبتمبر","أكتوبر","نوفمبر","ديسمبر"};
        Random r = new Random(year * 31L + 7); // seed ثابت لثبات النتائج
        for (int i = 0; i < 12; i++) {
            int value = 50 + r.nextInt(200); // 50..249
            s.getData().add(new XYChart.Data<>(months[i], value));
        }
        return s;
    }

    @FXML
    private void fillFormFromSelected() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            product_name_tx.setText(selected.getProductName());
            price_tx.setText(String.valueOf(selected.getPrice()));
            type_users_tx.setText(selected.getTypeUser());

            if ("Course".equalsIgnoreCase(selected.getTypeProduct())) {
                corses_Radio.setSelected(true);
            } else {
                pdf_books_Radio.setSelected(true);
            }

            try {
                image_path_imgvew.setImage(
                    (selected.getImagePath() != null && !selected.getImagePath().isEmpty())
                        ? new Image(selected.getImagePath())
                        : null
                );
            } catch (Exception e) {
                image_path_imgvew.setImage(null);
            }

            currentTable = selected.getTypeProduct().equals("Course") ? "corses" : "pdf_books";
        }
    }

    @FXML private void dashbord_btn(ActionEvent event) {
        anchorpan_dashbord_product.setVisible(false);
        anchorpan_dashbord_users.setVisible(true);
        loadDashboardData();  // يعيد تعبئة اللابلز + المخططات
    }

    @FXML private void productmang_btn(ActionEvent event) {
        anchorpan_dashbord_users.setVisible(false);
        anchorpan_dashbord_product.setVisible(true);
    }

    @FXML private void import_image_path_btn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            image_path_imgvew.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
        }
    }

    @FXML private void add_btn(ActionEvent event) {
        String productName = product_name_tx.getText();
        String typeUser = type_users_tx.getText();
        String typeProduct = corses_Radio.isSelected() ? "Course" : "PDF";
        String selectedTable = corses_Radio.isSelected() ? "corses" : "pdf_books";
        currentTable = selectedTable;

        double price;
        try {
            price = Double.parseDouble(price_tx.getText());
        } catch (NumberFormatException e) {
            showMessage("يرجى إدخال سعر صالح.", false);
            return;
        }

        String imagePath = image_path_imgvew.getImage() != null ? image_path_imgvew.getImage().getUrl() : "";

        Product product = new Product(productName, typeProduct, price, imagePath, typeUser);
        if (product.addProductToDatabase(selectedTable)) {
            showMessage("تم إضافة المنتج بنجاح!", true);
            clearFields();
            loadProductData();
        } else {
            showMessage("حدث خطأ أثناء إضافة المنتج.", false);
        }
    }

    @FXML private void delet_btn(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showMessage("يرجى اختيار منتج للحذف.", false);
            return;
        }
        if (selectedProduct.deleteProductFromDatabase()) {
            showMessage("تم حذف المنتج بنجاح!", true);
            clearFields();
            loadProductData();
        } else {
            showMessage("فشل حذف المنتج.", false);
        }
    }

    @FXML private void update_btn(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showMessage("يرجى اختيار منتج للتحديث.", false);
            return;
        }

        try {
            selectedProduct.setProductName(product_name_tx.getText());
            selectedProduct.setPrice(Double.parseDouble(price_tx.getText()));
            selectedProduct.setTypeProduct(corses_Radio.isSelected() ? "Course" : "PDF");
            selectedProduct.setTypeUser(type_users_tx.getText());
            selectedProduct.setImagePath(image_path_imgvew.getImage().getUrl());

            if (selectedProduct.updateProductInDatabase()) {
                showMessage("تم تحديث المنتج بنجاح!", true);
                clearFields();
                loadProductData();
            } else {
                showMessage("فشل تحديث المنتج.", false);
            }
        } catch (Exception e) {
            showMessage("تأكد من صحة البيانات المدخلة.", false);
        }
    }

    @FXML private void clear(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        product_name_tx.clear();
        price_tx.clear();
        type_users_tx.clear();
        corses_Radio.setSelected(false);
        pdf_books_Radio.setSelected(false);
        image_path_imgvew.setImage(null);
        productTable.getSelectionModel().clearSelection();
    }

    private void loadProductData() {
        ObservableList<Product> products = FXCollections.observableArrayList(Product.getAllProducts(currentTable));
        product_name_c1.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        type_product_c2.setCellValueFactory(cellData -> cellData.getValue().typeProductProperty());
        price_c3.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        type_users_c4.setCellValueFactory(cellData -> cellData.getValue().typeUserProperty());
        productTable.setItems(products);
    }

    private void showMessage(String message, boolean success) {
        massge_Label.setText(message);
        massge_Label.setStyle(success ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    @FXML private void primeomusers_btn(ActionEvent event) {
        System.out.println("تم الضغط على Primeom Users!");
    }

    @FXML private void customers_btn(ActionEvent event) {}

    @FXML private void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
