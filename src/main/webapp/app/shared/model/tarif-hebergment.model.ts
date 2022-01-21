export interface ITarifHebergment {
  id?: number;
  libTarifHergement?: string;
}

export class TarifHebergment implements ITarifHebergment {
  constructor(public id?: number, public libTarifHergement?: string) {}
}
