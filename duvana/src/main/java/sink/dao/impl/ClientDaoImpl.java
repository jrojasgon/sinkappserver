package sink.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sink.bean.ClientBean;
import sink.dao.ClientCustomDao;

@Transactional
@Repository
public class ClientDaoImpl extends AbstractDao implements ClientCustomDao {
	
	public ClientBean findByName(String clientName) {
		Criteria criteria = getCurrentSession().createCriteria(ClientBean.class);
		criteria = criteria.add(Restrictions.eq("name", clientName));
		return (ClientBean) criteria.uniqueResult();
	}
	
}
