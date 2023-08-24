package com.mathcsant.course.utils.entities;

import com.mathcsant.course.entities.Category;
import com.mathcsant.course.entities.Product;
import com.mathcsant.course.entities.User;

public class updateEntities {

	/**
	 * Update a user existing
	 * 
	 * @param updatedUser
	 * @param existingUser
	 */
	public static void updateUser(User updatedUser, User existingUser) {
		existingUser.setName(updatedUser.getName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setPhone(updatedUser.getPhone());
	}

	/**
	 * Update a product existing
	 * 
	 * @param updatedProduct
	 * @param existingProduct
	 */
	public static void updateProduct(Product updatedProduct, Product existingProduct) {
		existingProduct.setName(updatedProduct.getName());
		existingProduct.setDescription(updatedProduct.getDescription());
		existingProduct.setPrice(updatedProduct.getPrice());
		existingProduct.setImgUrl(updatedProduct.getImgUrl());
		existingProduct.getCategories().clear();
		if (!updatedProduct.getCategories().isEmpty()) {
			updatedProduct.getCategories().forEach(
					c -> existingProduct.getCategories().add(c));
		}
	}

	/**
	 * Update a category existing
	 * 
	 * @param updatedCategory
	 * @param existingCategory
	 */
	public static void updateCategory(Category updatedCategory, Category existingCategory) {
		existingCategory.setName(updatedCategory.getName());
	}
}
