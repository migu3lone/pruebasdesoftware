<?php
// Incluye la base de datos
require_once 'database.php';

// Recibir datos de la solicitud
$data = json_decode(file_get_contents("php://input"));

$idUsuario = $data->idUsuario;
$password = $data->password;

// Conectar a la base de datos
$db = database::getInstance()->getDb();

// Consultar el usuario
$query = "SELECT IdUsuario, Nombres, Rol, Password FROM usuario WHERE IdUsuario = :idUsuario";
$stmt = $db->prepare($query);
$stmt->bindParam(':idUsuario', $idUsuario, PDO::PARAM_STR);
$stmt->execute();

if ($stmt->rowCount() > 0) {
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
    
    // Verificar la contraseña sin cifrado
    if ($password === $user['Password']) {
        // Respuesta con éxito
        $response = array(
            'success' => true,
            'data' => array(
                'idUsuario' => $user['IdUsuario'],
                'nombres' => $user['Nombres'],
                'rol' => $user['Rol'] // Aquí añades el rol
            )
        );
    } else {
        // Contraseña incorrecta
        $response = array('success' => false, 'message' => 'Contraseña incorrecta');
    }
} else {
    // Usuario no encontrado
    $response = array('success' => false, 'message' => 'Usuario no encontrado');
}

// Devolver respuesta como JSON
echo json_encode($response);
?>
