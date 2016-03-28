$(document).ready(function () {
    // Generate a simple captcha
    function randomNumber(min, max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
    ;
    $('#captchaOperation').html([randomNumber(1, 100), '+', randomNumber(1, 200), '='].join(' '));

    $('#formRegistro').formValidation({
        message: 'Porfavor diligencie  este campo',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            primerNombre: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor ingrese el primer nombre del coordinador.'
                    },
                    stringLength: {
                        min: 3,
                        max: 15,
                        message: 'El primer nombre por lo menos debe tener mas de dos caracteres y menos de 40 '
                    },
                    regexp: {
                        regexp: /^[a-zA-Z_\.]+$/,
                        message: 'Caracteres incorrectos solo puede usar letras'
                    }
                }
            },
            segundoNombre: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor ingrese el segundo nombre del coordinado'
                    },
                    stringLength: {
                        min: 3,
                        max: 15,
                        message: 'El segundo nombre por lo menos debe tener mas de dos caracteres y menos de 40 '
                    },
                    regexp: {
                        regexp: /^[a-zA-Z_\.]+$/,
                        message: 'Caracteres incorrectos solo puede usar letras'
                    }
                }
            },
            primerApellido: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor ingrese el primer apellido del coordinado'
                    },
                    stringLength: {
                        min: 3,
                        max: 15,
                        message: 'El primer apellido por lo menos debe tener mas de dos caracteres y menos de 40 '
                    },
                    regexp: {
                        regexp: /^[a-zA-Z_\.]+$/,
                        message: 'Caracteres incorrectos solo puede usar letras'
                    }
                }
            },
            segundoApellido: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor ingrese el segundo apellido del coordinado'
                    },
                    stringLength: {
                        min: 3,
                        max: 15,
                        message: 'El segundo apellido por lo menos debe tener mas de dos caracteres y menos de 40 '
                    },
                    regexp: {
                        regexp: /^[a-zA-Z_\.]+$/,
                        message: 'Caracteres incorrectos solo puede usar letras'
                    }
                }
            },
            documento: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor ingrese el documento del coordinador'
                    },
                    stringLength: {
                        min: 10,
                        max: 15,
                        message: 'El documento por lo menos debe tener como minimo 10 caracteres y menos de 15 '
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: 'Caracteres incorrectos solo puede usar numeros'
                    }
                }
            },
            telefono: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor ingrese el telefono del coordinador'
                    },
                    stringLength: {
                        min: 10,
                        max: 15,
                        message: 'El telefono por lo menos debe tener como minimo 10 caracteres y menos de 15 '
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: 'Caracteres incorrectos solo puede usar numeros'
                    }
                }
            },
            correo: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor digite el correo electronico'
                    },
                    regexp: {
                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                        message: 'Tenga encuenta que el correo consta de tres partes el nombre del usuario, la dirreccion, y el dominio'
                    }
                }
            },
            clave: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor digite la contraseña'
                    }
                }
            }, 
            gender: {
                validators: {
                    notEmpty: {
                        message: 'La jornada es requerida'
                    }
                }
            }, 
            ficha: {
                validators: {
                    notEmpty: {
                        message: 'Porfavor diligencie la ficha a la que pertenece'
                    }, regexp: {
                        regexp: /^[0-9]+$/,
                        message: 'Caracteres incorrectos solo puede usar numeros'
                    }
                }
            },
            nombrePrograma: {
                validators: {
                    notEmpty: {
                        message: 'El nombre del programa es requerido'
                    }
                }
            },
            numeroFicha: {
                validators: {
                    notEmpty: {
                        message: 'El numero de la ficha es requerido'
                    }
                }
            }
        }
    });
});