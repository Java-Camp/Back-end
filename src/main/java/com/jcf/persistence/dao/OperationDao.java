package com.jcf.persistence.dao;

import com.jcf.persistence.dto.UserAccountDto;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
import liquibase.pro.packaged.S;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.TIMESTAMP;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


@Repository
@AllArgsConstructor
@Slf4j
public class OperationDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;



//    public List<OperationVO> getAllUserAccounts(String userEmail) {
//
//        log.info("Getting all operations of current user from database");
//        User user = userRepository.findByEmail(userEmail);
//        if (user == null) {
//            log.error("User not found");
//            throw new UsernameNotFoundException("User not found");
//        } else log.error("User found: {}", userEmail);
//
//        return jdbcTemplate.query("SELECT OPERATION.DATE_TIME, OPERATION.SUM, OPERATION_TYPE.NAME, CATEGORY.NAME FROM OPERATION" +
//                " INNER JOIN OPERATION_TYPE on OPERATION_TYPE.ID = OPERATION.OPERATION_TYPE_ID" +
//                " INNER JOIN CATEGORY on CATEGORY.ID = OPERATION.CATEGORY_ID" +
//                " INNER JOIN ACCOUNT on ACCOUNT.ID = OPERATION.ACCOUNT_ID" +
//                " INNER JOIN USER_ACCOUNT on USER_ACCOUNT.ACCOUNT_ID  = ACCOUNT.ID" +
//                " WHERE USER_ID = ?", new RowMapper<OperationVO>() {
//            @Override
//            public OperationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return OperationVO
//                        .builder()
//                        .date(rs.getTimestamp(1).toInstant())
//                        .sum(rs.getBigDecimal(2))
//                        .type(rs.getString(3))
//                        .category(rs.getString(4))
//                        .build();
//            }
//        }, new Object[]{user.getId()});
//    }
//
//    public List<OperationVO> getAllUserAccountsByDate(String userEmail, Timestamp start, Timestamp end) {
//
//        log.info("Getting all operations by date of current user from database");
//        User user = userRepository.findByEmail(userEmail);
//        if (user == null) {
//            log.error("User not found");
//            throw new UsernameNotFoundException("User not found");
//        } else log.error("User found: {}", userEmail);
//
//        return jdbcTemplate.query("SELECT OPERATION.DATE_TIME, OPERATION.SUM, OPERATION_TYPE.NAME, CATEGORY.NAME FROM OPERATION" +
//                " INNER JOIN OPERATION_TYPE on OPERATION_TYPE.ID = OPERATION.OPERATION_TYPE_ID" +
//                " INNER JOIN CATEGORY on CATEGORY.ID = OPERATION.CATEGORY_ID" +
//                " INNER JOIN ACCOUNT on ACCOUNT.ID = OPERATION.ACCOUNT_ID" +
//                " INNER JOIN USER_ACCOUNT on USER_ACCOUNT.ACCOUNT_ID  = ACCOUNT.ID" +
//                " WHERE USER_ID = ? AND " + "DATE_TIME BETWEEN "
//                + "TO_TIMESTAMP('" + start + "','yyyy-MM-dd HH24:mi:ss.ff')" + " AND " + "TO_TIMESTAMP('" + end + "','yyyy-MM-dd HH24:mi:ss.ff')" +
//                " ORDER BY DATE_TIME DESC", new RowMapper<OperationVO>() {
//            @Override
//            public OperationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return OperationVO
//                        .builder()
//                        .date(rs.getTimestamp(1).toInstant())
//                        .sum(rs.getBigDecimal(2))
//                        .type(rs.getString(3))
//                        .category(rs.getString(4))
//                        .build();
//            }
//        }, new Object[]{user.getId()});
//    }
//
//    public List<OperationVO> getAllUserAccountsBySum(String userEmail, BigDecimal leftSum, BigDecimal rightSum) {
//
//        log.info("Getting all operations by sum of current user from database");
//        User user = userRepository.findByEmail(userEmail);
//        if (user == null) {
//            log.error("User not found");
//            throw new UsernameNotFoundException("User not found");
//        } else log.error("User found: {}", userEmail);
//
//        return jdbcTemplate.query("SELECT OPERATION.DATE_TIME, OPERATION.SUM, OPERATION_TYPE.NAME, CATEGORY.NAME FROM OPERATION" +
//                " INNER JOIN OPERATION_TYPE on OPERATION_TYPE.ID = OPERATION.OPERATION_TYPE_ID" +
//                " INNER JOIN CATEGORY on CATEGORY.ID = OPERATION.CATEGORY_ID" +
//                " INNER JOIN ACCOUNT on ACCOUNT.ID = OPERATION.ACCOUNT_ID" +
//                " INNER JOIN USER_ACCOUNT on USER_ACCOUNT.ACCOUNT_ID  = ACCOUNT.ID" +
//                " WHERE USER_ID = ? AND " + "SUM BETWEEN " + leftSum + " AND " + rightSum +
//                " ORDER BY DATE_TIME DESC", new RowMapper<OperationVO>() {
//            @Override
//            public OperationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return OperationVO
//                        .builder()
//                        .date(rs.getTimestamp(1).toInstant())
//                        .sum(rs.getBigDecimal(2))
//                        .type(rs.getString(3))
//                        .category(rs.getString(4))
//                        .build();
//            }
//        }, new Object[]{user.getId()});
//    }
//
//
//    public List<OperationVO> getAllUserAccountsByType(String userEmail, String opType) {
//
//        log.info("Getting all operations by type of current user from database");
//        User user = userRepository.findByEmail(userEmail);
//        if (user == null) {
//            log.error("User not found");
//            throw new UsernameNotFoundException("User not found");
//        } else log.error("User found: {}", userEmail);
//
//        return jdbcTemplate.query("SELECT OPERATION.DATE_TIME, OPERATION.SUM, OPERATION_TYPE.NAME, CATEGORY.NAME FROM OPERATION" +
//                " INNER JOIN OPERATION_TYPE on OPERATION_TYPE.ID = OPERATION.OPERATION_TYPE_ID" +
//                " INNER JOIN CATEGORY on CATEGORY.ID = OPERATION.CATEGORY_ID" +
//                " INNER JOIN ACCOUNT on ACCOUNT.ID = OPERATION.ACCOUNT_ID" +
//                " INNER JOIN USER_ACCOUNT on USER_ACCOUNT.ACCOUNT_ID  = ACCOUNT.ID" +
//                " WHERE USER_ID = ? AND  OPERATION_TYPE.NAME = '" + opType + "'"+
//                " ORDER BY DATE_TIME DESC", new RowMapper<OperationVO>() {
//            @Override
//            public OperationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return OperationVO
//                        .builder()
//                        .date(rs.getTimestamp(1).toInstant())
//                        .sum(rs.getBigDecimal(2))
//                        .type(rs.getString(3))
//                        .category(rs.getString(4))
//                        .build();
//            }
//        }, new Object[]{user.getId()});
//    }


    public List<OperationVO> getFilteredOperation(Long accountID, FilteredOperationDto filter) {



        return jdbcTemplate.query(
                "SELECT OPERATION.DATE_TIME, OPERATION.SUM, OPERATION_TYPE.NAME, CATEGORY.NAME FROM OPERATION" +
                " INNER JOIN OPERATION_TYPE on OPERATION_TYPE.ID = OPERATION.OPERATION_TYPE_ID" +
                " INNER JOIN CATEGORY on CATEGORY.ID = OPERATION.CATEGORY_ID" +
                " INNER JOIN ACCOUNT on ACCOUNT.ID = OPERATION.ACCOUNT_ID" +
                " WHERE  ACCOUNT_ID = ? AND OPERATION_TYPE.NAME = NVL(?, OPERATION_TYPE.NAME)" +
                " AND  CATEGORY.NAME = NVL(?, CATEGORY.NAME )" +
                " AND  OPERATION.DATE_TIME BETWEEN TO_TIMESTAMP(NVL(?,OPERATION.DATE_TIME))" +
                " AND TO_TIMESTAMP(NVL(?,OPERATION.DATE_TIME))",
                new RowMapper<OperationVO>() {
            @Override
            public OperationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return OperationVO
                        .builder()
                        .date(rs.getTimestamp(1).toLocalDateTime())
                        .sum(rs.getBigDecimal(2))
                        .type(rs.getString(3))
                        .category(rs.getString(4))
                        .build();
            }
        }, accountID, filter.getType(), filter.getCategory(), filter.getFirstDate(), filter.getSecondDate());
    }
}


