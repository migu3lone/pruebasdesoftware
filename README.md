### Diseño de una aplicación móvil para el monitoreo y gestión de citas de una clínica

- Lucas Román, José Alonso
- U18100659 Manrique Benites, Michael Francois - migu3lone
- U20215056 Tintaya Teran, Oscar Johao - 

# Editor.md

![](https://raw.githubusercontent.com/migu3lone/pryClinica/main/resources/banner.webp)

![](https://img.shields.io/github/stars/pandao/editor.md.svg) ![](https://img.shields.io/github/forks/pandao/editor.md.svg) ![](https://img.shields.io/github/tag/pandao/editor.md.svg) ![](https://img.shields.io/github/release/pandao/editor.md.svg) ![](https://img.shields.io/github/issues/pandao/editor.md.svg) ![](https://img.shields.io/bower/v/editor.md.svg)

**Instrucciones**

- Copiar los archivos en C:\XAMPP\htdocs\pryClinica
- Usar Android Studio Koala o superior
- Ejecutar el sql bdClinica_procedure.sql, si es que da error, ejecutar primero bdClinica_sinprocedure.sql y luego procedure.sql
- Si prueba la aplicacion virtualmente, activar el ip 10.0.2.2 en el archivo RetrofitClient
- Si lo prueba en un equipo real cambiar la ip en estos dos archivos:
- En app\java\com.migu3lone.pryclinica\connection\RetrofitClient - cambiar la ip por la de su equipo, usar ipconfig para obtener el ip
- En res\xml\network_security_config.xml
