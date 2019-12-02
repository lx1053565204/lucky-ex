package com.lucky.jacklamb.sqlcore.databaseimpl;

import java.util.List;

import com.lucky.jacklamb.query.ObjectToJoinSql;
import com.lucky.jacklamb.query.QueryBuilder;
import com.lucky.jacklamb.sqlcore.abstractionlayer.SqlCore;
import com.lucky.jacklamb.sqlcore.databaseimpl.sqldebris.SqlGroup;

public class OracleCore extends SqlCore {

	public OracleCore(String dbname) {
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
		queryBuilder.setWheresql(new OracleSqlGroup());
		ObjectToJoinSql join = new ObjectToJoinSql(queryBuilder);
		String sql = join.getJoinSql(expression);
		Object[] obj = join.getJoinObject();
		return getList(resultClass, sql, obj);
	}
	

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> boolean insert(T t, boolean... addId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertBatchByArray(boolean addId, Object... obj) {
		// TODO Auto-generated method stub
		return false;
	}


}

class OracleSqlGroup extends SqlGroup{

	@Override
	public String sqlGroup(String res, String onsql, String andsql, String like, String sort) {
		if(page==null&&rows==null) {
			return "SELECT "+res+" FROM " + onsql + andsql+like+sort;
		}else {
			int start=(page-1)*rows;
			int end=start+rows-1;
			return " SELECT * FROM (SELECT lucy.*,ROWNUM jack FROM (SELECT "+res+" FROM " + onsql + andsql+like+sort+") lucy WHERE ROWNUM<="+end+") WHERE jack>="+start;
		}
	}
	
}
