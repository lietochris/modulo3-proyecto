/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.Cliente;
import models.Producto;
import presenter.ProductoPresenter;
import repositories.ProductoRepository;
import utils.Database;
import utils.Page;
import utils.Result;
import utils.Router;

/**
 *
 * @author Christopher
 */
public class ProductoView extends javax.swing.JFrame implements Page {

    private Router router;
    private ProductoPresenter presenter;
      private int indiceActual = 0; 
    private List<Producto> productos;


    /**
     * Creates new form ProductoView
     */
    public ProductoView() {
        initComponents();
    }
     private void llenarVentana() {
        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        this.productos = this.presenter.FindAll();
        if (!productos.isEmpty()) {
            muestraRegistroActual(productos);
            btnPrimero.setEnabled(true);
            btnSiguiente.setEnabled(true);
            btnAnterior.setEnabled(true);
            btnUltimo.setEnabled(true);
            btnEditar.setEnabled(true);
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);
        }
    }
     private void muestraRegistroActual(List<Producto> productos) {
        if(!productos.isEmpty()){
            if (indiceActual >= 0 && indiceActual < productos.size()) {
                Producto productoActual = productos.get(indiceActual);
                txtIdProducto.setText(String.valueOf(productoActual.idProducto()));
                txtNombreProducto.setText(productoActual.nombre());
                int stock = productoActual.stock();
                double precio = productoActual.precio();
                // Convertir los números a String
                String stockText = String.valueOf(stock); 
                String precioText = String.valueOf(precio); 
                // Mostrar los valores en los campos de texto
                txtStock.setText(stockText);
                txtPrecio.setText(precioText);
                txtDescripcion.setText(productoActual.descripcion());
                LocalDateTime fechaCreacion = productoActual.fechaCreacion();
                jdcFechaCreacion.setDate(Date.from(fechaCreacion.atZone(ZoneId.systemDefault()).toInstant()));

                btnEditar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }
        }else {
            nuevoRegistro();
            btnEliminar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
        }
    }
    private void primerRegistro(List<Producto> productos) {
        indiceActual = 0;
        muestraRegistroActual(productos);
    }
    
    private void anteriorRegistro(List<Producto> productos) {
        if (indiceActual > 0) {
            indiceActual--;
            muestraRegistroActual(productos);
        }
    }
    
    private void ultimoRegistro(List<Producto> productos) {
        indiceActual = productos.size() - 1;
        muestraRegistroActual(productos);
    }
    
    private void siguienteRegistro(List<Producto> productos) {
        if (indiceActual < productos.size() - 1) {
            indiceActual++;
            muestraRegistroActual(productos);
        }
    }
        private Producto obtenerDatosProducto() {
        
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        int idProducto = Integer.parseInt(txtIdProducto.getText());
        String nombreProducto = txtNombreProducto.getText();
        int stock =  Integer.parseInt(txtStock.getText());
        double precio = Double.parseDouble(txtPrecio.getText());
        String descripcion =txtDescripcion.getText();

        Instant instant = jdcFechaCreacion.getDate().toInstant();
        LocalDateTime fechaCreacion = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        jdcFechaCreacion.setDate(Date.from(fechaCreacion.atZone(ZoneId.systemDefault()).toInstant()));


        // Crear y devolver un objeto Cliente con los datos obtenidos
        return new Producto(idProducto, nombreProducto, stock, precio, descripcion, fechaCreacion);
    }
        
    private boolean validarCampos(){
                boolean correcto = true;
        try {
            Integer.parseInt(txtIdProducto.getText()); //numeros Enteros
            //Float.parseFloat(txtCredito.getText());//nmeros decimales
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Se debe introducir un valor entero para el id del Producto",
                    "Error en el campo ID Producto",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        if (txtNombreProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se deben introducir el nombre del Producto.",
                    "Error en el campo nombres",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        try {
            Integer.parseInt(txtStock.getText()); //numeros Enteros
            //Float.parseFloat(txtCredito.getText());//nmeros decimales
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Se debe introducir un valor entero para el stock",
                    "Error en el campo ID stock",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        try {
            Double.parseDouble(txtPrecio.getText()); //numeros Enteros
            //Float.parseFloat(txtCredito.getText());//nmeros decimales
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Se debe introducir un valor doble para el Precio",
                    "Error en el campo Precio",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        if (txtDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se deben introducir descripcion del Producto.",
                    "Error en el campo descripcion",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        
        try {
            LocalDateTime fechaCreacion = LocalDateTime.ofInstant(jdcFechaCreacion.getDate().toInstant(), ZoneId.systemDefault());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error, la fecha de creación no es válida",
                    "Error en el campo Fecha Creación",
                    JOptionPane.ERROR_MESSAGE);

            correcto = false;
            return correcto;
        }

        return correcto;
    }
    
    private void nuevoRegistro() {
        activarCampos();
        limpiarRegistros();

    }
    
    private void activarCampos() {
        txtIdProducto.setEditable(true);
        txtNombreProducto.setEditable(true);
        txtStock.setEditable(true);
        txtPrecio.setEditable(true);
        txtDescripcion.setEditable(true);
        jdcFechaCreacion.setVisible(true); 
    }

    private void desactivarCampos() {
        txtIdProducto.setEditable(false);
        txtNombreProducto.setEditable(false);
        txtStock.setEditable(false);
        txtPrecio.setEditable(false);
        txtDescripcion.setEditable(false);
        jdcFechaCreacion.setVisible(false);
    }
    
    private void limpiarRegistros() {
        txtIdProducto.setText("");
        txtNombreProducto.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
        txtDescripcion.setText("");
        jdcFechaCreacion.setCalendar(null);
        jdcFechaCreacion.setEnabled(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtIdProducto = new javax.swing.JTextField();
        txtNombreProducto = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        jdcFechaCreacion = new com.toedter.calendar.JDateChooser();
        btnPrimero = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Producto");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 14, 165, 40));

        jLabel2.setText("idProducto");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 69, -1, -1));

        jLabel3.setText("Nombre");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 105, -1, -1));

        jLabel4.setText("Stock");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 148, -1, -1));

        jLabel5.setText("Precio");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 188, -1, -1));

        jLabel6.setText("Descripcion");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 222, -1, -1));

        jLabel7.setText("FechaCreacion");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 277, -1, -1));

        txtIdProducto.setText("                                       ");
        txtIdProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductoActionPerformed(evt);
            }
        });
        jPanel1.add(txtIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 66, -1, -1));

        txtNombreProducto.setText("                               ");
        jPanel1.add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 102, 132, -1));
        jPanel1.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 145, 132, -1));
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 185, 132, -1));
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 219, 132, -1));
        jPanel1.add(jdcFechaCreacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 277, 132, -1));

        btnPrimero.setText("Primero");
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 392, -1, -1));

        btnAnterior.setText("Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 392, -1, -1));

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 392, -1, -1));

        btnUltimo.setText("Ultimo");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        jPanel1.add(btnUltimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 392, -1, -1));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 66, 83, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(489, 66, 83, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(367, 116, 84, -1));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 116, 82, -1));

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 165, 82, -1));

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 219, -1, -1));

        btnReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReporte.setText("Imprimir Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        jPanel1.add(btnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 248, 173, -1));

        btnMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMenu.setText("Regresar al Menu");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 305, 173, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductoActionPerformed

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        // TODO add your handling code here:
        primerRegistro(productos);
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        anteriorRegistro(productos);
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
        siguienteRegistro(productos);
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        ultimoRegistro(productos);
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoRegistro();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

         if (validarCampos()) {
            Producto nuevoProducto = obtenerDatosProducto();
        
                    // Llamar al método CreateClient del presentador
            Result<String> resultado = presenter.CreateProduct(nuevoProducto);

            // Manejar el resultado devuelto por el método CreateClient
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Producto creado correctamente");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pueden guardar los datos del producto.",
                    "Error al guardar el roducto",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro del producto?",
                "Confirme su respuesta",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            int idProducto = Integer.parseInt(txtIdProducto.getText()); 
            Result<String> resultado = presenter.DeleteProduct(idProducto);

            // Manejar el resultado devuelto por el método CreateClient
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del producto");
        }
        desactivarCampos();
        limpiarRegistros();
        //llenarVentana("cliente_net");
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desactivarCampos();
        //muestraRegistroActual();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        activarCampos();
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarCampos()) {
            Producto productoActualizado = obtenerDatosProducto();
            int idProducto = productoActualizado.idProducto();
            // Llamar al método CreateClient del presentador
            Result<Producto> resultado = presenter.UpdateProduct(idProducto,productoActualizado);

            // Manejar el resultado devuelto por el método CreateClient
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
            }

        } else {
            JOptionPane.showMessageDialog(this,
                "No se pueden actualizar los datos del producto.",
                "Error al actualizar el producto",
                JOptionPane.ERROR_MESSAGE);
        }

        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        try {
            // TODO add your handling code here:
            this.presenter.CreateReport();
        } catch (Exception ex) {
            Logger.getLogger(ProductoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        this.router.moveToHomeView();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        this.llenarVentana();
    }//GEN-LAST:event_formComponentShown

    /**
     * @param args the command line arguments
     */


    @Override
    public String pageName() {
        return "ProductoView";
    }

    @Override
    public void setRouter(Router router) {
        this.router = router;
    }

    public void setPresenter(ProductoPresenter presenter) {
        this.presenter = presenter;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jdcFechaCreacion;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
