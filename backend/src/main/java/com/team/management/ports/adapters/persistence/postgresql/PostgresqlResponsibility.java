package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.responsibility.Responsibility;
import com.team.management.domain.responsibility.ResponsibilityRepository;
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
public class PostgresqlResponsibility implements ResponsibilityRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlResponsibility(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<Responsibility> asRespMapper() {
        return (rs, rowNum) -> new Responsibility(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("outer_id"),
                rs.getString("label")
        );
    }

    @Override
    public void save(@NotNull Responsibility responsibility) {
        var sqlTemplate = """
                insert into tms_responsibility
                 values
                 (:id, :name, :outer_id, :label) 
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", responsibility.id())
                .addValue("name", responsibility.name())
                .addValue("outer_id", responsibility.outerId())
                .addValue("label", responsibility.label());

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void delete(@NotNull String respId) {
        var sqlTemplate = """
                delete from tms_responsibility
                where :id = id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", respId);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void patch(@NotNull String respId, @Nullable String newName, @Nullable String newOuterId, @Nullable String newLabel) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("name", newName);
        paramsMap.put("outer_id", newOuterId);
        paramsMap.put("label", newLabel);

        if (paramsMap.values().stream().allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("No params passed to patch");
        } else {

            var sqlTemplate = """
                    update tms_responsibility
                    set all_set_statements
                    where :id = id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", respId);

            List<String> allSetStatements = new ArrayList<>();
            String standardSetStatement = "column_name = 'column_value'";

            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                if (entry.getValue() != null) {
                    var setStatement = standardSetStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue());
                    allSetStatements.add(setStatement);
                }
            }

            sqlTemplate = sqlTemplate.replaceAll("all_set_statements", String.join(", ", allSetStatements));
            jdbcOperations.update(sqlTemplate, queryParams);

        }

    }

    @Override
    public List<Responsibility> search(@Nullable String name, @Nullable String outerId, @Nullable String label) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("outer_id", outerId);
        allParams.put("label", label);

        var sqlTemplate = """
                select * from tms_responsibility
                """;
        var queryParams = new MapSqlParameterSource();

        if (allParams.values().stream().allMatch(Objects::isNull)) {
            return jdbcOperations.query(sqlTemplate, queryParams, asRespMapper());
        } else {

            var basicWhereStatement = "column_name = 'column_value'";
            List<String> allWhereStatements = new ArrayList<>();

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var whereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue());
                    allWhereStatements.add(whereStatement);
                }
            }

            sqlTemplate = sqlTemplate + " where " + String.join(" and ", allWhereStatements);

            return jdbcOperations.query(sqlTemplate, queryParams, asRespMapper());

        }

    }

    @Override
    public Optional<Responsibility> ofId(@NotNull String respId) {
        var sqlTemplate = """
                select * from tms_responsibility
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", respId);

        return jdbcOperations.query(sqlTemplate, queryParams, asRespMapper()).stream().findFirst();

    }
}
