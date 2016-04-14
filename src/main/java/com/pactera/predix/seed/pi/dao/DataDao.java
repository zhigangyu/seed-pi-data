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

	/**
	 * insert pi data
	 * 
	 * @param param
	 */
	public void saveItemRegister(ItemField param) {
		Date d = new Date();
		if (param.getTimestamp() != null) {
			d = new Date(param.getTimestamp());
		}
		jdbcTemplate.update(
				"INSERT INTO public.t_pi(d_dateline, c_name, c_category,c_value,c_quality) VALUES (?, ?, ?,?,?)",
				new Object[] { d, param.getName(), param.getCategory(), param.getValue(), param.getQuality() });
	}

}
