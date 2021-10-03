import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './registro-de-vacunacion.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RegistroDeVacunacionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const registroDeVacunacionEntity = useAppSelector(state => state.registroDeVacunacion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="registroDeVacunacionDetailsHeading">
          <Translate contentKey="rdvApp.registroDeVacunacion.detail.title">RegistroDeVacunacion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{registroDeVacunacionEntity.id}</dd>
          <dt>
            <Translate contentKey="rdvApp.registroDeVacunacion.empleado">Empleado</Translate>
          </dt>
          <dd>
            {registroDeVacunacionEntity.user
              ? registroDeVacunacionEntity.user.firstName + '' + registroDeVacunacionEntity.user.lastName
              : ''}
          </dd>
          <dt>
            <span id="tipoDeVacuna">
              <Translate contentKey="rdvApp.registroDeVacunacion.tipoDeVacuna">Tipo De Vacuna</Translate>
            </span>
          </dt>
          <dd>{registroDeVacunacionEntity.tipoDeVacuna}</dd>
          <dt>
            <span id="fechaDeVacunacion">
              <Translate contentKey="rdvApp.registroDeVacunacion.fechaDeVacunacion">Fecha De Vacunacion</Translate>
            </span>
          </dt>
          <dd>
            {registroDeVacunacionEntity.fechaDeVacunacion ? (
              <TextFormat value={registroDeVacunacionEntity.fechaDeVacunacion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="numeroDeDosis">
              <Translate contentKey="rdvApp.registroDeVacunacion.numeroDeDosis">Numero De Dosis</Translate>
            </span>
          </dt>
          <dd>{registroDeVacunacionEntity.numeroDeDosis}</dd>
        </dl>
        <Button tag={Link} to="/registro-de-vacunacion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/registro-de-vacunacion/${registroDeVacunacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegistroDeVacunacionDetail;
