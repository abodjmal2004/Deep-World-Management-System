package PDFLib;

import Admin.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class PdfliibController implements Initializable {

    @FXML private Button home_btn, Courses_btn, pdf_btn, about_btn, logout_btn;
    @FXML private Label mnew_total;
    @FXML private TextField mnew_amont;
    @FXML private Button mnew_pay, mnew_remove, mnew_receipt;
    @FXML private AnchorPane mnew_form;
    @FXML private VBox Prody_VBx;
    @FXML private ScrollPane ScrollPane_n;
    @FXML private TableColumn<Product, String> mnew_prodictnam;
    @FXML private TableColumn<Product, Double> mnew_pric;
    @FXML private TableColumn<Product, Integer> mnew_quntiy;
    @FXML private TableView<Product> taplvewprodct;

    private ObservableList<Product> cartItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProducts();

        // إعداد أعمدة جدول السلة
        mnew_prodictnam.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        mnew_pric.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        mnew_quntiy.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(1).asObject());

        taplvewprodct.setItems(cartItems);
        updateTotal();
    }

    private void loadProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(Product.getAllProducts("pdf_books"));
        Prody_VBx.getChildren().clear();

        VBox rowVBox = new VBox(15);
        rowVBox.setAlignment(Pos.TOP_CENTER);

        for (Product product : products) {
            HBox productBox = new HBox(20);
            productBox.setAlignment(Pos.CENTER_LEFT);
            productBox.setStyle("-fx-padding: 12; -fx-background-color: #ffffff; -fx-border-color: #dddddd; -fx-background-radius: 10; -fx-border-radius: 10;");
            productBox.setPrefHeight(140);

            ImageView imageView = new ImageView();
            try {
                Image image = new Image(product.getImagePath(), true);
                imageView.setImage(image);
            } catch (Exception e) {
                imageView.setImage(null);
            }
            imageView.setFitWidth(110);
            imageView.setFitHeight(110);

            VBox productInfo = new VBox(6);
            productInfo.setAlignment(Pos.CENTER_LEFT);
            Label nameLabel = new Label(product.getProductName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label priceLabel = new Label("السعر: $" + product.getPrice());
            priceLabel.setStyle("-fx-font-size: 14px;");

            Label typeLabel = new Label("النوع: " + product.getTypeUser());
            typeLabel.setStyle("-fx-font-size: 13px;");

            productInfo.getChildren().addAll(nameLabel, priceLabel, typeLabel);

            Button addButton = new Button("إضافة للسلة");
            addButton.setOnAction(e -> {
                cartItems.add(product);
                updateTotal();
            });

            HBox buttonBox = new HBox(addButton);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            buttonBox.setPrefWidth(150);

            productBox.getChildren().addAll(imageView, productInfo, buttonBox);
            rowVBox.getChildren().add(productBox);
        }

        Prody_VBx.getChildren().add(rowVBox);
        ScrollPane_n.setContent(Prody_VBx);
        ScrollPane_n.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private void updateTotal() {
        double total = 0.0;
        for (Product product : cartItems) {
            total += product.getPrice();
        }
        mnew_total.setText("$" + String.format("%.2f", total));
    }

    private void Logout_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void home_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void pdf_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PDFLib/pdfliib.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void Courses_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cousrses/coursespag.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void about_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/About/about.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML private void logout_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
} 