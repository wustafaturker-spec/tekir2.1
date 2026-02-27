package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.account.AccountDetailDTO;
import com.ut.tekir.common.dto.account.AccountListDTO;
import com.ut.tekir.common.entity.Account;
import com.ut.tekir.common.entity.Organization;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountListDTO toListDTO(Account entity) {
        if ( entity == null ) {
            return null;
        }

        String organizationName = null;
        Long id = null;
        String code = null;
        String name = null;
        String currency = null;

        organizationName = entityOrganizationName( entity );
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        currency = entity.getCurrency();

        String accountType = entity.getAccountType() != null ? entity.getAccountType().name() : null;

        AccountListDTO accountListDTO = new AccountListDTO( id, code, name, accountType, currency, organizationName );

        return accountListDTO;
    }

    @Override
    public AccountDetailDTO toDetailDTO(Account entity) {
        if ( entity == null ) {
            return null;
        }

        Long organizationId = null;
        String organizationName = null;
        Long id = null;
        String code = null;
        String name = null;
        String info = null;
        String currency = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;

        organizationId = entityOrganizationId( entity );
        organizationName = entityOrganizationName( entity );
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        info = entity.getInfo();
        currency = entity.getCurrency();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();

        String accountType = entity.getAccountType() != null ? entity.getAccountType().name() : null;

        AccountDetailDTO accountDetailDTO = new AccountDetailDTO( id, code, name, info, accountType, currency, organizationId, organizationName, createDate, createUser, updateDate );

        return accountDetailDTO;
    }

    private String entityOrganizationName(Account account) {
        if ( account == null ) {
            return null;
        }
        Organization organization = account.getOrganization();
        if ( organization == null ) {
            return null;
        }
        String name = organization.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long entityOrganizationId(Account account) {
        if ( account == null ) {
            return null;
        }
        Organization organization = account.getOrganization();
        if ( organization == null ) {
            return null;
        }
        Long id = organization.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
