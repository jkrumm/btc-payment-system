import { element, by, ElementFinder } from 'protractor';

export class ConfidenceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-confidence div table .btn-danger'));
  title = element.all(by.css('jhi-confidence div h2#page-heading span')).first();
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

export class ConfidenceUpdatePage {
  pageTitle = element(by.id('jhi-confidence-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  confidenceTypeSelect = element(by.id('field_confidenceType'));
  confirmationsInput = element(by.id('field_confirmations'));

  transactionSelect = element(by.id('field_transaction'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setConfidenceTypeSelect(confidenceType: string): Promise<void> {
    await this.confidenceTypeSelect.sendKeys(confidenceType);
  }

  async getConfidenceTypeSelect(): Promise<string> {
    return await this.confidenceTypeSelect.element(by.css('option:checked')).getText();
  }

  async confidenceTypeSelectLastOption(): Promise<void> {
    await this.confidenceTypeSelect.all(by.tagName('option')).last().click();
  }

  async setConfirmationsInput(confirmations: string): Promise<void> {
    await this.confirmationsInput.sendKeys(confirmations);
  }

  async getConfirmationsInput(): Promise<string> {
    return await this.confirmationsInput.getAttribute('value');
  }

  async transactionSelectLastOption(): Promise<void> {
    await this.transactionSelect.all(by.tagName('option')).last().click();
  }

  async transactionSelectOption(option: string): Promise<void> {
    await this.transactionSelect.sendKeys(option);
  }

  getTransactionSelect(): ElementFinder {
    return this.transactionSelect;
  }

  async getTransactionSelectedOption(): Promise<string> {
    return await this.transactionSelect.element(by.css('option:checked')).getText();
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

export class ConfidenceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-confidence-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-confidence'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
