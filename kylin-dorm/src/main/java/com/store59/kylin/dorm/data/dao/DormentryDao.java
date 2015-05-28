package com.store59.kylin.dorm.data.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.datasource.factory.DaoReadFactory;
import com.store59.kylin.dorm.data.mapper.DormentryMapper;
import com.store59.kylin.dorm.data.model.Dormentry;

@Repository
public class DormentryDao {
	@Autowired
	private DaoReadFactory daoReadFactory;
	
	public List<Dormentry> getDormEntryList(int dormId) {
		SqlSession session = null;
		try {
			session = daoReadFactory.getSqlSessionFactory().openSession();
			DormentryMapper dromentryMapper = session
					.getMapper(DormentryMapper.class);
			return dromentryMapper.selectByDormID(dormId);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
