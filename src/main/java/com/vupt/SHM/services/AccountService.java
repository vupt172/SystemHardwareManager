package com.vupt.SHM.services;

import com.vupt.SHM.DTO.AccountDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.entity.Account;
import com.vupt.SHM.repositories.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    ModelMapper modelMapper;

    public List<AccountDTO> findAll() {

        return accountRepo.findAll().stream()
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .collect(Collectors.toList());

    }

    public void save(AccountDTO accountDTO) {
        if (accountDTO.getId() == 0) {
            Account account = modelMapper.map(accountDTO, Account.class);
            accountRepo.save(account);
        } else {
            Account curAccount = accountRepo.findById(accountDTO.getId()).get();
            curAccount.setFullName(accountDTO.getFullName());
            curAccount.setSuspended(accountDTO.isSuspended());
            accountRepo.save(curAccount);
        }
    }
}
