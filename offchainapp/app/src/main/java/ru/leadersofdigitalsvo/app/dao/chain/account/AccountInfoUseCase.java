package ru.leadersofdigitalsvo.app.dao.chain.account;

import ru.leadersofdigitalsvo.app.dao.chain.agreement.GetAgreementUseCase;
import ru.leadersofdigitalsvo.app.dao.chain.billing.GetBillListForAccountUseCase;
import ru.leadersofdigitalsvo.app.model.ChainIdentity;
import ru.leadersofdigitalsvo.app.model.entity.Account;
import ru.leadersofdigitalsvo.app.model.entity.AccountInfo;
import ru.leadersofdigitalsvo.app.model.entity.Agreement;
import ru.leadersofdigitalsvo.app.model.entity.Bill;
import ru.leadersofdigitalsvo.common.model.AccountState;
import ru.leadersofdigitalsvo.common.model.AgreementState;
import ru.leadersofdigitalsvo.common.model.BillState;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AccountInfoUseCase {

    public AccountInfo run(ChainIdentity chainIdentity, String userName, String accountId) throws IOException {

        AccountState accountState = new GetAccountUseCase().run(chainIdentity, userName, accountId);
        AgreementState agreementState = new GetAgreementUseCase().run(chainIdentity, userName, accountState.getAgreementId());
        List<BillState> billStateList = new GetBillListForAccountUseCase().run(chainIdentity, userName, accountState.getAccountId());

        Account account = new Account(accountState.getAccountId(), accountState.getState(), accountState.getValue());
        Agreement agreement = new Agreement(agreementState.getAgreementId(), agreementState.getAccountId(), agreementState.getPeriod(), agreementState.getPeriodAmount(), agreementState.getPeriodPercent(), agreementState.getSafeAmount());
        List<Bill> billList = billStateList.stream()
                .map(bill -> new Bill(bill.getBillId(), bill.getAccountId(), bill.getAgreementId(), bill.getAmount(), bill.getState(), bill.getDate()))
                .collect(Collectors.toList());

        return new AccountInfo(account, agreement, billList);
    }
}
