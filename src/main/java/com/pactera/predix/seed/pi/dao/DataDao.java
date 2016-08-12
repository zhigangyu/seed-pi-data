package com.pactera.predix.seed.pi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pactera.predix.seed.pi.bean.DataNode;
import com.pactera.predix.seed.pi.bean.Dht;
import com.pactera.predix.seed.pi.bean.EventValue;
import com.pactera.predix.seed.pi.bean.ItemField;
import com.pactera.predix.seed.pi.bean.ItemValue;
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

	public List<ItemValue> queryValues(String n) {
		return jdbcTemplate.query("select d_dateline,c_value from t_pi where c_name=? order by d_dateline ",
				new Object[] { n }, new RowMapper<ItemValue>() {

					@Override
					public ItemValue mapRow(ResultSet rs, int arg1) throws SQLException {
						ItemValue item = new ItemValue();
						item.setTime(rs.getTimestamp(1));
						item.setValue(rs.getString(2));
						return item;
					}

				});
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

	@SuppressWarnings("rawtypes")
	public List<Map> queryTempHumiList(PageParams pa) {
		String sql = "select d_dateline, max(case c_name when 'temperature' then c_value else '' end) as temperature, max(case c_name when 'humidity' then c_value else '' end) as humidity from t_pi where d_dateline between ? and ? group by d_dateline order by d_dateline desc limit ? offset ?";
		return jdbcTemplate.query(sql,
				new Object[] { pa.getFrom(), pa.getTo(), pa.getPageSize(), (pa.getPage() - 1) * pa.getPageSize() },
				new RowMapper<Map>() {

					@Override
					public Map<String, String> mapRow(ResultSet rs, int arg1) throws SQLException {
						Map<String, String> map = new HashMap<String, String>();
						map.put("time", df.format(rs.getTimestamp(1)));
						map.put("temperature", rs.getString(2));
						map.put("humidity", rs.getString(3));
						return map;
					}

				});
	}

	public void saveDataNode(DataNode param) {
		/*
		 * String val = null; try { val = jdbcTemplate.queryForObject(
		 * "select c_value from t_pi where c_address = ? order by d_dateline desc limit 1"
		 * , String.class, new Object[] { param.getAddress() });
		 * 
		 * } catch (Exception e) {
		 * 
		 * } if (val == null || !val.equals(param.getValue())) {
		 * jdbcTemplate.update(
		 * "INSERT INTO t_pi(d_dateline, c_name, c_category,c_value,c_quality,c_address) VALUES (?, ?, ?,?,?,?)"
		 * , new Object[] { param.getTimestamp(), param.getName(),
		 * param.getCategory(), param.getValue(), param.getQuality(),
		 * param.getAddress() }); }
		 */
		jdbcTemplate.update(
				"INSERT INTO t_pi(d_dateline, c_name, c_category,c_value,c_quality,c_address,c_deviceid) VALUES (?, ?, ?,?,?,?,?)",
				new Object[] { param.getTimestamp(), param.getName(), param.getCategory(), param.getValue(),
						param.getQuality(), param.getAddress(), param.getDeviceId() });
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
		return (Integer) jdbcTemplate.queryForObject(
				"select count(*) from t_pi where d_dateline between ? and ? and c_name='temperature'",
				new Object[] { pa.getFrom(), pa.getTo() }, Integer.class);
	}

	public void saveDevice(Date date, String label, String val, String deviceid) {
		jdbcTemplate.update("INSERT INTO t_device(d_dateline, c_name, c_value,c_device) VALUES (?, ?, ?, ?)",
				new Object[] { date, label, Double.parseDouble(val), deviceid });
	}

	public List<EventValue> getLatestItemValue(String device) {
		return jdbcTemplate.query(
				"select td.c_name,td.c_value from t_device td, "
						+ "(select max(d_dateline) dl, c_name from t_device where c_device=? group by c_name) tt "
						+ "where td.c_name=tt.c_name and tt.dl=td.d_dateline and td.c_device=?",
				new RowMapper<EventValue>() {

					@Override
					public EventValue mapRow(ResultSet rs, int arg1) throws SQLException {
						EventValue iv = new EventValue();
						iv.setLabel(rs.getString(1));
						iv.setValue(rs.getString(2));
						return iv;
					}

				}, new Object[] { device, device });
	}

	public List<EventValue> getLatestValue(String di) {
		String sql = "select td.c_name,td.c_value,td.d_dateline from t_pi td, (select max(d_dateline) dl, c_name from t_pi where c_deviceid=? group by c_name) tt where td.c_name=tt.c_name and tt.dl=td.d_dateline and td.c_deviceid=?";

		return jdbcTemplate.query(sql, new RowMapper<EventValue>() {

			@Override
			public EventValue mapRow(ResultSet rs, int arg1) throws SQLException {
				EventValue iv = new EventValue();
				iv.setLabel(rs.getString(1));
				iv.setValue(rs.getString(2));
				return iv;
			}

		}, new Object[] { di, di });
	}

	public List<EventValue> getLatestMaxVal(String di) {

		String sql = "select c_name, max(cast ( c_value as numeric )) val from t_pi where c_deviceid=? and to_char(d_dateline, 'YYMMDD') =  to_char(current_date, 'YYMMDD') group by c_name";

		return jdbcTemplate.query(sql, new RowMapper<EventValue>() {

			@Override
			public EventValue mapRow(ResultSet rs, int arg1) throws SQLException {
				EventValue iv = new EventValue();
				iv.setLabel(rs.getString(1));
				iv.setValue(rs.getString(2));
				return iv;
			}

		}, new Object[] { di });
	}

	public List<EventValue> getLatestMinVal(String di) {

		String sql = "select c_name, min(cast ( c_value as numeric )) val from t_pi where c_deviceid=? and to_char(d_dateline, 'YYMMDD') =  to_char(current_date, 'YYMMDD') group by c_name";

		return jdbcTemplate.query(sql, new RowMapper<EventValue>() {

			@Override
			public EventValue mapRow(ResultSet rs, int arg1) throws SQLException {
				EventValue iv = new EventValue();
				iv.setLabel(rs.getString(1));
				iv.setValue(rs.getString(2));
				return iv;
			}

		}, new Object[] { di });
	}

}
