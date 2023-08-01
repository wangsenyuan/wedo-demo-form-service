package com.wedo.demo.domain.form.repository;

import com.wedo.demo.domain.form.FormType;
import com.wedo.demo.domain.form.entity.FormEntity;
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

@Repository
public class FormRepository {
    private static final String INSERT_FORM = "insert into form_record" + "( form_type, form_version, form_content, created_by, updated_by) " + "values( ?, ?, ?, ?, ?) returning id";

    private static final String UPDATE_FORM = "update form_record set form_version = ?, form_content = ?, updated_by = ?, updated_at = now() where id = ?";

    private static final String SELECT_BY_ID = "select * from form_record where id = ?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(FormEntity entity) {
        if (entity.getId() == null) {
            return createNew(entity);
        }
        return updateExisting(entity);
    }

    private Long createNew(FormEntity entity) {

        return jdbcTemplate.execute(INSERT_FORM, new PreparedStatementCallback<Long>() {
            @Override
            public Long doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, entity.getFormType().name());
                ps.setString(2, entity.getFormVersion());
                ps.setString(3, entity.getFormContent());
                ps.setString(4, entity.getCreatedBy());
                ps.setString(5, entity.getCreatedBy());

                ResultSet res = ps.executeQuery();

                if (res.next()) {
                    return res.getLong(1);
                }

                return null;
            }
        });
    }

    private Long updateExisting(FormEntity entity) {
        jdbcTemplate.execute(UPDATE_FORM, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, entity.getFormVersion());
                ps.setString(2, entity.getFormContent());
                ps.setString(3, entity.getUpdatedBy());
                ps.setLong(4, entity.getId());
                return ps.execute();
            }
        });


        return entity.getId();
    }

    public FormEntity get(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        }, new ResultSetExtractor<FormEntity>() {
            @Override
            public FormEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (!rs.next()) {
                    return null;
                }
                FormEntity entity = new FormEntity();
                entity.setId(rs.getLong("id"));
                entity.setFormVersion(rs.getString("form_version"));
                entity.setFormType(FormType.valueOf(rs.getString("form_type").toUpperCase()));
                entity.setFormContent(rs.getString("form_content"));
                return entity;
            }
        });
    }
}
