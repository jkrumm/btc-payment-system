import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Transaction e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/transactions*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Transactions', () => {
    cy.intercept('GET', '/api/transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Transaction').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Transaction page', () => {
    cy.intercept('GET', '/api/transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('transaction');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Transaction page', () => {
    cy.intercept('GET', '/api/transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Transaction');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Transaction page', () => {
    cy.intercept('GET', '/api/transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Transaction');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Transaction', () => {
    cy.intercept('GET', '/api/transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Transaction');

    cy.get(`[data-cy="initiatedAt"]`).type('2021-01-19T05:53').invoke('val').should('equal', '2021-01-19T05:53');

    cy.get(`[data-cy="transactionType"]`).select('FORWARD_MERCHANT');

    cy.get(`[data-cy="txHash"]`).type('yellow', { force: true }).invoke('val').should('match', new RegExp('yellow'));

    cy.get(`[data-cy="expectedAmount"]`).type('6638').should('have.value', '6638');

    cy.get(`[data-cy="actualAmount"]`).type('23628').should('have.value', '23628');

    cy.get(`[data-cy="transactionFee"]`).type('46199').should('have.value', '46199');

    cy.get(`[data-cy="serviceFee"]`).type('7086').should('have.value', '7086');

    cy.get(`[data-cy="btcUsd"]`).type('88157').should('have.value', '88157');

    cy.get(`[data-cy="address"]`).type('mission-critical', { force: true }).invoke('val').should('match', new RegExp('mission-critical'));

    cy.get(`[data-cy="amount"]`).type('60942').should('have.value', '60942');

    cy.setFieldSelectToLastOfEntity('user');

    cy.setFieldSelectToLastOfEntity('merchant');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/transactions*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Transaction', () => {
    cy.intercept('GET', '/api/transactions*').as('entitiesRequest');
    cy.intercept('GET', '/api/transactions/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/transactions/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('transaction').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/transactions*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('transaction');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
