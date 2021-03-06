package com.jcf.service;

import com.jcf.exceptions.EntityNotFoundException;
import com.jcf.exceptions.FieldIsNullException;
import com.jcf.exceptions.LockedAccessException;
import com.jcf.exceptions.ServiceNotWorkingException;
import com.jcf.persistence.dao.OperationDao;
import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.Operation;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.UserAccount;
import com.jcf.vo.*;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
import com.jcf.vo.SpecialOperationVo;
import com.jcf.persistence.repository.AccountRepository;
import com.jcf.persistence.repository.OperationRepository;
import com.jcf.persistence.repository.UserAccountRepository;
import com.jcf.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Async
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OperationServiceImpl implements OperationService{

    private final OperationDao operationDao;
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final AccountRepository accountRepository;

/*    private void update(Long id){
        Account account = accountRepository.findById(id).get();
        BigDecimal expenses = BigDecimal.ZERO;
        for(Operation operation: operationRepository.findByUnique("OPERATION_TYPE_ID", 21)) {
            if(id.equals(operation.getAccountId().longValue()))
                expenses = expenses.add(operation.getSum());
        }

        BigDecimal income = BigDecimal.ZERO;

        for(Operation operation: operationRepository.findByUnique("OPERATION_TYPE_ID", 81)) {
            if(id.equals(operation.getAccountId().longValue()))
                income = income.add(operation.getSum());
        }

        BigDecimal money = expenses.multiply(BigDecimal.valueOf(-1)).add(income);
        account.setMoneyBalance(money);
        accountRepository.saveOrUpdate(account);
    }*/

    private boolean isControl(OperationDTO operationDTO, User user){
        for (UserAccount userAccount: userAccountRepository.findByUnique("USER_ID", user.getId())){
            if (userAccount.getAccount_id().equals(operationDTO.getAccountId()))
                return true;
        }
        return false;
    }


    @Override
    public List<SpecialOperationVo> getOperationsByCurrentDate(Long accountID) {
        List<SpecialOperationVo> oldList = operationDao.getOperationsByCurrentDate(accountID);

        List<SpecialOperationVo> newList = new ArrayList<>();

        Map <String, BigDecimal> map = new HashMap<>();

        for (int i = 0; i < oldList.size() ; i++) {
            if (!map.containsKey(oldList.get(i).getCategory())){
                map.put(oldList.get(i).getCategory(),oldList.get(i).getSum());
            }
            else {
                map.put(oldList.get(i).getCategory(), map.get(oldList.get(i).getCategory()).add(oldList.get(i).getSum()));
            }
        }
        for (Map.Entry <String, BigDecimal> pair : map.entrySet()){
            newList.add(SpecialOperationVo
                    .builder()
                    .sum(pair.getValue())
                    .category(pair.getKey())
                    .build());
        }
        return newList;
    }

    public Operation updateOperation(OperationDTO operationDTO) {
        if (operationRepository.findById(operationDTO.getId()).isEmpty() || Objects.isNull(operationDTO.getId()))
            throw new EntityNotFoundException(operationDTO.getId());

        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail);

        if(!isControl(operationDTO, user))
            throw new LockedAccessException("You can't do anything with account " + accountRepository.findById(operationDTO.getAccountId().longValue()).get().getAlias());

        Operation operation = operationRepository.findById(operationDTO.getId()).get();

        Account account = accountRepository.findById(operation.getAccountId().longValue()).get();
        if(operation.getOperationTypeId().longValue() == 21){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 81){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 82){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
            account = accountRepository.findById(operation.getOperationId().longValue()).get();
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        account = accountRepository.findById(operation.getAccountId().longValue()).get();

        if(!Objects.isNull(operationDTO.getOperationId()))
            operation.setOperationId(operationDTO.getOperationId());
        if(!Objects.isNull(operationDTO.getSum()))
            operation.setSum(operationDTO.getSum());
        if(!Objects.isNull(operationDTO.getOperationTypeId()))
            operation.setOperationTypeId(operationDTO.getOperationTypeId());
        if(!Objects.isNull(operationDTO.getDateTime()))
            operation.setDateTime(operationDTO.getDateTime().atZone(OffsetDateTime.now().getOffset()).toLocalDateTime());
        if(!Objects.isNull(operationDTO.getCategoryId()))
            operation.setCategoryId(operationDTO.getCategoryId());

        if(!Objects.isNull(operationDTO.getAccountId()))
            operation.setAccountId(operationDTO.getAccountId());

        if(operation.getOperationTypeId().longValue() == 21){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            operation.setOperationId(null);
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 81){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            operation.setOperationId(null);
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 82){
            operation.setCategoryId(BigDecimal.valueOf(61));
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
            account = accountRepository.findById(operation.getOperationId().longValue()).get();
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }

        return operationRepository.saveOrUpdate(operation);
    }

    @Override
    public Operation saveOperation(OperationDTO operationDTO) {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Operation operation = new Operation();
        User user = userRepository.findByEmail(userEmail);

        log.info("user id " + user.getId());

        if(!isControl(operationDTO, user))
            throw new LockedAccessException("You can't do anything with account " + accountRepository.findById(operationDTO.getAccountId().longValue()).get().getAlias());

        if (Objects.isNull(operationDTO.getDateTime())) {
            operation.setDateTime(Instant.now().atZone(OffsetDateTime.now().getOffset()).toLocalDateTime());
        } else {
            operation.setDateTime(operationDTO.getDateTime().atZone(OffsetDateTime.now().getOffset()).toLocalDateTime());
        }

        if (Objects.isNull(operationDTO.getAccountId()))
            throw new FieldIsNullException("Account");
        if (Objects.isNull(operationDTO.getSum()))
            throw new FieldIsNullException("Sum");
        if (Objects.isNull(operationDTO.getOperationTypeId()))
            throw new FieldIsNullException("Operation Type");
        if (Objects.isNull(operationDTO.getCategoryId()))
            throw new FieldIsNullException("Category");

        operation.setSum(operationDTO.getSum());
        operation.setAccountId(operationDTO.getAccountId());
        operation.setOperationTypeId(operationDTO.getOperationTypeId());
        operation.setCategoryId(operationDTO.getCategoryId());

        Account account = accountRepository.findById(operation.getAccountId().longValue()).get();

        if(operation.getOperationTypeId().longValue() == 21){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 81){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 82){
            operation.setOperationId(operationDTO.getOperationId());
            operation.setCategoryId(BigDecimal.valueOf(61));
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
            account = accountRepository.findById(operation.getOperationId().longValue()).get();
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        return operationRepository.saveOrUpdate(operation);
    }

    @Override
    public List<OperationVO> findOperationsByFilter(Long accountId, FilteredOperationDto filter) {
        return operationDao.getFilteredOperation(accountId, filter);
    }

    @Override
    public List<ChartOperationVO> getOperationsTypeSumByDate(Long accountId, DateFilteredOperationVO filter) {

        List<OperationVO> list = operationDao.getOperationsTypeSumByDate(accountId, filter);
        List<OperationVO> listExpenses = list.stream().filter(vo -> vo.getType().equals("Expenses")).collect(Collectors.toList());

        List<OperationVO> listIncomes = list.stream().filter(vo -> vo.getType().equals("Incomes")).collect(Collectors.toList());
        List<OperationVO> listTransfers = list.stream().filter(vo -> vo.getType().equals("Transfers")).collect(Collectors.toList());


        Map<LocalDate, BigDecimal> expensesMap =  new HashMap<>();
        Map<LocalDate, BigDecimal> incomesMap =  new HashMap<>();
        Map<LocalDate, BigDecimal> transfersMap =  new HashMap<>();
        List<ChartOperationVO> result = new ArrayList<>();

        try {
            for (int i = 0; i < listExpenses.size() ; i++) {
                if(!expensesMap.containsKey(listExpenses.get(i).getDate().toLocalDate())){
                    expensesMap.put(listExpenses.get(i).getDate().toLocalDate(), listExpenses.get(i).getSum());
                }
                else {
                    expensesMap.put(listExpenses.get(i).getDate().toLocalDate(), expensesMap.get(listExpenses.get(i).getDate().toLocalDate()).add(listExpenses.get(i).getSum()));
                }
            }
            for (int i = 0; i < listIncomes.size() ; i++) {
                if(!incomesMap.containsKey(listIncomes.get(i).getDate().toLocalDate())){
                    incomesMap.put(listIncomes.get(i).getDate().toLocalDate(), listIncomes.get(i).getSum());
                }
                else{
                    incomesMap.put(listIncomes.get(i).getDate().toLocalDate(), incomesMap.get(listIncomes.get(i).getDate().toLocalDate()).add(listIncomes.get(i).getSum()));
                }
            }
            for (int i = 0; i < listTransfers.size() ; i++) {
                if(!transfersMap.containsKey(listTransfers.get(i).getDate().toLocalDate())){
                    transfersMap.put(listTransfers.get(i).getDate().toLocalDate(), listTransfers.get(i).getSum());
                }
                else{
                    transfersMap.put(listTransfers.get(i).getDate().toLocalDate(), incomesMap.get(listTransfers.get(i).getDate().toLocalDate()).add(listTransfers.get(i).getSum()));
                }
            }



            for (Map.Entry<LocalDate, BigDecimal> pair : expensesMap.entrySet()){
                result.add(ChartOperationVO.builder().date(pair.getKey()).expense(pair.getValue()).build());
            }
            for (Map.Entry<LocalDate, BigDecimal> pair : incomesMap.entrySet()){
                for (int i = 0; i < result.size(); i++) {
                    if (pair.getKey().equals(result.get(i).getDate())){
                        result.get(i).setIncome(pair.getValue());
                    }
                }

            }
            for (Map.Entry<LocalDate, BigDecimal> pair : transfersMap.entrySet()){
                for (int i = 0; i < result.size(); i++) {
                    if (pair.getKey().equals(result.get(i).getDate())){
                        result.get(i).setTransfer(pair.getValue());
                    }
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return result;

    }

    @Override
    public void delete(Long id){
        if (operationRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(id);
        Long oldId = operationRepository.findById(id).get().getAccountId().longValue();
        log.info("OLD ID "+ oldId);
        Operation operation = operationRepository.findById(id).get();
        operationRepository.delete(id);
        log.info("Deleted");
        Account account = accountRepository.findById(oldId).get();
        if(operation.getOperationTypeId().longValue() == 21){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 81){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        else if(operation.getOperationTypeId().longValue() == 82){
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() + operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
            account = accountRepository.findById(operation.getOperationId().longValue()).get();
            account.setMoneyBalance(BigDecimal.valueOf(account.getMoneyBalance().longValue() - operation.getSum().longValue()));
            accountRepository.saveOrUpdate(account);
        }
        accountRepository.saveOrUpdate(account);
        if (operationRepository.findById(id).isPresent())
            throw new ServiceNotWorkingException("Delete");
    }
}
