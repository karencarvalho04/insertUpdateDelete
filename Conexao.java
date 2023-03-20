import javax.swing.*;
import java.net.URL;
import java.sql.*;

public class Conexao {
    final String URL = "jdbc:mysql://localhost:3306/cadaluno";
    final String USER = "root";
    final String PASSWORD = "root";

    public Conexao() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado!");

            Statement st = con.createStatement();
            System.out.println("Statement criado!");

            String sql = "INSERT INTO aluno (nome, matricula) VALUES (?,?)";
            st = con.prepareStatement(sql);


            String nome = JOptionPane.showInputDialog("Digite o nome");
            String matricula = JOptionPane.showInputDialog("Matricula");

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, matricula);

            int linhasAfetadas = stmt.executeUpdate();

            System.out.println(nome);
            System.out.println(matricula);

        } catch (Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] args) {

        Conexao conexao = new Conexao();

    }
}
