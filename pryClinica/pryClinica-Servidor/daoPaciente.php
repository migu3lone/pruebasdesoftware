<?php

require 'database.php';

class daoPaciente {
    function __construct() {

    }

    public static function getAll() {
        $sql = "SELECT IdPaciente, Apellidos, Nombres, DNI, Sexo, Nacimiento, Correo, Direccion, Celular
from paciente
ORDER BY IdPaciente ASC";

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
        $sql = "SELECT IdPaciente, Apellidos, Nombres, DNI, Sexo, Nacimiento, Correo, Direccion, Celular
from paciente
where IdPaciente like ?";

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
        $sql = "SELECT IdPaciente, Apellidos, Nombres, DNI, Sexo, Nacimiento, Correo, Direccion, Celular
from paciente
where Apellidos like ?
ORDER BY IdPaciente ASC";

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
	
	public static function getByCita($var3) {
        $sql = "SELECT IdPaciente, Apellidos, Nombres, DNI, Sexo, Nacimiento, Correo, Direccion, Celular
from paciente inner join cita on paciente.IdPaciente=cita.FK_Paciente
where IdCita like ?
ORDER BY IdPaciente ASC";

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
	
	public static function listSexo() {
        $sql = "SELECT DISTINCT Sexo FROM paciente";
		
		try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $stmt->execute();
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            error_log("Ningún ID encontrado:" . $e->getMessage());
            return false;
        }
    }

	/*public List<String> listSexo() {
	    List<String> sexos = new ArrayList<>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery("SELECT DISTINCT Sexo FROM paciente", null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	            sexos.add(cursor.getString(0)); // Columna 'descripcion'
	        } while (cursor.moveToNext());
	    }
	    cursor.close();
	    return sexos;
	}*/

    public static function update($var1, $var2, $var3, $var4, $var5, $var6, $var7, $var8, $var9, $var10) {
        //call actPaciente("C001", "P005", "E001", "M002", "2028-08-05", "08:11:33.000000");
        $sql = "call actPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $state = $stmt->execute([$var1, $var2, $var3, $var4, $var5, $var6, $var7, $var8, $var9, $var10]);
            return $state;
        } catch (PDOException $e) {
            error_log("Error al intentar actualizar la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function insert($var2, $var3, $var4, $var5, $var6, $var7, $var8, $var9, $var10) {
        //call agrCita("P001", "E001", "M001", "2025-08-05", "08:11:33.000000");
        $sql = "call agrPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            $stmt = Database::getInstance()->getDb()->prepare($sql);
            $state = $stmt->execute([$var2, $var3, $var4, $var5, $var6, $var7, $var8, $var9, $var10]);
            return $state;
        } catch (PDOException $e) {
            error_log("Error al intentar insertar en la base de datos: " . $e->getMessage());
            return false;
        }
    }

    public static function delete($var1) {
        $sql = "DELETE FROM paciente WHERE IdPaciente=?";

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
