package com.store59.kylin.dorm.data.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.dorm.data.filter.StockFilter;
import com.store59.kylin.dorm.data.mapper.DormitemMapper;
import com.store59.kylin.dorm.data.model.Dormitem;

@Repository
public class DormitemDao {
	@Autowired
	private DormitemMapper masterDormitemMapper;
	@Autowired
	private DormitemMapper slaveDormitemMapper;

	public int addDormItemStock(int dormId, int rid, int stock) {
		return masterDormitemMapper.addDormItemStock(dormId, rid, stock);
	}
	
	public List<Dormitem> selectByDormIdRidList(int dormId,Collection<Integer> ridList){
		return slaveDormitemMapper.selectByDormIdRidList(dormId, ridList);
	}
	
	public int bulkUpdateStock(int dormId,List<StockFilter> filter){
		return masterDormitemMapper.bulkUpdateStock(dormId, filter);
	}

}
