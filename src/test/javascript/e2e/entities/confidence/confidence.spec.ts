import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ConfidenceComponentsPage, ConfidenceDeleteDialog, ConfidenceUpdatePage } from './confidence.page-object';

const expect = chai.expect;

describe('Confidence e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let confidenceComponentsPage: ConfidenceComponentsPage;
  let confidenceUpdatePage: ConfidenceUpdatePage;
  let confidenceDeleteDialog: ConfidenceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Confidences', async () => {
    await navBarPage.goToEntity('confidence');
    confidenceComponentsPage = new ConfidenceComponentsPage();
    await browser.wait(ec.visibilityOf(confidenceComponentsPage.title), 5000);
    expect(await confidenceComponentsPage.getTitle()).to.eq('btcPaymentSystemApp.confidence.home.title');
    await browser.wait(ec.or(ec.visibilityOf(confidenceComponentsPage.entities), ec.visibilityOf(confidenceComponentsPage.noResult)), 1000);
  });

  it('should load create Confidence page', async () => {
    await confidenceComponentsPage.clickOnCreateButton();
    confidenceUpdatePage = new ConfidenceUpdatePage();
    expect(await confidenceUpdatePage.getPageTitle()).to.eq('btcPaymentSystemApp.confidence.home.createOrEditLabel');
    await confidenceUpdatePage.cancel();
  });

  it('should create and save Confidences', async () => {
    const nbButtonsBeforeCreate = await confidenceComponentsPage.countDeleteButtons();

    await confidenceComponentsPage.clickOnCreateButton();

    await promise.all([
      confidenceUpdatePage.confidenceTypeSelectLastOption(),
      confidenceUpdatePage.setConfirmationsInput('5'),
      confidenceUpdatePage.transactionSelectLastOption(),
    ]);

    expect(await confidenceUpdatePage.getConfirmationsInput()).to.eq('5', 'Expected confirmations value to be equals to 5');

    await confidenceUpdatePage.save();
    expect(await confidenceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await confidenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Confidence', async () => {
    const nbButtonsBeforeDelete = await confidenceComponentsPage.countDeleteButtons();
    await confidenceComponentsPage.clickOnLastDeleteButton();

    confidenceDeleteDialog = new ConfidenceDeleteDialog();
    expect(await confidenceDeleteDialog.getDialogTitle()).to.eq('btcPaymentSystemApp.confidence.delete.question');
    await confidenceDeleteDialog.clickOnConfirmButton();

    expect(await confidenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
