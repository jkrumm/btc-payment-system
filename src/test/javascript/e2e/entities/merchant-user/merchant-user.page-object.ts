import { element, by, ElementFinder } from 'protractor';

export class MerchantUserComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-merchant-user div table .btn-danger'));
  title = element.all(by.css('jhi-merchant-user div h2#page-heading span')).first();
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

export class MerchantUserUpdatePage {
  pageTitle = element(by.id('jhi-merchant-user-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  userSelect = element(by.id('field_user'));
  merchantSelect = element(by.id('field_merchant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async merchantSelectLastOption(): Promise<void> {
    await this.merchantSelect.all(by.tagName('option')).last().click();
  }

  async merchantSelectOption(option: string): Promise<void> {
    await this.merchantSelect.sendKeys(option);
  }

  getMerchantSelect(): ElementFinder {
    return this.merchantSelect;
  }

  async getMerchantSelectedOption(): Promise<string> {
    return await this.merchantSelect.element(by.css('option:checked')).getText();
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

export class MerchantUserDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-merchantUser-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-merchantUser'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
