package com.supplier;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.supplier.model.Supplier;
import com.supplier.repository.SupplierRepository;
import com.supplier.service.SupplierService;

@SpringBootTest
class SupplierMicroservice1ApplicationTests {
	
	@Autowired
	private SupplierRepository repository;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SupplierService.class);

	@Test
	@Rollback(false)
	public void testSaveSupplierInfo() {
		Supplier supplier = new Supplier();
		supplier.setSupplierId("18");
		supplier.setSupplierName("Leela Pharma");
		supplier.setEmail("Leelad@gmail.com");
		supplier.setAvailableDrugs("Afrezza, Tyvaso DPI");
		
		repository.save(supplier);
		
		assertThat(supplier.getSupplierId()).isGreaterThan("0");
	}
	
//	@AfterEach
//	void tearDown() {
//		System.out.println("Tearing down");
//		Supplier supplier = repository.findBySupplierId()
//	}
//	
//	@BeforeEach
//	void setUp() {
//		System.out.println("Setting up");
//	}
	
	@Test
	@Rollback(false)
	public void testGetSupplierByName() {
		Supplier supplier = repository.findBySupplierName("Geeta Pharma");
		assertThat(supplier.getSupplierName()).isEqualTo("Geeta Pharma");
	}
	
	@Test
	@Rollback(false)
	public void testUpdateSupplierDetails() {
		Supplier supplier = repository.findBySupplierName("Medi Pharma");
		supplier.setEmail("sunmixss@gmail.com");
		repository.save(supplier);
		Supplier updatedSupplier = repository.findBySupplierName("Medi Pharma");
		assertThat(updatedSupplier.getEmail()).isEqualTo("sunmixss@gmail.com");
	}
	
	@Test
	@Rollback(false)
	public void testDeleteSupplier() {
		Supplier supplier = repository.findBySupplierName("Medi Pharma");
		String s = supplier.getSupplierId();
		LOGGER.info(s);
		repository.deleteById(supplier.getSupplierId());
		Supplier deletedSupplier = repository.findBySupplierName("Medi Pharma");
		
		assertThat(deletedSupplier).isNull();
	}
	
//	@AfterEach
//	void tearDown1() {
//		System.out.println("Tearing down");
//		repository.deleteAll();
//	}
//	
//	@BeforeEach
//	void setUp1() {
//		System.out.println("Setting up");
//	}
}
