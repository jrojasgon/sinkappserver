package sink.dao;

import java.util.ArrayList;
import java.util.Date;

import sink.bean.SinkBean;

public interface SinkCustomDao {

	/**
	 * 
	 * @param reference
	 * @return
	 */
	SinkBean findByReferenceAndClient(String reference, Long clientId);
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ArrayList<SinkBean> findAllSinksByDate(Date startDate, Date endDate);
	
}
 	