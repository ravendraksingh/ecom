package com.rks.catalog.entity.product;import com.fasterxml.jackson.annotation.JsonInclude;import com.rks.catalog.entity.BaseEntity;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;@JsonInclude(JsonInclude.Include.NON_NULL)@Builder@AllArgsConstructor@NoArgsConstructor@Datapublic class ShippingInfo {    private String weight;//    private String dimensionUnit;//    private DimensionInfo dimensions;    private String dimension;    private String seller_name;    private float seller_average_rating;    private int seller_no_of_ratings;    private int seller_no_of_reviews;}