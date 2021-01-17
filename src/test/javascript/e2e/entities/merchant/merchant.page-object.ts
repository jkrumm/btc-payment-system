import { element, by, ElementFinder } from 'protractor';

export class MerchantComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-merchant div table .btn-danger'));
  title = element.all(by.css('jhi-merchant div h2#page-heading span')).first();
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

export class MerchantUpdatePage {
  pageTitle = element(by.id('jhi-merchant-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  emailInput = element(by.id('field_email'));

  feeSelect = element(by.id('field_fee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async feeSelectLastOption(): Promise<void> {
    await this.feeSelect.all(by.tagName('option')).last().click();
  }

  async feeSelectOption(option: string): Promise<void> {
    await this.feeSelect.sendKeys(option);
  }

  getFeeSelect(): ElementFinder {
    return this.feeSelect;
  }

  async getFeeSelectedOption(): Promise<string> {
    return await this.feeSelect.element(by.css('option:checked')).getText();
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

export class MerchantDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-merchant-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-merchant'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
