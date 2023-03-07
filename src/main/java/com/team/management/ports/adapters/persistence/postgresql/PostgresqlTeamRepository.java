package com.team.management.ports.adapters.persistence.postgresql;

import com.team.management.domain.team.Team;
import com.team.management.domain.team.TeamRepository;
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
public class PostgresqlTeamRepository implements TeamRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public PostgresqlTeamRepository(DataSource dataSource) {
        this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void delete(@NotNull String teamId) {
        var sqlTemplate = """
                delete from tms_teams
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", teamId);

        jdbcOperations.update(sqlTemplate, queryParams);
    }

    @Override
    public void save(@NotNull Team team){
        var sqlTemplate = """
                insert into tms_teams
                 values (:id, :name, :manager_id)
                """;
        var queryParams = new MapSqlParameterSource()
                .addValue("id", team.id())
                .addValue("name", team.name())
                .addValue("manager_id", team.managerId());

        jdbcOperations.update(sqlTemplate, queryParams);

    }

    @Override
    public void patch(@NotNull String teamId, @Nullable String newManagerId, @Nullable String newName) {
        HashMap<String, String> allParamsMap = new HashMap<>();
        allParamsMap.put("manager_id", newManagerId);
        allParamsMap.put("name", newName);

        if (allParamsMap.values().stream().allMatch(Objects::isNull)) {
            throw new IllegalStateException("No params passed for patching");
        } else {
            var sqlTemplate = """
                    update tms_teams
                     set set_statements_joined
                     where id = :id
                    """;

            var queryParams = new MapSqlParameterSource()
                    .addValue("id", teamId);

            String basicWhereStatement = "column_name = 'column_new_value'";
            List<String> allWhereStatements = new ArrayList<>();
            for (Map.Entry<String, String> entry : allParamsMap.entrySet()) {
                if (entry.getValue() != null) {
                    var whereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("column_new_value", entry.getValue());
                    allWhereStatements.add(whereStatement);
                }
            }

            sqlTemplate = sqlTemplate.replaceAll("set_statements_joined", String.join(", ", allWhereStatements));

            jdbcOperations.update(sqlTemplate, queryParams);

        }


    }

    @Override
    public List<Team> search(@Nullable String name, @Nullable String managerId) {
        HashMap<String, String> allParamsMap = new HashMap<>();
        allParamsMap.put("name", name);
        allParamsMap.put("manager_id", managerId);

        var sqlTemplate = """
                select * from tms_teams
                """;

        var queryParams = new MapSqlParameterSource();

        if (! allParamsMap.values().stream().allMatch(Objects::isNull)) {

            String basicWhereStatement = "column_name like '%search_value%'";

            List<String> allWhereStatements = new ArrayList<>();

            for (Map.Entry<String, String> entry : allParamsMap.entrySet()) {
                if (entry.getValue() != null) {
                    var singleWhereStatement = basicWhereStatement.replaceAll("column_name", entry.getKey()).replaceAll("search_value", entry.getValue());
                    allWhereStatements.add(singleWhereStatement);
                }
            }

            sqlTemplate = sqlTemplate + " where " + String.join(" and ", allWhereStatements);

        }

        return jdbcOperations.query(sqlTemplate, queryParams, asTeamMapper());

    }

    public static RowMapper<Team> asTeamMapper(){
        return (rs, rowNum) -> new Team(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("manager_id")
        );
    }

    @Override
    public Optional<Team> ofId(@NotNull String teamId) {
        var sqlTemplate = """
                select * from tms_teams
                 where id = :id
                """;

        var queryParams = new MapSqlParameterSource()
                .addValue("id", teamId);

        return jdbcOperations.query(sqlTemplate, queryParams, asTeamMapper()).stream().findFirst();

    }
}
