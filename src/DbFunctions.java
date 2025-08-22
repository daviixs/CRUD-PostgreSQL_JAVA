//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String username, String password) {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, username, password);
            if (conn != null) {
                System.out.println("Conectado com sucesso!");
            } else {
                System.out.println("Erro na conexão ");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return conn;
    }

    public void insertAnimal(Connection conn, String nome, String tipo, int idade, String raca, String sexo, String status, LocalDate dataResgate, Long canilId) {
        String sql = "INSERT INTO animais (nome, tipo, idade, raca, sexo, status, data_resgate, canil_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, tipo);
            pstmt.setInt(3, idade);
            pstmt.setString(4, raca);
            pstmt.setString(5, sexo);
            pstmt.setString(6, status);
            if (dataResgate != null) {
                pstmt.setDate(7, Date.valueOf(dataResgate));
            } else {
                pstmt.setDate(7, (Date)null);
            }

            if (canilId != null) {
                pstmt.setLong(8, canilId);
            } else {
                pstmt.setNull(8, -5);
            }

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Animal inserido com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao inserir animal: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void read_data(Connection conn, String table_name) {
        try {
            String query = String.format("select * from animais", table_name);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Raça: " + rs.getString("raca"));
                System.out.println("Sexo: " + rs.getString("sexo"));
                System.out.println("Situação: " + rs.getString("status"));
                System.out.println("Data de Resgate: " + rs.getString("data_resgate"));
                System.out.println("Canil ID: " + rs.getString("canil_id"));
                System.out.println("------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateFieldById(Connection conn, String tableName, String field, int id, Object newValue) {
        String sql = "UPDATE " + tableName + " SET " + field + " = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, newValue);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Campo '" + field + "' atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com esse ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }

    }

    public int getAnimalIdByName(Connection conn, String tableName, String nome) {
        String sql = "SELECT id FROM " + tableName + " WHERE nome = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar animal: " + e.getMessage());
        }

        return -1;
    }

    public void searchById(Connection conn, String tableName, int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Idade: " + rs.getInt("idade"));
                System.out.println("Raça: " + rs.getString("raca"));
                System.out.println("Sexo: " + rs.getString("sexo"));
                System.out.println("Situação: " + rs.getString("status"));
                System.out.println("Data de Resgate: " + String.valueOf(rs.getDate("data_resgate")));
                System.out.println("Canil ID: " + rs.getLong("canil_id"));
                System.out.println("------------------------------------");
            } else {
                System.out.println("Nenhum animal encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar animal por ID: " + e.getMessage());
        }

    }
}
