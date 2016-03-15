<?php
// La funcion debe imprimir una URL donde ADI dirigira al usuario en caso de exito o '-1' en caso de error
ini_set( "session.use_cookies",  0 );
ini_set( "magic_quotes_runtime", 0 );
ini_set( "magic_quotes_gpc",	 0 );


$firma = base64_decode( $_POST['firma'] );
unset( $_POST['firma'] );

$public_key = openssl_pkey_get_public( file_get_contents( 'https://www.u-cursos.cl/upasaporte/certificado' ) );
$result     = openssl_verify( array_reduce( $_POST, create_function( '$a,$b', 'return $a.$b;' ) ), $firma, $public_key );
openssl_free_key( $public_key );

// Si el resultado es negativo significa que el mensaje no est� siendo enviado por U-Pasaporte y por lo tanto debemos retornar un error.
if( ! $result ) exit( '-1' );

session_start();
exit( 'http://'.$_SERVER['SERVER_NAME'].'/receptor.php?alias='.$_POST['alias'].'&img='.$_POST['img']."&PHPSESSID=".session_id());
