package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.planning.SprintPlanningParticipantCapacity;
import com.team.management.domain.planning.SprintPlanningParticipantCapacityRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgresqlSprintPlanningParticipantCapacity implements SprintPlanningParticipantCapacityRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlSprintPlanningParticipantCapacity(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<SprintPlanningParticipantCapacity> asSprintPlanningParticipantCapacity() {
        return (rs, rowNum) -> new SprintPlanningParticipantCapacity(
                rs.getString("user_id"),
                rs.getString("sprint_planning_id"),
                rs.getString("capacity_eater_id"),
                rs.getInt("duration")
        );
    }

    @Override
    public void save(@NotNull String userId, @NotNull String planningId, @NotNull String capacityEaterId, @NotNull Integer duration) {
        var sqlTemplate = """
                insert into tms_sprint_planning_participant_capacity
                values
                (:user_id,:sprint_planning_id,:capacity_eater_id,:duration)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("sprint_planning_id", planningId)
                .addValue("capacity_eater_id", capacityEaterId)
                .addValue("duration", duration);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void delete(@NotNull String userId, @NotNull String planningId, @NotNull String capacityEaterId) {
        var sqlTemplate = """
                delete from tms_sprint_planning_participant_capacity
                where
                 user_id = :user_id and
                 sprint_planning_id = :sprint_planning_id and
                 capacity_eater_id = :capacity_eater_id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("sprint_planning_id", planningId)
                .addValue("capacity_eater_id", capacityEaterId);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public List<SprintPlanningParticipantCapacity> all() {
        var sqlTemplate = """
                select * from tms_sprint_planning_participant_capacity
                """;

        return jdbcOperations.query(sqlTemplate, new MapSqlParameterSource(), asSprintPlanningParticipantCapacity());
    }

    @Override
    public Optional<SprintPlanningParticipantCapacity> ofId(@NotNull String userId, @NotNull String planningId, @NotNull String capacityEaterId) {
        var sqlTemplate = """
                select * from tms_sprint_planning_participant_capacity
                where
                 user_id = :user_id and
                 sprint_planning_id = :sprint_planning_id and
                 capacity_eater_id = :capacity_eater_id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("sprint_planning_id", planningId)
                .addValue("capacity_eater_id", capacityEaterId);

        return jdbcOperations.query(sqlTemplate, queryParams, asSprintPlanningParticipantCapacity()).stream().findAny();
    }
}
