<?php
// Definición de constantes para los parámetros de conexión
define('DB_HOST', 'localhost'); // Nombre del host
define('DB_NAME', 'bdclinica');  // Nombre de la base de datos
define('DB_USER', 'root');      // Nombre del usuario
define('DB_PASS', '');          // Contraseña

// Configuración de la conexión PDO
$dsn = "mysql:host=" . DB_HOST . ";dbname=" . DB_NAME . ";charset=utf8mb4";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];

try {
    // Crear una nueva instancia de PDO
    $pdo = new PDO($dsn, DB_USER, DB_PASS, $options);
} catch (\PDOException $e) {
    // Manejo de excepciones
    throw new \PDOException($e->getMessage(), (int)$e->getCode());
}
?>
