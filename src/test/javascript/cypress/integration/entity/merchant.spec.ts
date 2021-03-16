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

describe('Merchant e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/merchants*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Merchants', () => {
    cy.intercept('GET', '/api/merchants*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Merchant').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Merchant page', () => {
    cy.intercept('GET', '/api/merchants*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('merchant');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Merchant page', () => {
    cy.intercept('GET', '/api/merchants*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Merchant');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Merchant page', () => {
    cy.intercept('GET', '/api/merchants*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Merchant');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Merchant', () => {
    cy.intercept('GET', '/api/merchants*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Merchant');

    cy.get(`[data-cy="name"]`)
      .type('Thüringen Licensed payment', { force: true })
      .invoke('val')
      .should('match', new RegExp('Thüringen Licensed payment'));

    cy.get(`[data-cy="email"]`).type('nR@.z3', { force: true }).invoke('val').should('match', new RegExp('nR@.z3'));

    cy.get(`[data-cy="forward"]`)
      .type('Computers Kirgisistan Customer', { force: true })
      .invoke('val')
      .should('match', new RegExp('Computers Kirgisistan Customer'));

    cy.setFieldSelectToLastOfEntity('fee');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/merchants*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Merchant', () => {
    cy.intercept('GET', '/api/merchants*').as('entitiesRequest');
    cy.intercept('GET', '/api/merchants/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/merchants/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('merchant');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('merchant').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/merchants*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('merchant');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
