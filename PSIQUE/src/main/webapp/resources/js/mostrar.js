function mostrarJornada(id) {
    if (id == 1) {
        $("#Mañana").show();
        $("#Tarde").hide();
    }

    if (id == 2) {
        $("#Mañana").hide();
        $("#Tarde").show();
   }
   if (id == 3) {
        $("#cliente").hide();
        $("#proveedor").hide();
        $("#instructor").show();
   }
   
}

function mostrarBoton(id) {
    if (id == "nombreEmpresa") {
        $("#boton1").show();
    }

    if (id == "proveedor") {
        $("#cliente").hide();
        $("#proveedor").show();
        $("#boton1").hide();

        
   }
   if (id == "instructor") {
        $("#cliente").hide();
        $("#proveedor").show();
        $("#boton1").hide();

        
   }
   

   
}



