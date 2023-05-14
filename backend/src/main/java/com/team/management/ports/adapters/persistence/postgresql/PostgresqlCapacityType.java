package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.capacity.CapacityType;
import com.team.management.domain.capacity.CapacityTypeRepository;
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
public class PostgresqlCapacityType implements CapacityTypeRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlCapacityType(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<CapacityType> asCapacityType() {
        return (rs, rowNum) -> new CapacityType(
                rs.getString("id"),
                rs.getString("name"),
                rs.getInt("default_duration")
        );
    }

    @Override
    public void save(@NotNull CapacityType capacityType) {
        var sqlTemplate = """
                insert into tms_task_types
                (id, name, default_duration)
                values
                (:id, :name, :default_duration)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", capacityType.id())
                .addValue("name", capacityType.name())
                .addValue("default_duration", capacityType.defaultDuration());

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void delete(@NotNull String id) {
        var sqlTemplate = """
                delete from tms_task_types
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void patch(@NotNull String id, @Nullable String name, @Nullable Integer defaultDuration) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("default_duration", defaultDuration != null ? defaultDuration.toString() : null);

        if (allParams.values().stream().allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("No params passed to patch");
        } else {
            var sqlTemplate = """
                    update tms_task_types
                    set all_set_statements
                    where id = :id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", id);

            var basicSetStatement = "column_name = 'column_value'";
            List<String> allSetStatements = new ArrayList<>();

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var setStatement = basicSetStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue());
                    allSetStatements.add(setStatement);

                }

            }

            sqlTemplate = sqlTemplate.replaceAll("all_set_statements", String.join(", ", allSetStatements));

            jdbcOperations.update(sqlTemplate, queryParams);

        }
    }

    @Override
    public List<CapacityType> search(@Nullable String name, @Nullable Integer defaultDuration) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("default_duration", defaultDuration != null ? defaultDuration.toString() : null);

        var sqlTemplate = """
                select * from tms_task_types
                """;

        var queryParams = new MapSqlParameterSource();

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
        return jdbcOperations.query(sqlTemplate, queryParams, asCapacityType());
    }

    @Override
    public Optional<CapacityType> ofId(@NotNull String id) {
        var sqlTemplate = """
                select * from tms_task_types
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcOperations.query(sqlTemplate, queryParams, asCapacityType()).stream().findFirst();
    }
}
