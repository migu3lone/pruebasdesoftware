<?php

$dao = 'daoLogin'; // Nombre del DAO que gestionará las operaciones
$var1 = 'username'; // ID del usuario (idMedico, idPaciente, idUsuario)
$var2 = 'password'; // Contraseña del usuario

header('Content-Type: application/json');
require 'daoLogin.php';
$requestMethod = $_SERVER['REQUEST_METHOD'];

switch ($requestMethod) {
    case 'POST':
        handlePostRequest();
        break;
    default:
        echo json_encode([
            "status" => 405,
            "message" => "Método no permitido"
        ]);
        break;
}

function handlePostRequest() {
    global $dao, $var1, $var2;
    $input = json_decode(file_get_contents('php://input'), true);

    if (isset($input[$var1]) && isset($input[$var2])) {
        $username = $input[$var1];
        $password = $input[$var2];

        // Determinar el prefijo del username
        $prefix = strtoupper(substr($username, 0, 1));

        // Lógica según el prefijo
        $data = null;
        switch ($prefix) {
            case 'M': // Médico
                $data = $dao::getMedico($username, $password);
                break;
            case 'P': // Paciente
                $data = $dao::getPaciente($username, $password);
                break;
			case 'U': // Usuario general o administrativo
                $data = $dao::getUsuario($username, $password);
                break;
            default:
                http_response_code(400);
                echo json_encode([
                    "status" => 400,
                    "message" => "Prefijo del username no válido"
                ]);
                return;
        }

        // Validar resultado
        if ($data) {
            http_response_code(200);
            echo json_encode([
                "status" => 200,
                "message" => "Autenticación exitosa",
                "data" => $data
            ]);
        } else {
            http_response_code(401);
            echo json_encode([
                "status" => 401,
                "message" => "Credenciales incorrectas"
            ]);
        }

    } else {
        http_response_code(400);
        echo json_encode([
            "status" => 400,
            "message" => "Datos incompletos"
        ]);
    }
}

?>
