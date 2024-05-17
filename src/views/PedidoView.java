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
import models.Pedido;
import presenter.PedidoPresenter;
import repositories.PedidoRepository;
import utils.Database;
import utils.Page;
import utils.Result;
import utils.Router;




/**
 *
 * @author Christopher
 */
public class PedidoView extends javax.swing.JFrame implements Page {

    private Router router;
    private PedidoPresenter presenter;
    private int indiceActual = 0; 
    private List<Pedido> pedidos;

    /**
     * Creates new form PedidoView
     */
    public PedidoView() {
        initComponents();
    }
        
        private void llenarVentana() {
        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        this.pedidos = this.presenter.FindAll();
        if (!pedidos.isEmpty()) {
            muestraRegistroActual(pedidos);
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
        
            private void muestraRegistroActual(List<Pedido> pedidos) {
        if(!pedidos.isEmpty()){
            if (indiceActual >= 0 && indiceActual < pedidos.size()) {
                Pedido pedidoActual = pedidos.get(indiceActual);
                txtidPedido.setText(String.valueOf(pedidoActual.idPedido()));
                txtCantidad.setText(String.valueOf(pedidoActual.cantidad()));
                LocalDateTime fechaCreacion = pedidoActual.fechaCreacion();
                jdcFechaCreacion.setDate(Date.from(fechaCreacion.atZone(ZoneId.systemDefault()).toInstant()));
                
                            // Obtener fecha de creación del pedido

            // Convertir LocalDateTime a Date
            Date fechaCreacionDate = Date.from(fechaCreacion.atZone(ZoneId.systemDefault()).toInstant());


            txtObservaciones.setText(pedidoActual.observaciones());
                
                txtObservaciones.setText(pedidoActual.observaciones());
                
                ////se agrega valor double
                double totalPago = pedidoActual.totalPago();
                txtTotalPago.setText(String.valueOf(totalPago));

//Pendientes cmbEntregado y 
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
    
    private void primerRegistro(List<Pedido> pedidos) {
        indiceActual = 0;
        muestraRegistroActual(pedidos);
    }
    
    private void anteriorRegistro(List<Pedido> pedidos) {
        if (indiceActual > 0) {
            indiceActual--;
            muestraRegistroActual(pedidos);
        }
    }
    
    private void ultimoRegistro(List<Pedido> pedidos) {
        indiceActual = pedidos.size() - 1;
        muestraRegistroActual(pedidos);
    }
    
    private void siguienteRegistro(List<Pedido> pedidos) {
        if (indiceActual < pedidos.size() - 1) {
            indiceActual++;
            muestraRegistroActual(pedidos);
        }
    }
    

    
private Pedido obtenerDatosPedido() {
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    int idPedido = Integer.parseInt(txtidPedido.getText());
    int cantidad = Integer.parseInt(txtCantidad.getText());
    
    Instant instant = jdcFechaCreacion.getDate().toInstant();
    LocalDateTime fechaCreacion = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    jdcFechaCreacion.setDate(Date.from(fechaCreacion.atZone(ZoneId.systemDefault()).toInstant()));
    
    String observaciones = txtObservaciones.getText();
    boolean entregado = Boolean.parseBoolean(cmbEntregado.getSelectedItem().toString()); // Tipo de dato booleano (1 es si y 0 No)
    double totalPago = Double.parseDouble(txtTotalPago.getText()); // Cantidad con decimales

    // Crear y devolver un objeto Pedido con los datos obtenidos
    return new Pedido(idPedido, cantidad, observaciones, entregado, totalPago, fechaCreacion);
}

 
private boolean validarCampos() {
    boolean correcto = true;
    
    try {
        Integer.parseInt(txtidPedido.getText()); // Números Enteros
    } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(this,
                "Se debe introducir un valor entero para el id del Pedido",
                "Error en el campo ID Pedido",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
        return correcto;
    }

    if (txtCantidad.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Se deben introducir la cantidad.",
                "Error en el campo Cantidad",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
        return correcto;
    }

    try {
        Date fecha = new Date(jdcFechaCreacion.getDate().getTime());
    } catch (Exception dte) {
        JOptionPane.showMessageDialog(this,
                "Error, la fecha no es valida",
                "Error en el campo Fecha Registro",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
        return correcto;
    }

    if (txtObservaciones.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Se deben introducir Observaciones de Pedido.",
                "Error en el campo Observaciones",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
        return correcto;
    }
  String entregadoText = cmbEntregado.getSelectedItem().toString();
 if (entregadoText.isEmpty()) {
    JOptionPane.showMessageDialog(this,
            "Se debe seleccionar el valor Entregado: 1 (SI), 0 (NO).",
            "Error en el campo Entregado",
            JOptionPane.ERROR_MESSAGE);
    correcto = false;
    return correcto;
}

    if (txtTotalPago.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Se deben introducir el Total de Pago.",
                "Error en el campo Total de Pago",
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
        txtidPedido.setEditable(true);
        txtCantidad.setEditable(true);
        txtFechaCreacion.setVisible(false);
        jdcFechaCreacion.setVisible(true);
        jdcFechaCreacion.setVisible(true); 
        txtObservaciones.setEditable(true);
        cmbEntregado.setEditable(true);
        txtTotalPago.setEditable(true);
    }

    private void desactivarCampos() {
        txtidPedido.setEditable(false);
        txtCantidad.setEditable(false);
        txtFechaCreacion.setVisible(true);
        jdcFechaCreacion.setVisible(false);
        jdcFechaCreacion.setVisible(false); 
        txtObservaciones.setEditable(false);
        cmbEntregado.setEditable(false);
        txtTotalPago.setEditable(false);
    }
    
    private void limpiarRegistros() {
        txtidPedido.setText("");
        txtCantidad.setText("");
        jdcFechaCreacion.setCalendar(null);
        jdcFechaCreacion.setEnabled(true);
        txtObservaciones.setText("");      
        cmbEntregado.setSelectedIndex(0);
        txtTotalPago.setText("");    

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
        lblPedido = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        lblObservaciones = new javax.swing.JLabel();
        lblEntregado = new javax.swing.JLabel();
        btnPrimero = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        txtidPedido = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtFechaCreacion = new javax.swing.JTextField();
        txtObservaciones = new javax.swing.JTextField();
        btnRegresarMenu = new javax.swing.JButton();
        lblTotalPago = new javax.swing.JLabel();
        txtTotalPago = new javax.swing.JTextField();
        cmbEntregado = new javax.swing.JComboBox<>();
        jdcFechaCreacion = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Entidad Pedido");

        lblPedido.setText("idPedido");

        lblCantidad.setText("Cantidad");

        lblFechaCreacion.setText("FechaCreacion");

        lblObservaciones.setText("Observaciones");

        lblEntregado.setText("Entregado");

        btnPrimero.setText("Primero");
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });

        btnAnterior.setText("Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnUltimo.setText("Ultimo");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReporte.setText("Imprimir Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearReporte(evt);
            }
        });

        btnRegresarMenu.setText("Regresar al Menu");
        btnRegresarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarMenuActionPerformed(evt);
            }
        });

        lblTotalPago.setText("TotalPago");

        cmbEntregado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPedido)
                    .addComponent(lblCantidad)
                    .addComponent(lblFechaCreacion)
                    .addComponent(lblObservaciones)
                    .addComponent(lblEntregado)
                    .addComponent(lblTotalPago))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCantidad)
                    .addComponent(txtidPedido)
                    .addComponent(txtFechaCreacion)
                    .addComponent(txtObservaciones)
                    .addComponent(txtTotalPago)
                    .addComponent(cmbEntregado, 0, 124, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdcFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRegresarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnReporte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addGap(37, 37, 37))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(btnPrimero)
                        .addGap(34, 34, 34)
                        .addComponent(btnAnterior)
                        .addGap(29, 29, 29)
                        .addComponent(btnSiguiente)
                        .addGap(31, 31, 31)
                        .addComponent(btnUltimo)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPedido)
                            .addComponent(txtidPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCantidad)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblFechaCreacion)
                                .addComponent(txtFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jdcFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblObservaciones))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEntregado)
                            .addComponent(cmbEntregado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotalPago)
                            .addComponent(txtTotalPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo)
                            .addComponent(btnGuardar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminar)
                            .addComponent(btnCancelar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReporte)
                        .addGap(18, 18, 18)
                        .addComponent(btnRegresarMenu)
                        .addGap(96, 96, 96)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrimero)
                    .addComponent(btnAnterior)
                    .addComponent(btnSiguiente)
                    .addComponent(btnUltimo))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarMenuActionPerformed
        // TODO add your handling code here:
        this.router.moveToHomeView();
    }//GEN-LAST:event_btnRegresarMenuActionPerformed
                                     

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
                int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro del Pedido?",
                "Confirme su respuesta",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            int idPedido = Integer.parseInt(txtidPedido.getText()); 
            Result<String> resultado = presenter.DeletePedido(idPedido);

            // Manejar el resultado devuelto por el método CreatePedido
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del Pedido");
        }
        desactivarCampos();
        limpiarRegistros();
        //llenarVentana("pedido");
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
        // TODO add your handling code here:
        activarCampos();
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
                if (validarCampos()) {
            Pedido pedidoActualizado = obtenerDatosPedido();
            int idPedido = pedidoActualizado.idPedido();
            // Llamar al método CreatePedido del presentador
            Result<Pedido> resultado = presenter.UpdatePedido(idPedido, pedidoActualizado);

            // Manejar el resultado devuelto por el método CreatePedido
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Pedido actualizado correctamente");
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pueden actualizar los datos del Pedido.",
                    "Error al actualizar el Pedido",
                    JOptionPane.ERROR_MESSAGE);
        }

        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnActualizarActionPerformed

    
    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        // TODO add your handling code here:
                primerRegistro(pedidos);
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
                anteriorRegistro(pedidos);
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
                siguienteRegistro(pedidos);
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
                ultimoRegistro(pedidos);
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void CrearReporte(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearReporte
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            this.presenter.CreateReport();
        } catch (Exception ex) {
            Logger.getLogger(PedidoView.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_CrearReporte

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        nuevoRegistro();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        this.llenarVentana();
    }//GEN-LAST:event_formComponentShown

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    if (validarCampos()) {
            Pedido nuevoPedido = obtenerDatosPedido();
        
                    // Llamar al método CreateClient del presentador
            Result<String> resultado = presenter.CreatePedido(nuevoPedido);

            // Manejar el resultado devuelto por el método CreateClient
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Pedido creado correctamente");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pueden guardar el pedido.",
                    "Error al guardar el pedido",
                    JOptionPane.ERROR_MESSAGE);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */

    @Override
    public String pageName() {
        return "PedidoView";
    }

    @Override
    public void setRouter(Router router) {
        this.router = router;
    }

    public void setPresenter(PedidoPresenter presenter) {
        this.presenter = presenter;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnRegresarMenu;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cmbEntregado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jdcFechaCreacion;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblEntregado;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblPedido;
    private javax.swing.JLabel lblTotalPago;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtFechaCreacion;
    private javax.swing.JTextField txtObservaciones;
    private javax.swing.JTextField txtTotalPago;
    private javax.swing.JTextField txtidPedido;
    // End of variables declaration//GEN-END:variables
}
