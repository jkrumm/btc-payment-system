import { element, by, ElementFinder } from 'protractor';

export class BlockComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-block div table .btn-danger'));
  title = element.all(by.css('jhi-block div h2#page-heading span')).first();
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

export class BlockUpdatePage {
  pageTitle = element(by.id('jhi-block-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  minedAtInput = element(by.id('field_minedAt'));
  blockHeightInput = element(by.id('field_blockHeight'));
  blockHashInput = element(by.id('field_blockHash'));
  availableInput = element(by.id('field_available'));
  estimatedInput = element(by.id('field_estimated'));
  availableSpendableInput = element(by.id('field_availableSpendable'));
  estimatedSpendableInput = element(by.id('field_estimatedSpendable'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMinedAtInput(minedAt: string): Promise<void> {
    await this.minedAtInput.sendKeys(minedAt);
  }

  async getMinedAtInput(): Promise<string> {
    return await this.minedAtInput.getAttribute('value');
  }

  async setBlockHeightInput(blockHeight: string): Promise<void> {
    await this.blockHeightInput.sendKeys(blockHeight);
  }

  async getBlockHeightInput(): Promise<string> {
    return await this.blockHeightInput.getAttribute('value');
  }

  async setBlockHashInput(blockHash: string): Promise<void> {
    await this.blockHashInput.sendKeys(blockHash);
  }

  async getBlockHashInput(): Promise<string> {
    return await this.blockHashInput.getAttribute('value');
  }

  async setAvailableInput(available: string): Promise<void> {
    await this.availableInput.sendKeys(available);
  }

  async getAvailableInput(): Promise<string> {
    return await this.availableInput.getAttribute('value');
  }

  async setEstimatedInput(estimated: string): Promise<void> {
    await this.estimatedInput.sendKeys(estimated);
  }

  async getEstimatedInput(): Promise<string> {
    return await this.estimatedInput.getAttribute('value');
  }

  async setAvailableSpendableInput(availableSpendable: string): Promise<void> {
    await this.availableSpendableInput.sendKeys(availableSpendable);
  }

  async getAvailableSpendableInput(): Promise<string> {
    return await this.availableSpendableInput.getAttribute('value');
  }

  async setEstimatedSpendableInput(estimatedSpendable: string): Promise<void> {
    await this.estimatedSpendableInput.sendKeys(estimatedSpendable);
  }

  async getEstimatedSpendableInput(): Promise<string> {
    return await this.estimatedSpendableInput.getAttribute('value');
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

export class BlockDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-block-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-block'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
