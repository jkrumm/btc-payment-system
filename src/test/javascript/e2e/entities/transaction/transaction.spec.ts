import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TransactionComponentsPage, TransactionDeleteDialog, TransactionUpdatePage } from './transaction.page-object';

const expect = chai.expect;

describe('Transaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionComponentsPage: TransactionComponentsPage;
  let transactionUpdatePage: TransactionUpdatePage;
  let transactionDeleteDialog: TransactionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Transactions', async () => {
    await navBarPage.goToEntity('transaction');
    transactionComponentsPage = new TransactionComponentsPage();
    await browser.wait(ec.visibilityOf(transactionComponentsPage.title), 5000);
    expect(await transactionComponentsPage.getTitle()).to.eq('btcPaymentSystemApp.transaction.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(transactionComponentsPage.entities), ec.visibilityOf(transactionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Transaction page', async () => {
    await transactionComponentsPage.clickOnCreateButton();
    transactionUpdatePage = new TransactionUpdatePage();
    expect(await transactionUpdatePage.getPageTitle()).to.eq('btcPaymentSystemApp.transaction.home.createOrEditLabel');
    await transactionUpdatePage.cancel();
  });

  it('should create and save Transactions', async () => {
    const nbButtonsBeforeCreate = await transactionComponentsPage.countDeleteButtons();

    await transactionComponentsPage.clickOnCreateButton();

    await promise.all([
      transactionUpdatePage.setInitiatedAtInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      transactionUpdatePage.transactionTypeSelectLastOption(),
      transactionUpdatePage.setTxHashInput('txHash'),
      transactionUpdatePage.setFromAddressInput('fromAddress'),
      transactionUpdatePage.setToAddressInput('toAddress'),
      transactionUpdatePage.setExpectedAmountInput('5'),
      transactionUpdatePage.setAmountInput('5'),
      transactionUpdatePage.setServiceFeeInput('5'),
      transactionUpdatePage.setBtcPriceInput('5'),
      transactionUpdatePage.userSelectLastOption(),
      transactionUpdatePage.blockSelectLastOption(),
    ]);

    expect(await transactionUpdatePage.getInitiatedAtInput()).to.contain(
      '2001-01-01T02:30',
      'Expected initiatedAt value to be equals to 2000-12-31'
    );
    const selectedIsMempool = transactionUpdatePage.getIsMempoolInput();
    if (await selectedIsMempool.isSelected()) {
      await transactionUpdatePage.getIsMempoolInput().click();
      expect(await transactionUpdatePage.getIsMempoolInput().isSelected(), 'Expected isMempool not to be selected').to.be.false;
    } else {
      await transactionUpdatePage.getIsMempoolInput().click();
      expect(await transactionUpdatePage.getIsMempoolInput().isSelected(), 'Expected isMempool to be selected').to.be.true;
    }
    expect(await transactionUpdatePage.getTxHashInput()).to.eq('txHash', 'Expected TxHash value to be equals to txHash');
    expect(await transactionUpdatePage.getFromAddressInput()).to.eq(
      'fromAddress',
      'Expected FromAddress value to be equals to fromAddress'
    );
    expect(await transactionUpdatePage.getToAddressInput()).to.eq('toAddress', 'Expected ToAddress value to be equals to toAddress');
    expect(await transactionUpdatePage.getExpectedAmountInput()).to.eq('5', 'Expected expectedAmount value to be equals to 5');
    expect(await transactionUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await transactionUpdatePage.getServiceFeeInput()).to.eq('5', 'Expected serviceFee value to be equals to 5');
    expect(await transactionUpdatePage.getBtcPriceInput()).to.eq('5', 'Expected btcPrice value to be equals to 5');
    const selectedIsWithdrawed = transactionUpdatePage.getIsWithdrawedInput();
    if (await selectedIsWithdrawed.isSelected()) {
      await transactionUpdatePage.getIsWithdrawedInput().click();
      expect(await transactionUpdatePage.getIsWithdrawedInput().isSelected(), 'Expected isWithdrawed not to be selected').to.be.false;
    } else {
      await transactionUpdatePage.getIsWithdrawedInput().click();
      expect(await transactionUpdatePage.getIsWithdrawedInput().isSelected(), 'Expected isWithdrawed to be selected').to.be.true;
    }

    await transactionUpdatePage.save();
    expect(await transactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Transaction', async () => {
    const nbButtonsBeforeDelete = await transactionComponentsPage.countDeleteButtons();
    await transactionComponentsPage.clickOnLastDeleteButton();

    transactionDeleteDialog = new TransactionDeleteDialog();
    expect(await transactionDeleteDialog.getDialogTitle()).to.eq('btcPaymentSystemApp.transaction.delete.question');
    await transactionDeleteDialog.clickOnConfirmButton();

    expect(await transactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
