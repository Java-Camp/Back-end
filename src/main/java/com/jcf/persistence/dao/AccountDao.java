package com.jcf.persistence.dao;

import com.jcf.orm.core.EntityMapper;
import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.UserAccount;
import com.jcf.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class AccountDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public List<Account> getAllAccounts(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        List<Account> accountList = new ArrayList<>();
        List<UserAccount>  userAccountList = jdbcTemplate.query("SELECT * FROM USER_ACCOUNT WHERE USER_ID = ?", new EntityMapper<>(UserAccount.class), new Object[]{user.getId()});
        for (UserAccount userAccount : userAccountList) {
            accountList.add(jdbcTemplate.queryForObject("SELECT * FROM ACCOUNT WHERE ID = ?", new EntityMapper<>(Account.class), new Object[]{userAccount.getAccount_id()}));
        }
        return accountList;
    }

    public int save(String userEmail, AccountDto accountDto) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        User user = userRepository.findByEmail(userEmail);
        String id_column = "ID";

        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ACCOUNT (ALIAS, LANGUAGE, MONEY, BALANCE_TYPE, CURRENCY_ID, ACCOUNT_TYPE_ID) VALUES(?, 'deprecated', 0.0, 'deprecated', ?, ?)", new String[]{id_column});
                    ps.setString(1, accountDto.getAlias());
                    ps.setLong(2, accountDto.getCurrencyId());
                    ps.setLong(3, accountDto.getAccountTypeId());
                    return ps;
                }
                , keyHolder);

        BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
        return jdbcTemplate.update("INSERT INTO USER_ACCOUNT (USER_ID, ACCOUNT_ID) VALUES(?, ?)", user.getId(), id.longValue());
    }
}
