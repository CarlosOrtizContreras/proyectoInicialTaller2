setTimeout(function(){
    let mensaje = document.querySelector(".alert");

    if(mensaje){
        mensaje.style.display ="none";
    }
},1500);

setTimeout(function () {
  let mensaje = document.querySelector(".error");

  if (mensaje) {
    mensaje.style.display = "none";
  }
}, 3000);