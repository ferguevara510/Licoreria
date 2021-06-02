package modelo;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;
import modelo.util.Conexion;

import java.sql.*;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

public class ReporteVenta {
    Connection conn = Conexion.getInstance();
    Document docu;
    PdfWriter escribir;
    String strRotuloPDF = "Reporte de ventas";
    String nombre;
    double suma = 0;
    String total;
    //private 
    Date fechaVenta = new Date();
    public void crear(){
        try{
            Calendar c1 = Calendar.getInstance();
            String dia = Integer.toString(c1.get(Calendar.DATE));
            String mes = Integer.toString(c1.get(Calendar.MONTH)+1);
            String annio = Integer.toString(c1.get(Calendar.YEAR));
            nombre = "Reporte de ventas"+dia+"-"+mes+"-"+annio+".pdf";
            docu = new Document(PageSize.LETTER.rotate());
            escribir = PdfWriter.getInstance(docu,new FileOutputStream(nombre));
            docu.open();
            agregarMetaDatos(docu);
            agregarContenido(docu);
            docu.close();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    private void agregarContenido(Document document) throws DocumentException{
        Paragraph ParrafoHoja = new Paragraph();
        agregarLineasBlancas(ParrafoHoja, 1);
        ParrafoHoja.add(new Paragraph(strRotuloPDF.toUpperCase()));
        agregarLineasBlancas(ParrafoHoja, 1);
        agregarTabla(ParrafoHoja);
        agregarLineasBlancas(ParrafoHoja, 2);
        total  = "El total de ganacias es: " + suma;
        ParrafoHoja.add(new Paragraph(total.toUpperCase()));
        docu.add(ParrafoHoja);
    }
    private void agregarTabla(Paragraph parrafo)throws BadElementException{
        Calendar c1 = Calendar.getInstance();
        String dia = Integer.toString(c1.get(Calendar.DATE));
        String mes = Integer.toString(c1.get(Calendar.MONTH));
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        String hora = Integer.toString(c1.get(Calendar.HOUR));
        DateFormat hourDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        float[] anchosFilas = {0.7f,2.1f,1.7f,0.7f,0.7f,0.7f };
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String[] rotulosColumnas = {"Folio","Fecha","Nombre del producto","Cantidad","Subtotal","Total"};
        tabla.setWidthPercentage(100);
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(new Paragraph("Tabla: Ventas"));
        cell.setColspan(9);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell);
        try{
            for(int i=0; i<rotulosColumnas.length; i++){
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i]));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
            }
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery("select * from venta;");
            Statement st1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            try {
                while(rs.next()){
                    cell = new PdfPCell(new Paragraph(String.valueOf(rs.getInt("id"))));
                    tabla.addCell(cell);
                    cell = new PdfPCell(new Paragraph(String.valueOf(rs.getDate("fecha"))));
                    tabla.addCell(cell);
                    ResultSet rs1=st1.executeQuery("select * from venta_producto;");
                    while(rs1.next()){
                        if(rs.getInt("id") == rs1.getInt("id_venta")){
                            cell = new PdfPCell(new Paragraph(String.valueOf(rs1.getString("id_producto"))));
                            tabla.addCell(cell);
                            cell = new PdfPCell(new Paragraph(String.valueOf(rs1.getInt("cantidad"))));
                            tabla.addCell(cell);
                            cell = new PdfPCell(new Paragraph(String.valueOf(rs1.getFloat("subtotal"))));
                            tabla.addCell(cell);
                            if(rs1.next()){
                                if(rs.getInt("id") == rs1.getInt("id_venta")){
                                    cell = new PdfPCell(new Paragraph(" "));
                                    tabla.addCell(cell);
                                    cell = new PdfPCell(new Paragraph(" "));
                                    tabla.addCell(cell);
                                    cell = new PdfPCell(new Paragraph(" "));
                                    tabla.addCell(cell);
                                    rs1.previous();
                                }
                            }
                        }
                    }
                    cell = new PdfPCell(new Paragraph(String.valueOf(rs.getFloat("total"))));
                    tabla.addCell(cell);
                    suma = suma + rs.getFloat("total");
                } 
            } catch (SQLException ex) {
                Logger.getLogger(ReporteVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        parrafo.add(tabla);
    }
    private static void agregarLineasBlancas(Paragraph parrafo, int nLineas){
        for(int i=0;i<nLineas;i++){
            parrafo.add(new Paragraph (" "));
        }
    }
    private static void agregarMetaDatos(Document docu){
        docu.addTitle("Reporte de ventas");
        docu.addSubject("Usando iText y MySql");
        docu.addKeywords("Java,PDF,iText");
        docu.addAuthor("");
        
    }
    public void abrirPDF(){
          try{
              Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+nombre);
          }catch(IOException e){
              
          }
    }
}
