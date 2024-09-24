export interface Cuenta {
    id:                number;
    tipoCuenta:        string;
    numeroCuenta:      string;
    estadoCuenta:      string;
    saldo:             number;
    exentaGmf:         boolean;
    fechaCreacion:     Date;
    fechaModificacion: Date;
    cliente:           Cliente;
}

export interface Cliente {
    id:                number;
    tipoId:            string;
    numId:             number;
    nombre:            string;
    apellido:          string;
    correo:            string;
    fechaNacimiento:   Date;
    fechaCreacion:     Date;
    fechaModificacion: Date;
}
