<?php

/**
 * Representa la estructura de la tabla
 * almacenadas en la base de datos
 */
require 'database.php';

//es el mismo nomque que en obtener_universidad 11
class daoEspecialidad {
    function __construct() {

    }

    /**
     * Retorna todas las filas de la tabla 'universidad'
     *
     * @return array Datos de todos los registros
     */

     /*
     SELECT IdCita, concat_ws(' ',paciente.Nombres,paciente.Apellidos) as FK_Paciente, especialidad.Especialidad as FK_Especialidad, concat_ws(' ',medico.Nombres,medico.Apellidos) as FK_Medico, Fecha, Hora
from cita inner join paciente on cita.FK_Paciente=paciente.IdPaciente
inner join especialidad on cita.FK_Especialidad=especialidad.IdEspecialidad
inner join medico on cita.FK_Medico=medico.IdMedico
ORDER BY IdCita ASC

SELECT IdCita, FK_Paciente, FK_Especialidad, FK_Medico, Fecha, Hora
        from cita
        ORDER BY IdCita ASC
     */
    public static function getAll() {
        $sql = "SELECT IdEspecialidad, Especialidad
from especialidad
ORDER BY IdEspecialidad ASC";

        try {
            // Preparar sentencia
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            // Ejecutar sentencia preparada
            $stmt->execute();
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            // Registrar el error
            error_log("Error en la consulta a la base de datos: " . $e->getMessage());
            return false;
        }
    }

/**
     * Obtiene los campos de una tabla con un identificador determinado
     *
     * @return mixed
     */
    public static function getById($var1) {//SELECT IdCita, concat_ws(' ',paciente.Nombres,paciente.Apellidos) as Paciente, especialidad.Especialidad, concat_ws(' ',medico.Nombres,medico.Apellidos) as Medico, Fecha, Hora
        // Consulta de la tabla
        $sql = "SELECT IdEspecialidad, Especialidad
from especialidad
where IdEspecialidad like ?";

        try {
            // Preparar sentencia
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            // Ejecutar sentencia preparada
            $stmt->execute([$var1]);
            // Capturar el resultado
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            // Registrar el error
            error_log("Error en la consulta a la base de datos: " . $e->getMessage());
            return false; // Indicar que ocurrió un error
        }
    }

    public static function getByValue($var2) {
        $sql = "SELECT IdEspecialidad, Especialidad
from especialidad
where Especialidad like ?
ORDER BY IdEspecialidad ASC";

        try {
            // Preparar sentencia
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            // Añadir el carácter comodín '%' a $var1 para usar en LIKE
            $param = "%" . $var2 . "%";
            // Ejecutar la sentencia preparada con el parámetro vinculado
            $stmt->execute([$param]);
            // Retornar los resultados
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            // Registrar el error
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }

    // Obtiene el ID de una tabla por su identificador
    public static function getIdById($var1) {
        //$sql = "SELECT Id FROM universidad WHERE Nombre = ?";
        $sql = "SELECT IdEspecialidad FROM especialidad WHERE IdEspecialidad = ?
ORDER BY IdEspecialidad ASC";

        try {
            // Preparar sentencia
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            // Ejecutar sentencia preparada
            $stmt->execute([$var1]);
            //$stmt->execute(array($var1));
            // Obtener el resultado
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            // Verificar si se encontró el resultado
            // En caso de no encontrar la universidad
            return $row && isset($row['IdEspecialidad']) ? $row['IdEspecialidad'] : null;
        } catch (PDOException $e) {
            // Registrar el error
            error_log("Error en la consulta a la base de datos: " . $e->getMessage());
            return -1; // Indicar que ocurrió un error
        }
    }

    /**
     * Actualiza un registro de la base de datos basado
     * en los nuevos valores relacionados con un identificador
     */
    public static function update($var1, $var2) {
        // Creando consulta UPDATE
        /*
        $sql = "UPDATE cita
            SET FK_Paciente=?, FK_Especialidad=?, FK_Medico=?, Fecha=?, Hora=?
            WHERE IdCita=?";*/
        //call actEspecialidad("C001", "P005", "E001", "M002", "2028-08-05", "08:11:33.000000");
        $sql = "call actEspecialidad(?, ?)";

        try {
            // Preparar la sentencia
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            // Relacionar y ejecutar la sentencia
            $state = $stmt->execute([$var1, $var2]);
            //$state = $stmt->execute([$var2, $var3, $var4, $var5, $var6, $var1]);
            //$stmt->execute(array($var2, $var3, $var4, $var5, $var6, $var1));
            // Verificar si la consulta se ejecutó correctamente
            return $state;
        } catch (PDOException $e) {
            // Registrar el error
            error_log("Error al intentar actualizar la base de datos: " . $e->getMessage());
            return false; // Indicar que ocurrió un error
        }
    }

    /**
     * Inserta un nuevo valor
     *
     * @return bool Resultado de la inserción
     */
    public static function insert($var2) {
        // Sentencia INSERT
        //$sql = "INSERT INTO cita (FK_Paciente, FK_Especialidad, FK_Medico, Fecha, Hora) VALUES (?, ?, ?, ?, ?)";
        //call agrEspecialidad("P001", "E001", "M001", "2025-08-05", "08:11:33.000000");
        $sql = "call agrEspecialidad(?)";

        try {
            // Preparar la sentencia
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            // Ejecutar la sentencia con los valores proporcionados
            $state = $stmt->execute([$var2]);
            //$state = $stmt->execute(array($var2, $var3, $var4, $var5, $var6));
            // Retornar el resultado de la ejecución
            return $state;
        } catch (PDOException $e) {
            // Registrar el error
            error_log("Error al intentar insertar en la base de datos: " . $e->getMessage());
            return false; // Indicar que ocurrió un error
        }
    }

    /**
     * Elimina el registro con el identificador especificado
     *
     * @return bool Resultado de la eliminación
     */
    public static function delete($var1) {
        // Sentencia DELETE
        $sql = "DELETE FROM especialidad WHERE IdEspecialidad=?";

        try {
            // Preparar la sentencia
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            // Ejecutar la sentencia con el parámetro proporcionado
            $state = $stmt->execute([$var1]);
            //$state = $stmt->execute(array($var1));
            // Retornar el resultado de la ejecución
            return $state;
        } catch (PDOException $e) {
            // Registrar el error
            error_log("Error al intentar eliminar en la base de datos: " . $e->getMessage());
            return false; // Indicar que ocurrió un error
        }
    }
}

?>
