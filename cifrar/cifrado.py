from PIL import ImageTk, Image
from tkinter import messagebox, Radiobutton, IntVar
import pygame
import tkinter as tk
import os
import sys

# Función para usar rutas absolutas (requerido para pyinstaller)
def resource_path(relative_path):
    try:
        base_path = sys._MEIPASS
    except Exception:
        base_path = os.path.abspath(".")

    return os.path.join(base_path, relative_path)

# Función para verficar el texto
def procesar_texto(cifrar=True):
    crypto = crypto_entry.get()
    modulo = modulo_entry.get()
    texto = texto_entry.get().upper()
    decimacion = decimacion_entry.get()
    desplazamiento = desplazamiento_entry.get()
    
    #verificar errores de entradas
    if len(texto) == 0 or len(decimacion) == 0 or len(desplazamiento) == 0:
        messagebox.showerror("Error", "Ingresa texto, decimacion y desplazamiento.")
        return
    if not texto.replace(' ', '').isalpha():
        messagebox.showerror("Error", "Ingresa un texto válido (solo letras y espacios).")
        return
    if not decimacion.replace(' ', '').isalnum():
        messagebox.showerror("Error", "Ingresa una decimacion válida (número o texto).")
        return
    if not desplazamiento.replace(' ', '').isalnum():
        messagebox.showerror("Error", "Ingresa una desplazamiento válida (número o texto).")
        return
    
    # Seleccionar el alfabeto adecuado según el módulo
    alfabeto = alfa1 if modulo == 26 else alfa2

    # Verificar si el texto pertenece al alfabeto y regresar los índices
    texto_array = []
    try:
        for caracter in texto:
            if caracter == ' ':
                texto_array.append(' ')
            else:
                indice = alfabeto.index(caracter)
                if crypto == "cbase": indice = '{:02d}'.format(alfabeto.index(caracter))
                texto_array.append(indice)
    except ValueError:
        messagebox.showerror("Error", "No todos los caracteres del texto están en el alfabeto.")
        return
    
    # Procesar el desplazamiento
    desplazamiento_array = []
    try:
        if desplazamiento.replace(' ', '').isdigit():
            desplazamiento_array = [int(desplazamiento)]
        else:
            for caracter in desplazamiento.upper().replace(" ", ""):
                if caracter in alfabeto:
                    desplazamiento_array.append(alfabeto.index(caracter))
                else:
                    messagebox.showerror("Error", f"El carácter '{caracter}' no está en el alfabeto.")
                    return
    except ValueError as e:
        messagebox.showerror("Error", str(e))
        return

    # Verificar y procesar la decimación
    try:
        decimacion = int(decimacion)
    except ValueError:
        try:
            decimacion = alfabeto.index(decimacion.upper())
        except ValueError:
            messagebox.showerror("Error", "La decimación debe ser un número o una letra válida.")
            return
    decimacion_array = [decimacion]

    match crypto:
        case "afin":
            if cifrar is False and not decimacion == 1:
                messagebox.showerror("Error", "Solo decimacion = 1\nformula para != 1:\n((cifrado - desplazamiento) * decimacion ^ -1) mod n")
            if desplazamiento.isalpha():
                if not len(desplazamiento_array) == 1:
                    messagebox.showerror("Error", "El desplazamiento debe ser número o una sola letra.")
        case "cbase":
            txt = ['']  # Iniciar con un espacio en blanco en el resultado
            for item in texto_array:
                if item != ' ':
                    txt[-1] += str(item)  # Agregar el número al último elemento de la lista
                else:
                    txt.append('')  # Agregar un nuevo elemento (espacio en blanco) a la lista
            texto_array = [int(num) for num in txt]
        case "vernam":
            texto_array = [ord(char) for char in texto]
            desplazamiento_array = [ord(char) for char in desplazamiento]
        case "vigenere":
            """
            # Repetir arreglo B hasta que su longitud sea mayor o igual que la de arreglo A
            while len(desplazamiento_array) < len(texto):
                desplazamiento_array.extend(desplazamiento_array)  # Agregar el arreglo B a sí mismo
            # Cortar arreglo B si su longitud es mayor que la de arreglo A
            if len(desplazamiento_array) > len(texto):
                desplazamiento_array = desplazamiento_array[:len(texto)]"""
        
        

    # Ajustar la longitud de desplazamiento_array para que coincida con texto_array
    while len(desplazamiento_array) < len(texto_array):
        desplazamiento_array.extend(desplazamiento_array)
    
    desplazamiento_array = desplazamiento_array[:len(texto_array)]

    xdecimacion = list(map(int, decimacion_array))
    xdesplazamiento = list(map(int, desplazamiento_array))

    cifrado(crypto, modulo, texto_array, xdecimacion, xdesplazamiento, alfabeto, cifrar)

# Función para cifrar o descifrar el texto
def cifrado(crypto, modulo, texto_array, xdecimacion, xdesplazamiento, alfabeto, cifrar):
    # Caracteres para representar dígitos mayores a 9 (A-Z)
    digitos = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    if cifrar is True:
        match crypto:
            case "afin":#(mensaje * decimacion + clave) mod n | ((cifrado - desplazamiento) * decimacion ^ -1) mod n
                resultado = [alfabeto[(m * xdecimacion[0] + d) % len(alfabeto)] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            case "beaufort":#(clave - mensaje) mod n | alfabeto inverso
                resultado = [alfabeto[len(alfabeto)-1-((m - d + xdecimacion[0]) % len(alfabeto))] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            case "cbase":#digitos[m % d]
                resultado = [digitos[[m % d] + [m // d]] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            case "vigenere":#(mensaje + clave) mod n | (mensaje - clave) mod n
                resultado = [alfabeto[(m + d) % len(alfabeto)] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            case "vernam":#XOR(mensaje; clave)
                resultado = [chr(m ^ d) for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            
    #elif xdecimacion[0] == 1:
    elif cifrar is False: #and xdecimacion[0] == 1:
        match crypto:
            case "afin":#(mensaje * decimacion + clave) mod n | ((cifrado - desplazamiento) * decimacion ^ -1) mod n
                resultado = [alfabeto[(m * xdecimacion[0] - d) % len(alfabeto)] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                #resultado = [alfabeto[(m / xdecimacion[0] - d) % len(alfabeto)] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            case "beaufort":#(clave - mensaje) mod n | alfabeto inverso
                resultado = [alfabeto[len(alfabeto)-1-((m - d + xdecimacion[0]) % len(alfabeto))] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            case "vernam":#XOR(mensaje; clave)
                resultado = [chr(m ^ d) for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
            case "vigenere":#(mensaje + clave) mod n | (mensaje - clave) mod n
                resultado = [alfabeto[(m - d) % len(alfabeto)] if m != ' ' else ' ' for m, d in zip(texto_array, xdesplazamiento)]
                txt = ''.join(resultado)
    else:
        messagebox.showerror("Error", "Solo decimacion = 1")
    output_entry.delete(0, tk.END)
    output_entry.insert(0, txt)

# Función para reproducir o detener el sonido en bucle
def toggle_sound():
    global sound_playing
    if sound_playing:
        pygame.mixer.music.stop()
    else:
        pygame.mixer.music.play(-1)
    sound_playing = not sound_playing

def cargar_imagen(ruta, width, height):
    # Cargar la imagen con PIL
    imagen = Image.open(ruta)
    # Redimensionar la imagen según el ancho y alto proporcionados
    imagen = imagen.resize((width, height), Image.LANCZOS)
    # Convertir la imagen para su uso en Tkinter
    imagen_tk = ImageTk.PhotoImage(imagen)
    return imagen_tk

# Configuración de pygame para reproducir sonido
pygame.mixer.init()
#pygame.mixer.music.load("sound.mp3")
pygame.mixer.music.load(resource_path("sound.mp3"))  # Cambiar por la ruta de tu archivo de sonido

# Definir alfabetos personalizados
alfa1 = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
alfa2 = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']

# Inicializar pygame para el sonido
pygame.mixer.init()
sound_playing = False

# Crear la ventana
root = tk.Tk()
root.title("Criptografía")

# Obtener las dimensiones de la pantalla
screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()

# Configurar la posición de la ventana en el centro de la pantalla
window_width = 400
window_height = 470
x_position = (screen_width // 2) - (window_width // 2)
y_position = (screen_height // 2) - (window_height // 2)
root.geometry(f"{window_width}x{window_height}+{x_position}+{y_position}")

#imagen
#ruta = 'utp.png'
ruta = resource_path("utp.png")
imagen = cargar_imagen(ruta, 250, 54)
img = tk.Label(root, image=imagen)
img.image = imagen  # Se necesita mantener una referencia
img.pack(padx=20, pady=10)

# Crear un marco para el sonido
sound_frame = tk.LabelFrame(root, text="sonido:")
sound_frame.pack(padx=20, pady=5)

# Radiobutton para activar/desactivar el sonido
sound_button = tk.IntVar()
sound_button.set(0)

sound_on = tk.Radiobutton(sound_frame, text="On", variable=sound_button, value=1, command=toggle_sound)
sound_on.grid(row=0, column=0, padx=10)

sound_off = tk.Radiobutton(sound_frame, text="Off", variable=sound_button, value=0, command=toggle_sound)
sound_off.grid(row=0, column=1, padx=10)

# Crear un marco para los cifrados
crypto_frame = tk.LabelFrame(root, text="escoge el cifrado:")
crypto_frame.pack(padx=20, pady=5)

# Radiobuttons para escoger el cifrado
crypto_entry = tk.StringVar()
crypto_entry.set("afin")

Radiobutton(crypto_frame, text="Afin", variable=crypto_entry, value="afin").grid(row=0, column=0, padx=10)
Radiobutton(crypto_frame, text="Beaufort", variable=crypto_entry, value="beaufort").grid(row=0, column=1, padx=10)
Radiobutton(crypto_frame, text="C. base", variable=crypto_entry, value="cbase").grid(row=0, column=12, padx=10)
Radiobutton(crypto_frame, text="Vernam", variable=crypto_entry, value="vernam").grid(row=1, column=0, padx=10)
Radiobutton(crypto_frame, text="Vigenere", variable=crypto_entry, value="vigenere").grid(row=1, column=1, padx=10)

# Crear un marco para el modulo
modulo_frame = tk.Frame(root)
modulo_frame.pack(padx=20, pady=5)

# Radiobuttons para seleccionar el módulo
modulo_entry = tk.IntVar()
modulo_entry.set(26)

module_label = tk.Label(modulo_frame, text="Módulo:")
module_label.grid(row=0, column=0, padx=10)

modulo_26 = tk.Radiobutton(modulo_frame, text="26", variable=modulo_entry, value=26)
modulo_26.grid(row=0, column=1, padx=10)

modulo_27 = tk.Radiobutton(modulo_frame, text="27", variable=modulo_entry, value=27)
modulo_27.grid(row=0, column=2, padx=10)

# Crear un marco para los datos
datos_frame = tk.LabelFrame(root, text="ingresar datos:")
datos_frame.pack(padx=20, pady=5)

# Etiqueta y entrada para el texto
texto_label = tk.Label(datos_frame, text="Texto:")
texto_label.grid(row=0, column=0, padx=10, pady=5)
texto_entry = tk.Entry(datos_frame)
texto_entry.grid(row=0, column=1, padx=10, pady=5)

# Etiqueta y entrada para el desplazamiento
desplazamiento_label = tk.Label(datos_frame, text="Clave:")
desplazamiento_label.grid(row=1, column=0, padx=10, pady=5)
desplazamiento_entry = tk.Entry(datos_frame)
desplazamiento_entry.grid(row=1, column=1, padx=10, pady=5)

# Etiqueta y entrada para la decimacion
decimacion_label = tk.Label(datos_frame, text="Decimacion \n Desplazamiento:")
decimacion_label.grid(row=2, column=0, padx=10, pady=5)
decimacion_entry = tk.Entry(datos_frame)
decimacion_entry.grid(row=2, column=1, padx=10, pady=5)
decimacion_entry.insert(0, str(1))

# Botones para cifrar y descifrar
cipher_button = tk.Button(datos_frame, text="Cifrar", command=lambda: procesar_texto(cifrar=True))
cipher_button.grid(row=3, column=0, padx=10, pady=5)

decipher_button = tk.Button(datos_frame, text="Descifrar", command=lambda: procesar_texto(cifrar=False))
decipher_button.grid(row=3, column=1, padx=10, pady=5)

# Crear un marco para el resultado
resultado_frame = tk.Frame(root)
resultado_frame.pack(padx=20, pady=5)

# Etiqueta y entrada para el resultado
output_label = tk.Label(resultado_frame, text="Resultado:")
output_label.grid(row=0, column=0, padx=10)
output_entry = tk.Entry(resultado_frame)
output_entry.grid(row=0, column=1, padx=10)

root.mainloop()
