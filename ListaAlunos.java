import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ListaAlunos extends JFrame{
    private JPanel pnlListaAlunos;
    private JButton alterarButton;
    private JButton excluirButton;
    private JTable tblListar;
    private JButton voltarButton;
    final String URL = "jdbc:mysql://localhost:3306/cadaluno";
    final String USER = "root";
    final String PASSWORD = "root";
    final String alterarDados = "UPDATE aluno SET nome = ?, matricula = ? WHERE id = ?";
    final String consultarDados = "SELECT * FROM aluno";
    final String excluirDados = "DELETE FROM aluno WHERE matricula = ?";

    public ListaAlunos(){
        AddListeners();
        iniciarComponentes();
        Buttons();
    }
    public void AddListeners(){
        DefaultTableModel alunos = new DefaultTableModel();
        alunos.addColumn("Nome");
        alunos.addColumn("Matrícula");
        tblListar.setModel(alunos);

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            Statement stmt = null;
            stmt = connection.createStatement();

            ResultSet rs = null;
            rs = stmt.executeQuery(consultarDados);

            while (rs.next()) {
                Object[] row = new Object[2];
                row[0] = rs.getObject(1);
                row[1] = rs.getObject(2);
                alunos.addRow(row);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        JScrollPane scrollPane = new JScrollPane(tblListar);
         pnlListaAlunos.add(scrollPane);
        }
        public void Buttons(){

            voltarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    Formulario formulario = new Formulario();
                    formulario.setVisible(true);
                    dispose();
                }
            });
            excluirButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection connection = null;

                    try {
                        // Obtenha a linha selecionada no JTable
                        int linhaSelecionada = tblListar.getSelectedRow();
                        if (linhaSelecionada < 0) {
                            JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir.");
                            return;
                        }
                        int matricula = (int) tblListar.getValueAt(linhaSelecionada, 1);

                        // Execute a instrução SQL DELETE no banco de dados
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        PreparedStatement stmt = connection.prepareStatement(excluirDados);
                        stmt.setInt(1, matricula);
                        int resultado = stmt.executeUpdate();
                        if (resultado == 1) {
                            JOptionPane.showMessageDialog(null, "Registro excluído com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao excluir registro.");
                        }

                        // Atualize o modelo do JTable após a exclusão
                        DefaultTableModel model = (DefaultTableModel) tblListar.getModel();
                        model.removeRow(linhaSelecionada);
                    } catch (SQLException ex) {
                        System.out.println("Erro ao excluir registro: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir.");
                    }
                }
            });
        }
    public void iniciarComponentes(){
        setTitle("Menu");
        setSize(1200,1100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlListaAlunos);
        setVisible(true);
        tblListar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public static void main(String[] args) {
        ListaAlunos lista = new ListaAlunos();
    }
}
