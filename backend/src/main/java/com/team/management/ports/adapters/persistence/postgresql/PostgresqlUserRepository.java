package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.user.Role;
import com.team.management.domain.user.User;
import com.team.management.domain.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class PostgresqlUserRepository implements UserRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlUserRepository(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void replaceEmail(@NotNull String email, @NotNull String id) {
        var sqlTemplate = """
                update tms_users
                 set email = :email
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void replaceRole(@NotNull Role role, @NotNull String id) {
        var sqlTemplate = """
                update tms_users
                 set role = :role
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("role", role.name())
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public @NotNull Optional<User> ofId(@NotNull String userId) {
        var sqlTemplate = """
                select * from tms_users
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", userId);

        var searchResults = jdbcOperations.query(sqlTemplate, queryParams, asUserMapper());

        return searchResults.stream().findFirst();
    }

    @Override
    public void patch(
            @NotNull String id,
            @Nullable String role,
            @Nullable String email,
            @Nullable String name
    ) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("role", role);
        allParams.put("email", email);
        allParams.put("name", name);

        if (allParams.values().stream().allMatch(Objects::isNull)) {
            throw new IllegalStateException("No parameters passed");
        } else {

            var sqlTemplate = """
                    update tms_users
                     set values_to_set_statements
                     where id = :id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", id);

            List<String> whereStatementsToAdd = new ArrayList<>();
            var basicWhereStatement = "column_name = 'column_new_value'";

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var whereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_new_value", entry.getValue());
                    whereStatementsToAdd.add(whereStatement);
                }
            }

            var joinedWhereStatements = String.join(", ", whereStatementsToAdd);
            sqlTemplate = sqlTemplate.replaceAll("values_to_set_statements", joinedWhereStatements);

            jdbcOperations.update(sqlTemplate, queryParams);
        }
    }

    @Override
    public @NotNull List<User> search(
            @Nullable String role,
            @Nullable String email,
            @Nullable String name
    ) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("role", role);
        allParams.put("email", email);
        allParams.put("name", name);

        if (allParams.values().stream().allMatch(Objects::isNull)) {
            return all();
        } else {

            var sqlTemplate = """
                    select * from tms_users
                     where 
                    """;

            var queryParams = new MapSqlParameterSource();

            List<String> whereStatementsToAdd = new ArrayList<>();
            var basicWhereStatement = "column_name like '%search_value%'";

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var whereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("search_value", entry.getValue());
                    whereStatementsToAdd.add(whereStatement);
                }
            }

            var joinedWhereStatements = String.join(" and ", whereStatementsToAdd);
            sqlTemplate = sqlTemplate + joinedWhereStatements;

            return jdbcOperations.query(sqlTemplate, queryParams, asUserMapper());
        }
    }

    @Override
    public @NotNull List<User> searchByEmail(@NotNull String email) {
        var sqlTemplate = """
                select * from tms_users
                 where email = :email
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("email", email);

        return jdbcOperations.query(sqlTemplate, queryParams, asUserMapper());
    }

    @Override
    public @NotNull List<User> searchByRole(@NotNull Role role) {
        var sqlTemplate = """
                select * from tms_users
                 where role = :role
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("role", role.name());

        return jdbcOperations.query(sqlTemplate, queryParams, asUserMapper());
    }

    @Override
    public void delete(@NotNull String id) {
        var sqlTemplate = """
                delete from tms_users
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public @NotNull String save(@NotNull User user) {
        var sqlTemplate = """
                insert into tms_users
                  (id, name, email, role)
                values
                  (:id, :name, :email, :role)
                """;
        var queryParams = new MapSqlParameterSource()
                .addValue("id", user.id())
                .addValue("name", user.name())
                .addValue("email", user.email())
                .addValue("role", user.role().name());

        jdbcOperations.update(sqlTemplate, queryParams);
        // именно тут возвращать, ибо если вернуть в сервисе id, на этапе jdbcOperations.update
        // может все упасть, а мы вернем в сервисе, мол все ок
        return user.id();
    }

    @Override
    public @NotNull List<User> all() {
        var sqlTemplate = """
                select * from tms_users
                """;
        var queryParams = new MapSqlParameterSource();

        return jdbcOperations.query(sqlTemplate, queryParams, asUserMapper());

    }

    private RowMapper<User> asUserMapper() {
        return (rs, rowNum) -> new User(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                switch (rs.getString("role")) {
                    case "MANAGER" -> Role.MANAGER;
                    case "TEAM_LEAD" -> Role.TEAM_LEAD;
                    case "EMPLOYEE" -> Role.EMPLOYEE;
                    default -> Role.EMPLOYEE;
                }
        );
    }
}
