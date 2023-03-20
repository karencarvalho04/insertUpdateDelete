import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static java.lang.Integer.parseInt;

public class Formulario extends JFrame{

    final String URL = "jdbc:mysql://localhost:3306/cadaluno";
    final String USER = "root";
    final String PASSWORD = "root";
    private JLabel lblTitulo;
    private JTextField txtNome;
    private JTextField txtMatricula;
    private JButton btnInserir;
    private JButton btnAtualizar;
    private JButton btnVerLista;
    private JButton btnLimpar;
    private JLabel lblMatricula;
    private JLabel lblNome;
    private JPanel pnlCadAlunos;

    public Formulario() {
        AddListeners();
        Conecta();
        iniciarComponentes();
    }
    public void AddListeners() {

        btnVerLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaAlunos alunos = new ListaAlunos();
                alunos.setVisible(true);
                dispose();
            }
        });

         btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNome.setText("");
                txtMatricula.setText("");
            }
        });
    }
    public void iniciarComponentes(){
        setTitle("Menu");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlCadAlunos);
        setVisible(true);
    }
    public void Conecta() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado!");

            final String inserirDados = "INSERT INTO aluno (nome, matricula) VALUES (?,?)";
            final String alterarDados = "UPDATE aluno SET nome = ?, matricula = ? WHERE id = ?";

            final  PreparedStatement stmtInserir;
            final  PreparedStatement stmtAlterar;

            stmtInserir = con.prepareStatement(inserirDados);
            stmtAlterar = con.prepareStatement(alterarDados);

            btnInserir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   String nome = txtNome.getText();
                   String matriculaStr = txtMatricula.getText();

                    try {
                        int matricula = Integer.parseInt(matriculaStr);
                        stmtInserir.setString(1, nome);
                        stmtInserir.setInt(2, matricula);
                        stmtInserir.executeUpdate();
                        System.out.println("Dados inseridos");
                        JOptionPane.showMessageDialog(btnInserir, "Dados inseridos!");
                        txtNome.setText("");
                        txtMatricula.setText("");

                    } catch (NumberFormatException ex){
                        System.out.println("A matrícula informada não é um número");
                    }
                    catch (Exception ex){
                        System.out.println("Erro ao inserir dados no banco");
                    }
        }
    });
            /*btnAtualizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nome = txtNome.getText();
                    String matriculaStr = txtMatricula.getText();

                    try {
                        int matricula = Integer.parseInt(matriculaStr);
                        stmtAlterar.setString(1, nome);
                        stmtAlterar.setInt(2, matricula);
                        stmtAlterar.executeUpdate();
                        System.out.println("Dados alterados");
                        JOptionPane.showMessageDialog(btnInserir, "Dados alterados!");
                        txtNome.setText("");
                        txtMatricula.setText("");

                    } catch (NumberFormatException ex){
                        System.out.println("A matrícula informada não é um número");
                    }
                    catch (Exception ex){
                        System.out.println("Erro ao inserir dados no banco");
                    }
                }
            });*/
        } catch (Exception e){
            System.out.println("Erro ao conectar o banco de dados" + e.getMessage());
        }
    }
    public static void main(String[] args) {

    Formulario form = new Formulario();
    }
}