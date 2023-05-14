package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.capacity.CapacityTemplate;
import com.team.management.domain.capacity.CapacityTemplateRepository;
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
public class PostgresqlCapacityTemplate implements CapacityTemplateRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlCapacityTemplate(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<CapacityTemplate> asCapacityTemplateMapper() {
        return (rs, rowNum) -> new CapacityTemplate(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("description")
        );
    }

    @Override
    public void save(@NotNull CapacityTemplate capacityTemplate) {
        var sqlTemplate = """
                insert into tms_capacity_template
                 (id, title, description)
                values
                 (:id, :title, :description)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", capacityTemplate.id())
                .addValue("title", capacityTemplate.title())
                .addValue("description", capacityTemplate.description());

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void delete(@NotNull String id) {
        var sqlTemplate = """
                delete from tms_capacity_template
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void patch(@NotNull String id, @Nullable String title, @Nullable String description) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("title", title);
        allParams.put("description", description);

        if (allParams.values().stream().allMatch(Objects::isNull)){
            throw new IllegalStateException("No params to patch");
        }

        else{
            var sqlTemplate = """
                    update tms_capacity_template
                    set all_set_statements
                    where id = :id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", id);

            var basicSetStatement = "column_name = 'column_value'";
            List<String> allSetStatements = new ArrayList<>();

            for(Map.Entry<String, String> entry : allParams.entrySet()){
                if (entry.getValue() != null){
                    var setStatement = basicSetStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue());
                    allSetStatements.add(setStatement);
                }
            }

            sqlTemplate = sqlTemplate.replaceAll("all_set_statements", String.join(", ", allSetStatements));

            jdbcOperations.update(sqlTemplate, queryParams);

        }

    }

    @Override
    public List<CapacityTemplate> search(@Nullable String title, @Nullable String description) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("title", title);
        allParams.put("description", description);

        var sqlTemplate = """
                select * from tms_capacity_template
                """;

        if (!allParams.values().stream().allMatch(Objects::isNull)) {
            var basicWhereStatement = "column_name = 'column_value'";
            List<String> allWhereStatements = new ArrayList<>();

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var whereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue());
                    allWhereStatements.add(whereStatement);
                }
            }

            sqlTemplate = sqlTemplate + " where " + String.join(" and ", allWhereStatements);

        }

        return jdbcOperations.query(sqlTemplate, new MapSqlParameterSource(), asCapacityTemplateMapper());
    }

    @Override
    public Optional<CapacityTemplate> ofId(@NotNull String id) {
        var sqlTemplate = """
                select * from tms_capacity_template 
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcOperations.query(sqlTemplate, queryParams, asCapacityTemplateMapper()).stream().findFirst();
    }
}
