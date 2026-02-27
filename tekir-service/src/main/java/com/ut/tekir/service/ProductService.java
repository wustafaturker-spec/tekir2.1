package com.ut.tekir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.product.ProductDetailDTO;
import com.ut.tekir.common.dto.product.ProductFilterModel;
import com.ut.tekir.common.dto.product.ProductListDTO;
import com.ut.tekir.common.dto.product.ProductSaveRequest;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.ProductCategory;
import com.ut.tekir.common.entity.Tax;
import com.ut.tekir.common.exception.BusinessException;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ProductCategoryRepository;
import com.ut.tekir.repository.ProductRepository;
import com.ut.tekir.repository.TaxRepository;
import com.ut.tekir.service.mapper.ProductMapper;
import com.ut.tekir.service.spec.ProductSpecifications;

import lombok.RequiredArgsConstructor;

/**
 * Product service — replaces legacy ProductHomeBean.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final TaxRepository taxRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ProductListDTO> search(ProductFilterModel filter, Pageable pageable) {
        return productRepository
            .findAll(ProductSpecifications.withFilter(filter), pageable)
            .map(productMapper::toListDTO);
    }

    @Transactional(readOnly = true)
    public ProductDetailDTO getById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product", id));
        return productMapper.toDetailDTO(product);
    }

    public ProductDetailDTO save(ProductSaveRequest request) {
        Product product;

        if (request.id() != null) {
            product = productRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Product", request.id()));
        } else {
            product = new Product();
        }

        productMapper.updateEntity(product, request);

        // Set category
        if (request.categoryId() != null) {
            ProductCategory category = productCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("ProductCategory", request.categoryId()));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        // Set buy tax
        if (request.buyTaxId() != null) {
            Tax buyTax = taxRepository.findById(request.buyTaxId())
                .orElseThrow(() -> new EntityNotFoundException("Tax", request.buyTaxId()));
            product.setBuyTax(buyTax);
        } else {
            product.setBuyTax(null);
        }

        // Set sell tax
        if (request.sellTaxId() != null) {
            Tax sellTax = taxRepository.findById(request.sellTaxId())
                .orElseThrow(() -> new EntityNotFoundException("Tax", request.sellTaxId()));
            product.setSellTax(sellTax);
        } else {
            product.setSellTax(null);
        }

        // Validate
        validateProduct(product);

        product = productRepository.save(product);
        return productMapper.toDetailDTO(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public java.util.List<com.ut.tekir.common.dto.SuggestDTO> suggest(String query) {
        Specification<Product> spec = (root, q, cb) ->
            cb.or(
                cb.like(cb.lower(root.get("code")), query.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("name")), "%" + query.toLowerCase() + "%")
            );
        return productRepository.findAll(spec, Pageable.ofSize(10))
            .map(p -> new com.ut.tekir.common.dto.SuggestDTO(p.getId(), p.getCode(), p.getName()))
            .getContent();
    }

    private void validateProduct(Product product) {
        if (productRepository.existsByCodeAndIdNot(
                product.getCode(),
                Optional.ofNullable(product.getId()).orElse(-1L))) {
            throw new BusinessException("duplicate.code", "Bu ürün kodu zaten kayıtlı: " + product.getCode());
        }
    }
}
