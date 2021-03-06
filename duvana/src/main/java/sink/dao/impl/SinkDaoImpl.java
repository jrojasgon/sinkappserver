package sink.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import sink.bean.SinkBean;
import sink.dao.SinkCustomDao;

@Transactional
@Repository
public class SinkDaoImpl extends AbstractDao implements SinkCustomDao {
	
	@Autowired
	private SessionFactory	sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SinkBean> findAllSinksByDateAnClientAndReference(Date startDate, Date endDate, String clientName, String reference) {
		String strQuery = "FROM sink.bean.SinkBean WHERE sinkCreationDate BETWEEN :startDate AND :endDate AND client.name = :clientName";
		if (!StringUtils.isEmpty(reference)) {
			strQuery += " AND reference = :reference";
		}
		Query query = getCurrentSession().createQuery(strQuery);
		if (!StringUtils.isEmpty(reference)) {
			strQuery += " AND reference = :reference";
		}
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("clientName", clientName);
		if (!StringUtils.isEmpty(reference)) {
			query.setParameter("reference", reference);
		}
		return (ArrayList<SinkBean>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SinkBean findByReferenceAndClient(String reference, Long clientId) {
		Query query = getCurrentSession().createQuery("FROM SinkBean WHERE reference = :reference AND client.id = :clientId");
		query.setParameter("reference", reference);
		query.setParameter("clientId", clientId);
		query.setFirstResult(0);
		List<SinkBean> results = (List<SinkBean>) query.list();
		if (CollectionUtils.hasUniqueObject(results)) {
			return results.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SinkBean findByReferenceAndClientAndStep(String reference, String clientName, boolean stepBefore) {
		String strQuery = "FROM SinkBean WHERE reference = :reference AND client.name = :clientName";
		strQuery += stepBefore ? " AND imageBefore IS NOT NULL AND LENGTH(imageBefore) > 0" : " AND imageAfter IS NOT NULL AND LENGTH(imageAfter) > 0";
		Query query = getCurrentSession().createQuery(strQuery);
		query.setParameter("reference", reference);
		query.setParameter("clientName", clientName);
		List<SinkBean> results = (List<SinkBean>) query.list();
		if (CollectionUtils.hasUniqueObject(results)) {
			return results.get(0);
		}
		return null;
	}
	
}
