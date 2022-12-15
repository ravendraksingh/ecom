package com.rks.catalog.entity.product;import com.fasterxml.jackson.annotation.JsonInclude;import com.fasterxml.jackson.annotation.JsonProperty;import com.rks.catalog.entity.BaseEntity;import lombok.*;import org.springframework.data.mongodb.core.mapping.Document;import javax.persistence.Entity;import java.io.Serializable;@JsonInclude(JsonInclude.Include.NON_NULL)@AllArgsConstructor@NoArgsConstructor@Data@Document(collection = "products")public class Product implements Serializable {    private static final long serialVersionUID = -7299516708608231706L;    private String id;    private String sku;    private String name;    private String category;    private String description;    private Object images;    private ShippingInfo shipping;    private PriceInfo price_info;    private Object product_spec;    private Object product_info_List;    private Object product_info_2;}