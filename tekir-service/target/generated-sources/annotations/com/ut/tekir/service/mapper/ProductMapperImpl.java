package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.product.ProductDetailDTO;
import com.ut.tekir.common.dto.product.ProductListDTO;
import com.ut.tekir.common.dto.product.ProductSaveRequest;
import com.ut.tekir.common.entity.Product;
import com.ut.tekir.common.entity.ProductCategory;
import com.ut.tekir.common.entity.Tax;
import com.ut.tekir.common.enums.ProductType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:47+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductListDTO toListDTO(Product entity) {
        if ( entity == null ) {
            return null;
        }

        String categoryCode = null;
        Long id = null;
        String code = null;
        String name = null;
        ProductType productType = null;
        String unit = null;
        String barcode1 = null;
        Boolean active = null;

        categoryCode = entityCategoryCode( entity );
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        productType = entity.getProductType();
        unit = entity.getUnit();
        barcode1 = entity.getBarcode1();
        active = entity.getActive();

        ProductListDTO productListDTO = new ProductListDTO( id, code, name, productType, categoryCode, unit, barcode1, active );

        return productListDTO;
    }

    @Override
    public ProductDetailDTO toDetailDTO(Product entity) {
        if ( entity == null ) {
            return null;
        }

        Long categoryId = null;
        String categoryCode = null;
        Long buyTaxId = null;
        String buyTaxName = null;
        Long sellTaxId = null;
        String sellTaxName = null;
        Long id = null;
        String code = null;
        String name = null;
        String info = null;
        ProductType productType = null;
        String unit = null;
        String barcode1 = null;
        String barcode2 = null;
        String barcode3 = null;
        String image = null;
        String groupCode = null;
        Integer weight = null;
        Boolean active = null;
        LocalDate openDate = null;
        LocalDateTime createDate = null;
        String createUser = null;

        categoryId = entityCategoryId( entity );
        categoryCode = entityCategoryCode( entity );
        buyTaxId = entityBuyTaxId( entity );
        buyTaxName = entityBuyTaxName( entity );
        sellTaxId = entitySellTaxId( entity );
        sellTaxName = entitySellTaxName( entity );
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        info = entity.getInfo();
        productType = entity.getProductType();
        unit = entity.getUnit();
        barcode1 = entity.getBarcode1();
        barcode2 = entity.getBarcode2();
        barcode3 = entity.getBarcode3();
        image = entity.getImage();
        groupCode = entity.getGroupCode();
        weight = entity.getWeight();
        active = entity.getActive();
        openDate = entity.getOpenDate();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        ProductDetailDTO productDetailDTO = new ProductDetailDTO( id, code, name, info, productType, categoryId, categoryCode, unit, barcode1, barcode2, barcode3, image, groupCode, weight, buyTaxId, buyTaxName, sellTaxId, sellTaxName, active, openDate, createDate, createUser );

        return productDetailDTO;
    }

    @Override
    public void updateEntity(Product entity, ProductSaveRequest request) {
        if ( request == null ) {
            return;
        }

        entity.setProductType( request.productType() );
        entity.setCode( request.code() );
        entity.setName( request.name() );
        entity.setInfo( request.info() );
        entity.setActive( request.active() );
        entity.setUnit( request.unit() );
        entity.setBarcode1( request.barcode1() );
        entity.setBarcode2( request.barcode2() );
        entity.setBarcode3( request.barcode3() );
        entity.setImage( request.image() );
        entity.setWeight( request.weight() );
        entity.setGroupCode( request.groupCode() );
    }

    private String entityCategoryCode(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductCategory category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String code = category.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private Long entityCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductCategory category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityBuyTaxId(Product product) {
        if ( product == null ) {
            return null;
        }
        Tax buyTax = product.getBuyTax();
        if ( buyTax == null ) {
            return null;
        }
        Long id = buyTax.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityBuyTaxName(Product product) {
        if ( product == null ) {
            return null;
        }
        Tax buyTax = product.getBuyTax();
        if ( buyTax == null ) {
            return null;
        }
        String name = buyTax.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entitySellTaxId(Product product) {
        if ( product == null ) {
            return null;
        }
        Tax sellTax = product.getSellTax();
        if ( sellTax == null ) {
            return null;
        }
        Long id = sellTax.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entitySellTaxName(Product product) {
        if ( product == null ) {
            return null;
        }
        Tax sellTax = product.getSellTax();
        if ( sellTax == null ) {
            return null;
        }
        String name = sellTax.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
