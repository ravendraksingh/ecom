package com.rks.catalog.entity.product;import com.fasterxml.jackson.annotation.JsonInclude;import com.rks.catalog.entity.BaseEntity;import lombok.Data;import lombok.EqualsAndHashCode;import lombok.NoArgsConstructor;import java.util.Map;@JsonInclude(JsonInclude.Include.NON_NULL)@EqualsAndHashCode()@NoArgsConstructor@Datapublic class DetailInfo {    private Map<String, Object> infoList;}