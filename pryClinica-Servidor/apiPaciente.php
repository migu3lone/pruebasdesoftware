<?php

/*$dao = 'daoPaciente';
$var1 = 'IdPaciente';
$var2 = 'Password';
$var3 = 'Apellidos';
$var4 = 'Nombres';
$var5 = 'DNI';
$var6 = 'Sexo';
$var7 = 'Nacimiento';
$var8 = 'Correo';
$var9 = 'Direccion';
$var10 = 'Celular';*/

$dao = 'daoPaciente';
$var1 = 'var1';
$var2 = 'var2';
$var3 = 'var3';
$var4 = 'var4';
$var5 = 'var5';
$var6 = 'var6';
$var7 = 'var7';
$var8 = 'var8';
$var9 = 'var9';
$var10 = 'var10';

// Define que las respuestas del servidor estarán en formato JSON
header('Content-Type: application/json');
// Incluye el archivo daoPaciente.php. Este archivo maneja las interacciones con la base de datos
require 'daoPaciente.php';
// Obtiene el método HTTP utilizado en la solicitud
$requestMethod = $_SERVER['REQUEST_METHOD'];

// Evalúa el método HTTP recibido y decide a qué función llamar
switch($requestMethod) {
    // GET llama a handleGetRequest(), que gestiona la solicitud GET.
    case 'GET':
        handleGetRequest();
        break;
    // POST llama a handlePostRequest(), que manejara las solicitudes para crear o modificar información
    case 'POST':
        handlePostRequest();
        break;
    // DELETE llama a handleDeleteRequest(), que probablemente elimine
    case 'DELETE':
        handleDeleteRequest();
        break;
    // Si el método no es uno de los anteriores,
    // devuelve un mensaje JSON con el código de estado 405 (Método no permitido)
    default:
        echo json_encode([
            "status" => 405,
            "message" => "Método no permitido"
        ]);
        break;
}

// Maneja solicitudes HTTP GET para recuperar datos desde una base de datos
function handleGetRequest() {
    global $dao, $var1, $var2, $var3, $varSexo;

    // Si la URL contiene el parámetro $var1, llama al método getById() del DAO
    // que consulta una base de datos para obtener la información de un paciente por su ID
    if (isset($_GET[$var1])) {
        $var1x = $_GET[$var1];
        $result = $dao::getById($var1x);

        // Si se encuentra un resultado,
        // se devuelve una respuesta JSON con el código HTTP 200 (OK) y los datos del recurso.
        if ($result) {
            http_response_code(200); // Código HTTP 200 (OK)
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        // Si no se encuentra el recurso, devuelve un error 404 (Not Found) con un mensaje "ID no existe"
        } else {
            http_response_code(404); // Código HTTP 404 (Not Found)
            echo json_encode([
                "status" => 404,
                "message" => "ID no existe"
            ]);
        }

    // Si la URL contiene el parámetro $var2, llama al método getByValue() del DAO
    // que busca pacientes cuyos apellidos coincidan (parcial o completamente) con el valor proporcionado
    } elseif (isset($_GET[$var2])) {
        $var2x = $_GET[$var2];
        $result = $dao::getByValue($var2x);

        if ($result) {
            http_response_code(200); // Código HTTP 200 (OK)
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        } else {
            http_response_code(404); // Código HTTP 404 (Not Found)
            echo json_encode([
                "status" => 404,
                "message" => "No se encontraron coincidencias para el valor de búsqueda"
            ]);
        }

    // Si la URL contiene el parámetro $var3, llama al método getByCita() del DAO
    // que busca al paciente que cuya cita coincida con el valor ingresado
    } elseif (isset($_GET[$var3])) {
        $var3x = $_GET[$var3];
        $result = $dao::getByCita($var3x);

        if ($result) {
            http_response_code(200); // Código HTTP 200 (OK)
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        } else {
            http_response_code(404); // Código HTTP 404 (Not Found)
            echo json_encode([
                "status" => 404,
                "message" => "No se encontraron coincidencias para el valor de búsqueda"
            ]);
        }

    } elseif (isset($_GET[$varSexo])) {
        $result = $dao::listSexo();

        if ($result) {
            http_response_code(200); // Código HTTP 200 (OK)
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        } else {
            http_response_code(500); // Código HTTP 500 (Internal Server Error)
            echo json_encode([
                "status" => 500,
                "message" => "Error al obtener los valores"
            ]);
        }

    // Si no se proporciona ningun parámetros, llama al método getAll() del DAO
    // que recupera todos los recursos disponibles
	} else {
        $result = $dao::getAll();

        if ($result) {
            http_response_code(200); // Código HTTP 200 (OK)
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        } else {
            http_response_code(500); // Código HTTP 500 (Internal Server Error)
            echo json_encode([
                "status" => 500,
                "message" => "Ha ocurrido un error"
            ]);
        }
    }
}

// Maneja solicitudes HTTP POST para actualizar o insertar datos en una base de datos
function handlePostRequest() {
    // Las variables globales ($var1, $var2, etc.)
    // representan nombres de campos específicos que se esperan recibir en la solicitud
    global $dao, $var1, $var2, $var3, $var4, $var5, $var6, $var7, $var8, $var9, $var10;
    // json_decode(..., true): Decodifica el contenido JSON en un arreglo asociativo de PHP
    // file_get_contents('php://input'): Obtiene los datos brutos del cuerpo de la solicitud POST
    $input = json_decode(file_get_contents('php://input'), true);

    // isset(): Verificar si todas las variables necesarias están presentes en los datos recibidos
    // para poder continuar con la operación
    if (isset($input[$var1]) && isset($input[$var2]) && isset($input[$var3]) && isset($input[$var4]) && isset($input[$var5]) && isset($input[$var6]) && isset($input[$var7]) && isset($input[$var8]) && isset($input[$var9]) && isset($input[$var10])) {
        $var1x = $input[$var1];
        $var2x = $input[$var2];
        $var3x = $input[$var3];
        $var4x = $input[$var4];
        $var5x = $input[$var5];
        $var6x = $input[$var6];
        $var7x = $input[$var7];
        $var8x = $input[$var8];
        $var9x = $input[$var9];
		$var10x = $input[$var10];

        // Si el resultado de getById() no está vacío, procederá a actualizarlo
        // llamando al método update() del DAO
        if (!empty($dao::getById($var1x))) {
            $result = $dao::update($var1x, $var2x, $var3x, $var4x, $var5x, $var6x, $var7x, $var8x, $var9x, $var10x);

            if ($result) {
                http_response_code(200); // Código 200: OK
                echo json_encode([
                    "status" => 200,
                    "message" => "Actualización exitosa"
                ]);
            } else {
                http_response_code(500); // Código 500: Error interno del servidor
                echo json_encode([
                    "status" => 500,
                    "message" => "No se pudo actualizar"
                ]);
            }

        // Si el resultado de getById() está vacío, procederá a insertar un nuevo registro en la base de datos
        // llamando al método insert() del DAO
        } else {
            $result = $dao::insert($var2x, $var3x, $var4x, $var5x, $var6x, $var7x, $var8x, $var9x, $var10x);

            if ($result) {
                http_response_code(201); // Código 201: Creado
                echo json_encode([
                    "status" => 201,
                    "message" => "Inserción exitosa"
                ]);
            } else {
                http_response_code(500); // Código 500: Error interno del servidor
                echo json_encode([
                    "status" => 500,
                    "message" => "No se pudo insertar"
                ]);
            }
        }

    // Si alguna de las variables necesarias ($var1 a $var10) no está presente en el cuerpo de la solicitud,
    // se envía una respuesta JSON indicando que los datos son incompletos
    } else {
        http_response_code(400); // Código 400: Solicitud incorrecta
        echo json_encode([
            "status" => 400,
            "message" => "Datos incompletos"
        ]);
    }
}

// Maneja solicitudes HTTP DELETE para eliminar un registro de la base de datos
function handleDeleteRequest() {
    global $dao, $var1;

    if (isset($_GET[$var1])) {
        $var1x = $_GET[$var1];

        // Si el resultado de getById() no está vacío, procederá a eliminarlo
        // llamando al método delete() del DAO
        if (!empty($dao::getById($var1x))) {
            $result = $dao::delete($var1x);

            if ($result) {
                http_response_code(200); // Código 200: OK
                echo json_encode([
                    "status" => 200,
                    "message" => "Eliminación exitosa"
                ]);
            } else {
                http_response_code(500); // Código 500: Error interno del servidor
                echo json_encode([
                    "status" => 500,
                    "message" => "No se pudo eliminar"
                ]);
            }

        // Si el resultado de getById() está vacío, significa que el id no existe
        } else {
            http_response_code(404); // Código 404: No encontrado
            echo json_encode([
                "status" => 404,
                "message" => "ID no existe"
            ]);
        }

    // Si no se proporciona ningún ID en la solicitud,
    // se envía una respuesta de error con el código 2 y un mensaje indicando que el ID no fue proporcionado.
    } else {
        http_response_code(400); // Código 400: Solicitud incorrecta
        echo json_encode([
            "status" => 400,
            "message" => "ID no proporcionado"
        ]);
    }
}
?>
