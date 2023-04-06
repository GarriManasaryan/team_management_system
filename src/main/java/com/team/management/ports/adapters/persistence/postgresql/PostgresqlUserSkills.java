package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.responsibility.UserResponsibility;
import com.team.management.domain.responsibility.UserResponsibilityRepository;
import com.team.management.domain.skill.UserSkillRepository;
import com.team.management.domain.skill.UserSkills;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostgresqlUserSkills implements UserSkillRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlUserSkills(DataSource dataSource){
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addUserSkill(@NotNull String userId, @NotNull String skillId) {
        var sqlTemplate = """
                insert into tms_user_skills
                (user_id, skill_id)
                 values
                (:user_id, :skill_id)
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("skill_id", skillId);

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void removeUserSkill(@NotNull String userId, @NotNull String skillId) {
        var sqlTemplate = """
                delete from tms_user_skills
                where user_id = :user_id and skill_id = :skill_id
                """;
        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("skill_id", skillId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public void deleteAllUserSkill(@NotNull String userId) {
        var sqlTemplate = """
                delete from tms_user_skills
                where user_id = :user_id
                """;
        var queryParams = new MapSqlParameterSource()
                .addValue("user_id", userId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    public static RowMapper<UserSkills> asUserSkillRowMapper(){
        return (rs, rowNum) -> new UserSkills(
                rs.getString("user_id"),
                rs.getString("skill_id")
        );
    }

    @Override
    public List<UserSkills> search(@Nullable String userId, @Nullable String skillId) {

        if (userId == null && skillId == null){
            var sqlTemplate = """
                select * from tms_user_skills
                """;

            return jdbcOperations.query(sqlTemplate, new MapSqlParameterSource(), asUserSkillRowMapper());
        }

        else if (userId != null && skillId != null){
            var sqlTemplate = """
                select * from tms_user_skills
                 where user_id = :user_id and skill_id = :skill_id
                """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("user_id", userId)
                    .addValue("skill_id", skillId);

            return jdbcOperations.query(sqlTemplate, queryParams, asUserSkillRowMapper());

        }

        else{
            throw new IllegalStateException("Both userId and skillId have to be provided");
        }

    }
}
