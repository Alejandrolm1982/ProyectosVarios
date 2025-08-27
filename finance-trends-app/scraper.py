import pandas as pd
import json as json

def obtener_simbolos_sp500():
    url = 'https://en.wikipedia.org/wiki/List_of_S%26P_500_companies'
    tablas = pd.read_html(url)
    df = tablas[0]  # Primera tabla de la página
    simbolos = df['Symbol'].tolist()
    return simbolos

# Ejecutamos la función y guardamos en JSON
simbolos = obtener_simbolos_sp500()

# Guardar en archivo JSON
with open('symbols_sp500.json', 'w', encoding='utf-8') as f:
    json.dump(simbolos, f, indent=2)

print(f"✅ Guardados {len(simbolos)} símbolos en 'sp500_symbols.json'")


