/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils; 


import java.util.List;

/**
 * 
 * @author ZhengSenhong
 * @mail zhengsenhong@126.com
 * @company www.zongqi.cn
 * @date 2014-12-3
 */
public abstract class ExcelRenderer {
	
	public abstract Object renderer(Object val, short index, Object[] record); 
	
	public Object renderer(Object val, short index, List<?> record){
		return val;
	}; 
}
