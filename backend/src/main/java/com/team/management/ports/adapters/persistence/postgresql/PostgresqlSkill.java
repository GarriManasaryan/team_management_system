package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.skill.Skill;
import com.team.management.domain.skill.SkillRepository;
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
public class PostgresqlSkill implements SkillRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlSkill(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(@NotNull Skill skill) {
        var sqlTemplate = """
                insert into tms_skills
                 values(:id, :name)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", skill.id())
                .addValue("name", skill.name());

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public void rename(@NotNull String skillId, @NotNull String newName) {
        var sqlTemplate = """
                update tms_skills
                set name = :name
                where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("name", newName)
                .addValue("id", skillId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public void delete(@NotNull String skillId) {
        var sqlTemplate = """
                delete from tms_skills
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", skillId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public List<Skill> search(@Nullable String name) {
        var sqlTemplate = """
                select * from tms_skills
                where name = :name::varchar or :name::varchar is null
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("name", name);

        return jdbcOperations.query(sqlTemplate, queryParams, asSkillMapper());

    }

    private RowMapper<Skill> asSkillMapper(){
        return (rs, rowNum) -> new Skill(
                rs.getString("id"),
                rs.getString("name")
        );
    }

    @Override
    public Optional<Skill> ofId(@NotNull String skillId) {
        var sqlTemplate = """
                select * from tms_skills
                """;

        var queryParams = new MapSqlParameterSource();

        return jdbcOperations.query(sqlTemplate, queryParams, asSkillMapper()).stream().findFirst();

    }
}
