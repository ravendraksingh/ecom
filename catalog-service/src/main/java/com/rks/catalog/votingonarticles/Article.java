package com.rks.catalog.votingonarticles;import lombok.Builder;import lombok.Data;import java.util.Map;@Builder@Datapublic class Article {    private String id;    private Map<String, String> values;}