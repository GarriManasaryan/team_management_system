package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.responsibility.UserResponsibility;
import com.team.management.domain.responsibility.UserResponsibilityRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgresqlUserResponsibility implements UserResponsibilityRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlUserResponsibility(DataSource dataSource){
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addUserResponsibility(@NotNull String userId, @NotNull String respId) {
        var sqlTemplate = """
                insert into tms_user_responsibilities
                (user_id, resp_id)
                 values
                (:user_id, :resp_id)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("resp_id", respId);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void removeUserResponsibility(@NotNull String userId, @NotNull String respId) {
        var sqlTemplate = """
                delete from tms_user_responsibilities
                where user_id = :user_id and resp_id = :resp_id
                """;
        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("resp_id", respId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public void deleteAllUserResponsibility(@NotNull String userId) {
        var sqlTemplate = """
                delete from tms_user_responsibilities
                where user_id = :user_id
                """;
        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    public static RowMapper<UserResponsibility> asUserResponsibilityRowMapper(){
        return (rs, rowNum) -> new UserResponsibility(
                rs.getString("user_id"),
                rs.getString("resp_id")
        );
    }

    @Override
    public List<UserResponsibility> search(@Nullable String userId, @Nullable String respId) {

        if (userId == null && respId == null){
            var sqlTemplate = """
                select * from tms_user_responsibilities
                """;
            return jdbcOperations.query(sqlTemplate, new MapSqlParameterSource(), asUserResponsibilityRowMapper());
        }

        else if (userId != null && respId != null){
            var sqlTemplate = """
                select * from tms_user_responsibilities
                 where user_id = :user_id and resp_id = :resp_id
                """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("user_id", userId)
                    .addValue("resp_id", respId);

            return jdbcOperations.query(sqlTemplate, queryParams, asUserResponsibilityRowMapper());

        }

        else{
            throw new IllegalStateException("Both userId and respId have to be provided");
        }

    }
}
