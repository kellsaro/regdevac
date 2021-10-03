import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './registro-de-vacunacion.reducer';
import { IRegistroDeVacunacion } from 'app/shared/model/registro-de-vacunacion.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RegistroDeVacunacionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const registroDeVacunacionEntity = useAppSelector(state => state.registroDeVacunacion.entity);
  const loading = useAppSelector(state => state.registroDeVacunacion.loading);
  const updating = useAppSelector(state => state.registroDeVacunacion.updating);
  const updateSuccess = useAppSelector(state => state.registroDeVacunacion.updateSuccess);

  const handleClose = () => {
    props.history.push('/registro-de-vacunacion' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...registroDeVacunacionEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.userId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          tipoDeVacuna: 'SPUTNIK',
          ...registroDeVacunacionEntity,
          userId: registroDeVacunacionEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rdvApp.registroDeVacunacion.home.createOrEditLabel" data-cy="RegistroDeVacunacionCreateUpdateHeading">
            <Translate contentKey="rdvApp.registroDeVacunacion.home.createOrEditLabel">Create or edit a RegistroDeVacunacion</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="registro-de-vacunacion-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="registro-de-vacunacion-user"
                name="userId"
                data-cy="user"
                label={translate('rdvApp.registroDeVacunacion.empleado')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(user => (
                      <option value={user.id} key={user.id}>
                        {user.firstName + ' ' + user.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('rdvApp.registroDeVacunacion.tipoDeVacuna')}
                id="registro-de-vacunacion-tipoDeVacuna"
                name="tipoDeVacuna"
                data-cy="tipoDeVacuna"
                type="select"
              >
                <option value="SPUTNIK">{translate('rdvApp.TipoDeVacuna.SPUTNIK')}</option>
                <option value="ASTRAZENECA">{translate('rdvApp.TipoDeVacuna.ASTRAZENECA')}</option>
                <option value="PFIZER">{translate('rdvApp.TipoDeVacuna.PFIZER')}</option>
                <option value="JHONSONANDJHONSON">{translate('rdvApp.TipoDeVacuna.JHONSONANDJHONSON')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('rdvApp.registroDeVacunacion.fechaDeVacunacion')}
                id="registro-de-vacunacion-fechaDeVacunacion"
                name="fechaDeVacunacion"
                data-cy="fechaDeVacunacion"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rdvApp.registroDeVacunacion.numeroDeDosis')}
                id="registro-de-vacunacion-numeroDeDosis"
                name="numeroDeDosis"
                data-cy="numeroDeDosis"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/registro-de-vacunacion" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RegistroDeVacunacionUpdate;
