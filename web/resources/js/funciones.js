//Cuando la página esté cargada completamente
  $(document).ready(function(){
    //Cada 600 segundos(10 min.)(600000 milisegundos) se ejecutará la función refrescar
    setTimeout(refrescar, 600000);
  });
  function refrescar(){
    //Actualiza la página
    location.reload();
  }
  
  function mostrarContrasena(){
      var pass = document.getElementById("formLogin:itPasaporte");
      var eye = document.getElementById("formLogin:eye");
      if(pass.type == "password"){
          pass.type = "text";
          $("#verpass").removeClass("fa-eye-slash");
        $("#verpass").addClass("fa-eye");
        eye.title="Password mostrado - Click para ocultar";
      }else{
          pass.type = "password";
          $("#verpass").removeClass("fa-eye");
          $("#verpass").addClass("fa-eye-slash");
          eye.title="Password oculto - Click para mostrar";
      }
  }
  
   function mostrarContrasena2(){
      var pass2 = document.getElementById("formPasaporte:pasaporte");
      var eye2 = document.getElementById("formPasaporte:eye2");
      if(pass2.type == "password"){
          pass2.type = "text";
          $("#verpass2").removeClass("fa-eye-slash");
        $("#verpass2").addClass("fa-eye");
        eye2.title="Password mostrado - Click para ocultar";
      }else{
          pass2.type = "password";
          $("#verpass2").removeClass("fa-eye");
          $("#verpass2").addClass("fa-eye-slash");
          eye2.title="Password oculto - Click para mostrar";
      }
  }
  
   function mostrarContrasena3(){
      var pass3 = document.getElementById("formPasaporte:pasaporte2");
      var eye3 = document.getElementById("formPasaporte:eye3");
      if(pass3.type == "password"){
          pass3.type = "text";
          $("#verpass3").removeClass("fa-eye-slash");
        $("#verpass3").addClass("fa-eye");
        eye3.title="Password mostrado - Click para ocultar";
      }else{
          pass3.type = "password";
          $("#verpass3").removeClass("fa-eye");
          $("#verpass3").addClass("fa-eye-slash");
          eye3.title="Password oculto - Click para mostrar";
      }
  }