package Banco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//necesarios para la seguridad de la contrasena
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//para la lista
import java.util.ArrayList;
import java.util.List;

public class controlador {
    
    //crea un archivo para guardar los usuarios
    private static final String ARCHIVO_USUARIOS = "usuarios.csv";
    //para el ID unico del admin
    private static final String ID_ADMIN = "Administrador";
    //lista de usuarios 
    private static final List<Usuario> usuarios = new ArrayList<>();
    //ID de usuarios que comienza en 1
    private static int contadorId = 1;
    
    //Clase de usuario
    public static class Usuario {
        public final String id;
        public final String nombre;
        public final String apellido;
        private final String hashContrasena;
        public final String rol;
        public double sueldo;
        
        //Contructor de usuario
        public Usuario(String id, String nombre, String apellido, String hashContrasena, String rol, double sueldo) {
            this.id = id;
            this.nombre = nombre;
            this.apellido = apellido;
            this.hashContrasena = hashContrasena;
            this.rol = rol;
            this.sueldo = sueldo;
        }
        
        //Si detecta a un admin entonces se le concede criterio de administrador
        public boolean esAdmin() {
            return "ADMIN".equalsIgnoreCase(rol) || ID_ADMIN.equalsIgnoreCase(id);
        }
    }
    
    //Carga a los usuarios por defecto y se asegura de que siempre haiga un admin
    static {
        cargarUsuarios();
        asegurarAdminPorDefecto();
    }

    //Metodo que sirve para encriptar la contrasena de los usuarios en el documento
    //No altera la contrasena original del usuario, solo la esconde
    private static String hashearContrasena(String contrasena) {
        try {
            //esconde la contraseña y la encripta para que no se recupere al formato normal
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Convierte la contraseña en un arreglo de bytes con URF_8
            //md.digest devuelve el arreglo SHA_256 en 32 bytes
            byte[] bytes = md.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            //Recorre los 32 bytes para convertirlos en hexagecimal con minusculas
            //%02x asegura que contengan siempre 2 digitos
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            //Regresa los digitos como un string y los manda al documento
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("hubo un error al esconder la contraseña", ex);
        }
    }

    // se asegura que el usuario ingrese los datos del documento para poder entrar
    public static boolean registrar(String nombre, String apellido, String contrasena, String confirmarContrasena, double sueldo) {
        if (nombre.isEmpty() || apellido.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            return false;
        }
        if (!contrasena.equals(confirmarContrasena)) {
            return false;
        }
        if (sueldo < 0) {
            return false;
        }
        if (existeUsuario(nombre, apellido)) {
            return false;
        }

        String nuevoId = generarSiguienteId();
        String hash = hashearContrasena(contrasena);
        usuarios.add(new Usuario(nuevoId, nombre, apellido, hash, "USUARIO", sueldo));
        guardarUsuarios();
        return true;
    }

    //principal metodo para actualizar documentos anteriores
    public static boolean registrar(String nombre, String apellido, String contrasena, String confirmarContrasena) {
        return registrar(nombre, apellido, contrasena, confirmarContrasena, 0.0);
    }
    
    //Se asegura de que el usuario exista 
    public static boolean existeUsuario(String nombre, String apellido) {
        for (Usuario u : usuarios) {
            if (u.nombre.equalsIgnoreCase(nombre) && u.apellido.equalsIgnoreCase(apellido)) {
                return true;
            }
        }
        return false;
    }
    
    //Valida la autenticidad del usuario 
    public static Usuario autenticar(String nombre, String apellido, String contrasena) {
        String hashIngresado = hashearContrasena(contrasena);
        for (Usuario u : usuarios) {
            if (u.nombre.equalsIgnoreCase(nombre)
                    && u.apellido.equalsIgnoreCase(apellido)
                    && u.hashContrasena.equals(hashIngresado)) {
                return u;
            }
        }
        return null;
    }
    
    //guarda cambios realizados al documento (usualmente util al actualizar el sueldo)
    public static void guardarCambios() {
        guardarUsuarios();
    }
    
    //Actualiza el ID si es que el usuario se registra exitosamente
    private static String generarSiguienteId() {
        String id;
        do {
            id = "ID -" + contadorId;
            contadorId++;
        } while (existeId(id));
        return id;
    }

    //Valida que haiga un ID vinculado a cada usuario
    private static boolean existeId(String id) {
        for (Usuario u : usuarios) {
            if (u.id.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
    
    //asegura que siempte haiga un admin registrado
    private static void asegurarAdminPorDefecto() {
        for (Usuario u : usuarios) {
            if (u.esAdmin()) {
                return;
            }
        }
        if (!existeId(ID_ADMIN)) {
            //datos del admin junto con la contrasena
            //la contrasena tambien se encripta en el documento
            usuarios.add(0, new Usuario(ID_ADMIN, "Banco", "Defenders", hashearContrasena("admin123"), "ADMIN", 0.0));
            guardarUsuarios();
        }
    }

    //Prirncipal metodo para guardar los usuarios en el archivo con todos sus datos y la contrasena encriptada
    private static void guardarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                bw.write(u.id + ";" + u.nombre + ";" + u.apellido + ";" + u.hashContrasena + ";" + u.rol + ";" + u.sueldo);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Hubo un error al guardar usuario " + ex.getMessage());
        }
    }

    //Carga a los usuarios desde el archivo
    private static void cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            return;
        }

        usuarios.clear();
        contadorId = 1;

        //conjuntos de trys que se aseguran de que los datos del usuario se cargen correctamente desde el archivo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";", -1);
                if (partes.length == 6) {
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    String apellido = partes[2].trim();
                    String hashGuardado = partes[3].trim();
                    String rol = partes[4].trim();
                    double sueldo = 0.0;
                    try {
                        sueldo = Double.parseDouble(partes[5].trim());
                    } catch (NumberFormatException ignored) {
                    }

                    usuarios.add(new Usuario(id, nombre, apellido, hashGuardado, rol, sueldo));
                    actualizarContador(id);
                } else if (partes.length == 5) {
                    String id = partes[0].trim();
                    usuarios.add(new Usuario(
                            id,
                            partes[1].trim(),
                            partes[2].trim(),
                            partes[3].trim(),
                            partes[4].trim(),
                            0.0
                    ));
                    actualizarContador(id);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al cargar usuarios: " + ex.getMessage());
        }
    }

    //Actualiza el contador ID
    private static void actualizarContador(String id) {
        if (id != null && id.startsWith("ID -")) {
            try {
                int num = Integer.parseInt(id.substring(4).trim());
                if (num >= contadorId) {
                    contadorId = num + 1;
                }
            } catch (NumberFormatException ignored) {
            }
        }
    }
}

