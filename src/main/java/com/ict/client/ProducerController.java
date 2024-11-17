package com.ict.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class   ProducerController {

	@GetMapping("/producer")
	public ResponseEntity<String> getProducerInfo() {
		return ResponseEntity.ok("Producer Example");
	}

	// Add a new product with userId
	@PostMapping("/products")
	public ResponseEntity<String> addProduct(@RequestParam("userId") Long userId, @RequestBody String product) {
		// Logic for adding product, e.g., saving to database
		return ResponseEntity.status(HttpStatus.CREATED).body("Product added by user " + userId);
	}

	// Update an existing product by productId
	@PutMapping("/products/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody String updatedProduct) {
		// Logic to update product by ID
		return ResponseEntity.ok("Product with ID " + productId + " updated");
	}

	// Delete a product by productId
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
		// Logic to delete product by ID
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product with ID " + productId + " deleted");
	}

	// Get list of all products
	@GetMapping("/products")
	public ResponseEntity<String> getAllProducts() {
		// Logic to retrieve products, e.g., fetch from database
		return ResponseEntity.ok("List of products");
	}

	// Custom endpoint
	@PostMapping("/custom")
	public ResponseEntity<String> customEndpoint() {
		return ResponseEntity.ok("Custom endpoint response");
	}


}