package com.loans.service.impl;

import com.loans.constants.LoansConstants;
import com.loans.dto.LoansDto;
import com.loans.entity.Loans;
import com.loans.exception.LoanAlreadyExistsException;
import com.loans.exception.ResourceNotFoundException;
import com.loans.mapper.LoansMapper;
import com.loans.repository.LoansRepository;
import com.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ILoansServiceImpl implements ILoansService {

    private  final LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans=loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " +mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber){
        Loans newLoan=new Loans();
        long randomMobileNumber= 100000000L+ new Random().nextInt(90000000);
        newLoan.setLoanNumber(Long.toString(randomMobileNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans=loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                ()->new ResourceNotFoundException("Loan", "LoanNumber",loansDto.getLoanNumber())
        );
        LoansMapper.mapToLoans(loansDto,loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loan=loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );
        loansRepository.deleteById(loan.getLoanId());
        return true;
    }


}
