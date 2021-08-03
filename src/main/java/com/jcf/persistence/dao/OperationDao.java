package com.jcf.persistence.dao;


import com.jcf.persistence.model.Operation;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Repository
@Slf4j
public class OperationDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public OperationDao(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public boolean saveOperation(String userEmail, Operation operation) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else log.error("User found: {}", userEmail);
        String id_column = "ID";

    int counter = 0;

       counter = jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement("insert into OPERATION (DATE_TIME, \"SUM\", " +
                            "ACCOUNT_ID, OPERATION_TYPE_ID, CATEGORY_ID) " +
                            "VALUES(?, ?, ?, ?, ?)", new String[]{id_column});
                    ps.setTimestamp(1, Timestamp.valueOf("1980-05-20 02:00:00.000000"));
                    ps.setBigDecimal(2, operation.getSum());
                    ps.setLong(3, operation.getAccountId());
                    ps.setLong(4, operation.getOperationTypeId());
                    ps.setLong(5, operation.getCategoryId());
                    return ps;
                }
                , keyHolder);

        log.info("Saving new user operation to database");

         return counter > 0 ;


    }
}