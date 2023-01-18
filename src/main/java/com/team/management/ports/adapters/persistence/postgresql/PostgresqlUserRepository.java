package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.user.Role;
import com.team.management.domain.user.User;
import com.team.management.domain.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostgresqlUserRepository implements UserRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlUserRepository(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
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
