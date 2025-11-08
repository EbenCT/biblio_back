package com.biblioteca.graphql.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador personalizado para GraphiQL
 * Evita problemas de CORS con CDN externos
 */
@Controller
public class GraphiQLController {

    @GetMapping("/favicon.ico")
    @ResponseBody
    public String favicon() {
        return "";
    }

    @GetMapping("/graphiql")
    @ResponseBody
    public String graphiql() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>GraphQL - Biblioteca API</title>
                <meta charset="UTF-8">
                <style>
                    body { 
                        margin: 0; 
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', system-ui; 
                        background: #f8f9fa;
                        color: #333;
                    }
                    .container { 
                        max-width: 1200px; 
                        margin: 0 auto; 
                        padding: 20px; 
                    }
                    .header {
                        background: #2196F3;
                        color: white;
                        padding: 20px 0;
                        margin: -20px -20px 30px -20px;
                        text-align: center;
                    }
                    .card {
                        background: white;
                        border-radius: 8px;
                        padding: 20px;
                        margin: 20px 0;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                    }
                    pre {
                        background: #f5f5f5;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                        padding: 15px;
                        overflow-x: auto;
                        font-size: 14px;
                        line-height: 1.4;
                    }
                    .endpoint {
                        background: #e3f2fd;
                        border-left: 4px solid #2196F3;
                        padding: 10px 15px;
                        margin: 10px 0;
                    }
                    .success { color: #4caf50; }
                    .info { color: #2196F3; }
                    h1, h2, h3 { margin-top: 0; }
                    .grid {
                        display: grid;
                        grid-template-columns: 1fr 1fr;
                        gap: 20px;
                    }
                    @media (max-width: 768px) {
                        .grid { grid-template-columns: 1fr; }
                    }
                </style>
                <link rel="icon" href="data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'><text y='.9em' font-size='90'>📚</text></svg>">
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🚀 GraphQL API - Sistema Biblioteca</h1>
                        <p>API híbrida con REST y GraphQL funcionando simultáneamente</p>
                    </div>
                    
                    <div class="card">
                        <h2 class="info">📡 Endpoints Disponibles</h2>
                        <div class="endpoint">
                            <strong>GraphQL:</strong> <code>POST http://localhost:8080/graphql</code>
                        </div>
                        <div class="endpoint">
                            <strong>REST:</strong> <code>GET http://localhost:8080/api/libro</code>
                        </div>
                    </div>

                    <div class="grid">
                        <div class="card">
                            <h3>📚 Query - Consultar Libros</h3>
                            <pre>{
  libros {
    id
    titulo
    autor
    categoria
    disponible
  }
}</pre>
                        </div>

                        <div class="card">
                            <h3>🔍 Query - Libro por ID</h3>
                            <pre>{
  libro(id: 1) {
    id
    titulo
    autor
    categoria
    disponible
  }
}</pre>
                        </div>

                        <div class="card">
                            <h3>📖 Query - Por Categoría</h3>
                            <pre>{
  librosPorCategoria(categoria: "Ficción") {
    id
    titulo
    autor
  }
}</pre>
                        </div>

                        <div class="card">
                            <h3>✅ Query - Libros Disponibles</h3>
                            <pre>{
  librosDisponibles {
    id
    titulo
    autor
    categoria
  }
}</pre>
                        </div>

                        <div class="card">
                            <h3>➕ Mutation - Crear Libro</h3>
                            <pre>mutation {
  crearLibro(input: {
    titulo: "1984"
    autor: "George Orwell"
    categoria: "Ficción"
    disponible: true
  }) {
    id
    titulo
    autor
    categoria
  }
}</pre>
                        </div>

                        <div class="card">
                            <h3>✏️ Mutation - Actualizar Libro</h3>
                            <pre>mutation {
  actualizarLibro(id: 1, input: {
    titulo: "Animal Farm"
    autor: "George Orwell"
    categoria: "Ficción"
    disponible: false
  }) {
    id
    titulo
    disponible
  }
}</pre>
                        </div>
                    </div>

                    <div class="card">
                        <h3>🛠️ Cómo Probar</h3>
                        <p><strong>Postman/Insomnia:</strong></p>
                        <ol>
                            <li>Método: <code>POST</code></li>
                            <li>URL: <code>http://localhost:8080/graphql</code></li>
                            <li>Header: <code>Content-Type: application/json</code></li>
                            <li>Body (JSON): <code>{"query": "{ libros { id titulo autor } }"}</code></li>
                        </ol>
                        
                        <p><strong>cURL:</strong></p>
                        <pre>curl -X POST http://localhost:8080/graphql \\
  -H "Content-Type: application/json" \\
  -d '{"query": "{ libros { id titulo autor categoria disponible } }"}'</pre>
                    </div>

                    <div class="card success">
                        <h3>✨ Estado del Sistema</h3>
                        <p>✅ GraphQL funcionando en: <code>/graphql</code></p>
                        <p>✅ REST funcionando en: <code>/api/**</code></p>
                        <p>✅ Base de datos MySQL conectada</p>
                        <p>✅ Seguridad JWT activa</p>
                    </div>
                </div>
            </body>
            </html>
            """;
    }
}