import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MerchantComponentsPage, MerchantDeleteDialog, MerchantUpdatePage } from './merchant.page-object';

const expect = chai.expect;

describe('Merchant e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let merchantComponentsPage: MerchantComponentsPage;
  let merchantUpdatePage: MerchantUpdatePage;
  let merchantDeleteDialog: MerchantDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Merchants', async () => {
    await navBarPage.goToEntity('merchant');
    merchantComponentsPage = new MerchantComponentsPage();
    await browser.wait(ec.visibilityOf(merchantComponentsPage.title), 5000);
    expect(await merchantComponentsPage.getTitle()).to.eq('btcPaymentSystemApp.merchant.home.title');
    await browser.wait(ec.or(ec.visibilityOf(merchantComponentsPage.entities), ec.visibilityOf(merchantComponentsPage.noResult)), 1000);
  });

  it('should load create Merchant page', async () => {
    await merchantComponentsPage.clickOnCreateButton();
    merchantUpdatePage = new MerchantUpdatePage();
    expect(await merchantUpdatePage.getPageTitle()).to.eq('btcPaymentSystemApp.merchant.home.createOrEditLabel');
    await merchantUpdatePage.cancel();
  });

  it('should create and save Merchants', async () => {
    const nbButtonsBeforeCreate = await merchantComponentsPage.countDeleteButtons();

    await merchantComponentsPage.clickOnCreateButton();

    await promise.all([
      merchantUpdatePage.setNameInput('name'),
      merchantUpdatePage.setEmailInput('I@#&gt;.rkt'),
      merchantUpdatePage.feeSelectLastOption(),
    ]);

    expect(await merchantUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await merchantUpdatePage.getEmailInput()).to.eq('I@#&gt;.rkt', 'Expected Email value to be equals to I@#&gt;.rkt');

    await merchantUpdatePage.save();
    expect(await merchantUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await merchantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Merchant', async () => {
    const nbButtonsBeforeDelete = await merchantComponentsPage.countDeleteButtons();
    await merchantComponentsPage.clickOnLastDeleteButton();

    merchantDeleteDialog = new MerchantDeleteDialog();
    expect(await merchantDeleteDialog.getDialogTitle()).to.eq('btcPaymentSystemApp.merchant.delete.question');
    await merchantDeleteDialog.clickOnConfirmButton();

    expect(await merchantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
