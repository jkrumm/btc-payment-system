import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MerchantUserComponentsPage, MerchantUserDeleteDialog, MerchantUserUpdatePage } from './merchant-user.page-object';

const expect = chai.expect;

describe('MerchantUser e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let merchantUserComponentsPage: MerchantUserComponentsPage;
  let merchantUserUpdatePage: MerchantUserUpdatePage;
  let merchantUserDeleteDialog: MerchantUserDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MerchantUsers', async () => {
    await navBarPage.goToEntity('merchant-user');
    merchantUserComponentsPage = new MerchantUserComponentsPage();
    await browser.wait(ec.visibilityOf(merchantUserComponentsPage.title), 5000);
    expect(await merchantUserComponentsPage.getTitle()).to.eq('btcPaymentSystemApp.merchantUser.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(merchantUserComponentsPage.entities), ec.visibilityOf(merchantUserComponentsPage.noResult)),
      1000
    );
  });

  it('should load create MerchantUser page', async () => {
    await merchantUserComponentsPage.clickOnCreateButton();
    merchantUserUpdatePage = new MerchantUserUpdatePage();
    expect(await merchantUserUpdatePage.getPageTitle()).to.eq('btcPaymentSystemApp.merchantUser.home.createOrEditLabel');
    await merchantUserUpdatePage.cancel();
  });

  it('should create and save MerchantUsers', async () => {
    const nbButtonsBeforeCreate = await merchantUserComponentsPage.countDeleteButtons();

    await merchantUserComponentsPage.clickOnCreateButton();

    await promise.all([merchantUserUpdatePage.userSelectLastOption(), merchantUserUpdatePage.merchantSelectLastOption()]);

    await merchantUserUpdatePage.save();
    expect(await merchantUserUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await merchantUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last MerchantUser', async () => {
    const nbButtonsBeforeDelete = await merchantUserComponentsPage.countDeleteButtons();
    await merchantUserComponentsPage.clickOnLastDeleteButton();

    merchantUserDeleteDialog = new MerchantUserDeleteDialog();
    expect(await merchantUserDeleteDialog.getDialogTitle()).to.eq('btcPaymentSystemApp.merchantUser.delete.question');
    await merchantUserDeleteDialog.clickOnConfirmButton();

    expect(await merchantUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
