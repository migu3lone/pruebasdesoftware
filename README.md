### Diseño de una aplicación móvil para el monitoreo y gestión de citas de una clínica

- U18100659 Manrique Benites, Michael Francois - migu3lone
- U20207357 Anhuaman Flores, Sebastián José - bastianf20
- U21205889 Cabrera Sánchez, Víctor Alessandro Liebedich - vcabrerasanchez
- U21218023 Dávila Durazno, Jordán Paolo - jordanpd03
- U21210433 Diaz Belleza, Pedro Jesús - pedrodiazbelleza
- U21230302 Hinostroza Lurquin, Jorge Luis - jlhinostroza

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
