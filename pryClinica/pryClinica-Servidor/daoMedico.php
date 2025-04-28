<?php

require 'database.php';

class daoMedico {
    function __construct() {

    }

    public static function getAll() {
        $sql = "SELECT IdMedico, Apellidos, Nombres, DNI, CMP, Direccion, Celular
from medico
ORDER BY IdMedico ASC";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $stmt->execute();
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Error en la consulta a la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function getById($var1) {
        $sql = "SELECT IdMedico, Apellidos, Nombres, DNI, CMP, Direccion, Celular
from medico
where IdMedico like ?";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $stmt->execute([$var1]);
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Error en la consulta a la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function getByValue($var2) {
        $sql = "SELECT IdMedico, Apellidos, Nombres, DNI, CMP, Direccion, Celular
from medico
where DNI like ?
ORDER BY IdMedico ASC";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $param = "%" . $var2 . "%";
            $stmt->execute([$param]);
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }

	public static function getByEspecialidad($var3) {
        $sql = "SELECT IdMedico, Apellidos, Nombres, DNI, CMP, Direccion, Celular
from medico inner join espmed on medico.IdMedico=espmed.FK_Medico
where espmed.FK_Especialidad like ?
ORDER BY Apellidos ASC";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $param = "%" . $var3 . "%";
            $stmt->execute([$param]);
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }

    public static function update($var1, $var2, $var3, $var4, $var5, $var6, $var7, $var8) {
        //call actMedico("C001", "P005", "E001", "M002", "2028-08-05", "08:11:33.000000");
        $sql = "call actMedico(?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $state = $stmt->execute([$var1, $var2, $var3, $var4, $var5, $var6, $var7, $var8]);
            return $state;
        } catch (PDOException $e) {
            error_log("Error al intentar actualizar la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function insert($var2, $var3, $var4, $var5, $var6, $var7, $var8) {
        //call agrCita("P001", "E001", "M001", "2025-08-05", "08:11:33.000000");
        $sql = "call agrMedico(?, ?, ?, ?, ?, ?, ?)";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $state = $stmt->execute([$var2, $var3, $var4, $var5, $var6, $var7, $var8]);
            return $state;
        } catch (PDOException $e) {
            error_log("Error al intentar insertar en la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function delete($var1) {
        $sql = "DELETE FROM medico WHERE IdMedico=?";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $state = $stmt->execute([$var1]);
            return $state;
        } catch (PDOException $e) {
            error_log("Error al intentar eliminar en la base de datos: " . $e->getMessage());
            return false;
        }
    }
}

?>
