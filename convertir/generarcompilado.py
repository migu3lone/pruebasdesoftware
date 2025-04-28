import tkinter as tk
import subprocess
from threading import Thread
import os

# Función para ejecutar los comandos en segundo plano
def ejecutar_comandos():
    # Guardar la ruta actual
    ruta_actual = os.getcwd()

    # Lista de nombres de las carpetas
    nombres_carpetas = ["base", "bin", "hexa", "octa"]

    # Ejecutar los comandos en cada carpeta
    for nombre in nombres_carpetas:
        # Cambiar al directorio correspondiente
        os.chdir(nombre)
        
        # Comandos a ejecutar en segundo plano en cada carpeta
        comando_bison = f"bison -dy {nombre}.y"
        comando_flex = f"flex {nombre}.l"
        comando_gcc = f"gcc lex.yy.c y.tab.c -o {nombre} -lm"

        # Ejecutar los comandos
        subprocess.run(comando_bison, shell=True)
        subprocess.run(comando_flex, shell=True)
        subprocess.run(comando_gcc, shell=True)

        # Volver a la ruta anterior
        os.chdir(ruta_actual)

# Función para iniciar la interfaz gráfica
def iniciar_interfaz():
    # Crear ventana de interfaz Tkinter
    ventana = tk.Tk()

    # Mostrar la ventana
    ventana.mainloop()

# Ejecutar los comandos en segundo plano al iniciar la interfaz
Thread(target=ejecutar_comandos).start()

# Ejecutar la función para iniciar la interfaz gráfica en un hilo separado
Thread(target=iniciar_interfaz).start()



