package Banco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//Librerias propias para la seguridad de la contraseña
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//Listas
import java.util.ArrayList;
import java.util.List;

public class controlador {
    
    //Crea el archivo donde se guardan los usuaios
    private static final String ARCHIVO_USUARIOS = "usuarios.csv";
    //Crea el usuario administrador
    private static final String ID_ADMIN = "Administrador";

    //Lista de usuarios
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static int contadorId = 1;
    
    //Clase del usuario
    public static class Usuario {
        public final String id;
        public final String nombre;
        public final String apellido;
        
    //Esconde la contraseña en el archivo
        private final String hashContrasena;
        public final String rol;

    //Contructor del usuario
        public Usuario(String id, String nombre, String apellido, String hashContrasena, String rol) {
            this.id = id;
            this.nombre = nombre;
            this.apellido = apellido;
            this.hashContrasena = hashContrasena;
            this.rol = rol;
        }

        //Si el usuario es el admin le da privilegios de admin
        public boolean esAdmin() {
            return "ADMIN".equalsIgnoreCase(rol) || ID_ADMIN.equalsIgnoreCase(id);
        }
    }

    static {
        cargarUsuarios();
        asegurarAdminPorDefecto();
    }
    
    //metodo que funciona principalmente para esconder la contraseña de los usuarios con hashtags
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

    //Verifica que el usuario ingrese los datos correctamente
    public static boolean registrar(String nombre, String apellido, String contrasena, String confirmarContrasena) {
        if (nombre.isEmpty() || apellido.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            return false;
        }
        if (!contrasena.equals(confirmarContrasena)) {
            return false;
        }
        if (existeUsuario(nombre, apellido)) {
            return false;
        }

        String nuevoId = generarSiguienteId();
        String hash = hashearContrasena(contrasena);
        usuarios.add(new Usuario(nuevoId, nombre, apellido, hash, "USUARIO"));
        guardarUsuarios();
        return true;
    }

    //verifica que el usuario existe
    public static boolean existeUsuario(String nombre, String apellido) {
        for (Usuario u : usuarios) {
            if (u.nombre.equalsIgnoreCase(nombre) && u.apellido.equalsIgnoreCase(apellido)) {
                return true;
            }
        }
        return false;
    }
    
    //Autenticador para que asegure que el usuario existe
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

    //actualiza id cuando un usuario se registra
    private static String generarSiguienteId() {
        String id;
        do {
            id = "ID - " + contadorId;
            contadorId++;
        } while (existeId(id));
        return id;
    }

    //verifica que el ID vinculado a los usuarios exista
    private static boolean existeId(String id) {
        for (Usuario u : usuarios) {
            if (u.id.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    //Asegura que siempre haiga un admin
    private static void asegurarAdminPorDefecto() {
        for (Usuario u : usuarios) {
            if (u.esAdmin()) {
                return;
            }
        }
        if (!existeId(ID_ADMIN)) {
            usuarios.add(0, new Usuario(ID_ADMIN, "Banco", "Defenders", hashearContrasena("admin123"), "ADMIN"));
            guardarUsuarios();
        }
    }

    //Guarda a los usuarios en el archivo
    private static void guardarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                bw.write(u.id + ";" + u.nombre + ";" + u.apellido + ";" + u.hashContrasena + ";" + u.rol);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Hubo un error al guardar el usuario " + ex.getMessage());
        }
    }

    //carga a los usuarios desde el archivo
    private static void cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            return;
        }

        usuarios.clear();
        contadorId = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";", -1);
                if (partes.length == 5) {
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    String apellido = partes[2].trim();
                    String hashGuardado = partes[3].trim();
                    String rol = partes[4].trim();

                    usuarios.add(new Usuario(id, nombre, apellido, hashGuardado, rol));
                    actualizarContador(id);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al cargar usuarios: " + ex.getMessage());
        }
    }

    //Actualiza el contador id
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