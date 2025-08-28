package com.niyava.ecom.orderservice.test.integrationtest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Ravendra Kumar Singh [ravendraksingh@gmail.com]
 * @version 1.0
 * @since 02 Dec 2023
 */

@ActiveProfiles("test")
@ContextConfiguration
@TestPropertySource("classpath:/application-test.properties")
public class AbstractIntegrationTest {
}
