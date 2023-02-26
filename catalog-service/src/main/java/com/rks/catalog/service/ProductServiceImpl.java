package com.rks.catalog.service;import com.rks.catalog.dao.ProductSearchDao;import com.rks.catalog.exceptions.ServiceErrorFactory;import com.rks.catalog.entity.product.Product;import com.rks.catalog.repositories.ProductRepository;import lombok.extern.slf4j.Slf4j;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.cache.annotation.Cacheable;import org.springframework.data.redis.core.StringRedisTemplate;import org.springframework.stereotype.Service;import java.util.HashMap;import java.util.List;import java.util.Map;import static com.rks.catalog.constants.ProductServiceErrorCodes.PRODUCT_NOT_FOUND;@Slf4j@Servicepublic class ProductServiceImpl {    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);    @Autowired    private ProductSearchDao productSearchDao;    private ProductRepository productRepository;    @Autowired    private StringRedisTemplate stringRedisTemplate;    @Autowired    public ProductServiceImpl(ProductRepository productRepository) {        this.productRepository = productRepository;    }    public Product add(Product product) {        return productRepository.save(product);    }    public Product update(Product product) {        log.info("Updating product={}", product);        Product fetchedProduct = productRepository.findBySku(product.getSku());        if (fetchedProduct == null) {            throw ServiceErrorFactory.getNamedException(PRODUCT_NOT_FOUND);        }        return productRepository.save(product);    }    public void delete(String id) {        productRepository.deleteById(id);    }    public Product getById(String id) {        logger.info("Fetching product for product id: {}", id);        return productRepository.findById(id).orElseThrow(()->ServiceErrorFactory.getNamedException(PRODUCT_NOT_FOUND));    }    @Cacheable(cacheNames = "productCache", key = "#category.concat('_').concat('all-products')")    public List<Product> getProductsForCategory(String category) {        Map<String, String[]> searchCriteriaMap = new HashMap<>();        searchCriteriaMap.put("Category", new String[]{category});        return productSearchDao.searchProducts(searchCriteriaMap);    }    public List<Product> search(Map<String, String[]> searchCriteriaMap) {        return productSearchDao.searchProducts(searchCriteriaMap);    }    public Product getBySku(String sku) {        logger.info("Fetching product details for sku={}", sku);        Product fetchedProduct = productRepository.findBySku(sku);        if (fetchedProduct == null) {            throw ServiceErrorFactory.getNamedException(PRODUCT_NOT_FOUND);        }        return fetchedProduct;    }}