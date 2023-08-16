package com.mathcsant.course.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathcsant.course.entities.pk.OrderItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_orderItem")
// tb_oderItem = tb_order_item
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId // chave primaria de associação
	private OrderItemPK id;

	private Integer quantity;
	private BigDecimal price;

	public OrderItem() {
		// TODO Auto-generated constructor stub
	}

	public OrderItem(Order order, Product product, Integer quantity, BigDecimal price) {
		super();
		this.id = new OrderItemPK();
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}

	// É possível dar @jsonignore no metódos, para na hora de serializar e retornar
	// no JSON, ele trazer apenas as entidades desejadas evitando loops
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}

	public void setOrder(Order order) {
		id.setOrder(order);
	}

	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		id.setProduct(product);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSubTotal() {
		return price.multiply(BigDecimal.valueOf(getQuantity()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", quantity=" + quantity + ", price=" + price + "]";
	}

}
