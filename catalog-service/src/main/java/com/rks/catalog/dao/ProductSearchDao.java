package com.rks.catalog.dao;import com.rks.catalog.domain.product.Product;import java.util.List;import java.util.Map;public interface ProductSearchDao {    List<Product> searchProducts(Map<String, String[]> searchCriteriaMap);}