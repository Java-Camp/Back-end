package com.jcf.persistence.dao;

import com.jcf.persistence.dto.UserAccountDto;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
import com.jcf.vo.SpecialOperationVo;
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
import java.time.LocalDateTime;
import java.util.List;


@Repository
@AllArgsConstructor
@Slf4j
public class OperationDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;


    public List<SpecialOperationVo> getOperationsByCurrentDate(Long accountID) {
        return jdbcTemplate.query(
                "SELECT  OPERATION.SUM, CATEGORY.NAME FROM OPERATION" +
                        " INNER JOIN CATEGORY on CATEGORY.ID = OPERATION.CATEGORY_ID" +
                        " INNER JOIN ACCOUNT on ACCOUNT.ID = OPERATION.ACCOUNT_ID" +
                        " WHERE  ACCOUNT_ID = ? "+
                        " AND  TRUNC(OPERATION.DATE_TIME) = TRUNC(SYSDATE)",
                new RowMapper<SpecialOperationVo>() {
                    @Override
                    public SpecialOperationVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return SpecialOperationVo
                                .builder()
                                .sum(rs.getBigDecimal(1))
                                .category(rs.getString(2))
                                .build();
                    }
                }, accountID);
    }




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


