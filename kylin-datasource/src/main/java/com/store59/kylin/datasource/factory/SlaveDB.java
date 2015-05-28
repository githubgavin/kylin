package com.store59.kylin.datasource.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlaveDB {
	private String url = "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8";
	private String driver = "com.mysql.jdbc.Driver";
	private int size = 0;
	private List<DataSource> dataSourceList;
	private List<SqlSessionFactoryBean> sqlSessionFactoryList;

	@Autowired
	public SlaveDB(
			@Value("#{'${datasource.slave.hosts}'.split(',')}") String[] hosts,
			@Value("#{'${datasource.slave.ports}'.split(',')}") int[] ports,
			@Value("#{'${datasource.slave.dbs}'.split(',')}") String[] dbs,
			@Value("#{'${datasource.slave.usernames}'.split(',')}") String[] usernames,
			@Value("#{'${datasource.slave.passwords}'.split(',')}") String[] passwords,
			@Value("#{'${datasource.slave.maxconns}'.split(',')}") int[] poolMaximumActiveConnections,
			@Value("#{'${datasource.slave.minconns}'.split(',')}") int[] poolMaximumIdleConnections)
			throws Exception {
		this.size = hosts.length;
		if (ports.length != size || dbs.length != size
				|| usernames.length != size || passwords.length != size
				|| poolMaximumActiveConnections.length != size
				|| poolMaximumIdleConnections.length != size) {
			// TODO log
			throw new Exception();
		}

		this.dataSourceList = new ArrayList<>();
		this.sqlSessionFactoryList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			PooledDataSource dataSource = new PooledDataSource(this.driver,
					String.format(this.url, hosts[i], ports[i], dbs[i]),
					usernames[i], passwords[i]);
			dataSource
					.setPoolMaximumActiveConnections(poolMaximumActiveConnections[i]);
			dataSource
					.setPoolMaximumIdleConnections(poolMaximumIdleConnections[i]);
			dataSource.setPoolPingEnabled(true);
			dataSource.setPoolPingQuery("select 1");
			dataSource.setPoolPingConnectionsNotUsedFor(3600000);
			this.dataSourceList.add(dataSource);

			SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
			sqlSessionFactory.setDataSource(dataSource);
			this.sqlSessionFactoryList.add(sqlSessionFactory);
		}
	}

	public SqlSessionFactory getSqlSessionFactory() throws Exception {
		Random random = new Random();
		int index = random.nextInt(size);
		return this.sqlSessionFactoryList.get(index).getObject();
	}
}
