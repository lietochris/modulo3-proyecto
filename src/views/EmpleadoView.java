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
import models.Empleado;
import presenter.EmpleadoPresenter;
import repositories.EmpleadoRepository;
import utils.Database;
import utils.Page;
import utils.Result;
import utils.Router;

/**
 *
 * @author Christopher
 */
public class EmpleadoView extends javax.swing.JFrame implements Page {
    
    private Router router;
    private EmpleadoPresenter presenter;
    private int indiceActual = 0; 
    private List<Empleado> empleado;
    
    /**
     * Creates new form EmpleadoView
     */
    public EmpleadoView() {
        initComponents();

    }
    
    private void llenarVentana() {
        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        this.empleado = this.presenter.FindAll();
        if (!empleado.isEmpty()) {
            muestraRegistroActual(empleado);
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
    
    private void muestraRegistroActual(List<Empleado> empleado) {
        if(!empleado.isEmpty()){
            if (indiceActual >= 0 && indiceActual < empleado.size()) {
                Empleado empleadoActual = empleado.get(indiceActual);
                txtIdEmpleado.setText(String.valueOf(empleadoActual.idEmpleado()));
                txtNombreEmpleado.setText(empleadoActual.nombre());
                txtApellidoPaterno.setText(empleadoActual.apellidoPaterno());
                txtApellidoMaterno.setText(empleadoActual.apellidoMaterno());
                LocalDate FechaInicio = empleadoActual.fechaInicio();
                jdcFechaInicio.setDate(Date.from(FechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()));

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

    private void primerRegistro(List<Empleado> empleado) {
        indiceActual = 0;
        muestraRegistroActual(empleado);
    }
    
    private void anteriorRegistro(List<Empleado> empleado) {
        if (indiceActual > 0) {
            indiceActual--;
            muestraRegistroActual(empleado);
        }
    }
    
    private void ultimoRegistro(List<Empleado> empleado) {
        indiceActual = empleado.size() - 1;
        muestraRegistroActual(empleado);
    }
    
    private void siguienteRegistro(List<Empleado> empleado) {
        if (indiceActual < empleado.size() - 1) {
            indiceActual++;
            muestraRegistroActual(empleado);
        }
    }
    
     private Empleado obtenerDatosEmpleados() {
        
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    int idEmpleado = Integer.parseInt(txtIdEmpleado.getText()); 
    String Nombre = txtNombreEmpleado.getText(); 
    String ApellidoPaterno = txtApellidoPaterno.getText(); 
    String ApellidoMaterno = txtApellidoMaterno.getText(); 
    String Correo = txtCorreo.getText(); 
    
    Instant instantInicio = jdcFechaInicio.getDate().toInstant();
    LocalDate FechaInicio = instantInicio.atZone(ZoneId.systemDefault()).toLocalDate();
    jdcFechaInicio.setDate(Date.from(FechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()));

    // Crear y devolver un objeto Cliente con los datos obtenidos
    return new Empleado(idEmpleado, Nombre, ApellidoPaterno, ApellidoMaterno, Correo, FechaInicio);
}
    
    private boolean validarCampos(){
                boolean correcto = true;
        try {
            Integer.parseInt(txtIdEmpleado.getText()); //numeros Enteros
            //Float.parseFloat(txtCredito.getText());//nmeros decimales
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Se debe introducir un valor entero para el id del Cliente",
                    "Error en el campo ID Cliente",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        if (txtNombreEmpleado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se deben introducir el nombre del cliente.",
                    "Error en el campo nombres",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
 
        if (txtApellidoPaterno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se deben introducir el Apellido Paterno del cliente.",
                    "Error en el campo Apellido Paterno",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
        if (txtApellidoMaterno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se deben introducir el Apellido Materno del cliente.",
                    "Error en el campo Apellido Materno",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
 
       try {
 
            Date fecha = new Date(jdcFechaInicio.getDate().getTime());
        } catch (Exception dte) {
            JOptionPane.showMessageDialog(this,
                    "Error, la fecha no es valida",
                    "Error en el campo Fecha Registro",
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
        txtIdEmpleado.setEditable(true);
        txtNombreEmpleado.setEditable(true);
        txtApellidoPaterno.setEditable(true);
        txtApellidoMaterno.setEditable(true);
        txtCorreo.setEditable(true);
        jdcFechaInicio.setVisible(true);  
    }

    private void desactivarCampos() {
        txtIdEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoPaterno.setEditable(false);
        txtApellidoMaterno.setEditable(false);
        txtCorreo.setEditable(false);
        jdcFechaInicio.setVisible(false);
    }
    
    private void limpiarRegistros() {
        txtIdEmpleado.setText("");
        txtNombreEmpleado.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtCorreo.setText("");
        jdcFechaInicio.setCalendar(null);
        jdcFechaInicio.setEnabled(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbEmpleado = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblApellidoP = new javax.swing.JLabel();
        lblApellidoM = new javax.swing.JLabel();
        lblFechaCreacion = new javax.swing.JLabel();
        txtFechaInicio = new javax.swing.JLabel();
        txtIdEmpleado = new javax.swing.JTextField();
        txtNombreEmpleado = new javax.swing.JTextField();
        txtApellidoPaterno = new javax.swing.JTextField();
        txtApellidoMaterno = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnPrimero = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        jdcFechaInicio = new com.toedter.calendar.JDateChooser();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(143, 211, 196));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("ENTIDAD EMPLEADO");

        lbEmpleado.setText("idEmpleado:");

        lblNombreCliente.setText("NombreEmpleado:");

        lblApellidoP.setText("Apellido Paterno:");

        lblApellidoM.setText("Apellido Materno");

        lblFechaCreacion.setText("Correo:");

        txtFechaInicio.setText("FechaInicio:");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReporte.setText("Imprimir Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        btnMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMenu.setText("Regresar al Menu");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnPrimero.setText("Primero");
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnAnterior.setText("Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        btnUltimo.setText("Ultimo");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(txtIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnActualizar))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblApellidoM)
                                    .addComponent(lblFechaCreacion)
                                    .addComponent(lblApellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFechaInicio)
                                    .addComponent(lbEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(138, 138, 138)
                                        .addComponent(btnSiguiente))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                        .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(btnPrimero)
                                .addGap(18, 18, 18)
                                .addComponent(btnAnterior)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 61, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnReporte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnUltimo)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEmpleado)
                    .addComponent(txtIdEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreCliente)
                    .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellidoP)
                    .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar)
                    .addComponent(btnActualizar))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApellidoM)
                    .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaCreacion)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(txtFechaInicio))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btnReporte)
                        .addGap(18, 18, 18)
                        .addComponent(btnMenu)))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrimero)
                    .addComponent(btnAnterior)
                    .addComponent(btnSiguiente)
                    .addComponent(btnUltimo))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        lbEmpleado.getAccessibleContext().setAccessibleName("idEmpleado");
        lblNombreCliente.getAccessibleContext().setAccessibleName("NombreEmpleado");
        lblApellidoP.getAccessibleContext().setAccessibleName("ApellidoPaterno");
        lblApellidoM.getAccessibleContext().setAccessibleName("ApellidoMaterno");
        lblFechaCreacion.getAccessibleContext().setAccessibleName("correo");
        txtNombreEmpleado.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           

        if (validarCampos()) {
            Empleado nuevoEmpleado = obtenerDatosEmpleados();

            // Llamar al método CreateClient del presentador
            Result <String> resultado = presenter.CrearEmpleado(nuevoEmpleado);

            // Manejar el resultado devuelto por el método CreateClient
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Cliente creado correctamente");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pueden guardar los datos del cliente.",
                "Error al guardar el cliente",
                JOptionPane.ERROR_MESSAGE);
        }

    }                                          

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {                                           
      try {
            // TODO add your handling code here:
            this.presenter.CreateReport();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoView.class.getName()).log(Level.SEVERE, null, ex);
        }
            // TODO add your handling code here:
    }                                          

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        desactivarCampos();
        //muestraRegistroActual();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);        // TODO add your handling code here:
    }                                           

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        activarCampos();
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);        // TODO add your handling code here:
    }                                         

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {                                            
                int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro del cliente?",
                "Confirme su respuesta",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText()); 
            Result<String> resultado = presenter.DeleteEmpleoyee(idEmpleado);

            // Manejar el resultado devuelto por el método Create
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Empleado creado correctamente");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del Empleado");
        }
        desactivarCampos();
        limpiarRegistros();
        //llenarVentana("cliente_net");
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);        // TODO add your handling code here:
    }                                           

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {                                              
 if (validarCampos()) {
            Empleado empleadoActualizado = obtenerDatosEmpleados();
            int idEmpleado = empleadoActualizado.idEmpleado();
            // Llamar al método CreateClient del presentador
            Result<Empleado> resultado = presenter.UpdateEmployee(idEmpleado,empleadoActualizado);

            // Manejar el resultado devuelto por el método CreateClient
            if (resultado.isError()) {
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(this, "Error: " + resultado.error().message());
            } else {
                // Mostrar mensaje de éxito al usuario
                JOptionPane.showMessageDialog(this, "Cliente creado correctamente");
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pueden actualizar los datos del cliente.",
                    "Error al actualizar el cliente",
                    JOptionPane.ERROR_MESSAGE);
        }

        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
                                  // TODO add your handling code here:
    }                                             

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
         nuevoRegistro();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);  
    }                                        

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        this.router.moveToHomeView();
    }                                       

    private void formComponentShown(java.awt.event.ComponentEvent evt) {                                    
        // TODO add your handling code here:
        this.llenarVentana();
    }                                   

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        primerRegistro(empleado);
    }                                          

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        anteriorRegistro(empleado);
        
    }                                           

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        siguienteRegistro(empleado);
    }                                            

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        ultimoRegistro(empleado);
    }                                         


    @Override
    public String pageName() {
        return "EmpleadoView";
    }

    @Override
    public void setRouter(Router router) {
        this.router = router;
    }

    public void setPresenter(EmpleadoPresenter presenter) {
        this.presenter = presenter;
    }
    // Variables declaration - do not modify                     
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jdcFechaInicio;
    private javax.swing.JLabel lbEmpleado;
    private javax.swing.JLabel lblApellidoM;
    private javax.swing.JLabel lblApellidoP;
    private javax.swing.JLabel lblFechaCreacion;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JLabel txtFechaInicio;
    private javax.swing.JTextField txtIdEmpleado;
    private javax.swing.JTextField txtNombreEmpleado;
    // End of variables declaration                   
}
