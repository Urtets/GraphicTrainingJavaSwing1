import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrafficSignal extends JPanel implements ActionListener {

    // --- Constants ---
    private static final int LIGHT_DIAMETER = 50;
    private static final int HOUSING_WIDTH = LIGHT_DIAMETER + 30; // Add padding
    private static final int HOUSING_HEIGHT = (LIGHT_DIAMETER * 3) + (20 * 3) + 30; // 3 lights + 3 gaps + padding
    private static final int GAP = 10;
    private static final int PADDING = 15;

    private static final Color COLOR_RED_ON = new Color(255, 0, 0);
    private static final Color COLOR_YELLOW_ON = new Color(255, 255, 0);
    private static final Color COLOR_GREEN_ON = new Color(0, 255, 0);
    private static final Color COLOR_OFF = new Color(50, 50, 50); // Dark grey for off lights
    private static final Color COLOR_HOUSING = new Color(30, 30, 30); // Dark grey for housing

    // --- State Variables ---
    private int currentState = 0; // 0: Red, 1: Yellow, 2: Green
    private Timer timer;
    private static final int RED_DURATION = 3000;  // 3 seconds
    private static final int YELLOW_DURATION = 1000; // 1 second
    private static final int GREEN_DURATION = 3000; // 3 seconds

    public void TrafficLightPanel() {
        setPreferredSize(new Dimension(HOUSING_WIDTH, HOUSING_HEIGHT));
        setBackground(Color.BLACK); // Background of the panel itself (behind the housing)

        // Initialize the timer for light changes
        // We'll start the timer when the frame is visible
    }

    // This method is called whenever the panel needs to be redrawn
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the panel

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate positions for the housing
        int housingX = (getWidth() - HOUSING_WIDTH) / 2;
        int housingY = (getHeight() - HOUSING_HEIGHT) / 2;

        // 1. Draw the housing
        g2d.setColor(COLOR_HOUSING);
        g2d.fillRoundRect(housingX, housingY, HOUSING_WIDTH, HOUSING_HEIGHT, 20, 20); // Rounded rectangle

        // 2. Draw the lights
        int lightY = housingY + PADDING; // Starting Y for the top light

        // Red Light
        g2d.setColor(currentState == 0 ? COLOR_RED_ON : COLOR_OFF);
        g2d.fillOval(housingX + PADDING, lightY, LIGHT_DIAMETER, LIGHT_DIAMETER);

        // Yellow Light
        lightY += LIGHT_DIAMETER + GAP;
        g2d.setColor(currentState == 1 ? COLOR_YELLOW_ON : COLOR_OFF);
        g2d.fillOval(housingX + PADDING, lightY, LIGHT_DIAMETER, LIGHT_DIAMETER);

        // Green Light
        lightY += LIGHT_DIAMETER + GAP;
        g2d.setColor(currentState == 2 ? COLOR_GREEN_ON : COLOR_OFF);
        g2d.fillOval(housingX + PADDING, lightY, LIGHT_DIAMETER, LIGHT_DIAMETER);
    }

    // Method to change the light state and repaint
    private void changeLight() {
        currentState = (currentState + 1) % 3; // Cycle through 0, 1, 2
        repaint(); // Request a repaint to show the new light
    }

    // Method to start the traffic light simulation
    public void start() {
        if (timer == null) {
            // Initialize timer based on current state
            int delay = getDelayForState(currentState);
            timer = new Timer(delay, this);
            timer.start();
        } else if (!timer.isRunning()) {
            // If timer was stopped, restart it
            int delay = getDelayForState(currentState);
            timer.setInitialDelay(delay); // Set the initial delay for the current state
            timer.start();
        }
    }

    // Method to stop the traffic light simulation
    public void stop() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    // ActionListener implementation for the timer
    @Override
    public void actionPerformed(ActionEvent e) {
        // Determine the delay for the *next* state
        int nextDelay = getDelayForState(currentState);

        // Change the light
        changeLight();

        // Reset the timer with the correct delay for the *new* current state
        if (timer != null) {
            timer.setInitialDelay(getDelayForState(currentState)); // Set delay for the light that just turned ON
            timer.restart(); // Restart the timer
        }
    }

    // Helper to get the duration for each light state
    private int getDelayForState(int state) {
        switch (state) {
            case 0: return RED_DURATION;
            case 1: return YELLOW_DURATION;
            case 2: return GREEN_DURATION;
            default: return 1000; // Default to 1 second
        }
    }

    // --- Main method to run the example ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Java Swing Traffic Light");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout()); // Use BorderLayout for the frame

                TrafficSignal trafficLight = new TrafficSignal();
                frame.add(trafficLight, BorderLayout.CENTER); // Add to the center

                frame.pack(); // Sizes the frame based on preferred sizes of its components
                frame.setLocationRelativeTo(null); // Center the frame on the screen
                frame.setVisible(true);

                // Start the traffic light simulation once the frame is visible
                trafficLight.start();
            }
        });
    }
}