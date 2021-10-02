import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { TipoDeVacuna } from 'app/shared/model/enumerations/tipo-de-vacuna.model';

export interface IRegistroDeVacunacion {
  id?: number;
  tipoDeVacuna?: TipoDeVacuna;
  fechaDeVacunacion?: string;
  numeroDeDosis?: number;
  user?: IUser | null;
}

export const defaultValue: Readonly<IRegistroDeVacunacion> = {};
