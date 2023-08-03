package com.wedo.demo.domain.process.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedo.demo.domain.process.ProcessState;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class ProcessInstanceRepository {
    @Autowired
    private JdbcTemplate template;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static class SQL {

        static final String SELECT = "select * from process_instance where id = ?";
        static final String INSERT = "insert into process_instance(id, process_code, state, form_values, created_at, created_by, updated_at, updated_by) values(?, ?, ?, ?, now(), ?, now(), ?)";

        static final String UPDATE = "update process_instance set state = ?, form_values = ?, process_key = ?, updated_at = now(), updated_by = ? where id = ?";
    }

    public void save(ProcessInstanceEntity entity) {
        ProcessInstanceEntity old = get(entity.getId());
        if (old == null) {
            createNew(entity);
            return;
        }
        old.copyValue(entity);
        updateExisting(old);
    }

    private ProcessInstanceEntity get(Long id) {
        return template.query(SQL.SELECT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        }, new ResultSetExtractor<ProcessInstanceEntity>() {
            @Override
            public ProcessInstanceEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {

                    ProcessInstanceEntity entity = new ProcessInstanceEntity();
                    entity.setId(id);
                    entity.setState(ProcessState.valueOf(rs.getString("state")));
                    entity.setProcessCode(rs.getString("process_code"));
                    entity.setProcessKey(rs.getString("process_key"));
                    entity.setFormValues(parseJson(rs.getString("form_values")));

                    return entity;
                }
                return null;
            }
        });
    }

    private void updateExisting(ProcessInstanceEntity entity) {
        template.execute(SQL.UPDATE, new PreparedStatementCallback<Void>() {
            @Override
            public Void doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, entity.getState().name());
                ps.setString(2, formatJson(entity.getFormValues()));
                ps.setString(3, entity.getProcessKey());
                ps.setString(4, entity.getUpdatedBy());
                ps.setLong(5, entity.getId());
                ps.executeUpdate();
                return null;
            }
        });
    }

    private void createNew(ProcessInstanceEntity entity) {
        template.execute(SQL.INSERT, new PreparedStatementCallback<Void>() {
            @Override
            public Void doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setLong(1, entity.getId());
                ps.setString(2, entity.getProcessCode());
                ps.setString(3, ProcessState.NEW.name());
                ps.setString(4, formatJson(entity.getFormValues()));
                ps.setString(5, entity.getCreatedBy());
                ps.setString(6, entity.getCreatedBy());
                ps.executeUpdate();
                return null;
            }
        });
    }

    private static String formatJson(Map<String, String> formValues) {
        if (formValues == null) {
            return "";
        }
        try {
            return objectMapper.writeValueAsString(formValues);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, String> parseJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
