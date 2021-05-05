package com.db.extrato.repository.extract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReportTimeLimitRepositoryImpl {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Date checkUserReport(String username, Long clientId) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT MAX(STARTDATE) AS STARTDATE ");
		query.append(" FROM EXTRATO_APP.REPORT_TIME_LIMIT ");
		query.append(" WHERE USERNAME = '" + username + "' AND CLIENTID = '" + String.valueOf(clientId) + "' ");
		query.append(" ORDER BY STARTDATE ");
		List<Date> resultSet = jdbcTemplate
				.query(query.toString(), new RowMapper<Date>() {
						public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getTimestamp("STARTDATE");
						}
					})
				.stream()
				.collect(Collectors.toList());
		return CollectionUtils.isNotEmpty(resultSet) ? resultSet.get(0) : null;
	}
	
	public void insertReportTimeLimit(String username, Long clientId) {
		if(StringUtils.isBlank(username) || clientId == null)
			throw new IllegalArgumentException();
		String query = "INSERT INTO EXTRATO_APP.REPORT_TIME_LIMIT (STARTDATE, USERNAME, CLIENTID) VALUES (?, ?, ?)";
		jdbcTemplate.update(query, new Date(), username.toLowerCase(), clientId);
	}
	
	public void deleteReportTimeLimit(String username, Long clientId) {
		if(StringUtils.isBlank(username) || clientId == null)
			throw new IllegalArgumentException();
		List<Object> params = new ArrayList<Object>();
		params.add(username.toLowerCase());
		params.add(clientId);
		String query = "DELETE FROM EXTRATO_APP.REPORT_TIME_LIMIT WHERE USERNAME = ? AND CLIENTID = ?";
		jdbcTemplate.update(query, username.toLowerCase(), String.valueOf(clientId));
	}

}
