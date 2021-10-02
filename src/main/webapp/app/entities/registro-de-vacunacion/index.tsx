import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RegistroDeVacunacion from './registro-de-vacunacion';
import RegistroDeVacunacionDetail from './registro-de-vacunacion-detail';
import RegistroDeVacunacionUpdate from './registro-de-vacunacion-update';
import RegistroDeVacunacionDeleteDialog from './registro-de-vacunacion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RegistroDeVacunacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RegistroDeVacunacionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RegistroDeVacunacionDetail} />
      <ErrorBoundaryRoute path={match.url} component={RegistroDeVacunacion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RegistroDeVacunacionDeleteDialog} />
  </>
);

export default Routes;
