package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.capacity.CapacityEater;
import com.team.management.domain.capacity.CapacityEaterRepository;
import com.team.management.domain.capacity.CapacityEaterStatus;
import com.team.management.domain.capacity.CapacityPriority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public class PostgresqlCapacityEater implements CapacityEaterRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlCapacityEater(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<CapacityEater> asCapacityEaterMapper() {
        return (rs, rowNum) -> new CapacityEater(
                rs.getString("id"),
                rs.getString("name"),
                rs.getInt("duration"),
                rs.getString("type_id"),
                CapacityPriority.valueOf(rs.getString("priority")),
                rs.getObject("deadline", OffsetDateTime.class),
                CapacityEaterStatus.stringToEnum(rs.getString("status"))
        );
    }

    @Override
    public void save(@NotNull CapacityEater capacityEater) {
        var sqlTemplate = """
                insert into tms_capacity_eater
                (id, name, type_id, priority, deadline, duration, status)
                values
                (:id, :name, :type_id, :priority, :deadline::timestamp with time zone, :duration, :status)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", capacityEater.id())
                .addValue("name", capacityEater.name())
                .addValue("type_id", capacityEater.typeId())
                .addValue("priority", capacityEater.priority().name())
                .addValue("deadline", new Timestamp(capacityEater.deadline().toEpochSecond() * 1000))
                .addValue("duration", capacityEater.duration())
                .addValue("status", CapacityEaterStatus.EnumToString(capacityEater.status()));

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public void delete(@NotNull String id) {
        var sqlTemplate = """
                delete from tms_capacity_eater
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
            @Nullable Integer duration,
            @Nullable String typeId,
            @Nullable CapacityPriority priority,
            @Nullable OffsetDateTime deadline,
            @Nullable CapacityEaterStatus status
    ) {
        HashMap<String, Object> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("duration", duration);
        allParams.put("type_id", typeId);
        allParams.put("priority", priority != null ? priority.name() : null);
        allParams.put("deadline", deadline);
        allParams.put("status", status != null ? CapacityEaterStatus.EnumToString(status) : null);

        if (allParams.values().stream().allMatch(Objects::isNull)) {
            throw new IllegalStateException("No params passed to patch");
        } else {
            var sqlTemplate = """
                    update tms_capacity_eater
                    set all_set_values
                    where id = :id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", id);

            var basicSetStatement = "column_name = 'column_value'";
            List<String> allSetStatements = new ArrayList<>();

            for (Map.Entry<String, Object> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var setStatement = basicSetStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue().toString());
                    allSetStatements.add(setStatement);
                }
            }

            sqlTemplate = sqlTemplate.replaceAll("all_set_values", String.join(", ", allSetStatements));

            jdbcOperations.update(sqlTemplate, queryParams);

        }

    }

    @Override
    public List<CapacityEater> search(
            @Nullable String name,
            @Nullable Integer duration,
            @Nullable String typeId,
            @Nullable CapacityPriority priority,
            @Nullable OffsetDateTime deadline,
            @Nullable CapacityEaterStatus status
    ) {

        HashMap<String, Object> allParams = new HashMap<>();
        allParams.put("name", name);
        allParams.put("duration", duration);
        allParams.put("type_id", typeId);
        allParams.put("priority", priority != null ? priority.name() : null);
        allParams.put("deadline", deadline);
        allParams.put("status", status != null ? CapacityEaterStatus.EnumToString(status) : null);

        var sqlTemplate = """
                select * from tms_capacity_eater
                """;

        var queryParams = new MapSqlParameterSource();

        if (!allParams.values().stream().allMatch(Objects::isNull)) {
            var basicWhereStatement = "column_name = 'column_value'";
            List<String> allWhereStatements = new ArrayList<>();

            for (Map.Entry<String, Object> entry : allParams.entrySet()) {
                if (entry.getValue() != null) {
                    var whereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_value", entry.getValue() instanceof OffsetDateTime ? entry.getValue() + "::timestamp with time zone" : entry.getValue().toString());
                    allWhereStatements.add(whereStatement);
                }
            }

            sqlTemplate = sqlTemplate + " where " + String.join(" and ", allWhereStatements);

        }

        return jdbcOperations.query(sqlTemplate, queryParams, asCapacityEaterMapper());

    }

    @Override
    public Optional<CapacityEater> ofId(@NotNull String id) {
        var sqlTemplate = """
                select * from tms_capacity_eater
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcOperations.query(sqlTemplate, queryParams, asCapacityEaterMapper()).stream().findFirst();

    }
}
