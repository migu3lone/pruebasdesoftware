<?php

require 'database.php';

class daoLogin {
    function __construct() {

    }
	
	public static function getMedico($username, $password) {
        //$sql = "SELECT * FROM medico WHERE idMedico = ? AND Password = ?";
        $sql = "SELECT 'medico' AS Rol, Apellidos, Nombres FROM medico WHERE idMedico = ? AND Password = ?";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $stmt->execute([$username, $password]);
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }

	public static function getPaciente($username, $password) {
        //$sql = "SELECT * FROM paciente WHERE idPaciente = ? AND Password = ?";
		$sql = "SELECT 'paciente' AS Rol, Apellidos, Nombres FROM paciente WHERE idPaciente = ? AND Password = ?";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $stmt->execute([$username, $password]);
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }

	public static function getUsuario($username, $password) {//SELECT * FROM usuario WHERE IdUsuario = 'U001' AND Password = '12345678' AND Rol = 'general'
        //$sql = "SELECT * FROM usuario WHERE IdUsuario = ? AND Password = ?";
        $sql = "SELECT Rol, Apellidos, Nombres FROM usuario WHERE IdUsuario = ? AND Password = ?";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $stmt->execute([$username, $password]);
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }
	
	/*public static function getUsuario($username, $password) {
        $sql = "SELECT * FROM usuario WHERE IdUsuario = ? AND Password = ? AND Rol = 'administrativo'";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $stmt->execute([$username, $password]);
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }

	
    public static function getUsuarioByIdAndPassword($username, $password) {
        $conn = (new Database())->getInstance();
        $query = "SELECT * FROM usuario WHERE idUsuario = :username AND Password = :password";
        $stmt = $conn->prepare($query);
        $stmt->bindParam(':username', $username);
        $stmt->bindParam(':password', $password);
        $stmt->execute();
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }*/
}

?>
