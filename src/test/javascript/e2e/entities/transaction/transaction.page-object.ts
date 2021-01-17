import { element, by, ElementFinder } from 'protractor';

export class TransactionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-transaction div table .btn-danger'));
  title = element.all(by.css('jhi-transaction div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TransactionUpdatePage {
  pageTitle = element(by.id('jhi-transaction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  initiatedAtInput = element(by.id('field_initiatedAt'));
  transactionTypeSelect = element(by.id('field_transactionType'));
  isMempoolInput = element(by.id('field_isMempool'));
  txHashInput = element(by.id('field_txHash'));
  fromAddressInput = element(by.id('field_fromAddress'));
  toAddressInput = element(by.id('field_toAddress'));
  expectedAmountInput = element(by.id('field_expectedAmount'));
  amountInput = element(by.id('field_amount'));
  serviceFeeInput = element(by.id('field_serviceFee'));
  btcPriceInput = element(by.id('field_btcPrice'));
  isWithdrawedInput = element(by.id('field_isWithdrawed'));

  userSelect = element(by.id('field_user'));
  blockSelect = element(by.id('field_block'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setInitiatedAtInput(initiatedAt: string): Promise<void> {
    await this.initiatedAtInput.sendKeys(initiatedAt);
  }

  async getInitiatedAtInput(): Promise<string> {
    return await this.initiatedAtInput.getAttribute('value');
  }

  async setTransactionTypeSelect(transactionType: string): Promise<void> {
    await this.transactionTypeSelect.sendKeys(transactionType);
  }

  async getTransactionTypeSelect(): Promise<string> {
    return await this.transactionTypeSelect.element(by.css('option:checked')).getText();
  }

  async transactionTypeSelectLastOption(): Promise<void> {
    await this.transactionTypeSelect.all(by.tagName('option')).last().click();
  }

  getIsMempoolInput(): ElementFinder {
    return this.isMempoolInput;
  }

  async setTxHashInput(txHash: string): Promise<void> {
    await this.txHashInput.sendKeys(txHash);
  }

  async getTxHashInput(): Promise<string> {
    return await this.txHashInput.getAttribute('value');
  }

  async setFromAddressInput(fromAddress: string): Promise<void> {
    await this.fromAddressInput.sendKeys(fromAddress);
  }

  async getFromAddressInput(): Promise<string> {
    return await this.fromAddressInput.getAttribute('value');
  }

  async setToAddressInput(toAddress: string): Promise<void> {
    await this.toAddressInput.sendKeys(toAddress);
  }

  async getToAddressInput(): Promise<string> {
    return await this.toAddressInput.getAttribute('value');
  }

  async setExpectedAmountInput(expectedAmount: string): Promise<void> {
    await this.expectedAmountInput.sendKeys(expectedAmount);
  }

  async getExpectedAmountInput(): Promise<string> {
    return await this.expectedAmountInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setServiceFeeInput(serviceFee: string): Promise<void> {
    await this.serviceFeeInput.sendKeys(serviceFee);
  }

  async getServiceFeeInput(): Promise<string> {
    return await this.serviceFeeInput.getAttribute('value');
  }

  async setBtcPriceInput(btcPrice: string): Promise<void> {
    await this.btcPriceInput.sendKeys(btcPrice);
  }

  async getBtcPriceInput(): Promise<string> {
    return await this.btcPriceInput.getAttribute('value');
  }

  getIsWithdrawedInput(): ElementFinder {
    return this.isWithdrawedInput;
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async blockSelectLastOption(): Promise<void> {
    await this.blockSelect.all(by.tagName('option')).last().click();
  }

  async blockSelectOption(option: string): Promise<void> {
    await this.blockSelect.sendKeys(option);
  }

  getBlockSelect(): ElementFinder {
    return this.blockSelect;
  }

  async getBlockSelectedOption(): Promise<string> {
    return await this.blockSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TransactionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-transaction-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-transaction'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
