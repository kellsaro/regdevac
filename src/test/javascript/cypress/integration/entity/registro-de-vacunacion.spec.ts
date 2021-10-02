import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('RegistroDeVacunacion e2e test', () => {
  const registroDeVacunacionPageUrl = '/registro-de-vacunacion';
  const registroDeVacunacionPageUrlPattern = new RegExp('/registro-de-vacunacion(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/registro-de-vacunacions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/registro-de-vacunacions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/registro-de-vacunacions/*').as('deleteEntityRequest');
  });

  it('should load RegistroDeVacunacions', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('registro-de-vacunacion');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RegistroDeVacunacion').should('exist');
    cy.url().should('match', registroDeVacunacionPageUrlPattern);
  });

  it('should load details RegistroDeVacunacion page', function () {
    cy.visit(registroDeVacunacionPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityDetailsButtonSelector).first().click({ force: true });
    cy.getEntityDetailsHeading('registroDeVacunacion');
    cy.get(entityDetailsBackButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', registroDeVacunacionPageUrlPattern);
  });

  it('should load create RegistroDeVacunacion page', () => {
    cy.visit(registroDeVacunacionPageUrl);
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('RegistroDeVacunacion');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', registroDeVacunacionPageUrlPattern);
  });

  it('should load edit RegistroDeVacunacion page', function () {
    cy.visit(registroDeVacunacionPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        this.skip();
      }
    });
    cy.get(entityEditButtonSelector).first().click({ force: true });
    cy.getEntityCreateUpdateHeading('RegistroDeVacunacion');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.get(entityCreateCancelButtonSelector).click({ force: true });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', registroDeVacunacionPageUrlPattern);
  });

  it('should create an instance of RegistroDeVacunacion', () => {
    cy.visit(registroDeVacunacionPageUrl);
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('RegistroDeVacunacion');

    cy.get(`[data-cy="tipoDeVacuna"]`).select('JHONSONANDJHONSON');

    cy.get(`[data-cy="fechaDeVacunacion"]`).type('2021-10-02').should('have.value', '2021-10-02');

    cy.get(`[data-cy="numeroDeDosis"]`).type('79245').should('have.value', '79245');

    cy.setFieldSelectToLastOfEntity('user');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.wait('@postEntityRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(201);
    });
    cy.wait('@entitiesRequest').then(({ response }) => {
      expect(response.statusCode).to.equal(200);
    });
    cy.url().should('match', registroDeVacunacionPageUrlPattern);
  });

  it('should delete last instance of RegistroDeVacunacion', function () {
    cy.intercept('GET', '/api/registro-de-vacunacions/*').as('dialogDeleteRequest');
    cy.visit(registroDeVacunacionPageUrl);
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', response.body.length);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('registroDeVacunacion').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', registroDeVacunacionPageUrlPattern);
      } else {
        this.skip();
      }
    });
  });
});
