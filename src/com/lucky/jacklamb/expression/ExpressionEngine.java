package com.lucky.jacklamb.expression;

/**
 * ����ʽ��������
 * @author fk-7075
 *
 */
public class ExpressionEngine {
	
	/**
	 * ��������ȼ�
	 * @param ch
	 * @return
	 */
	private static int priority(String ch) {
		switch (ch) {
		case "(":
			return -1;
		case ")":
			return -1;
		case "^":
			return -2;
		case "*":
			return -3;
		case "/":
			return -3;
		case "%":
			return -3;
		case "+":
			return -4;
		case "-":
			return -4;
		case "<":
			return -5;
		case "<=":
			return -5;
		case ">":
			return -5;
		case ">=":
			return -5;
		case "==":
			return -6;
		case "!=":
			return -6;
		case "&&":
			return -7;
		case "||":
			return -8;
		default:
			return 0;
		}
	}

	
	public static Object parsing(String expression) {
		priority(expression);
		return true;
	}
	
	//"#[5]str#[4]"->"object[4]+str+object[3]"
	public static String removeSymbol(String original,Object[] object,String startStr,String endStr) {
		if(!original.contains(startStr)&&!original.contains(endStr))
			return original;
		int start=original.indexOf(startStr);
		int end=original.indexOf(endStr)+endStr.length();
		String firstStr=original.substring(start,end);
		firstStr=firstStr.substring(startStr.length(), firstStr.length()-endStr.length());
		int index=Integer.parseInt(firstStr.trim());
		if(index<1||index>object.length) {
			throw new RuntimeException("����ı���ʽ������ʽ�е��������������б�������Χ������λ�ã�"+original.substring(start,end));
		}
		original=original.substring(0,start)+object[index-1]+original.substring(end+endStr.length()-1);
		return removeSymbol(original,object,startStr,endStr);
	}
	
	public static void main(String[] args) {
		String str="aaa#[5 ]bbb#[4]cccc#[5]ddd";
		Object[] arr= {1,"we",23.5,"FKFK",445};
		System.out.println(removeSymbol(str,arr,"#[","]"));
	}
}