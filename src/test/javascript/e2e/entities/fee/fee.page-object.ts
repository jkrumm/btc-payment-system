import { element, by, ElementFinder } from 'protractor';

export class FeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-fee div table .btn-danger'));
  title = element.all(by.css('jhi-fee div h2#page-heading span')).first();
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

export class FeeUpdatePage {
  pageTitle = element(by.id('jhi-fee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  feeTypeSelect = element(by.id('field_feeType'));
  percentInput = element(by.id('field_percent'));
  percentSecureInput = element(by.id('field_percentSecure'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFeeTypeSelect(feeType: string): Promise<void> {
    await this.feeTypeSelect.sendKeys(feeType);
  }

  async getFeeTypeSelect(): Promise<string> {
    return await this.feeTypeSelect.element(by.css('option:checked')).getText();
  }

  async feeTypeSelectLastOption(): Promise<void> {
    await this.feeTypeSelect.all(by.tagName('option')).last().click();
  }

  async setPercentInput(percent: string): Promise<void> {
    await this.percentInput.sendKeys(percent);
  }

  async getPercentInput(): Promise<string> {
    return await this.percentInput.getAttribute('value');
  }

  async setPercentSecureInput(percentSecure: string): Promise<void> {
    await this.percentSecureInput.sendKeys(percentSecure);
  }

  async getPercentSecureInput(): Promise<string> {
    return await this.percentSecureInput.getAttribute('value');
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

export class FeeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-fee-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-fee'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
