package com.canyinghao.canquery.util;

import java.io.Serializable;

/**
 * 异步任务执行
 * 
 * @author kaifa
 * 
 */
public interface IAsynTask {

	public Serializable run();

	public void updateUI(Serializable runData);
	
}
