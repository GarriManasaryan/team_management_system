package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.sprint.ActualSprint;
import com.team.management.domain.sprint.ActualSprintRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class PostgresqlActualSprint implements ActualSprintRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlActualSprint(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<ActualSprint> asActualSprint(){
        return  (rs, rowNum) -> new ActualSprint(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("planning_id"),
                rs.getObject("start_at", OffsetDateTime.class)
                );
    }

    @Override
    public void save(@NotNull ActualSprint actualSprint) {
        var sqlTemplate = """
                insert into tms_actual_sprint
                (id, name, planning_id, start_at)
                values
                (:id,:name,:planning_id,:start_at)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", actualSprint.id())
                .addValue("name", actualSprint.name())
                .addValue("planning_id", actualSprint.planningId())
                .addValue("start_at", actualSprint.startAt());

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void delete(@NotNull String id) {
        var sqlTemplate = """
                delete from tms_actual_sprint
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void patch(@NotNull String id, @Nullable String name, @Nullable String planningId, @Nullable OffsetDateTime startAt) {
        HashMap<String, Object> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("planning_id", planningId);
        allParams.put("start_at", startAt);

        if (allParams.values().stream().allMatch(Objects::isNull)){
            throw new IllegalStateException("No params passed to patch");
        }
        else{
            var sqlTemplate = """
                    update tms_actual_sprint
                    set all_set_statements
                    where id = :id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", id);

            var basicSetStatement = "column_name = 'column_value'";

            List<String> allSetStatements = new ArrayList<>();

            for (Map.Entry<String, Object> entry : allParams.entrySet()){
                if (entry.getValue() != null){
                    var setStatement = basicSetStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue().toString());
                    allSetStatements.add(setStatement);
                }
            }

            sqlTemplate = sqlTemplate.replaceAll("all_set_statements", String.join(", ", allSetStatements));

            jdbcOperations.update(sqlTemplate, queryParams);

        }
    }

    @Override
    public List<ActualSprint> search(@Nullable String name, @Nullable String planningId, @Nullable OffsetDateTime startAt) {
        HashMap<String, Object> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("planning_id", planningId);
        allParams.put("start_at", startAt);

        var sqlTemplate = """
                select * from tms_actual_sprint
                """;

        var queryParams = new MapSqlParameterSource();

        if (!allParams.values().stream().allMatch(Objects::isNull)){
            var basicWhereStatement = "column_name = 'column_value'";
            List<String> allWhereStatements = new ArrayList<>();

            for (Map.Entry<String, Object> entry : allParams.entrySet()){
                if (entry.getValue() != null){
                    var whereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue().toString());
                    allWhereStatements.add(whereStatement);
                }
            }

            sqlTemplate = sqlTemplate + " where " + String.join(" and ", allWhereStatements);

        }

        return jdbcOperations.query(sqlTemplate, queryParams, asActualSprint());
    }

    @Override
    public Optional<ActualSprint> ofId(@NotNull String id) {
        var sqlTemplate = """
                select * from tms_actual_sprint
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcOperations.query(sqlTemplate, queryParams, asActualSprint()).stream().findFirst();

    }
}
