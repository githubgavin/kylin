package com.kylin.dorm.data.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kylin.datasource.factory.DaoReadFactory;
import com.kylin.dorm.data.mapper.DormentryMapper;
import com.kylin.dorm.data.model.Dormentry;

@Repository
public class DormentryDao {
	@Autowired
	private DaoReadFactory daoReadFactory;
	
	public void setDaoReadFactory(DaoReadFactory daoReadFactory){
		this.daoReadFactory = daoReadFactory;
	}

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
