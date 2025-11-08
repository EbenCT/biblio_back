
## 1. OBJETIVO GENERAL

Diseñar e implementar un sistema de información inteligente para gestionar el ingreso, permanencia y salida de los usuarios en la biblioteca, aplicando microservicios, Machine Learning (supervisado, no supervisado y Deep Learning), y un agente inteligente automatizado, con soporte de múltiples bases de datos y despliegue multi-nube.

---

## 2. DIVISIÓN GENERAL DEL SISTEMA Y TECNOLOGÍAS

| Área funcional | Qué hace | Tecnología / Técnica recomendada | Justificación |
|----------------|----------|----------------------------------|---------------|
| **Microservicio de Control de Ingreso (Core)** | Registra entrada y salida mediante QR y credenciales del estudiante | Backend en Java Spring Boot + API REST/GraphQL + PostgreSQL | Es el núcleo transaccional; necesita consistencia y seguridad |
| **Módulo de Machine Learning (IA central)** | Analiza patrones de ingreso, detecta anomalías y realiza reconocimiento facial/video | Python (FastAPI + TensorFlow/PyTorch) | Python es ideal para IA; se encapsula en un microservicio aparte |
| **Módulo de NLP (Lenguaje Natural)** | Analiza correos o mensajes de WhatsApp/email de los usuarios para reconocer intenciones (ej. "Quiero reservar un espacio") | ML supervisado (NLP) — modelo BERT o similar | Cumple con el área de "procesamiento de lenguaje natural" |
| **Módulo de Procesamiento de Imagen** | Verifica identidad mediante rostro o QR, o analiza imágenes de cámaras de acceso | ML supervisado + Deep Learning (CNN) | Cumple con el área de "procesamiento de imagen" y Deep Learning |
| **Módulo de Procesamiento de Video** | Detecta patrones en video en tiempo real (entrada de múltiples personas, permanencia, objetos olvidados) | ML no supervisado + Deep Learning (Autoencoder, Clustering) | Cumple con el área "procesamiento de video" y el tipo "no supervisado" |
| **Agente Inteligente** | Automatiza consultas, genera reportes, envía notificaciones (WhatsApp, email), responde preguntas frecuentes | Python Agent (LangChain/RASA) + conexión a DB y uso de prompts | Cumple la parte de "automatización con agente inteligente" y "prompts IA" |
| **Dashboard de Gestión (Analítica)** | Muestra KPIs, estadísticas, rendimiento de modelos ML, reportes dinámicos | React + Node.js backend + MongoDB | Cumple con "dashboard sin Power BI" y "visualización de resultados ML" |
| **App móvil** | Permite escanear QR para ingresar, consultar historial, recibir notificaciones | Flutter (Dart) conectando al microservicio de Auth y Notificaciones | Cumple con requisito de "funcionalidades móviles estratégicas" |

---

## 3. APLICACIÓN DE LOS TRES TIPOS DE MACHINE LEARNING

| Tipo de ML | Área aplicada | Objetivo | Ejemplo de modelo |
|------------|---------------|----------|-------------------|
| **Supervisado** | NLP (lenguaje natural) e imagen (reconocimiento facial) | Entrenar modelos con ejemplos etiquetados para clasificación de intención o validación de rostro | BERT (NLP), CNN (imagen) |
| **No supervisado** | Video y patrones de acceso | Detectar comportamientos atípicos o accesos fuera del horario | Autoencoder, K-Means |
| **Reinforcement Learning** (opcional como 3er tipo) | Agente inteligente | Aprender cuándo y cómo responder o escalar alertas (optimización de acciones) | Q-Learning, Deep Q-Network |

---

## 4. ROL DEL AGENTE INTELIGENTE (AUTOMATIZACIÓN)

El agente inteligente actúa como una capa de automatización sobre el sistema:

### Interfaz con usuarios
- Atiende mensajes por WhatsApp o correo electrónico
- Interpreta intenciones (gracias al modelo NLP supervisado)

### Conexión con la base de datos
- Consulta información del sistema: quién ingresó, cuántas personas hay, reservas activas
- Genera reportes automáticos (usando prompts) y los envía por email o mensaje

### Acciones automatizadas
- Si detecta un ingreso sospechoso (según ML no supervisado), puede bloquear temporalmente un acceso o enviar alerta
- Si el modelo de reconocimiento facial tiene baja confianza, solicita confirmación humana

### Herramientas
- LangChain o RASA para la estructura del agente
- Conexión a la base PostgreSQL mediante ORM
- Lógica basada en Reinforcement Learning para mejorar decisiones de automatización

---

## 5. DASHBOARD

El dashboard se construirá con:
- **Frontend:** React.js + TailwindCSS
- **Backend:** Node.js + Express
- **Visualización:** Recharts
- **Base de datos:** MongoDB (para almacenar métricas agregadas y resultados de modelos)

### KPIs sugeridos:
- Total de ingresos por día
- Porcentaje de reconocimiento facial exitoso
- Promedio de tiempo de permanencia
- Alertas automáticas generadas (por agente o ML)

---

## 6. BASES DE DATOS

| Base de datos | Propósito | Ubicación recomendada |
|---------------|-----------|----------------------|
| **PostgreSQL** | Datos estructurados del sistema (usuarios, entradas, roles) | AWS RDS |
| **MongoDB** | Datos no estructurados: logs, resultados de modelos, respuestas del agente | Mongo Atlas |
| **SQL Server** (de pago) | Auditoría, estadísticas históricas y reportes | Azure SQL |

---

## 7. MICROSERVICIOS Y DESPLIEGUE MULTI-NUBE

| Microservicio | Lenguaje | Proveedor | DB principal |
|---------------|----------|-----------|--------------|
| Auth & Usuarios | Java (Spring Boot) | AWS (EKS) | PostgreSQL |
| Core Biblioteca (QR, control acceso) | C# (.NET) | Azure (AKS) | SQL Server |
| ML / IA Service | Python (FastAPI) | GCP (GKE) | MongoDB |
| Notificaciones / Integraciones | Go o Node.js | DigitalOcean | MongoDB |
| Dashboard API | Python (Django) | Oracle Cloud | PostgreSQL |
| Agente Inteligente | Python (RASA) | Hetzner / Render | PostgreSQL |
| Mobile API | Node.js | IBM Cloud / Render | PostgreSQL |

---

## 8. APP MÓVIL

### Funciones principales:
- Escanear código QR para ingresar
- Recibir notificaciones o alertas del agente
- Ver historial personal de accesos y tiempo de permanencia

**Nota:** No incluye gestión completa del sistema (solo funcionalidades necesarias)

---

**Ubicación:** Santa Cruz – Bolivia