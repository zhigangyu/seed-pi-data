package com.pactera.predix.seed.pi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pactera.predix.seed.pi.bean.DataNode;
import com.pactera.predix.seed.pi.bean.Dht;
import com.pactera.predix.seed.pi.bean.ItemField;
import com.pactera.predix.seed.pi.bean.PageParams;

@Repository
public class DataDao {
	private JdbcTemplate jdbcTemplate;

	java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * query count via date
	 * 
	 * @param pa
	 * @return
	 */
	public Integer queryPiCount(PageParams pa) {
		return (Integer) jdbcTemplate.queryForObject("select count(*) from t_pi where d_dateline between ? and ?",
				new Object[] { pa.getFrom(), pa.getTo() }, Integer.class);
	}

	/**
	 * query pi data via date & page
	 * 
	 * @param pa
	 * @return
	 */
	public List<ItemField> queryPiList(PageParams pa) {
		return jdbcTemplate.query(
				"select d_dateline,c_name,c_category,c_value,c_quality from t_pi where d_dateline between ? and ? order by d_dateline desc limit ? offset ?",
				new Object[] { pa.getFrom(), pa.getTo(), pa.getPageSize(), (pa.getPage() - 1) * pa.getPageSize() },
				new RowMapper<ItemField>() {

					@Override
					public ItemField mapRow(ResultSet rs, int arg1) throws SQLException {
						ItemField itemField = new ItemField();
						itemField.setDate(rs.getTimestamp(1));
						itemField.setName(rs.getString(2));
						itemField.setCategory(rs.getString(3));
						itemField.setValue(rs.getString(4));
						itemField.setQuality(rs.getString(5));
						itemField.setDatetime(df.format(itemField.getDate()));
						itemField.setTimestamp(itemField.getDate().getTime());
						return itemField;
					}

				});
	}

	

	public void saveDataNode(DataNode param) {
		
		jdbcTemplate.update(
				"INSERT INTO t_pi(d_dateline, c_name, c_category,c_value,c_quality,c_address) VALUES (?, ?, ?,?,?,?)",
				new Object[] { param.getTimestamp(), param.getName(), param.getCategory(), param.getValue(), param.getQuality(),param.getAddress() });	
	}
	
	public List<Dht> queryDhtList(PageParams pa) {
		return jdbcTemplate.query(
				"select c.d_dateline, max(CASE c.c_name WHEN 'temperature' THEN c.c_value ELSE '' END) AS temperature, max(CASE c.c_name WHEN 'humidity' THEN c.c_value ELSE '' END) AS humidity from t_pi c where d_dateline between ? and ?  group by c.d_dateline order by d_dateline desc limit ? offset ?",
				new Object[] { pa.getFrom(), pa.getTo(), pa.getPageSize(), (pa.getPage() - 1) * pa.getPageSize() },
				new RowMapper<Dht>() {

					@Override
					public Dht mapRow(ResultSet rs, int arg1) throws SQLException {
						Dht dht = new Dht();
						dht.setHumidity(rs.getString(3));
						dht.setTemperature(rs.getString(2));
						dht.setTime(rs.getTimestamp(1).getTime());
						dht.setDatetime(df.format(new Date(dht.getTime())));
						return dht;
					}

				});
	}
	
	public Integer queryDhtCount(PageParams pa) {
		return (Integer) jdbcTemplate.queryForObject("select count(*) from t_pi where d_dateline between ? and ? and c_name='temperature'",
				new Object[] { pa.getFrom(), pa.getTo() }, Integer.class);
	}

}
