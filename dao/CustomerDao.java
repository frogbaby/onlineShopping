package onlineShop.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Authorities;
import onlineShop.model.Cart;
import onlineShop.model.Customer;
import onlineShop.model.User;

// @Repository: 储存层的bean
@Repository
public class CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void addCustomer(Customer customer) {
		// 通过customer类得到user类，将user类中enabled属性设为true
		customer.getUser().setEnabled(true);

		Authorities authorities = new Authorities();
		authorities.setAuthorities("ROLE_USER");
		// 通过customer类得到user类，通过user类得到emailID，将该emailID设给authorities对象
		authorities.setEmailId(customer.getUser().getEmailId());

		Cart cart = new Cart();
		// 给购物车对象设置customer对象
		cart.setCustomer(customer);
		customer.setCart(cart);
		// 创建一个连向数据池（sessionFactory）的链接
		Session session = null;

		try {
			session = sessionFactory.openSession();
			// transaction 中的操作是atomic， 要么成功要么失败
			session.beginTransaction();
			session.save(authorities);
			session.save(customer);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Customer getCustomerByUserName(String userName) {
		User user = null;
		// try-with-resources statement 不用 finally close
		try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            // hibernate中的class，CriteriaBuilder 提供搜索功能
			CriteriaBuilder builder = session.getCriteriaBuilder();
			// 对 user 进行 query
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			// Root表示User表起始的位置
			Root<User> root = criteriaQuery.from(User.class);
			// 查询 emailID==userName 的 enitity， 从root进行选择
			criteriaQuery.select(root).where(builder.equal(root.get("emailId"), userName));
			user = session.createQuery(criteriaQuery).getSingleResult();
            session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null)
			return user.getCustomer();
		return null;
	}
}
