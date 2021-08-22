package com.jcf.service;
import com.jcf.persistence.dao.OperationDao;
import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.UserAccount;
import com.jcf.persistence.repository.AccountRepository;
import com.jcf.persistence.repository.OperationRepository;
import com.jcf.persistence.repository.UserAccountRepository;
import com.jcf.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.TIMESTAMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


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

    private boolean isControl(OperationDTO operationDTO, User user){
        for (UserAccount userAccount: userAccountRepository.findByUnique("USER_ID", user.getId())){
            if (userAccount.getAccount_id().equals(operationDTO.getAccountId()))
                return true;
        }
        return false;
    }

   
    public Operation updateOperation(OperationDTO operationDTO){
        if(operationRepository.findById(operationDTO.getId()).isEmpty() || Objects.isNull(operationDTO.getId()))
            throw new EntityNotFoundException(operationDTO.getId());

        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail);

        if(!isControl(operationDTO, user))
            throw new LockedAccessException("You can't do anything with account " + accountRepository.findById(operationDTO.getAccountId().longValue()).get().getAlias());

        Operation operation = operationRepository.findById(operationDTO.getId()).get();
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

        return operationRepository.saveOrUpdate(operation);

    @Override
    public Operation saveOperation(OperationDTO operationDTO) {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Operation operation = new Operation();
        User user = userRepository.findByEmail(userEmail);

        log.info("user id " + user.getId());

        if(!isControl(operationDTO, user))
            throw new LockedAccessException("You can't do anything with account " + accountRepository.findById(operationDTO.getAccountId().longValue()).get().getAlias());

/*        if (!userAccountRepository.findByUnique("userId", user.getId()).contains(operationDTO.getAccountId().longValue()))
            throw new LockedAccessException("You can't do anything with account " + accountRepository.findById(operationDTO.getAccountId().longValue()).get().getId());*/

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
        operation.setOperationId(operationDTO.getOperationId());
        operation.setCategoryId(operationDTO.getCategoryId());
        return operationRepository.saveOrUpdate(operation);
    }

    @Override
    public List<OperationVO> findOperationsByFilter(Long accountId, FilteredOperationDto filter) {
        return operationDao.getFilteredOperation(accountId, filter);
    }

    @Override
    public void delete(Long id){
        if (operationRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(id);
        operationRepository.delete(id);
        // todo make a change for accounts (update)
        if (operationRepository.findById(id).isPresent())
            throw new ServiceNotWorkingException("Delete");
    }
}
