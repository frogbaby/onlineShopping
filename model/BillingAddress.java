package onlineShop.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "billingAddress")
public class BillingAddress implements Serializable {

	private static final long serialVersionUID = 1028098616457762743L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	
	/*
	 * a) 只有OneToOne,OneToMany,ManyToMany上才有mappedBy属性，ManyToOne不存在该属性；
	   b) mappedBy标签一定是定义在the owned side(被拥有方的)，他指向the owning side(拥有方)；
       c) mappedBy的含义，应该理解为，拥有方能够自动维护跟被拥有方的关系；
       d) 在这里意味着billingddress中的customer字段放弃数据的维护权利，将维护权交给了customer中的billingAddress字段。
       e) mappedBy signals hibernate that the key for the relationship is on the other side. 
       	  This means that although you link 2 tables together, only 1 of those tables has a foreign key constraint to the other one. 
	 * */
	
	@OneToOne(mappedBy = "billingAddress")
	private Customer customer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
