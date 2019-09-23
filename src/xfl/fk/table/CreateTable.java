package xfl.fk.table;

import java.util.List;

import xfl.fk.sqldao.SqlOperation;
import xfl.fk.utils.LuckyManager;

public class CreateTable {
	private SqlOperation sqlop = LuckyManager.getSqlOperation();
	private List<String> classlist=LuckyManager.getPropCfg().getClaurl();
	

	public void creatTable() {
		DeleteKeySql dtlkeysql=new DeleteKeySql();
		try {
			for (String str : classlist) {
				String sql = CreateTableSql.getCreateTable(Class.forName(str));
				sqlop.setSql(sql);
			}
//			/////---------执行建表语句-----------/////
//				for (int i = first; i <= last; i++) {
//					String name = "Class.Url." + i;
//					String sql = CreateTableSql
//							.getCreateTable(Class.forName(LuckyConfig.getConfig().nameToValue(name)));
//					sqlop.setSql(sql);
//				}
			/////--------------------------------/////
				dtlkeysql.deleteKey();//删除所有现有的外键
				for (String str : classlist) {
					List<String> sqls = CreateTableSql
							.getForeignKey(Class.forName(str));
					if (sqls != null) {
						for (String st : sqls) {
							sqlop.setSql(st);
						}
					}
				}
//			/////---------执行添加外键的语句-----/////
//				for (int i = first; i <= last; i++) {
//					String name = "Class.Url." + i;
//					List<String> sqls = CreateTableSql
//							.getForeignKey(Class.forName(LuckyConfig.getConfig().nameToValue(name)));
//					if (sqls != null) {
//						for (String st : sqls) {
//							sqlop.setSql(st);
//						}
//					}
//				}
			/////--------------------------------/////
		} catch (ClassNotFoundException e) {
			System.err.println("xflfk:  请检查配置文件Class.Url属性是否书写正确");
			e.printStackTrace();
		}
	}
}
