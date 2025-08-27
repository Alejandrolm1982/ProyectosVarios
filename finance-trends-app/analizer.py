import yfinance as yf
import pandas as pd
import numpy as np
import json
import time

# Función para calcular el RSI real
def calcular_rsi(cierres, periodo=14):

    """
    Calcula el RSI (Relative Strength Index) basado en precios de cierre.
    RSI mide la fuerza relativa del precio y puede indicar si una acción
    está sobrevendida (<30) o sobrecomprada (>70).
    """

    delta = cierres.diff()
    ganancia = delta.clip(lower=0)
    perdida = -delta.clip(upper=0)

    media_ganancia = ganancia.rolling(window=periodo).mean()
    media_perdida = perdida.rolling(window=periodo).mean()

    rs = media_ganancia / media_perdida
    rsi = 100 - (100 / (1 + rs))
    return rsi

# Función para clasificar las acciones en rangos de tiempo 
def clasificar_accion(fila):
    per = fila['PER']
    roe = fila['ROE (%)']
    rsi = fila['RSI']

    # Corto plazo: señal técnica (RSI muy bajo)
    if rsi < 30:
        return "Corto plazo"
    
    # Medio plazo: RSI bajo + empresa barata
    elif rsi < 40 and per < 15:
        return "Medio plazo"
    
    # Largo plazo: fundamentos sólidos
    elif roe > 20 and per < 15:
        return "Largo plazo"
    
    else:
        return "Neutral"
    
# Cargar símbolos desde JSON
with open('symbols_sp500.json', 'r', encoding='utf-8') as f:
    simbolos = json.load(f)

print(f"📥 Analizando {len(simbolos)} acciones del S&P 500...")

# Análisis por símbolo
resultados = []

for i, simbolo in enumerate(simbolos):
    try:
        #imprimir porque acción vamos
        print(f"{i+1}/{len(simbolos)} → Analizando {simbolo}")

        # Reemplazar punto por guion para que funcione con Yahoo
        symbol_yahoo = simbolo.replace('.', '-')
        ticker = yf.Ticker(symbol_yahoo)
        info = ticker.info
        historial = ticker.history(period='30d')

        if historial.empty:
            print(f"⚠️ Sin datos de historial para {simbolo}")
            continue

        # Calcular RSI real
        rsi_series = calcular_rsi(historial['Close'])
        rsi_actual = rsi_series.iloc[-1]

        # Obtener datos clave
        nombre = info.get('shortName', 'N/A')
        precio = info.get('currentPrice')
        pe_ratio = info.get('trailingPE')
        roe = info.get('returnOnEquity')  # en decimal
        volumen = info.get('volume')
        cap = info.get('marketCap')

        # Filtros básicos: infravaloradas y saludables
        if (
            pe_ratio and pe_ratio < 15 and       # PER bajo → puede estar infravalorada
            roe and roe > 0.10 and               # ROE alto → empresa eficiente
            rsi_actual and rsi_actual < 40       # RSI bajo → posible rebote (acción sobrevendida)
        ):
            resultados.append({
                'símbolo': simbolo,
                'nombre': nombre,
                'precio': precio,
                'PER': round(pe_ratio, 2),
                'ROE (%)': round(roe * 100, 2),
                'RSI': round(rsi_actual, 2),
                'Volumen': volumen,
                'Capitalización': cap
            })

        time.sleep(1)  # Evita sobrecargar la API con 1 o 0.3 para pruebas en prod

    except Exception as e:
        print(f"❌ Error con {simbolo} → {e}")

# Guardar resultados
df = pd.DataFrame(resultados)


# Aplica la función "clasificar_accion" a cada fila del DataFrame
df['Clasificación'] = df.apply(clasificar_accion, axis=1)
df = df.sort_values(by='ROE (%)', ascending=False)

# Guardar como CSV compatible con Excel
df.to_csv('acciones_filtradas.csv', index=False, encoding='latin1', sep=';')
print("✅ CSV guardado como 'acciones_filtradas.csv' (compatible con Excel)")

# Mostrar resumen de clasificaciones
conteo = df['Clasificación'].value_counts()
print("\n📊 Resumen por tipo de inversión:")
for tipo, cantidad in conteo.items():
    print(f"  - {tipo}: {cantidad} acciones")

# Al final de analizer.py
from grafico import generar_grafico_clasificacion

generar_grafico_clasificacion(df)


