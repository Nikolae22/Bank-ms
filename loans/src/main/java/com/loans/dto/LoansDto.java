package com.loans.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Loans")
@Data
public class LoansDto {

    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    @Schema(description = "Mobile number of Customer")
    private String mobileNumber;

    @NotEmpty
    @Schema(description = "Loan number of the customer")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    private String loanNumber;

    @NotEmpty
    @Schema(description = "Type of loan")
    private String loanType;

    @Positive
    @Schema(description = "Total loan amount")
    private int totalLoan;

    @PositiveOrZero
    @Schema(description = "Total loan amount paid")
    private int amountPaid;

    @PositiveOrZero
    @Schema(description = "Total outstanding amount against a loan")
    private  int outstandingAmount;


}
