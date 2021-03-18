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

describe('Confidence e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/confidences*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Confidences', () => {
    cy.intercept('GET', '/api/confidences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Confidence').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Confidence page', () => {
    cy.intercept('GET', '/api/confidences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('confidence');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Confidence page', () => {
    cy.intercept('GET', '/api/confidences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Confidence');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Confidence page', () => {
    cy.intercept('GET', '/api/confidences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Confidence');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Confidence', () => {
    cy.intercept('GET', '/api/confidences*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Confidence');

    cy.get(`[data-cy="changeAt"]`).type('2021-01-19T09:02').invoke('val').should('equal', '2021-01-19T09:02');

    cy.get(`[data-cy="confidenceType"]`).select('INCOMING');

    cy.get(`[data-cy="confirmations"]`).type('5').should('have.value', '5');

    cy.setFieldSelectToLastOfEntity('transaction');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/confidences*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Confidence', () => {
    cy.intercept('GET', '/api/confidences*').as('entitiesRequest');
    cy.intercept('GET', '/api/confidences/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/confidences/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('confidence');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('confidence').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/confidences*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('confidence');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
