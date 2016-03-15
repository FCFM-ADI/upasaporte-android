# Android U-Pasaporte demo

Demo para integrar U-Pasaporte en una aplicación Android. 

Para poder ejecutar este demo, necesitas registrar tu servicio con ADI e implementar lo requerido para completar el flujo de autenticación. Información detallada para activar tu servicio e integrarlo con tus servidores lo encuentras en https://github.com/FCFM-ADI/upasaporte-server-demo

Una vez que tengas el servicio habilitado debes ingresar tu URL de redirección y el nombre de tu servicio en el archivo [UPasaporteActivity.java](https://github.com/FCFM-ADI/upasaporte-android/blob/master/app/src/main/java/cl/uchile/ing/adi/demoupasaporte/UPasaporteActivity.java#L21-L23).

* UPASAPORTE_SERVICE es el nombre del servicio en U-Pasaporte.
* UPASAPORTE_REDIRECT_URL es la URL de redirección que imprime el autenticador EXTERNO cuando el usuario ha sido correctamente autenticado tanto en U-Pasaporte como en el servidor EXTERNO. Este campo debe estar correctamente ingresado ya que la app lo necesita para reconocer que vienen los datos del usuario y la sesión. Solo debe ingresarse la URL, sin la parte de query, por ejemplo ```https://servicioexterno.cl/receptor.php``` y NO ```https://servicioexterno.cl/receptor.php?alias=...``` No es necesario que la URL exista realmente porque nunca es llamada, sino que es capturada antes.

Este demo asume la implementación del autenticador externo como en [external.php](https://github.com/FCFM-ADI/upasaporte-android/blob/master/server/external.php)

A diferencia de la demostración del servidor, en la URL se envía información del usuario para que el móvil despliegue la información en cuando llegue la respuesta. Luego el sessionId recibido puede ser utilizado por la app móvil para identificarse con el servidor EXTERNO.

# Contacto

Si tienes problemas con este demo, puedes ingresar un Issue o [contactarnos](https://www.u-cursos.cl/dev/paginas/contacto).
