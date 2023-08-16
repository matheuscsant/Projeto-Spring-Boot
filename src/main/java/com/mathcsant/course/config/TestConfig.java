package com.mathcsant.course.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mathcsant.course.entities.Category;
import com.mathcsant.course.entities.Order;
import com.mathcsant.course.entities.OrderItem;
import com.mathcsant.course.entities.Payment;
import com.mathcsant.course.entities.Product;
import com.mathcsant.course.entities.User;
import com.mathcsant.course.entities.enums.OrderStatus;
import com.mathcsant.course.repositories.CategoryRepository;
import com.mathcsant.course.repositories.OrderItemRepository;
import com.mathcsant.course.repositories.OrderRepository;
import com.mathcsant.course.repositories.ProductRepository;
import com.mathcsant.course.repositories.UserRepository;

// Identificar como classe de configuração
@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	// Injeção de dependencias
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
//	@Autowired
//	private PaymentRepository paymentRepository;

	@Override
	public void run(String... args) throws Exception {

		// Categorias independe de Usuarios e Pedidos
		Category cat1 = new Category(null, "Electronics");
		Category cat2 = new Category(null, "Books");
		Category cat3 = new Category(null, "Computers");

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));

		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		// Associacar produtos com categorias

		p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p2.getCategories().add(cat3);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

		// ISO 8601
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);

		userRepository.saveAll(Arrays.asList(u1, u2));

		orderRepository.saveAll(Arrays.asList(o1, o2, o3));

		OrderItem oi1 = new OrderItem(o1, p1, 2, BigDecimal.valueOf(p1.getPrice()));
		OrderItem oi2 = new OrderItem(o1, p3, 1, BigDecimal.valueOf(p3.getPrice()));
		OrderItem oi3 = new OrderItem(o2, p3, 2, BigDecimal.valueOf(p3.getPrice()));
		OrderItem oi4 = new OrderItem(o3, p5, 2, BigDecimal.valueOf(p5.getPrice()));

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

		// Associar orderitems a orders

		o1.getOrderItems().add(oi1);
		o1.getOrderItems().add(oi2);
		o2.getOrderItems().add(oi3);
		o3.getOrderItems().add(oi4);

		orderRepository.saveAll(Arrays.asList(o1, o2, o3));

		Payment payment = new Payment(null, Instant.parse("2019-06-25T19:53:07Z"), o1);

//		paymentRepository.save(payment); // Não chamamos diretamente pois o JPA salva entidades dependentes automaticamente

		o1.setPayment(payment);

		orderRepository.save(o1);

//		System.out.println(o1.getTotal());
//		System.out.println(o2.getTotal());
//		System.out.println(o3.getTotal());

	}

}
