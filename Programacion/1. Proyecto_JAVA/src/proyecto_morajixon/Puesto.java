/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_morajixon;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author DELL-INS14
 */
public class Puesto extends javax.swing.JFrame {
    Conexion c=new Conexion();
    Connection con=c.conectar();
    /*int[] Id_Puesto=new int[4];
    int i=0;*/
    /**
     * Creates new form Puesto
     */
    public Puesto() {
        initComponents();
        Mostrar();
        setIconImage(new ImageIcon(getClass().getResource("/Image/guardia.png")).getImage());//Agrega icono al aplicativo
    }
    private void Mostrar() {
        try{            
            PreparedStatement ps = con.prepareStatement("select * from Vis_puesto");
            ResultSet rs=ps.executeQuery();
            DefaultTableModel modelo=new DefaultTableModel();
            modelo.addColumn("Delta");
            modelo.addColumn("Puesto");
            modelo.addColumn("Telefono");
            modelo.addColumn("E mail");
            Tabla_principal.setModel(modelo);
            String[] dato = new String[4];
            while(rs.next()){
                dato[0]=rs.getString("N_delta");
                dato[1]=rs.getString("Nombre_puesto");
                dato[2]=rs.getString("Telefono");
                dato[3]=rs.getString("e_mail");
                modelo.addRow(dato);
            }
            /*PreparedStatement ps2 = con.prepareStatement("select Id_puesto from Vis_puesto");            
            ResultSet rs2=ps2.executeQuery();
            while(rs2.next()){
                //Llenar el identificador del puesto
                Id_Puesto[i]=rs2.getInt("Id_puesto");
                i=i+1;
                if(i==Tabla_principal.getRowCount())
                {break;}
            }*/
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error : "+ex); 
        }
    }
    private void Limpiar(){
        Txt_Delta.setText("");
        Txt_Nombre.setText("");
        Txt_Telefono.setText("");
        Txt_Email.setText("");
        Txt_Coddir.setText("");
        Txt_Calle1.setText("");
        Txt_Calle2.setText("");
        Txt_Sector.setText("");
        Txt_Referencia.setText("");
        Txt_NomCli.setText("");
        Txt_Tel1Cli.setText("");
        Txt_Tel2Cli.setText("");
        Txa_Observacion.setText("");
        Txt_Buscar.setText("");
    }
    private boolean Campos_vacios(){
        if(Txt_Delta.getText().length()!=0&&
            Txt_Nombre.getText().length()!=0&&
            Txt_Telefono.getText().length()!=0&&
            Txt_Email.getText().length()!=0&&
            Txt_Coddir.getText().length()!=0&&
            Txt_Calle1.getText().length() !=0 &&
            Txt_Calle2.getText().length()!=0&&
            Txt_Sector.getText().length()!=0&&
            Txt_Referencia.getText().length()!=0&&
            Txt_NomCli.getText().length() !=0 &&
            Txt_Tel1Cli.getText().length()!=0&&
            Txt_Tel2Cli.getText().length()!=0&&
            Txa_Observacion.getText().length()!=0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private int ConsultarId(int Delta, String Nombre,String Telefono, String Email){
        int Id_Pues=0;
        try{
            PreparedStatement ps = con.prepareStatement("CALL Sp_IdentificarPuesto (?,?,?,?)");
            ps.setInt(1,Delta);
            ps.setString(2,Nombre);
            ps.setString(3,Telefono);
            ps.setString(4,Email);
            ResultSet rs=ps.executeQuery();
             if(rs.next()){
                    //Existe el PUESTO
                    Id_Pues=rs.getInt("Id_Puesto");
                    /*JOptionPane.showMessageDialog(null,"La ID de la clase es : "+Id_Pues);*/
                }
                else
                {
                    //No existe el PUESTO
                    //JOptionPane.showMessageDialog(null, "No se encontro el ID del puesto");
                }
                Mostrar();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null,"ERROR!! DIR_EMP :"+ex);
        }
        return(Id_Pues);
    }
    private void InsertardatosPuesto(){
        try{
        PreparedStatement ps = con.prepareStatement("CALL Sp_InsertarPuesto (?,?,?,?)");
        ps.setInt(1,Integer.parseInt(Txt_Delta.getText()));
        ps.setString(2,Txt_Nombre.getText());
        ps.setString(3,Txt_Telefono.getText());
        ps.setString(4,Txt_Email.getText());
        int r=ps.executeUpdate();
            if (r>0)
            {
                //JOptionPane.showMessageDialog(null,"Puesto registrado");
            }
            else
            {
                 JOptionPane.showMessageDialog(null,"Error al registrar datos del Puesto");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"ERROR TABLA PUESTO: "+ex);
        }
        
    }
    private void InsertarDirPuesto(){
        int Id;
        try{
        PreparedStatement ps = con.prepareStatement("CALL Sp_InsertarDirPuesto  (?,?,?,?,?,?)");
        ps.setString(1,Txt_Coddir.getText());
        ps.setString(2,Txt_Calle1.getText());
        ps.setString(3,Txt_Calle2.getText());
        ps.setString(4,Txt_Sector.getText());
        ps.setString(5,Txt_Referencia.getText());
        //Consultar ID de puesto
        int Delta=Integer.parseInt(Txt_Delta.getText());
        Id=ConsultarId(Delta, Txt_Nombre.getText(), Txt_Telefono.getText(), Txt_Email.getText());
        ps.setInt(6,Id);
        
        int r=ps.executeUpdate();
            if (r>0)
            {
                //JOptionPane.showMessageDialog(null,"Direccion registrada");
            }
            else
            {
                 JOptionPane.showMessageDialog(null,"Error al registrar datos de Direccion PUESTO");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"ERROR TABLA DIR PUESTO: "+ex);
        }
    }
    private void InsertarCliPuesto(){
        int Id;
        try{
        PreparedStatement ps = con.prepareStatement("CALL Sp_InsertarCliPuesto  (?,?,?,?,?)");
        ps.setString(1,Txt_NomCli.getText());
        ps.setString(2,Txt_Tel1Cli.getText());
        ps.setString(3,Txt_Tel2Cli.getText());
        ps.setString(4,Txa_Observacion.getText());
        //Consultar ID de puesto
        int Delta=Integer.parseInt(Txt_Delta.getText());
        Id=ConsultarId(Delta, Txt_Nombre.getText(), Txt_Telefono.getText(), Txt_Email.getText());
        ps.setInt(5,Id);
        
        int r=ps.executeUpdate();
            if (r>0)
            {
                //JOptionPane.showMessageDialog(null,"Cliente puesto registrado");
            }
            else
            {
                 JOptionPane.showMessageDialog(null,"Error al registrar datos de Datos Clientes");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"ERROR TABLA CLI PUESTO: "+ex);
        }
        
    }
    private void Actualizar_Puesto(){
        int Id;
        try{
            PreparedStatement ps = con.prepareStatement("CALL Sp_ActualizarDatosPuesto (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            //Consultar ID de puesto
            int Delta=Integer.parseInt(Txt_Delta.getText());
            Id=ConsultarId(Delta, Txt_Nombre.getText(), Txt_Telefono.getText(), Txt_Email.getText());
            ps.setInt(1,Id);
            ps.setString(2,Txt_Delta.getText());
            ps.setString(3,Txt_Nombre.getText());
            ps.setString(4,Txt_Telefono.getText());
            ps.setString(5,Txt_Email.getText());
            ps.setString(6,Txt_Coddir.getText());//Fecha nac
            ps.setString(7,Txt_Calle1.getText());
            ps.setString(8,Txt_Calle2.getText());
            ps.setString(9,Txt_Sector.getText());
            ps.setString(10,Txt_Referencia.getText());
            ps.setString(11,Txt_NomCli.getText());
            ps.setString(12,Txt_Tel1Cli.getText());
            ps.setString(13,Txt_Tel2Cli.getText());
            ps.setString(14,Txa_Observacion.getText());
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Txt_Delta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Txt_Nombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Txt_Telefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Txt_Email = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_principal = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        Txt_Buscar = new javax.swing.JTextField();
        Btn_Atras1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        Btn_Limpiar = new javax.swing.JButton();
        Btn_Guardar = new javax.swing.JButton();
        Btn_Eliminar = new javax.swing.JButton();
        Btn_Actualizar = new javax.swing.JButton();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Txt_NomCli = new javax.swing.JTextField();
        Txt_Tel1Cli = new javax.swing.JTextField();
        Txt_Tel2Cli = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Txa_Observacion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("PUESTO");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Delta :");

        jLabel3.setText("Nombre :");

        jLabel4.setText("Telefono :");

        jLabel5.setText("E-mail :");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("DATOS PRINCIPALES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Txt_Email)
                            .addComponent(Txt_Telefono)
                            .addComponent(Txt_Nombre)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Txt_Delta, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                                .addGap(126, 126, 126))))
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Txt_Delta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
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

        jLabel6.setText("Buscar :");

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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(Txt_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Txt_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        Btn_Atras1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Btn_Atras1.setText("Atras");
        Btn_Atras1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Atras1ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Btn_Limpiar.setText("LIMPIAR");
        Btn_Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_LimpiarActionPerformed(evt);
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
                .addGap(2, 2, 2)
                .addComponent(Btn_Limpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_Guardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_Eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_Actualizar)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Limpiar)
                    .addComponent(Btn_Guardar)
                    .addComponent(Btn_Eliminar)
                    .addComponent(Btn_Actualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(Txt_Calle1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(Txt_Sector)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(Txt_Referencia)))
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
                .addGap(52, 52, 52))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("DATOS CLIENTE");

        jLabel7.setText("Nombre :");

        jLabel8.setText("Telefono 1 :");

        jLabel9.setText("Telefono 2 :");

        jLabel10.setText("Observacion :");

        Txa_Observacion.setColumns(20);
        Txa_Observacion.setRows(5);
        jScrollPane2.setViewportView(Txa_Observacion);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Txt_NomCli, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(Txt_Tel1Cli)
                            .addComponent(Txt_Tel2Cli))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Txt_NomCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Txt_Tel1Cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Txt_Tel2Cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(30, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(375, 375, 375)
                                .addComponent(Btn_Atras1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(358, 358, 358)
                        .addComponent(jLabel1)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Btn_Atras1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_Atras1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Atras1ActionPerformed
        // TODO add your handling code here:
        Inicio in=new Inicio();
        in.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Btn_Atras1ActionPerformed

    private void Btn_LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_LimpiarActionPerformed
        // TODO add your handling code here:
        Limpiar();
        Txt_Delta.requestFocus();
    }//GEN-LAST:event_Btn_LimpiarActionPerformed

    private void Btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_GuardarActionPerformed
        // TODO add your handling code here:
        int id=0;
        boolean obj; 
        obj=Campos_vacios();
        if (obj==false){//Verificar si los campos no estan vacios
            int Delta=Integer.parseInt(Txt_Delta.getText());
            id=ConsultarId(Delta, Txt_Nombre.getText(), Txt_Telefono.getText(), Txt_Email.getText());
            if(id==0){//Es igual a cero porque no encuentro un Id_puesto
                InsertardatosPuesto();
                InsertarDirPuesto();
                InsertarCliPuesto();
                JOptionPane.showMessageDialog(null,"El Puesto se registro correctamente");
                Limpiar();
                Txt_Delta.requestFocus();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"El Puesto ya se encuentra registrado");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Existen campos estan vacios");
            Txt_Delta.requestFocus();
        }
        Mostrar();
    }//GEN-LAST:event_Btn_GuardarActionPerformed

    private void Btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_EliminarActionPerformed
        // TODO add your handling code here:
        int Id;
        boolean obj; 
        obj=Campos_vacios();
        if (obj==false){//Verificar si los campos no estan vacios
            int Delta=Integer.parseInt(Txt_Delta.getText());
                Id=ConsultarId(Delta, Txt_Nombre.getText(), Txt_Telefono.getText(), Txt_Email.getText());
            try{
                if(Id!=0){//Es distinto a cero porque encontro un Id_puesto
                    PreparedStatement ps2 = con.prepareStatement("CALL Sp_EliminarPuesto (?)");
                    ps2.setInt(1,Id);//ID
                    int r=ps2.executeUpdate();
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
                }
            }catch (SQLException ex) {
                 JOptionPane.showMessageDialog(null,"ERROR!! : "+ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Existen campos estan vacios");
            Txt_Delta.requestFocus();
        }
    }//GEN-LAST:event_Btn_EliminarActionPerformed

    private void Btn_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ActualizarActionPerformed
        // TODO add your handling code here:
        int Id=0;
        boolean obj; 
        obj=Campos_vacios();
        if (obj==false){ //Verificar si los campos no estan vacios
            int Delta = Integer.parseInt(Txt_Delta.getText());
            Id=ConsultarId(Delta, Txt_Nombre.getText(), Txt_Telefono.getText(), Txt_Email.getText());
            if(Id!=0){//Es distinto a cero porque encontro un Id_puesto
                //Existe el empleado
                Actualizar_Puesto();
                JOptionPane.showMessageDialog(null, "El Puesto se ha actualizado");
            }
            else
            {
                //No existe el empleado
                JOptionPane.showMessageDialog(null, "El Puesto no se encuentra registrado, verifique la cedula");
            }
            Mostrar();
            Limpiar();
        }else{
                JOptionPane.showMessageDialog(null,"Existen campos estan vacios");
                Txt_Delta.requestFocus(); 
        }
    }//GEN-LAST:event_Btn_ActualizarActionPerformed

    private void Tabla_principalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_principalMouseClicked
        int Id;
        // TODO add your handling code here:
        //Sp_DatosPuesto
        Limpiar();
        int fila = Tabla_principal.rowAtPoint(evt.getPoint());//Obtener la fila donde se selecciona
        int Delta=Integer.parseInt(Tabla_principal.getValueAt(fila, 0).toString());//Para obtener el valor del puesto (columna posicion 0)
        String Puesto=Tabla_principal.getValueAt(fila, 1).toString();
        String Telefono=Tabla_principal.getValueAt(fila, 2).toString();
        String Email=Tabla_principal.getValueAt(fila, 3).toString();
        Id=ConsultarId(Delta, Puesto, Telefono, Email);
        //JOptionPane.showMessageDialog(null,"La ID Puesto es : "+Id_Pues);
        try{
            
            PreparedStatement ps = con.prepareStatement("call Sp_DatosPuesto(?);");
            ps.setInt(1, Id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Txt_Delta.setText(rs.getString("N_delta"));
                Txt_Nombre.setText(rs.getString("Nombre_puesto"));
                Txt_Telefono.setText(rs.getString("Telefono"));
                Txt_Email.setText(rs.getString("e_mail"));
                Txt_Coddir.setText(rs.getString("Cod_dir"));
                Txt_Calle1.setText(rs.getString("Calle_prin"));
                Txt_Calle2.setText(rs.getString("Calle_sec"));
                Txt_Sector.setText(rs.getString("Sector"));
                Txt_Referencia.setText(rs.getString("Referencia"));
                Txt_NomCli.setText(rs.getString("Nombre"));
                Txt_Tel1Cli.setText(rs.getString("Tel_Cliente"));
                Txt_Tel2Cli.setText(rs.getString("Celular"));
                Txa_Observacion.setText(rs.getString("Observacion"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"ERROR : "+ex);
        }
        
    }//GEN-LAST:event_Tabla_principalMouseClicked

    private void Txt_BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Txt_BuscarKeyReleased
        // TODO add your handling code here:
        try{            
            PreparedStatement ps = con.prepareStatement("CALL Sp_BuscarPuesto (?)");
            ps.setString(1, "%"+Txt_Buscar.getText()+"%");
            ResultSet rs=ps.executeQuery();
            DefaultTableModel modelo=new DefaultTableModel();
            modelo.addColumn("Delta");
            modelo.addColumn("Puesto");
            modelo.addColumn("Telefono");
            modelo.addColumn("E mail");
            Tabla_principal.setModel(modelo);
            String[] dato = new String[4];
            while(rs.next()){
                dato[0]=rs.getString("N_delta");
                dato[1]=rs.getString("Nombre_puesto");
                dato[2]=rs.getString("Telefono");
                dato[3]=rs.getString("e_mail");
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
            java.util.logging.Logger.getLogger(Puesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Puesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Puesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Puesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Puesto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Actualizar;
    private javax.swing.JButton Btn_Atras1;
    private javax.swing.JButton Btn_Eliminar;
    private javax.swing.JButton Btn_Guardar;
    private javax.swing.JButton Btn_Limpiar;
    private javax.swing.JTable Tabla_principal;
    private javax.swing.JTextArea Txa_Observacion;
    private javax.swing.JTextField Txt_Buscar;
    private javax.swing.JTextField Txt_Calle1;
    private javax.swing.JTextField Txt_Calle2;
    private javax.swing.JTextField Txt_Coddir;
    private javax.swing.JTextField Txt_Delta;
    private javax.swing.JTextField Txt_Email;
    private javax.swing.JTextField Txt_NomCli;
    private javax.swing.JTextField Txt_Nombre;
    private javax.swing.JTextField Txt_Referencia;
    private javax.swing.JTextField Txt_Sector;
    private javax.swing.JTextField Txt_Tel1Cli;
    private javax.swing.JTextField Txt_Tel2Cli;
    private javax.swing.JTextField Txt_Telefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
