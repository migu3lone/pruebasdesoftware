<?php

/*$dao = 'daoEspecialidad';
$var1 = 'IdEspecialidad';
$var2 = 'Especialidad';*/

$dao = 'daoEspecialidad';
$var1 = 'var1';
$var2 = 'var2';

header('Content-Type: application/json');
require 'daoEspecialidad.php';
$requestMethod = $_SERVER['REQUEST_METHOD'];

switch($requestMethod) {
    case 'GET':
        handleGetRequest();
        break;
    case 'POST':
        handlePostRequest();
        break;
    case 'DELETE':
        handleDeleteRequest();
        break;
    default:
        echo json_encode([
            "status" => 405,
            "message" => "Método no permitido"
        ]);
        break;
}

function handleGetRequest() {
    global $dao, $var1, $var2;

    // Comprobar si se ha pasado el ID a través de la URL (por ejemplo, ?Id=valor)
    if (isset($_GET[$var1])) { // $_GET['Id'] // Si se pasa un ID
        $var1x = $_GET[$var1]; // $_GET['Id'] // Captura el valor del ID

    //if (isset($input[$var1])) {
    //    $var1x = $input[$var1];

        // Llamar a la función getById
        $result = $dao::getById($var1x);

        // Verificar si se encontró el resultado
        if ($result) {
            http_response_code(200);
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        } else {
            http_response_code(404);
            echo json_encode([
                "status" => 404,
                "message" => "ID no existe"
            ]);
        }

    // Comprobar si se ha pasado otro valor de búsqueda, por ejemplo, ?search=valor
    } elseif (isset($_GET[$var2])) { // $_GET['Id'] // Si se pasa un valor de búsqueda (como 'nombre', 'fecha', etc.)
        $var2x = $_GET[$var2]; // $_GET['Id'] // Captura el valor de búsqueda

    //if (isset($input[$var1])) {
    //    $var1x = $input[$var1];

        // Llamar a la función getByValue
        $result = $dao::getByValue($var2x);

        // Verificar si se encontró el resultado
        if ($result) {
            http_response_code(200);
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        } else {
            http_response_code(404);
            echo json_encode([
                "status" => 404,
                "message" => "No se encontraron coincidencias para el valor de búsqueda"
            ]);
        }

    // Si no se pasa ningún parámetro, obtener todos los registros
    } else {
        $result = $dao::getAll(); // Obtener todos los registros

        if ($result) {
            http_response_code(200);
            echo json_encode([
                "status" => 200,
                "data" => $result
            ]);
        } else {
            http_response_code(500);
            echo json_encode([
                "status" => 500,
                "message" => "Ha ocurrido un error"
            ]);
        }
    }
}

function handlePostRequest() {
    global $dao, $var1, $var2;
    $input = json_decode(file_get_contents('php://input'), true);

    if (isset($input[$var1]) && isset($input[$var2])) {
        $var1x = $input[$var1];
        $var2x = $input[$var2];

        // Verificar si existe el ID para decidir entre inserción o actualización
        if (!empty($dao::getById($var1x))) {
            // Si el ID existe, actualizar
            $result = $dao::update($var1x, $var2x);

            if ($result) {
                http_response_code(200);
                echo json_encode([
                    "status" => 200,
                    "message" => "Actualización exitosa"
                ]);
            } else {
                http_response_code(500);
                echo json_encode([
                    "status" => 500,
                    "message" => "No se pudo actualizar"
                ]);
            }

        } else {
            // Si el ID no existe, insertar
            $result = $dao::insert($var2x);

            if ($result) {
                http_response_code(201);
                echo json_encode([
                    "status" => 201,
                    "message" => "Inserción exitosa"
                ]);
            } else {
                http_response_code(500);
                echo json_encode([
                    "status" => 500,
                    "message" => "No se pudo insertar"
                ]);
            }
        }

    } else {
        http_response_code(400);
        echo json_encode([
            "status" => 400,
            "message" => "Datos incompletos"
        ]);
    }
}

function handleDeleteRequest() {
    global $dao, $var1;

    if (isset($_GET[$var1])) {
        $var1x = $_GET[$var1];

        // Verificar si existe el ID
        if (!empty($dao::getById($var1x))) {
            // Si el ID existe, eliminar
            $result = $dao::delete($var1x);

            if ($result) {
                http_response_code(200);
                echo json_encode([
                    "status" => 200,
                    "message" => "Eliminación exitosa"
                ]);
            } else {
                http_response_code(500);
                echo json_encode([
                    "status" => 500,
                    "message" => "No se pudo eliminar"
                ]);
            }

        } else {
            // Si el ID no existe
            http_response_code(404);
            echo json_encode([
                "status" => 404,
                "message" => "ID no existe"
            ]);
        }

    } else {
        http_response_code(400);
        echo json_encode([
            "status" => 400,
            "message" => "ID no proporcionado"
        ]);
    }
}
?>
