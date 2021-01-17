import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BlockComponentsPage, BlockDeleteDialog, BlockUpdatePage } from './block.page-object';

const expect = chai.expect;

describe('Block e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blockComponentsPage: BlockComponentsPage;
  let blockUpdatePage: BlockUpdatePage;
  let blockDeleteDialog: BlockDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Blocks', async () => {
    await navBarPage.goToEntity('block');
    blockComponentsPage = new BlockComponentsPage();
    await browser.wait(ec.visibilityOf(blockComponentsPage.title), 5000);
    expect(await blockComponentsPage.getTitle()).to.eq('btcPaymentSystemApp.block.home.title');
    await browser.wait(ec.or(ec.visibilityOf(blockComponentsPage.entities), ec.visibilityOf(blockComponentsPage.noResult)), 1000);
  });

  it('should load create Block page', async () => {
    await blockComponentsPage.clickOnCreateButton();
    blockUpdatePage = new BlockUpdatePage();
    expect(await blockUpdatePage.getPageTitle()).to.eq('btcPaymentSystemApp.block.home.createOrEditLabel');
    await blockUpdatePage.cancel();
  });

  it('should create and save Blocks', async () => {
    const nbButtonsBeforeCreate = await blockComponentsPage.countDeleteButtons();

    await blockComponentsPage.clickOnCreateButton();

    await promise.all([
      blockUpdatePage.setMinedAtInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      blockUpdatePage.setBlockInput('5'),
      blockUpdatePage.setAvailableInput('5'),
      blockUpdatePage.setEstimatedInput('5'),
      blockUpdatePage.setAvailableSpendableInput('5'),
      blockUpdatePage.setEstimatedSpendableInput('5'),
    ]);

    expect(await blockUpdatePage.getMinedAtInput()).to.contain('2001-01-01T02:30', 'Expected minedAt value to be equals to 2000-12-31');
    expect(await blockUpdatePage.getBlockInput()).to.eq('5', 'Expected block value to be equals to 5');
    expect(await blockUpdatePage.getAvailableInput()).to.eq('5', 'Expected available value to be equals to 5');
    expect(await blockUpdatePage.getEstimatedInput()).to.eq('5', 'Expected estimated value to be equals to 5');
    expect(await blockUpdatePage.getAvailableSpendableInput()).to.eq('5', 'Expected availableSpendable value to be equals to 5');
    expect(await blockUpdatePage.getEstimatedSpendableInput()).to.eq('5', 'Expected estimatedSpendable value to be equals to 5');

    await blockUpdatePage.save();
    expect(await blockUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await blockComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Block', async () => {
    const nbButtonsBeforeDelete = await blockComponentsPage.countDeleteButtons();
    await blockComponentsPage.clickOnLastDeleteButton();

    blockDeleteDialog = new BlockDeleteDialog();
    expect(await blockDeleteDialog.getDialogTitle()).to.eq('btcPaymentSystemApp.block.delete.question');
    await blockDeleteDialog.clickOnConfirmButton();

    expect(await blockComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
