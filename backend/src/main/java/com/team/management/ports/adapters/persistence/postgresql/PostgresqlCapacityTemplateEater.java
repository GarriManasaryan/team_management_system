package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.capacity.CapacityTemplateEater;
import com.team.management.domain.capacity.CapacityTemplateEaterRepository;
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
public class PostgresqlCapacityTemplateEater implements CapacityTemplateEaterRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlCapacityTemplateEater(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    private static RowMapper<CapacityTemplateEater> asCapacityTemplateEaterMapper() {
        return (rs, rowNum) -> new CapacityTemplateEater(
                rs.getString("capacity_template_id"),
                rs.getString("capacity_eater_id")
        );
    }

    @Override
    public void saveTemplateEater(@NotNull String capacityTemplateId, @NotNull String capacityEaterId) {
        var sqlTemplate = """
                insert into tms_capacity_template_eaters
                values
                (:capacity_template_id, :capacity_eater_id)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("capacity_template_id", capacityTemplateId)
                .addValue("capacity_eater_id", capacityEaterId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public void removeEater(@NotNull String capacityTemplateId, @NotNull String capacityEaterId) {
        var sqlTemplate = """
                delete from tms_capacity_template_eaters
                where capacity_template_id = :capacity_template_id and capacity_eater_id = :capacity_eater_id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("capacity_template_id", capacityTemplateId)
                .addValue("capacity_eater_id", capacityEaterId);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void removeAllEatersByTemplateId(@NotNull String capacityTemplateId) {
        var sqlTemplate = """
                delete from tms_capacity_template_eaters
                where capacity_template_id = :capacity_template_id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("capacity_template_id", capacityTemplateId);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public List<CapacityTemplateEater> search(@Nullable String capacityTemplateId, @Nullable String capacityEaterId) {
        var sqlTemplate = """
                select * from tms_capacity_template_eaters
                where (capacity_template_id = :capacity_template_id or :capacity_template_id::varchar is null) 
                 and 
                (capacity_eater_id = :capacity_eater_id or :capacity_eater_id::varchar is null)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("capacity_template_id", capacityTemplateId)
                .addValue("capacity_eater_id", capacityEaterId);

        return jdbcOperations.query(sqlTemplate, queryParams, asCapacityTemplateEaterMapper());

    }

    @Override
    public Optional<CapacityTemplateEater> ofId(@NotNull String capacityTemplateId, @NotNull String capacityEaterId) {
        var sqlTemplate = """
                select * from tms_capacity_template_eaters
                where capacity_template_id = :capacity_template_id and capacity_eater_id = :capacity_eater_id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("capacity_template_id", capacityTemplateId)
                .addValue("capacity_eater_id", capacityEaterId);

        return jdbcOperations.query(sqlTemplate, queryParams, asCapacityTemplateEaterMapper()).stream().findFirst();
    }
}
