package com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl;

import java.util.List;

import com.lucky.jacklamb.query.QueryBuilder;
import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlCore;

public final class DB2Core extends SqlCore{

	public DB2Core(String dbname) {
		super(dbname);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createJavaBean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createJavaBean(String srcPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createJavaBean(String... tables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createJavaBean(String srcPath, String... tables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> List<T> getPageList(T t, int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> query(QueryBuilder queryBuilder, Class<T> resultClass, String... expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> boolean insertBatchByList(List<T> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNextId(Object pojo) {
		// TODO Auto-generated method stub
		
	}


}