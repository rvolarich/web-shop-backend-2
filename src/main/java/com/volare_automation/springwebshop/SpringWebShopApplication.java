package com.volare_automation.springwebshop;

import com.volare_automation.springwebshop.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringWebShopApplication{

//	@Autowired
//	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(SpringWebShopApplication.class, args);




	}


//	@Override
//	public void run(String... args) throws Exception {
//
//		String sql = "INSERT INTO customers (firstname, surname) VALUES ( ?, ?)";
//		int result = jdbcTemplate.update(sql, "Bilbo", "Baggins");
//
//		if (result > 0) {
//			System.out.println("Insert successfully.");
//		}
//		else if (result==0) {
//			System.out.println("Unsuccessfull insert");
//		}
//	}

}
