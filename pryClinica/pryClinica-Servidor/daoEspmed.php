<?php

require 'database.php';

class daoEspmed {
    function __construct() {

    }

    public static function getAll() {
        $sql = "SELECT especialidad.Especialidad as especialidad, concat_ws(', ',medico.Apellidos,medico.Nombres) as medico, FK_Especialidad, FK_Medico
from espmed inner join especialidad on espmed.FK_Especialidad=especialidad.IdEspecialidad
inner join medico on espmed.FK_Medico=medico.IdMedico
ORDER BY especialidad.Especialidad ASC";

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
        $sql = "SELECT especialidad.Especialidad as especialidad, concat_ws(', ',medico.Apellidos,medico.Nombres) as medico
from espmed inner join especialidad on espmed.FK_Especialidad=especialidad.IdEspecialidad
inner join medico on espmed.FK_Medico=medico.IdMedico
where FK_Especialidad like ?";

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
        $sql = "SELECT especialidad.Especialidad as especialidad, concat_ws(', ',medico.Apellidos,medico.Nombres) as medico
from espmed inner join especialidad on espmed.FK_Especialidad=especialidad.IdEspecialidad
inner join medico on espmed.FK_Medico=medico.IdMedico
where especialidad.Especialidad like ?
ORDER BY medico.Apellidos ASC";

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

    public static function update($var1, $var2, $var3) {
        //call actEspmed("C001", "P005", "E001", "M002", "2028-08-05", "08:11:33.000000");
        $sql = "call actEspmed(?, ?, ?)";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $state = $stmt->execute([$var1, $var2, $var3]);
            return $state;
        } catch (PDOException $e) {
            error_log("Error al intentar actualizar la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function insert($var2, $var3) {
		//INSERT INTO espmed (FK_Especialidad, FK_Medico) VALUES ('E016', 'M046')
        //call agrEspmed("P001", "E001", "M001", "2025-08-05", "08:11:33.000000");
        $sql = "call agrEspmed(?, ?)";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $state = $stmt->execute([$var2, $var3]);
            return $state;
        } catch (PDOException $e) {
            error_log("Error al intentar insertar en la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function delete($var1) {
        $sql = "DELETE FROM espmed WHERE IdEspmed=?";

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
