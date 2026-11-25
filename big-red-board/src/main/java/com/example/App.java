package com.example;

import io.javalin.Javalin;
import com.google.gson.Gson;
import java.sql.*;
import java.util.*;

public class App {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/big_red_board";
    static final String DB_USER = "postgres";
    static final String DB_PASS = System.getenv("DB_PASS");

    public static void main(String[] args) {
        Gson gson = new Gson();
        Javalin app = Javalin.create(config -> {
        config.staticFiles.add("/public");
        }).start(7070);

        app.get("/api/recruits", ctx -> {
            List<Map<String, Object>> board = new ArrayList<>();
            String sql = """
                SELECT r.recruit_id, r.first_name, r.last_name, r.position, 
                       r.state, r.grad_class, r.high_school, o.status, o.is_committed
                FROM recruits r
                JOIN offers o ON r.recruit_id = o.recruit_id
                JOIN schools s ON o.school_id = s.school_id
                WHERE s.abbreviation = 'NEB'
                ORDER BY o.is_committed DESC, r.grad_class ASC, r.last_name ASC
            """;

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id", rs.getInt("recruit_id"));
                    row.put("name", rs.getString("first_name") + " " + rs.getString("last_name"));
                    row.put("position", rs.getString("position"));
                    row.put("state", rs.getString("state"));
                    row.put("gradClass", rs.getInt("grad_class"));
                    row.put("highSchool", rs.getString("high_school"));
                    row.put("status", rs.getString("status"));
                    row.put("committed", rs.getBoolean("is_committed"));
                    board.add(row);
                }
            }
            ctx.contentType("application/json").result(gson.toJson(board));
        });
    }
}
