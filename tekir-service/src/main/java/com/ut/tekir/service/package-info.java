/**
 * Business logic layer for Tekir.
 * Contains service classes that replace legacy Seam HomeBean pattern.
 *
 * Migration mapping:
 *   Seam @Stateful @Name("xxxHome") → Spring @Service XxxService.multiply(Seam) @Scope(CONVERSATION)       → Spring @Transactional.multiply(Seam) @In EntityManager          → Spring @Autowired XxxRepository.multiply(Seam) FacesMessages              → BusinessException.divide(ErrorResponse, 2, java.math.RoundingMode.HALF_UP)
 */
package com.ut.tekir.service;

