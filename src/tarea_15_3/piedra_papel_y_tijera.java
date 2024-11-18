package tarea_15_3;
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionListener;
	import java.util.HashMap;
	import java.util.Random;

	public class piedra_papel_y_tijera extends JFrame {
	    private String[] opciones = {"Piedra", "Papel", "Tijera"};
	    private HashMap<String, Integer> historialJugador = new HashMap<>();
	    private JLabel lblResultado;
	    private JLabel lblHistorial;

	    public piedra_papel_y_tijera() {
	        setTitle("Piedra, Papel o Tijera");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout(10, 10));

	        lblResultado = new JLabel("¡Haz tu jugada!", SwingConstants.CENTER);
	        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
	        add(lblResultado, BorderLayout.NORTH);

	        JPanel panelBotones = new JPanel(new GridLayout(1, 3));
	        for (String opcion : opciones) {
	            JButton btn = new JButton(opcion);
	            btn.addActionListener(e -> jugar(opcion));
	            panelBotones.add(btn);
	        }
	        add(panelBotones, BorderLayout.CENTER);

	        lblHistorial = new JLabel("Historial: ", SwingConstants.CENTER);
	        lblHistorial.setFont(new Font("Arial", Font.PLAIN, 14));
	        add(lblHistorial, BorderLayout.SOUTH);

	        setVisible(true);
	    }

	    private void jugar(String eleccionJugador) {
	        // Actualizar historial del jugador
	        historialJugador.put(eleccionJugador, historialJugador.getOrDefault(eleccionJugador, 0) + 1);

	        // Máquina selecciona su jugada
	        String eleccionMaquina = elegirJugadaCompetitiva();

	        // Determinar ganador
	        String resultado = determinarGanador(eleccionJugador, eleccionMaquina);

	        lblResultado.setText("Tú: " + eleccionJugador + " | Máquina: " + eleccionMaquina + " | " + resultado);
	        actualizarHistorial();
	    }

	    private String elegirJugadaCompetitiva() {
	        if (historialJugador.isEmpty()) {
	            // Sin historial, elige aleatoriamente
	            return opciones[new Random().nextInt(opciones.length)];
	        }

	        // Predecir jugada del jugador más frecuente
	        String prediccionJugador = historialJugador.entrySet().stream()
	                .max((a, b) -> a.getValue().compareTo(b.getValue()))
	                .map(HashMap.Entry::getKey)
	                .orElse("Piedra");

	        // Elegir jugada para vencer la predicción del jugador
	        switch (prediccionJugador) {
	            case "Piedra":
	                return "Papel";
	            case "Papel":
	                return "Tijera";
	            case "Tijera":
	                return "Piedra";
	            default:
	                return opciones[new Random().nextInt(opciones.length)];
	        }
	    }

	    private String determinarGanador(String jugador, String maquina) {
	        if (jugador.equals(maquina)) {
	            return "¡Empate!";
	        } else if ((jugador.equals("Piedra") && maquina.equals("Tijera")) ||
	                (jugador.equals("Papel") && maquina.equals("Piedra")) ||
	                (jugador.equals("Tijera") && maquina.equals("Papel"))) {
	            return "¡Ganaste!";
	        } else {
	            return "¡Perdiste!";
	        }
	    }

	    private void actualizarHistorial() {
	        StringBuilder historial = new StringBuilder("<html>Historial:<br>");
	        for (String opcion : opciones) {
	            historial.append(opcion).append(": ").append(historialJugador.getOrDefault(opcion, 0)).append("<br>");
	        }
	        historial.append("</html>");
	        lblHistorial.setText(historial.toString());
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(piedra_papel_y_tijera::new);
	    }
	}

