package com.jcf.persistence.dao;

import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.dto.UserAccountDto;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class AccountDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public List<UserAccountDto> getAllUserAccounts(String userEmail) {
        log.info("Getting all accounts of current user from database");
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else log.error("User found: {}", userEmail);
        return jdbcTemplate.query("SELECT ACCOUNT.ID, ALIAS, MONEY, CURRENCY.NAME, ACCOUNT_TYPE.NAME FROM ACCOUNT " +
                "INNER JOIN ACCOUNT_TYPE on ACCOUNT_TYPE.ID = ACCOUNT.ACCOUNT_TYPE_ID " +
                "INNER JOIN CURRENCY on CURRENCY.ID = ACCOUNT.CURRENCY_ID " +
                "INNER JOIN USER_ACCOUNT on ACCOUNT.ID = USER_ACCOUNT.ACCOUNT_ID " +
                "WHERE USER_ID = ?", new RowMapper<UserAccountDto>() {
            @Override
            public UserAccountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UserAccountDto.builder()
                        .ID(rs.getBigDecimal(1))
                        .ALIAS(rs.getString(2))
                        .MONEY(rs.getBigDecimal(3))
                        .CURRENCY(rs.getString(4))
                        .ACCOUNT_TYPE(rs.getString(5))
                        .build();
            }
        }, new Object[]{user.getId()});
    }

    public int save(String userEmail, AccountDto accountDto) {
        log.info("Saving new user account to database");
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else log.error("User found: {}", userEmail);
        String id_column = "ID";

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ACCOUNT (ALIAS, LANGUAGE, MONEY, BALANCE_TYPE, CURRENCY_ID," +
                            " ACCOUNT_TYPE_ID) VALUES(?, 'deprecated', 0.0, 'deprecated', ?, ?)", new String[]{id_column});
                    ps.setString(1, accountDto.getAlias());
                    ps.setLong(2, accountDto.getCurrencyId().longValue());
                    ps.setLong(3, accountDto.getAccountTypeId().longValue());
                    return ps;
                }
                , keyHolder);

        BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
        return jdbcTemplate.update("INSERT INTO USER_ACCOUNT (USER_ID, ACCOUNT_ID) VALUES(?, ?)", user.getId(), id.longValue());
    }
}

