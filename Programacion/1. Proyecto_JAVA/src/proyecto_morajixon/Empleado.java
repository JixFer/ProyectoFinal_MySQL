/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_morajixon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author DELL-INS14
 */
public class Empleado extends javax.swing.JFrame {
    Conexion c=new Conexion();
    Connection con=c.conectar();
    /**
     * Creates new form Empleado
     */
    public Empleado() {
        initComponents();
        Mostrar();
        setIconImage(new ImageIcon(getClass().getResource("/Image/guardia.png")).getImage());//Agrega icono al aplicativo
    }
    private void Mostrar() {
        try{            
            PreparedStatement ps = con.prepareStatement("select * from Vis_empleado");
            ResultSet rs=ps.executeQuery();
            
            DefaultTableModel modelo=new DefaultTableModel();
            modelo.addColumn("Cedula");
            modelo.addColumn("Apellido 1");
            modelo.addColumn("Apellido 2");
            modelo.addColumn("Nombre");
            modelo.addColumn("Cargo");
            modelo.addColumn("Edad");
            modelo.addColumn("Sangre");
            modelo.addColumn("Telefono 1");
            modelo.addColumn("Telefono 2");
            Tabla_principal.setModel(modelo);
            String[] dato = new String[9];
            
            while(rs.next()){
                dato[0]=rs.getString("Cedula");
                dato[1]=rs.getString("Apellido1");
                dato[2]=rs.getString("Apellido2");
                dato[3]=rs.getString("Nombre1");
                dato[4]=rs.getString("Cargo");
                dato[5]=rs.getString("Edad");
                dato[6]=rs.getString("Tipo_sangre");
                dato[7]=rs.getString("Telefono1");
                dato[8]=rs.getString("Telefono2");
                modelo.addRow(dato);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void Limpiar(){
        Txt_Cedula.setText("");
        Txt_Apellido1.setText("");
        Txt_Apellido2.setText("");
        Txt_Nombre1.setText("");
        Txt_Nombre2.setText("");
        Dtm_Nacimiento.setCalendar(null);
        Txt_Telefono1.setText("");
        Txt_Telefono2.setText("");
        Txt_Email.setText("");
        Txt_Coddir.setText("");
        Txt_Calle1.setText("");
        Txt_Calle2.setText("");
        Txt_Sector.setText("");
        Txt_Referencia.setText("");
        Txt_Reentrenamiento.setText("");
        Txt_Capacitacion.setText("");
        Txt_Buscar.setText("");        
    }
    private boolean Campos_vacios(){
        if(Txt_Cedula.getText().length()!=0&&
            Txt_Apellido1.getText().length()!=0&&
            Txt_Apellido2.getText().length()!=0&&
            Txt_Nombre1.getText().length()!=0&&
            Txt_Nombre2.getText().length()!=0&&
            Dtm_Nacimiento.getDate()!=null&&
            Txt_Telefono1.getText().length()!=0&&
            Txt_Coddir.getText().length()!=0&&
            Txt_Calle1.getText().length()!=0&&
            Txt_Calle2.getText().length()!=0&&
            Txt_Sector.getText().length()!=0&&
            Txt_Referencia.getText().length()!=0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private void InsertarDir(){
        try{
            PreparedStatement ps = con.prepareStatement("CALL Sp_InsertarDirEmp (?,?,?,?,?)");
            ps.setString(1,Txt_Coddir.getText());
            ps.setString(2,Txt_Calle1.getText());
            ps.setString(3,Txt_Calle2.getText());
            ps.setString(4,Txt_Sector.getText());
            ps.setString(5,Txt_Referencia.getText());
            int r=ps.executeUpdate();
            if (r>0)
            {
                //JOptionPane.showMessageDialog(null,"Registro guardado");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Error al registrar dirección del empleado");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null,"ERROR!! DIR_EMP :"+ex);
        }
    }
    private void InsertarEmpl(){
        Date date=Dtm_Nacimiento.getDate();
        //long d=date.getTime();
        java.sql.Date Fecha = new java.sql.Date(date.getTime());
        
        try{
        PreparedStatement ps = con.prepareStatement("CALL Sp_InsertarEmpl (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,Txt_Cedula.getText());
        ps.setString(2,Txt_Apellido1.getText());
        ps.setString(3,Txt_Apellido2.getText());
        ps.setString(4,Txt_Nombre1.getText());
        ps.setString(5,Txt_Nombre2.getText());
        ps.setString(6,Fecha.toString()); //Fecha Dtm_Nacimiento
        ps.setString(7,Cmb_Tsangre.getSelectedItem().toString());
        ps.setString(8,Txt_Telefono1.getText());
        ps.setString(9,Txt_Telefono2.getText());
        ps.setString(10,Txt_Email.getText());
        ps.setString(11,Cmb_Escivil.getSelectedItem().toString());
        ps.setString(12,Cmb_Cargo.getSelectedItem().toString());
        ps.setString(13,Txt_Coddir.getText());
        int r=ps.executeUpdate();
            if (r>0)
            {
                //JOptionPane.showMessageDialog(null,"Registro guardado");
            }
            else
            {
                 JOptionPane.showMessageDialog(null,"Error al registrar datos del empleado");
            }
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"ERROR!! EMPL : "+ex);
        }
        
    }
    private void InsertarEstudiosEMPL(){
        try{
        PreparedStatement ps = con.prepareStatement("CALL Sp_InsertarEstudios (?,?,?,?)");
        ps.setString(1,Cmb_Academico.getSelectedItem().toString());
        ps.setString(2,Txt_Capacitacion.getText());
        ps.setString(3,Txt_Reentrenamiento.getText());
        ps.setString(4,Txt_Cedula.getText());
        int r=ps.executeUpdate();
            if (r>0)
            {
                //JOptionPane.showMessageDialog(null,"Registro guardado");
            }
            else
            {
                 JOptionPane.showMessageDialog(null,"Error al registrar Estudios del empleado");
            }
            
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"ERROR ES_EMPL "+ex);
        }
    }
    
    private void Actualizar_Empl(){//PENDIENTE
        Date date=Dtm_Nacimiento.getDate();
        java.sql.Date Fecha = new java.sql.Date(date.getTime());
        try{
        PreparedStatement ps = con.prepareStatement("CALL Sp_ActualizarDatosEmpl (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,Txt_Cedula.getText());
        ps.setString(2,Txt_Apellido1.getText());
        ps.setString(3,Txt_Apellido2.getText());
        ps.setString(4,Txt_Nombre1.getText());
        ps.setString(5,Txt_Nombre2.getText());
        ps.setString(6,Fecha.toString());//Fecha nac
        ps.setString(7,Cmb_Tsangre.getSelectedItem().toString());
        ps.setString(8,Txt_Telefono1.getText());
        ps.setString(9,Txt_Telefono2.getText());
        ps.setString(10,Txt_Email.getText());
        ps.setString(11,Cmb_Escivil.getSelectedItem().toString());
        ps.setString(12,Cmb_Cargo.getSelectedItem().toString());
        ps.setString(13,Txt_Coddir.getText());
        ps.setString(14,Txt_Calle1.getText());
        ps.setString(15,Txt_Calle2.getText());
        ps.setString(16,Txt_Sector.getText());
        ps.setString(17,Txt_Referencia.getText());
        ps.setString(18,Cmb_Academico.getSelectedItem().toString());
        ps.setString(19,Txt_Capacitacion.getText());
        ps.setString(20,Txt_Reentrenamiento.getText());
        int r=ps.executeUpdate();
            if (r>0)
            {
                //JOptionPane.showMessageDialog(null,"Registro guardado");
            }
            else
            {
                 JOptionPane.showMessageDialog(null,"Error al actualizar registros del empleado");
            }
            
        }catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"ERROR!! : "+ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Btn_Atras = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Txt_Cedula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Txt_Apellido1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Txt_Apellido2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Txt_Nombre1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Txt_Nombre2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Cmb_Tsangre = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        Txt_Telefono1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        Txt_Telefono2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Txt_Email = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Cmb_Escivil = new javax.swing.JComboBox<>();
        Dtm_Nacimiento = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_principal = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        Txt_Buscar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Cmb_Academico = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        Txt_Reentrenamiento = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        Txt_Capacitacion = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        Cmb_Cargo = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        Txt_Coddir = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        Txt_Calle1 = new javax.swing.JTextField();
        Txt_Sector = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        Txt_Calle2 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        Txt_Referencia = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        Btn_Nuevo = new javax.swing.JButton();
        Btn_Guardar = new javax.swing.JButton();
        Btn_Eliminar = new javax.swing.JButton();
        Btn_Actualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("EMPLEADO");

        Btn_Atras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Btn_Atras.setText("Atras");
        Btn_Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AtrasActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Cedula :");

        Txt_Cedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Txt_CedulaKeyTyped(evt);
            }
        });

        jLabel4.setText("Apellido 1 :");

        jLabel5.setText("Apellido 2 :");

        jLabel6.setText("Nombre 1 :");

        jLabel7.setText("Nombre 2 :");

        jLabel8.setText("Fecha Nacimiento:");

        jLabel9.setText("Tipo sangre :");

        Cmb_Tsangre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "O+", "O-", "A+", "A+", "B+", "B-", "AB+", "AB-" }));

        jLabel10.setText("Telefóno 1 :");

        jLabel11.setText("Telefóno 2 :");

        jLabel12.setText("E-mail :");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("DATOS PRINCIPALES");

        jLabel25.setText("Estado civi l:");

        Cmb_Escivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soltero", "Casado", "Unión libre", "Separado", "Divorciado", "Viudo" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel25))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 13, Short.MAX_VALUE)
                                                .addComponent(Cmb_Escivil, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(41, 41, 41)
                                                .addComponent(jLabel12))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(Txt_Nombre1)
                                                        .addComponent(Txt_Cedula, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(3, 3, 3)
                                                        .addComponent(Cmb_Tsangre, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(30, 30, 30)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel10)
                                                    .addComponent(jLabel4)))))
                                    .addComponent(jLabel3))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(Txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(Txt_Telefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(Txt_Nombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel11)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(Txt_Apellido1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel5)))))))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Txt_Telefono2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Dtm_Nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Txt_Apellido2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel14))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(Txt_Cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Txt_Apellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Txt_Apellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(Txt_Nombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(Txt_Nombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(Dtm_Nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Cmb_Tsangre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(Txt_Telefono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(Txt_Telefono2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(Txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(Cmb_Escivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Tabla_principal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Tabla_principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_principalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_principal);

        jLabel2.setText("Buscar :");

        Txt_Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Txt_BuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(Txt_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Txt_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("ESTUDIOS");

        jLabel21.setText("Academico :");

        Cmb_Academico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Primaria", "Bachiller", "Tercer nivel" }));

        jLabel22.setText("Reentrenamiento:");

        Txt_Reentrenamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Txt_ReentrenamientoActionPerformed(evt);
            }
        });

        jLabel23.setText("Capacitacion:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("CARGO ASIGNADO ");

        Cmb_Cargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fijo", "Sacafranco", "Practicante", "Desvinculado" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel20)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Cmb_Cargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Cmb_Academico, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(Txt_Reentrenamiento)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Txt_Capacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(Cmb_Academico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(Txt_Reentrenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Txt_Capacitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(24, 24, 24)
                .addComponent(jLabel24)
                .addGap(12, 12, 12)
                .addComponent(Cmb_Cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("DIRECCIÓN");

        jLabel27.setText("Código:");

        jLabel28.setText("Calle 1 :");

        jLabel29.setText("Sector :");

        jLabel30.setText("Calle 2 :");

        jLabel31.setText("Referencia :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(Txt_Referencia))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(18, 18, 18)
                                .addComponent(Txt_Calle2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(Txt_Coddir, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Txt_Calle1)
                            .addComponent(Txt_Sector)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel26)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(Txt_Coddir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(Txt_Calle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(Txt_Calle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(Txt_Sector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(Txt_Referencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Btn_Nuevo.setText("LIMPIAR");
        Btn_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_NuevoActionPerformed(evt);
            }
        });

        Btn_Guardar.setText("GUARDAR");
        Btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_GuardarActionPerformed(evt);
            }
        });

        Btn_Eliminar.setText("ELIMINAR");
        Btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_EliminarActionPerformed(evt);
            }
        });

        Btn_Actualizar.setText("ACTUALIZAR");
        Btn_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(Btn_Nuevo)
                .addGap(18, 18, 18)
                .addComponent(Btn_Guardar)
                .addGap(18, 18, 18)
                .addComponent(Btn_Eliminar)
                .addGap(18, 18, 18)
                .addComponent(Btn_Actualizar)
                .addGap(40, 40, 40))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Nuevo)
                    .addComponent(Btn_Guardar)
                    .addComponent(Btn_Eliminar)
                    .addComponent(Btn_Actualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Btn_Atras, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(layout.createSequentialGroup()
                .addGap(532, 532, 532)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Atras, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AtrasActionPerformed
        // TODO add your handling code here:
        Inicio in=new Inicio();
        in.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Btn_AtrasActionPerformed

    private void Btn_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ActualizarActionPerformed
        boolean obj; 
        obj=Campos_vacios();
        if (obj==false){//Verificar si los campos no estan vacios
            
            try{
                PreparedStatement ps = con.prepareStatement("call Sp_VerificarEmpl(?);");
                ps.setString(1, Txt_Cedula.getText());
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    //Existe el empleado
                    Actualizar_Empl();
                    JOptionPane.showMessageDialog(null, "El empleado con C.I:"+Txt_Cedula.getText()+"se ha actualizado");
                    Mostrar();
                    Limpiar();
                }
                else
                {
                    //No existe el empleado
                    JOptionPane.showMessageDialog(null, "El empleado no se encuentra registrado, verifique la cedula");
                }
                Mostrar();
                Limpiar();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: "+ex);
            }
        }else{
           JOptionPane.showMessageDialog(null,"Existen campos estan vacios");
           Txt_Cedula.requestFocus(); 
        }
        
    }//GEN-LAST:event_Btn_ActualizarActionPerformed

    private void Btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_EliminarActionPerformed
        // TODO add your handling code here:
        boolean obj; 
        obj=Campos_vacios();
        if (obj==false){//Verificar si los campos no estan vacios
            try{
                PreparedStatement ps = con.prepareStatement("CALL Sp_EliminarEmpl (?)");
                ps.setString(1,Txt_Cedula.getText());
                int r=ps.executeUpdate();
                if (r>0)
                {
                    Limpiar();
                    JOptionPane.showMessageDialog(null,"Registro Eliminado");
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"Error al Eliminar el registro");
                }
                Mostrar();
                Limpiar();
            }catch (SQLException ex) {
                 JOptionPane.showMessageDialog(null,"ERROR!! : "+ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Existen campos estan vacios");
            Txt_Cedula.requestFocus();
        }
    }//GEN-LAST:event_Btn_EliminarActionPerformed

    private void Tabla_principalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_principalMouseClicked
        // TODO add your handling code here:
        String Cargo;
        String Sangre;
        String Civil;
        String Academico;
        Limpiar();
        int fila = Tabla_principal.rowAtPoint(evt.getPoint());//Obtener la fila donde se selecciona
        String cedula=Tabla_principal.getValueAt(fila, 0).toString();//Para obtener el valor de la cedula (columna posicion 0)
        //JOptionPane.showMessageDialog(null,"La cedula seleccionada es : "+cedula);
        try{
            
            PreparedStatement ps = con.prepareStatement("call Sp_DatosEmpl(?);");
            ps.setString(1, cedula);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                this.Txt_Coddir.setText(rs.getString("Cod_dir"));
                this.Txt_Calle1.setText(rs.getString("Calle_prin"));
                this.Txt_Calle2.setText(rs.getString("Calle_sec"));
                this.Txt_Sector.setText(rs.getString("Sector"));
                this.Txt_Referencia.setText(rs.getString("Referencia"));
                Txt_Cedula.setText(rs.getString("Cedula"));
                Txt_Apellido1.setText(rs.getString("Apellido1"));
                Txt_Apellido2.setText(rs.getString("Apellido2"));
                Txt_Nombre1.setText(rs.getString("Nombre1"));
                Txt_Nombre2.setText(rs.getString("Nombre2"));
                //Transforma fecha para que Mysql lo detecte
                SimpleDateFormat FormatoFecha= new SimpleDateFormat("yyyy-MM-dd");
                Dtm_Nacimiento.setDate(FormatoFecha.parse(rs.getString("F_nac")));
                //Validacion de tipo de sangre Cmb_Tsangre
                Sangre=rs.getString("Tipo_sangre");
                for(int i=0; i<=Cmb_Tsangre.getItemCount()-1;i++)
                {
                    if(Sangre.equals(Cmb_Tsangre.getItemAt(i)))
                    {
                        Cmb_Tsangre.setSelectedIndex(i);
                    }
                }
                Txt_Telefono1.setText(rs.getString("Telefono1"));
                Txt_Telefono2.setText(rs.getString("Telefono2"));
                //Validacion de estado civil Cmb_Escivil
                Civil=rs.getString("Estado_civil");
                for(int i=0; i<=Cmb_Escivil.getItemCount()-1;i++)
                {
                    if(Civil.equals(Cmb_Escivil.getItemAt(i)))
                    {
                        Cmb_Escivil.setSelectedIndex(i);
                    }
                }
                Txt_Email.setText(rs.getString("E_mail"));
                //Validacion de nivel academico Cmb_Academico
                Academico=rs.getString("Grado_academico");
                for(int i=0; i<=Cmb_Academico.getItemCount()-1;i++)
                {
                    if(Academico.equals(Cmb_Academico.getItemAt(i)))
                    {
                        Cmb_Academico.setSelectedIndex(i);
                    }
                }
                Txt_Reentrenamiento.setText(rs.getString("Reentrenamiento"));
                Txt_Capacitacion.setText(rs.getString("Curso_capacitacion"));
                //Cmb_cargo
                Cargo=rs.getString("Id_cargo");
                switch (Cargo){
                    case "Fi":
                        Cmb_Cargo.setSelectedIndex(0);
                    break;
                    case "Sa":
                        Cmb_Cargo.setSelectedIndex(1);
                    break;
                    case "Pr":
                        Cmb_Cargo.setSelectedIndex(2);
                    break;
                    case "De":
                        Cmb_Cargo.setSelectedIndex(3);
                    break;
                }
            }
            
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_Tabla_principalMouseClicked

    private void Txt_ReentrenamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Txt_ReentrenamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Txt_ReentrenamientoActionPerformed

    private void Txt_CedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Txt_CedulaKeyTyped
        // TODO add your handling code here:
        char Validar=evt.getKeyChar();
        if(Character.isLetter(Validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane,"Ingrese solo números");
        }
        if(Txt_Cedula.getText().length()>=10){
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(rootPane,"Solo se permite ingresar 10 digitos");
        }
    }//GEN-LAST:event_Txt_CedulaKeyTyped

    private void Btn_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_NuevoActionPerformed
        // TODO add your handling code here:
        Limpiar();
        Txt_Cedula.requestFocus();
    }//GEN-LAST:event_Btn_NuevoActionPerformed

    private void Btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_GuardarActionPerformed
        // TODO add your handling code here:
        boolean obj; 
        obj=Campos_vacios();
        if (obj==false){//Verificar si los campos no estan vacios
            String Cedula=Txt_Cedula.getText();
            String Cd_dir=Txt_Coddir.getText();
            //Primero verificar si existe la direccion
            try{
                PreparedStatement ps1 = con.prepareStatement("call Sp_VerificarEmpl(?);");
                ps1.setString(1, Cedula);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()){//Existe un usuario con la cedula
                    JOptionPane.showMessageDialog(null, "El usuario con C.I : "+Cedula+", ya se encuentra registrado (Ingrese un usuario nuevo)");
                }
                else
                {
                    PreparedStatement ps = con.prepareStatement("call Sp_VerificarDirEmp(?);");
                    ps.setString(1, Cd_dir);
                    ResultSet rs=ps.executeQuery();
                    if(rs.next()){
                        //Existe el Codigo de la direccion
                        InsertarEmpl();
                        InsertarEstudiosEMPL();
                    }
                    else
                    {
                        //No existe el codigo de la direccion
                        InsertarDir();
                        InsertarEmpl();
                        InsertarEstudiosEMPL();
                    }
                    JOptionPane.showMessageDialog(null, "Se guardo correctamente el registro");
                    Mostrar();
                    Limpiar();
                }
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: "+ex);
            }
        }else{ 
            JOptionPane.showMessageDialog(null,"Existen campos estan vacios");
            Txt_Cedula.requestFocus();
        }
    }//GEN-LAST:event_Btn_GuardarActionPerformed

    private void Txt_BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Txt_BuscarKeyReleased
        // TODO add your handling code here:
        try{            
            PreparedStatement ps = con.prepareStatement("CALL Sp_BuscarEmpl (?)");
            ps.setString(1, "%"+Txt_Buscar.getText()+"%");
            ResultSet rs=ps.executeQuery();
            DefaultTableModel modelo=new DefaultTableModel();
            modelo.addColumn("Cedula");
            modelo.addColumn("Apellido 1");
            modelo.addColumn("Apellido 2");
            modelo.addColumn("Nombre");
            modelo.addColumn("Cargo");
            modelo.addColumn("Edad");
            modelo.addColumn("Sangre");
            modelo.addColumn("Telefono 1");
            modelo.addColumn("Telefono 2");
            Tabla_principal.setModel(modelo);
            String[] dato = new String[9];
            
            while(rs.next()){
                dato[0]=rs.getString("Cedula");
                dato[1]=rs.getString("Apellido1");
                dato[2]=rs.getString("Apellido2");
                dato[3]=rs.getString("Nombre1");
                dato[4]=rs.getString("Cargo");
                dato[5]=rs.getString("Edad");
                dato[6]=rs.getString("Tipo_sangre");
                dato[7]=rs.getString("Telefono1");
                dato[8]=rs.getString("Telefono2");
                modelo.addRow(dato);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_Txt_BuscarKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Empleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Actualizar;
    private javax.swing.JButton Btn_Atras;
    private javax.swing.JButton Btn_Eliminar;
    private javax.swing.JButton Btn_Guardar;
    private javax.swing.JButton Btn_Nuevo;
    private javax.swing.JComboBox<String> Cmb_Academico;
    private javax.swing.JComboBox<String> Cmb_Cargo;
    private javax.swing.JComboBox<String> Cmb_Escivil;
    private javax.swing.JComboBox<String> Cmb_Tsangre;
    private com.toedter.calendar.JDateChooser Dtm_Nacimiento;
    private javax.swing.JTable Tabla_principal;
    private javax.swing.JTextField Txt_Apellido1;
    private javax.swing.JTextField Txt_Apellido2;
    private javax.swing.JTextField Txt_Buscar;
    private javax.swing.JTextField Txt_Calle1;
    private javax.swing.JTextField Txt_Calle2;
    private javax.swing.JTextField Txt_Capacitacion;
    private javax.swing.JTextField Txt_Cedula;
    private javax.swing.JTextField Txt_Coddir;
    private javax.swing.JTextField Txt_Email;
    private javax.swing.JTextField Txt_Nombre1;
    private javax.swing.JTextField Txt_Nombre2;
    private javax.swing.JTextField Txt_Reentrenamiento;
    private javax.swing.JTextField Txt_Referencia;
    private javax.swing.JTextField Txt_Sector;
    private javax.swing.JTextField Txt_Telefono1;
    private javax.swing.JTextField Txt_Telefono2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    
}
