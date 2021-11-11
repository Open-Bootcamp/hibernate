package com.example.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia:
 *
 * 1. Java DataBase Connectivity (JDBC)
 *
 * 2. JPA (Java Persistence API) Especificación
 *      Implementaciones:
 *      - Hibernate (ORM Object Relational Mapping)
 *      - EclipseLink
 *
 * 3. Spring Data JPA
 *
 * CRUD - Create Retrieve Update Delete
 */
public class JDBC {

    private static final String SQL_QUERY = "SELECT * FROM taller.persona";
    private static final String URL_TALLER = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static void main(String[] args) {

        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // 1. crear conexion
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/taller",
                    "root", "admin");

            // 2. Sentencia
            statement = conexion.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM coches;");

            // 3. Procesar resultados
            List<Coche> cars = new ArrayList<>();
            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String modelo = resultSet.getString("modelo");
                String fabricante = resultSet.getString("fabricante");
                Integer numCilindros = resultSet.getInt("num_cilindros");
                Double numCV = resultSet.getDouble("num_cv");
                Coche coche = new Coche(
                        id, modelo, fabricante, numCilindros, numCV);
                cars.add(coche);
            }


        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) { e.printStackTrace();}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if (conexion != null) conexion.close(); } catch (Exception e) {}
        }



    }
}
