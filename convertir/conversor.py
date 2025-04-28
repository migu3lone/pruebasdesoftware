#pyinstaller --onefile --noconsole --add-data "sound.mp3;." --add-data "utp.png;." --add-data "bin/bin.exe;bin" --add-data "octa/octa.exe;octa" --add-data "hexa/hexa.exe;hexa" --add-data "base/base.exe;base" conversor.py

from tkinter import *
from PIL import ImageTk, Image
import pygame
import tkinter as tk
import subprocess
import os
import sys

# Función para usar rutas absolutas (requerido para pyinstaller)
def resource_path(relative_path):
    try:
        base_path = sys._MEIPASS
    except Exception:
        base_path = os.path.abspath(".")

    return os.path.join(base_path, relative_path)

def convertir():
    #number = entry_number.get()
    number = int(entry_number.get())

    if option.get() == 1: valor=resource_path("./bin/bin"); val = f"{number}\n"
    elif  option.get() == 2: valor=resource_path("./octa/octa"); val = f"{number}\n"
    elif  option.get() == 3: valor=resource_path("./hexa/hexa"); val = f"{number}\n"
    elif  option.get() == 4: valor=resource_path("./base/base"); base = int(entry_base.get()); val = f"{number} {base}\n"
    #val = f"{number}{base}\n"

    try:
        process = subprocess.Popen([valor], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, creationflags=subprocess.CREATE_NO_WINDOW)#, text=True
        input_data = val
        output_data, error = process.communicate(input=input_data)

        if process.returncode == 0:
            result_label.config(text=f"{output_data.strip()}")
            #result_label.config(text=f"Decimal: {number} -> Hexadecimal: {output_data.strip()}")
        else:
            result_label.config(text=f"Error: {error}")
    except Exception as e:
        result_label.config(text=f"Error: {str(e)}")

def habilitar_frame():
    if option.get() == 4:
        habilitar_widgets(baseframe)
    else:
        deshabilitar_widgets(baseframe)

def habilitar_widgets(frame):
    for child in frame.winfo_children():
        child.config(state=tk.NORMAL)

def deshabilitar_widgets(frame):
    for child in frame.winfo_children():
        child.config(state=tk.DISABLED)

def reproducir_sonido():
    pygame.mixer.init()
    pygame.mixer.music.load(resource_path('sound.mp3'))  # Reemplaza 'tu_archivo_de_audio.mp3' con la ruta de tu archivo de audio
    pygame.mixer.music.play(-1)  # -1 indica que el sonido se reproducirá en bucle

def detener_sonido():
    pygame.mixer.music.stop()

def controlar_sonido():
    if varsound.get() == 1:
        reproducir_sonido()
    else:
        detener_sonido()

def center_window(window):
    window.update_idletasks()
    screen_width = window.winfo_screenwidth()
    screen_height = window.winfo_screenheight()
    window_width = window.winfo_width()
    window_height = window.winfo_height()
    x = (screen_width // 2) - (window_width // 2)
    y = (screen_height // 2) - (window_height // 2)
    window.geometry(f"+{x}+{y}")

def cargar_imagen(ruta, width, height):
    # Cargar la imagen con PIL
    imagen = Image.open(ruta)
    # Redimensionar la imagen según el ancho y alto proporcionados
    imagen = imagen.resize((width, height), Image.LANCZOS)
    # Convertir la imagen para su uso en Tkinter
    imagen_tk = ImageTk.PhotoImage(imagen)
    return imagen_tk

# Crear una ventana principal
root = tk.Tk()
root.title("Conversor de base")
window_width = 300
window_height = 325
root.geometry(f"{window_width}x{window_height}")
root.after(0, lambda: center_window(root))
#center_window(root, window_width, window_height)

#alinear a la iquierda
#width=20, anchor=tk.W,

#imagen
ruta = resource_path("utp.png")
imagen = cargar_imagen(ruta, 250, 54)
img = tk.Label(root, image=imagen)
img.image = imagen  # Se necesita mantener una referencia
img.pack(padx=20, pady=10)

# Crear un marco para organizar los elementos
frame = tk.Frame(root)
frame.pack(padx=20, pady=5)

#sonido
# Crear un marco para ingresar la base
soundframe = tk.Frame(frame)
soundframe.pack()
varsound = tk.IntVar()
varsound.set(2)
# Función para controlar el sonido
varsound.trace("w", lambda *args: controlar_sonido())
# Radiobutton para activar/desactivar el sonido
Radiobutton(soundframe, text="Activar Sonido", variable=varsound, value=1).grid(row=0, column=0)

Radiobutton(soundframe, text="Desactivar Sonido", variable=varsound, value=2).grid(row=0, column=1)

# Etiqueta y campo de entrada para ingresar el número
label_number = tk.Label(frame, text="Ingrese un número:")
label_number.pack()
entry_number = tk.Entry(frame)
entry_number.pack()

#Radiobutton
cuadro = tk.LabelFrame(frame, text="Convertir a:")
cuadro.pack(pady=10)
option = tk.IntVar()
option.set(1)
# Función para habilitar/deshabilitar el frame secundario
option.trace("w", lambda *args: habilitar_frame())
Radiobutton(cuadro, text="Binario", variable=option, value=1).grid(row=0, column=0)
Radiobutton(cuadro, text="Octal", variable=option, value=2).grid(row=0, column=1)
Radiobutton(cuadro, text="Hexadecimal", variable=option, value=3).grid(row=0, column=2)
Radiobutton(cuadro, text="Cambiar a cualquier base", variable=option, value=4).grid(row=1, column=0, columnspan=3)

# Crear un marco para ingresar la base
baseframe = tk.Frame(cuadro)
baseframe.grid(row=2, column=0, columnspan=3)

# Etiqueta y campo de entrada para ingresar la base
label_base = tk.Label(baseframe, text="Ingresar la base:", state=tk.DISABLED)
label_base.grid(row=0, column=0)
entry_base = tk.Entry(baseframe, state=tk.DISABLED)
entry_base.grid(row=0, column=1)

# Botón para iniciar la conversión
convert_button = tk.Button(frame, text="Convertir", command=convertir)
convert_button.pack(pady=5)

# Etiqueta para mostrar el resultado hexadecimal
result_label = tk.Label(frame, text="")
result_label.pack(pady=5)

# Ejecutar el bucle principal de la ventana
root.mainloop()
