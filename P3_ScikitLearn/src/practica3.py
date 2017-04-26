"""
Carga de los distintos ficheros de la práctica
"""
import pandas as pd
import numpy as np


def getUsuariosEspeciales(df):
    """
    Obtiene los usuarios especiales (no alumnos).
    Aquellos que son capaces de hacer acciones propias del administrador o el profesor
    Como por ejemplo Nombre evento igual a "Módulo de curso actualizado" o "Rol asignado"

    dataFrame es el fichero de logs original

    """
    selecting_criteria = df['Nombre evento'].isin(["Módulo de curso actualizado", "Rol asignado"])

    return df[selecting_criteria]['Nombre completo del usuario'].unique().tolist()


def eliminaUsuariosEspeciales(dataFrame, especiales):
    """
    Elimina los usuarios especiales del log

    dataFrame es el fichero de logs original

    """
    one_hot_especiales = dataFrame['Nombre completo del usuario'].isin(especiales)
    # El nombre hace referencia a one hot encoding
    return dataFrame[one_hot_especiales.apply(lambda x: not x)]


def clasificaInteraccionesMEPRO(fila):
    """
    Devuelve la clase de una interaccion
    """
    if 'Carpeta: Material adicional' in fila['Contexto del evento']:
        return 'Adicional'
    elif any(interaction in fila['Contexto del evento'] for interaction in ('prácticas', 'código', 'proyecto')):
        return 'Practicas'
    else:
        return generic_interaction(fila)


def clasificaInteraccionesSISIN(fila):
    """
    Devuelve la clase de una interaccion
    """
    if any(w1 in fila['Contexto del evento'] and w2 in fila['Contexto del evento']
           for w1, w2 in (('URL', ''), ('Página', 'video'), ('Página', 'enlace'))):
        return 'Adicional'
    elif any(word in fila['Contexto del evento'] for word in ('prácticas', 'código', 'notebook')):
        return 'Practicas'
    else:
        return generic_interaction(fila)


def generic_interaction(fila):
    if 'Recurso' in fila['Componente']:
        return 'Teoria'
    elif 'Cuestionario' in fila['Componente']:
        return 'AutoEvaluacion'
    elif 'Comentarios de la entrega' in fila['Componente']:
        return 'Feedback'
    elif 'Foro' in fila['Componente']:
        return 'Foro'
    else:
        return 'Otro'


def addTipoInteraccion(df, f_tipos):
    """
    Haz una copia de df, añade el tipo y devuelve la copia
    sin modificar el original.
    Pista: usar Apply. Apply toma una Serie (la fila en este caso) y devuelve un valor
    """
    return df.assign(Interaccion=pd.Series(df.apply(f_tipos, axis=1)))


def eliminaIrrelevantes(df):
    """
    Devuelve un dataframe de logs sin todos los eventos de tipo Otro
    Pista: Máscara binaria
    """
    return df[df['Interaccion'] != 'Otro']


def getGlobalStats(df):
    """
    Devuelve el total de las interacciones de cada tipo.
    """
    df_n = df.assign(N_usuarios=pd.Series([1 for i in range(len(df))]))
    for column in df_n:
        if column != 'Interaccion' and column != 'N_usuarios':
            del df_n[column]
    return df_n.groupby('Interaccion').count()


def getIndividualStats(df):
    """
    Devuelve las estadísticas individuales de cada alumno, por tipo
    """

    df_nu = df.assign(values=pd.Series([1 for i in range(len(df))]))
    for column in df_nu:
        if column != 'Interaccion' and column != 'Nombre completo del usuario' and column != 'values':
            del df_nu[column]


    df_nu['Nombre completo del usuario'] = df_nu['Nombre completo del usuario'].apply(lambda fila: int(fila[8:]))

    df_result = df_nu.pivot_table(
        index='Nombre completo del usuario',
        columns='Interaccion', values='values',
        aggfunc='count')
    return df_result.fillna(0) # TODO quitar usuario.


def getCorrelaciones(df_asignatura, df_notas):
    """
    Recibe una tabla de estadísticas de los alumnos, la que devuelve getIndividualStats
    Y recibe un dataframe con las notas.
    Los combina y obtiene las correlaciones entre las distintas columnas y la nota.
    """
    df_asignatura_id = df_asignatura.copy()

    df_notas.Id = df_notas.Id.astype(np.float64)
    df_notas['Total 1ª convocatoria (Real)'] = \
        df_notas['Total 1ª convocatoria (Real)'].apply(lambda x: x if x != '-' else 0).astype(np.float64)
    df_notas['Total 2ª convocatoria (Real)'] = \
        df_notas['Total 2ª convocatoria (Real)'].apply(lambda x: x if x != '-' else 0).astype(np.float64)

    df_asignatura_id = pd.merge(df_asignatura_id, df_notas.set_index('Id'), left_index=True, right_index=True)

    return df_asignatura_id.corr(), df_asignatura_id

meLogs = pd.read_excel('../data/xlsAnon.xlsx', 'Sheet1', index_col=None, na_values=['NA'])
siLogs = pd.read_csv('../data/csvAnon.csv')

notasME = pd.read_csv('../data/meNotas.csv')
del notasME['Unnamed: 0']
notasSI = pd.read_csv('../data/siNotas.csv')

usuariosEspecialesME = getUsuariosEspeciales(meLogs)

dfME = eliminaUsuariosEspeciales(meLogs, usuariosEspecialesME)

dfME = addTipoInteraccion(dfME, clasificaInteraccionesMEPRO)

dfME = eliminaIrrelevantes(dfME)

dfMEstats_ind = getIndividualStats(dfME)

dfMEstats_corr, dfMErged = getCorrelaciones(dfMEstats_ind, notasME)

dfMErged_bool = dfMErged.copy()
dfMErged_bool['Total 1ª convocatoria (Real)'] = \
    dfMErged_bool['Total 1ª convocatoria (Real)'].apply(lambda x: 1 if x >= 5 else 0)
dfMErged_bool['Total 2ª convocatoria (Real)'] = \
    dfMErged_bool['Total 2ª convocatoria (Real)'].apply(lambda x: 1 if x >= 5 else 0)


# _________________2a_parte_____________________


import matplotlib.cm as cmx
import matplotlib.colors as colors
import matplotlib.pyplot as plt
import sklearn.cluster as cluster
import time

plot_kwds = {'alpha' : 0.5, 's' : 20, 'linewidths':0}


def get_cmap(N):
    '''Returns a function that maps each index in 0, 1, ... N-1 to a distinct
    RGB color.'''
    color_norm = colors.Normalize(vmin=0, vmax=N - 1)
    scalar_map = cmx.ScalarMappable(norm=color_norm, cmap='hsv')

    def map_index_to_rgb_color(index):
        return scalar_map.to_rgba(index)

    return map_index_to_rgb_color


def plot_clusters(data, algorithm, args, kwds):
    start_time = time.time()
    labels = algorithm(*args, **kwds).fit_predict(data)
    end_time = time.time()
    # Con seaborn
    # palette = sns.color_palette('deep', np.unique(labels).max() + 1)
    # colors = [palette[x] if x >= 0 else (0.0, 0.0, 0.0) for x in labels]
    # Con matplotlib
    palette = get_cmap(np.unique(labels).max() + 1)
    colors = [palette(x) if x >= 0 else (0.0, 0.0, 0.0) for x in labels]

    plt.scatter(data.T[0], data.T[1], c=colors, **plot_kwds)
    frame = plt.gca()
    frame.axes.get_xaxis().set_visible(False)
    frame.axes.get_yaxis().set_visible(False)
    plt.title('Clusters encontrados por {}'.format(str(algorithm.__name__)), fontsize=24)
    plt.text(-0.5, 0.7, 'Ejecutado en {:.2f} s'.format(end_time - start_time), fontsize=14)

plot_clusters(dfMErged_bool, cluster.KMeans, (), {'n_clusters':2})


