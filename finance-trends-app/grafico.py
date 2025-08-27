import pandas as pd
import matplotlib.pyplot as plt

# Cargar el archivo clasificado
df = pd.read_csv('acciones_filtradas.csv', sep=';', encoding='latin1')

# Contar cuántas acciones hay en cada clasificación
conteo = df['Clasificación'].value_counts()

# Crear gráfico de pastel
plt.figure(figsize=(6, 6))
plt.pie(conteo.values, labels=conteo.index, autopct='%1.1f%%', startangle=140)
plt.title('Distribución de oportunidades por tipo de inversión')
plt.axis('equal')  # Hace que el pastel sea circular

# Mostrar el gráfico
plt.tight_layout()
plt.savefig('grafico_clasificacion.png', dpi=300)
plt.show()

def generar_grafico_clasificacion(df):
    conteo = df['Clasificación'].value_counts()
    plt.pie(conteo, labels=conteo.index, autopct='%1.1f%%')
    plt.title("Distribución de oportunidades por tipo de inversión")
    plt.show()
