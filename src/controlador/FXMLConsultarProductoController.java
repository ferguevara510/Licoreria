package controlador;

import modelo.Producto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;//
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;//
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.util.BusquedaProducto;

public class FXMLConsultarProductoController implements Initializable{
    
    public static Producto aux= new Producto();
    @FXML
    private TextField buscarProductoTf;
    @FXML
    private TableView<Producto> table;
    @FXML
    private TableColumn<Producto, String> col_nombre;
    @FXML
    private TableColumn<Producto, String> col_marca;
    @FXML
    private TableColumn<Producto, Float> col_precio;
    @FXML
    private TableColumn<Producto, Integer> col_cantidad;
    
    
    @FXML
    public void agregar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/DatosProducto.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" DatosProducto ");
        stage.setScene(new Scene(root, 600, 375));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    @FXML
    public void editar(MouseEvent event) throws IOException{
        try{
            Producto sele= table.getSelectionModel().getSelectedItem();
            aux.setNombre(sele.getNombre());
            aux.setMarca(sele.getMarca());
            aux.setCantidad(sele.getCantidad());
            aux.setPrecio(sele.getPrecio());
            aux.setIdProveedor(sele.getIdProveedor());
            Parent root; 
            root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/EditarProducto.fxml"));
            Stage stage = new Stage();
            stage.setTitle(" DatosProducto ");
            System.out.println(table.getSelectionModel().getSelectedItem().getNombre());
            stage.setScene(new Scene(root, 600, 375));
            stage.show();
            stage.setResizable(false);
            stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch(Exception e){
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "Selecciona un elemento a modificar", ButtonType.OK);
            aler.showAndWait();
        }
    }
    
    @FXML
    public void regresar(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/Principal.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" Principal ");
        stage.setScene(new Scene(root, 650, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    @FXML
    public void ventas(MouseEvent event) throws IOException{
        Parent root; 
        root = FXMLLoader.load(getClass().getClassLoader().getResource("vista/Ventas.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" Ventas ");
        stage.setScene(new Scene(root, 1100, 600));
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/vista/imagenes/vintage.png"));
        ((Node)(event.getSource())).getScene().getWindow().hide(); 
    }
    
    @FXML
    public void consultar(MouseEvent event){
        this.table.getItems().clear();
        String textToSearch = buscarProductoTf.getText();
        ObservableList<Producto> list = null;
        Producto producto = new Producto();
        List<Producto> productos = producto.buscarProductos(textToSearch,BusquedaProducto.ProductosPorNombre);
        list = FXCollections.observableArrayList(productos);

        col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        col_marca.setCellValueFactory(new PropertyValueFactory("marca"));
        col_precio.setCellValueFactory(new PropertyValueFactory("precio"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        table.setItems(list);
    }
    
    @FXML
    public void eliminar(MouseEvent event){
        Producto rowSelected = table.getSelectionModel().getSelectedItem();
        if(rowSelected != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de que lo quiere eliminar?", ButtonType.YES, ButtonType.NO);
            ButtonType resul = alert.showAndWait().orElse(ButtonType.YES);
            if(ButtonType.YES.equals(resul)) {
                String nombre = table.getSelectionModel().getSelectedItem().getNombre();
                Producto producto = new Producto();
                if(producto.borrarProducto(nombre)){
                    this.table.getItems().remove(rowSelected);
                    Alert aler2 = new Alert(Alert.AlertType.INFORMATION, "¡Eliminación exitosa!", ButtonType.OK);
                    aler2.showAndWait();
                }else{
                    Alert aler2 = new Alert(Alert.AlertType.INFORMATION, "No se pudo eliminar", ButtonType.OK);
                    aler2.showAndWait();
                }
                
            }
        }else{
            Alert aler = new Alert(Alert.AlertType.INFORMATION, "Selecciona un elemento a eliminar", ButtonType.OK);
            aler.showAndWait();
        }
        
    }
    
    @FXML
    private Button registroProductoBtn;
    @FXML
    private Button editarProductoBtn;
    @FXML
    private Button eliminarProductoBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Producto> list = null;
        Producto producto = new Producto();
        List<Producto> productos = producto.buscarProductos("",BusquedaProducto.TodosLosProductosSinBorrar);
        list = FXCollections.observableArrayList(productos);
        
        col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        col_marca.setCellValueFactory(new PropertyValueFactory("marca"));
        col_precio.setCellValueFactory(new PropertyValueFactory("precio"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        table.setItems(list);
        
        String tipo = System.getProperties().getProperty("Empleado");
        if(tipo.equals("Trabajador")){
            registroProductoBtn.setDisable(true);
            editarProductoBtn.setDisable(true);
            eliminarProductoBtn.setDisable(true);
        }
    }
    
}