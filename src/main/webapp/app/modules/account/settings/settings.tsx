import React, { useEffect } from 'react';
import { Button, Col, Row } from 'reactstrap';
import { Translate, translate, ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { toast } from 'react-toastify';

import { locales, languages } from 'app/config/translation';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset } from './settings.reducer';

export const SettingsPage = () => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const successMessage = useAppSelector(state => state.settings.successMessage);

  useEffect(() => {
    dispatch(getSession());
    return () => {
      dispatch(reset());
    };
  }, []);

  useEffect(() => {
    if (successMessage) {
      toast.success(translate(successMessage));
    }
  }, [successMessage]);

  const handleValidSubmit = values => {
    dispatch(
      saveAccountSettings({
        ...account,
        ...values,
      })
    );
  };

  const isCedulaEcuatoriana = cedula => {
    if (cedula === null) return false;
    cedula = cedula.toString().trim();
    if (cedula.length !== 10) return false;

    const digitos = cedula.split('').flatMap(d => parseInt(d, 10));
    let suma = 0;

    for (let i = 0; i < 9; i++) {
      if (i % 2 === 0) {
        let mult = digitos[i] * 2;
        if (mult > 9) mult = mult - 9;
        suma += mult;
      } else {
        suma += digitos[i];
      }
    }
    const modulo = suma % 10;
    const verificador = modulo === 0 ? 0 : 10 - modulo;
    return verificador === digitos[9];
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="settings-title">
            <Translate contentKey="settings.title" interpolate={{ username: account.login }}>
              User settings for {account.login}
            </Translate>
          </h2>
          <ValidatedForm id="settings-form" onSubmit={handleValidSubmit} defaultValues={account}>
            <ValidatedField
              name="firstName"
              label={translate('settings.form.firstname')}
              id="firstName"
              placeholder={translate('settings.form.firstname.placeholder')}
              validate={{
                required: { value: true, message: translate('settings.messages.validate.firstname.required') },
                minLength: { value: 1, message: translate('settings.messages.validate.firstname.minlength') },
                maxLength: { value: 50, message: translate('settings.messages.validate.firstname.maxlength') },
              }}
              data-cy="firstname"
            />
            <ValidatedField
              name="lastName"
              label={translate('settings.form.lastname')}
              id="lastName"
              placeholder={translate('settings.form.lastname.placeholder')}
              validate={{
                required: { value: true, message: translate('settings.messages.validate.lastname.required') },
                minLength: { value: 1, message: translate('settings.messages.validate.lastname.minlength') },
                maxLength: { value: 50, message: translate('settings.messages.validate.lastname.maxlength') },
              }}
              data-cy="lastname"
            />
            <ValidatedField
              name="email"
              label={translate('global.form.email.label')}
              placeholder={translate('global.form.email.placeholder')}
              type="email"
              validate={{
                required: { value: true, message: translate('global.messages.validate.email.required') },
                minLength: { value: 5, message: translate('global.messages.validate.email.minlength') },
                maxLength: { value: 254, message: translate('global.messages.validate.email.maxlength') },
                validate: v => isEmail(v) || translate('global.messages.validate.email.invalid'),
              }}
              data-cy="email"
            />
            <ValidatedField
              name="cedula"
              label={translate('settings.form.cedula')}
              id="cedula"
              placeholder={translate('settings.form.cedula.placeholder')}
              validate={{
                required: { value: true, message: translate('global.messages.validate.cedula.required') },
                minLength: { value: 5, message: translate('global.messages.validate.cedula.minlength') },
                maxLength: { value: 254, message: translate('global.messages.validate.cedula.maxlength') },
                validate: v => isCedulaEcuatoriana(v) || translate('global.messages.validate.cedula.invalid'),
              }}
              data-cy="cedula"
            />
            <ValidatedField
              name="fechaDeNacimiento"
              label={translate('settings.form.fechaDeNacimiento')}
              id="cedula"
              placeholder={translate('settings.form.fechaDeNacimiento.placeholder')}
              validate={{
                required: { value: true, message: translate('global.messages.validate.fechaDeNacimiento.required') },
              }}
              data-cy="fechaDeNacimiento"
            />
            <ValidatedField
              name="direccion"
              label={translate('settings.form.direccion')}
              id="direccion"
              placeholder={translate('settings.form.direccion.placeholder')}
              validate={{
                required: { value: true, message: translate('global.messages.validate.direccion.required') },
                minLength: { value: 5, message: translate('global.messages.validate.direccion.minlength') },
                maxLength: { value: 100, message: translate('global.messages.validate.direccion.maxlength') },
              }}
              data-cy="direccion"
            />
            <ValidatedField
              name="celular"
              label={translate('settings.form.celular')}
              id="celular"
              placeholder={translate('settings.form.celular.placeholder')}
              validate={{
                required: { value: true, message: translate('global.messages.validate.celular.required') },
                minLength: { value: 10, message: translate('global.messages.validate.celular.minlength') },
                maxLength: { value: 10, message: translate('global.messages.validate.celular.maxlength') },
              }}
              data-cy="celular"
            />
            <ValidatedField type="select" id="langKey" name="langKey" label={translate('settings.form.language')} data-cy="langKey">
              {locales.map(locale => (
                <option value={locale} key={locale}>
                  {languages[locale].name}
                </option>
              ))}
            </ValidatedField>
            <Button color="primary" type="submit" data-cy="submit">
              <Translate contentKey="settings.form.button">Save</Translate>
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default SettingsPage;
