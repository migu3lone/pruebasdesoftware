<?php
  
/*$dao = 'daoEspmed';
$var1 = 'IdEspmed';
$var2 = 'FK_Especialidad';
$var3 = 'FK_Medico';*/

$dao = 'daoEspmed';
$var1 = 'var1';
$var2 = 'var2';
$var3 = 'var3';

header('Content-Type: application/json');
require 'daoEspmed.php';
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

    if (isset($_GET[$var1])) {
        $var1x = $_GET[$var1];
        $result = $dao::getById($var1x);

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

    } elseif (isset($_GET[$var2])) {
        $var2x = $_GET[$var2];
        $result = $dao::getByValue($var2x);

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

    } else {
        $result = $dao::getAll();

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
    global $dao, $var1, $var2, $var3;
    $input = json_decode(file_get_contents('php://input'), true);

    if (isset($input[$var1]) && isset($input[$var2]) && isset($input[$var3])) {
        $var1x = $input[$var1];
        $var2x = $input[$var2];
        $var3x = $input[$var3];

        if (!empty($dao::getById($var1x))) {
            $result = $dao::update($var1x, $var2x, $var3x);

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
            $result = $dao::insert($var2x, $var3x);

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

        if (!empty($dao::getById($var1x))) {
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
