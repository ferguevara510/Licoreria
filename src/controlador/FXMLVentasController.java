package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Producto;
import modelo.Venta;
import modelo.util.BusquedaProducto;

public class FXMLVentasController implements Initializable{

    @FXML
    private TextField buscarProductoTf;
    @FXML
    private Button buscarProductoBtn;
    @FXML
    private Button cerrarBtn;
    @FXML
    private Button agregarProductoBtn;
    @FXML
    private TableView<Producto> table;
    @FXML
    private TableColumn<Producto, String> col_nombre;
    @FXML
    private TableColumn<Producto, Float> col_precio;
    @FXML
    private TableColumn<Producto, Integer> col_cantidad;
    @FXML
    private TableView<Producto> tableVenta;
    @FXML
    private TextField totalTf;
    @FXML
    private Button ventaProductoBtn;
    @FXML
    private Button eliminarProductoBtn;
    @FXML
    private TableColumn<Producto, String> col_nombreventa;
    @FXML
    private TableColumn<Producto, Float> col_precioventa;
    @FXML
    private TableColumn<Producto, Spinner> col_cantidadventa;
    @FXML
    private TableColumn<Producto, Float> col_subtotalventa;
    

    @FXML
    public void cancelar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/ConsultarProductos.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" ConsultarProductos ");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    @FXML
    public void consultar(MouseEvent event){
        ObservableList<Producto> list = null;
        this.table.getItems().clear();
        String textToSearch = buscarProductoTf.getText();
        Producto producto = new Producto();
        List<Producto> productos = producto.buscarProductos(textToSearch,BusquedaProducto.ProductosPorNombre);
        list = FXCollections.observableArrayList(productos);
        this.table.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Producto> list = null;
        col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        col_precio.setCellValueFactory(new PropertyValueFactory("precio"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.col_nombreventa.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.col_precioventa.setCellValueFactory(new PropertyValueFactory("precio"));
        this.col_cantidadventa.setCellValueFactory((TableColumn.CellDataFeatures<Producto, Spinner> row) -> {
            Producto producto = row.getValue();
            float subtotal = producto.getSubtotal();
            Spinner<Integer> cantidadVenta = new Spinner<>(1, producto.getCantidad(), producto.getCantidadVendida());
            cantidadVenta.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
                producto.setCantidadVendida(newValue);
                producto.setSubtotal(producto.getPrecio() * newValue);
            });
            cantidadVenta.setOnMouseClicked((MouseEvent event) -> {
                tableVenta.refresh();
                ObservableList<Producto> productos = tableVenta.getItems();
                float total = 0;
                for(Producto productoVendido: productos){
                    total += productoVendido.getSubtotal();
                }
                
                totalTf.setText(""+total);
            }
            );
            return new SimpleObjectProperty<>(cantidadVenta);
        });
        
        this.col_subtotalventa.setCellValueFactory(new PropertyValueFactory("subtotal"));
        Producto producto = new Producto();
        List<Producto> productos = producto.buscarProductos("",BusquedaProducto.TodosLosProductosSinBorrar);
        list = FXCollections.observableArrayList(productos);
        this.table.setItems(list);
        this.tableVenta.setEditable(true);
    }

    @FXML
    private void agregarVenta(MouseEvent event) {
        Producto rowSelected = this.table.getSelectionModel().getSelectedItem();
        
        if(rowSelected != null){
            if(!this.tableVenta.getItems().contains(rowSelected) && rowSelected.getCantidad() > 0){
                float total = Float.parseFloat(totalTf.getText());
                this.tableVenta.getItems().add(rowSelected);
                
                total += rowSelected.getPrecio();
                
                this.totalTf.setText(""+total);
                
            }else{
                Alert aler = new Alert(Alert.AlertType.INFORMATION, "Ya se encuentra en la lista", ButtonType.OK);
                aler.showAndWait();
            }
        }else{
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "Selecciona un producto", ButtonType.OK);
            aler.showAndWait();
        }
    }

    @FXML
    private void eliminarDeVenta(MouseEvent event) {
        Producto rowSelected = this.tableVenta.getSelectionModel().getSelectedItem();
        
        if(rowSelected != null){
            float total = Float.parseFloat(totalTf.getText());
            total -= rowSelected.getSubtotal();
            rowSelected.setCantidadVendida(1);
            rowSelected.setSubtotal(rowSelected.getPrecio());
            
            totalTf.setText(""+total);
            this.tableVenta.getItems().remove(rowSelected);
        }else{
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "Selecciona un producto a eliminar", ButtonType.OK);
            aler.showAndWait();
        }
    }

    @FXML
    private void guardarVenta(MouseEvent event) {
        if(!this.tableVenta.getItems().isEmpty()){
            Venta venta = new Venta();
            
            venta.setFechaVenta(new Date());
            venta.setTotal(Float.parseFloat(totalTf.getText()));
            
            List<Producto> productos = new ArrayList<>();
            
            for(Producto producto: tableVenta.getItems()){
                productos.add(producto);
            }
            
            venta.setProductosVendidos(productos);
            
            if(venta.guardarVenta(venta)){
                Alert aler = new Alert(Alert.AlertType.INFORMATION, "Venta realizada", ButtonType.OK);
                aler.showAndWait();
                tableVenta.getItems().clear();
                table.refresh();
                totalTf.setText("0");
            }else{
                Alert aler = new Alert(Alert.AlertType.INFORMATION, "No se pudo realizar la venta", ButtonType.OK);
                aler.showAndWait();
            }
            
        }else{
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "No se agregó ningun producto", ButtonType.OK);
            aler.showAndWait();
        }
    }
    
}
