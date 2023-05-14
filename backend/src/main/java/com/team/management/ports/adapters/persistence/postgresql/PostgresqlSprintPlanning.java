package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.planning.SprintPlanning;
import com.team.management.domain.planning.SprintPlanningRepository;
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
public class PostgresqlSprintPlanning implements SprintPlanningRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlSprintPlanning(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<SprintPlanning> asSprintPlanningMapper() {
        return (rs, rowNum) -> new SprintPlanning(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("time_unit_id"),
                rs.getString("capacity_template_id"),
                rs.getInt("max_capacity")
        );
    }

    @Override
    public void save(@NotNull SprintPlanning sprintPlanning) {
        var sqlTemplate = """
                insert into tms_sprint_planning
                (id, name, time_unit_id, capacity_template_id, max_capacity)
                values
                (:id, :name, :time_unit_id, :capacity_template_id, :max_capacity)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", sprintPlanning.id())
                .addValue("name", sprintPlanning.name())
                .addValue("time_unit_id", sprintPlanning.timeUnitId())
                .addValue("capacity_template_id", sprintPlanning.capacityTemplateId())
                .addValue("max_capacity", sprintPlanning.maxCapacity());

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void delete(@NotNull String id) {
        var sqlTemplate = """
                delete from tms_sprint_planning
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void patch(
            @NotNull String id,
            @Nullable String name,
            @Nullable String timeUnitId,
            @Nullable String capacityTemplateId,
            @Nullable Integer maxCapacity
    ) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("time_unit_id", timeUnitId);
        allParams.put("capacity_template_id", capacityTemplateId);
        allParams.put("max_capacity", maxCapacity != null ? maxCapacity.toString() : null);

        if (allParams.values().stream().allMatch(Objects::isNull)) {
            throw new IllegalStateException("No params passed to patch");
        } else {
            var sqlTemplate = """
                    update tms_sprint_planning
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
    public List<SprintPlanning> search(@Nullable String name, @Nullable String timeUnitId, @Nullable String capacityTemplateId, @Nullable Integer maxCapacity) {
        HashMap<String, String> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("time_unit_id", timeUnitId);
        allParams.put("capacity_template_id", capacityTemplateId);
        allParams.put("max_capacity", maxCapacity != null ? maxCapacity.toString() : null);

        var sqlTemplate = """
                select * from tms_sprint_planning
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

        return jdbcOperations.query(sqlTemplate, queryParams, asSprintPlanningMapper());

    }

    @Override
    public Optional<SprintPlanning> ofId(@NotNull String id) {
        var sqlTemplate = """
                select * from tms_sprint_planning
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcOperations.query(sqlTemplate, queryParams, asSprintPlanningMapper()).stream().findFirst();

    }
}
