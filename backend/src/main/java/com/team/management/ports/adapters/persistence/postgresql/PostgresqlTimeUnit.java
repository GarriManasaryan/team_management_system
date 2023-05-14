package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.timeunit.TimeUnit;
import com.team.management.domain.timeunit.TimeUnitRepository;
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
public class PostgresqlTimeUnit implements TimeUnitRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlTimeUnit(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<TimeUnit> asTimeUnitMapper() {
        return (rs, rowNum) -> new TimeUnit(
                rs.getString("id"),
                rs.getString("name"),
                rs.getInt("duration")

        );
    }

    @Override
    public void save(@NotNull TimeUnit timeUnit) {
        var sqlTemplate = """
                insert into tms_time_units 
                 (id, name, duration)
                 values
                 (:id, :name, :duration)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", timeUnit.id())
                .addValue("name", timeUnit.name())
                .addValue("duration", timeUnit.duration());

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void delete(@NotNull String id) {
        var sqlTemplate = """
                delete from tms_time_units 
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void patch(@NotNull String id, @Nullable String name, @Nullable Integer duration) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("duration", duration != null ? duration.toString() : null);

        if (allParams.values().stream().allMatch(Objects::isNull)) {
            throw new IllegalStateException("No params passed to patch");
        } else {
            var sqlTemplate = """
                    update tms_time_units
                    set values_to_set
                    where id = :id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", id);

            var basicSetStatement = "column_name = 'column_value'";
            List<String> allSetStatement = new ArrayList<>();

            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var setStatement = basicSetStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue());
                    allSetStatement.add(setStatement);

                }

            }

            sqlTemplate = sqlTemplate.replaceAll("values_to_set", String.join(", ", allSetStatement));

            jdbcOperations.update(sqlTemplate, queryParams);

        }

    }

    @Override
    public List<TimeUnit> search(@Nullable String name, @Nullable Integer duration) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("duration", duration != null ? duration.toString() : null);

        var sqlTemplate = """
                select * from tms_time_units
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

        return jdbcOperations.query(sqlTemplate, new MapSqlParameterSource(), asTimeUnitMapper());
    }

    @Override
    public Optional<TimeUnit> ofId(@NotNull String id) {
        var sqlTemplate = """
                select * from tms_time_units 
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcOperations.query(sqlTemplate, queryParams, asTimeUnitMapper()).stream().findFirst();
    }
}
